package com.checkout.payments.hosted;

import java.util.concurrent.CompletableFuture;

public interface HostedPaymentsClient {

    CompletableFuture<HostedPaymentResponse> createAsync(HostedPaymentRequest hostedPaymentRequest);

    CompletableFuture<HostedPaymentDetailsResponse> get(String hostedPaymentId);

}
