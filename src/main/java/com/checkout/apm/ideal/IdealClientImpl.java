package com.checkout.apm.ideal;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

public class IdealClientImpl extends AbstractClient implements IdealClient {

    private static final String IDEAL_EXTERNAL_PATH = "/ideal-external";
    private static final String ISSUERS = "issuers";

    public IdealClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<IdealInfo> getInfo() {
        return apiClient.getAsync(buildPath(IDEAL_EXTERNAL_PATH), sdkAuthorization(), IdealInfo.class);
    }

    @Override
    public CompletableFuture<IssuerResponse> getIssuers() {
        return apiClient.getAsync(buildPath(IDEAL_EXTERNAL_PATH, ISSUERS), sdkAuthorization(), IssuerResponse.class);
    }

}
