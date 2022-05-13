package com.checkout.apm.rapipago;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class RapiPagoClientImpl extends AbstractClient implements RapiPagoClient {

    private static final String RAPI_PAGO_PATH = "apms/rapipago/payments";

    public RapiPagoClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<EmptyResponse> succeed(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.postAsync(buildPath(RAPI_PAGO_PATH, paymentId, "succeed"), sdkAuthorization(), EmptyResponse.class, null, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> expire(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.postAsync(buildPath(RAPI_PAGO_PATH, paymentId, "expire"), sdkAuthorization(), EmptyResponse.class, null, null);
    }

}
