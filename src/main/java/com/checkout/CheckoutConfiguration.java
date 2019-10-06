package com.checkout;

import com.checkout.common.CheckoutUtils;
import lombok.Data;

@Data
public class CheckoutConfiguration {
    public static final String PRODUCTION_URI = "https://api.checkout.com/";
    public static final String SANDBOX_URI = "https://api.sandbox.checkout.com/";

    private final String secretKey;
    private final String uri;

    private int connectionTimeout = 10000; // default to 10 seconds
    private String publicKey = null; // no public key required for production

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

    public CheckoutConfiguration(String secretKey, boolean useSandbox, String publicKey) {
        this(secretKey, useSandbox ? SANDBOX_URI : PRODUCTION_URI, publicKey);
    }

    public CheckoutConfiguration(String secretKey, String uri, String publicKey) {
        this(secretKey, uri);
        this.publicKey = publicKey;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}