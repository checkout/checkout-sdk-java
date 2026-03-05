package com.checkout.identities.faceauthentications;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.identities.faceauthentications.requests.FaceAuthenticationAttemptRequest;
import com.checkout.identities.faceauthentications.requests.FaceAuthenticationRequest;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationAttemptResponse;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationAttemptsResponse;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

/**
 * Implementation of the Face Authentication client.
 */
public class FaceAuthenticationClientImpl extends AbstractClient implements FaceAuthenticationClient {

    private static final String FACE_AUTHENTICATIONS_PATH = "face-authentications";
    private static final String ANONYMIZE_PATH = "anonymize";
    private static final String ATTEMPTS_PATH = "attempts";

    public FaceAuthenticationClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    /**
     * Creates a new face authentication for an applicant.
     *
     * @param faceAuthenticationRequest The face authentication request
     * @return CompletableFuture containing the face authentication response
     */
    @Override
    public CompletableFuture<FaceAuthenticationResponse> createFaceAuthentication(
            final FaceAuthenticationRequest faceAuthenticationRequest) {
        validateParams("faceAuthenticationRequest", faceAuthenticationRequest);
        return apiClient.postAsync(FACE_AUTHENTICATIONS_PATH, sdkAuthorization(), FaceAuthenticationResponse.class,
                faceAuthenticationRequest, null);
    }

    /**
     * Retrieves a face authentication by ID.
     *
     * @param faceAuthenticationId The face authentication ID
     * @return CompletableFuture containing the face authentication response
     */
    @Override
    public CompletableFuture<FaceAuthenticationResponse> getFaceAuthentication(final String faceAuthenticationId) {
        validateParams("faceAuthenticationId", faceAuthenticationId);
        return apiClient.getAsync(buildPath(FACE_AUTHENTICATIONS_PATH, faceAuthenticationId), sdkAuthorization(), 
                FaceAuthenticationResponse.class);
    }

    /**
     * Anonymizes a face authentication by removing personal data.
     *
     * @param faceAuthenticationId The face authentication ID
     * @return CompletableFuture containing the face authentication response
     */
    @Override
    public CompletableFuture<FaceAuthenticationResponse> anonymizeFaceAuthentication(final String faceAuthenticationId) {
        validateParams("faceAuthenticationId", faceAuthenticationId);
        return apiClient.postAsync(buildPath(FACE_AUTHENTICATIONS_PATH, faceAuthenticationId, ANONYMIZE_PATH), 
                sdkAuthorization(), FaceAuthenticationResponse.class, null, null);
    }

    /**
     * Creates a new face authentication attempt.
     *
     * @param faceAuthenticationId The face authentication ID
     * @param attemptRequest The face authentication attempt request
     * @return CompletableFuture containing the face authentication attempt response
     */
    @Override
    public CompletableFuture<FaceAuthenticationAttemptResponse> createFaceAuthenticationAttempt(
            final String faceAuthenticationId, final FaceAuthenticationAttemptRequest attemptRequest) {
        validateParams("faceAuthenticationId", faceAuthenticationId, "attemptRequest", attemptRequest);
        return apiClient.postAsync(buildPath(FACE_AUTHENTICATIONS_PATH, faceAuthenticationId, ATTEMPTS_PATH), 
                sdkAuthorization(), FaceAuthenticationAttemptResponse.class, attemptRequest, null);
    }

    /**
     * Retrieves all attempts for a specific face authentication.
     *
     * @param faceAuthenticationId The face authentication ID
     * @return CompletableFuture containing the face authentication attempts response
     */
    @Override
    public CompletableFuture<FaceAuthenticationAttemptsResponse> getFaceAuthenticationAttempts(
            final String faceAuthenticationId) {
        validateParams("faceAuthenticationId", faceAuthenticationId);
        return apiClient.getAsync(buildPath(FACE_AUTHENTICATIONS_PATH, faceAuthenticationId, ATTEMPTS_PATH), 
                sdkAuthorization(), FaceAuthenticationAttemptsResponse.class);
    }

    /**
     * Retrieves a specific attempt for a face authentication.
     *
     * @param faceAuthenticationId The face authentication ID
     * @param attemptId The attempt ID
     * @return CompletableFuture containing the face authentication attempt response
     */
    @Override
    public CompletableFuture<FaceAuthenticationAttemptResponse> getFaceAuthenticationAttempt(
            final String faceAuthenticationId, final String attemptId) {
        validateParams("faceAuthenticationId", faceAuthenticationId, "attemptId", attemptId);
        return apiClient.getAsync(buildPath(FACE_AUTHENTICATIONS_PATH, faceAuthenticationId, ATTEMPTS_PATH, attemptId), 
                sdkAuthorization(), FaceAuthenticationAttemptResponse.class);
    }

    // Synchronous methods

    /**
     * Creates a new face authentication for an applicant.
     *
     * @param faceAuthenticationRequest The face authentication request
     * @return The face authentication response
     */
    @Override
    public FaceAuthenticationResponse createFaceAuthenticationSync(
            final FaceAuthenticationRequest faceAuthenticationRequest) {
        validateParams("faceAuthenticationRequest", faceAuthenticationRequest);
        return apiClient.post(FACE_AUTHENTICATIONS_PATH, sdkAuthorization(), FaceAuthenticationResponse.class,
                faceAuthenticationRequest, null);
    }

    /**
     * Retrieves a face authentication by ID.
     *
     * @param faceAuthenticationId The face authentication ID
     * @return The face authentication response
     */
    @Override
    public FaceAuthenticationResponse getFaceAuthenticationSync(final String faceAuthenticationId) {
        validateParams("faceAuthenticationId", faceAuthenticationId);
        return apiClient.get(buildPath(FACE_AUTHENTICATIONS_PATH, faceAuthenticationId), sdkAuthorization(), 
                FaceAuthenticationResponse.class);
    }

    /**
     * Anonymizes a face authentication by removing personal data.
     *
     * @param faceAuthenticationId The face authentication ID
     * @return The face authentication response
     */
    @Override
    public FaceAuthenticationResponse anonymizeFaceAuthenticationSync(final String faceAuthenticationId) {
        validateParams("faceAuthenticationId", faceAuthenticationId);
        return apiClient.post(buildPath(FACE_AUTHENTICATIONS_PATH, faceAuthenticationId, ANONYMIZE_PATH), 
                sdkAuthorization(), FaceAuthenticationResponse.class, null, null);
    }

    /**
     * Creates a new face authentication attempt.
     *
     * @param faceAuthenticationId The face authentication ID
     * @param attemptRequest The face authentication attempt request
     * @return The face authentication attempt response
     */
    @Override
    public FaceAuthenticationAttemptResponse createFaceAuthenticationAttemptSync(
            final String faceAuthenticationId, final FaceAuthenticationAttemptRequest attemptRequest) {
        validateParams("faceAuthenticationId", faceAuthenticationId, "attemptRequest", attemptRequest);
        return apiClient.post(buildPath(FACE_AUTHENTICATIONS_PATH, faceAuthenticationId, ATTEMPTS_PATH), 
                sdkAuthorization(), FaceAuthenticationAttemptResponse.class, attemptRequest, null);
    }

    /**
     * Retrieves all attempts for a specific face authentication.
     *
     * @param faceAuthenticationId The face authentication ID
     * @return The face authentication attempts response
     */
    @Override
    public FaceAuthenticationAttemptsResponse getFaceAuthenticationAttemptsSync(final String faceAuthenticationId) {
        validateParams("faceAuthenticationId", faceAuthenticationId);
        return apiClient.get(buildPath(FACE_AUTHENTICATIONS_PATH, faceAuthenticationId, ATTEMPTS_PATH), 
                sdkAuthorization(), FaceAuthenticationAttemptsResponse.class);
    }

    /**
     * Retrieves a specific attempt for a face authentication.
     *
     * @param faceAuthenticationId The face authentication ID
     * @param attemptId The attempt ID
     * @return The face authentication attempt response
     */
    @Override
    public FaceAuthenticationAttemptResponse getFaceAuthenticationAttemptSync(
            final String faceAuthenticationId, final String attemptId) {
        validateParams("faceAuthenticationId", faceAuthenticationId, "attemptId", attemptId);
        return apiClient.get(buildPath(FACE_AUTHENTICATIONS_PATH, faceAuthenticationId, ATTEMPTS_PATH, attemptId), 
                sdkAuthorization(), FaceAuthenticationAttemptResponse.class);
    }
}