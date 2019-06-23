if [ "$TRAVIS_BRANCH" = "master" ] && [ "$TRAVIS_PULL_REQUEST" = "false" ];
then
	openssl aes-256-cbc -K $encrypted_175f9c159b8a_key -iv $encrypted_175f9c159b8a_iv -in $ENCRYPTED_GPG_KEY_LOCATION -out $GPG_KEY_LOCATION -d
fi