package com.checkout.instruments.get;

import com.checkout.GsonSerializer;
import com.checkout.common.CardCategory;
import com.checkout.common.CardType;
import com.checkout.common.CardWalletType;
import com.checkout.common.CountryCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GetCardInstrumentResponseSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDeserializeMinimalResponse() {
        final String json = "{"
                + "\"type\":\"card\","
                + "\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"expiry_month\":6,"
                + "\"expiry_year\":2025,"
                + "\"last4\":\"1234\","
                + "\"regulated_indicator\":true"
                + "}";

        final GetCardInstrumentResponse response = serializer.fromJson(json, GetCardInstrumentResponse.class);

        assertNotNull(response);
        assertEquals(6, response.getExpiryMonth());
        assertEquals(2025, response.getExpiryYear());
        assertEquals("1234", response.getLast4());
        assertTrue(response.getRegulatedIndicator());
    }

    @Test
    void shouldDeserializeEncryptedCardNumber() {
        final String json = "{"
                + "\"type\":\"card\","
                + "\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"last4\":\"4242\","
                + "\"regulated_indicator\":false,"
                + "\"encrypted_card_number\":\"eyJhbGciOiJSU0EtT0FFUC0yNTYifQ...\""
                + "}";

        final GetCardInstrumentResponse response = serializer.fromJson(json, GetCardInstrumentResponse.class);

        assertNotNull(response);
        assertEquals("eyJhbGciOiJSU0EtT0FFUC0yNTYifQ...", response.getEncryptedCardNumber());
    }

    @Test
    void shouldDeserializeNetworkToken() {
        final String json = "{"
                + "\"type\":\"card\","
                + "\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"last4\":\"4242\","
                + "\"regulated_indicator\":false,"
                + "\"network_token\":{"
                + "  \"id\":\"nt_abcdefghijklmnopqrstuvwxyz12\","
                + "  \"state\":\"active\""
                + "}"
                + "}";

        final GetCardInstrumentResponse response = serializer.fromJson(json, GetCardInstrumentResponse.class);

        assertNotNull(response);
        assertNotNull(response.getNetworkToken());
        assertEquals("nt_abcdefghijklmnopqrstuvwxyz12", response.getNetworkToken().getId());
        assertEquals(NetworkTokenState.ACTIVE, response.getNetworkToken().getState());
    }

    @Test
    void shouldDeserializeNetworkTokenStates() {
        for (final String state : new String[]{"active", "suspended", "inactive"}) {
            final String json = "{\"type\":\"card\",\"last4\":\"4242\",\"regulated_indicator\":false,"
                    + "\"network_token\":{\"id\":\"nt_abcdefghijklmnopqrstuvwxyz12\",\"state\":\"" + state + "\"}}";
            final GetCardInstrumentResponse response = serializer.fromJson(json, GetCardInstrumentResponse.class);
            assertNotNull(response.getNetworkToken().getState(), "State should not be null for: " + state);
        }
    }

    @Test
    void shouldDeserializeCardWalletType() {
        final String jsonGpay = "{"
                + "\"type\":\"card\",\"last4\":\"4242\",\"regulated_indicator\":false,"
                + "\"card_wallet_type\":\"googlepay\""
                + "}";
        final String jsonApay = "{"
                + "\"type\":\"card\",\"last4\":\"4242\",\"regulated_indicator\":false,"
                + "\"card_wallet_type\":\"applepay\""
                + "}";

        final GetCardInstrumentResponse gpay = serializer.fromJson(jsonGpay, GetCardInstrumentResponse.class);
        final GetCardInstrumentResponse apay = serializer.fromJson(jsonApay, GetCardInstrumentResponse.class);

        assertEquals(CardWalletType.GOOGLEPAY, gpay.getCardWalletType());
        assertEquals(CardWalletType.APPLEPAY, apay.getCardWalletType());
    }

    @Test
    void shouldDeserializeFullResponse() {
        final String json = "{"
                + "\"type\":\"card\","
                + "\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"fingerprint\":\"vnsdrvikkvre3dtrjpwv7ire7u\","
                + "\"expiry_month\":6,"
                + "\"expiry_year\":2025,"
                + "\"name\":\"John Smith\","
                + "\"scheme\":\"Visa\","
                + "\"scheme_local\":\"Cartes Bancaires\","
                + "\"last4\":\"4242\","
                + "\"bin\":\"424242\","
                + "\"card_type\":\"Credit\","
                + "\"card_category\":\"Consumer\","
                + "\"issuer\":\"JPMORGAN CHASE BANK NA\","
                + "\"issuer_country\":\"US\","
                + "\"product_id\":\"A\","
                + "\"product_type\":\"Visa Traditional\","
                + "\"encrypted_card_number\":\"eyJhbGciOiJSU0EtT0FFUC0yNTYifQ...\","
                + "\"network_token\":{\"id\":\"nt_abcdefghijklmnopqrstuvwxyz12\",\"state\":\"active\"},"
                + "\"card_wallet_type\":\"googlepay\","
                + "\"regulated_indicator\":true"
                + "}";

        final GetCardInstrumentResponse response = serializer.fromJson(json, GetCardInstrumentResponse.class);

        assertNotNull(response);
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", response.getId());
        assertEquals(6, response.getExpiryMonth());
        assertEquals("John Smith", response.getName());
        assertEquals("Visa", response.getScheme());
        assertEquals("4242", response.getLast4());
        assertEquals(CardType.CREDIT, response.getCardType());
        assertEquals(CardCategory.CONSUMER, response.getCardCategory());
        assertEquals(CountryCode.US, response.getIssuerCountry());
        assertEquals("eyJhbGciOiJSU0EtT0FFUC0yNTYifQ...", response.getEncryptedCardNumber());
        assertNotNull(response.getNetworkToken());
        assertEquals(NetworkTokenState.ACTIVE, response.getNetworkToken().getState());
        assertEquals(CardWalletType.GOOGLEPAY, response.getCardWalletType());
        assertTrue(response.getRegulatedIndicator());
    }

    @Test
    void shouldRoundTripSerialize() {
        final String original = "{"
                + "\"type\":\"card\","
                + "\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"last4\":\"4242\","
                + "\"encrypted_card_number\":\"eyJhbGciOiJSU0EtT0FFUC0yNTYifQ...\","
                + "\"network_token\":{\"id\":\"nt_abcdefghijklmnopqrstuvwxyz12\",\"state\":\"suspended\"},"
                + "\"card_wallet_type\":\"applepay\","
                + "\"regulated_indicator\":true"
                + "}";

        final GetCardInstrumentResponse response = serializer.fromJson(original, GetCardInstrumentResponse.class);
        final String reserialised = serializer.toJson(response);
        final GetCardInstrumentResponse roundTripped = serializer.fromJson(reserialised, GetCardInstrumentResponse.class);

        assertNotNull(roundTripped);
        assertEquals(response.getId(), roundTripped.getId());
        assertEquals(response.getEncryptedCardNumber(), roundTripped.getEncryptedCardNumber());
        assertEquals(response.getNetworkToken().getState(), roundTripped.getNetworkToken().getState());
        assertEquals(response.getCardWalletType(), roundTripped.getCardWalletType());
        assertEquals(response.getRegulatedIndicator(), roundTripped.getRegulatedIndicator());
    }

    @Test
    void shouldHandleAbsentOptionalFields() {
        final String json = "{"
                + "\"type\":\"card\","
                + "\"last4\":\"4242\","
                + "\"regulated_indicator\":false"
                + "}";

        final GetCardInstrumentResponse response = serializer.fromJson(json, GetCardInstrumentResponse.class);

        assertNotNull(response);
        assertDoesNotThrow(() -> serializer.toJson(response));
        assertEquals(null, response.getEncryptedCardNumber());
        assertEquals(null, response.getNetworkToken());
        assertEquals(null, response.getCardWalletType());
    }
}
