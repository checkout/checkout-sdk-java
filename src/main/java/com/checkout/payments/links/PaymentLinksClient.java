package com.checkout.payments.links;

import java.util.concurrent.CompletableFuture;

public interface PaymentLinksClient {
    CompletableFuture<PaymentLinkDetailsResponse> getAsync(String reference);

    CompletableFuture<PaymentLinkResponse> createAsync(PaymentLinkRequest paymentLinkRequest);
}
