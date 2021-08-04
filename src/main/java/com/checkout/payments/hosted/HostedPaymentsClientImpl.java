package com.checkout.payments.hosted;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.payments.AbstractClient;

import java.util.concurrent.CompletableFuture;

public class HostedPaymentsClientImpl extends AbstractClient implements HostedPaymentsClient {

    public static final String HOSTED_PAYMENTS = "/hosted-payments";

    public HostedPaymentsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration);
    }

    @Override
    public CompletableFuture<HostedPaymentResponse> createAsync(final HostedPaymentRequest hostedPaymentRequest) {
        return apiClient.postAsync(HOSTED_PAYMENTS, credentials, HostedPaymentResponse.class, hostedPaymentRequest, null);
    }
}
