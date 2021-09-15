package com.checkout.payments.links;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

public class PaymentLinksClientImpl extends AbstractClient implements PaymentLinksClient {

    private static final String PAYMENT_LINKS = "/payment-links";

    public PaymentLinksClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<PaymentLinkDetailsResponse> getAsync(final String reference) {
        return apiClient.getAsync(PAYMENT_LINKS + "/" + reference, sdkAuthorization(), PaymentLinkDetailsResponse.class);
    }

    @Override
    public CompletableFuture<PaymentLinkResponse> createAsync(final PaymentLinkRequest paymentLinkRequest) {
        return apiClient.postAsync(PAYMENT_LINKS, sdkAuthorization(), PaymentLinkResponse.class, paymentLinkRequest, null);
    }
}
