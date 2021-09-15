package com.checkout.payments.four;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.payments.four.action.PaymentAction;
import com.checkout.payments.four.capture.CaptureRequest;
import com.checkout.payments.four.capture.CaptureResponse;
import com.checkout.payments.four.payout.PayoutRequest;
import com.checkout.payments.four.payout.PayoutResponse;
import com.checkout.payments.four.refund.RefundRequest;
import com.checkout.payments.four.refund.RefundResponse;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.payments.four.response.source.ResponseCardSource;
import com.checkout.payments.four.response.source.ResponseCurrencyAccountSource;
import com.checkout.payments.four.response.source.ResponseIdSource;
import com.checkout.payments.four.response.source.ResponseNetworkTokenSource;
import com.checkout.payments.four.response.source.ResponseSource;
import com.checkout.payments.four.response.source.ResponseTokenSource;
import com.checkout.payments.four.voids.VoidRequest;
import com.checkout.payments.four.voids.VoidResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class PaymentsClientImpl extends AbstractClient implements PaymentsClient {

    private static final String PAYMENTS_PATH = "/payments";
    private static final String ACTIONS_PATH = "/actions";
    private static final String CAPTURES_PATH = "/captures";
    private static final String REFUNDS_PATH = "/refunds";
    private static final String VOIDS_PATH = "/voids";

    private static final Type PAYMENT_TYPE = TypeToken.getParameterized(PaymentResponse.class,
            ResponseCardSource.class,
            ResponseCurrencyAccountSource.class,
            ResponseIdSource.class,
            ResponseNetworkTokenSource.class,
            ResponseTokenSource.class
    ).getType();

    private static final Type PAYMENT_ACTION_TYPE = new TypeToken<List<PaymentAction>>() {
    }.getType();

    public PaymentsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public <P extends ResponseSource> CompletableFuture<PaymentResponse<P>> requestPayment(final PaymentRequest paymentRequest) {
        return payment(paymentRequest, null);
    }

    @Override
    public <P extends ResponseSource> CompletableFuture<PaymentResponse<P>> requestPayment(final PaymentRequest paymentRequest, final String idempotencyKey) {
        return payment(paymentRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<PayoutResponse> requestPayout(final PayoutRequest payoutRequest) {
        return payout(payoutRequest, null);
    }

    @Override
    public CompletableFuture<PayoutResponse> requestPayout(final PayoutRequest payoutRequest, final String idempotencyKey) {
        return payout(payoutRequest, idempotencyKey);
    }

    @Override
    public <P extends ResponseSource> CompletableFuture<PaymentResponse<P>> getPayment(final String paymentId) {
        return apiClient.getAsync(getPaymentUrl(paymentId), sdkAuthorization(), PAYMENT_TYPE);
    }

    @Override
    public CompletableFuture<List<PaymentAction>> getPaymentActions(final String paymentId) {
        return apiClient.getAsync(getPaymentUrl(paymentId) + ACTIONS_PATH, sdkAuthorization(), PAYMENT_ACTION_TYPE);
    }

    @Override
    public CompletableFuture<CaptureResponse> capturePayment(final String paymentId) {
        return capturePayment(paymentId, (CaptureRequest) null);
    }

    @Override
    public CompletableFuture<CaptureResponse> capturePayment(final String paymentId, final String idempotencyKey) {
        return capturePayment(paymentId, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<CaptureResponse> capturePayment(final String paymentId, final CaptureRequest captureRequest) {
        return apiClient.postAsync(getPaymentUrl(paymentId) + CAPTURES_PATH, sdkAuthorization(), CaptureResponse.class, captureRequest, null);
    }

    @Override
    public CompletableFuture<CaptureResponse> capturePayment(final String paymentId, final CaptureRequest captureRequest, final String idempotencyKey) {
        return apiClient.postAsync(getPaymentUrl(paymentId) + CAPTURES_PATH, sdkAuthorization(), CaptureResponse.class, captureRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<RefundResponse> refundPayment(final String paymentId) {
        return refundPayment(paymentId, (RefundRequest) null);
    }

    @Override
    public CompletableFuture<RefundResponse> refundPayment(final String paymentId, final String idempotencyKey) {
        return refundPayment(paymentId, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<RefundResponse> refundPayment(final String paymentId, final RefundRequest refundRequest) {
        return apiClient.postAsync(getPaymentUrl(paymentId) + REFUNDS_PATH, sdkAuthorization(), RefundResponse.class, refundRequest, null);
    }

    @Override
    public CompletableFuture<RefundResponse> refundPayment(final String paymentId, final RefundRequest refundRequest, final String idempotencyKey) {
        return apiClient.postAsync(getPaymentUrl(paymentId) + REFUNDS_PATH, sdkAuthorization(), RefundResponse.class, refundRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<VoidResponse> voidPayment(final String paymentId) {
        return voidPayment(paymentId, (VoidRequest) null);
    }

    @Override
    public CompletableFuture<VoidResponse> voidPayment(final String paymentId, final String idempotencyKey) {
        return voidPayment(paymentId, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<VoidResponse> voidPayment(final String paymentId, final VoidRequest voidRequest) {
        return apiClient.postAsync(getPaymentUrl(paymentId) + VOIDS_PATH, sdkAuthorization(), VoidResponse.class, voidRequest, null);
    }

    @Override
    public CompletableFuture<VoidResponse> voidPayment(final String paymentId, final VoidRequest voidRequest, final String idempotencyKey) {
        return apiClient.postAsync(getPaymentUrl(paymentId) + VOIDS_PATH, sdkAuthorization(), VoidResponse.class, voidRequest, idempotencyKey);
    }

    private <P extends ResponseSource> CompletableFuture<PaymentResponse<P>> payment(
            final PaymentRequest paymentRequest,
            final String idempotencyKey
    ) {
        return apiClient.postAsync(PAYMENTS_PATH, sdkAuthorization(), PAYMENT_TYPE, paymentRequest, idempotencyKey);
    }

    private CompletableFuture<PayoutResponse> payout(
            final PayoutRequest paymentRequest,
            final String idempotencyKey
    ) {
        return apiClient.postAsync(PAYMENTS_PATH, sdkAuthorization(), PayoutResponse.class, paymentRequest, idempotencyKey);
    }

    private String getPaymentUrl(final String paymentId) {
        return PAYMENTS_PATH + "/" + paymentId;
    }

}