package com.checkout.payments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.checkout.GsonSerializer;
import com.checkout.common.CountryCode;
import com.checkout.payments.request.PayoutRequest;
import com.checkout.payments.request.source.PayoutRequestCurrencyAccountSource;

class PayoutRequestProcessingTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeHubModelOriginationCountry() {
        final PayoutRequest request = PayoutRequest.builder()
                .source(PayoutRequestCurrencyAccountSource.builder().id("ca_123").build())
                .processing(ProcessingSettings.builder()
                        .hubModelOriginationCountry(CountryCode.AU)
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"hub_model_origination_country\":\"AU\""));
    }

    @Test
    void shouldDeserializeHubModelOriginationCountry() {
        final String json = "{\"processing\":{\"hub_model_origination_country\":\"AU\"}}";

        final PayoutRequest request = serializer.fromJson(json, PayoutRequest.class);

        assertNotNull(request.getProcessing());
        assertEquals(CountryCode.AU, request.getProcessing().getHubModelOriginationCountry());
    }
}