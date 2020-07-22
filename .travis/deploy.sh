#!/usr/bin/env sh

if [ "$TRAVIS_BRANCH" = "master" ] && [ "$TRAVIS_PULL_REQUEST" = "false" ] && [ "$MANUAL_RELEASE_TRIGGERED" = "true" ];
then
	echo "Promote repository"
	./gradlew publishToSonatype closeAndReleaseRepository
fi
