package com.checkout.payments.previous.request.source.apm;

import com.checkout.GsonSerializer;
import com.checkout.common.PaymentSourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Serialization tests for the previous-platform SEPA source.
 *
 * <p>These pin the wire contract that the previous platform references a stored SEPA mandate
 * through the generic "id" source. The source class was switched from the deprecated
 * PaymentSourceType.SEPA to PaymentSourceType.ID, which both map to "id", so these assertions must
 * hold identically before and after that change.
 */
class RequestSepaSourceSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeTypeAsIdAndNotSepa() {
        final RequestSepaSource source = RequestSepaSource.builder()
                .id("src_wmlfc3zyhqzehihu7giusaaawu")
                .build();

        final String json = serializer.toJson(source);

        assertTrue(json.contains("\"type\":\"id\""));
        assertFalse(json.contains("\"sepa\""));
        assertTrue(json.contains("\"id\":\"src_wmlfc3zyhqzehihu7giusaaawu\""));
    }

    @Test
    void shouldUseTheIdSourceTypeConstant() {
        assertEquals(PaymentSourceType.ID, new RequestSepaSource().getType());
        assertEquals(PaymentSourceType.ID,
                RequestSepaSource.builder().id("src_wmlfc3zyhqzehihu7giusaaawu").build().getType());
    }

    @Test
    void shouldMatchTheCurrentPlatformSourceOnEverythingButTheType() {
        final String previousJson = serializer.toJson(RequestSepaSource.builder()
                .id("src_wmlfc3zyhqzehihu7giusaaawu")
                .build());
        final String currentJson = serializer.toJson(
                com.checkout.payments.request.source.apm.RequestSepaSource.builder().build());

        assertTrue(previousJson.contains("\"type\":\"id\""));
        assertTrue(currentJson.contains("\"type\":\"sepa\""));
    }
}
