package com.checkout.handlepaymentsandpayouts.flow;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.PaymentSessionRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.responses.PaymentSessionResponse;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.PaymentSessionWithPaymentRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.responses.PaymentSessionWithPaymentResponse;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.requests.SubmitPaymentSessionRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.responses.SubmitPaymentSessionResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class FlowClientImpl extends AbstractClient implements FlowClient {

    private static final String PAYMENT_SESSIONS_PATH = "payment-sessions";
    private static final String COMPLETE_PATH = "complete";
    private static final String SUBMIT_PATH = "submit";

    public FlowClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<PaymentSessionResponse> requestPaymentSession(
            final PaymentSessionRequest paymentSessionRequest
    ) {

        validateParams("paymentSessionRequest", paymentSessionRequest);

        return apiClient.postAsync(
                PAYMENT_SESSIONS_PATH,
                sdkAuthorization(),
                PaymentSessionResponse.class,
                paymentSessionRequest,
                null
        );

    }

    @Override
    public CompletableFuture<SubmitPaymentSessionResponse> submitPaymentSessions(
            final String paymentId,
            final SubmitPaymentSessionRequest submitPaymentSessionsRequest
    ) {

        validateParams("paymentId", paymentId,"submitPaymentSessionsRequest", submitPaymentSessionsRequest);

        return apiClient.postAsync(
                buildPath(PAYMENT_SESSIONS_PATH, paymentId, SUBMIT_PATH),
                sdkAuthorization(),
                SubmitPaymentSessionResponse.class,
                submitPaymentSessionsRequest,
                null
        );
    }

    @Override
    public CompletableFuture<PaymentSessionWithPaymentResponse> requestPaymentSessionWithPayment(
            final PaymentSessionWithPaymentRequest paymentSessionWithPaymentRequest
    ) {

        validateParams("paymentSessionWithPaymentRequest", paymentSessionWithPaymentRequest);

        return apiClient.postAsync(
                buildPath(PAYMENT_SESSIONS_PATH, COMPLETE_PATH),
                sdkAuthorization(),
                PaymentSessionWithPaymentResponse.class,
                paymentSessionWithPaymentRequest,
                null
        );

    }

}
