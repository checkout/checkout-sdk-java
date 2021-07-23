package com.checkout.beta;

import com.checkout.ApiClient;
import com.checkout.ApiCredentials;

import static com.checkout.common.CheckoutUtils.requiresNonNull;

public abstract class AbstractClient {

    protected final ApiClient apiClient;
    protected final ApiCredentials apiCredentials;

    public AbstractClient(final ApiClient apiClient, final ApiCredentials apiCredentials) {
        requiresNonNull("apiClient", apiClient);
        requiresNonNull("apiCredentials", apiCredentials);
        this.apiClient = apiClient;
        this.apiCredentials = apiCredentials;
    }

}