package com.checkout.apm.giropay;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

public class GiropayClientImpl extends AbstractClient implements GiropayClient {

    private static final String GIROPAY_PATH = "/giropay";

    public GiropayClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<BanksResponse> getBanks() {
        return apiClient.getAsync(buildPath(GIROPAY_PATH, "banks"), sdkAuthorization(), BanksResponse.class);
    }

    @Override
    public CompletableFuture<BanksResponse> getEpsBanks() {
        return apiClient.getAsync(buildPath(GIROPAY_PATH, "eps", "banks"), sdkAuthorization(), BanksResponse.class);
    }

}
