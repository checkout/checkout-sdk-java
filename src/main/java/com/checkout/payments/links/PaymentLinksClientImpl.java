package com.checkout.payments.links;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class PaymentLinksClientImpl extends AbstractClient implements PaymentLinksClient {

    private static final String PAYMENT_LINKS_PATH = "payment-links";

    public PaymentLinksClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<PaymentLinkDetailsResponse> getPaymentLink(final String reference) {
        validateParams("reference", reference);
        return apiClient.getAsync(buildPath(PAYMENT_LINKS_PATH, reference), sdkAuthorization(), PaymentLinkDetailsResponse.class);
    }

    @Override
    public CompletableFuture<PaymentLinkResponse> createPaymentLink(final PaymentLinkRequest paymentLinkRequest) {
        validateParams("paymentLinkRequest", paymentLinkRequest);
        return apiClient.postAsync(PAYMENT_LINKS_PATH, sdkAuthorization(), PaymentLinkResponse.class, paymentLinkRequest, null);
    }
}
