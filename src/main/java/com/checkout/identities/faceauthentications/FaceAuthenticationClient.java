package com.checkout.identities.faceauthentications;

import com.checkout.identities.faceauthentications.requests.FaceAuthenticationAttemptRequest;
import com.checkout.identities.faceauthentications.requests.FaceAuthenticationRequest;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationAttemptResponse;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationAttemptsResponse;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Client interface for Face Authentication operations.
 */
public interface FaceAuthenticationClient {

    /**
     * Creates a new face authentication for an applicant.
     *
     * @param faceAuthenticationRequest The face authentication request
     * @return CompletableFuture containing the face authentication response
     */
    CompletableFuture<FaceAuthenticationResponse> createFaceAuthentication(FaceAuthenticationRequest faceAuthenticationRequest);

    /**
     * Retrieves a face authentication by ID.
     *
     * @param faceAuthenticationId The face authentication ID
     * @return CompletableFuture containing the face authentication response
     */
    CompletableFuture<FaceAuthenticationResponse> getFaceAuthentication(String faceAuthenticationId);

    /**
     * Anonymizes a face authentication by removing personal data.
     *
     * @param faceAuthenticationId The face authentication ID
     * @return CompletableFuture containing the face authentication response
     */
    CompletableFuture<FaceAuthenticationResponse> anonymizeFaceAuthentication(String faceAuthenticationId);

    /**
     * Creates a new face authentication attempt.
     *
     * @param faceAuthenticationId The face authentication ID
     * @param attemptRequest The face authentication attempt request
     * @return CompletableFuture containing the face authentication attempt response
     */
    CompletableFuture<FaceAuthenticationAttemptResponse> createFaceAuthenticationAttempt(String faceAuthenticationId, FaceAuthenticationAttemptRequest attemptRequest);

    /**
     * Retrieves all attempts for a specific face authentication.
     *
     * @param faceAuthenticationId The face authentication ID
     * @return CompletableFuture containing the face authentication attempts response
     */
    CompletableFuture<FaceAuthenticationAttemptsResponse> getFaceAuthenticationAttempts(String faceAuthenticationId);

    /**
     * Retrieves a specific attempt for a face authentication.
     *
     * @param faceAuthenticationId The face authentication ID
     * @param attemptId The attempt ID
     * @return CompletableFuture containing the face authentication attempt response
     */
    CompletableFuture<FaceAuthenticationAttemptResponse> getFaceAuthenticationAttempt(String faceAuthenticationId, String attemptId);

    // Synchronous methods

    /**
     * Creates a new face authentication for an applicant.
     *
     * @param faceAuthenticationRequest The face authentication request
     * @return The face authentication response
     */
    FaceAuthenticationResponse createFaceAuthenticationSync(FaceAuthenticationRequest faceAuthenticationRequest);

    /**
     * Retrieves a face authentication by ID.
     *
     * @param faceAuthenticationId The face authentication ID
     * @return The face authentication response
     */
    FaceAuthenticationResponse getFaceAuthenticationSync(String faceAuthenticationId);

    /**
     * Anonymizes a face authentication by removing personal data.
     *
     * @param faceAuthenticationId The face authentication ID
     * @return The face authentication response
     */
    FaceAuthenticationResponse anonymizeFaceAuthenticationSync(String faceAuthenticationId);

    /**
     * Creates a new face authentication attempt.
     *
     * @param faceAuthenticationId The face authentication ID
     * @param attemptRequest The face authentication attempt request
     * @return The face authentication attempt response
     */
    FaceAuthenticationAttemptResponse createFaceAuthenticationAttemptSync(String faceAuthenticationId, FaceAuthenticationAttemptRequest attemptRequest);

    /**
     * Retrieves all attempts for a specific face authentication.
     *
     * @param faceAuthenticationId The face authentication ID
     * @return The face authentication attempts response
     */
    FaceAuthenticationAttemptsResponse getFaceAuthenticationAttemptsSync(String faceAuthenticationId);

    /**
     * Retrieves a specific attempt for a face authentication.
     *
     * @param faceAuthenticationId The face authentication ID
     * @param attemptId The attempt ID
     * @return The face authentication attempt response
     */
    FaceAuthenticationAttemptResponse getFaceAuthenticationAttemptSync(String faceAuthenticationId, String attemptId);
}