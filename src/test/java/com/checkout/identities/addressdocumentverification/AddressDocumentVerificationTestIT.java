package com.checkout.identities.addressdocumentverification;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.identities.addressdocumentverification.requests.AddressDocumentVerificationAttemptRequest;
import com.checkout.identities.addressdocumentverification.requests.AddressDocumentVerificationRequest;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptAssetsResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationAttemptsResponse;
import com.checkout.identities.addressdocumentverification.responses.AddressDocumentVerificationResponse;
import com.checkout.identities.entities.AttemptAssetsQueryFilter;
import com.checkout.identities.entities.AttemptsQueryFilter;
import com.checkout.identities.entities.DeclaredData;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for the address document verification attempt-assets endpoint and the
 * paginated attempts endpoint added by swagger 2026-09-02.
 *
 * Scoped to the capabilities this row introduced. The rest of the address document verification
 * surface has no integration coverage in this SDK, which predates this row.
 */
class AddressDocumentVerificationTestIT extends SandboxTestFixture {

    AddressDocumentVerificationTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    @Disabled("Integration test - requires valid address document verification and attempt IDs")
    void shouldGetAddressDocumentVerificationAttemptAssetsSync() {
        // Arrange
        final AddressDocumentVerificationResponse created = checkoutApi.addressDocumentVerificationClient()
                .createAddressDocumentVerificationSync(createAddressDocumentVerificationRequest());
        final AddressDocumentVerificationAttemptResponse createdAttempt = checkoutApi.addressDocumentVerificationClient()
                .createAddressDocumentVerificationAttemptSync(created.getId(), createAttemptRequest());
        final AttemptAssetsQueryFilter queryFilter = AttemptAssetsQueryFilter.builder().skip(0).limit(10).build();

        // Act
        final AddressDocumentVerificationAttemptAssetsResponse assets = checkoutApi.addressDocumentVerificationClient()
                .getAddressDocumentVerificationAttemptAssetsSync(created.getId(), createdAttempt.getId(), queryFilter);

        // Assert
        assertNotNull(assets);
        assertNotNull(assets.getData());
        assertEquals(10, assets.getLimit());
    }

    @Test
    @Disabled("Integration test - requires valid address document verification and attempt IDs")
    void shouldGetAddressDocumentVerificationAttemptAssetsAsync() {
        // Arrange
        final AddressDocumentVerificationResponse created = blocking(() ->
                checkoutApi.addressDocumentVerificationClient()
                        .createAddressDocumentVerification(createAddressDocumentVerificationRequest()));
        final AddressDocumentVerificationAttemptResponse createdAttempt = blocking(() ->
                checkoutApi.addressDocumentVerificationClient()
                        .createAddressDocumentVerificationAttempt(created.getId(), createAttemptRequest()));
        final AttemptAssetsQueryFilter queryFilter = AttemptAssetsQueryFilter.builder().skip(0).limit(10).build();

        // Act
        final AddressDocumentVerificationAttemptAssetsResponse assets = blocking(() ->
                checkoutApi.addressDocumentVerificationClient()
                        .getAddressDocumentVerificationAttemptAssets(created.getId(), createdAttempt.getId(), queryFilter));

        // Assert
        assertNotNull(assets);
        assertNotNull(assets.getData());
    }

    @Test
    @Disabled("Integration test - requires valid address document verification ID")
    void shouldGetAddressDocumentVerificationAttemptsPaginatedSync() {
        // Arrange
        final AddressDocumentVerificationResponse created = checkoutApi.addressDocumentVerificationClient()
                .createAddressDocumentVerificationSync(createAddressDocumentVerificationRequest());
        checkoutApi.addressDocumentVerificationClient()
                .createAddressDocumentVerificationAttemptSync(created.getId(), createAttemptRequest());
        final AttemptsQueryFilter queryFilter = AttemptsQueryFilter.builder().skip(0).limit(5).build();

        // Act
        final AddressDocumentVerificationAttemptsResponse attempts = checkoutApi.addressDocumentVerificationClient()
                .getAddressDocumentVerificationAttemptsSync(created.getId(), queryFilter);

        // Assert
        assertNotNull(attempts);
        assertNotNull(attempts.getData());
        assertEquals(5, attempts.getLimit());
    }

    // Common methods
    private static AddressDocumentVerificationRequest createAddressDocumentVerificationRequest() {
        return AddressDocumentVerificationRequest.builder()
                .applicantId("aplt_" + UUID.randomUUID().toString().replace("-", "").substring(0, 26))
                .userJourneyId("usj_" + UUID.randomUUID().toString().replace("-", "").substring(0, 26))
                .declaredData(DeclaredData.builder()
                        .name("Hannah Bret")
                        .birthDate("1994-10-15")
                        .build())
                .build();
    }

    private static AddressDocumentVerificationAttemptRequest createAttemptRequest() {
        return AddressDocumentVerificationAttemptRequest.builder()
                .document("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAA...")
                .build();
    }
}
