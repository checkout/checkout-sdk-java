package com.checkout.issuing.cards;

import com.checkout.GsonSerializer;
import com.checkout.issuing.cards.requests.update.CardUpdateHeaders;
import com.checkout.issuing.cards.responses.update.UpdateCardResponse;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * GSON serialization tests for UpdateCardResponse and CardUpdateHeaders.
 *
 * Swagger reference: PATCH /issuing/cards/{cardId}
 * Schema: update-card-response. Only last_modified_date is required; encrypted_cvv is returned
 * solely when the return-encrypted-cvv header is set to true.
 */
class CardUpdateResponseSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDeserializeWithEncryptedCvv() {
        final String json = "{"
                + "\"last_modified_date\":\"2026-06-01T10:00:00Z\","
                + "\"encrypted_cvv\":\"ZW5jcnlwdGVkLWN2dg==\","
                + "\"_links\":{\"self\":{\"href\":\"https://api.checkout.com/issuing/cards/crd_test\"}}"
                + "}";

        final UpdateCardResponse response = serializer.fromJson(json, UpdateCardResponse.class);

        assertNotNull(response);
        assertNotNull(response.getLastModifiedDate());
        assertEquals("ZW5jcnlwdGVkLWN2dg==", response.getEncryptedCvv());
    }

    /**
     * encrypted_cvv is optional: a plain update that did not opt in must leave it null.
     */
    @Test
    void shouldDeserializeWithoutEncryptedCvv() {
        final UpdateCardResponse response = serializer.fromJson(
                "{\"last_modified_date\":\"2026-06-01T10:00:00Z\"}", UpdateCardResponse.class);

        assertNotNull(response);
        assertNotNull(response.getLastModifiedDate());
        assertNull(response.getEncryptedCvv());
    }

    /**
     * Gson HTML-escapes by default, so a Base64 value containing '=' is emitted as \\u003d. The key
     * name is what this test pins; the exact value is verified by the round trip below.
     */
    @Test
    void shouldSerializeEncryptedCvvUsingTheSwaggerKey() {
        final UpdateCardResponse response = new UpdateCardResponse();
        response.setEncryptedCvv("ZW5jcnlwdGVkLWN2dg==");

        final String json = serializer.toJson(response);

        assertTrue(json.contains("\"encrypted_cvv\""));
        assertFalse(json.contains("\"encryptedCvv\""));
    }

    @Test
    void shouldRoundTripEncryptedCvvExactly() {
        final UpdateCardResponse original = new UpdateCardResponse();
        original.setEncryptedCvv("ZW5jcnlwdGVkLWN2dg==");

        final UpdateCardResponse deserialized =
                serializer.fromJson(serializer.toJson(original), UpdateCardResponse.class);

        assertEquals("ZW5jcnlwdGVkLWN2dg==", deserialized.getEncryptedCvv());
    }

    @Test
    void shouldExposeTheUpdateHeadersUsingTheExactSwaggerHeaderNames() {
        final CardUpdateHeaders headers = CardUpdateHeaders.builder()
                .returnEncryptedCvv(true)
                .encryptionKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A")
                .build();

        final Map<String, String> map = headers.getHeaders();

        // Header names are case sensitive: return-encrypted-cvv is lower case,
        // Encryption-Key is title case.
        assertEquals("true", map.get("return-encrypted-cvv"));
        assertEquals("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A", map.get("Encryption-Key"));
        assertEquals(2, map.size());
    }

    @Test
    void shouldOmitUnsetUpdateHeaders() {
        final Map<String, String> map = CardUpdateHeaders.builder().returnEncryptedCvv(true).build().getHeaders();

        assertEquals(1, map.size());
        assertTrue(map.containsKey("return-encrypted-cvv"));
    }
}
