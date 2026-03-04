package com.checkout.identities.identityverification;

import com.checkout.identities.identityverification.requests.CreateAndOpenIdentityVerificationRequest;
import com.checkout.identities.identityverification.requests.IdentityVerificationRequest;
import com.checkout.identities.identityverification.requests.IdentityVerificationAttemptRequest;
import com.checkout.identities.identityverification.responses.IdentityVerificationAttemptResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationAttemptsResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationReportResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Client for identity verification operations
 */
public interface IdentityVerificationClient {

    // Async methods

    /**
     * Create and open an identity verification session
     *
     * @param identityVerificationRequest the identity verification request
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationResponse}
     */
    CompletableFuture<IdentityVerificationResponse> createAndOpenIdentityVerificationAsync(CreateAndOpenIdentityVerificationRequest identityVerificationRequest);

    /**
     * Create an identity verification session
     *
     * @param identityVerificationRequest the identity verification request
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationResponse}
     */
    CompletableFuture<IdentityVerificationResponse> createIdentityVerificationAsync(IdentityVerificationRequest identityVerificationRequest);

    /**
     * Retrieve an identity verification session
     *
     * @param identityVerificationId the identity verification ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationResponse}
     */
    CompletableFuture<IdentityVerificationResponse> getIdentityVerificationAsync(String identityVerificationId);

    /**
     * Anonymize an identity verification
     *
     * @param identityVerificationId the identity verification ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationResponse}
     */
    CompletableFuture<IdentityVerificationResponse> anonymizeIdentityVerificationAsync(String identityVerificationId);

    /**
     * Retrieve all identity verification attempts
     *
     * @param identityVerificationId the identity verification ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationAttemptsResponse}
     */
    CompletableFuture<IdentityVerificationAttemptsResponse> getIdentityVerificationAttemptsAsync(String identityVerificationId);

    /**
     * Create an identity verification attempt
     *
     * @param identityVerificationId the identity verification ID
     * @param identityVerificationAttemptRequest the attempt request
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationAttemptResponse}
     */
    CompletableFuture<IdentityVerificationAttemptResponse> createIdentityVerificationAttemptAsync(String identityVerificationId, IdentityVerificationAttemptRequest identityVerificationAttemptRequest);

    /**
     * Retrieve a specific identity verification attempt
     *
     * @param identityVerificationId the identity verification ID
     * @param attemptId the attempt ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationAttemptResponse}
     */
    CompletableFuture<IdentityVerificationAttemptResponse> getIdentityVerificationAttemptAsync(String identityVerificationId, String attemptId);

    /**
     * Generate and download a PDF report
     *
     * @param identityVerificationId the identity verification ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationReportResponse}
     */
    CompletableFuture<IdentityVerificationReportResponse> generateIdentityVerificationReportAsync(String identityVerificationId);

    // Sync methods

    /**
     * Create and open an identity verification session
     *
     * @param identityVerificationRequest the identity verification request
     * @return the {@link IdentityVerificationResponse}
     */
    IdentityVerificationResponse createAndOpenIdentityVerification(CreateAndOpenIdentityVerificationRequest identityVerificationRequest);

    /**
     * Create an identity verification session
     *
     * @param identityVerificationRequest the identity verification request
     * @return the {@link IdentityVerificationResponse}
     */
    IdentityVerificationResponse createIdentityVerification(IdentityVerificationRequest identityVerificationRequest);

    /**
     * Retrieve an identity verification session
     *
     * @param identityVerificationId the identity verification ID
     * @return the {@link IdentityVerificationResponse}
     */
    IdentityVerificationResponse getIdentityVerification(String identityVerificationId);

    /**
     * Anonymize an identity verification
     *
     * @param identityVerificationId the identity verification ID
     * @return the {@link IdentityVerificationResponse}
     */
    IdentityVerificationResponse anonymizeIdentityVerification(String identityVerificationId);

    /**
     * Retrieve all identity verification attempts
     *
     * @param identityVerificationId the identity verification ID
     * @return the {@link IdentityVerificationAttemptsResponse}
     */
    IdentityVerificationAttemptsResponse getIdentityVerificationAttempts(String identityVerificationId);

    /**
     * Create an identity verification attempt
     *
     * @param identityVerificationId the identity verification ID
     * @param identityVerificationAttemptRequest the attempt request
     * @return the {@link IdentityVerificationAttemptResponse}
     */
    IdentityVerificationAttemptResponse createIdentityVerificationAttempt(String identityVerificationId, IdentityVerificationAttemptRequest identityVerificationAttemptRequest);

    /**
     * Retrieve a specific identity verification attempt
     *
     * @param identityVerificationId the identity verification ID
     * @param attemptId the attempt ID
     * @return the {@link IdentityVerificationAttemptResponse}
     */
    IdentityVerificationAttemptResponse getIdentityVerificationAttempt(String identityVerificationId, String attemptId);

    /**
     * Generate and download a PDF report
     *
     * @param identityVerificationId the identity verification ID
     * @return the {@link IdentityVerificationReportResponse}
     */
    IdentityVerificationReportResponse generateIdentityVerificationReport(String identityVerificationId);
}