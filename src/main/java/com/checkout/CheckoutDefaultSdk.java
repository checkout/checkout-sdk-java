package com.checkout;

public final class CheckoutDefaultSdk {

    public StaticKeysCheckoutSdkBuilder staticKeys() {
        return new StaticKeysCheckoutSdkBuilder();
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
            return new DefaultStaticKeysSdkCredentials(secretKey, publicKey);
        }

        @Override
        public CheckoutApiImpl build() {
            final CheckoutConfiguration configuration = getCheckoutConfiguration();
            return new CheckoutApiImpl(configuration);
        }

    }

}
