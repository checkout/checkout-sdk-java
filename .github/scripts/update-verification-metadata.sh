#!/usr/bin/env bash
# Regenerates gradle/verification-metadata.xml (sha256 of every dependency and plugin artifact
# Gradle resolves). Run it after any change to a dependency or plugin version (Dependabot pull
# requests included: Dependabot does not update this file) and review the diff before committing:
# CI fails when an artifact is missing from the file or its checksum differs. No trust rule is
# added: IDEs that download -sources or -javadoc jars need them listed too (run the IDE sync
# once with --write-verification-metadata sha256, or resolve them here).
#
# It uses a fresh Gradle home, so every artifact is downloaded again from Maven Central or the
# Gradle Plugin Portal instead of a local copy, and
# it resolves every configuration (plugins, compile, test, jacoco, javadoc, the release staging)
# so nothing CI uses is left out.
# Entries already in the file are kept; delete the file first to drop stale ones.
set -euo pipefail

root="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
cd "$root"
work="$(mktemp -d)"
trap 'rm -rf "$work"' EXIT

cat > "$work/resolve-all.gradle" <<'GRADLE'
allprojects {
    tasks.register('resolveAllConfigurations') {
        doLast {
            configurations.findAll { it.canBeResolved }.each { it.resolve() }
            buildscript.configurations.findAll { it.canBeResolved }.each { it.resolve() }
        }
    }
}
GRADLE

GRADLE_USER_HOME="$work/gradle-home" ./gradlew --no-daemon --quiet --no-scan \
  --init-script "$work/resolve-all.gradle" \
  --write-verification-metadata sha256 \
  resolveAllConfigurations compileJava compileTestJava javadoc jar sourcesJar javadocJar \
  jacocoTestReport publishMavenPublicationToStagingRepository -x test

echo "gradle/verification-metadata.xml updated; review 'git diff gradle/verification-metadata.xml'"
