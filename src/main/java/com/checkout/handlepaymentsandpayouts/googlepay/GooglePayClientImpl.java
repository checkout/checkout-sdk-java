package com.checkout.handlepaymentsandpayouts.googlepay;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.CheckoutUtils;
import com.checkout.handlepaymentsandpayouts.googlepay.requests.GooglePayEnrollmentRequest;
import com.checkout.handlepaymentsandpayouts.googlepay.requests.GooglePayRegisterDomainRequest;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayDomainListResponse;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayEnrollmentStateResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the Google Pay client.
 */
public class GooglePayClientImpl extends AbstractClient implements GooglePayClient {

    private static final String GOOGLEPAY_PATH = "googlepay";
    private static final String ENROLLMENTS_PATH = "enrollments";
    private static final String DOMAIN_PATH = "domain";
    private static final String DOMAINS_PATH = "domains";
    private static final String STATE_PATH = "state";

    public GooglePayClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.OAUTH);
    }

    @Override
    public CompletableFuture<EmptyResponse> enrollEntity(final GooglePayEnrollmentRequest request) {
        CheckoutUtils.validateParams("request", request);
        return apiClient.postAsync(buildPath(GOOGLEPAY_PATH, ENROLLMENTS_PATH), sdkAuthorization(), EmptyResponse.class, request, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> registerDomain(final String entityId, final GooglePayRegisterDomainRequest request) {
        CheckoutUtils.validateParams("entityId", entityId, "request", request);
        return apiClient.postAsync(buildPath(GOOGLEPAY_PATH, ENROLLMENTS_PATH, entityId, DOMAIN_PATH), sdkAuthorization(), EmptyResponse.class, request, null);
    }

    @Override
    public CompletableFuture<GooglePayDomainListResponse> getRegisteredDomains(final String entityId) {
        CheckoutUtils.validateParams("entityId", entityId);
        return apiClient.getAsync(buildPath(GOOGLEPAY_PATH, ENROLLMENTS_PATH, entityId, DOMAINS_PATH), sdkAuthorization(), GooglePayDomainListResponse.class);
    }

    @Override
    public CompletableFuture<GooglePayEnrollmentStateResponse> getEnrollmentState(final String entityId) {
        CheckoutUtils.validateParams("entityId", entityId);
        return apiClient.getAsync(buildPath(GOOGLEPAY_PATH, ENROLLMENTS_PATH, entityId, STATE_PATH), sdkAuthorization(), GooglePayEnrollmentStateResponse.class);
    }

    @Override
    public EmptyResponse enrollEntitySync(final GooglePayEnrollmentRequest request) {
        CheckoutUtils.validateParams("request", request);
        return apiClient.post(buildPath(GOOGLEPAY_PATH, ENROLLMENTS_PATH), sdkAuthorization(), EmptyResponse.class, request, null);
    }

    @Override
    public EmptyResponse registerDomainSync(final String entityId, final GooglePayRegisterDomainRequest request) {
        CheckoutUtils.validateParams("entityId", entityId, "request", request);
        return apiClient.post(buildPath(GOOGLEPAY_PATH, ENROLLMENTS_PATH, entityId, DOMAIN_PATH), sdkAuthorization(), EmptyResponse.class, request, null);
    }

    @Override
    public GooglePayDomainListResponse getRegisteredDomainsSync(final String entityId) {
        CheckoutUtils.validateParams("entityId", entityId);
        return apiClient.get(buildPath(GOOGLEPAY_PATH, ENROLLMENTS_PATH, entityId, DOMAINS_PATH), sdkAuthorization(), GooglePayDomainListResponse.class);
    }

    @Override
    public GooglePayEnrollmentStateResponse getEnrollmentStateSync(final String entityId) {
        CheckoutUtils.validateParams("entityId", entityId);
        return apiClient.get(buildPath(GOOGLEPAY_PATH, ENROLLMENTS_PATH, entityId, STATE_PATH), sdkAuthorization(), GooglePayEnrollmentStateResponse.class);
    }

}
