package com.checkout.payments.response.source;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PayPalResponseSourceSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDeserializeWithAccountHolder() {
        final String json = "{"
                + "\"type\":\"paypal\","
                + "\"account_holder\":{"
                + "  \"email\":\"john.smith@example.com\","
                + "  \"full_name\":\"John Smith\""
                + "}"
                + "}";

        final PayPalResponseSource source = serializer.fromJson(json, PayPalResponseSource.class);

        assertNotNull(source);
        assertNotNull(source.getAccountHolder());
        assertEquals("john.smith@example.com", source.getAccountHolder().getEmail());
        assertEquals("John Smith", source.getAccountHolder().getFullName());
    }

    @Test
    void shouldDeserializeWithEmailOnly() {
        final String json = "{"
                + "\"type\":\"paypal\","
                + "\"account_holder\":{"
                + "  \"email\":\"buyer@example.com\""
                + "}"
                + "}";

        final PayPalResponseSource source = serializer.fromJson(json, PayPalResponseSource.class);

        assertNotNull(source);
        assertNotNull(source.getAccountHolder());
        assertEquals("buyer@example.com", source.getAccountHolder().getEmail());
        assertNull(source.getAccountHolder().getFullName());
    }

    @Test
    void shouldDeserializeSwaggerExample() {
        final String swaggerJson = "{"
                + "\"type\":\"paypal\","
                + "\"account_holder\":{"
                + "  \"email\":\"payer@example.com\","
                + "  \"full_name\":\"Jane Doe\""
                + "}"
                + "}";

        final PayPalResponseSource source = serializer.fromJson(swaggerJson, PayPalResponseSource.class);

        assertNotNull(source);
        assertNotNull(source.getAccountHolder());
        assertEquals("payer@example.com", source.getAccountHolder().getEmail());
        assertEquals("Jane Doe", source.getAccountHolder().getFullName());
    }

    @Test
    void shouldRoundTripSerialize() {
        final String original = "{"
                + "\"type\":\"paypal\","
                + "\"account_holder\":{"
                + "  \"email\":\"john.smith@example.com\","
                + "  \"full_name\":\"John Smith\""
                + "}"
                + "}";

        final PayPalResponseSource source = serializer.fromJson(original, PayPalResponseSource.class);
        final String reserialised = serializer.toJson(source);
        final PayPalResponseSource roundTripped = serializer.fromJson(reserialised, PayPalResponseSource.class);

        assertNotNull(roundTripped);
        assertNotNull(roundTripped.getAccountHolder());
        assertEquals("john.smith@example.com", roundTripped.getAccountHolder().getEmail());
        assertEquals("John Smith", roundTripped.getAccountHolder().getFullName());
    }

    @Test
    void shouldHandleAbsentOptionalFields() {
        final String json = "{\"type\":\"paypal\"}";

        final PayPalResponseSource source = serializer.fromJson(json, PayPalResponseSource.class);

        assertNotNull(source);
        assertDoesNotThrow(() -> serializer.toJson(source));
        assertNull(source.getAccountHolder());
    }
}
