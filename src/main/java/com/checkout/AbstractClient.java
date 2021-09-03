package com.checkout;

import static com.checkout.common.CheckoutUtils.validateParams;

public abstract class AbstractClient {

    protected final ApiClient apiClient;
    protected final ApiCredentials apiCredentials;

    protected AbstractClient(final ApiClient apiClient, final ApiCredentials apiCredentials) {
        validateParams("apiClient", apiClient, "apiCredentials", apiCredentials);
        this.apiClient = apiClient;
        this.apiCredentials = apiCredentials;
    }

    protected static String buildPath(final String... pathParams) {
        return String.join("/", pathParams);
    }

}