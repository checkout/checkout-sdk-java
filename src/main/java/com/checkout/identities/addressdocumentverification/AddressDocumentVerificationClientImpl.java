package com.checkout.identities.addressdocumentverification;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.identities.addressdocumentverification.requests.AddressDocumentVerificationAttemptRequest;
import com.checkout.identities.addressdocumentverification.requests.AddressDocumentVerificationRequest;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptAssetsResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptsResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationReportResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationResponse;

import com.checkout.identities.entities.AttemptAssetsQueryFilter;
import com.checkout.identities.entities.AttemptsQueryFilter;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

/**
 * Implementation of the Address Document Verification client.
 */
public class AddressDocumentVerificationClientImpl extends AbstractClient implements AddressDocumentVerificationClient {

    private static final String ADDRESS_DOCUMENT_VERIFICATIONS_PATH = "address-document-verifications";
    private static final String ANONYMIZE_PATH = "anonymize";
    private static final String ATTEMPTS_PATH = "attempts";
    private static final String ASSETS_PATH = "assets";
    private static final String PDF_REPORT_PATH = "pdf-report";

    public AddressDocumentVerificationClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    /**
     * Create an address document verification
     *
     * @param addressDocumentVerificationRequest the address document verification request
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationResponse}
     */
    @Override
    public CompletableFuture<AddressDocumentVerificationResponse> createAddressDocumentVerification(
            final AddressDocumentVerificationRequest addressDocumentVerificationRequest) {
        validateParams("addressDocumentVerificationRequest", addressDocumentVerificationRequest);
        return apiClient.postAsync(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, sdkAuthorization(),
                AddressDocumentVerificationResponse.class, addressDocumentVerificationRequest, null);
    }

    /**
     * Retrieve an address document verification
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationResponse}
     */
    @Override
    public CompletableFuture<AddressDocumentVerificationResponse> getAddressDocumentVerification(
            final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.getAsync(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId),
                sdkAuthorization(), AddressDocumentVerificationResponse.class);
    }

    /**
     * Anonymize an address document verification
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationResponse}
     */
    @Override
    public CompletableFuture<AddressDocumentVerificationResponse> anonymizeAddressDocumentVerification(
            final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.postAsync(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ANONYMIZE_PATH),
                sdkAuthorization(), AddressDocumentVerificationResponse.class, null, null);
    }

    /**
     * Create an address document verification attempt
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @param addressDocumentVerificationAttemptRequest the attempt request
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationAttemptResponse}
     */
    @Override
    public CompletableFuture<AddressDocumentVerificationAttemptResponse> createAddressDocumentVerificationAttempt(
            final String addressDocumentVerificationId,
            final AddressDocumentVerificationAttemptRequest addressDocumentVerificationAttemptRequest) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId,
                "addressDocumentVerificationAttemptRequest", addressDocumentVerificationAttemptRequest);
        return apiClient.postAsync(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), AddressDocumentVerificationAttemptResponse.class, addressDocumentVerificationAttemptRequest,
                null);
    }

    /**
     * Retrieve all address document verification attempts
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationAttemptsResponse}
     */
    @Override
    public CompletableFuture<AddressDocumentVerificationAttemptsResponse> getAddressDocumentVerificationAttempts(
            final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.getAsync(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), AddressDocumentVerificationAttemptsResponse.class);
    }

    /**
     * Retrieve a page of address document verification attempts
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @param queryFilter the pagination query parameters (skip and limit)
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationAttemptsResponse}
     */
    @Override
    public CompletableFuture<AddressDocumentVerificationAttemptsResponse> getAddressDocumentVerificationAttempts(
            final String addressDocumentVerificationId, final AttemptsQueryFilter queryFilter) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.queryAsync(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), queryFilter, AddressDocumentVerificationAttemptsResponse.class);
    }

    /**
     * Retrieve a specific address document verification attempt
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @param attemptId the attempt ID
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationAttemptResponse}
     */
    @Override
    public CompletableFuture<AddressDocumentVerificationAttemptResponse> getAddressDocumentVerificationAttempt(
            final String addressDocumentVerificationId, final String attemptId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId, "attemptId", attemptId);
        return apiClient.getAsync(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ATTEMPTS_PATH, attemptId),
                sdkAuthorization(), AddressDocumentVerificationAttemptResponse.class);
    }

    /**
     * Generate and download a PDF report
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationReportResponse}
     */
    @Override
    public CompletableFuture<AddressDocumentVerificationReportResponse> getAddressDocumentVerificationReport(
            final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.getAsync(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, PDF_REPORT_PATH),
                sdkAuthorization(), AddressDocumentVerificationReportResponse.class);
    }

    /**
     * Retrieve the assets (the document image) uploaded for an address document verification attempt
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @param attemptId the attempt ID
     * @param queryFilter the pagination query parameters (skip and limit)
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationAttemptAssetsResponse}
     */
    @Override
    public CompletableFuture<AddressDocumentVerificationAttemptAssetsResponse> getAddressDocumentVerificationAttemptAssets(
            final String addressDocumentVerificationId, final String attemptId, final AttemptAssetsQueryFilter queryFilter) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId, "attemptId", attemptId);
        return apiClient.queryAsync(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ATTEMPTS_PATH, attemptId, ASSETS_PATH),
                sdkAuthorization(), queryFilter, AddressDocumentVerificationAttemptAssetsResponse.class);
    }

    // Synchronous methods

    /**
     * Create an address document verification
     *
     * @param addressDocumentVerificationRequest the address document verification request
     * @return the {@link AddressDocumentVerificationResponse}
     */
    @Override
    public AddressDocumentVerificationResponse createAddressDocumentVerificationSync(
            final AddressDocumentVerificationRequest addressDocumentVerificationRequest) {
        validateParams("addressDocumentVerificationRequest", addressDocumentVerificationRequest);
        return apiClient.post(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, sdkAuthorization(),
                AddressDocumentVerificationResponse.class, addressDocumentVerificationRequest, null);
    }

    /**
     * Retrieve an address document verification
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return the {@link AddressDocumentVerificationResponse}
     */
    @Override
    public AddressDocumentVerificationResponse getAddressDocumentVerificationSync(final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.get(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId),
                sdkAuthorization(), AddressDocumentVerificationResponse.class);
    }

    /**
     * Anonymize an address document verification
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return the {@link AddressDocumentVerificationResponse}
     */
    @Override
    public AddressDocumentVerificationResponse anonymizeAddressDocumentVerificationSync(final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.post(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ANONYMIZE_PATH),
                sdkAuthorization(), AddressDocumentVerificationResponse.class, null, null);
    }

    /**
     * Create an address document verification attempt
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @param attemptRequest the attempt request
     * @return the {@link AddressDocumentVerificationAttemptResponse}
     */
    @Override
    public AddressDocumentVerificationAttemptResponse createAddressDocumentVerificationAttemptSync(
            final String addressDocumentVerificationId,
            final AddressDocumentVerificationAttemptRequest addressDocumentVerificationAttemptRequest) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId,
                "addressDocumentVerificationAttemptRequest", addressDocumentVerificationAttemptRequest);
        return apiClient.post(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), AddressDocumentVerificationAttemptResponse.class, addressDocumentVerificationAttemptRequest,
                null);
    }

    /**
     * Retrieve all address document verification attempts
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return the {@link AddressDocumentVerificationAttemptsResponse}
     */
    @Override
    public AddressDocumentVerificationAttemptsResponse getAddressDocumentVerificationAttemptsSync(final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.get(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), AddressDocumentVerificationAttemptsResponse.class);
    }

    /**
     * Retrieve a page of address document verification attempts
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @param queryFilter the pagination query parameters (skip and limit)
     * @return the {@link AddressDocumentVerificationAttemptsResponse}
     */
    @Override
    public AddressDocumentVerificationAttemptsResponse getAddressDocumentVerificationAttemptsSync(
            final String addressDocumentVerificationId, final AttemptsQueryFilter queryFilter) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.query(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), queryFilter, AddressDocumentVerificationAttemptsResponse.class);
    }

    /**
     * Retrieve a specific address document verification attempt
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @param attemptId the attempt ID
     * @return the {@link AddressDocumentVerificationAttemptResponse}
     */
    @Override
    public AddressDocumentVerificationAttemptResponse getAddressDocumentVerificationAttemptSync(
            final String addressDocumentVerificationId, final String attemptId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId, "attemptId", attemptId);
        return apiClient.get(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ATTEMPTS_PATH, attemptId),
                sdkAuthorization(), AddressDocumentVerificationAttemptResponse.class);
    }

    /**
     * Generate and download a PDF report
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return the {@link AddressDocumentVerificationReportResponse}
     */
    @Override
    public AddressDocumentVerificationReportResponse getAddressDocumentVerificationReportSync(final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.get(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, PDF_REPORT_PATH),
                sdkAuthorization(), AddressDocumentVerificationReportResponse.class);
    }

    /**
     * Retrieve the assets (the document image) uploaded for an address document verification attempt
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @param attemptId the attempt ID
     * @param queryFilter the pagination query parameters (skip and limit)
     * @return the {@link AddressDocumentVerificationAttemptAssetsResponse}
     */
    @Override
    public AddressDocumentVerificationAttemptAssetsResponse getAddressDocumentVerificationAttemptAssetsSync(
            final String addressDocumentVerificationId, final String attemptId, final AttemptAssetsQueryFilter queryFilter) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId, "attemptId", attemptId);
        return apiClient.query(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ATTEMPTS_PATH, attemptId, ASSETS_PATH),
                sdkAuthorization(), queryFilter, AddressDocumentVerificationAttemptAssetsResponse.class);
    }
}
