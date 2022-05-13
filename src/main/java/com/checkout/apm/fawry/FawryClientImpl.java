package com.checkout.apm.fawry;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

public class FawryClientImpl extends AbstractClient implements FawryClient {

    private static final String FAWRY_PATH = "fawry/payments";

    public FawryClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<EmptyResponse> approve(final String reference) {
        return apiClient.putAsync(buildPath(FAWRY_PATH, reference, "approval"), sdkAuthorization(), EmptyResponse.class, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> cancel(final String reference) {
        return apiClient.putAsync(buildPath(FAWRY_PATH, reference, "cancellation"), sdkAuthorization(), EmptyResponse.class, null);
    }

}
