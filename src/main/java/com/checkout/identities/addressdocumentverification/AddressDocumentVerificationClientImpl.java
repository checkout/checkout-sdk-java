package com.checkout.identities.addressdocumentverification;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.identities.addressdocumentverification.requests.AddressDocumentVerificationAttemptRequest;
import com.checkout.identities.addressdocumentverification.requests.AddressDocumentVerificationRequest;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptsResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationReportResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

/**
 * Implementation of the Address Document Verification client.
 */
public class AddressDocumentVerificationClientImpl extends AbstractClient implements AddressDocumentVerificationClient {

    private static final String ADDRESS_DOCUMENT_VERIFICATIONS_PATH = "address-document-verifications";
    private static final String ANONYMIZE_PATH = "anonymize";
    private static final String ATTEMPTS_PATH = "attempts";
    private static final String PDF_REPORT_PATH = "pdf-report";

    public AddressDocumentVerificationClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<AddressDocumentVerificationResponse> createAddressDocumentVerification(
            final AddressDocumentVerificationRequest addressDocumentVerificationRequest) {
        validateParams("addressDocumentVerificationRequest", addressDocumentVerificationRequest);
        return apiClient.postAsync(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, sdkAuthorization(),
                AddressDocumentVerificationResponse.class, addressDocumentVerificationRequest, null);
    }

    @Override
    public CompletableFuture<AddressDocumentVerificationResponse> getAddressDocumentVerification(
            final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.getAsync(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId),
                sdkAuthorization(), AddressDocumentVerificationResponse.class);
    }

    @Override
    public CompletableFuture<AddressDocumentVerificationResponse> anonymizeAddressDocumentVerification(
            final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.postAsync(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ANONYMIZE_PATH),
                sdkAuthorization(), AddressDocumentVerificationResponse.class, null, null);
    }

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

    @Override
    public CompletableFuture<AddressDocumentVerificationAttemptsResponse> getAddressDocumentVerificationAttempts(
            final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.getAsync(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), AddressDocumentVerificationAttemptsResponse.class);
    }

    @Override
    public CompletableFuture<AddressDocumentVerificationAttemptResponse> getAddressDocumentVerificationAttempt(
            final String addressDocumentVerificationId, final String attemptId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId, "attemptId", attemptId);
        return apiClient.getAsync(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ATTEMPTS_PATH, attemptId),
                sdkAuthorization(), AddressDocumentVerificationAttemptResponse.class);
    }

    @Override
    public CompletableFuture<AddressDocumentVerificationReportResponse> getAddressDocumentVerificationReport(
            final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.getAsync(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, PDF_REPORT_PATH),
                sdkAuthorization(), AddressDocumentVerificationReportResponse.class);
    }

    // Synchronous methods

    @Override
    public AddressDocumentVerificationResponse createAddressDocumentVerificationSync(
            final AddressDocumentVerificationRequest addressDocumentVerificationRequest) {
        validateParams("addressDocumentVerificationRequest", addressDocumentVerificationRequest);
        return apiClient.post(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, sdkAuthorization(),
                AddressDocumentVerificationResponse.class, addressDocumentVerificationRequest, null);
    }

    @Override
    public AddressDocumentVerificationResponse getAddressDocumentVerificationSync(final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.get(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId),
                sdkAuthorization(), AddressDocumentVerificationResponse.class);
    }

    @Override
    public AddressDocumentVerificationResponse anonymizeAddressDocumentVerificationSync(final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.post(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ANONYMIZE_PATH),
                sdkAuthorization(), AddressDocumentVerificationResponse.class, null, null);
    }

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

    @Override
    public AddressDocumentVerificationAttemptsResponse getAddressDocumentVerificationAttemptsSync(final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.get(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ATTEMPTS_PATH),
                sdkAuthorization(), AddressDocumentVerificationAttemptsResponse.class);
    }

    @Override
    public AddressDocumentVerificationAttemptResponse getAddressDocumentVerificationAttemptSync(
            final String addressDocumentVerificationId, final String attemptId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId, "attemptId", attemptId);
        return apiClient.get(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, ATTEMPTS_PATH, attemptId),
                sdkAuthorization(), AddressDocumentVerificationAttemptResponse.class);
    }

    @Override
    public AddressDocumentVerificationReportResponse getAddressDocumentVerificationReportSync(final String addressDocumentVerificationId) {
        validateParams("addressDocumentVerificationId", addressDocumentVerificationId);
        return apiClient.get(buildPath(ADDRESS_DOCUMENT_VERIFICATIONS_PATH, addressDocumentVerificationId, PDF_REPORT_PATH),
                sdkAuthorization(), AddressDocumentVerificationReportResponse.class);
    }
}
