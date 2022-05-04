package com.checkout;

import static com.checkout.common.CheckoutUtils.validateParams;

public abstract class AbstractClient {

    protected final ApiClient apiClient;
    protected final CheckoutConfiguration configuration;
    private final SdkAuthorizationType authorizationType;

    protected AbstractClient(final ApiClient apiClient,
                             final CheckoutConfiguration configuration,
                             final SdkAuthorizationType authorizationType) {
        validateParams("apiClient", apiClient, "configuration", configuration, "authorizationType", authorizationType);
        this.apiClient = apiClient;
        this.configuration = configuration;
        this.authorizationType = authorizationType;
    }

    protected SdkAuthorization sdkAuthorization(final SdkAuthorizationType authorizationType) {
        return configuration.getSdkCredentials().getAuthorization(authorizationType);
    }

    protected SdkAuthorization sdkAuthorization() {
        return configuration.getSdkCredentials().getAuthorization(authorizationType);
    }

    protected static String buildPath(final String... pathParams) {
        return String.join("/", pathParams);
    }

    protected boolean isSandbox() {
        return Environment.SANDBOX.equals(configuration.getEnvironment());
    }

}
