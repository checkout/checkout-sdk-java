package com.checkout.identities.faceauthentications;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.identities.faceauthentications.requests.FaceAuthenticationAttemptRequest;
import com.checkout.identities.faceauthentications.requests.FaceAuthenticationRequest;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationAttemptResponse;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationAttemptsResponse;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FaceAuthenticationTestIT extends SandboxTestFixture {

    FaceAuthenticationTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    @Disabled("Integration test - requires valid applicant and user journey")
    void shouldCreateFaceAuthentication() {
        // Arrange
        final FaceAuthenticationRequest request = createFaceAuthenticationRequest();

        // Act
        final FaceAuthenticationResponse response = blocking(() -> 
                checkoutApi.faceAuthenticationClient().createFaceAuthentication(request));

        // Assert
        validateCreatedFaceAuthentication(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid face authentication ID")
    void shouldGetFaceAuthentication() {
        // Arrange
        final FaceAuthenticationRequest request = createFaceAuthenticationRequest();
        final FaceAuthenticationResponse created = blocking(() ->
                checkoutApi.faceAuthenticationClient().createFaceAuthentication(request));

        // Act
        final FaceAuthenticationResponse retrieved = blocking(() ->
                checkoutApi.faceAuthenticationClient().getFaceAuthentication(created.getId()));

        // Assert
        validateRetrievedFaceAuthentication(retrieved, created);
    }

    @Test
    @Disabled("Integration test - requires valid face authentication ID")
    void shouldAnonymizeFaceAuthentication() {
        // Arrange
        final FaceAuthenticationRequest request = createFaceAuthenticationRequest();
        final FaceAuthenticationResponse created = blocking(() ->
                checkoutApi.faceAuthenticationClient().createFaceAuthentication(request));

        // Act
        final FaceAuthenticationResponse anonymized = blocking(() ->
                checkoutApi.faceAuthenticationClient().anonymizeFaceAuthentication(created.getId()));

        // Assert
        validateAnonymizedFaceAuthentication(anonymized);
    }

    @Test
    @Disabled("Integration test - requires valid face authentication ID")
    void shouldCreateFaceAuthenticationAttempt() {
        // Arrange
        final FaceAuthenticationRequest request = createFaceAuthenticationRequest();
        final FaceAuthenticationResponse created = blocking(() ->
                checkoutApi.faceAuthenticationClient().createFaceAuthentication(request));
        final FaceAuthenticationAttemptRequest attemptRequest = createFaceAuthenticationAttemptRequest();

        // Act
        final FaceAuthenticationAttemptResponse attempt = blocking(() ->
                checkoutApi.faceAuthenticationClient().createFaceAuthenticationAttempt(created.getId(), attemptRequest));

        // Assert
        validateCreatedFaceAuthenticationAttempt(attempt, attemptRequest);
    }

    @Test
    @Disabled("Integration test - requires valid face authentication ID")
    void shouldGetFaceAuthenticationAttempts() {
        // Arrange
        final FaceAuthenticationRequest request = createFaceAuthenticationRequest();
        final FaceAuthenticationResponse created = blocking(() ->
                checkoutApi.faceAuthenticationClient().createFaceAuthentication(request));
        final FaceAuthenticationAttemptRequest attemptRequest = createFaceAuthenticationAttemptRequest();
        final FaceAuthenticationAttemptResponse createdAttempt = blocking(() ->
                checkoutApi.faceAuthenticationClient().createFaceAuthenticationAttempt(created.getId(), attemptRequest));

        // Act
        final FaceAuthenticationAttemptsResponse attempts = blocking(() ->
                checkoutApi.faceAuthenticationClient().getFaceAuthenticationAttempts(created.getId()));

        // Assert
        validateRetrievedFaceAuthenticationAttempts(attempts, createdAttempt);
    }

    @Test
    @Disabled("Integration test - requires valid face authentication and attempt IDs")
    void shouldGetFaceAuthenticationAttempt() {
        // Arrange
        final FaceAuthenticationRequest request = createFaceAuthenticationRequest();
        final FaceAuthenticationResponse created = blocking(() ->
                checkoutApi.faceAuthenticationClient().createFaceAuthentication(request));
        final FaceAuthenticationAttemptRequest attemptRequest = createFaceAuthenticationAttemptRequest();
        final FaceAuthenticationAttemptResponse createdAttempt = blocking(() ->
                checkoutApi.faceAuthenticationClient().createFaceAuthenticationAttempt(created.getId(), attemptRequest));

        // Act
        final FaceAuthenticationAttemptResponse retrievedAttempt = blocking(() ->
                checkoutApi.faceAuthenticationClient().getFaceAuthenticationAttempt(created.getId(), createdAttempt.getId()));

        // Assert
        validateRetrievedFaceAuthenticationAttempt(retrievedAttempt, createdAttempt);
    }

    @Test
    @Disabled("Integration test - comprehensive workflow test")
    void shouldPerformFaceAuthenticationWorkflow() {
        // Arrange
        final FaceAuthenticationRequest request = createFaceAuthenticationRequest();

        // Act & Assert - Create Face Authentication
        final FaceAuthenticationResponse created = blocking(() ->
                checkoutApi.faceAuthenticationClient().createFaceAuthentication(request));
        validateCreatedFaceAuthentication(created, request);

        // Act & Assert - Get Face Authentication
        final FaceAuthenticationResponse retrieved = blocking(() ->
                checkoutApi.faceAuthenticationClient().getFaceAuthentication(created.getId()));
        validateRetrievedFaceAuthentication(retrieved, created);

        // Act & Assert - Create Attempt
        final FaceAuthenticationAttemptRequest attemptRequest = createFaceAuthenticationAttemptRequest();
        final FaceAuthenticationAttemptResponse createdAttempt = blocking(() ->
                checkoutApi.faceAuthenticationClient().createFaceAuthenticationAttempt(created.getId(), attemptRequest));
        validateCreatedFaceAuthenticationAttempt(createdAttempt, attemptRequest);

        // Act & Assert - Get Attempts
        final FaceAuthenticationAttemptsResponse attempts = blocking(() ->
                checkoutApi.faceAuthenticationClient().getFaceAuthenticationAttempts(created.getId()));
        validateRetrievedFaceAuthenticationAttempts(attempts, createdAttempt);

        // Act & Assert - Get Single Attempt
        final FaceAuthenticationAttemptResponse retrievedAttempt = blocking(() ->
                checkoutApi.faceAuthenticationClient().getFaceAuthenticationAttempt(created.getId(), createdAttempt.getId()));
        validateRetrievedFaceAuthenticationAttempt(retrievedAttempt, createdAttempt);

        // Act & Assert - Anonymize
        final FaceAuthenticationResponse anonymized = blocking(() ->
                checkoutApi.faceAuthenticationClient().anonymizeFaceAuthentication(created.getId()));
        validateAnonymizedFaceAuthentication(anonymized);
    }

    // Synchronous methods
    @Test
    @Disabled("Integration test - requires valid applicant and user journey")
    void shouldCreateFaceAuthenticationSync() {
        // Arrange
        final FaceAuthenticationRequest request = createFaceAuthenticationRequest();

        // Act
        final FaceAuthenticationResponse response = checkoutApi.faceAuthenticationClient().createFaceAuthenticationSync(request);

        // Assert
        validateCreatedFaceAuthentication(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid face authentication ID")
    void shouldGetFaceAuthenticationSync() {
        // Arrange
        final FaceAuthenticationRequest request = createFaceAuthenticationRequest();
        final FaceAuthenticationResponse created = checkoutApi.faceAuthenticationClient().createFaceAuthenticationSync(request);

        // Act
        final FaceAuthenticationResponse retrieved = checkoutApi.faceAuthenticationClient().getFaceAuthenticationSync(created.getId());

        // Assert
        validateRetrievedFaceAuthentication(retrieved, created);
    }

    @Test
    @Disabled("Integration test - requires valid face authentication ID")
    void shouldAnonymizeFaceAuthenticationSync() {
        // Arrange
        final FaceAuthenticationRequest request = createFaceAuthenticationRequest();
        final FaceAuthenticationResponse created = checkoutApi.faceAuthenticationClient().createFaceAuthenticationSync(request);

        // Act
        final FaceAuthenticationResponse anonymized = checkoutApi.faceAuthenticationClient().anonymizeFaceAuthenticationSync(created.getId());

        // Assert
        validateAnonymizedFaceAuthentication(anonymized);
    }

    @Test
    @Disabled("Integration test - requires valid face authentication ID")
    void shouldCreateFaceAuthenticationAttemptSync() {
        // Arrange
        final FaceAuthenticationRequest request = createFaceAuthenticationRequest();
        final FaceAuthenticationResponse created = checkoutApi.faceAuthenticationClient().createFaceAuthenticationSync(request);
        final FaceAuthenticationAttemptRequest attemptRequest = createFaceAuthenticationAttemptRequest();

        // Act
        final FaceAuthenticationAttemptResponse attempt = checkoutApi.faceAuthenticationClient().createFaceAuthenticationAttemptSync(created.getId(), attemptRequest);

        // Assert
        validateCreatedFaceAuthenticationAttempt(attempt, attemptRequest);
    }

    @Test
    @Disabled("Integration test - requires valid face authentication ID")
    void shouldGetFaceAuthenticationAttemptsSync() {
        // Arrange
        final FaceAuthenticationRequest request = createFaceAuthenticationRequest();
        final FaceAuthenticationResponse created = checkoutApi.faceAuthenticationClient().createFaceAuthenticationSync(request);
        final FaceAuthenticationAttemptRequest attemptRequest = createFaceAuthenticationAttemptRequest();
        final FaceAuthenticationAttemptResponse createdAttempt = checkoutApi.faceAuthenticationClient().createFaceAuthenticationAttemptSync(created.getId(), attemptRequest);

        // Act
        final FaceAuthenticationAttemptsResponse attempts = checkoutApi.faceAuthenticationClient().getFaceAuthenticationAttemptsSync(created.getId());

        // Assert
        validateRetrievedFaceAuthenticationAttempts(attempts, createdAttempt);
    }

    @Test
    @Disabled("Integration test - requires valid face authentication and attempt IDs")
    void shouldGetFaceAuthenticationAttemptSync() {
        // Arrange
        final FaceAuthenticationRequest request = createFaceAuthenticationRequest();
        final FaceAuthenticationResponse created = checkoutApi.faceAuthenticationClient().createFaceAuthenticationSync(request);
        final FaceAuthenticationAttemptRequest attemptRequest = createFaceAuthenticationAttemptRequest();
        final FaceAuthenticationAttemptResponse createdAttempt = checkoutApi.faceAuthenticationClient().createFaceAuthenticationAttemptSync(created.getId(), attemptRequest);

        // Act
        final FaceAuthenticationAttemptResponse retrievedAttempt = checkoutApi.faceAuthenticationClient().getFaceAuthenticationAttemptSync(created.getId(), createdAttempt.getId());

        // Assert
        validateRetrievedFaceAuthenticationAttempt(retrievedAttempt, createdAttempt);
    }

    // Common methods
    private static FaceAuthenticationRequest createFaceAuthenticationRequest() {
        return FaceAuthenticationRequest.builder()
                .applicantId("aplt_" + generateRandomString(26)) // Mock applicant ID - should be real in integration tests
                .userJourneyId("usj_" + generateRandomString(26)) // Mock user journey ID - should be real in integration tests
                .build();
    }

    private static FaceAuthenticationAttemptRequest createFaceAuthenticationAttemptRequest() {
        return FaceAuthenticationAttemptRequest.builder()
                .redirectUrl("https://example.com/redirect?session=" + generateRandomString(10))
                .clientInformation(FaceAuthenticationAttemptRequest.ClientInformation.builder()
                        .preSelectedResidenceCountry("GB")
                        .preSelectedLanguage("en-US")
                        .build())
                .build();
    }

    private static void validateCreatedFaceAuthentication(final FaceAuthenticationResponse response, final FaceAuthenticationRequest request) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getId().startsWith("fav_"));
        assertEquals(request.getUserJourneyId(), response.getUserJourneyId());
        assertEquals(request.getApplicantId(), response.getApplicantId());
        assertNotNull(response.getStatus());
        assertNotNull(response.getCreatedOn());
        validateCommonFaceAuthenticationFields(response);
    }

    private static void validateRetrievedFaceAuthentication(final FaceAuthenticationResponse retrieved, final FaceAuthenticationResponse created) {
        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals(created.getUserJourneyId(), retrieved.getUserJourneyId());
        assertEquals(created.getApplicantId(), retrieved.getApplicantId());
        assertNotNull(retrieved.getStatus());
        validateCommonFaceAuthenticationFields(retrieved);
    }

    private static void validateAnonymizedFaceAuthentication(final FaceAuthenticationResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getId().startsWith("fav_"));
        // After anonymization, personal data may be removed
        validateCommonFaceAuthenticationFields(response);
    }

    private static void validateCreatedFaceAuthenticationAttempt(final FaceAuthenticationAttemptResponse response, final FaceAuthenticationAttemptRequest request) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getId().startsWith("fatp_"));
        assertNotNull(response.getStatus());
        assertEquals(request.getRedirectUrl(), response.getRedirectUrl());
        
        if (request.getClientInformation() != null) {
            assertNotNull(response.getClientInformation());
            assertEquals(request.getClientInformation().getPreSelectedResidenceCountry(), 
                    response.getClientInformation().getPreSelectedResidenceCountry());
            assertEquals(request.getClientInformation().getPreSelectedLanguage(), 
                    response.getClientInformation().getPreSelectedLanguage());
        }
        
        validateCommonAttemptFields(response);
    }

    private static void validateRetrievedFaceAuthenticationAttempts(final FaceAuthenticationAttemptsResponse response, final FaceAuthenticationAttemptResponse createdAttempt) {
        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.getTotalCount() > 0);
        assertFalse(response.getData().isEmpty());
        
        // Verify our created attempt is in the list
        final boolean foundAttempt = response.getData().stream()
                .anyMatch(attempt -> attempt.getId().equals(createdAttempt.getId()));
        assertTrue(foundAttempt, "Created attempt should be found in the list");
        
        // Validate each attempt in the list
        response.getData().forEach(FaceAuthenticationTestIT::validateCommonAttemptFields);
    }

    private static void validateRetrievedFaceAuthenticationAttempt(final FaceAuthenticationAttemptResponse retrieved, final FaceAuthenticationAttemptResponse created) {
        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        assertNotNull(retrieved.getStatus());
        validateCommonAttemptFields(retrieved);
    }

    private static void validateCommonFaceAuthenticationFields(final FaceAuthenticationResponse response) {
        assertNotNull(response.getModifiedOn());
        assertNotNull(response.getResponseCodes());
        assertNotNull(response.getRiskLabels());
        assertNotNull(response.getLinks());
        assertNotNull(response.getSelfLink());
        assertNotNull(response.getSelfLink().getHref());
        assertNotNull(response.getLink("applicant"));
        assertNotNull(response.getLink("applicant").getHref());
    }

    private static void validateCommonAttemptFields(final FaceAuthenticationAttemptResponse response) {
        assertNotNull(response.getCreatedOn());
        assertNotNull(response.getModifiedOn());
        assertNotNull(response.getRedirectUrl());
        assertNotNull(response.getResponseCodes());
        assertNotNull(response.getLinks());
        assertNotNull(response.getSelfLink());
        assertNotNull(response.getSelfLink().getHref());
        assertNotNull(response.getLink("verification_url"));
        assertNotNull(response.getLink("verification_url").getHref());
    }

    /**
     * Generates a random string for test data.
     */
    private static String generateRandomString(final int length) {
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }
}