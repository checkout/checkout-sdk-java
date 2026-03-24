package com.checkout.compliance;

import java.util.concurrent.CompletableFuture;

/**
 * Client interface for Compliance Requests operations.
 */
public interface ComplianceClient {

    /**
     * Get a compliance request by payment ID.
     *
     * @param paymentId the payment identifier associated with the compliance request
     * @return a {@link CompletableFuture} of {@link ComplianceRequestDetails}
     */
    CompletableFuture<ComplianceRequestDetails> getComplianceRequest(String paymentId);

    /**
     * Respond to a compliance request.
     *
     * @param paymentId the payment identifier associated with the compliance request
     * @param request   the response payload
     * @return a {@link CompletableFuture} of {@link ComplianceRequestDetails}
     */
    CompletableFuture<ComplianceRequestDetails> respondToComplianceRequest(String paymentId, ComplianceRespondRequest request);

    // Synchronous methods

    /**
     * Get a compliance request by payment ID (synchronous version).
     *
     * @param paymentId the payment identifier associated with the compliance request
     * @return {@link ComplianceRequestDetails}
     */
    ComplianceRequestDetails getComplianceRequestSync(String paymentId);

    /**
     * Respond to a compliance request (synchronous version).
     *
     * @param paymentId the payment identifier associated with the compliance request
     * @param request   the response payload
     * @return {@link ComplianceRequestDetails}
     */
    ComplianceRequestDetails respondToComplianceRequestSync(String paymentId, ComplianceRespondRequest request);

}
