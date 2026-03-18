package com.checkout.standaloneaccountupdater;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.standaloneaccountupdater.entities.AccountUpdateStatus;
import com.checkout.standaloneaccountupdater.entities.CardDetails;
import com.checkout.standaloneaccountupdater.entities.InstrumentReference;
import com.checkout.standaloneaccountupdater.entities.SourceOptions;
import com.checkout.standaloneaccountupdater.requests.GetUpdatedCardCredentialsRequest;
import com.checkout.standaloneaccountupdater.responses.GetUpdatedCardCredentialsResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StandaloneAccountUpdaterTestIT extends SandboxTestFixture {

    public StandaloneAccountUpdaterTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Disabled("This test requires valid card data and OAuth scope vault:real-time-account-updater")
    @Test
    void shouldGetUpdatedCardCredentialsWithCard() {

        final GetUpdatedCardCredentialsRequest request = createGetUpdatedCardCredentialsRequestWithCard();

        final GetUpdatedCardCredentialsResponse response = blocking(() -> checkoutApi.standaloneAccountUpdaterClient().getUpdatedCardCredentials(request));

        validateGetUpdatedCardCredentialsResponse(response);

    }

    @Disabled("This test requires valid instrument ID and OAuth scope vault:real-time-account-updater")
    @Test
    void shouldGetUpdatedCardCredentialsWithInstrument() {

        final GetUpdatedCardCredentialsRequest request = createGetUpdatedCardCredentialsRequestWithInstrument();

        final GetUpdatedCardCredentialsResponse response = blocking(() -> checkoutApi.standaloneAccountUpdaterClient().getUpdatedCardCredentials(request));

        validateGetUpdatedCardCredentialsResponse(response);

    }

    // Sync methods
    @Disabled("This test requires valid card data and OAuth scope vault:real-time-account-updater")
    @Test
    void shouldGetUpdatedCardCredentialsWithCardSync() {

        final GetUpdatedCardCredentialsRequest request = createGetUpdatedCardCredentialsRequestWithCard();

        final GetUpdatedCardCredentialsResponse response = checkoutApi.standaloneAccountUpdaterClient().getUpdatedCardCredentialsSync(request);

        validateGetUpdatedCardCredentialsResponse(response);

    }

    @Disabled("This test requires valid instrument ID and OAuth scope vault:real-time-account-updater")
    @Test
    void shouldGetUpdatedCardCredentialsWithInstrumentSync() {

        final GetUpdatedCardCredentialsRequest request = createGetUpdatedCardCredentialsRequestWithInstrument();

        final GetUpdatedCardCredentialsResponse response = checkoutApi.standaloneAccountUpdaterClient().getUpdatedCardCredentialsSync(request);

        validateGetUpdatedCardCredentialsResponse(response);

    }

    // Common methods
    private GetUpdatedCardCredentialsRequest createGetUpdatedCardCredentialsRequestWithCard() {
        final CardDetails cardDetails = CardDetails.builder()
                .number("5436424242424242")
                .expiryMonth(5)
                .expiryYear(2025)
                .build();

        final SourceOptions sourceOptions = SourceOptions.builder()
                .card(cardDetails)
                .build();

        return GetUpdatedCardCredentialsRequest.builder()
                .sourceOptions(sourceOptions)
                .build();
    }

    private GetUpdatedCardCredentialsRequest createGetUpdatedCardCredentialsRequestWithInstrument() {
        final InstrumentReference instrumentReference = InstrumentReference.builder()
                .id("src_nmukohhu7vbe5f55ndwqzwv2c4")
                .build();

        final SourceOptions sourceOptions = SourceOptions.builder()
                .instrument(instrumentReference)
                .build();

        return GetUpdatedCardCredentialsRequest.builder()
                .sourceOptions(sourceOptions)
                .build();
    }

    private void validateGetUpdatedCardCredentialsResponse(final GetUpdatedCardCredentialsResponse response) {
        assertNotNull(response);
        assertNotNull(response.getAccountUpdateStatus());
        assertTrue(response.getAccountUpdateStatus() == AccountUpdateStatus.CARD_UPDATED
                || response.getAccountUpdateStatus() == AccountUpdateStatus.CARD_EXPIRY_UPDATED
                || response.getAccountUpdateStatus() == AccountUpdateStatus.CARD_CLOSED
                || response.getAccountUpdateStatus() == AccountUpdateStatus.UPDATE_FAILED);
        
        if (response.getAccountUpdateStatus() == AccountUpdateStatus.CARD_UPDATED 
            || response.getAccountUpdateStatus() == AccountUpdateStatus.CARD_EXPIRY_UPDATED) {
            assertNotNull(response.getCard());
            assertNotNull(response.getCard().getExpiryMonth());
            assertNotNull(response.getCard().getExpiryYear());
            // Card details like bin, last4, fingerprint may or may not be present depending on PCI compliance level
        }
        
        if (response.getAccountUpdateStatus() == AccountUpdateStatus.UPDATE_FAILED) {
            // accountUpdateFailureCode may be present when update fails
        }
    }

}