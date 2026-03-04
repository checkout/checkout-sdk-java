package com.checkout.identities.applicants;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.identities.applicants.requests.ApplicantRequest;
import com.checkout.identities.applicants.requests.ApplicantUpdateRequest;
import com.checkout.identities.applicants.responses.ApplicantResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicantTestIT extends SandboxTestFixture {

    ApplicantTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    @Disabled("Integration test - requires valid test data")
    void shouldCreateApplicant() {
        // Arrange
        final ApplicantRequest request = createApplicantRequest();

        // Act
        final ApplicantResponse response = blocking(() -> 
                checkoutApi.applicantClient().createApplicant(request));

        // Assert
        validateCreatedApplicant(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid applicant ID")
    void shouldGetApplicant() {
        // Arrange
        final ApplicantRequest request = createApplicantRequest();
        final ApplicantResponse created = blocking(() ->
                checkoutApi.applicantClient().createApplicant(request));

        // Act
        final ApplicantResponse retrieved = blocking(() ->
                checkoutApi.applicantClient().getApplicant(created.getId()));

        // Assert
        validateRetrievedApplicant(retrieved, created);
    }

    @Test
    @Disabled("Integration test - requires valid applicant ID")
    void shouldUpdateApplicant() {
        // Arrange
        final ApplicantRequest createRequest = createApplicantRequest();
        final ApplicantResponse created = blocking(() ->
                checkoutApi.applicantClient().createApplicant(createRequest));
        final ApplicantUpdateRequest updateRequest = createApplicantUpdateRequest();

        // Act
        final ApplicantResponse updated = blocking(() ->
                checkoutApi.applicantClient().updateApplicant(created.getId(), updateRequest));

        // Assert
        validateUpdatedApplicant(updated, updateRequest);
    }

    @Test
    @Disabled("Integration test - requires valid applicant ID")
    void shouldAnonymizeApplicant() {
        // Arrange
        final ApplicantRequest request = createApplicantRequest();
        final ApplicantResponse created = blocking(() ->
                checkoutApi.applicantClient().createApplicant(request));

        // Act
        final ApplicantResponse anonymized = blocking(() ->
                checkoutApi.applicantClient().anonymizeApplicant(created.getId()));

        // Assert
        validateAnonymizedApplicant(anonymized);
    }

    // Synchronous methods tests
    @Test
    @Disabled("Integration test - requires valid test data")
    void shouldCreateApplicantSync() {
        // Arrange
        final ApplicantRequest request = createApplicantRequest();

        // Act
        final ApplicantResponse response = checkoutApi.applicantClient().createApplicantSync(request);

        // Assert
        validateCreatedApplicant(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid applicant ID")
    void shouldGetApplicantSync() {
        // Arrange
        final ApplicantRequest request = createApplicantRequest();
        final ApplicantResponse created = checkoutApi.applicantClient().createApplicantSync(request);

        // Act
        final ApplicantResponse retrieved = checkoutApi.applicantClient().getApplicantSync(created.getId());

        // Assert
        validateRetrievedApplicant(retrieved, created);
    }

    @Test
    @Disabled("Integration test - requires valid applicant ID")
    void shouldUpdateApplicantSync() {
        // Arrange
        final ApplicantRequest createRequest = createApplicantRequest();
        final ApplicantResponse created = checkoutApi.applicantClient().createApplicantSync(createRequest);
        final ApplicantUpdateRequest updateRequest = createApplicantUpdateRequest();

        // Act
        final ApplicantResponse updated = checkoutApi.applicantClient().updateApplicantSync(created.getId(), updateRequest);

        // Assert
        validateUpdatedApplicant(updated, updateRequest);
    }

    @Test
    @Disabled("Integration test - requires valid applicant ID")
    void shouldAnonymizeApplicantSync() {
        // Arrange
        final ApplicantRequest request = createApplicantRequest();
        final ApplicantResponse created = checkoutApi.applicantClient().createApplicantSync(request);

        // Act
        final ApplicantResponse anonymized = checkoutApi.applicantClient().anonymizeApplicantSync(created.getId());

        // Assert
        validateAnonymizedApplicant(anonymized);
    }

    // Common methods
    private static ApplicantRequest createApplicantRequest() {
        return ApplicantRequest.builder()
                .externalApplicantId("ext_" + generateRandomString(26)) // Mock external ID - should be real in integration tests
                .email("test." + generateRandomString(8) + "@example.com")
                .externalApplicantName("Test User " + generateRandomString(6))
                .build();
    }

    private static ApplicantUpdateRequest createApplicantUpdateRequest() {
        return ApplicantUpdateRequest.builder()
                .email("updated." + generateRandomString(8) + "@example.com")
                .externalApplicantName("Updated Test User " + generateRandomString(6))
                .build();
    }

    private static void validateCreatedApplicant(final ApplicantResponse response, final ApplicantRequest request) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getId().startsWith("aplt_"));
        assertEquals(request.getExternalApplicantId(), response.getExternalApplicantId());
        assertEquals(request.getEmail(), response.getEmail());
        assertEquals(request.getExternalApplicantName(), response.getExternalApplicantName());
        assertNotNull(response.getCreatedOn());
        validateCommonApplicantFields(response);
    }

    private static void validateRetrievedApplicant(final ApplicantResponse retrieved, final ApplicantResponse created) {
        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals(created.getExternalApplicantId(), retrieved.getExternalApplicantId());
        assertEquals(created.getEmail(), retrieved.getEmail());
        assertEquals(created.getExternalApplicantName(), retrieved.getExternalApplicantName());
        validateCommonApplicantFields(retrieved);
    }

    private static void validateUpdatedApplicant(final ApplicantResponse response, final ApplicantUpdateRequest request) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getId().startsWith("aplt_"));
        assertEquals(request.getEmail(), response.getEmail());
        assertEquals(request.getExternalApplicantName(), response.getExternalApplicantName());
        assertNotNull(response.getModifiedOn());
        validateCommonApplicantFields(response);
    }

    private static void validateAnonymizedApplicant(final ApplicantResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getId().startsWith("aplt_"));
        // After anonymization, personal data may be removed or modified
        validateCommonApplicantFields(response);
    }

    private static void validateCommonApplicantFields(final ApplicantResponse response) {
        assertNotNull(response.getModifiedOn());
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