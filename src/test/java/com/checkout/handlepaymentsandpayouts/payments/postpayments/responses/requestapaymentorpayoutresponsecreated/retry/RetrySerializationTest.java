package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.retry;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RetrySerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDeserializeAllProperties() {
        final String json = "{\"enabled\":true,\"max_attempts\":6,"
                + "\"ends_on\":\"2019-08-24T14:15:22Z\",\"next_attempt_on\":\"2019-08-25T14:15:22Z\"}";

        final Retry retry = serializer.fromJson(json, Retry.class);

        assertTrue(retry.getEnabled());
        assertEquals(6, retry.getMaxAttempts());
        assertEquals(Instant.parse("2019-08-24T14:15:22Z"), retry.getEndsOn());
        assertEquals(Instant.parse("2019-08-25T14:15:22Z"), retry.getNextAttemptOn());
    }

    @Test
    void shouldNotInventMaxAttemptsWhenAbsent() {
        assertNull(serializer.fromJson("{\"enabled\":true}", Retry.class).getMaxAttempts());
    }

    @Test
    void shouldRoundTrip() {
        final Retry original = Retry.builder()
                .enabled(false)
                .maxAttempts(1)
                .endsOn(Instant.parse("2019-08-24T14:15:22Z"))
                .nextAttemptOn(Instant.parse("2019-08-25T14:15:22Z"))
                .build();

        final String json = serializer.toJson(original);

        assertTrue(json.contains("\"enabled\":false"));
        assertEquals(original, serializer.fromJson(json, Retry.class));
    }
}
