package com.checkout;

public class SecretKeyCredentials implements ApiCredentials {
    private final CheckoutConfiguration configuration;

    public SecretKeyCredentials(CheckoutConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("configuration must not be null");
        }
        this.configuration = configuration;
    }

    @Override
    public String getAuthorizationHeader() {
        return configuration.getSecretKey();
    }
}