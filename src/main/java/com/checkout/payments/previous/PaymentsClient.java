package com.checkout.payments.previous;

import com.checkout.ItemsResponse;
import com.checkout.payments.CaptureRequest;
import com.checkout.payments.CaptureResponse;
import com.checkout.payments.RefundRequest;
import com.checkout.payments.RefundResponse;
import com.checkout.payments.VoidRequest;
import com.checkout.payments.VoidResponse;
import com.checkout.payments.previous.request.PaymentRequest;
import com.checkout.payments.previous.request.PayoutRequest;
import com.checkout.payments.previous.response.GetPaymentResponse;
import com.checkout.payments.previous.response.PaymentResponse;
import com.checkout.payments.PaymentsQueryFilter;
import com.checkout.payments.previous.response.PaymentsQueryResponse;

import java.util.concurrent.CompletableFuture;

public interface PaymentsClient {

    CompletableFuture<PaymentResponse> requestPayment(PaymentRequest paymentRequest);

    CompletableFuture<PaymentResponse> requestPayment(PaymentRequest paymentRequest, String idempotencyKey);

    CompletableFuture<PaymentResponse> requestPayout(PayoutRequest payoutRequest);

    CompletableFuture<PaymentResponse> requestPayout(PayoutRequest payoutRequest, String idempotencyKey);

    CompletableFuture<PaymentsQueryResponse> getPaymentsList(PaymentsQueryFilter queryFilter);

    CompletableFuture<GetPaymentResponse> getPayment(String paymentId);

    CompletableFuture<ItemsResponse<PaymentAction>> getPaymentActions(String paymentId);

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