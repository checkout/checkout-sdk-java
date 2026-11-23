#!/usr/bin/env bash
# The only way CI runs Gradle: bash .github/scripts/gradle.sh <task or allowed option>...
#
# 1. Arguments: task names and a short list of options. Build scans are never published
#    (--no-scan on every run).
# 2. Early warning (static): gradle/verification-metadata.xml verifies metadata and trusts no
#    artifact or key without a checksum, the wrapper settings are exactly the official download
#    into the Gradle user home with its sha256, gradle.properties holds project values only, and
#    no Gradle user home init script, properties file, environment variable or build script
#    changes dependency verification.
# 3. Probe run: Gradle starts from the wrapper jar. Before settings are evaluated, an init script
#    resolves a probe artifact from a temporary local repository (a coordinate no project uses,
#    so it is never in gradle/verification-metadata.xml); dependency verification must refuse it.
#    The files that run created are removed, then the build runs with the same command line.
# 4. The working tree is clean before, between and after the runs.
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
# The wrapper settings are exactly the official distribution download into the Gradle user home,
# with its sha256.
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
# A distribution already unpacked in the Gradle user home sits next to its zip, which has the
# pinned sha256.
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
# Tracked gradle.properties files hold project values only: plain lowercase keys without dots,
# values without backslashes.
pattern='verification|\\u|disableDependencyVerification'
while IFS= read -r -d '' file; do
  if grep -vE '^[[:space:]]*(#.*)?$' "$file" | grep -qvE '^[a-z][a-z0-9_]*=[^\\]*$'; then
    fail "$file may only hold plain project values (key=value, lowercase key without dots)"
  fi
done < <(git ls-files -z -- '*gradle.properties')
if [ -f "$gradle_home/gradle.properties" ] && grep -qiE "$pattern|org\.gradle\.|systemProp" "$gradle_home/gradle.properties"; then
  fail "$gradle_home/gradle.properties holds Gradle settings"
fi
# The environment variables the JVM or Gradle read (GRADLE_OPTS and JAVA_OPTS are not used, see
# below): no dependency verification setting, agent, boot class path or other Java home.
for variable in JAVA_TOOL_OPTIONS JDK_JAVA_OPTIONS _JAVA_OPTIONS JAVA_OPTIONS; do
  if grep -qiE 'verification|-javaagent|-agentpath|-agentlib|-Xbootclasspath|java\.home' <<< "${!variable:-}"; then
    fail "$variable sets dependency verification, an agent, the boot class path or the Java home"
  fi
done
if env | grep -iE '^ORG_GRADLE_' | grep -qiE 'verification'; then
  fail "an ORG_GRADLE_* variable sets dependency verification"
fi
while IFS= read -r -d '' file; do
  if grep -qiE 'DependencyVerification|dependency[._-]?verification' "$file"; then
    fail "$file changes dependency verification"
  fi
done < <(git ls-files -z -- '*.gradle' '*.gradle.kts' 'gradlew' 'gradlew.bat')
[ -z "$(git status --porcelain)" ] || fail "the working tree is not clean before the build"

# ---- 3. probe run, then the build, with the same command line
# Gradle starts from the wrapper jar, which every job validates first
# (gradle/actions/wrapper-validation). A verification failure lasts for the whole invocation, so
# the probe has its own run; both runs use the same command line and the same init script path,
# only the init script's content differs.
work="$(mktemp -d)"
trap 'rm -rf "$work"' EXIT
init="$work/init.gradle"
java_cmd="java"
if [ -n "${JAVA_HOME:-}" ]; then java_cmd="$JAVA_HOME/bin/java"; fi
# The wrapper jar is started directly, so the client JVM options gradlew would read are not used.
for variable in GRADLE_OPTS JAVA_OPTS; do
  if [ -n "${!variable:-}" ]; then
    echo "::warning::$variable is set but not used: gradle.sh starts the wrapper jar directly"
  fi
done
gradle() {
  "$java_cmd" -Xmx64m -Xms64m -Dorg.gradle.appname=gradlew \
    -classpath gradle/wrapper/gradle-wrapper.jar org.gradle.wrapper.GradleWrapperMain \
    --init-script "$init" --no-scan "$@"
}

# The probe artifact: a coordinate no project uses, served from a temporary local repository.
probe_dir="$work/probe-repository/local/gradle-sh/verification-probe/1"
mkdir -p "$probe_dir"
printf '%s\n' '<project xmlns="http://maven.apache.org/POM/4.0.0"><modelVersion>4.0.0</modelVersion><groupId>local.gradle-sh</groupId><artifactId>verification-probe</artifactId><version>1</version></project>' \
  > "$probe_dir/verification-probe-1.pom"
printf 'probe\n' > "$probe_dir/verification-probe-1.jar"

cat > "$init" <<GRADLE
beforeSettings { settings ->
    settings.buildscript.repositories.maven { url = new File('$work/probe-repository').toURI() }
    def probe = settings.buildscript.configurations.detachedConfiguration(
        settings.buildscript.dependencies.create('local.gradle-sh:verification-probe:1'))
    def refused = false
    try {
        probe.resolve()
    } catch (Exception e) {
        def text = ''
        for (def cause = e; cause != null; cause = cause.cause) { text += String.valueOf(cause.message) + '\\n' }
        if (!text.contains('Dependency verification failed')) {
            throw new GradleException('VERIFICATION-PROBE-ERROR: the probe failed for another reason', e)
        }
        refused = true
    }
    throw new GradleException(refused ? 'VERIFICATION-PROBE-REFUSED' : 'VERIFICATION-PROBE-RESOLVED')
}
GRADLE

# NUL-separated records, so paths with spaces or special characters are compared exactly.
# Ignored and untracked directories are listed once, not file by file.
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

# Files the probe run added (Gradle's project cache) are removed, so the build run starts from the
# same state.
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
