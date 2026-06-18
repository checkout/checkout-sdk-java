package com.checkout.identities.identityverification;

import com.checkout.identities.entities.AttemptAssetsQueryFilter;
import com.checkout.identities.identityverification.requests.CreateAndOpenIdentityVerificationRequest;
import com.checkout.identities.identityverification.requests.IdentityVerificationRequest;
import com.checkout.identities.identityverification.requests.IdentityVerificationAttemptRequest;
import com.checkout.identities.identityverification.responses.IdentityVerificationAttemptAssetsResponse;
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
    CompletableFuture<IdentityVerificationResponse> createAndOpenIdentityVerification(CreateAndOpenIdentityVerificationRequest identityVerificationRequest);

    /**
     * Create an identity verification session
     *
     * @param identityVerificationRequest the identity verification request
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationResponse}
     */
    CompletableFuture<IdentityVerificationResponse> createIdentityVerification(IdentityVerificationRequest identityVerificationRequest);

    /**
     * Retrieve an identity verification session
     *
     * @param identityVerificationId the identity verification ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationResponse}
     */
    CompletableFuture<IdentityVerificationResponse> getIdentityVerification(String identityVerificationId);

    /**
     * Anonymize an identity verification
     *
     * @param identityVerificationId the identity verification ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationResponse}
     */
    CompletableFuture<IdentityVerificationResponse> anonymizeIdentityVerification(String identityVerificationId);

    /**
     * Retrieve all identity verification attempts
     *
     * @param identityVerificationId the identity verification ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationAttemptsResponse}
     */
    CompletableFuture<IdentityVerificationAttemptsResponse> getIdentityVerificationAttempts(String identityVerificationId);

    /**
     * Create an identity verification attempt
     *
     * @param identityVerificationId the identity verification ID
     * @param identityVerificationAttemptRequest the attempt request
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationAttemptResponse}
     */
    CompletableFuture<IdentityVerificationAttemptResponse> createIdentityVerificationAttempt(String identityVerificationId, IdentityVerificationAttemptRequest identityVerificationAttemptRequest);

    /**
     * Retrieve a specific identity verification attempt
     *
     * @param identityVerificationId the identity verification ID
     * @param attemptId the attempt ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationAttemptResponse}
     */
    CompletableFuture<IdentityVerificationAttemptResponse> getIdentityVerificationAttempt(String identityVerificationId, String attemptId);

    /**
     * Retrieves the assets (face images, videos, and document images) captured during an identity verification attempt.
     *
     * @param identityVerificationId the identity verification ID
     * @param attemptId the attempt ID
     * @param queryFilter the pagination query parameters (skip and limit)
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationAttemptAssetsResponse}
     */
    CompletableFuture<IdentityVerificationAttemptAssetsResponse> getIdentityVerificationAttemptAssets(String identityVerificationId, String attemptId, AttemptAssetsQueryFilter queryFilter);

    /**
     * Generate and download a PDF report
     *
     * @param identityVerificationId the identity verification ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationReportResponse}
     */
    CompletableFuture<IdentityVerificationReportResponse> generateIdentityVerificationReport(String identityVerificationId);

    // Sync methods

    /**
     * Create and open an identity verification session
     *
     * @param identityVerificationRequest the identity verification request
     * @return the {@link IdentityVerificationResponse}
     */
    IdentityVerificationResponse createAndOpenIdentityVerificationSync(CreateAndOpenIdentityVerificationRequest identityVerificationRequest);

    /**
     * Create an identity verification session
     *
     * @param identityVerificationRequest the identity verification request
     * @return the {@link IdentityVerificationResponse}
     */
    IdentityVerificationResponse createIdentityVerificationSync(IdentityVerificationRequest identityVerificationRequest);

    /**
     * Retrieve an identity verification session
     *
     * @param identityVerificationId the identity verification ID
     * @return the {@link IdentityVerificationResponse}
     */
    IdentityVerificationResponse getIdentityVerificationSync(String identityVerificationId);

    /**
     * Anonymize an identity verification
     *
     * @param identityVerificationId the identity verification ID
     * @return the {@link IdentityVerificationResponse}
     */
    IdentityVerificationResponse anonymizeIdentityVerificationSync(String identityVerificationId);

    /**
     * Retrieve all identity verification attempts
     *
     * @param identityVerificationId the identity verification ID
     * @return the {@link IdentityVerificationAttemptsResponse}
     */
    IdentityVerificationAttemptsResponse getIdentityVerificationAttemptsSync(String identityVerificationId);

    /**
     * Create an identity verification attempt
     *
     * @param identityVerificationId the identity verification ID
     * @param identityVerificationAttemptRequest the attempt request
     * @return the {@link IdentityVerificationAttemptResponse}
     */
    IdentityVerificationAttemptResponse createIdentityVerificationAttemptSync(String identityVerificationId, IdentityVerificationAttemptRequest identityVerificationAttemptRequest);

    /**
     * Retrieve a specific identity verification attempt
     *
     * @param identityVerificationId the identity verification ID
     * @param attemptId the attempt ID
     * @return the {@link IdentityVerificationAttemptResponse}
     */
    IdentityVerificationAttemptResponse getIdentityVerificationAttemptSync(String identityVerificationId, String attemptId);

    /**
     * Retrieves the assets (face images, videos, and document images) captured during an identity verification attempt.
     *
     * @param identityVerificationId the identity verification ID
     * @param attemptId the attempt ID
     * @param queryFilter the pagination query parameters (skip and limit)
     * @return the {@link IdentityVerificationAttemptAssetsResponse}
     */
    IdentityVerificationAttemptAssetsResponse getIdentityVerificationAttemptAssetsSync(String identityVerificationId, String attemptId, AttemptAssetsQueryFilter queryFilter);

    /**
     * Generate and download a PDF report
     *
     * @param identityVerificationId the identity verification ID
     * @return the {@link IdentityVerificationReportResponse}
     */
    IdentityVerificationReportResponse generateIdentityVerificationReportSync(String identityVerificationId);
}