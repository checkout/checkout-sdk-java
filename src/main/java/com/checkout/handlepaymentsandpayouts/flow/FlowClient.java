package com.checkout.handlepaymentsandpayouts.flow;

import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.PaymentSessionRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.responses.PaymentSessionResponse;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.PaymentSessionWithPaymentRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.responses.PaymentSessionWithPaymentResponse;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.requests.SubmitPaymentSessionRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.responses.SubmitPaymentSessionResponse;

import java.util.concurrent.CompletableFuture;

public interface FlowClient {

    CompletableFuture<PaymentSessionResponse> requestPaymentSession(PaymentSessionRequest paymentSessionRequest);

    CompletableFuture<SubmitPaymentSessionResponse> submitPaymentSessions(
            String paymentId,
            SubmitPaymentSessionRequest submitPaymentSessionRequest
    );

    CompletableFuture<PaymentSessionWithPaymentResponse> requestPaymentSessionWithPayment(
            PaymentSessionWithPaymentRequest paymentSessionRequest
    );

    // Synchronous methods
    PaymentSessionResponse requestPaymentSessionSync(PaymentSessionRequest paymentSessionRequest);

    SubmitPaymentSessionResponse submitPaymentSessionsSync(
            String paymentId,
            SubmitPaymentSessionRequest submitPaymentSessionRequest
    );

    PaymentSessionWithPaymentResponse requestPaymentSessionWithPaymentSync(
            PaymentSessionWithPaymentRequest paymentSessionRequest
    );
}
