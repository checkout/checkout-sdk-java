package com.checkout.apm.klarna;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.payments.CaptureResponse;
import com.checkout.payments.VoidRequest;
import com.checkout.payments.VoidResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class KlarnaClientImpl extends AbstractClient implements KlarnaClient {

    private static final String CREDIT_SESSIONS = "credit-sessions";
    private static final String ORDERS = "orders";
    private static final String CAPTURES = "captures";
    private static final String VOIDS = "voids";

    public KlarnaClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.PUBLIC_KEY);
    }

    @Override
    public CompletableFuture<CreditSessionResponse> createCreditSession(final CreditSessionRequest creditSessionRequest) {
        validateParams("creditSessionRequest", creditSessionRequest);
        return apiClient.postAsync(buildPath(getBaseURL(), CREDIT_SESSIONS), sdkAuthorization(), CreditSessionResponse.class, creditSessionRequest, null);
    }

    @Override
    public CompletableFuture<CreditSession> getCreditSession(final String sessionId) {
        validateParams("sessionId", sessionId);
        return apiClient.getAsync(buildPath(getBaseURL(), CREDIT_SESSIONS, sessionId), sdkAuthorization(), CreditSession.class);
    }

    @Override
    public CompletableFuture<CaptureResponse> capturePayment(final String paymentId, final OrderCaptureRequest captureRequest) {
        validateParams("paymentId", paymentId, "captureRequest", captureRequest);
        return apiClient.postAsync(buildPath(getBaseURL(), ORDERS, paymentId, CAPTURES), sdkAuthorization(), CaptureResponse.class, captureRequest, null);
    }

    @Override
    public CompletableFuture<VoidResponse> voidCapture(final String paymentId, final VoidRequest voidRequest) {
        validateParams("paymentId", paymentId, "voidRequest", voidRequest);
        return apiClient.postAsync(buildPath(getBaseURL(), ORDERS, paymentId, VOIDS), sdkAuthorization(), VoidResponse.class, voidRequest, null);
    }

    private String getBaseURL() {
        if (isSandbox()) {
            return "klarna-external";
        }
        return "klarna";
    }

}
