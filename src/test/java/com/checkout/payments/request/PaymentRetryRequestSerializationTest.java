package com.checkout.payments.request;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentRetryRequestSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeWithDunningConfig() {
        final PaymentRetryRequest request = PaymentRetryRequest.builder()
                .enabled(true)
                .dunning(DunningRetryRequest.builder()
                        .enabled(true)
                        .maxAttempts(3)
                        .endAfterDays(7)
                        .build())
                .build();

        assertEquals("{\"enabled\":true,\"dunning\":{\"enabled\":true,\"max_attempts\":3,\"end_after_days\":7}}",
                serializer.toJson(request));
    }

    @Test
    void shouldSerializeWithDowntimeConfig() {
        final PaymentRetryRequest request = PaymentRetryRequest.builder()
                .enabled(true)
                .downtime(DowntimeRetryRequest.builder().enabled(true).build())
                .build();

        assertEquals("{\"enabled\":true,\"downtime\":{\"enabled\":true}}", serializer.toJson(request));
    }

    @Test
    void shouldSerializeWithBothDunningAndDowntime() {
        final PaymentRetryRequest request = PaymentRetryRequest.builder()
                .enabled(true)
                .dunning(DunningRetryRequest.builder()
                        .enabled(true)
                        .maxAttempts(5)
                        .endAfterDays(14)
                        .build())
                .downtime(DowntimeRetryRequest.builder().enabled(false).build())
                .build();

        assertEquals("{\"enabled\":true,"
                        + "\"dunning\":{\"enabled\":true,\"max_attempts\":5,\"end_after_days\":14},"
                        + "\"downtime\":{\"enabled\":false}}",
                serializer.toJson(request));
    }

    @Test
    void shouldSerializeEnabledFalseEverywhere() {
        final PaymentRetryRequest request = PaymentRetryRequest.builder()
                .enabled(false)
                .dunning(DunningRetryRequest.builder().enabled(false).build())
                .downtime(DowntimeRetryRequest.builder().enabled(false).build())
                .build();

        assertEquals("{\"enabled\":false,\"dunning\":{\"enabled\":false},\"downtime\":{\"enabled\":false}}",
                serializer.toJson(request));
    }

    @Test
    void shouldStillSendDeprecatedTopLevelFields() {
        final PaymentRetryRequest request = serializer.fromJson(
                "{\"enabled\":true,\"max_attempts\":4,\"end_after_days\":10}", PaymentRetryRequest.class);

        assertEquals("{\"enabled\":true,\"max_attempts\":4,\"end_after_days\":10}", serializer.toJson(request));
    }

    @Test
    void shouldDeserializeFromJson() {
        final String json = "{\"enabled\":true,"
                + "\"dunning\":{\"enabled\":true,\"max_attempts\":3,\"end_after_days\":7},"
                + "\"downtime\":{\"enabled\":false}}";

        final PaymentRetryRequest request = serializer.fromJson(json, PaymentRetryRequest.class);

        assertTrue(request.getEnabled());
        assertTrue(request.getDunning().getEnabled());
        assertEquals(3, request.getDunning().getMaxAttempts());
        assertEquals(7, request.getDunning().getEndAfterDays());
        assertFalse(request.getDowntime().getEnabled());
    }

    @Test
    void shouldRoundTripAllProperties() {
        final PaymentRetryRequest original = serializer.fromJson("{\"enabled\":true,"
                + "\"max_attempts\":2,\"end_after_days\":3,"
                + "\"dunning\":{\"enabled\":true,\"max_attempts\":15,\"end_after_days\":60},"
                + "\"downtime\":{\"enabled\":true}}", PaymentRetryRequest.class);

        final String json = serializer.toJson(original);

        assertEquals("{\"enabled\":true,"
                + "\"dunning\":{\"enabled\":true,\"max_attempts\":15,\"end_after_days\":60},"
                + "\"downtime\":{\"enabled\":true},"
                + "\"max_attempts\":2,\"end_after_days\":3}", json);
        assertEquals(original, serializer.fromJson(json, PaymentRetryRequest.class));
    }

    @Test
    void shouldHandleAbsentOptionalFields() {
        final PaymentRetryRequest request = serializer.fromJson("{}", PaymentRetryRequest.class);

        assertNotNull(request);
        assertNull(request.getEnabled());
        assertNull(request.getDunning());
        assertNull(request.getDowntime());
        assertEquals("{}", serializer.toJson(request));
    }
}
