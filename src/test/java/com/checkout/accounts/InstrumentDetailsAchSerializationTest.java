package com.checkout.accounts;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InstrumentDetailsAchSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeWithAllProperties() {
        final InstrumentDetailsAch details = InstrumentDetailsAch.builder()
                .accountNumber("12345100")
                .routingNumber("026009593")
                .accountType(InstrumentAccountType.SAVINGS)
                .build();

        final String json = serializer.toJson(details);

        assertTrue(json.contains("\"account_number\""));
        assertTrue(json.contains("\"routing_number\""));
        assertTrue(json.contains("\"account_type\""));
        assertTrue(json.contains("savings"));
    }

    @Test
    void shouldDeserializeSwaggerExample() {
        final String json = "{\"account_number\":\"12345100\",\"routing_number\":\"026009593\",\"account_type\":\"checking\"}";

        final InstrumentDetailsAch details = serializer.fromJson(json, InstrumentDetailsAch.class);

        assertNotNull(details);
        assertEquals("12345100", details.getAccountNumber());
        assertEquals("026009593", details.getRoutingNumber());
        assertEquals(InstrumentAccountType.CHECKING, details.getAccountType());
    }
}
