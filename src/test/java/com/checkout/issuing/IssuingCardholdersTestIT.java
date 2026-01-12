package com.checkout.issuing;

import com.checkout.issuing.cardholders.CardholderCardsResponse;
import com.checkout.issuing.cardholders.CardholderDetailsResponse;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cardholders.CardholderStatus;
import com.checkout.issuing.cardholders.CardholderType;
import com.checkout.issuing.cards.responses.CardDetailsResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IssuingCardholdersTestIT extends BaseIssuingTestIT {

    private CardholderResponse cardholder;

    @BeforeAll
    void setUp() {
        cardholder = createCardholder();
    }

    @Test
    void shouldCreateCardholder() {
        final CardholderResponse cardholderResponse = cardholder;

        validateCardholderCreation(cardholderResponse);
    }

    @Test
    void shouldGetCardholderDetails() {
        final CardholderDetailsResponse cardholderDetails = blocking(() ->
                issuingApi.issuingClient().getCardholder(cardholder.getId()));

        validateCardholderDetails(cardholderDetails);
    }

    @Test
    void shouldGetCardholderCards() {
        final CardholderCardsResponse cardholderCards = blocking(() ->
                issuingApi.issuingClient().getCardholderCards(cardholder.getId()));

        validateCardholderCards(cardholderCards);
    }

    // Synchronous methods
    @Test
    void shouldCreateCardholderSync() {
        final CardholderResponse cardholderResponse = createCardholder();

        validateCardholderCreation(cardholderResponse);
    }

    @Test
    void shouldGetCardholderDetailsSync() {
        final CardholderDetailsResponse cardholderDetails = 
                issuingApi.issuingClient().getCardholderSync(cardholder.getId());

        validateCardholderDetails(cardholderDetails);
    }

    @Test
    void shouldGetCardholderCardsSync() {
        final CardholderCardsResponse cardholderCards = 
                issuingApi.issuingClient().getCardholderCardsSync(cardholder.getId());

        validateCardholderCards(cardholderCards);
    }

    // Common methods
    private void validateCardholderCreation(CardholderResponse cardholderResponse) {
        assertNotNull(cardholderResponse);
        assertEquals(CardholderType.INDIVIDUAL, cardholderResponse.getType());
        assertEquals(CardholderStatus.ACTIVE, cardholderResponse.getStatus());
        assertEquals("X-123456-N11", cardholderResponse.getReference());
    }

    private void validateCardholderDetails(CardholderDetailsResponse cardholderDetails) {
        assertNotNull(cardholderDetails);
        assertEquals(CardholderType.INDIVIDUAL, cardholderDetails.getType());
        assertEquals("X-123456-N11", cardholderDetails.getReference());
    }

    private void validateCardholderCards(CardholderCardsResponse cardholderCards) {
        assertNotNull(cardholderCards);
        for (final CardDetailsResponse card : cardholderCards.getCards()) {
            assertEquals(cardholder.getId(), card.getId());
            assertEquals("cli_p6jeowdtuxku3azxgt2qa7kq7a", card.getClientId());
        }
    }
}
