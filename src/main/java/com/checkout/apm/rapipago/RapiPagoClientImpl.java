package com.checkout.apm.rapipago;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class RapiPagoClientImpl extends AbstractClient implements RapiPagoClient {

    private static final String RAPI_PAGO_PATH = "/apms/rapipago/payments";

    public RapiPagoClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, SecretKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<Void> succeed(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.postAsync(buildPath(RAPI_PAGO_PATH, paymentId, "succeed"), apiCredentials, Void.class, null, null);
    }

    @Override
    public CompletableFuture<Void> expire(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.postAsync(buildPath(RAPI_PAGO_PATH, paymentId, "expire"), apiCredentials, Void.class, null, null);
    }

}
