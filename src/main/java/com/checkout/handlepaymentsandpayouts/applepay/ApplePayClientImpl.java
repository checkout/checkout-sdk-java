package com.checkout.handlepaymentsandpayouts.applepay;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.CheckoutUtils;
import com.checkout.handlepaymentsandpayouts.applepay.requests.EnrollDomainRequest;
import com.checkout.handlepaymentsandpayouts.applepay.requests.GenerateSigningRequestRequest;
import com.checkout.handlepaymentsandpayouts.applepay.requests.UploadCertificateRequest;
import com.checkout.handlepaymentsandpayouts.applepay.responses.GenerateSigningRequestResponse;
import com.checkout.handlepaymentsandpayouts.applepay.responses.UploadCertificateResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the Apple Pay client.
 */
public class ApplePayClientImpl extends AbstractClient implements ApplePayClient {

    private static final String APPLEPAY_PATH = "applepay";
    private static final String CERTIFICATES_PATH = "certificates";
    private static final String ENROLLMENTS_PATH = "enrollments";
    private static final String SIGNING_REQUESTS_PATH = "signing-requests";

    public ApplePayClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<UploadCertificateResponse> uploadPaymentProcessingCertificate(
            final UploadCertificateRequest uploadCertificateRequest) {
        validateUploadCertificateRequest(uploadCertificateRequest);
        return apiClient.postAsync(buildPath(APPLEPAY_PATH, CERTIFICATES_PATH), sdkAuthorization(), 
                UploadCertificateResponse.class, uploadCertificateRequest, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> enrollDomain(final EnrollDomainRequest enrollDomainRequest) {
        validateEnrollDomainRequest(enrollDomainRequest);
        return apiClient.postAsync(buildPath(APPLEPAY_PATH, ENROLLMENTS_PATH), sdkAuthorization(SdkAuthorizationType.OAUTH), 
                EmptyResponse.class, enrollDomainRequest, null);
    }

    @Override
    public CompletableFuture<GenerateSigningRequestResponse> generateCertificateSigningRequest(
            final GenerateSigningRequestRequest generateSigningRequestRequest) {
        validateGenerateSigningRequestRequest(generateSigningRequestRequest);
        return apiClient.postAsync(buildPath(APPLEPAY_PATH, SIGNING_REQUESTS_PATH), sdkAuthorization(), 
                GenerateSigningRequestResponse.class, generateSigningRequestRequest, null);
    }

    // Synchronous methods
    @Override
    public UploadCertificateResponse uploadPaymentProcessingCertificateSync(
            final UploadCertificateRequest uploadCertificateRequest) {
        validateUploadCertificateRequest(uploadCertificateRequest);
        return apiClient.post(buildPath(APPLEPAY_PATH, CERTIFICATES_PATH), sdkAuthorization(), 
                UploadCertificateResponse.class, uploadCertificateRequest, null);
    }

    @Override
    public EmptyResponse enrollDomainSync(final EnrollDomainRequest enrollDomainRequest) {
        validateEnrollDomainRequest(enrollDomainRequest);
        return apiClient.post(buildPath(APPLEPAY_PATH, ENROLLMENTS_PATH), sdkAuthorization(SdkAuthorizationType.OAUTH), 
                EmptyResponse.class, enrollDomainRequest, null);
    }

    @Override
    public GenerateSigningRequestResponse generateCertificateSigningRequestSync(
            final GenerateSigningRequestRequest generateSigningRequestRequest) {
        validateGenerateSigningRequestRequest(generateSigningRequestRequest);
        return apiClient.post(buildPath(APPLEPAY_PATH, SIGNING_REQUESTS_PATH), sdkAuthorization(), 
                GenerateSigningRequestResponse.class, generateSigningRequestRequest, null);
    }

    // Common methods
    private void validateUploadCertificateRequest(final UploadCertificateRequest request) {
        CheckoutUtils.validateParams("uploadCertificateRequest", request);
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("Certificate content cannot be null or empty");
        }
    }

    private void validateEnrollDomainRequest(final EnrollDomainRequest request) {
        CheckoutUtils.validateParams("enrollDomainRequest", request);
        if (request.getDomain() == null || request.getDomain().trim().isEmpty()) {
            throw new IllegalArgumentException("Domain cannot be null or empty");
        }
    }

    private void validateGenerateSigningRequestRequest(final GenerateSigningRequestRequest request) {
        CheckoutUtils.validateParams("generateSigningRequestRequest", request);
        // protocolVersion has a default value, so no null check needed
    }

}