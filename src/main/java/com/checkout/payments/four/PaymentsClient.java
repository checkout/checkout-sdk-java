package com.checkout.payments.four;

import com.checkout.payments.four.action.PaymentAction;
import com.checkout.payments.four.capture.CaptureRequest;
import com.checkout.payments.four.capture.CaptureResponse;
import com.checkout.payments.four.payout.PayoutRequest;
import com.checkout.payments.four.payout.PayoutResponse;
import com.checkout.payments.four.refund.RefundRequest;
import com.checkout.payments.four.refund.RefundResponse;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.payments.four.response.source.ResponseSource;
import com.checkout.payments.four.voids.VoidRequest;
import com.checkout.payments.four.voids.VoidResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PaymentsClient {

    <P extends ResponseSource> CompletableFuture<PaymentResponse<P>> requestPayment(PaymentRequest paymentRequest);

    <P extends ResponseSource> CompletableFuture<PaymentResponse<P>> requestPayment(PaymentRequest paymentRequest, final String idempotencyKey);

    CompletableFuture<PayoutResponse> requestPayout(PayoutRequest payoutRequest);

    CompletableFuture<PayoutResponse> requestPayout(PayoutRequest payoutRequest, final String idempotencyKey);

    <P extends ResponseSource> CompletableFuture<PaymentResponse<P>> getPayment(String paymentId);

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