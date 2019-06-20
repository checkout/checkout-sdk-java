package com.checkout;

import com.checkout.common.CheckoutUtils;

public class CheckoutConfiguration {
    public static final String PRODUCTION_URI = "https://api.checkout.com/";
    public static final String SANDBOX_URI = "https://api.sandbox.checkout.com/";

    private final String secretKey;
    private final String uri;
    private String publicKey;

    public CheckoutConfiguration(String secretKey, boolean useSandbox) {
        this(secretKey, useSandbox ? SANDBOX_URI : PRODUCTION_URI);
    }

    public CheckoutConfiguration(String secretKey, String uri) {
        if (CheckoutUtils.isNullOrEmpty(secretKey)) {
            throw new IllegalArgumentException("Your API secret key is required");
        }
        if (CheckoutUtils.isNullOrEmpty(uri)) {
            throw new IllegalArgumentException("The API URI is required");
        }

        this.secretKey = secretKey;
        this.uri = uri;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getUri() {
        return uri;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}