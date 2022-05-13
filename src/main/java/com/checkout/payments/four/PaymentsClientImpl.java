package com.checkout.payments.four;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorizationType;
import com.checkout.payments.four.request.AuthorizationRequest;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.request.PayoutRequest;
import com.checkout.payments.four.response.AuthorizationResponse;
import com.checkout.payments.four.response.GetPaymentResponse;
import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.payments.four.response.PayoutResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public final class PaymentsClientImpl extends AbstractClient implements PaymentsClient {

    private static final String PAYMENTS_PATH = "payments";
    private static final String ACTIONS_PATH = "actions";
    private static final String CAPTURES_PATH = "captures";
    private static final String AUTHORIZATIONS_PATH = "authorizations";
    private static final String REFUNDS_PATH = "refunds";
    private static final String VOIDS_PATH = "voids";

    private static final Type PAYMENT_ACTIONS_TYPE = new TypeToken<ItemsResponse<PaymentAction>>() {
    }.getType();

    public PaymentsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<PaymentResponse> requestPayment(final PaymentRequest paymentRequest) {
        validateParams("paymentRequest", paymentRequest);
        return apiClient.postAsync(PAYMENTS_PATH, sdkAuthorization(), PaymentResponse.class, paymentRequest, null);
    }

    @Override
    public CompletableFuture<PaymentResponse> requestPayment(final PaymentRequest paymentRequest, final String idempotencyKey) {
        validateParams("paymentRequest", paymentRequest, "idempotencyKey", idempotencyKey);
        return apiClient.postAsync(PAYMENTS_PATH, sdkAuthorization(), PaymentResponse.class, paymentRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<PayoutResponse> requestPayout(final PayoutRequest payoutRequest) {
        validateParams("payoutRequest", payoutRequest);
        return apiClient.postAsync(PAYMENTS_PATH, sdkAuthorization(), PayoutResponse.class, payoutRequest, null);
    }

    @Override
    public CompletableFuture<PayoutResponse> requestPayout(final PayoutRequest payoutRequest, final String idempotencyKey) {
        validateParams("payoutRequest", payoutRequest, "idempotencyKey", idempotencyKey);
        return apiClient.postAsync(PAYMENTS_PATH, sdkAuthorization(), PayoutResponse.class, payoutRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<GetPaymentResponse> getPayment(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.getAsync(buildPath(PAYMENTS_PATH, paymentId), sdkAuthorization(), GetPaymentResponse.class);
    }

    @Override
    public CompletableFuture<ItemsResponse<PaymentAction>> getPaymentActions(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.getAsync(buildPath(PAYMENTS_PATH, paymentId, ACTIONS_PATH), sdkAuthorization(), PAYMENT_ACTIONS_TYPE);
    }

    @Override
    public CompletableFuture<AuthorizationResponse> incrementPaymentAuthorization(final String paymentId, final AuthorizationRequest authorizationRequest) {
        validateParams("paymentId", paymentId);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, AUTHORIZATIONS_PATH), sdkAuthorization(), AuthorizationResponse.class, authorizationRequest, null);
    }

    @Override
    public CompletableFuture<AuthorizationResponse> incrementPaymentAuthorization(final String paymentId, final AuthorizationRequest authorizationRequest, final String idempotencyKey) {
        validateParams("paymentId", paymentId, "idempotencyKey", idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, AUTHORIZATIONS_PATH), sdkAuthorization(), AuthorizationResponse.class, authorizationRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<CaptureResponse> capturePayment(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, CAPTURES_PATH), sdkAuthorization(), CaptureResponse.class, null, null);
    }

    @Override
    public CompletableFuture<CaptureResponse> capturePayment(final String paymentId, final String idempotencyKey) {
        validateParams("paymentId", paymentId, "idempotencyKey", idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, CAPTURES_PATH), sdkAuthorization(), CaptureResponse.class, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<CaptureResponse> capturePayment(final String paymentId, final CaptureRequest captureRequest) {
        validateParams("paymentId", paymentId, "captureRequest", captureRequest);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, CAPTURES_PATH), sdkAuthorization(), CaptureResponse.class, captureRequest, null);
    }

    @Override
    public CompletableFuture<CaptureResponse> capturePayment(final String paymentId, final CaptureRequest captureRequest, final String idempotencyKey) {
        validateParams("paymentId", paymentId, "captureRequest", captureRequest, "idempotencyKey", idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, CAPTURES_PATH), sdkAuthorization(), CaptureResponse.class, captureRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<RefundResponse> refundPayment(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, REFUNDS_PATH), sdkAuthorization(), RefundResponse.class, null, null);
    }

    @Override
    public CompletableFuture<RefundResponse> refundPayment(final String paymentId, final String idempotencyKey) {
        validateParams("paymentId", paymentId, "idempotencyKey", idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, REFUNDS_PATH), sdkAuthorization(), RefundResponse.class, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<RefundResponse> refundPayment(final String paymentId, final RefundRequest refundRequest) {
        validateParams("paymentId", paymentId, "refundRequest", refundRequest);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, REFUNDS_PATH), sdkAuthorization(), RefundResponse.class, refundRequest, null);
    }

    @Override
    public CompletableFuture<RefundResponse> refundPayment(final String paymentId, final RefundRequest refundRequest, final String idempotencyKey) {
        validateParams("paymentId", paymentId, "refundRequest", refundRequest, "idempotencyKey", idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, REFUNDS_PATH), sdkAuthorization(), RefundResponse.class, refundRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<VoidResponse> voidPayment(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, VOIDS_PATH), sdkAuthorization(), VoidResponse.class, null, null);
    }

    @Override
    public CompletableFuture<VoidResponse> voidPayment(final String paymentId, final String idempotencyKey) {
        validateParams("paymentId", paymentId, "idempotencyKey", idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, VOIDS_PATH), sdkAuthorization(), VoidResponse.class, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<VoidResponse> voidPayment(final String paymentId, final VoidRequest voidRequest) {
        validateParams("paymentId", paymentId, "voidRequest", voidRequest);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, VOIDS_PATH), sdkAuthorization(), VoidResponse.class, voidRequest, null);
    }

    @Override
    public CompletableFuture<VoidResponse> voidPayment(final String paymentId, final VoidRequest voidRequest, final String idempotencyKey) {
        validateParams("paymentId", paymentId, "voidRequest", voidRequest, "idempotencyKey", idempotencyKey);
        return apiClient.postAsync(buildPath(PAYMENTS_PATH, paymentId, VOIDS_PATH), sdkAuthorization(), VoidResponse.class, voidRequest, idempotencyKey);
    }

}
