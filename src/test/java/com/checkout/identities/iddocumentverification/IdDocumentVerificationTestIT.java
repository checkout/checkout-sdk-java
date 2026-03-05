package com.checkout.identities.iddocumentverification;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.identities.entities.DeclaredData;
import com.checkout.identities.iddocumentverification.requests.IdDocumentVerificationAttemptRequest;
import com.checkout.identities.iddocumentverification.requests.IdDocumentVerificationRequest;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationAttemptResponse;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationAttemptsResponse;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationReportResponse;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdDocumentVerificationTestIT extends SandboxTestFixture {

    IdDocumentVerificationTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    @Disabled("Integration test - requires valid test data")
    void shouldCreateIdDocumentVerification() {
        // Arrange
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();

        // Act
        final IdDocumentVerificationResponse response = blocking(() -> 
                checkoutApi.idDocumentVerificationClient().createIdDocumentVerificationAsync(request));

        // Assert
        validateCreatedIdDocumentVerification(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid ID document verification ID")
    void shouldGetIdDocumentVerification() {
        // Arrange
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse created = blocking(() ->
                checkoutApi.idDocumentVerificationClient().createIdDocumentVerificationAsync(request));

        // Act
        final IdDocumentVerificationResponse retrieved = blocking(() ->
                checkoutApi.idDocumentVerificationClient().getIdDocumentVerificationAsync(created.getId()));

        // Assert
        validateRetrievedIdDocumentVerification(retrieved, created);
    }

    @Test
    @Disabled("Integration test - requires valid ID document verification ID")
    void shouldAnonymizeIdDocumentVerification() {
        // Arrange
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse created = blocking(() ->
                checkoutApi.idDocumentVerificationClient().createIdDocumentVerificationAsync(request));

        // Act
        final IdDocumentVerificationResponse anonymized = blocking(() ->
                checkoutApi.idDocumentVerificationClient().anonymizeIdDocumentVerificationAsync(created.getId()));

        // Assert
        validateAnonymizedIdDocumentVerification(anonymized);
    }

    @Test
    @Disabled("Integration test - requires valid ID document verification ID")
    void shouldCreateIdDocumentVerificationAttempt() {
        // Arrange
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse created = blocking(() ->
                checkoutApi.idDocumentVerificationClient().createIdDocumentVerificationAsync(request));
        final IdDocumentVerificationAttemptRequest attemptRequest = createIdDocumentVerificationAttemptRequest();

        // Act
        final IdDocumentVerificationAttemptResponse attempt = blocking(() ->
                checkoutApi.idDocumentVerificationClient().createIdDocumentVerificationAttemptAsync(created.getId(), attemptRequest));

        // Assert
        validateCreatedIdDocumentVerificationAttempt(attempt, attemptRequest);
    }

    @Test
    @Disabled("Integration test - requires valid ID document verification ID")
    void shouldGetIdDocumentVerificationAttempts() {
        // Arrange
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse created = blocking(() ->
                checkoutApi.idDocumentVerificationClient().createIdDocumentVerificationAsync(request));
        final IdDocumentVerificationAttemptRequest attemptRequest = createIdDocumentVerificationAttemptRequest();
        final IdDocumentVerificationAttemptResponse createdAttempt = blocking(() ->
                checkoutApi.idDocumentVerificationClient().createIdDocumentVerificationAttemptAsync(created.getId(), attemptRequest));

        // Act
        final IdDocumentVerificationAttemptsResponse attempts = blocking(() ->
                checkoutApi.idDocumentVerificationClient().getIdDocumentVerificationAttemptsAsync(created.getId()));

        // Assert
        validateRetrievedIdDocumentVerificationAttempts(attempts, createdAttempt);
    }

    @Test
    @Disabled("Integration test - requires valid ID document verification ID")
    void shouldGetIdDocumentVerificationAttempt() {
        // Arrange
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse created = blocking(() ->
                checkoutApi.idDocumentVerificationClient().createIdDocumentVerificationAsync(request));
        final IdDocumentVerificationAttemptRequest attemptRequest = createIdDocumentVerificationAttemptRequest();
        final IdDocumentVerificationAttemptResponse createdAttempt = blocking(() ->
                checkoutApi.idDocumentVerificationClient().createIdDocumentVerificationAttemptAsync(created.getId(), attemptRequest));

        // Act
        final IdDocumentVerificationAttemptResponse retrievedAttempt = blocking(() ->
                checkoutApi.idDocumentVerificationClient().getIdDocumentVerificationAttemptAsync(created.getId(), createdAttempt.getId()));

        // Assert
        validateRetrievedIdDocumentVerificationAttempt(retrievedAttempt, createdAttempt);
    }

    @Test
    @Disabled("Integration test - requires valid ID document verification ID")
    void shouldGetIdDocumentVerificationReport() {
        // Arrange
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse created = blocking(() ->
                checkoutApi.idDocumentVerificationClient().createIdDocumentVerificationAsync(request));

        // Act
        final IdDocumentVerificationReportResponse report = blocking(() ->
                checkoutApi.idDocumentVerificationClient().getIdDocumentVerificationReportAsync(created.getId()));

        // Assert
        validateGeneratedIdDocumentVerificationReport(report);
    }

    // Synchronous methods tests
    @Test
    @Disabled("Integration test - requires valid test data")
    void shouldCreateIdDocumentVerificationSync() {
        // Arrange
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();

        // Act
        final IdDocumentVerificationResponse response = checkoutApi.idDocumentVerificationClient().createIdDocumentVerification(request);

        // Assert
        validateCreatedIdDocumentVerification(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid ID document verification ID")
    void shouldGetIdDocumentVerificationSync() {
        // Arrange
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse created = checkoutApi.idDocumentVerificationClient().createIdDocumentVerification(request);

        // Act
        final IdDocumentVerificationResponse retrieved = checkoutApi.idDocumentVerificationClient().getIdDocumentVerification(created.getId());

        // Assert
        validateRetrievedIdDocumentVerification(retrieved, created);
    }

    @Test
    @Disabled("Integration test - requires valid ID document verification ID")
    void shouldAnonymizeIdDocumentVerificationSync() {
        // Arrange
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse created = checkoutApi.idDocumentVerificationClient().createIdDocumentVerification(request);

        // Act
        final IdDocumentVerificationResponse anonymized = checkoutApi.idDocumentVerificationClient().anonymizeIdDocumentVerification(created.getId());

        // Assert
        validateAnonymizedIdDocumentVerification(anonymized);
    }

    @Test
    @Disabled("Integration test - requires valid ID document verification ID")
    void shouldCreateIdDocumentVerificationAttemptSync() {
        // Arrange
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse created = checkoutApi.idDocumentVerificationClient().createIdDocumentVerification(request);
        final IdDocumentVerificationAttemptRequest attemptRequest = createIdDocumentVerificationAttemptRequest();

        // Act
        final IdDocumentVerificationAttemptResponse attempt = checkoutApi.idDocumentVerificationClient().createIdDocumentVerificationAttempt(created.getId(), attemptRequest);

        // Assert
        validateCreatedIdDocumentVerificationAttempt(attempt, attemptRequest);
    }

    @Test
    @Disabled("Integration test - requires valid ID document verification ID")
    void shouldGetIdDocumentVerificationAttemptsSync() {
        // Arrange
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse created = checkoutApi.idDocumentVerificationClient().createIdDocumentVerification(request);
        final IdDocumentVerificationAttemptRequest attemptRequest = createIdDocumentVerificationAttemptRequest();
        final IdDocumentVerificationAttemptResponse createdAttempt = checkoutApi.idDocumentVerificationClient().createIdDocumentVerificationAttempt(created.getId(), attemptRequest);

        // Act
        final IdDocumentVerificationAttemptsResponse attempts = checkoutApi.idDocumentVerificationClient().getIdDocumentVerificationAttempts(created.getId());

        // Assert
        validateRetrievedIdDocumentVerificationAttempts(attempts, createdAttempt);
    }

    @Test
    @Disabled("Integration test - requires valid ID document verification ID")
    void shouldGetIdDocumentVerificationAttemptSync() {
        // Arrange
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse created = checkoutApi.idDocumentVerificationClient().createIdDocumentVerification(request);
        final IdDocumentVerificationAttemptRequest attemptRequest = createIdDocumentVerificationAttemptRequest();
        final IdDocumentVerificationAttemptResponse createdAttempt = checkoutApi.idDocumentVerificationClient().createIdDocumentVerificationAttempt(created.getId(), attemptRequest);

        // Act
        final IdDocumentVerificationAttemptResponse retrievedAttempt = checkoutApi.idDocumentVerificationClient().getIdDocumentVerificationAttempt(created.getId(), createdAttempt.getId());

        // Assert
        validateRetrievedIdDocumentVerificationAttempt(retrievedAttempt, createdAttempt);
    }

    @Test
    @Disabled("Integration test - requires valid ID document verification ID")
    void shouldGetIdDocumentVerificationReportSync() {
        // Arrange
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse created = checkoutApi.idDocumentVerificationClient().createIdDocumentVerification(request);

        // Act
        final IdDocumentVerificationReportResponse report = checkoutApi.idDocumentVerificationClient().getIdDocumentVerificationReport(created.getId());

        // Assert
        validateGeneratedIdDocumentVerificationReport(report);
    }

    // Common methods
    private static IdDocumentVerificationRequest createIdDocumentVerificationRequest() {
        return IdDocumentVerificationRequest.builder()
                .applicantId("aplt_" + generateRandomString(8))
                .userJourneyId("usj_" + generateRandomString(26))
                .declaredData(DeclaredData.builder()
                        .name("John Doe")
                        .build())
                .build();
    }

    private static IdDocumentVerificationAttemptRequest createIdDocumentVerificationAttemptRequest() {
        return IdDocumentVerificationAttemptRequest.builder()
                .documentFront("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD...")
                .documentBack("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...")
                .build();
    }

    private static void validateCreatedIdDocumentVerification(final IdDocumentVerificationResponse response, final IdDocumentVerificationRequest request) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getId().startsWith("iddv_"));
        assertNotNull(response.getCreatedOn());
        validateCommonIdDocumentVerificationFields(response);
    }

    private static void validateRetrievedIdDocumentVerification(final IdDocumentVerificationResponse retrieved, final IdDocumentVerificationResponse created) {
        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals(created.getCreatedOn(), retrieved.getCreatedOn());
        validateCommonIdDocumentVerificationFields(retrieved);
    }

    private static void validateAnonymizedIdDocumentVerification(final IdDocumentVerificationResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getId().startsWith("iddv_"));
        assertNotNull(response.getStatus());
        // After anonymization, personal data may be removed or modified
        validateCommonIdDocumentVerificationFields(response);
    }

    private static void validateCreatedIdDocumentVerificationAttempt(final IdDocumentVerificationAttemptResponse response, final IdDocumentVerificationAttemptRequest request) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getId().startsWith("datp_"));
        assertNotNull(response.getCreatedOn());
        validateCommonIdDocumentVerificationAttemptFields(response);
    }

    private static void validateRetrievedIdDocumentVerificationAttempts(final IdDocumentVerificationAttemptsResponse response, final IdDocumentVerificationAttemptResponse createdAttempt) {
        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.getData().size() > 0);
        // Verify the created attempt is in the list
        assertTrue(response.getData().stream().anyMatch(attempt -> attempt.getId().equals(createdAttempt.getId())));
    }

    private static void validateRetrievedIdDocumentVerificationAttempt(final IdDocumentVerificationAttemptResponse retrieved, final IdDocumentVerificationAttemptResponse created) {
        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals(created.getCreatedOn(), retrieved.getCreatedOn());
        validateCommonIdDocumentVerificationAttemptFields(retrieved);
    }

    private static void validateGeneratedIdDocumentVerificationReport(final IdDocumentVerificationReportResponse response) {
        assertNotNull(response);
        // May have signed_url and pdf_report depending on implementation
        validateCommonIdDocumentVerificationReportFields(response);
    }

    private static void validateCommonIdDocumentVerificationFields(final IdDocumentVerificationResponse response) {
        assertNotNull(response.getStatus());
        assertNotNull(response.getLinks());
        assertNotNull(response.getSelfLink());
        assertNotNull(response.getSelfLink().getHref());
    }

    private static void validateCommonIdDocumentVerificationAttemptFields(final IdDocumentVerificationAttemptResponse response) {
        assertNotNull(response.getStatus());
        assertNotNull(response.getLinks());
        assertNotNull(response.getSelfLink());
        assertNotNull(response.getSelfLink().getHref());
    }

    private static void validateCommonIdDocumentVerificationReportFields(final IdDocumentVerificationReportResponse response) {
        assertNotNull(response.getLinks());
        assertNotNull(response.getSelfLink());
        assertNotNull(response.getSelfLink().getHref());
    }

    /**
     * Generates a random string for test data.
     */
    private static String generateRandomString(final int length) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }
}