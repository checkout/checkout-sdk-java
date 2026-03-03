package com.checkout.identities.applicants;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.identities.applicants.requests.ApplicantRequest;
import com.checkout.identities.applicants.requests.ApplicantUpdateRequest;
import com.checkout.identities.applicants.responses.ApplicantResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

/**
 * Implementation of the Applicant client.
 */
public class ApplicantClientImpl extends AbstractClient implements ApplicantClient {

    private static final String APPLICANTS_PATH = "applicants";
    private static final String ANONYMIZE_PATH = "anonymize";

    public ApplicantClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    /**
     * Creates a new applicant profile.
     *
     * @param applicantRequest The applicant request
     * @return CompletableFuture containing the applicant response
     */
    @Override
    public CompletableFuture<ApplicantResponse> createApplicant(final ApplicantRequest applicantRequest) {
        validateParams("applicantRequest", applicantRequest);
        return apiClient.postAsync(APPLICANTS_PATH, sdkAuthorization(), ApplicantResponse.class,
                applicantRequest, null);
    }

    /**
     * Retrieves an applicant by ID.
     *
     * @param applicantId The applicant ID
     * @return CompletableFuture containing the applicant response
     */
    @Override
    public CompletableFuture<ApplicantResponse> getApplicant(final String applicantId) {
        validateParams("applicantId", applicantId);
        return apiClient.getAsync(buildPath(APPLICANTS_PATH, applicantId), sdkAuthorization(), 
                ApplicantResponse.class);
    }

    /**
     * Updates an existing applicant profile.
     *
     * @param applicantId The applicant ID
     * @param applicantUpdateRequest The applicant update request
     * @return CompletableFuture containing the applicant response
     */
    @Override
    public CompletableFuture<ApplicantResponse> updateApplicant(final String applicantId, 
            final ApplicantUpdateRequest applicantUpdateRequest) {
        validateParams("applicantId", applicantId, "applicantUpdateRequest", applicantUpdateRequest);
        return apiClient.patchAsync(buildPath(APPLICANTS_PATH, applicantId), sdkAuthorization(), 
                ApplicantResponse.class, applicantUpdateRequest, null);
    }

    /**
     * Anonymizes an applicant by removing personal data.
     *
     * @param applicantId The applicant ID
     * @return CompletableFuture containing the applicant response
     */
    @Override
    public CompletableFuture<ApplicantResponse> anonymizeApplicant(final String applicantId) {
        validateParams("applicantId", applicantId);
        return apiClient.postAsync(buildPath(APPLICANTS_PATH, applicantId, ANONYMIZE_PATH), 
                sdkAuthorization(), ApplicantResponse.class, null, null);
    }

    // Synchronous methods

    /**
     * Creates a new applicant profile.
     *
     * @param applicantRequest The applicant request
     * @return The applicant response
     */
    @Override
    public ApplicantResponse createApplicantSync(final ApplicantRequest applicantRequest) {
        validateParams("applicantRequest", applicantRequest);
        return apiClient.post(APPLICANTS_PATH, sdkAuthorization(), ApplicantResponse.class,
                applicantRequest, null);
    }

    /**
     * Retrieves an applicant by ID.
     *
     * @param applicantId The applicant ID
     * @return The applicant response
     */
    @Override
    public ApplicantResponse getApplicantSync(final String applicantId) {
        validateParams("applicantId", applicantId);
        return apiClient.get(buildPath(APPLICANTS_PATH, applicantId), sdkAuthorization(), 
                ApplicantResponse.class);
    }

    /**
     * Updates an existing applicant profile.
     *
     * @param applicantId The applicant ID
     * @param applicantUpdateRequest The applicant update request
     * @return The applicant response
     */
    @Override
    public ApplicantResponse updateApplicantSync(final String applicantId, 
            final ApplicantUpdateRequest applicantUpdateRequest) {
        validateParams("applicantId", applicantId, "applicantUpdateRequest", applicantUpdateRequest);
        return apiClient.patch(buildPath(APPLICANTS_PATH, applicantId), sdkAuthorization(), 
                ApplicantResponse.class, applicantUpdateRequest, null);
    }

    /**
     * Anonymizes an applicant by removing personal data.
     *
     * @param applicantId The applicant ID
     * @return The applicant response
     */
    @Override
    public ApplicantResponse anonymizeApplicantSync(final String applicantId) {
        validateParams("applicantId", applicantId);
        return apiClient.post(buildPath(APPLICANTS_PATH, applicantId, ANONYMIZE_PATH), 
                sdkAuthorization(), ApplicantResponse.class, null, null);
    }
}