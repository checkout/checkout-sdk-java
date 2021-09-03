package com.checkout.payments.links;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;

import java.util.concurrent.CompletableFuture;

public class PaymentLinksClientImpl extends AbstractClient implements PaymentLinksClient {

    private static final String PAYMENT_LINKS = "/payment-links";

    public PaymentLinksClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, SecretKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<PaymentLinkDetailsResponse> getAsync(final String reference) {
        return apiClient.getAsync(PAYMENT_LINKS + "/" + reference, apiCredentials, PaymentLinkDetailsResponse.class);
    }

    @Override
    public CompletableFuture<PaymentLinkResponse> createAsync(final PaymentLinkRequest paymentLinkRequest) {
        return apiClient.postAsync(PAYMENT_LINKS, apiCredentials, PaymentLinkResponse.class, paymentLinkRequest, null);
    }
}
