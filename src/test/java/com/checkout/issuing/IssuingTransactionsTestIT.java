package com.checkout.issuing;

import com.checkout.issuing.cardholders.CardholderResponse;
import com.checkout.issuing.cards.responses.CardResponse;
import com.checkout.issuing.transactions.requests.TransactionsQuery;
import com.checkout.issuing.transactions.responses.TransactionsListResponse;
import com.checkout.issuing.transactions.responses.TransactionsSingleResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IssuingTransactionsTestIT extends BaseIssuingTestIT {

    private CardholderResponse cardholder;
    private CardResponse card;

    @BeforeAll
    void setUp() {
        cardholder = createCardholder();
        card = createCard(cardholder.getId());
    }

    @Test
    void shouldGetListTransactions() {
        final TransactionsQuery query = createTransactionsQuery();

        final TransactionsListResponse response = blocking(() -> 
            issuingApi.issuingClient().getListTransactions(query));

        validateTransactionsListResponse(response);
    }

    @Test
    void shouldGetSingleTransaction() {
        // First get transactions to have a valid transaction ID
        final TransactionsQuery query = createTransactionsQuery();
        final TransactionsListResponse listResponse = blocking(() -> 
            issuingApi.issuingClient().getListTransactions(query));

        if (listResponse.getData() != null && !listResponse.getData().isEmpty()) {
            final String transactionId = listResponse.getData().get(0).getId();

            final TransactionsSingleResponse response = blocking(() -> 
                issuingApi.issuingClient().getSingleTransaction(transactionId));

            validateTransactionsSingleResponse(response);
        }
    }

    // Synchronous methods
    @Test
    void shouldGetListTransactionsSync() {
        final TransactionsQuery query = createTransactionsQuery();

        final TransactionsListResponse response = issuingApi.issuingClient().getListTransactionsSync(query);

        validateTransactionsListResponse(response);
    }

    @Test
    void shouldGetSingleTransactionSync() {
        // First get transactions to have a valid transaction ID
        final TransactionsQuery query = createTransactionsQuery();
        final TransactionsListResponse listResponse = issuingApi.issuingClient().getListTransactionsSync(query);

        if (listResponse.getData() != null && !listResponse.getData().isEmpty()) {
            final String transactionId = listResponse.getData().get(0).getId();

            final TransactionsSingleResponse response = issuingApi.issuingClient().getSingleTransactionSync(transactionId);

            validateTransactionsSingleResponse(response);
        }
    }

    // Common methods
    private TransactionsQuery createTransactionsQuery() {
        return TransactionsQuery.builder()
                .cardId(card.getId())
                .limit(10)
                .build();
    }

    private void validateTransactionsListResponse(TransactionsListResponse response) {
        assertNotNull(response);
        assertNotNull(response.getLimit());
        assertNotNull(response.getSkip());
        assertNotNull(response.getTotalCount());
        assertNotNull(response.getData());
    }

    private void validateTransactionsSingleResponse(TransactionsSingleResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getTransactionType());
        assertNotNull(response.getStatus());
        assertNotNull(response.getAmounts());
        assertNotNull(response.getCreatedOn());
    }
}