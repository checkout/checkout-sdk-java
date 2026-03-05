package com.checkout.identities.applicants;

import com.checkout.identities.applicants.requests.ApplicantRequest;
import com.checkout.identities.applicants.requests.ApplicantUpdateRequest;
import com.checkout.identities.applicants.responses.ApplicantResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Client interface for Applicant operations.
 */
public interface ApplicantClient {

    /**
     * Creates a new applicant profile.
     *
     * @param applicantRequest The applicant request
     * @return CompletableFuture containing the applicant response
     */
    CompletableFuture<ApplicantResponse> createApplicant(ApplicantRequest applicantRequest);

    /**
     * Retrieves an applicant by ID.
     *
     * @param applicantId The applicant ID
     * @return CompletableFuture containing the applicant response
     */
    CompletableFuture<ApplicantResponse> getApplicant(String applicantId);

    /**
     * Updates an existing applicant profile.
     *
     * @param applicantId The applicant ID
     * @param applicantUpdateRequest The applicant update request
     * @return CompletableFuture containing the applicant response
     */
    CompletableFuture<ApplicantResponse> updateApplicant(String applicantId, ApplicantUpdateRequest applicantUpdateRequest);

    /**
     * Anonymizes an applicant by removing personal data.
     *
     * @param applicantId The applicant ID
     * @return CompletableFuture containing the applicant response
     */
    CompletableFuture<ApplicantResponse> anonymizeApplicant(String applicantId);

    // Synchronous methods

    /**
     * Creates a new applicant profile.
     *
     * @param applicantRequest The applicant request
     * @return The applicant response
     */
    ApplicantResponse createApplicantSync(ApplicantRequest applicantRequest);

    /**
     * Retrieves an applicant by ID.
     *
     * @param applicantId The applicant ID
     * @return The applicant response
     */
    ApplicantResponse getApplicantSync(String applicantId);

    /**
     * Updates an existing applicant profile.
     *
     * @param applicantId The applicant ID
     * @param applicantUpdateRequest The applicant update request
     * @return The applicant response
     */
    ApplicantResponse updateApplicantSync(String applicantId, ApplicantUpdateRequest applicantUpdateRequest);

    /**
     * Anonymizes an applicant by removing personal data.
     *
     * @param applicantId The applicant ID
     * @return The applicant response
     */
    ApplicantResponse anonymizeApplicantSync(String applicantId);
}