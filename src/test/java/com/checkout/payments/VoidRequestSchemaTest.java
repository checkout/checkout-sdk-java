package com.checkout.payments;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema validation tests for VoidRequest.
 * Validates serialization and field completeness against swagger, in
 * particular the optional amount field (partial voids, swagger 2026-08-13):
 * when amount is not set it must be absent from the JSON body, because a
 * null or zero amount leaking in would turn a full void into a zero-amount void.
 */
class VoidRequestSchemaTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeWithAllFields() {
        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("coupon_code", "NY2018");

        final VoidRequest request = VoidRequest.builder()
                .amount(6540L)
                .reference("ORD-5023-4E89")
                .metadata(metadata)
                .build();

        assertDoesNotThrow(() -> {
            final String json = serializer.toJson(request);
            assertNotNull(json);
            assertTrue(json.contains("\"amount\":6540"));
            assertTrue(json.contains("\"reference\":\"ORD-5023-4E89\""));
            assertTrue(json.contains("\"coupon_code\":\"NY2018\""));
        });
    }

    @Test
    void shouldOmitAmountWhenNotSet() {
        final VoidRequest request = VoidRequest.builder()
                .reference("ORD-5023-4E89")
                .build();

        final String json = serializer.toJson(request);
        assertNotNull(json);
        assertFalse(json.contains("amount"));
    }

    @Test
    void shouldDeserializeFromSwaggerExample() {
        final String json = "{\"amount\":6540,\"reference\":\"ORD-5023-4E89\",\"metadata\":{\"coupon_code\":\"NY2018\"}}";

        final VoidRequest request = serializer.fromJson(json, VoidRequest.class);
        assertNotNull(request);
        assertEquals(Long.valueOf(6540L), request.getAmount());
        assertEquals("ORD-5023-4E89", request.getReference());
        assertEquals("NY2018", request.getMetadata().get("coupon_code"));
    }

    @Test
    void shouldRoundTripSerialize() {
        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("partner_id", "123989");

        final VoidRequest original = VoidRequest.builder()
                .amount(1L)
                .reference("REF-1")
                .metadata(metadata)
                .build();

        final String json = serializer.toJson(original);
        final VoidRequest deserialized = serializer.fromJson(json, VoidRequest.class);

        assertEquals(original.getAmount(), deserialized.getAmount());
        assertEquals(original.getReference(), deserialized.getReference());
        assertEquals(original.getMetadata(), deserialized.getMetadata());
    }

    @Test
    void shouldRoundTripWithoutAmountAsFullVoid() {
        final VoidRequest original = VoidRequest.builder()
                .reference("REF-FULL-VOID")
                .build();

        final String json = serializer.toJson(original);
        final VoidRequest deserialized = serializer.fromJson(json, VoidRequest.class);

        assertNull(deserialized.getAmount());
        assertEquals("REF-FULL-VOID", deserialized.getReference());
    }
}
