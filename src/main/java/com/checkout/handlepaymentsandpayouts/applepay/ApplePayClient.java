package com.checkout.handlepaymentsandpayouts.applepay;

import com.checkout.EmptyResponse;
import com.checkout.handlepaymentsandpayouts.applepay.requests.EnrollDomainRequest;
import com.checkout.handlepaymentsandpayouts.applepay.requests.GenerateSigningRequestRequest;
import com.checkout.handlepaymentsandpayouts.applepay.requests.UploadCertificateRequest;
import com.checkout.handlepaymentsandpayouts.applepay.responses.GenerateSigningRequestResponse;
import com.checkout.handlepaymentsandpayouts.applepay.responses.UploadCertificateResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Client interface for Apple Pay operations.
 */
public interface ApplePayClient {

    /**
     * Upload a payment processing certificate.
     * Upload a payment processing certificate. This will allow you to start processing payments via Apple Pay.
     *
     * @param uploadCertificateRequest The upload certificate request
     * @return CompletableFuture containing the upload certificate response
     */
    CompletableFuture<UploadCertificateResponse> uploadPaymentProcessingCertificate(UploadCertificateRequest uploadCertificateRequest);

    /**
     * Enroll a domain to the Apple Pay Service.
     * Enroll a domain to the Apple Pay Service.
     *
     * @param enrollDomainRequest The enroll domain request
     * @return CompletableFuture containing the empty response
     */
    CompletableFuture<EmptyResponse> enrollDomain(EnrollDomainRequest enrollDomainRequest);

    /**
     * Generate a certificate signing request.
     * Generate a certificate signing request. You'll need to upload this to your Apple Developer account to download a payment processing certificate.
     *
     * @param generateSigningRequestRequest The generate signing request
     * @return CompletableFuture containing the generate signing request response
     */
    CompletableFuture<GenerateSigningRequestResponse> generateCertificateSigningRequest(GenerateSigningRequestRequest generateSigningRequestRequest);

    // Synchronous methods
    /**
     * Upload a payment processing certificate (synchronous).
     * Upload a payment processing certificate. This will allow you to start processing payments via Apple Pay.
     *
     * @param uploadCertificateRequest The upload certificate request
     * @return The upload certificate response
     */
    UploadCertificateResponse uploadPaymentProcessingCertificateSync(UploadCertificateRequest uploadCertificateRequest);

    /**
     * Enroll a domain to the Apple Pay Service (synchronous).
     * Enroll a domain to the Apple Pay Service.
     *
     * @param enrollDomainRequest The enroll domain request
     * @return The empty response
     */
    EmptyResponse enrollDomainSync(EnrollDomainRequest enrollDomainRequest);

    /**
     * Generate a certificate signing request (synchronous).
     * Generate a certificate signing request. You'll need to upload this to your Apple Developer account to download a payment processing certificate.
     *
     * @param generateSigningRequestRequest The generate signing request
     * @return The generate signing request response
     */
    GenerateSigningRequestResponse generateCertificateSigningRequestSync(GenerateSigningRequestRequest generateSigningRequestRequest);

}