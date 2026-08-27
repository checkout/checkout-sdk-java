package com.checkout.payments.request.source.apm;

import com.checkout.GsonSerializer;
import com.checkout.common.PaymentSourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema validation tests for {@link RequestBacsSource}.
 */
class RequestBacsSourceSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeTypeAndId() {
        final RequestBacsSource source = RequestBacsSource.builder()
                .id("src_wmlfc3zyhqzehihu7giusaaawu")
                .build();

        final String json = serializer.toJson(source);

        assertTrue(json.contains("\"type\":\"bacs\""));
        assertTrue(json.contains("\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\""));
    }

    @Test
    void shouldRoundTripTypeAndId() {
        final RequestBacsSource original = RequestBacsSource.builder()
                .id("src_wmlfc3zyhqzehihu7giusaaawu")
                .build();

        final RequestBacsSource result =
                serializer.fromJson(serializer.toJson(original), RequestBacsSource.class);

        assertNotNull(result);
        assertEquals(PaymentSourceType.BACS, result.getType());
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", result.getId());
    }

    @Test
    void shouldDefaultTypeOnNoArgsConstructor() {
        assertEquals(PaymentSourceType.BACS, new RequestBacsSource().getType());
    }
}
