package com.checkout;

import com.checkout.accounts.Headers;
import com.checkout.agenticcommerce.request.DelegatePaymentHeaders;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Validates that all {@link IHeaders} implementations produce correct header maps.
 */
class HeadersReflectionTest {

    @Test
    void accountsHeadersShouldProduceCorrectHeaders() {
        final Headers headers = Headers.builder()
                .ifMatch("etag-value")
                .build();

        final Map<String, String> map = headers.getHeaders();

        assertNotNull(map);
        assertEquals(1, map.size());
        assertEquals("etag-value", map.get("if-match"));
    }

    @Test
    void accountsHeadersShouldOmitNullValues() {
        final Headers headers = Headers.builder().build();

        final Map<String, String> map = headers.getHeaders();

        assertNotNull(map);
        assertTrue(map.isEmpty());
    }

    @Test
    void delegatePaymentHeadersShouldProduceAllHeaders() {
        final DelegatePaymentHeaders headers = DelegatePaymentHeaders.builder()
                .signature("sha256=abc123")
                .timestamp("2026-04-08T12:00:00Z")
                .apiVersion("2025-01-01")
                .build();

        final Map<String, String> map = headers.getHeaders();

        assertNotNull(map);
        assertEquals(3, map.size());
        assertEquals("sha256=abc123", map.get("Signature"));
        assertEquals("2026-04-08T12:00:00Z", map.get("Timestamp"));
        assertEquals("2025-01-01", map.get("API-Version"));
    }

    @Test
    void delegatePaymentHeadersShouldOmitOptionalApiVersion() {
        final DelegatePaymentHeaders headers = DelegatePaymentHeaders.builder()
                .signature("sha256=abc123")
                .timestamp("2026-04-08T12:00:00Z")
                .build();

        final Map<String, String> map = headers.getHeaders();

        assertNotNull(map);
        assertEquals(2, map.size());
        assertEquals("sha256=abc123", map.get("Signature"));
        assertEquals("2026-04-08T12:00:00Z", map.get("Timestamp"));
        assertTrue(!map.containsKey("API-Version"));
    }

    @Test
    void delegatePaymentHeadersShouldOmitAllNullValues() {
        final DelegatePaymentHeaders headers = DelegatePaymentHeaders.builder().build();

        final Map<String, String> map = headers.getHeaders();

        assertNotNull(map);
        assertTrue(map.isEmpty());
    }
}
