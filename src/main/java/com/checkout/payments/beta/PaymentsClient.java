package com.checkout.payments.beta;

import com.checkout.payments.beta.action.PaymentAction;
import com.checkout.payments.beta.capture.CaptureRequest;
import com.checkout.payments.beta.capture.CaptureResponse;
import com.checkout.payments.beta.payout.PayoutRequest;
import com.checkout.payments.beta.payout.PayoutResponse;
import com.checkout.payments.beta.refund.RefundRequest;
import com.checkout.payments.beta.refund.RefundResponse;
import com.checkout.payments.beta.request.PaymentRequest;
import com.checkout.payments.beta.response.PaymentResponse;
import com.checkout.payments.beta.response.source.ResponseSource;
import com.checkout.payments.beta.voids.VoidRequest;
import com.checkout.payments.beta.voids.VoidResponse;

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