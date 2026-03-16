package com.checkout.handlepaymentsandpayouts.flow;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionCreateRequest;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionSubmitRequest;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionCompleteRequest;
import com.checkout.handlepaymentsandpayouts.flow.responses.PaymentSessionResponse;
import com.checkout.handlepaymentsandpayouts.flow.responses.PaymentSubmissionResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class FlowClientImpl extends AbstractClient implements FlowClient {

    private static final String PAYMENT_SESSIONS_PATH = "payment-sessions";
    private static final String SUBMIT_PATH = "submit";
    private static final String COMPLETE_PATH = "complete";

    public FlowClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<PaymentSessionResponse> requestPaymentSession(final PaymentSessionCreateRequest request) {
        validatePaymentSessionCreateRequest(request);
        return apiClient.postAsync(PAYMENT_SESSIONS_PATH, 
                                        sdkAuthorization(),
                                        PaymentSessionResponse.class,
                                        request,
                                        null);
    }

    @Override
    public CompletableFuture<PaymentSubmissionResponse> submitPaymentSession(final String sessionId, final PaymentSessionSubmitRequest request) {
        validatePaymentSessionSubmitParameters(sessionId, request);
        return apiClient.postAsync(buildPath(PAYMENT_SESSIONS_PATH, sessionId, SUBMIT_PATH), 
                                        sdkAuthorization(),
                                        PaymentSubmissionResponse.class,
                                        request,
                                        null);
    }

    @Override
    public CompletableFuture<PaymentSubmissionResponse> requestPaymentSessionWithPayment(final PaymentSessionCompleteRequest request) {
        validatePaymentSessionCompleteRequest(request);
        return apiClient.postAsync(buildPath(PAYMENT_SESSIONS_PATH, COMPLETE_PATH), 
                                        sdkAuthorization(),
                                        PaymentSubmissionResponse.class,
                                        request,
                                        null);
    }

    // Synchronous methods
    @Override
    public PaymentSessionResponse requestPaymentSessionSync(final PaymentSessionCreateRequest request) {
        validatePaymentSessionCreateRequest(request);
        return apiClient.post(PAYMENT_SESSIONS_PATH, 
                                        sdkAuthorization(),
                                        PaymentSessionResponse.class,
                                        request,
                                        null);
    }

    @Override
    public PaymentSubmissionResponse submitPaymentSessionSync(final String sessionId, final PaymentSessionSubmitRequest request) {
        validatePaymentSessionSubmitParameters(sessionId, request);
        return apiClient.post(buildPath(PAYMENT_SESSIONS_PATH, sessionId, SUBMIT_PATH), 
                                        sdkAuthorization(),
                                        PaymentSubmissionResponse.class,
                                        request,
                                        null);
    }

    @Override
    public PaymentSubmissionResponse requestPaymentSessionWithPaymentSync(final PaymentSessionCompleteRequest request) {
        validatePaymentSessionCompleteRequest(request);
        return apiClient.post(buildPath(PAYMENT_SESSIONS_PATH, COMPLETE_PATH), 
                                        sdkAuthorization(),
                                        PaymentSubmissionResponse.class,
                                        request,
                                        null);
    }

    // Common methods
    private void validatePaymentSessionCreateRequest(final PaymentSessionCreateRequest request) {
        validateParams("request", request);
    }

    private void validatePaymentSessionSubmitParameters(final String sessionId, final PaymentSessionSubmitRequest request) {
        validateParams("sessionId", sessionId, "request", request);
    }

    private void validatePaymentSessionCompleteRequest(final PaymentSessionCompleteRequest request) {
        validateParams("request", request);
    }
}
