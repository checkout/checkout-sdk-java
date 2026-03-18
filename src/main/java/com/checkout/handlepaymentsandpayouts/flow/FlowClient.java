package com.checkout.handlepaymentsandpayouts.flow;

import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionCreateRequest;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionSubmitRequest;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionCompleteRequest;
import com.checkout.handlepaymentsandpayouts.flow.responses.PaymentSessionResponse;
import com.checkout.handlepaymentsandpayouts.flow.responses.PaymentSubmissionResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Flow - Create payment sessions and submit payment attempts
 */
public interface FlowClient {

    /**
     * Request a Payment Session
     * Creates a payment session.
     * The values you provide in the request will be used to determine the payment methods available to Flow. 
     * Some payment methods may require you to provide specific values for certain fields.
     * You must supply the unmodified response body when you initialize Flow.
     */
    CompletableFuture<PaymentSessionResponse> requestPaymentSession(PaymentSessionCreateRequest request);

    /**
     * Submit a Payment Session
     * Submit a payment attempt for a payment session.
     * This request works with the Flow handleSubmit callback, where you can perform a customized payment submission.
     * You must send the unmodified response body as the response of the handleSubmit callback.
     */
    CompletableFuture<PaymentSubmissionResponse> submitPaymentSession(String sessionId, PaymentSessionSubmitRequest request);

    /**
     * Request a Payment Session with Payment
     * Create a payment session and submit a payment attempt for it.
     * The values you provide in the request will be used to determine the payment methods available to Flow.
     * This request works with the advanced Flow integration, where you do not need to create a payment session for initializing Flow.
     * You must send the unmodified response body as the response of the handleSubmit callback.
     */
    CompletableFuture<PaymentSubmissionResponse> requestPaymentSessionWithPayment(PaymentSessionCompleteRequest request);

    // Synchronous methods
    PaymentSessionResponse requestPaymentSessionSync(PaymentSessionCreateRequest request);
    
    PaymentSubmissionResponse submitPaymentSessionSync(String sessionId, PaymentSessionSubmitRequest request);
    
    PaymentSubmissionResponse requestPaymentSessionWithPaymentSync(PaymentSessionCompleteRequest request);
}
