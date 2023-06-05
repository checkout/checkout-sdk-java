package com.checkout.issuing;

import com.checkout.EmptyResponse;
import com.checkout.common.Currency;
import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cards.responses.CardResponse;
import com.checkout.issuing.testing.requests.CardAuthorizationClearingRequest;
import com.checkout.issuing.testing.requests.CardAuthorizationIncrementingRequest;
import com.checkout.issuing.testing.requests.CardAuthorizationRequest;
import com.checkout.issuing.testing.requests.CardAuthorizationReversalRequest;
import com.checkout.issuing.testing.requests.CardSimulation;
import com.checkout.issuing.testing.requests.TransactionMerchant;
import com.checkout.issuing.testing.requests.TransactionSimulation;
import com.checkout.issuing.testing.requests.TransactionType;
import com.checkout.issuing.testing.responses.CardAuthorizationIncrementingResponse;
import com.checkout.issuing.testing.responses.CardAuthorizationResponse;
import com.checkout.issuing.testing.responses.CardAuthorizationReversalResponse;
import com.checkout.issuing.testing.responses.ReversalStatus;
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
    private CardAuthorizationResponse transaction;

    @BeforeAll
    void setUp() {
        final CardholderResponse cardholder = createCardholder();
        card = createCard(cardholder.getId(), true);
        transaction = getCardAuthorizationResponse();
    }

    @Test
    void shouldSimulateAuthorization() {
        assertNotNull(transaction);
        assertEquals(TransactionStatus.AUTHORIZED, transaction.getStatus());
    }

    @Test
    void shouldSimulateIncrementingAuthorization() {
        final CardAuthorizationIncrementingRequest request = CardAuthorizationIncrementingRequest.builder()
                .amount(1)
                .build();

        final CardAuthorizationIncrementingResponse response = blocking(() ->
                issuingApi.issuingClient().simulateIncrementingAuthorization(transaction.getId(), request));

        assertNotNull(response);
        assertEquals(TransactionStatus.AUTHORIZED, response.getStatus());
    }

    @Test
    void shouldSimulateClearing() {
        final CardAuthorizationClearingRequest request = CardAuthorizationClearingRequest.builder()
                .amount(1)
                .build();

        final EmptyResponse response = blocking(() ->
                issuingApi.issuingClient().simulateClearing(transaction.getId(), request));

        assertNotNull(response);
        assertEquals(202, response.getHttpStatusCode());
    }

    @Test
    void shouldSimulateReversal() {
        final CardAuthorizationReversalRequest request = CardAuthorizationReversalRequest.builder()
                .amount(1)
                .build();

        final CardAuthorizationReversalResponse response = blocking(() ->
                issuingApi.issuingClient().simulateReversal(transaction.getId(), request));

        assertNotNull(response);
        assertEquals(ReversalStatus.REVERSED, response.getStatus());
    }

    private CardAuthorizationResponse getCardAuthorizationResponse() {
        final CardAuthorizationRequest authorizationRequest = CardAuthorizationRequest.builder()
                .card(CardSimulation.builder()
                        .id(card.getId())
                        .expiryMonth(card.getExpiryMonth())
                        .expiryYear(card.getExpiryYear())
                        .build())
                .transaction(TransactionSimulation.builder()
                        .type(TransactionType.PURCHASE)
                        .amount(100)
                        .currency(Currency.GBP)
                        .merchant(TransactionMerchant.builder()
                                .categoryCode("7399")
                                .build())
                        .build())
                .build();

        final CardAuthorizationResponse authorizationResponse = blocking(() ->
                issuingApi.issuingClient().simulateAuthorization(authorizationRequest));
        return authorizationResponse;
    }
}
