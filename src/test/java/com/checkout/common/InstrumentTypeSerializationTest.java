package com.checkout.common;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Value-by-value serialization test for {@link InstrumentType}.
 */
class InstrumentTypeSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeEveryValueBothDirections() {
        final Map<InstrumentType, String> expected = new LinkedHashMap<>();
        expected.put(InstrumentType.BANK_ACCOUNT, "bank_account");
        expected.put(InstrumentType.TOKEN, "token");
        expected.put(InstrumentType.CARD, "card");
        expected.put(InstrumentType.CARD_TOKEN, "card_token");
        expected.put(InstrumentType.SEPA, "sepa");
        expected.put(InstrumentType.ACH, "ach");
        expected.put(InstrumentType.BACS, "bacs");

        assertEquals(expected.size(), InstrumentType.values().length);

        expected.forEach((value, wire) -> {
            assertEquals("\"" + wire + "\"", serializer.toJson(value));
            assertEquals(value, serializer.fromJson("\"" + wire + "\"", InstrumentType.class));
        });
    }
}
