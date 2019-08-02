package com.checkout;

public class PublicKeyCredentials implements ApiCredentials {
    private final CheckoutConfiguration configuration;

    public PublicKeyCredentials(CheckoutConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("configuration must not be null");
        }
        this.configuration = configuration;
    }

    @Override
    public String getAuthorizationHeader() {
        return configuration.getPublicKey();
    }
}