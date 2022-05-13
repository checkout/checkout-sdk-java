package com.checkout.apm.pagofacil;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class PagoFacilImpl extends AbstractClient implements PagoFacilClient {

    private static final String PAGO_FACIL_PATH = "apms/pagofacil/payments";

    public PagoFacilImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<EmptyResponse> succeed(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.postAsync(buildPath(PAGO_FACIL_PATH, paymentId, "succeed"), sdkAuthorization(), EmptyResponse.class, null, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> expire(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.postAsync(buildPath(PAGO_FACIL_PATH, paymentId, "expire"), sdkAuthorization(), EmptyResponse.class, null, null);
    }

}
