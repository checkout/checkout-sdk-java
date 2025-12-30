package com.checkout.payments.links;

import java.util.concurrent.CompletableFuture;

public interface PaymentLinksClient {

    CompletableFuture<PaymentLinkDetailsResponse> getPaymentLink(String reference);

    CompletableFuture<PaymentLinkResponse> createPaymentLink(PaymentLinkRequest paymentLinkRequest);

    // Synchronous methods
    PaymentLinkDetailsResponse getPaymentLinkSync(String reference);

    PaymentLinkResponse createPaymentLinkSync(PaymentLinkRequest paymentLinkRequest);
}
