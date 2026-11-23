#!/usr/bin/env python3
"""Verify the release artifacts before they are signed and uploaded (build-release.yml, verify job).

Usage: python3 -I .github/scripts/verify-artifacts.py --version <v> --directory <dir> [--repo <root>]

<dir> must hold exactly the five files Maven Central receives for checkout-sdk-java <v>: the
jar, the sources and javadoc jars, the POM and the Gradle module metadata. Every check refuses
on anything unexpected (fail closed):

- the file set is exact and every file is a regular file;
- every archive: entry names read from the raw central directory and equal to each local
  header's name (no NUL, ASCII only, no absolute, `..` or backslash names, no names that differ
  only in case, no directory entry with data, no zip comment or trailing data) and a manifest
  with exactly the bytes Gradle writes;
- jar: only classes whose package is a package of src/main/java, plus the manifest, whose
  Implementation-Version is <v>;
- sources jar: exactly the tracked src/main/java/**/*.java files, byte for byte, plus the manifest;
- javadoc jar: documentation file types only (no class, jar or other binary; only the javadoc
  search-index zips, each holding only its JSON index);
- POM: UTF-8 and ASCII only, its name, description, url, license, developer and scm values
  exactly the project_* values of gradle.properties, no DTD, entity or processing instruction, no control or format characters (also
  as character references), no attribute below the root, no text next to child elements, the
  nested licenses, developers and scm shapes Gradle writes, only the expected top-level elements, coordinates com.checkout:checkout-sdk-java:<v>, and dependencies
  exactly the `implementation` dependencies of build.gradle, scope runtime;
- module: no duplicate JSON keys, the same coordinates and dependencies, exactly the two Gradle
  variants of the main jar with their expected attributes (JSON types compared too), and each
  file entry bound to the real file by size and sha256.

The expected dependencies come from build.gradle itself, so a dependency change needs no edit
here; a build.gradle declaration this script cannot read fails the check.
"""

import argparse
import hashlib
import io
import json
import os
import re
import stat
import struct
import subprocess
import sys
import unicodedata
import xml.etree.ElementTree as ET
import zlib
import zipfile

GROUP = "com.checkout"
ARTIFACT = "checkout-sdk-java"
POM_NS = "{http://maven.apache.org/POM/4.0.0}"
XSI_SCHEMA_LOCATION = "{http://www.w3.org/2001/XMLSchema-instance}schemaLocation"
POM_TOP_LEVEL = {
    "modelVersion", "groupId", "artifactId", "version", "name", "description", "url",
    "licenses", "developers", "scm", "dependencies", "packaging",
}
POM_LEAVES = {"modelVersion", "groupId", "artifactId", "version", "name", "description", "url", "packaging"}
POM_DEPENDENCY_CHILDREN = {"groupId", "artifactId", "version", "scope"}
# The sources and javadoc jars are attached to the publication as plain artifacts, so the module
# metadata only describes the main jar. Attributes as Gradle writes them for a Java 8 library.
VARIANT_ATTRIBUTES = {
    "apiElements": {
        "org.gradle.category": "library", "org.gradle.dependency.bundling": "external",
        "org.gradle.jvm.version": 8, "org.gradle.libraryelements": "jar", "org.gradle.usage": "java-api",
    },
    "runtimeElements": {
        "org.gradle.category": "library", "org.gradle.dependency.bundling": "external",
        "org.gradle.jvm.version": 8, "org.gradle.libraryelements": "jar", "org.gradle.usage": "java-runtime",
    },
}
COMPONENT_ATTRIBUTES = {"org.gradle.status": "release"}
JAVADOC_EXTENSIONS = {".html", ".css", ".js", ".png", ".gif", ".svg", ".md"}
JAVADOC_NAMES = {
    "element-list", "package-list",
    "legal/LICENSE", "legal/ADDITIONAL_LICENSE_INFO", "legal/ASSEMBLY_EXCEPTION",
    "member-search-index.zip", "package-search-index.zip", "type-search-index.zip",
    "module-search-index.zip", "tag-search-index.zip",
}
PLAIN_MANIFEST = b"Manifest-Version: 1.0\r\n\r\n"
DEPENDENCY_LINE = re.compile(r"^\s*implementation\s+'([A-Za-z0-9_.-]+):([A-Za-z0-9_.-]+):([A-Za-z0-9_.-]+)'\s*$")
CONFIGURATION_LINE = re.compile(r"^\s*(api|implementation|runtimeOnly|compileOnlyApi)\b")


class VerificationError(Exception):
    pass


def fail(message):
    raise VerificationError(message)


def expected_files(version):
    base = f"{ARTIFACT}-{version}"
    return {
        "jar": f"{base}.jar",
        "sources": f"{base}-sources.jar",
        "javadoc": f"{base}-javadoc.jar",
        "pom": f"{base}.pom",
        "module": f"{base}.module",
    }


def check_file_set(directory, files):
    if not os.path.isdir(directory) or os.path.islink(directory):
        fail(f"{directory} is not a directory")
    actual = sorted(os.listdir(directory))
    if actual != sorted(files.values()):
        fail(f"the directory must hold exactly {sorted(files.values())}, found {actual}")
    for name in files.values():
        mode = os.lstat(os.path.join(directory, name)).st_mode
        if not stat.S_ISREG(mode):
            fail(f"{name} is not a regular file")


def read_dependencies(build_gradle):
    expected = set()
    with open(build_gradle, encoding="utf-8") as handle:
        for number, line in enumerate(handle, 1):
            if not CONFIGURATION_LINE.match(line):
                continue
            match = DEPENDENCY_LINE.match(line)
            if not match:
                fail(f"build.gradle:{number}: dependency declaration not in the form implementation 'group:name:version'")
            expected.add(match.groups())
    if not expected:
        fail("build.gradle declares no implementation dependency")
    return expected


def tracked_sources(repo):
    output = subprocess.run(
        ["git", "-C", repo, "ls-files", "-z", "--", "src/main/java"],
        check=True, capture_output=True,
    ).stdout.decode("utf-8")
    prefix = "src/main/java/"
    sources = {path[len(prefix):] for path in output.split("\0") if path.endswith(".java")}
    if not sources:
        fail("no tracked source under src/main/java")
    return sources


def raw_zip_names(data, label):
    """Entry names as stored in the zip (zipfile cuts them at a NUL), after checking that the
    local entries follow each other from the first byte to the central directory with nothing
    in between: no entry can hide from the central directory, and each local name equals the
    central one (stream readers such as JarInputStream use the local headers)."""
    end = data.rfind(b"PK\x05\x06")
    if end < 0 or len(data) - end < 22:
        fail(f"{label} has no zip end record")
    disk, cd_disk, here, total, size, offset, comment = struct.unpack("<HHHHIIH", data[end + 4:end + 22])
    if comment or end + 22 != len(data):
        fail(f"{label} has a zip comment or data after its end record")
    # A zip64 archive has its locator right before the end record; the signature bytes can also
    # occur inside compressed data, so only that position counts.
    zip64 = end >= 20 and data[end - 20:end - 16] == b"PK\x06\x07"
    if disk or cd_disk or here != total or total == 0xFFFF or offset == 0xFFFFFFFF or zip64:
        fail(f"{label} is a multi-disk or zip64 archive")
    if offset + size != end:
        fail(f"{label} has data between its central directory and its end record")
    entries, position = [], offset
    for _ in range(total):
        if data[position:position + 4] != b"PK\x01\x02":
            fail(f"{label} has a damaged central directory")
        (needed,) = struct.unpack("<H", data[position + 6:position + 8])
        flags, method = struct.unpack("<HH", data[position + 8:position + 12])
        crc, compressed, uncompressed = struct.unpack("<III", data[position + 16:position + 28])
        name_length, extra_length, comment_length = struct.unpack("<HHH", data[position + 28:position + 34])
        (local_offset,) = struct.unpack("<I", data[position + 42:position + 46])
        entries.append((local_offset, compressed, flags, data[position + 46:position + 46 + name_length],
                        method, crc, uncompressed, needed))
        position += 46 + name_length + extra_length + comment_length
    if position != end:
        fail(f"{label} has a central directory of an unexpected size")
    cursor = 0
    for local_offset, compressed, flags, name, method, crc, uncompressed, needed in sorted(entries):
        if local_offset != cursor or data[cursor:cursor + 4] != b"PK\x03\x04":
            fail(f"{label} has data that no central directory entry describes (a hidden entry?)")
        local_length, local_extra = struct.unpack("<HH", data[cursor + 26:cursor + 30])
        if data[cursor + 30:cursor + 30 + local_length] != name:
            fail(f"{label} has a local header name that differs from the central directory")
        # The local header must describe the same entry as the central record: stream readers
        # (JarInputStream) use the local method, flags and sizes to find where the entry ends.
        l_needed, l_flags, l_method = struct.unpack("<HHH", data[cursor + 4:cursor + 10])
        l_crc, l_compressed, l_uncompressed = struct.unpack("<III", data[cursor + 14:cursor + 26])
        if (l_needed, l_flags, l_method) != (needed, flags, method):
            fail(f"{label} has a local header whose version, flags or method differ from the central directory")
        recorded = (crc, compressed, uncompressed)
        if (l_crc, l_compressed, l_uncompressed) != recorded and not (flags & 0x08 and (l_crc, l_compressed, l_uncompressed) == (0, 0, 0)):
            fail(f"{label} has a local header whose CRC or sizes differ from the central directory")
        start = cursor + 30 + local_length + local_extra
        stored = data[start:start + compressed]
        # The entry's data must be exactly its compressed stream: a deflate stream that ends before
        # the central size would leave room for an entry only stream readers see.
        if method == 8:
            inflater = zlib.decompressobj(-15)
            try:
                content = inflater.decompress(stored)
            except zlib.error:
                fail(f"{label} has an entry whose data is not a deflate stream")
            if not inflater.eof or inflater.unused_data or inflater.unconsumed_tail:
                fail(f"{label} has an entry whose deflate stream does not end at its recorded size")
        elif method == 0 and not flags & 0x08:
            content = stored
        else:
            fail(f"{label} has an entry with an unexpected compression method or a stored data descriptor")
        if len(content) != uncompressed or zlib.crc32(content) != crc:
            fail(f"{label} has an entry whose content does not match its recorded size or CRC")
        cursor = start + compressed
        if flags & 0x08:  # data descriptor after the data, with or without its signature
            if data[cursor:cursor + 4] == b"PK\x07\x08":
                cursor += 4
            if struct.unpack("<III", data[cursor:cursor + 12]) != recorded:
                fail(f"{label} has a data descriptor that differs from the central directory")
            cursor += 12
    if cursor != offset:
        fail(f"{label} has data that no central directory entry describes (a hidden entry?)")
    return [entry[3] for entry in entries]


def raw_central_directory_names(path):
    with open(path, "rb") as handle:
        return raw_zip_names(handle.read(), os.path.basename(path))


def zip_entries(path, manifest):
    label = os.path.basename(path)
    raw = raw_central_directory_names(path)
    try:
        with zipfile.ZipFile(path) as archive:
            infos = archive.infolist()
            found_manifest = archive.read("META-INF/MANIFEST.MF") if "META-INF/MANIFEST.MF" in archive.namelist() else None
    except (zipfile.BadZipFile, NotImplementedError, OSError):
        fail(f"{label} is not a zip archive")
    names = [info.filename for info in infos]
    if [name.encode("utf-8") for name in names] != raw:
        fail(f"{label} has an entry name that does not read back as stored (NUL or encoding)")
    if len({name.casefold() for name in names}) != len(names):
        fail(f"{label} has duplicate entries (case-insensitively)")
    for info in infos:
        name = info.filename
        if (not name.isascii() or name.startswith("/") or "\\" in name or ":" in name
                or any(ord(char) < 0x20 or ord(char) == 0x7F for char in name)
                or any(part in ("", ".", "..") for part in name.rstrip("/").split("/"))):
            fail(f"{label} has an unsafe entry name {name!r}")
        if name.endswith("/") and info.file_size:
            fail(f"{label} has a directory entry with data: {name!r}")
    if found_manifest != manifest:
        fail(f"{label} manifest is not the expected one")
    return [name for name in names if not name.endswith("/") and name != "META-INF/MANIFEST.MF"], path


def check_jar(path, version, sources):
    manifest = (f"Manifest-Version: 1.0\r\nImplementation-Title: {ARTIFACT}\r\n"
                f"Implementation-Version: {version}\r\n\r\n").encode("utf-8")
    names, _ = zip_entries(path, manifest)
    packages = {os.path.dirname(source) for source in sources}
    if not names:
        fail("the jar holds no class")
    for name in names:
        if not name.endswith(".class") or os.path.dirname(name) not in packages:
            fail(f"jar entry {name!r} is not a class of a src/main/java package")


def check_sources(path, repo, sources):
    names, _ = zip_entries(path, PLAIN_MANIFEST)
    entries = set(names)
    if entries != sources:
        extra = sorted(entries - sources)[:5]
        missing = sorted(sources - entries)[:5]
        fail(f"the sources jar does not match src/main/java (extra {extra}, missing {missing})")
    with zipfile.ZipFile(path) as archive:
        for name in sorted(entries):
            with open(os.path.join(repo, "src/main/java", name), "rb") as handle:
                if archive.read(name) != handle.read():
                    fail(f"sources jar entry {name!r} differs from src/main/java")


def check_javadoc(path):
    names, _ = zip_entries(path, PLAIN_MANIFEST)
    pages = 0
    for name in names:
        if name.endswith("-search-index.zip") and name in JAVADOC_NAMES:
            # A javadoc search index: one JSON file named after the zip, nothing else.
            with zipfile.ZipFile(path) as archive:
                nested = archive.read(name)
            wanted = name[:-len(".zip")] + ".json"
            if raw_zip_names(nested, name) != [wanted.encode("ascii")]:
                fail(f"javadoc entry {name!r} must hold only {wanted}")
            try:
                index = json.loads(zipfile.ZipFile(io.BytesIO(nested)).read(wanted).decode("utf-8"))
            except (zipfile.BadZipFile, ValueError, UnicodeDecodeError):
                fail(f"javadoc entry {name!r} does not hold a JSON index")
            if not isinstance(index, list) or not all(isinstance(item, dict) for item in index):
                fail(f"javadoc entry {name!r} is not a javadoc search index")
            continue
        if name in JAVADOC_NAMES:
            continue
        if os.path.splitext(name)[1] not in JAVADOC_EXTENSIONS:
            fail(f"javadoc entry {name!r} is not a documentation file")
        pages += name.endswith(".html")
    if pages == 0:
        fail("the javadoc jar holds no page")


# Expected shape of the nested POM elements Gradle writes from build.gradle.
POM_NESTED = {
    "licenses": ("license", {"name", "url"}),
    "developers": ("developer", {"id", "name"}),
}
POM_SCM = {"connection", "developerConnection", "url"}


def bad_characters(value):
    return any(unicodedata.category(char) in ("Cf", "Co", "Cs", "Cn") or (unicodedata.category(char) == "Cc" and char not in "\t\n\r")
               for char in value)


def strictly_equal(left, right):
    """== that also compares JSON types (1234.0 is not 1234, True is not 1)."""
    if type(left) is not type(right):
        return False
    if isinstance(left, dict):
        return left.keys() == right.keys() and all(strictly_equal(left[k], right[k]) for k in left)
    if isinstance(left, list):
        return len(left) == len(right) and all(strictly_equal(a, b) for a, b in zip(left, right))
    return left == right


def local(tag):
    return tag[len(POM_NS):] if isinstance(tag, str) and tag.startswith(POM_NS) else None


def text(element, tag):
    child = element.find(POM_NS + tag)
    # Only ASCII whitespace, as Maven's trim(): a non-breaking space must not vanish here.
    return None if child is None else (child.text or "").strip(" \t\r\n")


def read_properties(path):
    values = {}
    with open(path, encoding="utf-8") as handle:
        for line in handle:
            line = line.rstrip("\r\n")
            if line and not line.startswith("#") and "=" in line:
                key, value = line.split("=", 1)
                values[key.strip(" \t")] = value.strip(" \t")
    return values


def check_pom_values(root, properties):
    """The free-text POM values are the project_* values of gradle.properties, exactly."""
    def want(key):
        if key not in properties:
            fail(f"gradle.properties has no {key}")
        return properties[key]
    def one(parent, tag, item):
        container = root.find(POM_NS + parent)
        entries = [] if container is None else list(container)
        if len(entries) != 1:
            fail(f"the POM must have exactly one {item}")
        return entries[0]
    expected = {
        (None, "name"): want("project_name"),
        (None, "description"): want("project_description"),
        (None, "url"): want("project_url"),
        ("license", "name"): want("project_license_slug"),
        ("license", "url"): want("project_license_url"),
        ("developer", "id"): want("project_developer"),
        ("developer", "name"): want("project_developer"),
        ("scm", "connection"): want("project_scm"),
        ("scm", "developerConnection"): want("project_scm"),
        ("scm", "url"): want("project_url"),
    }
    parents = {None: root, "license": one("licenses", "license", "license"),
               "developer": one("developers", "developer", "developer"), "scm": root.find(POM_NS + "scm")}
    for (parent, tag), value in expected.items():
        element = parents[parent]
        if element is None or text(element, tag) != value:
            fail(f"POM {parent + '/' if parent else ''}{tag} is not the gradle.properties value")


def check_pom(path, version, dependencies, properties):
    with open(path, "rb") as handle:
        raw = handle.read()
    try:
        decoded = raw.decode("utf-8")
    except UnicodeDecodeError:
        fail("the POM is not UTF-8")
    if not decoded.startswith('<?xml version="1.0" encoding="UTF-8"?>\n'):
        fail("the POM does not start with the UTF-8 XML declaration")
    body = decoded[len('<?xml version="1.0" encoding="UTF-8"?>\n'):]
    if not body.isascii() or bad_characters(body):
        fail("the POM holds non-ASCII, control or format characters")
    if re.search(r"<!(?!--)", body) or "<?" in body:
        fail("the POM declares a DTD, an entity, a CDATA section or a processing instruction")
    try:
        root = ET.fromstring(raw)
    except ET.ParseError:
        fail("the POM is not well-formed XML")
    if root.tag != POM_NS + "project":
        fail("the POM root is not a Maven 4.0.0 project")
    if set(root.attrib) - {XSI_SCHEMA_LOCATION}:
        fail("unexpected attribute on the POM root")
    for element in root.iter():
        if element is not root and element.attrib:
            fail(f"unexpected attribute on POM element {element.tag!r}")
        for value in (element.text, element.tail):
            if value and bad_characters(value):
                fail("the POM holds control or format characters (character reference)")
        if len(element) and element.text and element.text.strip():
            fail(f"POM element {element.tag!r} holds text next to its children")
        if element.tail and element.tail.strip():
            fail(f"text after POM element {element.tag!r}")
        if not isinstance(element.tag, str) or local(element.tag) is None:
            fail(f"POM element outside the Maven namespace: {element.tag!r}")
    for child in root:
        name = local(child.tag)
        if name not in POM_TOP_LEVEL:
            fail(f"unexpected POM element {child.tag!r}")
        if name in POM_LEAVES and len(child):
            fail(f"POM element {name} has children")
        if name in POM_NESTED:
            item, fields = POM_NESTED[name]
            for entry in child:
                if local(entry.tag) != item or sorted(local(f.tag) for f in entry) != sorted(fields) or any(len(f) for f in entry):
                    fail(f"unexpected structure in POM {name}")
        if name == "scm" and (sorted(local(f.tag) for f in child) != sorted(POM_SCM) or any(len(f) for f in child)):
            fail("unexpected structure in POM scm")
    if [local(child.tag) for child in root].count("dependencies") > 1:
        fail("the POM has two dependencies sections")
    if (text(root, "groupId"), text(root, "artifactId"), text(root, "version")) != (GROUP, ARTIFACT, version):
        fail("the POM coordinates are not com.checkout:checkout-sdk-java at this version")
    check_pom_values(root, properties)
    if text(root, "packaging") not in (None, "jar"):
        fail("the POM packaging is not jar")
    found = set()
    container = root.find(POM_NS + "dependencies")
    for dependency in ([] if container is None else list(container)):
        if dependency.tag != POM_NS + "dependency":
            fail(f"unexpected element {dependency.tag!r} in POM dependencies")
        children = [local(child.tag) for child in dependency]
        if sorted(children) != sorted(POM_DEPENDENCY_CHILDREN) or any(len(child) for child in dependency):
            fail(f"POM dependency with elements {sorted(str(c) for c in children)}")
        if text(dependency, "scope") != "runtime":
            fail("a POM dependency is not scope runtime")
        key = (text(dependency, "groupId"), text(dependency, "artifactId"), text(dependency, "version"))
        if key in found:
            fail(f"duplicate POM dependency {key}")
        found.add(key)
    if found != dependencies:
        fail(f"POM dependencies differ from build.gradle (extra {sorted(found - dependencies)}, missing {sorted(dependencies - found)})")


def sha256(path):
    digest = hashlib.sha256()
    with open(path, "rb") as handle:
        for block in iter(lambda: handle.read(1 << 20), b""):
            digest.update(block)
    return digest.hexdigest()


def no_duplicate_keys(pairs):
    keys = [key for key, _ in pairs]
    if len(keys) != len(set(keys)):
        fail("the module file has duplicate JSON keys")
    return dict(pairs)


def check_module(path, directory, version, dependencies):
    try:
        with open(path, encoding="utf-8") as handle:
            module = json.load(handle, object_pairs_hook=no_duplicate_keys)
    except (ValueError, UnicodeDecodeError):
        fail("the module file is not JSON")
    if not isinstance(module, dict) or set(module) != {"formatVersion", "component", "createdBy", "variants"}:
        fail("unexpected top-level keys in the module file")
    if module["formatVersion"] != "1.1":
        fail("unexpected module format version")
    component = module["component"]
    if not isinstance(component, dict) or set(component) != {"group", "module", "version", "attributes"}:
        fail("the module component has unexpected keys (a redirect elsewhere?)")
    if (component["group"], component["module"], component["version"]) != (GROUP, ARTIFACT, version):
        fail("the module coordinates are not com.checkout:checkout-sdk-java at this version")
    if not strictly_equal(component["attributes"], COMPONENT_ATTRIBUTES):
        fail("unexpected module component attributes")
    variants = module["variants"]
    if not isinstance(variants, list) or not all(isinstance(v, dict) for v in variants) \
            or sorted(str(v.get("name")) for v in variants) != sorted(VARIANT_ATTRIBUTES):
        fail(f"the module variants must be exactly {sorted(VARIANT_ATTRIBUTES)}")
    wanted = f"{ARTIFACT}-{version}.jar"
    real = os.path.join(directory, wanted)
    for variant in variants:
        name = variant["name"]
        extra = set(variant) - {"name", "attributes", "files", "dependencies"}
        if extra or "files" not in variant or "attributes" not in variant:
            fail(f"module variant {name} has unexpected or missing keys")
        if not strictly_equal(variant["attributes"], VARIANT_ATTRIBUTES[name]):
            fail(f"module variant {name} has unexpected attributes")
        files = variant["files"]
        if not isinstance(files, list) or len(files) != 1 or not isinstance(files[0], dict):
            fail(f"module variant {name} must list exactly one file")
        entry = files[0]
        if set(entry) != {"name", "url", "size", "sha512", "sha256", "sha1", "md5"}:
            fail(f"module variant {name} file entry has unexpected keys")
        if entry["name"] != wanted or entry["url"] != wanted:
            fail(f"module variant {name} does not point at {wanted}")
        if type(entry["size"]) is not int or entry["size"] != os.path.getsize(real) or entry["sha256"] != sha256(real):
            fail(f"module variant {name} does not match {wanted} (size or sha256)")
        found = set()
        listed = variant.get("dependencies", [])
        if not isinstance(listed, list):
            fail(f"module variant {name} dependencies are not a list")
        for dependency in listed:
            if not isinstance(dependency, dict) or set(dependency) != {"group", "module", "version"} \
                    or not isinstance(dependency["version"], dict) or set(dependency["version"]) != {"requires"} \
                    or not all(isinstance(value, str) for value in
                               (dependency["group"], dependency["module"], dependency["version"]["requires"])):
                fail(f"module variant {name} has a dependency of an unexpected shape")
            key = (dependency["group"], dependency["module"], dependency["version"]["requires"])
            if key in found:
                fail(f"duplicate module dependency {key}")
            found.add(key)
        expected = dependencies if name == "runtimeElements" else set()
        if found != expected:
            fail(f"module variant {name} dependencies differ from build.gradle (extra {sorted(found - expected)}, missing {sorted(expected - found)})")


def main(argv):
    parser = argparse.ArgumentParser()
    parser.add_argument("--version", required=True)
    parser.add_argument("--directory", required=True)
    parser.add_argument("--repo", default=".")
    args = parser.parse_args(argv)
    if not re.fullmatch(r"[0-9A-Za-z.-]+", args.version) or ".." in args.version:
        fail("invalid version")
    files = expected_files(args.version)
    check_file_set(args.directory, files)
    dependencies = read_dependencies(os.path.join(args.repo, "build.gradle"))
    sources = tracked_sources(args.repo)
    path = {kind: os.path.join(args.directory, name) for kind, name in files.items()}
    check_jar(path["jar"], args.version, sources)
    check_sources(path["sources"], args.repo, sources)
    check_javadoc(path["javadoc"])
    check_pom(path["pom"], args.version, dependencies, read_properties(os.path.join(args.repo, "gradle.properties")))
    check_module(path["module"], args.directory, args.version, dependencies)
    print(f"Artifacts of {ARTIFACT} {args.version} verified ({len(dependencies)} runtime dependencies)")


if __name__ == "__main__":
    try:
        main(sys.argv[1:])
    except VerificationError as error:
        print(f"::error::{error}")
        sys.exit(1)
    except Exception as error:  # any malformed input not caught above still fails closed
        print(f"::error::unexpected artifact structure ({type(error).__name__})")
        sys.exit(1)
