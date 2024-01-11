package com.checkout.payments.sessions;

import java.util.concurrent.CompletableFuture;

public interface PaymentSessionsClient {

    CompletableFuture<PaymentSessionsResponse> requestPaymentSessions(PaymentSessionsRequest paymentSessionsRequest);

}
