package com.checkout.identities.iddocumentverification;

import com.checkout.identities.iddocumentverification.requests.IdDocumentVerificationAttemptRequest;
import com.checkout.identities.iddocumentverification.requests.IdDocumentVerificationRequest;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationAttemptResponse;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationAttemptsResponse;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationReportResponse;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Client for ID document verification operations
 */
public interface IdDocumentVerificationClient {

    /**
     * Create an ID document verification
     *
     * @param idDocumentVerificationRequest the ID document verification request
     * @return a {@link CompletableFuture} containing the {@link IdDocumentVerificationResponse}
     */
    CompletableFuture<IdDocumentVerificationResponse> createIdDocumentVerificationAsync(IdDocumentVerificationRequest idDocumentVerificationRequest);

    /**
     * Retrieve an ID document verification
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return a {@link CompletableFuture} containing the {@link IdDocumentVerificationResponse}
     */
    CompletableFuture<IdDocumentVerificationResponse> getIdDocumentVerificationAsync(String idDocumentVerificationId);

    /**
     * Anonymize an ID document verification
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return a {@link CompletableFuture} containing the {@link IdDocumentVerificationResponse}
     */
    CompletableFuture<IdDocumentVerificationResponse> anonymizeIdDocumentVerificationAsync(String idDocumentVerificationId);

    /**
     * Create an ID document verification attempt
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @param idDocumentVerificationAttemptRequest the attempt request
     * @return a {@link CompletableFuture} containing the {@link IdDocumentVerificationAttemptResponse}
     */
    CompletableFuture<IdDocumentVerificationAttemptResponse> createIdDocumentVerificationAttemptAsync(String idDocumentVerificationId, IdDocumentVerificationAttemptRequest idDocumentVerificationAttemptRequest);

    /**
     * Retrieve all ID document verification attempts
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return a {@link CompletableFuture} containing the {@link IdDocumentVerificationAttemptsResponse}
     */
    CompletableFuture<IdDocumentVerificationAttemptsResponse> getIdDocumentVerificationAttemptsAsync(String idDocumentVerificationId);

    /**
     * Retrieve a specific ID document verification attempt
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @param attemptId the attempt ID
     * @return a {@link CompletableFuture} containing the {@link IdDocumentVerificationAttemptResponse}
     */
    CompletableFuture<IdDocumentVerificationAttemptResponse> getIdDocumentVerificationAttemptAsync(String idDocumentVerificationId, String attemptId);

    /**
     * Generate and download a PDF report
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return a {@link CompletableFuture} containing the {@link IdDocumentVerificationReportResponse}
     */
    CompletableFuture<IdDocumentVerificationReportResponse> getIdDocumentVerificationReportAsync(String idDocumentVerificationId);

    // Synchronous methods

    /**
     * Create an ID document verification
     *
     * @param idDocumentVerificationRequest the ID document verification request
     * @return the {@link IdDocumentVerificationResponse}
     */
    IdDocumentVerificationResponse createIdDocumentVerification(IdDocumentVerificationRequest idDocumentVerificationRequest);

    /**
     * Retrieve an ID document verification
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return the {@link IdDocumentVerificationResponse}
     */
    IdDocumentVerificationResponse getIdDocumentVerification(String idDocumentVerificationId);

    /**
     * Anonymize an ID document verification
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return the {@link IdDocumentVerificationResponse}
     */
    IdDocumentVerificationResponse anonymizeIdDocumentVerification(String idDocumentVerificationId);

    /**
     * Create an ID document verification attempt
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @param idDocumentVerificationAttemptRequest the attempt request
     * @return the {@link IdDocumentVerificationAttemptResponse}
     */
    IdDocumentVerificationAttemptResponse createIdDocumentVerificationAttempt(String idDocumentVerificationId, IdDocumentVerificationAttemptRequest idDocumentVerificationAttemptRequest);

    /**
     * Retrieve all ID document verification attempts
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return the {@link IdDocumentVerificationAttemptsResponse}
     */
    IdDocumentVerificationAttemptsResponse getIdDocumentVerificationAttempts(String idDocumentVerificationId);

    /**
     * Retrieve a specific ID document verification attempt
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @param attemptId the attempt ID
     * @return the {@link IdDocumentVerificationAttemptResponse}
     */
    IdDocumentVerificationAttemptResponse getIdDocumentVerificationAttempt(String idDocumentVerificationId, String attemptId);

    /**
     * Generate and download a PDF report
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return the {@link IdDocumentVerificationReportResponse}
     */
    IdDocumentVerificationReportResponse getIdDocumentVerificationReport(String idDocumentVerificationId);
}