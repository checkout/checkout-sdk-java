#!/usr/bin/env bash
# The only way CI runs Gradle: bash .github/scripts/gradle.sh <task or allowed option>...
#
# 1. Arguments: tasks and a short allowlist of options only (no system properties, project
#    directory, init script or verification flag can reach Gradle from a workflow).
# 2. Early warning (static): gradle/verification-metadata.xml verifies metadata and trusts no
#    artifact or key without a checksum, the wrapper settings are exactly the official download
#    into the Gradle user home with its sha256, gradle.properties holds project values only, and no
#    Gradle user home init script, properties file, environment variable or build script
#    mentions dependency verification.
# 3. Canary (runtime, the check that matters): Gradle starts from the validated wrapper jar, not
#    the committed gradlew script. A first run, before settings are evaluated, resolves an
#    artifact gradle/verification-metadata.xml does not list and stops; dependency verification
#    must refuse it. What that run left is removed, then the build runs with exactly the same
#    command line (the same init script path, now empty).
# 4. The working tree must be clean before, between and after the runs: no build logic may
#    rewrite tracked files (gradle.properties, the metadata, this script).
set -euo pipefail

fail() { echo "::error::$1"; exit 1; }
cd "$(git rev-parse --show-toplevel)"

# ---- 1. arguments
previous=""
for arg in "$@"; do
  if [ "$previous" = "-x" ]; then
    [[ "$arg" =~ ^[a-z][A-Za-z0-9]*$ ]] || fail "gradle.sh: -x needs a task name"
  else
    case "$arg" in
      --no-daemon | --no-build-cache | --fail-fast | --no-scan | -q | --quiet | --stacktrace | -x) ;;
      *) [[ "$arg" =~ ^[a-z][A-Za-z0-9]*$ ]] || fail "gradle.sh does not pass $arg to Gradle" ;;
    esac
  fi
  previous="$arg"
done
[ "$previous" != "-x" ] || fail "gradle.sh: -x needs a task name"

# ---- 2. early warning
metadata=gradle/verification-metadata.xml
[ -f "$metadata" ] && [ ! -L "$metadata" ] || fail "$metadata is missing"
grep -qx '      <verify-metadata>true</verify-metadata>' "$metadata" || fail "$metadata must keep <verify-metadata>true</verify-metadata>"
if grep -qiE 'trusted-artifacts|<([a-z0-9_-]+:)?trust[[:space:]>/]|ignored-key|trusted-key|<!DOCTYPE|<!ENTITY' "$metadata"; then
  fail "$metadata must not trust artifacts or keys without a checksum"
fi
# The wrapper must download the official distribution into the Gradle user home and check its
# sha256: no other base, path or URL (a distribution committed to the repository and marked as
# already unpacked would run instead of Gradle, both bases set to PROJECT for example).
wrapper=gradle/wrapper/gradle-wrapper.properties
settings="$(grep -vE '^[[:space:]]*(#|$)' "$wrapper" | LC_ALL=C sort)"
expected_re='^distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionSha256Sum=[0-9a-f]{64}
distributionUrl=https\\://services\.gradle\.org/distributions/gradle-[0-9]+(\.[0-9]+)+-(all|bin)\.zip
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists$'
[[ "$settings" =~ $expected_re ]] || fail "$wrapper must hold exactly the official distribution settings"
dist_sha="$(sed -n 's/^distributionSha256Sum=//p' "$wrapper")"
dist_name="$(sed -n 's#^distributionUrl=.*/\(gradle-[0-9.]*-[a-z]*\)\.zip$#\1#p' "$wrapper")"
gradle_home="${GRADLE_USER_HOME:-$HOME/.gradle}"
# A distribution already unpacked in the Gradle user home is trusted by the wrapper without a
# check: it must sit next to its zip, with the pinned sha256.
for dist in "$gradle_home/wrapper/dists/$dist_name"/*/; do
  [ -d "$dist" ] || continue
  zip="$dist$dist_name.zip"
  [ -f "$zip" ] || fail "$dist holds a distribution without its zip"
  actual="$( (sha256sum "$zip" 2>/dev/null || shasum -a 256 "$zip") | cut -d' ' -f1)"
  [ "$actual" = "$dist_sha" ] || fail "$zip does not match distributionSha256Sum"
done
if [ -d "$gradle_home/init.d" ] && [ -n "$(ls -A "$gradle_home/init.d")" ]; then
  fail "$gradle_home/init.d holds init scripts"
fi
for script in init.gradle init.gradle.kts; do
  [ ! -e "$gradle_home/$script" ] || fail "$gradle_home/$script exists"
done
# Tracked gradle.properties: project values only. Every line is a plain lowercase key, no dot,
# no backslash (no org.gradle.* setting such as jvmargs or java.home, no systemProp.*, no escape
# or continuation line).
pattern='verification|\\u|disableDependencyVerification'
while IFS= read -r -d '' file; do
  if grep -vE '^[[:space:]]*(#.*)?$' "$file" | grep -qvE '^[a-z][a-z0-9_]*=[^\\]*$'; then
    fail "$file may only hold plain project values (key=value, lowercase key without dots)"
  fi
done < <(git ls-files -z -- '*gradle.properties')
if [ -f "$gradle_home/gradle.properties" ] && grep -qiE "$pattern|org\.gradle\.|systemProp" "$gradle_home/gradle.properties"; then
  fail "$gradle_home/gradle.properties holds Gradle settings"
fi
if env | grep -qiE 'verification'; then
  fail "an environment variable mentions dependency verification"
fi
if env | grep -qiE '^(GRADLE_OPTS|JAVA_OPTS|JAVA_TOOL_OPTIONS|JDK_JAVA_OPTIONS|_JAVA_OPTIONS)=.*(-javaagent|-agentpath|-agentlib|-Xbootclasspath|java\.home)'; then
  fail "a JVM options variable loads an agent or changes the boot class path"
fi
while IFS= read -r -d '' file; do
  if grep -qiE 'DependencyVerification|dependency[._-]?verification' "$file"; then
    fail "$file changes dependency verification"
  fi
done < <(git ls-files -z -- '*.gradle' '*.gradle.kts' 'gradlew' 'gradlew.bat')
[ -z "$(git status --porcelain)" ] || fail "the working tree is not clean before the build"

# ---- 3. canary, then the build, with the same command line
# The committed gradlew script is not used: Gradle starts from the wrapper jar, which every job
# validates first (gradle/actions/wrapper-validation), so no repository script sits between this
# check and Gradle. Gradle keeps a verification failure for the rest of an invocation, so the
# probe has its own run. Both runs have exactly the same command line (same init script path,
# same arguments); only the init script's content differs. The probe fires before settings are
# evaluated, so no settings plugin, build script or plugin is resolved or run before it.
work="$(mktemp -d)"
trap 'rm -rf "$work"' EXIT
init="$work/init.gradle"
java_cmd="java"
if [ -n "${JAVA_HOME:-}" ]; then java_cmd="$JAVA_HOME/bin/java"; fi
gradle() {
  "$java_cmd" -Xmx64m -Xms64m -Dorg.gradle.appname=gradlew \
    -classpath gradle/wrapper/gradle-wrapper.jar org.gradle.wrapper.GradleWrapperMain \
    --init-script "$init" "$@"
}

cat > "$init" <<'GRADLE'
beforeSettings { settings ->
    settings.buildscript.repositories.mavenCentral()
    def probe = settings.buildscript.configurations.detachedConfiguration(
        settings.buildscript.dependencies.create('junit:junit:4.13.2'))
    def refused = false
    try {
        probe.resolve()
    } catch (Exception e) {
        def text = ''
        for (def cause = e; cause != null; cause = cause.cause) { text += String.valueOf(cause.message) + '\n' }
        if (!text.contains('Dependency verification failed')) {
            throw new GradleException('VERIFICATION-PROBE-ERROR: the probe failed for another reason', e)
        }
        refused = true
    }
    throw new GradleException(refused ? 'VERIFICATION-PROBE-REFUSED' : 'VERIFICATION-PROBE-RESOLVED')
}
GRADLE

# NUL-separated records: paths with spaces or special characters are handled exactly. Ignored
# and untracked directories are listed once, not file by file, so the comparison stays small
# (the probe fires before any build code, so all it can create is Gradle's own .gradle/).
snapshot() { git status --porcelain=v1 -z --ignored=traditional --untracked-files=normal | od -An -v -tx1 | tr -d ' \n'; }
state_before="$(snapshot)"
git status --porcelain=v1 -z --ignored=traditional --untracked-files=normal > "$work/before.z"
out="$(gradle "$@" 2>&1)" && fail "the dependency verification probe did not run"
if grep -q 'VERIFICATION-PROBE-RESOLVED' <<< "$out"; then
  fail "dependency verification is not enforced: an unlisted artifact resolved"
fi
if ! grep -q 'VERIFICATION-PROBE-REFUSED' <<< "$out"; then
  echo "$out" | tail -20
  fail "the dependency verification probe failed for another reason"
fi
echo "Dependency verification is enforced (unlisted artifact refused)"

# Whatever the probe run left (Gradle's project cache, ignored files) goes, so the build run
# starts from the same state as the probe run did.
while IFS= read -r -d '' record; do
  code="${record:0:2}"
  path="${record:3}"
  case "$code" in "??" | "!!") ;; *) fail "the probe run changed a tracked file: $path" ;; esac
  case "$path" in /* | ../* | */../* | "") fail "unexpected path after the probe run: $path" ;; esac
  known=false
  while IFS= read -r -d '' earlier; do
    if [ "$earlier" = "$record" ]; then known=true; break; fi
  done < "$work/before.z"
  if [ "$known" = false ]; then rm -rf -- "./$path"; fi
done < <(git status --porcelain=v1 -z --ignored=traditional --untracked-files=normal)
[ "$(snapshot)" = "$state_before" ] || fail "the probe run changed the working tree"

: > "$init"
status=0
gradle "$@" || status=$?

# ---- 4. nothing tracked changed during the build
if [ -n "$(git status --porcelain)" ]; then
  git status --porcelain | head -20
  fail "the build changed the working tree"
fi
exit "$status"
