package com.checkout.payments.hosted;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class HostedPaymentsClientImpl extends AbstractClient implements HostedPaymentsClient {

    private static final String HOSTED_PAYMENTS_PATH = "hosted-payments";

    public HostedPaymentsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<HostedPaymentResponse> createAsync(final HostedPaymentRequest hostedPaymentRequest) {
        validateParams("hostedPaymentRequest", hostedPaymentRequest);
        return apiClient.postAsync(HOSTED_PAYMENTS_PATH, sdkAuthorization(), HostedPaymentResponse.class, hostedPaymentRequest, null);
    }

    @Override
    public CompletableFuture<HostedPaymentDetailsResponse> get(final String hostedPaymentId) {
        validateParams("hostedPayment", hostedPaymentId);
        return apiClient.getAsync(buildPath(HOSTED_PAYMENTS_PATH, hostedPaymentId), sdkAuthorization(), HostedPaymentDetailsResponse.class);
    }
}
