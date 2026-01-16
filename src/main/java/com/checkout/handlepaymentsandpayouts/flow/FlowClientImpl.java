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

        validatePaymentSessionRequest(paymentSessionRequest);

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
            final SubmitPaymentSessionRequest submitPaymentSessionRequest
    ) {

        validateSubmitPaymentSessionRequest(paymentId, submitPaymentSessionRequest);

        return apiClient.postAsync(
                buildPath(PAYMENT_SESSIONS_PATH, paymentId, SUBMIT_PATH),
                sdkAuthorization(),
                SubmitPaymentSessionResponse.class,
                submitPaymentSessionRequest,
                null
        );
    }

    @Override
    public CompletableFuture<PaymentSessionWithPaymentResponse> requestPaymentSessionWithPayment(
            final PaymentSessionWithPaymentRequest paymentSessionWithPaymentRequest
    ) {

        validatePaymentSessionWithPaymentRequest(paymentSessionWithPaymentRequest);

        return apiClient.postAsync(
                buildPath(PAYMENT_SESSIONS_PATH, COMPLETE_PATH),
                sdkAuthorization(),
                PaymentSessionWithPaymentResponse.class,
                paymentSessionWithPaymentRequest,
                null
        );

    }

    // Synchronous methods
    @Override
    public PaymentSessionResponse requestPaymentSessionSync(
            final PaymentSessionRequest paymentSessionRequest
    ) {

        validatePaymentSessionRequest(paymentSessionRequest);

        return apiClient.post(
                PAYMENT_SESSIONS_PATH,
                sdkAuthorization(),
                PaymentSessionResponse.class,
                paymentSessionRequest,
                null
        );

    }

    @Override
    public SubmitPaymentSessionResponse submitPaymentSessionsSync(
            final String paymentId,
            final SubmitPaymentSessionRequest submitPaymentSessionRequest
    ) {

        validateSubmitPaymentSessionRequest(paymentId, submitPaymentSessionRequest);

        return apiClient.post(
                buildPath(PAYMENT_SESSIONS_PATH, paymentId, SUBMIT_PATH),
                sdkAuthorization(),
                SubmitPaymentSessionResponse.class,
                submitPaymentSessionRequest,
                null
        );
    }

    @Override
    public PaymentSessionWithPaymentResponse requestPaymentSessionWithPaymentSync(
            final PaymentSessionWithPaymentRequest paymentSessionWithPaymentRequest
    ) {

        validatePaymentSessionWithPaymentRequest(paymentSessionWithPaymentRequest);

        return apiClient.post(
                buildPath(PAYMENT_SESSIONS_PATH, COMPLETE_PATH),
                sdkAuthorization(),
                PaymentSessionWithPaymentResponse.class,
                paymentSessionWithPaymentRequest,
                null
        );

    }

    // Common methods
    void validatePaymentSessionRequest(final PaymentSessionRequest paymentSessionRequest)
    {
        validateParams("paymentSessionRequest", paymentSessionRequest);
    }

    void validateSubmitPaymentSessionRequest(final String paymentId, final SubmitPaymentSessionRequest submitPaymentSessionRequest)
    {
        validateParams("paymentId", paymentId,"submitPaymentSessionRequest", submitPaymentSessionRequest);
    }

    void validatePaymentSessionWithPaymentRequest(final PaymentSessionWithPaymentRequest paymentSessionWithPaymentRequest)
    {
        validateParams("paymentSessionWithPaymentRequest", paymentSessionWithPaymentRequest);
    }
}
