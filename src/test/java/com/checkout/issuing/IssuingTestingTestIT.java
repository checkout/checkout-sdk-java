package com.checkout.issuing;

import com.checkout.common.Currency;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cards.responses.CardResponse;
import com.checkout.issuing.testing.requests.CardAuthorizationRequest;
import com.checkout.issuing.testing.requests.CardSimulation;
import com.checkout.issuing.testing.requests.TransactionSimulation;
import com.checkout.issuing.testing.requests.TransactionType;
import com.checkout.issuing.testing.responses.CardAuthorizationResponse;
import com.checkout.issuing.testing.responses.TransactionStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("Avoid creating cards all the time")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IssuingTestingTestIT extends BaseIssuingTestIT {

    private CardResponse card;

    @BeforeAll
    void setUp() {
        final CardholderResponse cardholder = createCardholder();
        card = createCard(cardholder.getId(), true);
    }

    @Test
    void shouldSimulateAuthorization() {
        final CardAuthorizationRequest request = CardAuthorizationRequest.builder()
                .card(CardSimulation.builder()
                        .id(card.getId())
                        .expiryMonth(card.getExpiryMonth())
                        .expiryYear(card.getExpiryYear())
                        .build())
                .transaction(TransactionSimulation.builder()
                        .type(TransactionType.PURCHASE)
                        .amount(100)
                        .currency(Currency.GBP)
                        .build())
                .build();

        final CardAuthorizationResponse response = blocking(() ->
                issuingApi.issuingClient().simulateAuthorization(request));

        assertNotNull(response);
        assertEquals(TransactionStatus.AUTHORIZED, response.getStatus());
    }
}
