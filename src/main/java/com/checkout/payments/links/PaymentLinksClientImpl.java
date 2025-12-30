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
        validateReference(reference);
        return apiClient.getAsync(buildPath(PAYMENT_LINKS_PATH, reference), sdkAuthorization(), PaymentLinkDetailsResponse.class);
    }

    @Override
    public CompletableFuture<PaymentLinkResponse> createPaymentLink(final PaymentLinkRequest paymentLinkRequest) {
        validatePaymentLinkRequest(paymentLinkRequest);
        return apiClient.postAsync(PAYMENT_LINKS_PATH, sdkAuthorization(), PaymentLinkResponse.class, paymentLinkRequest, null);
    }

    // Synchronous methods
    @Override
    public PaymentLinkDetailsResponse getPaymentLinkSync(final String reference) {
        validateReference(reference);
        return apiClient.get(buildPath(PAYMENT_LINKS_PATH, reference), sdkAuthorization(), PaymentLinkDetailsResponse.class);
    }

    @Override
    public PaymentLinkResponse createPaymentLinkSync(final PaymentLinkRequest paymentLinkRequest) {
        validatePaymentLinkRequest(paymentLinkRequest);
        return apiClient.post(PAYMENT_LINKS_PATH, sdkAuthorization(), PaymentLinkResponse.class, paymentLinkRequest, null);
    }

    // Common methods
    protected void validateReference(final String reference) {
        validateParams("reference", reference);
    }

    protected void validatePaymentLinkRequest(final PaymentLinkRequest paymentLinkRequest) {
        validateParams("paymentLinkRequest", paymentLinkRequest);
    }
}
