package com.checkout.apm.klarna;

import com.checkout.payments.VoidRequest;
import com.checkout.payments.VoidResponse;

import java.util.concurrent.CompletableFuture;

public interface KlarnaClient {

    CompletableFuture<CreditSessionResponse> createCreditSession(CreditSessionRequest creditSessionRequest);

    CompletableFuture<CreditSession> getCreditSession(String sessionId);

    CompletableFuture<VoidResponse> capturePayment(String paymentId, OrderCaptureRequest captureRequest);

    CompletableFuture<VoidResponse> voidCapture(String paymentId, VoidRequest voidRequest);

}
