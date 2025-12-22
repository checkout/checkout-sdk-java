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
    public CompletableFuture<HostedPaymentResponse> createHostedPaymentsPageSession(final HostedPaymentRequest hostedPaymentRequest) {
        validateHostedPaymentRequest(hostedPaymentRequest);
        return apiClient.postAsync(HOSTED_PAYMENTS_PATH, sdkAuthorization(), HostedPaymentResponse.class, hostedPaymentRequest, null);
    }

    @Override
    public CompletableFuture<HostedPaymentDetailsResponse> getHostedPaymentsPageDetails(final String hostedPaymentId) {
        validateHostedPayment(hostedPaymentId);
        final String path = buildHostedPaymentPath(hostedPaymentId);
        return apiClient.getAsync(path, sdkAuthorization(), HostedPaymentDetailsResponse.class);
    }

    // Synchronous methods
    @Override
    public HostedPaymentResponse createHostedPaymentsPageSessionSync(final HostedPaymentRequest hostedPaymentRequest) {
        validateHostedPaymentRequest(hostedPaymentRequest);
        return apiClient.post(HOSTED_PAYMENTS_PATH, sdkAuthorization(), HostedPaymentResponse.class, hostedPaymentRequest, null);
    }

    @Override
    public HostedPaymentDetailsResponse getHostedPaymentsPageDetailsSync(final String hostedPaymentId) {
        validateHostedPayment(hostedPaymentId);
        final String path = buildHostedPaymentPath(hostedPaymentId);
        return apiClient.get(path, sdkAuthorization(), HostedPaymentDetailsResponse.class);
    }

    // Common methods
    protected void validateHostedPaymentRequest(final HostedPaymentRequest hostedPaymentRequest) {
        validateParams("hostedPaymentRequest", hostedPaymentRequest);
    }

    protected void validateHostedPayment(final String hostedPaymentId) {
        validateParams("hostedPayment", hostedPaymentId);
    }

    protected String buildHostedPaymentPath(final String hostedPaymentId) {
        return buildPath(HOSTED_PAYMENTS_PATH, hostedPaymentId);
    }
}
