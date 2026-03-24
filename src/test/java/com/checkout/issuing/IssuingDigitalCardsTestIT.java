package com.checkout.issuing;

import com.checkout.EmptyResponse;
import com.checkout.issuing.cards.responses.GetDigitalCardResponse;
import com.checkout.issuing.testing.requests.OobTransactionDetails;
import com.checkout.issuing.testing.requests.SimulateOobAuthenticationRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for:
 *   GET  /issuing/digital-cards/{digitalCardId}
 *   POST /issuing/simulate/oob/authentication
 *
 * Both endpoints require a physical or virtual card that has been provisioned
 * to a digital wallet (e.g. Apple Pay, Google Pay), which cannot be created
 * automatically in sandbox. Enable tests manually with a valid digitalCardId.
 */
@Disabled("Requires a provisioned digital card — enable manually with a valid digitalCardId")
class IssuingDigitalCardsTestIT extends BaseIssuingTestIT {

    private static final String DIGITAL_CARD_ID = "dc_replace_with_real_digital_card_id";
    private static final String CARD_ID = "crd_replace_with_real_card_id";

    @Test
    void shouldGetDigitalCard() {
        validateDigitalCardResponse(blocking(() ->
                issuingApi.issuingClient().getDigitalCard(DIGITAL_CARD_ID)));
    }

    @Test
    void shouldGetDigitalCardSync() {
        validateDigitalCardResponse(issuingApi.issuingClient().getDigitalCardSync(DIGITAL_CARD_ID));
    }

    @Test
    void shouldSimulateOobAuthentication() {
        final EmptyResponse response = blocking(() ->
                issuingApi.issuingClient().simulateOobAuthentication(createOobRequest()));

        assertNotNull(response);
    }

    @Test
    void shouldSimulateOobAuthenticationSync() {
        final EmptyResponse response = issuingApi.issuingClient().simulateOobAuthenticationSync(createOobRequest());

        assertNotNull(response);
    }

    // Common methods

    private SimulateOobAuthenticationRequest createOobRequest() {
        return SimulateOobAuthenticationRequest.builder()
                .cardId(CARD_ID)
                .transactionDetails(OobTransactionDetails.builder()
                        .lastFour("4242")
                        .merchantName("Test Merchant")
                        .build())
                .build();
    }

    private void validateDigitalCardResponse(final GetDigitalCardResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getCardId());
    }
}
