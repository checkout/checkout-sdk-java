package com.checkout.apm.oxxo;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class OxxoClientImpl extends AbstractClient implements OxxoClient {

    private static final String OXXO_PATH = "/apms/oxxo/payments";

    public OxxoClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, SecretKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<Void> succeed(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.postAsync(buildPath(OXXO_PATH, paymentId, "succeed"), apiCredentials, Void.class, null, null);
    }

    @Override
    public CompletableFuture<Void> expire(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.postAsync(buildPath(OXXO_PATH, paymentId, "expire"), apiCredentials, Void.class, null, null);
    }

}
