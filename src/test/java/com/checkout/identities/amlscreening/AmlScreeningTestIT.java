package com.checkout.identities.amlscreening;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.identities.amlscreening.requests.AmlScreeningRequest;
import com.checkout.identities.amlscreening.responses.AmlScreeningResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AmlScreeningTestIT extends SandboxTestFixture {

    AmlScreeningTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    // Async methods

    @Test
    @Disabled("Integration test - requires valid test data")
    void shouldCreateAmlScreening() {
        // Arrange
        final AmlScreeningRequest request = createAmlScreeningRequest();

        // Act
        final AmlScreeningResponse response = blocking(() -> 
                checkoutApi.amlScreeningClient().createAmlScreeningAsync(request));

        // Assert
        validateCreatedAmlScreening(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid AML screening ID")
    void shouldGetAmlScreening() {
        // Arrange
        final AmlScreeningRequest request = createAmlScreeningRequest();
        final AmlScreeningResponse created = blocking(() ->
                checkoutApi.amlScreeningClient().createAmlScreeningAsync(request));

        // Act
        final AmlScreeningResponse retrieved = blocking(() ->
                checkoutApi.amlScreeningClient().getAmlScreeningAsync(created.getId()));

        // Assert
        validateRetrievedAmlScreening(retrieved, created);
    }

    // Sync methods
    @Test
    @Disabled("Integration test - requires valid test data")
    void shouldCreateAmlScreeningSync() {
        // Arrange
        final AmlScreeningRequest request = createAmlScreeningRequest();

        // Act
        final AmlScreeningResponse response = checkoutApi.amlScreeningClient().createAmlScreening(request);

        // Assert
        validateCreatedAmlScreening(response, request);
    }

    @Test
    @Disabled("Integration test - requires valid AML screening ID")
    void shouldGetAmlScreeningSync() {
        // Arrange
        final AmlScreeningRequest request = createAmlScreeningRequest();
        final AmlScreeningResponse created = checkoutApi.amlScreeningClient().createAmlScreening(request);

        // Act
        final AmlScreeningResponse retrieved = checkoutApi.amlScreeningClient().getAmlScreening(created.getId());

        // Assert
        validateRetrievedAmlScreening(retrieved, created);
    }

    // Common methods
    private static AmlScreeningRequest createAmlScreeningRequest() {
        return AmlScreeningRequest.builder()
                .applicantId("aplt_" + generateRandomString(8))
                .searchParameters(AmlScreeningRequest.SearchParameters.builder()
                        .configurationIdentifier(UUID.randomUUID().toString())
                        .build())
                .monitored(false)
                .build();
    }

    private static void validateCreatedAmlScreening(final AmlScreeningResponse response, final AmlScreeningRequest request) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertTrue(response.getId().startsWith("amlv_"));
        assertNotNull(response.getCreatedOn());
        assertEquals(request.getApplicantId(), response.getApplicantId());
        assertEquals(request.getMonitored(), response.getMonitored());
        validateCommonAmlScreeningFields(response);
    }

    private static void validateRetrievedAmlScreening(final AmlScreeningResponse retrieved, final AmlScreeningResponse created) {
        assertNotNull(retrieved);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals(created.getCreatedOn(), retrieved.getCreatedOn());
        assertEquals(created.getApplicantId(), retrieved.getApplicantId());
        assertEquals(created.getMonitored(), retrieved.getMonitored());
        validateCommonAmlScreeningFields(retrieved);
    }

    private static void validateCommonAmlScreeningFields(final AmlScreeningResponse response) {
        assertNotNull(response.getStatus());
        assertNotNull(response.getSearchParameters());
        assertNotNull(response.getSearchParameters().getConfigurationIdentifier());
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