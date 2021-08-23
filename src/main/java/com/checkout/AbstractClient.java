package com.checkout;

import static com.checkout.common.CheckoutUtils.requiresNonNull;

public abstract class AbstractClient {

    protected final ApiClient apiClient;
    protected final ApiCredentials apiCredentials;

    protected AbstractClient(final ApiClient apiClient, final ApiCredentials apiCredentials) {
        requiresNonNull("apiClient", apiClient);
        requiresNonNull("apiCredentials", apiCredentials);
        this.apiClient = apiClient;
        this.apiCredentials = apiCredentials;
    }

    protected static String constructApiPath(final String... pathParams) {
        return String.join("/", pathParams);
    }

}