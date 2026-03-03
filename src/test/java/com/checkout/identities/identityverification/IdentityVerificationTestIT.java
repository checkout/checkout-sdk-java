package com.checkout.identities.identityverification;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.identities.identityverification.requests.CreateAndOpenIdentityVerificationRequest;
import com.checkout.identities.identityverification.requests.IdentityVerificationRequest;
import com.checkout.identities.identityverification.requests.IdentityVerificationAttemptRequest;
import com.checkout.identities.identityverification.responses.IdentityVerificationAttemptResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationAttemptsResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationReportResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdentityVerificationTestIT extends SandboxTestFixture {

    IdentityVerificationTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    @Disabled("Integration test - requires valid test data")
    void shouldCreateAndOpenIdentityVerification() {
        // Arrange
        final CreateAndOpenIdentityVerificationRequest request = createCreateAndOpenIdentityVerificationRequest();

        // Act
        final IdentityVerificationResponse response = blocking(() -> 
                checkoutApi.identityVerificationClient().createAndOpenIdentityVerificationAsync(request));

        // Assert
        validateCreatedAndOpenedIdentityVerification(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid test data")
    void shouldCreateIdentityVerification() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();

        // Act
        final IdentityVerificationResponse response = blocking(() -> 
                checkoutApi.identityVerificationClient().createIdentityVerificationAsync(request));

        // Assert
        validateCreatedIdentityVerification(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldGetIdentityVerification() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = blocking(() ->
                checkoutApi.identityVerificationClient().createIdentityVerificationAsync(request));

        // Act
        final IdentityVerificationResponse retrieved = blocking(() ->
                checkoutApi.identityVerificationClient().getIdentityVerificationAsync(created.getId()));

        // Assert
        validateRetrievedIdentityVerification(retrieved, created);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldUpdateIdentityVerification() {
        // Arrange
        final IdentityVerificationRequest createRequest = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = blocking(() ->
                checkoutApi.identityVerificationClient().createIdentityVerificationAsync(createRequest));
        final IdentityVerificationRequest updateRequest = createIdentityVerificationUpdateRequest();

        // Act
        final IdentityVerificationResponse updated = blocking(() ->
                checkoutApi.identityVerificationClient().updateIdentityVerificationAsync(created.getId(), updateRequest));

        // Assert
        validateUpdatedIdentityVerification(updated, updateRequest);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldCreateIdentityVerificationAttempt() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = blocking(() ->
                checkoutApi.identityVerificationClient().createIdentityVerificationAsync(request));
        final IdentityVerificationAttemptRequest attemptRequest = createIdentityVerificationAttemptRequest();

        // Act
        final IdentityVerificationAttemptResponse attempt = blocking(() ->
                checkoutApi.identityVerificationClient().createIdentityVerificationAttemptAsync(created.getId(), attemptRequest));

        // Assert
        validateCreatedIdentityVerificationAttempt(attempt, attemptRequest);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldGetIdentityVerificationAttempts() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = blocking(() ->
                checkoutApi.identityVerificationClient().createIdentityVerificationAsync(request));
        final IdentityVerificationAttemptRequest attemptRequest = createIdentityVerificationAttemptRequest();
        final IdentityVerificationAttemptResponse createdAttempt = blocking(() ->
                checkoutApi.identityVerificationClient().createIdentityVerificationAttemptAsync(created.getId(), attemptRequest));

        // Act
        final IdentityVerificationAttemptsResponse attempts = blocking(() ->
                checkoutApi.identityVerificationClient().getIdentityVerificationAttemptsAsync(created.getId()));

        // Assert
        validateRetrievedIdentityVerificationAttempts(attempts, createdAttempt);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldGenerateIdentityVerificationReport() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = blocking(() ->
                checkoutApi.identityVerificationClient().createIdentityVerificationAsync(request));

        // Act
        final IdentityVerificationReportResponse report = blocking(() ->
                checkoutApi.identityVerificationClient().generateIdentityVerificationReportAsync(created.getId()));

        // Assert
        validateGeneratedIdentityVerificationReport(report);
    }

    // Synchronous methods tests
    @Test
    @Disabled("Integration test - requires valid test data")
    void shouldCreateAndOpenIdentityVerificationSync() {
        // Arrange
        final CreateAndOpenIdentityVerificationRequest request = createCreateAndOpenIdentityVerificationRequest();

        // Act
        final IdentityVerificationResponse response = checkoutApi.identityVerificationClient().createAndOpenIdentityVerification(request);

        // Assert
        validateCreatedAndOpenedIdentityVerification(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid test data")
    void shouldCreateIdentityVerificationSync() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();

        // Act
        final IdentityVerificationResponse response = checkoutApi.identityVerificationClient().createIdentityVerification(request);

        // Assert
        validateCreatedIdentityVerification(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldGetIdentityVerificationSync() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = checkoutApi.identityVerificationClient().createIdentityVerification(request);

        // Act
        final IdentityVerificationResponse retrieved = checkoutApi.identityVerificationClient().getIdentityVerification(created.getId());

        // Assert
        validateRetrievedIdentityVerification(retrieved, created);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldUpdateIdentityVerificationSync() {
        // Arrange
        final IdentityVerificationRequest createRequest = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = checkoutApi.identityVerificationClient().createIdentityVerification(createRequest);
        final IdentityVerificationRequest updateRequest = createIdentityVerificationUpdateRequest();

        // Act
        final IdentityVerificationResponse updated = checkoutApi.identityVerificationClient().updateIdentityVerification(created.getId(), updateRequest);

        // Assert
        validateUpdatedIdentityVerification(updated, updateRequest);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldCreateIdentityVerificationAttemptSync() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = checkoutApi.identityVerificationClient().createIdentityVerification(request);
        final IdentityVerificationAttemptRequest attemptRequest = createIdentityVerificationAttemptRequest();

        // Act
        final IdentityVerificationAttemptResponse attempt = checkoutApi.identityVerificationClient().createIdentityVerificationAttempt(created.getId(), attemptRequest);

        // Assert
        validateCreatedIdentityVerificationAttempt(attempt, attemptRequest);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldGetIdentityVerificationAttemptsSync() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = checkoutApi.identityVerificationClient().createIdentityVerification(request);
        final IdentityVerificationAttemptRequest attemptRequest = createIdentityVerificationAttemptRequest();
        final IdentityVerificationAttemptResponse createdAttempt = checkoutApi.identityVerificationClient().createIdentityVerificationAttempt(created.getId(), attemptRequest);

        // Act
        final IdentityVerificationAttemptsResponse attempts = checkoutApi.identityVerificationClient().getIdentityVerificationAttempts(created.getId());

        // Assert
        validateRetrievedIdentityVerificationAttempts(attempts, createdAttempt);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldGenerateIdentityVerificationReportSync() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = checkoutApi.identityVerificationClient().createIdentityVerification(request);

        // Act
        final IdentityVerificationReportResponse report = checkoutApi.identityVerificationClient().generateIdentityVerificationReport(created.getId());

        // Assert
        validateGeneratedIdentityVerificationReport(report);
    }

    // Common methods
    private static CreateAndOpenIdentityVerificationRequest createCreateAndOpenIdentityVerificationRequest() {
        return CreateAndOpenIdentityVerificationRequest.builder()
                .applicantId("aplt_" + generateRandomString(8))
                .userJourneyId("idv_uj_" + generateRandomString(8))
                .redirectUrl("https://example.com/callback")
                .declaredData(CreateAndOpenIdentityVerificationRequest.DeclaredData.builder()
                        .name("John Doe")
                        .build())
                .build();
    }

    private static IdentityVerificationRequest createIdentityVerificationRequest() {
        return IdentityVerificationRequest.builder()
                .applicantId("aplt_" + generateRandomString(8))
                .userJourneyId("idv_uj_" + generateRandomString(8))
                .declaredData(IdentityVerificationRequest.DeclaredData.builder()
                        .name("Jane Smith")
                        .build())
                .build();
    }

    private static IdentityVerificationRequest createIdentityVerificationUpdateRequest() {
        return IdentityVerificationRequest.builder()
                .applicantId("aplt_" + generateRandomString(8))
                .userJourneyId("idv_uj_" + generateRandomString(8))
                .declaredData(IdentityVerificationRequest.DeclaredData.builder()
                        .name("Updated Jane Smith")
                        .build())
                .build();
    }

    private static IdentityVerificationAttemptRequest createIdentityVerificationAttemptRequest() {
        return IdentityVerificationAttemptRequest.builder()
                .redirectUrl("https://example.com/callback")
                .verificationInformation(IdentityVerificationAttemptRequest.VerificationInformation.builder()
                        .deviceFingerprint("device_fp_" + generateRandomString(8))
                        .ipAddress("192.168.1.100")
                        .userAgent("Mozilla/5.0 TestAgent")
                        .build())
                .build();
    }

    private static void validateCreatedAndOpenedIdentityVerification(final IdentityVerificationResponse response, final CreateAndOpenIdentityVerificationRequest request) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getId().startsWith("idv_"));
        assertNotNull(response.getCreatedOn());
        validateCommonIdentityVerificationFields(response);
    }

    private static void validateCreatedIdentityVerification(final IdentityVerificationResponse response, final IdentityVerificationRequest request) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getId().startsWith("idv_"));
        assertNotNull(response.getCreatedOn());
        validateCommonIdentityVerificationFields(response);
    }

    private static void validateRetrievedIdentityVerification(final IdentityVerificationResponse retrieved, final IdentityVerificationResponse created) {
        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals(created.getCreatedOn(), retrieved.getCreatedOn());
        validateCommonIdentityVerificationFields(retrieved);
    }

    private static void validateUpdatedIdentityVerification(final IdentityVerificationResponse response, final IdentityVerificationRequest request) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getId().startsWith("idv_"));
        validateCommonIdentityVerificationFields(response);
    }

    private static void validateCreatedIdentityVerificationAttempt(final IdentityVerificationAttemptResponse response, final IdentityVerificationAttemptRequest request) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getId().startsWith("idva_"));
        assertNotNull(response.getCreatedOn());
        validateCommonIdentityVerificationAttemptFields(response);
    }

    private static void validateRetrievedIdentityVerificationAttempts(final IdentityVerificationAttemptsResponse response, final IdentityVerificationAttemptResponse createdAttempt) {
        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.getData().size() > 0);
        // Verify the created attempt is in the list
        assertTrue(response.getData().stream().anyMatch(attempt -> attempt.getId().equals(createdAttempt.getId())));
    }

    private static void validateGeneratedIdentityVerificationReport(final IdentityVerificationReportResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getCreatedOn());
        // May have URL and expiration depending on implementation
        validateCommonIdentityVerificationReportFields(response);
    }

    private static void validateCommonIdentityVerificationFields(final IdentityVerificationResponse response) {
        assertNotNull(response.getStatus());
        assertNotNull(response.getLinks());
        assertNotNull(response.getSelfLink());
        assertNotNull(response.getSelfLink().getHref());
    }

    private static void validateCommonIdentityVerificationAttemptFields(final IdentityVerificationAttemptResponse response) {
        assertNotNull(response.getStatus());
        assertNotNull(response.getLinks());
        assertNotNull(response.getSelfLink());
        assertNotNull(response.getSelfLink().getHref());
    }

    private static void validateCommonIdentityVerificationReportFields(final IdentityVerificationReportResponse response) {
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