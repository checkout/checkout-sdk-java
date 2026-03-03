package com.checkout.identities.amlscreening;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.identities.amlscreening.requests.AmlScreeningRequest;
import com.checkout.identities.amlscreening.responses.AmlScreeningResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

/**
 * Implementation of the AML Screening client.
 */
public class AmlScreeningClientImpl extends AbstractClient implements AmlScreeningClient {

    private static final String AML_VERIFICATIONS_PATH = "aml-verifications";

    public AmlScreeningClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    // Async methods

    /**
     * Create an AML screening
     *
     * @param amlScreeningRequest the AML screening request
     * @return a {@link CompletableFuture} containing the {@link AmlScreeningResponse}
     */
    @Override
    public CompletableFuture<AmlScreeningResponse> createAmlScreeningAsync(
            final AmlScreeningRequest amlScreeningRequest) {
        validateParams("amlScreeningRequest", amlScreeningRequest);
        return apiClient.postAsync(AML_VERIFICATIONS_PATH, sdkAuthorization(), AmlScreeningResponse.class,
                amlScreeningRequest, null);
    }

    /**
     * Retrieve an AML screening
     *
     * @param amlScreeningId the AML screening ID
     * @return a {@link CompletableFuture} containing the {@link AmlScreeningResponse}
     */
    @Override
    public CompletableFuture<AmlScreeningResponse> getAmlScreeningAsync(
            final String amlScreeningId) {
        validateParams("amlScreeningId", amlScreeningId);
        return apiClient.getAsync(buildPath(AML_VERIFICATIONS_PATH, amlScreeningId), sdkAuthorization(),
                AmlScreeningResponse.class);
    }

    // Sync methods

    /**
     * Create an AML screening
     *
     * @param amlScreeningRequest the AML screening request
     * @return a {@link AmlScreeningResponse}
     */
    @Override
    public AmlScreeningResponse createAmlScreening(final AmlScreeningRequest amlScreeningRequest) {
        validateParams("amlScreeningRequest", amlScreeningRequest);
        return apiClient.post(AML_VERIFICATIONS_PATH, sdkAuthorization(), AmlScreeningResponse.class,
                amlScreeningRequest, null);
    }

    /**
     * Retrieve an AML screening
     *
     * @param amlScreeningId the AML screening ID
     * @return a {@link AmlScreeningResponse}
     */
    @Override
    public AmlScreeningResponse getAmlScreening(final String amlScreeningId) {
        validateParams("amlScreeningId", amlScreeningId);
        return apiClient.get(buildPath(AML_VERIFICATIONS_PATH, amlScreeningId), sdkAuthorization(),
                AmlScreeningResponse.class);
    }

}