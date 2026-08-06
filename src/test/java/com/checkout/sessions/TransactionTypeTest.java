package com.checkout.sessions;

import com.checkout.GsonSerializer;
import com.checkout.Serializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionTypeTest {

    private final Serializer serializer = new GsonSerializer();

    private static Stream<Arguments> transactionTypes() {
        return Stream.of(
                Arguments.of(TransactionType.GOODS_SERVICE, "\"goods_service\""),
                Arguments.of(TransactionType.CHECK_ACCEPTANCE, "\"check_acceptance\""),
                Arguments.of(TransactionType.ACCOUNT_FUNDING, "\"account_funding\""),
                Arguments.of(TransactionType.QUASI_CARD_TRANSACTION, "\"quasi_card_transaction\""),
                Arguments.of(TransactionType.PREPAID_ACTIVATION_AND_LOAD, "\"prepaid_activation_and_load\"")
        );
    }

    @ParameterizedTest
    @MethodSource("transactionTypes")
    void shouldSerializeTransactionTypeToSnakeCase(final TransactionType value, final String expectedJson) {
        assertEquals(expectedJson, serializer.toJson(value));
    }

    @ParameterizedTest
    @MethodSource("transactionTypes")
    void shouldDeserializeTransactionTypeFromSnakeCase(final TransactionType expected, final String json) {
        assertEquals(expected, serializer.fromJson(json, TransactionType.class));
    }

    @Test
    void shouldExposeTheFiveTypesDefinedBySpec() {
        assertEquals(5, TransactionType.values().length);
    }

    @Test
    void shouldRoundTripAllValues() {
        for (final TransactionType value : TransactionType.values()) {
            assertEquals(value, serializer.fromJson(serializer.toJson(value), TransactionType.class));
        }
    }

    /**
     * The constant name retains a historical misspelling, but the API value is
     * {@code quasi_card_transaction}. Guards both directions of the wire contract.
     */
    @Test
    void shouldUseTheSpecSpellingForQuasiCardTransaction() {
        assertEquals("\"quasi_card_transaction\"", serializer.toJson(TransactionType.QUASI_CARD_TRANSACTION));
        assertEquals(TransactionType.QUASI_CARD_TRANSACTION,
                serializer.fromJson("\"quasi_card_transaction\"", TransactionType.class));
    }

    @Test
    void shouldSerializeQuasiCardTransactionOnSessionRequest() {
        final SessionRequest request = SessionRequest.builder()
                .transactionType(TransactionType.QUASI_CARD_TRANSACTION)
                .build();

        assertTrue(serializer.toJson(request).contains("\"transaction_type\":\"quasi_card_transaction\""));
    }

    @ParameterizedTest
    @MethodSource("transactionTypes")
    void shouldDeserializeEveryTypeOnGetSessionResponse(final TransactionType expected, final String json) {
        final GetSessionResponse response =
                serializer.fromJson("{\"transaction_type\":" + json + "}", GetSessionResponse.class);

        assertNotNull(response);
        assertEquals(expected, response.getTransactionType());
    }

}
