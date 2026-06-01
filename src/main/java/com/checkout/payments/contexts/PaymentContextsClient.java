package com.checkout.payments.contexts;

import java.util.concurrent.CompletableFuture;

public interface PaymentContextsClient {

    CompletableFuture<PaymentContextsRequestResponse> requestPaymentContexts(PaymentContextsRequest paymentContextsRequest, String idempotencyKey);

    CompletableFuture<PaymentContextDetailsResponse> getPaymentContextDetails(String paymentContextId);

    // Synchronous methods
    PaymentContextsRequestResponse requestPaymentContextsSync(PaymentContextsRequest paymentContextsRequest, String idempotencyKey);

    PaymentContextDetailsResponse getPaymentContextDetailsSync(String paymentContextId);

}
