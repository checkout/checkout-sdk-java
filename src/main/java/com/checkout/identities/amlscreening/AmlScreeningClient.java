package com.checkout.identities.amlscreening;

import com.checkout.identities.amlscreening.requests.AmlScreeningRequest;
import com.checkout.identities.amlscreening.responses.AmlScreeningResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Defines the operations available on the Identities AML Screening API
 */
public interface AmlScreeningClient {

    // Async methods

    /**
     * Create an AML screening
     *
     * @param amlScreeningRequest the {@link AmlScreeningRequest}
     * @return a {@link CompletableFuture} of {@link AmlScreeningResponse}
     */
    CompletableFuture<AmlScreeningResponse> createAmlScreeningAsync(AmlScreeningRequest amlScreeningRequest);

    /**
     * Get an AML screening
     *
     * @param amlScreeningId the AML screening's unique identifier
     * @return a {@link CompletableFuture} of {@link AmlScreeningResponse}
     */
    CompletableFuture<AmlScreeningResponse> getAmlScreeningAsync(String amlScreeningId);

    // Sync methods

    /**
     * Create an AML screening
     *
     * @param amlScreeningRequest the {@link AmlScreeningRequest}
     * @return a {@link AmlScreeningResponse}
     */
    AmlScreeningResponse createAmlScreening(AmlScreeningRequest amlScreeningRequest);

    /**
     * Get an AML screening
     *
     * @param amlScreeningId the AML screening's unique identifier
     * @return a {@link AmlScreeningResponse}
     */
    AmlScreeningResponse getAmlScreening(String amlScreeningId);

}