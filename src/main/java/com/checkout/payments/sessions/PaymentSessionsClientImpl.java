package com.checkout.payments.sessions;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class PaymentSessionsClientImpl extends AbstractClient implements PaymentSessionsClient {

    private static final String PAYMENT_SESSIONS_PATH = "payment-sessions";

    public PaymentSessionsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<PaymentSessionsResponse> requestPaymentSessions(final PaymentSessionsRequest paymentSessionsRequest) {

        validateParams("paymentSessionsRequest", paymentSessionsRequest);

        return apiClient.postAsync(PAYMENT_SESSIONS_PATH, sdkAuthorization(), PaymentSessionsResponse.class, paymentSessionsRequest, null);

    }

}
