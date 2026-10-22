package com.checkout.issuing.cards;

import com.checkout.issuing.cards.requests.update.CardUpdateHeaders;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the deprecated CardUpdateHeaders, which must keep sending the headers when set.
 *
 * Swagger reference: PATCH /issuing/cards/{cardId}
 * The current spec documents neither these headers nor encrypted_cvv in update-card-response.
 * Coverage for the update-card-response field set lives in
 * CardScheduledRevocationAndActivationSerializationTest.
 */
@SuppressWarnings("deprecation")
class CardUpdateResponseSerializationTest {

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
