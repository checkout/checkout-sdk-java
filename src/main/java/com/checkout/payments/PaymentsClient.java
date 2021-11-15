package com.checkout.payments;

import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.PayoutRequest;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PaymentsClient {

    CompletableFuture<PaymentResponse> requestPayment(PaymentRequest paymentRequest);

    CompletableFuture<PaymentResponse> requestPayment(PaymentRequest paymentRequest, String idempotencyKey);

    CompletableFuture<PaymentResponse> requestPayout(PayoutRequest payoutRequest);

    CompletableFuture<PaymentResponse> requestPayout(PayoutRequest payoutRequest, String idempotencyKey);

    CompletableFuture<GetPaymentResponse> getPayment(String paymentId);

    CompletableFuture<List<PaymentAction>> getPaymentActions(String paymentId);

    CompletableFuture<CaptureResponse> capturePayment(String paymentId);

    CompletableFuture<CaptureResponse> capturePayment(String paymentId, String idempotencyKey);

    CompletableFuture<CaptureResponse> capturePayment(String paymentId, CaptureRequest captureRequest);

    CompletableFuture<CaptureResponse> capturePayment(String paymentId, CaptureRequest captureRequest, String idempotencyKey);

    CompletableFuture<RefundResponse> refundPayment(String paymentId);

    CompletableFuture<RefundResponse> refundPayment(String paymentId, String idempotencyKey);

    CompletableFuture<RefundResponse> refundPayment(String paymentId, RefundRequest refundRequest);

    CompletableFuture<RefundResponse> refundPayment(String paymentId, RefundRequest refundRequest, String idempotencyKey);

    CompletableFuture<VoidResponse> voidPayment(String paymentId);

    CompletableFuture<VoidResponse> voidPayment(String paymentId, String idempotencyKey);

    CompletableFuture<VoidResponse> voidPayment(String paymentId, VoidRequest voidRequest);

    CompletableFuture<VoidResponse> voidPayment(String paymentId, VoidRequest voidRequest, String idempotencyKey);
}