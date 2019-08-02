package com.checkout.payments;

import com.checkout.ApiClient;
import com.checkout.ApiCredentials;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
import com.checkout.common.Resource;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class PaymentsClientImpl implements PaymentsClient {
    private static final Map<Integer, Class<? extends Resource>> PAYMENT_RESPONSE_MAPPINGS = new HashMap<>();

    static {
        PAYMENT_RESPONSE_MAPPINGS.put(202, PaymentPending.class); // ACCEPTED
        PAYMENT_RESPONSE_MAPPINGS.put(201, PaymentProcessed.class); // CREATED
    }

    private final ApiClient apiClient;
    private final ApiCredentials credentials;

    public PaymentsClientImpl(ApiClient apiClient, CheckoutConfiguration configuration) {
        if (apiClient == null) {
            throw new IllegalArgumentException("apiClient must not be null");
        }
        if (configuration == null) {
            throw new IllegalArgumentException("configuration must not be null");
        }

        this.apiClient = apiClient;
        credentials = new SecretKeyCredentials(configuration);
    }

    private static String getPaymentUrl(String paymentId) {
        final String path = "payments/";
        return path + paymentId;
    }

    @Override
    public <T extends RequestSource> CompletableFuture<PaymentResponse> requestAsync(PaymentRequest<T> paymentRequest) {
        return requestPaymentAsync(paymentRequest, PAYMENT_RESPONSE_MAPPINGS, Optional.empty());
    }

    @Override
    public <T extends RequestSource> CompletableFuture<PaymentResponse> requestAsync(PaymentRequest<T> paymentRequest, String idempotencyKey) {
        return requestPaymentAsync(paymentRequest, PAYMENT_RESPONSE_MAPPINGS, Optional.of(idempotencyKey));
    }

    @Override
    public CompletableFuture<GetPaymentResponse> getAsync(String paymentId) {
        return apiClient.getAsync(getPaymentUrl(paymentId), credentials, GetPaymentResponse.class);
    }

    @Override
    public CompletableFuture<List<PaymentAction>> getActionsAsync(String paymentId) {
        final String path = "/actions";
        return apiClient.getAsync(getPaymentUrl(paymentId) + path, credentials, PaymentAction[].class)
                .thenApply(Arrays::asList);
    }

    @Override
    public CompletableFuture<CaptureResponse> captureAsync(String paymentId) {
        return captureAsync(paymentId, (CaptureRequest) null);
    }

    @Override
    public CompletableFuture<CaptureResponse> captureAsync(String paymentId, String idempotencyKey) {
        return captureAsync(paymentId, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<CaptureResponse> captureAsync(String paymentId, CaptureRequest captureRequest) {
        final String path = "/captures";
        return apiClient.postAsync(getPaymentUrl(paymentId) + path, credentials, CaptureResponse.class, captureRequest, Optional.empty());
    }

    @Override
    public CompletableFuture<CaptureResponse> captureAsync(String paymentId, CaptureRequest captureRequest, String idempotencyKey) {
        final String path = "/captures";
        return apiClient.postAsync(getPaymentUrl(paymentId) + path, credentials, CaptureResponse.class, captureRequest, Optional.of(idempotencyKey));
    }

    @Override
    public CompletableFuture<RefundResponse> refundAsync(String paymentId) {
        return refundAsync(paymentId, (RefundRequest) null);
    }

    @Override
    public CompletableFuture<RefundResponse> refundAsync(String paymentId, String idempotencyKey) {
        return refundAsync(paymentId, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<RefundResponse> refundAsync(String paymentId, RefundRequest refundRequest) {
        final String path = "/refunds";
        return apiClient.postAsync(getPaymentUrl(paymentId) + path, credentials, RefundResponse.class, refundRequest, Optional.empty());
    }

    @Override
    public CompletableFuture<RefundResponse> refundAsync(String paymentId, RefundRequest refundRequest, String idempotencyKey) {
        final String path = "/refunds";
        return apiClient.postAsync(getPaymentUrl(paymentId) + path, credentials, RefundResponse.class, refundRequest, Optional.of(idempotencyKey));
    }

    @Override
    public CompletableFuture<VoidResponse> voidAsync(String paymentId) {
        return voidAsync(paymentId, (VoidRequest) null);
    }

    @Override
    public CompletableFuture<VoidResponse> voidAsync(String paymentId, String idempotencyKey) {
        return voidAsync(paymentId, null, idempotencyKey);
    }

    @Override
    public CompletableFuture<VoidResponse> voidAsync(String paymentId, VoidRequest voidRequest) {
        final String path = "/voids";
        return apiClient.postAsync(getPaymentUrl(paymentId) + path, credentials, VoidResponse.class, voidRequest, Optional.empty());
    }

    @Override
    public CompletableFuture<VoidResponse> voidAsync(String paymentId, VoidRequest voidRequest, String idempotencyKey) {
        final String path = "/voids";
        return apiClient.postAsync(getPaymentUrl(paymentId) + path, credentials, VoidResponse.class, voidRequest, Optional.of(idempotencyKey));
    }

    private <T extends RequestSource> CompletableFuture<PaymentResponse> requestPaymentAsync(PaymentRequest<T> paymentRequest, Map<Integer, Class<? extends Resource>> resultTypeMappings, Optional<String> idempotencyKey) {
        final String path = "payments";
        return apiClient.postAsync(path, credentials, resultTypeMappings, paymentRequest, idempotencyKey)
                .thenApply((Resource it) -> {
                    if(it instanceof PaymentPending) {
                        return PaymentResponse.from((PaymentPending)it);
                    } else if(it instanceof PaymentProcessed) {
                        return PaymentResponse.from((PaymentProcessed) it);
                    } else {
                        throw new IllegalStateException("Expected one of PaymentPending and PaymentProcessed but was " + it.getClass());
                    }
                });
    }
}