package com.checkout.apm.pagofacil;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class PagoFacilImpl extends AbstractClient implements PagoFacilClient {

    private static final String PAGO_FACIL_PATH = "/apms/pagofacil/payments";

    public PagoFacilImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, SecretKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<Void> succeed(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.postAsync(buildPath(PAGO_FACIL_PATH, paymentId, "succeed"), apiCredentials, Void.class, null, null);
    }

    @Override
    public CompletableFuture<Void> expire(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.postAsync(buildPath(PAGO_FACIL_PATH, paymentId, "expire"), apiCredentials, Void.class, null, null);
    }

}
