package com.checkout;

import com.checkout.four.CheckoutApiImpl;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public final class CheckoutFourSdk {

    public StaticKeysCheckoutSdkBuilder staticKeys() {
        return new StaticKeysCheckoutSdkBuilder();
    }

    public FourOAuthCheckoutSdkBuilder oAuth() {
        return new FourOAuthCheckoutSdkBuilder();
    }

    public static class FourOAuthCheckoutSdkBuilder extends AbstractCheckoutSdkBuilder<CheckoutApiImpl> {

        private String clientId;
        private String clientSecret;
        private URI authorizationUri;
        private final Set<FourOAuthScope> scopes = new HashSet<>();

        public FourOAuthCheckoutSdkBuilder clientCredentials(final URI authorizationUri,
                                                             final String clientId,
                                                             final String clientSecret) {
            this.authorizationUri = authorizationUri;
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            return this;
        }

        public FourOAuthCheckoutSdkBuilder clientCredentials(final String clientId,
                                                             final String clientSecret) {
            this.clientId = clientId;
            this.clientSecret = clientSecret;
            return this;
        }

        public FourOAuthCheckoutSdkBuilder scopes(final FourOAuthScope... scopes) {
            this.scopes.addAll(asList(scopes));
            return this;
        }

        @Override
        protected SdkCredentials getSdkCredentials() {
            if (this.authorizationUri == null) {
                final Environment environment = getEnvironment();
                if (environment == null) {
                    throw new CheckoutArgumentException("Invalid configuration. Please specify an Environment or a specific OAuth authorizationURI.");
                }
                this.authorizationUri = environment.getOAuthAuthorizeUri();
            }
            final FourOAuthSdkCredentials credentials = new FourOAuthSdkCredentials(authorizationUri, clientId, clientSecret, scopes);
            credentials.initOAuthAccess();
            return credentials;
        }

        @Override
        public CheckoutApiImpl build() {
            final CheckoutConfiguration configuration = getCheckoutConfiguration();
            final ApiClient client = new ApiClientImpl(configuration);
            return new CheckoutApiImpl(client, configuration);
        }

    }

    public static class StaticKeysCheckoutSdkBuilder extends AbstractCheckoutSdkBuilder<CheckoutApiImpl> {

        private String publicKey;
        private String secretKey;

        public StaticKeysCheckoutSdkBuilder publicKey(final String publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        public StaticKeysCheckoutSdkBuilder secretKey(final String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        @Override
        protected SdkCredentials getSdkCredentials() {
            return new FourStaticKeysSdkCredentials(secretKey, publicKey);
        }

        @Override
        public CheckoutApiImpl build() {
            final CheckoutConfiguration configuration = getCheckoutConfiguration();
            final ApiClient client = new ApiClientImpl(configuration);
            return new CheckoutApiImpl(client, configuration);
        }

    }

}
