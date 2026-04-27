package com.checkout.common;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentMethodTypeTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void blik_shouldSerializeToExpectedJsonValue() {
        final String json = serializer.toJson(PaymentMethodType.BLIK);
        assertEquals("\"blik\"", json);
    }

    @Test
    void blik_shouldDeserializeFromExpectedJsonValue() {
        final PaymentMethodType type = serializer.fromJson("\"blik\"", PaymentMethodType.class);
        assertEquals(PaymentMethodType.BLIK, type);
    }
}
