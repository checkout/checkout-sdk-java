package com.checkout.payments;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PaymentsClient {
    <T extends RequestSource> CompletableFuture<PaymentResponse> requestAsync(PaymentRequest<T> paymentRequest);

    CompletableFuture<GetPaymentResponse> getAsync(String paymentId);

    CompletableFuture<List<PaymentAction>> getActionsAsync(String paymentId);

    CompletableFuture<CaptureResponse> captureAsync(String paymentId);

    CompletableFuture<CaptureResponse> captureAsync(String paymentId, CaptureRequest captureRequest);

    CompletableFuture<RefundResponse> refundAsync(String paymentId);

    CompletableFuture<RefundResponse> refundAsync(String paymentId, RefundRequest refundRequest);

    CompletableFuture<VoidResponse> voidAsync(String paymentId);

    CompletableFuture<VoidResponse> voidAsync(String paymentId, VoidRequest voidRequest);
}