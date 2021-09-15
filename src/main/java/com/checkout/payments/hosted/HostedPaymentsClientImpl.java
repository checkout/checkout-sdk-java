package com.checkout.payments.hosted;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

public class HostedPaymentsClientImpl extends AbstractClient implements HostedPaymentsClient {

    private static final String HOSTED_PAYMENTS = "/hosted-payments";

    public HostedPaymentsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<HostedPaymentResponse> createAsync(final HostedPaymentRequest hostedPaymentRequest) {
        return apiClient.postAsync(HOSTED_PAYMENTS, sdkAuthorization(), HostedPaymentResponse.class, hostedPaymentRequest, null);
    }
}
