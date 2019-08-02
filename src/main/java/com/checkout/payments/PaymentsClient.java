package com.checkout.payments;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PaymentsClient {
    <T extends RequestSource> CompletableFuture<PaymentResponse> requestAsync(PaymentRequest<T> paymentRequest);

    <T extends RequestSource> CompletableFuture<PaymentResponse> requestAsync(PaymentRequest<T> paymentRequest, String idempotencyKey);

    CompletableFuture<GetPaymentResponse> getAsync(String paymentId);

    CompletableFuture<List<PaymentAction>> getActionsAsync(String paymentId);

    CompletableFuture<CaptureResponse> captureAsync(String paymentId);

    CompletableFuture<CaptureResponse> captureAsync(String paymentId, String idempotencyKey);

    CompletableFuture<CaptureResponse> captureAsync(String paymentId, CaptureRequest captureRequest);

    CompletableFuture<CaptureResponse> captureAsync(String paymentId, CaptureRequest captureRequest, String idempotencyKey);

    CompletableFuture<RefundResponse> refundAsync(String paymentId);

    CompletableFuture<RefundResponse> refundAsync(String paymentId, String idempotencyKey);

    CompletableFuture<RefundResponse> refundAsync(String paymentId, RefundRequest refundRequest);

    CompletableFuture<RefundResponse> refundAsync(String paymentId, RefundRequest refundRequest, String idempotencyKey);

    CompletableFuture<VoidResponse> voidAsync(String paymentId);

    CompletableFuture<VoidResponse> voidAsync(String paymentId, String idempotencyKey);

    CompletableFuture<VoidResponse> voidAsync(String paymentId, VoidRequest voidRequest);

    CompletableFuture<VoidResponse> voidAsync(String paymentId, VoidRequest voidRequest, String idempotencyKey);
}