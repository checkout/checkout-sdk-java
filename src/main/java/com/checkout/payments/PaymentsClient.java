package com.checkout.payments;

import java.util.concurrent.CompletableFuture;

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

    // Synchronous methods
    PaymentResponse requestPaymentSync(PaymentRequest paymentRequest);

    PaymentResponse requestPaymentSync(PaymentRequest paymentRequest, String idempotencyKey);

    RequestAPaymentOrPayoutResponse requestPaymentSync(UnreferencedRefundRequest paymentRequest);

    RequestAPaymentOrPayoutResponse requestPaymentSync(UnreferencedRefundRequest paymentRequest, String idempotencyKey);

    PayoutResponse requestPayoutSync(PayoutRequest payoutRequest);

    PayoutResponse requestPayoutSync(PayoutRequest payoutRequest, String idempotencyKey);

    PaymentsQueryResponse getPaymentsListSync(PaymentsQueryFilter queryFilter);

    GetPaymentResponse getPaymentSync(String paymentId);

    ItemsResponse<PaymentAction> getPaymentActionsSync(String paymentId);

    AuthorizationResponse incrementPaymentAuthorizationSync(String paymentId, AuthorizationRequest authorizationRequest);

    AuthorizationResponse incrementPaymentAuthorizationSync(String paymentId, AuthorizationRequest authorizationRequest, String idempotencyKey);

    CaptureResponse capturePaymentSync(String paymentId);

    CaptureResponse capturePaymentSync(String paymentId, String idempotencyKey);

    CaptureResponse capturePaymentSync(String paymentId, CaptureRequest captureRequest);

    CaptureResponse capturePaymentSync(String paymentId, CaptureRequest captureRequest, String idempotencyKey);

    RefundResponse refundPaymentSync(String paymentId);

    RefundResponse refundPaymentSync(String paymentId, String idempotencyKey);

    RefundResponse refundPaymentSync(String paymentId, RefundRequest refundRequest);

    RefundResponse refundPaymentSync(String paymentId, RefundRequest refundRequest, String idempotencyKey);

    ReverseResponse reversePaymentSync(String paymentId);

    ReverseResponse reversePaymentSync(String paymentId, String idempotencyKey);

    ReverseResponse reversePaymentSync(String paymentId, ReverseRequest reverseRequest);

    ReverseResponse reversePaymentSync(String paymentId, ReverseRequest reverseRequest, String idempotencyKey);

    VoidResponse voidPaymentSync(String paymentId);

    VoidResponse voidPaymentSync(String paymentId, String idempotencyKey);

    VoidResponse voidPaymentSync(String paymentId, VoidRequest voidRequest);

    VoidResponse voidPaymentSync(String paymentId, VoidRequest voidRequest, String idempotencyKey);

}