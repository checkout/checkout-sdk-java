package com.checkout;

import com.checkout.common.CheckoutUtils;

import java.net.HttpURLConnection;

public class SecretKeyCredentials implements ApiCredentials {
    private final CheckoutConfiguration configuration;

    public SecretKeyCredentials(CheckoutConfiguration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("configuration must not be null");
        }
        this.configuration = configuration;
    }

    @Override
    public void authorizeAsync(HttpURLConnection httpUrlConnection) {
        if (httpUrlConnection == null) {
            throw new IllegalArgumentException("httpUrlConnection must not be null");
        }
        if (CheckoutUtils.isNullOrEmpty(configuration.getSecretKey())) {
            throw new IllegalArgumentException("Your Secret Key must be configured");
        }

        httpUrlConnection.setRequestProperty("Authorization", configuration.getSecretKey());
    }
}