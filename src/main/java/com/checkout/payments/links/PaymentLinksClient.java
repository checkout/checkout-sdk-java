package com.checkout.payments.links;

import java.util.concurrent.CompletableFuture;

public interface PaymentLinksClient {

    CompletableFuture<PaymentLinkDetailsResponse> getPaymentLink(String reference);

    CompletableFuture<PaymentLinkResponse> createPaymentLink(PaymentLinkRequest paymentLinkRequest);
}
