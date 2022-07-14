package com.checkout;

import com.checkout.previous.CheckoutApiImpl;

public final class CheckoutPreviousSdkBuilder {

    public CheckoutStaticKeysSdkBuilder staticKeys() {
        return new CheckoutStaticKeysSdkBuilder();
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
            return new PreviousStaticKeysSdkCredentials(secretKey, publicKey);
        }

        @Override
        public CheckoutApiImpl build() {
            return new CheckoutApiImpl(getCheckoutConfiguration());
        }

    }

}
