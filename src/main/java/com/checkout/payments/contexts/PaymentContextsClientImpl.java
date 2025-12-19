package com.checkout.payments.contexts;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class PaymentContextsClientImpl extends AbstractClient implements PaymentContextsClient {

    private static final String PAYMENT_CONTEXTS_PATH = "payment-contexts";

    public PaymentContextsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<PaymentContextsRequestResponse> requestPaymentContexts(final PaymentContextsRequest paymentContextsRequest) {
        prepareRequestPaymentContexts(paymentContextsRequest);
        return apiClient.postAsync(PAYMENT_CONTEXTS_PATH, sdkAuthorization(), PaymentContextsRequestResponse.class, paymentContextsRequest, null);
    }

    @Override
    public CompletableFuture<PaymentContextDetailsResponse> getPaymentContextDetails(final String paymentContextId) {
        final String path = prepareGetPaymentContextDetails(paymentContextId);
        return apiClient.getAsync(path, sdkAuthorization(), PaymentContextDetailsResponse.class);
    }

    // Synchronous methods
    @Override
    public PaymentContextsRequestResponse requestPaymentContextsSync(final PaymentContextsRequest paymentContextsRequest) {
        prepareRequestPaymentContexts(paymentContextsRequest);
        return apiClient.post(PAYMENT_CONTEXTS_PATH, sdkAuthorization(), PaymentContextsRequestResponse.class, paymentContextsRequest, null);
    }

    @Override
    public PaymentContextDetailsResponse getPaymentContextDetailsSync(final String paymentContextId) {
        final String path = prepareGetPaymentContextDetails(paymentContextId);
        return apiClient.get(path, sdkAuthorization(), PaymentContextDetailsResponse.class);
    }

    // Common methods
    protected void prepareRequestPaymentContexts(final PaymentContextsRequest paymentContextsRequest) {
        validateParams("paymentContextsRequest", paymentContextsRequest);
    }

    protected String prepareGetPaymentContextDetails(final String paymentContextId) {
        validateParams("paymentContextId", paymentContextId);
        return buildPath(PAYMENT_CONTEXTS_PATH, paymentContextId);
    }
}
