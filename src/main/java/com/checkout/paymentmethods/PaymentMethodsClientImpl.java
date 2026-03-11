package com.checkout.paymentmethods;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.CheckoutUtils;
import com.checkout.paymentmethods.requests.PaymentMethodsQuery;
import com.checkout.paymentmethods.responses.PaymentMethodsResponse;

import java.util.concurrent.CompletableFuture;

public class PaymentMethodsClientImpl extends AbstractClient implements PaymentMethodsClient {

    private static final String PAYMENT_METHODS_PATH = "payment-methods";

    public PaymentMethodsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<PaymentMethodsResponse> getPaymentMethods(final PaymentMethodsQuery paymentMethodsQuery) {
        validatePaymentMethodsQuery(paymentMethodsQuery);
        return apiClient.queryAsync(PAYMENT_METHODS_PATH, sdkAuthorization(), paymentMethodsQuery, PaymentMethodsResponse.class);
    }

    // Synchronous methods
    @Override
    public PaymentMethodsResponse getPaymentMethodsSync(final PaymentMethodsQuery paymentMethodsQuery) {
        validatePaymentMethodsQuery(paymentMethodsQuery);
        return apiClient.query(PAYMENT_METHODS_PATH, sdkAuthorization(), paymentMethodsQuery, PaymentMethodsResponse.class);
    }

    // Common methods
    private void validatePaymentMethodsQuery(final PaymentMethodsQuery paymentMethodsQuery) {
        CheckoutUtils.validateParams("paymentMethodsQuery", paymentMethodsQuery);
        CheckoutUtils.validateParams("processingChannelId", paymentMethodsQuery.getProcessingChannelId());
        
        if (!paymentMethodsQuery.getProcessingChannelId().matches("^(pc)_(\\w{26})$")) {
            throw new IllegalArgumentException("processingChannelId must match pattern ^(pc)_(\\w{26})$");
        }
    }
}