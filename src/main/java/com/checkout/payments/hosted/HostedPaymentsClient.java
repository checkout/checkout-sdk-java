package com.checkout.payments.hosted;

import java.util.concurrent.CompletableFuture;

public interface HostedPaymentsClient {

    CompletableFuture<HostedPaymentResponse> createHostedPaymentsPageSession(HostedPaymentRequest hostedPaymentRequest);

    CompletableFuture<HostedPaymentDetailsResponse> getHostedPaymentsPageDetails(String hostedPaymentId);

}
