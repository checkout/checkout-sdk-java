package com.checkout.apm.ideal;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

public class IdealClientImpl extends AbstractClient implements IdealClient {

    private static final String IDEAL_PATH = "/ideal-external/issuers";

    public IdealClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<IssuerResponse> getIssuers() {
        return apiClient.getAsync(IDEAL_PATH, sdkAuthorization(), IssuerResponse.class);
    }

}
