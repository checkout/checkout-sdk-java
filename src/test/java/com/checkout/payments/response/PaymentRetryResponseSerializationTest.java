package com.checkout.payments.response;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentRetryResponseSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDeserializeGetPaymentRetry() {
        final String json = "{\"enabled\":true,\"attempts_made\":1,\"max_attempts\":6,"
                + "\"ends_on\":\"2019-08-24T14:15:22Z\",\"next_attempt_on\":\"2019-08-25T14:15:22Z\"}";

        final PaymentRetryResponse retry = serializer.fromJson(json, PaymentRetryResponse.class);

        assertTrue(retry.getEnabled());
        assertEquals(1, retry.getAttemptsMade());
        assertEquals(6, retry.getMaxAttempts());
        assertEquals(Instant.parse("2019-08-24T14:15:22Z"), retry.getEndsOn());
        assertEquals(Instant.parse("2019-08-25T14:15:22Z"), retry.getNextAttemptOn());
    }

    @Test
    void shouldDeserializePostPaymentRetryWithoutAttemptsMade() {
        final PaymentRetryResponse retry = serializer.fromJson("{\"enabled\":false}", PaymentRetryResponse.class);

        assertFalse(retry.getEnabled());
        assertNull(retry.getAttemptsMade());
        assertNull(retry.getMaxAttempts());
    }

    @Test
    void shouldRoundTrip() {
        final PaymentRetryResponse original = PaymentRetryResponse.builder()
                .enabled(true)
                .attemptsMade(0)
                .maxAttempts(15)
                .endsOn(Instant.parse("2019-08-24T14:15:22Z"))
                .nextAttemptOn(Instant.parse("2019-08-25T14:15:22Z"))
                .build();

        assertEquals(original, serializer.fromJson(serializer.toJson(original), PaymentRetryResponse.class));
    }

    @Test
    void shouldDeserializeRetryInsidePaymentResponses() {
        final String retry = "{\"retry\":{\"enabled\":true,\"attempts_made\":2,\"max_attempts\":6}}";

        assertEquals(2, serializer.fromJson(retry, GetPaymentResponse.class).getRetry().getAttemptsMade());
        assertEquals(6, serializer.fromJson(retry, PaymentResponse.class).getRetry().getMaxAttempts());
    }

    // ------------------------------------------------------------------------
    // PaymentResponse.subscription
    // The spec PaymentResponse schema returns subscription (Subscription: id, max 50 characters).
    // The spec has no example value, so the id below is illustrative.
    // ------------------------------------------------------------------------

    @Test
    void shouldDeserializePaymentResponseSubscription() {
        final PaymentResponse response = serializer.fromJson(
                "{\"id\":\"pay_mbabizu24mvu3mela5njyhpit4\",\"subscription\":{\"id\":\"sub_reference_123\"}}",
                PaymentResponse.class);

        assertEquals("sub_reference_123", response.getSubscription().getId());
    }

    @Test
    void shouldRoundTripPaymentResponseSubscription() {
        final PaymentResponse original = serializer.fromJson(
                "{\"subscription\":{\"id\":\"sub_reference_123\"}}", PaymentResponse.class);

        final String json = serializer.toJson(original);

        assertTrue(json.contains("\"subscription\":{\"id\":\"sub_reference_123\"}"));
        assertEquals(original.getSubscription(), serializer.fromJson(json, PaymentResponse.class).getSubscription());
    }
}
