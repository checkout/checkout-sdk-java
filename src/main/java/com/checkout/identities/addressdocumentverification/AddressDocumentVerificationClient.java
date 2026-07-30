package com.checkout.identities.addressdocumentverification;

import com.checkout.identities.addressdocumentverification.requests.AddressDocumentVerificationAttemptRequest;
import com.checkout.identities.addressdocumentverification.requests.AddressDocumentVerificationRequest;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptsResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationReportResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Client for address document verification operations
 */
public interface AddressDocumentVerificationClient {

    /**
     * Create an address document verification
     *
     * @param addressDocumentVerificationRequest the address document verification request
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationResponse}
     */
    CompletableFuture<AddressDocumentVerificationResponse> createAddressDocumentVerification(AddressDocumentVerificationRequest addressDocumentVerificationRequest);

    /**
     * Retrieve an address document verification
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationResponse}
     */
    CompletableFuture<AddressDocumentVerificationResponse> getAddressDocumentVerification(String addressDocumentVerificationId);

    /**
     * Anonymize an address document verification
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationResponse}
     */
    CompletableFuture<AddressDocumentVerificationResponse> anonymizeAddressDocumentVerification(String addressDocumentVerificationId);

    /**
     * Create an address document verification attempt
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @param addressDocumentVerificationAttemptRequest the attempt request
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationAttemptResponse}
     */
    CompletableFuture<AddressDocumentVerificationAttemptResponse> createAddressDocumentVerificationAttempt(String addressDocumentVerificationId, AddressDocumentVerificationAttemptRequest addressDocumentVerificationAttemptRequest);

    /**
     * Retrieve all address document verification attempts
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationAttemptsResponse}
     */
    CompletableFuture<AddressDocumentVerificationAttemptsResponse> getAddressDocumentVerificationAttempts(String addressDocumentVerificationId);

    /**
     * Retrieve a specific address document verification attempt
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @param attemptId the attempt ID
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationAttemptResponse}
     */
    CompletableFuture<AddressDocumentVerificationAttemptResponse> getAddressDocumentVerificationAttempt(String addressDocumentVerificationId, String attemptId);

    /**
     * Generate and download a PDF report
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return a {@link CompletableFuture} containing the {@link AddressDocumentVerificationReportResponse}
     */
    CompletableFuture<AddressDocumentVerificationReportResponse> getAddressDocumentVerificationReport(String addressDocumentVerificationId);

    // Synchronous methods

    /**
     * Create an address document verification
     *
     * @param addressDocumentVerificationRequest the address document verification request
     * @return the {@link AddressDocumentVerificationResponse}
     */
    AddressDocumentVerificationResponse createAddressDocumentVerificationSync(AddressDocumentVerificationRequest addressDocumentVerificationRequest);

    /**
     * Retrieve an address document verification
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return the {@link AddressDocumentVerificationResponse}
     */
    AddressDocumentVerificationResponse getAddressDocumentVerificationSync(String addressDocumentVerificationId);

    /**
     * Anonymize an address document verification
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return the {@link AddressDocumentVerificationResponse}
     */
    AddressDocumentVerificationResponse anonymizeAddressDocumentVerificationSync(String addressDocumentVerificationId);

    /**
     * Create an address document verification attempt
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @param addressDocumentVerificationAttemptRequest the attempt request
     * @return the {@link AddressDocumentVerificationAttemptResponse}
     */
    AddressDocumentVerificationAttemptResponse createAddressDocumentVerificationAttemptSync(String addressDocumentVerificationId, AddressDocumentVerificationAttemptRequest addressDocumentVerificationAttemptRequest);

    /**
     * Retrieve all address document verification attempts
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return the {@link AddressDocumentVerificationAttemptsResponse}
     */
    AddressDocumentVerificationAttemptsResponse getAddressDocumentVerificationAttemptsSync(String addressDocumentVerificationId);

    /**
     * Retrieve a specific address document verification attempt
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @param attemptId the attempt ID
     * @return the {@link AddressDocumentVerificationAttemptResponse}
     */
    AddressDocumentVerificationAttemptResponse getAddressDocumentVerificationAttemptSync(String addressDocumentVerificationId, String attemptId);

    /**
     * Generate and download a PDF report
     *
     * @param addressDocumentVerificationId the address document verification ID
     * @return the {@link AddressDocumentVerificationReportResponse}
     */
    AddressDocumentVerificationReportResponse getAddressDocumentVerificationReportSync(String addressDocumentVerificationId);
}
