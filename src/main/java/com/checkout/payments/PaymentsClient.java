package com.checkout.payments;

import com.checkout.ItemsResponse;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.UnreferencedRefundRequest;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.RequestAPaymentOrPayoutResponse;
import com.checkout.payments.request.AuthorizationRequest;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.PayoutRequest;
import com.checkout.payments.response.AuthorizationResponse;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.PaymentsQueryResponse;
import com.checkout.payments.response.PayoutResponse;

import java.util.concurrent.CompletableFuture;

public interface PaymentsClient {

    CompletableFuture<PaymentResponse> requestPayment(PaymentRequest paymentRequest);

    CompletableFuture<PaymentResponse> requestPayment(PaymentRequest paymentRequest, final String idempotencyKey);

    CompletableFuture<RequestAPaymentOrPayoutResponse> requestPayment(UnreferencedRefundRequest paymentRequest);

    CompletableFuture<RequestAPaymentOrPayoutResponse> requestPayment(UnreferencedRefundRequest paymentRequest, final String idempotencyKey);

    CompletableFuture<PayoutResponse> requestPayout(PayoutRequest payoutRequest);

    CompletableFuture<PayoutResponse> requestPayout(PayoutRequest payoutRequest, final String idempotencyKey);

    CompletableFuture<PaymentsQueryResponse> getPaymentsList(PaymentsQueryFilter queryFilter);

    CompletableFuture<GetPaymentResponse> getPayment(String paymentId);

    CompletableFuture<ItemsResponse<PaymentAction>> getPaymentActions(String paymentId);

    CompletableFuture<AuthorizationResponse> incrementPaymentAuthorization(String paymentId, AuthorizationRequest authorizationRequest);

    CompletableFuture<AuthorizationResponse> incrementPaymentAuthorization(String paymentId, AuthorizationRequest authorizationRequest, String idempotencyKey);

    CompletableFuture<CaptureResponse> capturePayment(String paymentId);

    CompletableFuture<CaptureResponse> capturePayment(String paymentId, String idempotencyKey);

    CompletableFuture<CaptureResponse> capturePayment(String paymentId, CaptureRequest captureRequest);

    CompletableFuture<CaptureResponse> capturePayment(String paymentId, CaptureRequest captureRequest, String idempotencyKey);

    CompletableFuture<RefundResponse> refundPayment(String paymentId);

    CompletableFuture<RefundResponse> refundPayment(String paymentId, String idempotencyKey);

    CompletableFuture<RefundResponse> refundPayment(String paymentId, RefundRequest refundRequest);

    CompletableFuture<RefundResponse> refundPayment(String paymentId, RefundRequest refundRequest, String idempotencyKey);

    CompletableFuture<ReverseResponse> reversePayment(String paymentId);

    CompletableFuture<ReverseResponse> reversePayment(String paymentId, String idempotencyKey);

    CompletableFuture<ReverseResponse> reversePayment(String paymentId, ReverseRequest reverseRequest);

    CompletableFuture<ReverseResponse> reversePayment(String paymentId, ReverseRequest reverseRequest, String idempotencyKey);

    CompletableFuture<VoidResponse> voidPayment(String paymentId);

    CompletableFuture<VoidResponse> voidPayment(String paymentId, String idempotencyKey);

    CompletableFuture<VoidResponse> voidPayment(String paymentId, VoidRequest voidRequest);

    CompletableFuture<VoidResponse> voidPayment(String paymentId, VoidRequest voidRequest, String idempotencyKey);

}