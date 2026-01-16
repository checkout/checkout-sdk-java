package com.checkout.payments.hosted;

import java.util.concurrent.CompletableFuture;

public interface HostedPaymentsClient {

    CompletableFuture<HostedPaymentResponse> createHostedPaymentsPageSession(HostedPaymentRequest hostedPaymentRequest);

    CompletableFuture<HostedPaymentDetailsResponse> getHostedPaymentsPageDetails(String hostedPaymentId);

    // Synchronous methods
    HostedPaymentResponse createHostedPaymentsPageSessionSync(HostedPaymentRequest hostedPaymentRequest);

    HostedPaymentDetailsResponse getHostedPaymentsPageDetailsSync(String hostedPaymentId);

}
