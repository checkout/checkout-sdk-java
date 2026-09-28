package com.checkout.issuing.cards;

import com.checkout.issuing.cards.requests.update.CardUpdateHeaders;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for CardUpdateHeaders.
 *
 * Swagger reference: PATCH /issuing/cards/{cardId}
 * Schema: update-card-response. The 2026-09-02 delta added encrypted_cvv here; the 2026-09-17
 * delta (INT-1700) removed it again, so the current spec never includes it. Coverage for the
 * current update-card-response field set lives in CardScheduledRevocationAndActivationSerializationTest.
 */
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
