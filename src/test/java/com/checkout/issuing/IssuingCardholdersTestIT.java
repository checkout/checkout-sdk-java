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

        assertNotNull(cardholderResponse);
        assertEquals(CardholderType.INDIVIDUAL, cardholderResponse.getType());
        assertEquals(CardholderStatus.ACTIVE, cardholderResponse.getStatus());
        assertEquals("X-123456-N11", cardholderResponse.getReference());
    }

    @Test
    void shouldGetCardholderDetails() {
        final CardholderDetailsResponse cardholderDetails = blocking(() ->
                issuingApi.issuingClient().getCardholder(cardholder.getId()));

        assertNotNull(cardholderDetails);
        assertEquals(CardholderType.INDIVIDUAL, cardholderDetails.getType());
        assertEquals("X-123456-N11", cardholderDetails.getReference());
    }

    @Test
    void shouldGetCardholderCards() {
        final CardholderCardsResponse cardholderCards = blocking(() ->
                issuingApi.issuingClient().getCardholderCards(cardholder.getId()));

        assertNotNull(cardholderCards);
        for (final CardDetailsResponse card : cardholderCards.getCards()) {
            assertEquals(cardholder.getId(), card.getId());
            assertEquals("cli_p6jeowdtuxku3azxgt2qa7kq7a", card.getClientId());
        }
    }
}
