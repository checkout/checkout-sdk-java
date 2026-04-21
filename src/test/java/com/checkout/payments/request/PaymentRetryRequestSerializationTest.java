package com.checkout.payments.request;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentRetryRequestSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeWithDunningConfig() {
        final PaymentRetryRequest request = PaymentRetryRequest.builder()
                .dunning(DunningRetryRequest.builder()
                        .enabled(true)
                        .maxAttempts(3)
                        .endAfterDays(7)
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"dunning\""));
        assertTrue(json.contains("\"enabled\":true"));
        assertTrue(json.contains("\"max_attempts\":3"));
        assertTrue(json.contains("\"end_after_days\":7"));
    }

    @Test
    void shouldSerializeWithDowntimeConfig() {
        final PaymentRetryRequest request = PaymentRetryRequest.builder()
                .downtime(DowntimeRetryRequest.builder()
                        .enabled(true)
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"downtime\""));
        assertTrue(json.contains("\"enabled\":true"));
    }

    @Test
    void shouldSerializeWithBothDunningAndDowntime() {
        final PaymentRetryRequest request = PaymentRetryRequest.builder()
                .dunning(DunningRetryRequest.builder()
                        .enabled(true)
                        .maxAttempts(5)
                        .endAfterDays(14)
                        .build())
                .downtime(DowntimeRetryRequest.builder()
                        .enabled(false)
                        .build())
                .build();

        assertDoesNotThrow(() -> serializer.toJson(request));
    }

    @Test
    void shouldDeserializeDunningFromJson() {
        final String json = "{"
                + "\"dunning\":{"
                + "  \"enabled\":true,"
                + "  \"max_attempts\":3,"
                + "  \"end_after_days\":7"
                + "},"
                + "\"downtime\":{"
                + "  \"enabled\":false"
                + "}"
                + "}";

        final PaymentRetryRequest request = serializer.fromJson(json, PaymentRetryRequest.class);

        assertNotNull(request);
        assertNotNull(request.getDunning());
        assertTrue(request.getDunning().getEnabled());
        assertEquals(3, request.getDunning().getMaxAttempts());
        assertEquals(7, request.getDunning().getEndAfterDays());
        assertNotNull(request.getDowntime());
        assertEquals(false, request.getDowntime().getEnabled());
    }

    @Test
    void shouldRoundTripSerialize() {
        final PaymentRetryRequest original = PaymentRetryRequest.builder()
                .dunning(DunningRetryRequest.builder()
                        .enabled(true)
                        .maxAttempts(3)
                        .endAfterDays(7)
                        .build())
                .downtime(DowntimeRetryRequest.builder()
                        .enabled(true)
                        .build())
                .build();

        final String json = serializer.toJson(original);
        final PaymentRetryRequest deserialized = serializer.fromJson(json, PaymentRetryRequest.class);

        assertNotNull(deserialized);
        assertNotNull(deserialized.getDunning());
        assertEquals(true, deserialized.getDunning().getEnabled());
        assertEquals(3, deserialized.getDunning().getMaxAttempts());
        assertEquals(7, deserialized.getDunning().getEndAfterDays());
        assertNotNull(deserialized.getDowntime());
        assertEquals(true, deserialized.getDowntime().getEnabled());
    }

    @Test
    void shouldHandleAbsentOptionalFields() {
        final String json = "{}";

        final PaymentRetryRequest request = serializer.fromJson(json, PaymentRetryRequest.class);

        assertNotNull(request);
        assertDoesNotThrow(() -> serializer.toJson(request));
        assertNull(request.getDunning());
        assertNull(request.getDowntime());
    }
}
