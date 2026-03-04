package com.checkout.identities.identityverification;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.identities.identityverification.requests.CreateAndOpenIdentityVerificationRequest;
import com.checkout.identities.identityverification.requests.IdentityVerificationRequest;
import com.checkout.identities.identityverification.requests.IdentityVerificationAttemptRequest;
import com.checkout.identities.identityverification.responses.IdentityVerificationAttemptResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationAttemptsResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationReportResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

/**
 * Implementation of the Identity Verification client.
 */
public class IdentityVerificationClientImpl extends AbstractClient implements IdentityVerificationClient {

    private static final String IDENTITY_VERIFICATIONS_PATH = "identity-verifications";
    private static final String CREATE_AND_OPEN_PATH = "create-and-open-idv";
    private static final String ANONYMIZE_PATH = "anonymize";
    private static final String ATTEMPTS_PATH = "attempts";
    private static final String PDF_REPORT_PATH = "pdf-report";

    public IdentityVerificationClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    // Async methods

    /**
     * Create and open an identity verification session
     *
     * @param identityVerificationRequest the identity verification request
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationResponse}
     */
    @Override
    public CompletableFuture<IdentityVerificationResponse> createAndOpenIdentityVerificationAsync(
            final CreateAndOpenIdentityVerificationRequest identityVerificationRequest) {
        validateParams("identityVerificationRequest", identityVerificationRequest);
        return apiClient.postAsync(CREATE_AND_OPEN_PATH, sdkAuthorization(), IdentityVerificationResponse.class,
                identityVerificationRequest, null);
    }

    /**
     * Create an identity verification session
     *
     * @param identityVerificationRequest the identity verification request
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationResponse}
     */
    @Override
    public CompletableFuture<IdentityVerificationResponse> createIdentityVerificationAsync(
            final IdentityVerificationRequest identityVerificationRequest) {
        validateParams("identityVerificationRequest", identityVerificationRequest);
        return apiClient.postAsync(IDENTITY_VERIFICATIONS_PATH, sdkAuthorization(), IdentityVerificationResponse.class,
                identityVerificationRequest, null);
    }

    /**
     * Retrieve an identity verification session
     *
     * @param identityVerificationId the identity verification ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationResponse}
     */
    @Override
    public CompletableFuture<IdentityVerificationResponse> getIdentityVerificationAsync(
            final String identityVerificationId) {
        validateParams("identityVerificationId", identityVerificationId);
        return apiClient.getAsync(buildPath(IDENTITY_VERIFICATIONS_PATH, identityVerificationId), sdkAuthorization(),
                IdentityVerificationResponse.class);
    }

    /**
     * Anonymize an identity verification
     *
     * @param identityVerificationId the identity verification ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationResponse}
     */
    @Override
    public CompletableFuture<IdentityVerificationResponse> anonymizeIdentityVerificationAsync(
            final String identityVerificationId) {
        validateParams("identityVerificationId", identityVerificationId);
        return apiClient.postAsync(buildPath(IDENTITY_VERIFICATIONS_PATH, identityVerificationId, ANONYMIZE_PATH), 
                sdkAuthorization(), IdentityVerificationResponse.class, null, null);
    }

    /**
     * Create an identity verification attempt
     *
     * @param identityVerificationId             the identity verification ID
     * @param identityVerificationAttemptRequest the attempt request
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationAttemptResponse}
     */
    @Override
    public CompletableFuture<IdentityVerificationAttemptResponse> createIdentityVerificationAttemptAsync(
            final String identityVerificationId,
            final IdentityVerificationAttemptRequest identityVerificationAttemptRequest) {
        validateParams("identityVerificationId", identityVerificationId, "identityVerificationAttemptRequest",
                identityVerificationAttemptRequest);
        return apiClient.postAsync(buildPath(IDENTITY_VERIFICATIONS_PATH, identityVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), IdentityVerificationAttemptResponse.class, identityVerificationAttemptRequest,
                null);
    }

    /**
     * Retrieve all identity verification attempts
     *
     * @param identityVerificationId the identity verification ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationAttemptsResponse}
     */
    @Override
    public CompletableFuture<IdentityVerificationAttemptsResponse> getIdentityVerificationAttemptsAsync(
            final String identityVerificationId) {
        validateParams("identityVerificationId", identityVerificationId);
        return apiClient.getAsync(buildPath(IDENTITY_VERIFICATIONS_PATH, identityVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), IdentityVerificationAttemptsResponse.class);
    }

    /**
     * Retrieve a specific identity verification attempt
     *
     * @param identityVerificationId the identity verification ID
     * @param attemptId the attempt ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationAttemptResponse}
     */
    @Override
    public CompletableFuture<IdentityVerificationAttemptResponse> getIdentityVerificationAttemptAsync(
            final String identityVerificationId, final String attemptId) {
        validateParams("identityVerificationId", identityVerificationId, "attemptId", attemptId);
        return apiClient.getAsync(buildPath(IDENTITY_VERIFICATIONS_PATH, identityVerificationId, ATTEMPTS_PATH, attemptId),
                sdkAuthorization(), IdentityVerificationAttemptResponse.class);
    }

    /**
     * Generate and download a PDF report
     *
     * @param identityVerificationId the identity verification ID
     * @return a {@link CompletableFuture} containing the {@link IdentityVerificationReportResponse}
     */
    @Override
    public CompletableFuture<IdentityVerificationReportResponse> generateIdentityVerificationReportAsync(
            final String identityVerificationId) {
        validateParams("identityVerificationId", identityVerificationId);
        return apiClient.getAsync(buildPath(IDENTITY_VERIFICATIONS_PATH, identityVerificationId, PDF_REPORT_PATH),
                sdkAuthorization(), IdentityVerificationReportResponse.class);
    }

    // Sync methods

    /**
     * Create and open an identity verification session
     *
     * @param identityVerificationRequest the identity verification request
     * @return the {@link IdentityVerificationResponse}
     */
    @Override
    public IdentityVerificationResponse createAndOpenIdentityVerification(
            final CreateAndOpenIdentityVerificationRequest identityVerificationRequest) {
        validateParams("identityVerificationRequest", identityVerificationRequest);
        return apiClient.post(CREATE_AND_OPEN_PATH, sdkAuthorization(), IdentityVerificationResponse.class,
                identityVerificationRequest, null);
    }

    /**
     * Create an identity verification session
     *
     * @param identityVerificationRequest the identity verification request
     * @return the {@link IdentityVerificationResponse}
     */
    @Override
    public IdentityVerificationResponse createIdentityVerification(
            final IdentityVerificationRequest identityVerificationRequest) {
        validateParams("identityVerificationRequest", identityVerificationRequest);
        return apiClient.post(IDENTITY_VERIFICATIONS_PATH, sdkAuthorization(), IdentityVerificationResponse.class,
                identityVerificationRequest, null);
    }

    /**
     * Retrieve an identity verification session
     *
     * @param identityVerificationId the identity verification ID
     * @return the {@link IdentityVerificationResponse}
     */
    @Override
    public IdentityVerificationResponse getIdentityVerification(final String identityVerificationId) {
        validateParams("identityVerificationId", identityVerificationId);
        return apiClient.get(buildPath(IDENTITY_VERIFICATIONS_PATH, identityVerificationId), sdkAuthorization(),
                IdentityVerificationResponse.class);
    }

    /**
     * Anonymize an identity verification
     *
     * @param identityVerificationId the identity verification ID
     * @return the {@link IdentityVerificationResponse}
     */
    @Override
    public IdentityVerificationResponse anonymizeIdentityVerification(final String identityVerificationId) {
        validateParams("identityVerificationId", identityVerificationId);
        return apiClient.post(buildPath(IDENTITY_VERIFICATIONS_PATH, identityVerificationId, ANONYMIZE_PATH), 
                sdkAuthorization(), IdentityVerificationResponse.class, null, null);
    }

    /**
     * Retrieve all identity verification attempts
     *
     * @param identityVerificationId the identity verification ID
     * @return the {@link IdentityVerificationAttemptsResponse}
     */
    @Override
    public IdentityVerificationAttemptsResponse getIdentityVerificationAttempts(final String identityVerificationId) {
        validateParams("identityVerificationId", identityVerificationId);
        return apiClient.get(buildPath(IDENTITY_VERIFICATIONS_PATH, identityVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), IdentityVerificationAttemptsResponse.class);
    }

    /**
     * Create an identity verification attempt
     *
     * @param identityVerificationId             the identity verification ID
     * @param identityVerificationAttemptRequest the attempt request
     * @return the {@link IdentityVerificationAttemptResponse}
     */
    @Override
    public IdentityVerificationAttemptResponse createIdentityVerificationAttempt(
            final String identityVerificationId,
            final IdentityVerificationAttemptRequest identityVerificationAttemptRequest) {
        validateParams("identityVerificationId", identityVerificationId, "identityVerificationAttemptRequest",
                identityVerificationAttemptRequest);
        return apiClient.post(buildPath(IDENTITY_VERIFICATIONS_PATH, identityVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), IdentityVerificationAttemptResponse.class, identityVerificationAttemptRequest,
                null);
    }

    /**
     * Retrieve a specific identity verification attempt
     *
     * @param identityVerificationId the identity verification ID
     * @param attemptId the attempt ID
     * @return the {@link IdentityVerificationAttemptResponse}
     */
    @Override
    public IdentityVerificationAttemptResponse getIdentityVerificationAttempt(
            final String identityVerificationId, final String attemptId) {
        validateParams("identityVerificationId", identityVerificationId, "attemptId", attemptId);
        return apiClient.get(buildPath(IDENTITY_VERIFICATIONS_PATH, identityVerificationId, ATTEMPTS_PATH, attemptId),
                sdkAuthorization(), IdentityVerificationAttemptResponse.class);
    }

    /**
     * Generate and download a PDF report
     *
     * @param identityVerificationId the identity verification ID
     * @return the {@link IdentityVerificationReportResponse}
     */
    @Override
    public IdentityVerificationReportResponse generateIdentityVerificationReport(final String identityVerificationId) {
        validateParams("identityVerificationId", identityVerificationId);
        return apiClient.get(buildPath(IDENTITY_VERIFICATIONS_PATH, identityVerificationId, PDF_REPORT_PATH),
                sdkAuthorization(), IdentityVerificationReportResponse.class);
    }

}