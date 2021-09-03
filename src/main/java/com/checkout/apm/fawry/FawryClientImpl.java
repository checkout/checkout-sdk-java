package com.checkout.apm.fawry;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;

import java.util.concurrent.CompletableFuture;

public class FawryClientImpl extends AbstractClient implements FawryClient {

    private static final String FAWRY_PATH = "/fawry/payments";

    public FawryClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, SecretKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<Void> approve(final String reference) {
        return apiClient.putAsync(buildPath(FAWRY_PATH, reference, "approval"), apiCredentials, Void.class, null);
    }

    @Override
    public CompletableFuture<Void> cancel(final String reference) {
        return apiClient.putAsync(buildPath(FAWRY_PATH, reference, "cancellation"), apiCredentials, Void.class, null);
    }

}
