package com.checkout.compliance;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.CheckoutUtils;

import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the Compliance Requests client.
 */
public class ComplianceClientImpl extends AbstractClient implements ComplianceClient {

    private static final String COMPLIANCE_REQUESTS_PATH = "compliance-requests";

    public ComplianceClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<ComplianceRequestDetails> getComplianceRequest(final String paymentId) {
        CheckoutUtils.validateParams("paymentId", paymentId);
        return apiClient.getAsync(buildPath(COMPLIANCE_REQUESTS_PATH, paymentId), sdkAuthorization(), ComplianceRequestDetails.class);
    }

    @Override
    public CompletableFuture<ComplianceRequestDetails> respondToComplianceRequest(final String paymentId, final ComplianceRespondRequest request) {
        CheckoutUtils.validateParams("paymentId", paymentId, "request", request);
        return apiClient.postAsync(buildPath(COMPLIANCE_REQUESTS_PATH, paymentId), sdkAuthorization(), ComplianceRequestDetails.class, request, null);
    }

    @Override
    public ComplianceRequestDetails getComplianceRequestSync(final String paymentId) {
        CheckoutUtils.validateParams("paymentId", paymentId);
        return apiClient.get(buildPath(COMPLIANCE_REQUESTS_PATH, paymentId), sdkAuthorization(), ComplianceRequestDetails.class);
    }

    @Override
    public ComplianceRequestDetails respondToComplianceRequestSync(final String paymentId, final ComplianceRespondRequest request) {
        CheckoutUtils.validateParams("paymentId", paymentId, "request", request);
        return apiClient.post(buildPath(COMPLIANCE_REQUESTS_PATH, paymentId), sdkAuthorization(), ComplianceRequestDetails.class, request, null);
    }

}
