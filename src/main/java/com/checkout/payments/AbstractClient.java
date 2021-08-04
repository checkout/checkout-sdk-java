package com.checkout.payments;

import com.checkout.ApiClient;
import com.checkout.ApiCredentials;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;

public abstract class AbstractClient {

    protected final ApiClient apiClient;
    protected final ApiCredentials credentials;

    protected AbstractClient(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        if (apiClient == null) {
            throw new IllegalArgumentException("apiClient must not be null");
        }
        if (configuration == null) {
            throw new IllegalArgumentException("configuration must not be null");
        }
        this.apiClient = apiClient;
        this.credentials = new SecretKeyCredentials(configuration);
    }
}
