package com.checkout.identities.iddocumentverification;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.identities.iddocumentverification.requests.IdDocumentVerificationAttemptRequest;
import com.checkout.identities.iddocumentverification.requests.IdDocumentVerificationRequest;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationAttemptResponse;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationAttemptsResponse;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationReportResponse;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

/**
 * Implementation of the ID Document Verification client.
 */
public class IdDocumentVerificationClientImpl extends AbstractClient implements IdDocumentVerificationClient {

    private static final String ID_DOCUMENT_VERIFICATIONS_PATH = "id-document-verifications";
    private static final String ANONYMIZE_PATH = "anonymize";
    private static final String ATTEMPTS_PATH = "attempts";
    private static final String PDF_REPORT_PATH = "pdf-report";

    public IdDocumentVerificationClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    /**
     * Create an ID document verification
     *
     * @param idDocumentVerificationRequest the ID document verification request
     * @return a {@link CompletableFuture} containing the {@link IdDocumentVerificationResponse}
     */
    @Override
    public CompletableFuture<IdDocumentVerificationResponse> createIdDocumentVerificationAsync(
            final IdDocumentVerificationRequest idDocumentVerificationRequest) {
        validateParams("idDocumentVerificationRequest", idDocumentVerificationRequest);
        return apiClient.postAsync(ID_DOCUMENT_VERIFICATIONS_PATH, sdkAuthorization(), IdDocumentVerificationResponse.class,
                idDocumentVerificationRequest, null);
    }

    /**
     * Retrieve an ID document verification
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return a {@link CompletableFuture} containing the {@link IdDocumentVerificationResponse}
     */
    @Override
    public CompletableFuture<IdDocumentVerificationResponse> getIdDocumentVerificationAsync(
            final String idDocumentVerificationId) {
        validateParams("idDocumentVerificationId", idDocumentVerificationId);
        return apiClient.getAsync(buildPath(ID_DOCUMENT_VERIFICATIONS_PATH, idDocumentVerificationId), sdkAuthorization(),
                IdDocumentVerificationResponse.class);
    }

    /**
     * Anonymize an ID document verification
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return a {@link CompletableFuture} containing the {@link IdDocumentVerificationResponse}
     */
    @Override
    public CompletableFuture<IdDocumentVerificationResponse> anonymizeIdDocumentVerificationAsync(
            final String idDocumentVerificationId) {
        validateParams("idDocumentVerificationId", idDocumentVerificationId);
        return apiClient.postAsync(buildPath(ID_DOCUMENT_VERIFICATIONS_PATH, idDocumentVerificationId, ANONYMIZE_PATH),
                sdkAuthorization(), IdDocumentVerificationResponse.class, null, null);
    }

    /**
     * Create an ID document verification attempt
     *
     * @param idDocumentVerificationId             the ID document verification ID
     * @param idDocumentVerificationAttemptRequest the attempt request
     * @return a {@link CompletableFuture} containing the {@link IdDocumentVerificationAttemptResponse}
     */
    @Override
    public CompletableFuture<IdDocumentVerificationAttemptResponse> createIdDocumentVerificationAttemptAsync(
            final String idDocumentVerificationId,
            final IdDocumentVerificationAttemptRequest idDocumentVerificationAttemptRequest) {
        validateParams("idDocumentVerificationId", idDocumentVerificationId, 
                "idDocumentVerificationAttemptRequest", idDocumentVerificationAttemptRequest);
        return apiClient.postAsync(buildPath(ID_DOCUMENT_VERIFICATIONS_PATH, idDocumentVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), IdDocumentVerificationAttemptResponse.class, idDocumentVerificationAttemptRequest,
                null);
    }

    /**
     * Retrieve all ID document verification attempts
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return a {@link CompletableFuture} containing the {@link IdDocumentVerificationAttemptsResponse}
     */
    @Override
    public CompletableFuture<IdDocumentVerificationAttemptsResponse> getIdDocumentVerificationAttemptsAsync(
            final String idDocumentVerificationId) {
        validateParams("idDocumentVerificationId", idDocumentVerificationId);
        return apiClient.getAsync(buildPath(ID_DOCUMENT_VERIFICATIONS_PATH, idDocumentVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), IdDocumentVerificationAttemptsResponse.class);
    }

    /**
     * Retrieve a specific ID document verification attempt
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @param attemptId                the attempt ID
     * @return a {@link CompletableFuture} containing the {@link IdDocumentVerificationAttemptResponse}
     */
    @Override
    public CompletableFuture<IdDocumentVerificationAttemptResponse> getIdDocumentVerificationAttemptAsync(
            final String idDocumentVerificationId, final String attemptId) {
        validateParams("idDocumentVerificationId", idDocumentVerificationId, "attemptId", attemptId);
        return apiClient.getAsync(buildPath(ID_DOCUMENT_VERIFICATIONS_PATH, idDocumentVerificationId, ATTEMPTS_PATH, attemptId),
                sdkAuthorization(), IdDocumentVerificationAttemptResponse.class);
    }

    /**
     * Generate and download a PDF report
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return a {@link CompletableFuture} containing the {@link IdDocumentVerificationReportResponse}
     */
    @Override
    public CompletableFuture<IdDocumentVerificationReportResponse> getIdDocumentVerificationReportAsync(
            final String idDocumentVerificationId) {
        validateParams("idDocumentVerificationId", idDocumentVerificationId);
        return apiClient.getAsync(buildPath(ID_DOCUMENT_VERIFICATIONS_PATH, idDocumentVerificationId, PDF_REPORT_PATH),
                sdkAuthorization(), IdDocumentVerificationReportResponse.class);
    }

    // Synchronous methods

    /**
     * Create an ID document verification
     *
     * @param idDocumentVerificationRequest the ID document verification request
     * @return the {@link IdDocumentVerificationResponse}
     */
    @Override
    public IdDocumentVerificationResponse createIdDocumentVerification(
            final IdDocumentVerificationRequest idDocumentVerificationRequest) {
        validateParams("idDocumentVerificationRequest", idDocumentVerificationRequest);
        return apiClient.post(ID_DOCUMENT_VERIFICATIONS_PATH, sdkAuthorization(), IdDocumentVerificationResponse.class,
                idDocumentVerificationRequest, null);
    }

    /**
     * Retrieve an ID document verification
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return the {@link IdDocumentVerificationResponse}
     */
    @Override
    public IdDocumentVerificationResponse getIdDocumentVerification(final String idDocumentVerificationId) {
        validateParams("idDocumentVerificationId", idDocumentVerificationId);
        return apiClient.get(buildPath(ID_DOCUMENT_VERIFICATIONS_PATH, idDocumentVerificationId), sdkAuthorization(),
                IdDocumentVerificationResponse.class);
    }

    /**
     * Anonymize an ID document verification
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return the {@link IdDocumentVerificationResponse}
     */
    @Override
    public IdDocumentVerificationResponse anonymizeIdDocumentVerification(final String idDocumentVerificationId) {
        validateParams("idDocumentVerificationId", idDocumentVerificationId);
        return apiClient.post(buildPath(ID_DOCUMENT_VERIFICATIONS_PATH, idDocumentVerificationId, ANONYMIZE_PATH),
                sdkAuthorization(), IdDocumentVerificationResponse.class, null, null);
    }

    /**
     * Create an ID document verification attempt
     *
     * @param idDocumentVerificationId             the ID document verification ID
     * @param idDocumentVerificationAttemptRequest the attempt request
     * @return the {@link IdDocumentVerificationAttemptResponse}
     */
    @Override
    public IdDocumentVerificationAttemptResponse createIdDocumentVerificationAttempt(
            final String idDocumentVerificationId,
            final IdDocumentVerificationAttemptRequest idDocumentVerificationAttemptRequest) {
        validateParams("idDocumentVerificationId", idDocumentVerificationId, 
                "idDocumentVerificationAttemptRequest", idDocumentVerificationAttemptRequest);
        return apiClient.post(buildPath(ID_DOCUMENT_VERIFICATIONS_PATH, idDocumentVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), IdDocumentVerificationAttemptResponse.class, idDocumentVerificationAttemptRequest,
                null);
    }

    /**
     * Retrieve all ID document verification attempts
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return the {@link IdDocumentVerificationAttemptsResponse}
     */
    @Override
    public IdDocumentVerificationAttemptsResponse getIdDocumentVerificationAttempts(final String idDocumentVerificationId) {
        validateParams("idDocumentVerificationId", idDocumentVerificationId);
        return apiClient.get(buildPath(ID_DOCUMENT_VERIFICATIONS_PATH, idDocumentVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), IdDocumentVerificationAttemptsResponse.class);
    }

    /**
     * Retrieve a specific ID document verification attempt
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @param attemptId                the attempt ID
     * @return the {@link IdDocumentVerificationAttemptResponse}
     */
    @Override
    public IdDocumentVerificationAttemptResponse getIdDocumentVerificationAttempt(
            final String idDocumentVerificationId, final String attemptId) {
        validateParams("idDocumentVerificationId", idDocumentVerificationId, "attemptId", attemptId);
        return apiClient.get(buildPath(ID_DOCUMENT_VERIFICATIONS_PATH, idDocumentVerificationId, ATTEMPTS_PATH, attemptId),
                sdkAuthorization(), IdDocumentVerificationAttemptResponse.class);
    }

    /**
     * Generate and download a PDF report
     *
     * @param idDocumentVerificationId the ID document verification ID
     * @return the {@link IdDocumentVerificationReportResponse}
     */
    @Override
    public IdDocumentVerificationReportResponse getIdDocumentVerificationReport(final String idDocumentVerificationId) {
        validateParams("idDocumentVerificationId", idDocumentVerificationId);
        return apiClient.get(buildPath(ID_DOCUMENT_VERIFICATIONS_PATH, idDocumentVerificationId, PDF_REPORT_PATH),
                sdkAuthorization(), IdDocumentVerificationReportResponse.class);
    }
}