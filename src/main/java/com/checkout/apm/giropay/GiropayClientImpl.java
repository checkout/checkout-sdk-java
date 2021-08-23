package com.checkout.apm.giropay;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;

import java.util.concurrent.CompletableFuture;

public class GiropayClientImpl extends AbstractClient implements GiropayClient {

    private static final String GIROPAY_PATH = "/giropay";

    public GiropayClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, SecretKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<BanksResponse> getBanks() {
        return apiClient.getAsync(constructApiPath(GIROPAY_PATH, "banks"), apiCredentials, BanksResponse.class);
    }

    @Override
    public CompletableFuture<BanksResponse> getEpsBanks() {
        return apiClient.getAsync(constructApiPath(GIROPAY_PATH, "eps", "banks"), apiCredentials, BanksResponse.class);
    }

}
