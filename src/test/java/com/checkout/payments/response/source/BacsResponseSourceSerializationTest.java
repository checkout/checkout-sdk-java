package com.checkout.payments.response.source;

import com.checkout.GsonSerializer;
import com.checkout.common.PaymentSourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Schema validation tests for {@link BacsResponseSource}, including the polymorphic dispatch that
 * selects it instead of the alternative payment source fallback.
 */
class BacsResponseSourceSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDeserializeTypeAndId() {
        final BacsResponseSource source = serializer.fromJson(
                "{\"type\":\"bacs\",\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\"}",
                BacsResponseSource.class);

        assertNotNull(source);
        assertEquals(PaymentSourceType.BACS, source.getType());
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", source.getId());
    }

    @Test
    void shouldDispatchToTypedSourceAndNotTheFallback() {
        final ResponseSource source = serializer.fromJson(
                "{\"type\":\"bacs\",\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\"}",
                ResponseSource.class);

        final BacsResponseSource bacs = assertInstanceOf(BacsResponseSource.class, source);
        assertEquals(PaymentSourceType.BACS, bacs.getType());
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", bacs.getId());
    }
}
