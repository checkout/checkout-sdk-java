package com.checkout.handlepaymentsandpayouts.googlepay;

import com.checkout.EmptyResponse;
import com.checkout.handlepaymentsandpayouts.googlepay.requests.GooglePayEnrollmentRequest;
import com.checkout.handlepaymentsandpayouts.googlepay.requests.GooglePayRegisterDomainRequest;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayDomainListResponse;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayEnrollmentStateResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Client interface for Google Pay operations.
 */
public interface GooglePayClient {

    /**
     * Enroll an entity to the Google Pay Service.
     *
     * @param request the enrollment request containing entity ID, email, and terms acceptance
     * @return a {@link CompletableFuture} of {@link EmptyResponse}
     */
    CompletableFuture<EmptyResponse> enrollEntity(GooglePayEnrollmentRequest request);

    /**
     * Register a web domain for a Google Pay enrolled entity.
     *
     * @param entityId the unique identifier of the enrolled entity
     * @param request  the domain registration request
     * @return a {@link CompletableFuture} of {@link EmptyResponse}
     */
    CompletableFuture<EmptyResponse> registerDomain(String entityId, GooglePayRegisterDomainRequest request);

    /**
     * Get registered domains for a Google Pay enrolled entity.
     *
     * @param entityId the unique identifier of the enrolled entity
     * @return a {@link CompletableFuture} of {@link GooglePayDomainListResponse}
     */
    CompletableFuture<GooglePayDomainListResponse> getRegisteredDomains(String entityId);

    /**
     * Get the enrollment state of a Google Pay entity.
     *
     * @param entityId the unique identifier of the enrolled entity
     * @return a {@link CompletableFuture} of {@link GooglePayEnrollmentStateResponse}
     */
    CompletableFuture<GooglePayEnrollmentStateResponse> getEnrollmentState(String entityId);

    // Synchronous methods

    /**
     * Enroll an entity to the Google Pay Service (synchronous version).
     *
     * @param request the enrollment request
     * @return {@link EmptyResponse}
     */
    EmptyResponse enrollEntitySync(GooglePayEnrollmentRequest request);

    /**
     * Register a web domain for a Google Pay enrolled entity (synchronous version).
     *
     * @param entityId the unique identifier of the enrolled entity
     * @param request  the domain registration request
     * @return {@link EmptyResponse}
     */
    EmptyResponse registerDomainSync(String entityId, GooglePayRegisterDomainRequest request);

    /**
     * Get registered domains for a Google Pay enrolled entity (synchronous version).
     *
     * @param entityId the unique identifier of the enrolled entity
     * @return {@link GooglePayDomainListResponse}
     */
    GooglePayDomainListResponse getRegisteredDomainsSync(String entityId);

    /**
     * Get the enrollment state of a Google Pay entity (synchronous version).
     *
     * @param entityId the unique identifier of the enrolled entity
     * @return {@link GooglePayEnrollmentStateResponse}
     */
    GooglePayEnrollmentStateResponse getEnrollmentStateSync(String entityId);

}
