package com.checkout.payments.contexts;

import java.util.concurrent.CompletableFuture;

public interface PaymentContextsClient {

    CompletableFuture<PaymentContextsRequestResponse> requestPaymentContexts(PaymentContextsRequest paymentContextsRequest);

    CompletableFuture<PaymentContextDetailsResponse> getPaymentContextDetails(String paymentContextId);

    // Synchronous methods
    PaymentContextsRequestResponse requestPaymentContextsSync(PaymentContextsRequest paymentContextsRequest);

    PaymentContextDetailsResponse getPaymentContextDetailsSync(String paymentContextId);

}
