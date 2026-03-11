package com.checkout.identities.identityverification;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.identities.entities.ClientInformation;
import com.checkout.identities.entities.DeclaredData;
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

    // Async methods

    @Test
    @Disabled("Integration test - requires valid applicant and user journey")
    void shouldCreateAndOpenIdentityVerificationAsync() {
        // Arrange
        final CreateAndOpenIdentityVerificationRequest request = createCreateAndOpenIdentityVerificationRequest();

        // Act
        final IdentityVerificationResponse response = blocking(() -> 
                checkoutApi.identityVerificationClient().createAndOpenIdentityVerificationAsync(request));

        // Assert
        validateCreatedAndOpenedIdentityVerification(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid applicant and user journey")
    void shouldCreateIdentityVerificationAsync() {
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
    void shouldGetIdentityVerificationAsync() {
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
    void shouldAnonymizeIdentityVerificationAsync() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = blocking(() ->
                checkoutApi.identityVerificationClient().createIdentityVerificationAsync(request));

        // Act
        final IdentityVerificationResponse anonymized = blocking(() ->
                checkoutApi.identityVerificationClient().anonymizeIdentityVerificationAsync(created.getId()));

        // Assert
        validateAnonymizedIdentityVerification(anonymized);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldGetIdentityVerificationAttemptsAsync() {
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
    void shouldCreateIdentityVerificationAttemptAsync() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = blocking(() ->
                checkoutApi.identityVerificationClient().createIdentityVerificationAsync(request));
        final IdentityVerificationAttemptRequest attemptRequest = createIdentityVerificationAttemptRequest();

        // Act
        final IdentityVerificationAttemptResponse attempt = blocking(() ->
                checkoutApi.identityVerificationClient().createIdentityVerificationAttemptAsync(created.getId(), attemptRequest));

        // Assert
        validateCreatedIdentityVerificationAttempt(attempt);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification and attempt IDs")
    void shouldGetIdentityVerificationAttemptAsync() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = blocking(() ->
                checkoutApi.identityVerificationClient().createIdentityVerificationAsync(request));
        final IdentityVerificationAttemptRequest attemptRequest = createIdentityVerificationAttemptRequest();
        final IdentityVerificationAttemptResponse createdAttempt = blocking(() ->
                checkoutApi.identityVerificationClient().createIdentityVerificationAttemptAsync(created.getId(), attemptRequest));

        // Act
        final IdentityVerificationAttemptResponse retrievedAttempt = blocking(() ->
                checkoutApi.identityVerificationClient().getIdentityVerificationAttemptAsync(created.getId(), createdAttempt.getId()));

        // Assert
        validateRetrievedIdentityVerificationAttempt(retrievedAttempt, createdAttempt);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldGenerateIdentityVerificationReportAsync() {
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

    // Synchronous methods

    @Test
    @Disabled("Integration test - requires valid applicant and user journey")
    void shouldCreateAndOpenIdentityVerification() {
        // Arrange
        final CreateAndOpenIdentityVerificationRequest request = createCreateAndOpenIdentityVerificationRequest();

        // Act
        final IdentityVerificationResponse response = checkoutApi.identityVerificationClient().createAndOpenIdentityVerification(request);

        // Assert
        validateCreatedAndOpenedIdentityVerification(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid applicant and user journey")
    void shouldCreateIdentityVerification() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();

        // Act
        final IdentityVerificationResponse response = checkoutApi.identityVerificationClient().createIdentityVerification(request);

        // Assert
        validateCreatedIdentityVerification(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldGetIdentityVerification() {
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
    void shouldAnonymizeIdentityVerification() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = checkoutApi.identityVerificationClient().createIdentityVerification(request);

        // Act
        final IdentityVerificationResponse anonymized = checkoutApi.identityVerificationClient().anonymizeIdentityVerification(created.getId());

        // Assert
        validateAnonymizedIdentityVerification(anonymized);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldGetIdentityVerificationAttempts() {
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
    void shouldCreateIdentityVerificationAttempt() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = checkoutApi.identityVerificationClient().createIdentityVerification(request);
        final IdentityVerificationAttemptRequest attemptRequest = createIdentityVerificationAttemptRequest();

        // Act
        final IdentityVerificationAttemptResponse attempt = checkoutApi.identityVerificationClient().createIdentityVerificationAttempt(created.getId(), attemptRequest);

        // Assert
        validateCreatedIdentityVerificationAttempt(attempt);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification and attempt IDs")
    void shouldGetIdentityVerificationAttempt() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = checkoutApi.identityVerificationClient().createIdentityVerification(request);
        final IdentityVerificationAttemptRequest attemptRequest = createIdentityVerificationAttemptRequest();
        final IdentityVerificationAttemptResponse createdAttempt = checkoutApi.identityVerificationClient().createIdentityVerificationAttempt(created.getId(), attemptRequest);

        // Act
        final IdentityVerificationAttemptResponse retrievedAttempt = checkoutApi.identityVerificationClient().getIdentityVerificationAttempt(created.getId(), createdAttempt.getId());

        // Assert
        validateRetrievedIdentityVerificationAttempt(retrievedAttempt, createdAttempt);
    }

    @Test
    @Disabled("Integration test - requires valid identity verification ID")
    void shouldGenerateIdentityVerificationReport() {
        // Arrange
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse created = checkoutApi.identityVerificationClient().createIdentityVerification(request);

        // Act
        final IdentityVerificationReportResponse report = checkoutApi.identityVerificationClient().generateIdentityVerificationReport(created.getId());

        // Assert
        validateGeneratedIdentityVerificationReport(report);
    }

    // Common methods

    private CreateAndOpenIdentityVerificationRequest createCreateAndOpenIdentityVerificationRequest() {
        return CreateAndOpenIdentityVerificationRequest.builder()
                .applicantId("app_test_" + UUID.randomUUID().toString().substring(0, 8))
                .userJourneyId("uj_test_" + UUID.randomUUID().toString().substring(0, 8))
                .declaredData(createDeclaredData())
                .build();
    }

    private IdentityVerificationRequest createIdentityVerificationRequest() {
        return IdentityVerificationRequest.builder()
                .applicantId("app_test_" + UUID.randomUUID().toString().substring(0, 8))
                .userJourneyId("uj_test_" + UUID.randomUUID().toString().substring(0, 8))
                .declaredData(createDeclaredData())
                .build();
    }

    private IdentityVerificationAttemptRequest createIdentityVerificationAttemptRequest() {
        return IdentityVerificationAttemptRequest.builder()
                .clientInformation(createClientInformation())
                .build();
    }

    private DeclaredData createDeclaredData() {
        return DeclaredData.builder()
                .name("John Doe")
                .build();
    }

    private ClientInformation createClientInformation() {
        return ClientInformation.builder()
                .preSelectedResidenceCountry("GB")
                .preSelectedLanguage("en")
                .build();
    }

    private void validateCreatedAndOpenedIdentityVerification(final IdentityVerificationResponse response, 
                                                             final CreateAndOpenIdentityVerificationRequest request) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getRedirectUrl());
        assertEquals(request.getApplicantId(), response.getApplicantId());
        assertEquals(request.getUserJourneyId(), response.getUserJourneyId());
    }

    private void validateCreatedIdentityVerification(final IdentityVerificationResponse response, 
                                                    final IdentityVerificationRequest request) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(request.getApplicantId(), response.getApplicantId());
        assertEquals(request.getUserJourneyId(), response.getUserJourneyId());
    }

    private void validateRetrievedIdentityVerification(final IdentityVerificationResponse retrieved, 
                                                      final IdentityVerificationResponse created) {
        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals(created.getApplicantId(), retrieved.getApplicantId());
        assertEquals(created.getUserJourneyId(), retrieved.getUserJourneyId());
    }

    private void validateAnonymizedIdentityVerification(final IdentityVerificationResponse anonymized) {
        assertNotNull(anonymized);
        assertNotNull(anonymized.getId());
        // Add specific validation for anonymized data
    }

    private void validateCreatedIdentityVerificationAttempt(final IdentityVerificationAttemptResponse attempt) {
        assertNotNull(attempt);
        assertNotNull(attempt.getId());
        // Add specific validation for attempt data
    }

    private void validateRetrievedIdentityVerificationAttempts(final IdentityVerificationAttemptsResponse attempts, 
                                                              final IdentityVerificationAttemptResponse createdAttempt) {
        assertNotNull(attempts);
        assertNotNull(attempts.getData());
        assertTrue(attempts.getData().size() > 0);
        assertTrue(attempts.getData().stream().anyMatch(a -> a.getId().equals(createdAttempt.getId())));
    }

    private void validateRetrievedIdentityVerificationAttempt(final IdentityVerificationAttemptResponse retrieved, 
                                                             final IdentityVerificationAttemptResponse created) {
        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        // Add specific validation for attempt data
    }

    private void validateGeneratedIdentityVerificationReport(final IdentityVerificationReportResponse report) {
        assertNotNull(report);
        assertNotNull(report.getSignedUrl());
        // Add specific validation for report data
    }
}