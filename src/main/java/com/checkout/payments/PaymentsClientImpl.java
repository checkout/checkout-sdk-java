package com.checkout.payments;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.Resource;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class PaymentsClientImpl extends AbstractClient implements PaymentsClient {

    private static final Map<Integer, Class<? extends Resource>> PAYMENT_RESPONSE_MAPPINGS = new HashMap<>();

    static {
        PAYMENT_RESPONSE_MAPPINGS.put(202, PaymentPending.class); // ACCEPTED
        PAYMENT_RESPONSE_MAPPINGS.put(201, PaymentProcessed.class); // CREATED
    }

    public PaymentsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public <T extends RequestSource> CompletableFuture<PaymentResponse> requestAsync(final PaymentRequest<T> paymentRequest) {
        return requestPaymentAsync(paymentRequest, null);
    }

    @Override
    public <T extends RequestSource> CompletableFuture<PaymentResponse> requestAsync(final PaymentRequest<T> paymentRequest, final String idempotencyKey) {
        return requestPaymentAsync(paymentRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<GetPaymentResponse> getAsync(final String paymentId) {
        return apiClient.getAsync(getPaymentUrl(paymentId), sdkAuthorization(), GetPaymentResponse.class);
    }

    @Override
    public CompletableFuture<List<PaymentAction>> getActionsAsync(final String paymentId) {
        final String path = "/actions";
        return apiClient.getAsync(getPaymentUrl(paymentId) + path, sdkAuthorization(), PaymentAction[].class)
                .thenApply(Arrays::asList);
    }

    @Override
    public CompletableFuture<CaptureResponse> captureAsync(final String paymentId) {
        return captureAsync(paymentId, (CaptureRequest) null);
    }

    @Override
    public CompletableFuture<CaptureResponse> captureAsync(final String paymentId, final String idempotencyKey) {
        return captureAsync(paymentId, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<CaptureResponse> captureAsync(final String paymentId, final CaptureRequest captureRequest) {
        final String path = "/captures";
        return apiClient.postAsync(getPaymentUrl(paymentId) + path, sdkAuthorization(), CaptureResponse.class, captureRequest, null);
    }

    @Override
    public CompletableFuture<CaptureResponse> captureAsync(final String paymentId, final CaptureRequest captureRequest, final String idempotencyKey) {
        final String path = "/captures";
        return apiClient.postAsync(getPaymentUrl(paymentId) + path, sdkAuthorization(), CaptureResponse.class, captureRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<RefundResponse> refundAsync(final String paymentId) {
        return refundAsync(paymentId, (RefundRequest) null);
    }

    @Override
    public CompletableFuture<RefundResponse> refundAsync(final String paymentId, final String idempotencyKey) {
        return refundAsync(paymentId, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<RefundResponse> refundAsync(final String paymentId, final RefundRequest refundRequest) {
        final String path = "/refunds";
        return apiClient.postAsync(getPaymentUrl(paymentId) + path, sdkAuthorization(), RefundResponse.class, refundRequest, null);
    }

    @Override
    public CompletableFuture<RefundResponse> refundAsync(final String paymentId, final RefundRequest refundRequest, final String idempotencyKey) {
        final String path = "/refunds";
        return apiClient.postAsync(getPaymentUrl(paymentId) + path, sdkAuthorization(), RefundResponse.class, refundRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<VoidResponse> voidAsync(final String paymentId) {
        return voidAsync(paymentId, (VoidRequest) null);
    }

    @Override
    public CompletableFuture<VoidResponse> voidAsync(final String paymentId, final String idempotencyKey) {
        return voidAsync(paymentId, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<VoidResponse> voidAsync(final String paymentId, final VoidRequest voidRequest) {
        final String path = "/voids";
        return apiClient.postAsync(getPaymentUrl(paymentId) + path, sdkAuthorization(), VoidResponse.class, voidRequest, null);
    }

    @Override
    public CompletableFuture<VoidResponse> voidAsync(final String paymentId, final VoidRequest voidRequest, final String idempotencyKey) {
        final String path = "/voids";
        return apiClient.postAsync(getPaymentUrl(paymentId) + path, sdkAuthorization(), VoidResponse.class, voidRequest, idempotencyKey);
    }

    private <T extends RequestSource> CompletableFuture<PaymentResponse> requestPaymentAsync(final PaymentRequest<T> paymentRequest, final String idempotencyKey) {
        final String path = "payments";
        return apiClient.postAsync(path, sdkAuthorization(), PAYMENT_RESPONSE_MAPPINGS, paymentRequest, idempotencyKey)
                .thenApply((Resource it) -> {
                    if (it instanceof PaymentPending) {
                        return PaymentResponse.from((PaymentPending) it);
                    } else if (it instanceof PaymentProcessed) {
                        return PaymentResponse.from((PaymentProcessed) it);
                    } else {
                        throw new IllegalStateException("Expected one of PaymentPending and PaymentProcessed but was " + it.getClass());
                    }
                });
    }

    private static String getPaymentUrl(final String paymentId) {
        final String path = "payments/";
        return path + paymentId;
    }

}
