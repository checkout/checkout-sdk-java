package com.checkout.apm.klarna;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.PublicKeyCredentials;
import com.checkout.payments.VoidRequest;
import com.checkout.payments.VoidResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class KlarnaClientImpl extends AbstractClient implements KlarnaClient {

    private static final String KLARNA = "klarna";
    private static final String CREDIT_SESSIONS = "credit-sessions";
    private static final String ORDERS = "orders";
    private static final String CAPTURES = "captures";
    private static final String VOIDS = "voids";

    public KlarnaClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, PublicKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<CreditSessionResponse> createCreditSession(final CreditSessionRequest creditSessionRequest) {
        validateParams("creditSessionRequest", creditSessionRequest);
        return apiClient.postAsync(buildPath(KLARNA, CREDIT_SESSIONS), apiCredentials, CreditSessionResponse.class, creditSessionRequest, null);
    }

    @Override
    public CompletableFuture<CreditSession> getCreditSession(final String sessionId) {
        validateParams("sessionId", sessionId);
        return apiClient.getAsync(buildPath(KLARNA, CREDIT_SESSIONS, sessionId), apiCredentials, CreditSession.class);
    }

    @Override
    public CompletableFuture<VoidResponse> capturePayment(final String paymentId, final OrderCaptureRequest captureRequest) {
        validateParams("paymentId", paymentId, "captureRequest", captureRequest);
        return apiClient.postAsync(buildPath(KLARNA, ORDERS, paymentId, CAPTURES), apiCredentials, VoidResponse.class, captureRequest, null);
    }

    @Override
    public CompletableFuture<VoidResponse> voidCapture(final String paymentId, final VoidRequest voidRequest) {
        validateParams("paymentId", paymentId, "voidRequest", voidRequest);
        return apiClient.postAsync(buildPath(KLARNA, ORDERS, paymentId, VOIDS), apiCredentials, VoidResponse.class, voidRequest, null);
    }

}
