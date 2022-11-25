package com.checkout;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public final class CheckoutSdkBuilder {

    public CheckoutPreviousSdkBuilder previous() {
        return new CheckoutPreviousSdkBuilder();
    }

    public CheckoutStaticKeysSdkBuilder staticKeys() {
        return new CheckoutStaticKeysSdkBuilder();
    }

    public CheckoutOAuthSdkBuilder oAuth() {
        return new CheckoutOAuthSdkBuilder();
    }

    public static class CheckoutOAuthSdkBuilder extends AbstractCheckoutSdkBuilder<CheckoutApiImpl> {

        private String clientId;
        private String clientSecret;
        private URI authorizationUri;
        private final Set<OAuthScope> scopes = new HashSet<>();

        public CheckoutOAuthSdkBuilder clientCredentials(final URI authorizationUri,
                                                         final String clientId,
                                                         final String clientSecret) {
            this.authorizationUri = authorizationUri;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            return this;
        }

        public CheckoutOAuthSdkBuilder clientCredentials(final String clientId,
                                                         final String clientSecret) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            return this;
        }

        public CheckoutOAuthSdkBuilder scopes(final OAuthScope... scopes) {
            this.scopes.addAll(asList(scopes));
            return this;
        }

        @Override
        protected SdkCredentials getSdkCredentials() {
            if (this.authorizationUri == null) {
                final IEnvironment environment = getEnvironment();
                if (environment == null) {
                    throw new CheckoutArgumentException("Invalid configuration. Please specify an Environment or a specific OAuth authorizationURI.");
                }
                this.authorizationUri = environment.getOAuthAuthorizationApi();
            }
            final OAuthSdkCredentials credentials = new OAuthSdkCredentials(httpClientBuilder, authorizationUri, clientId, clientSecret, scopes);
            credentials.initOAuthAccess();
            return credentials;
        }

        @Override
        public CheckoutApiImpl build() {
            return new CheckoutApiImpl(getCheckoutConfiguration());
        }

    }

    public static class CheckoutStaticKeysSdkBuilder extends AbstractCheckoutSdkBuilder<CheckoutApiImpl> {

        private String publicKey;
        private String secretKey;

        public CheckoutStaticKeysSdkBuilder publicKey(final String publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public CheckoutStaticKeysSdkBuilder secretKey(final String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        @Override
        protected SdkCredentials getSdkCredentials() {
            return new StaticKeysSdkCredentials(secretKey, publicKey);
        }

        @Override
        public CheckoutApiImpl build() {
            return new CheckoutApiImpl(getCheckoutConfiguration());
        }

    }

}
