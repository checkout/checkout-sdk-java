package com.checkout.payments.response;

import com.checkout.GsonSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Deserialization tests for {@link ProcessingData}, focused on fields recently aligned with
 * the Checkout.com swagger spec (scheme, partner_fraud_status, partner_merchant_advice_code,
 * accommodation_data, airline_data, fallback_source_used, failure_code, partner_code,
 * partner_response_code).
 */
class ProcessingDataDeserializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDeserializeScheme() {
        final String json = "{\"scheme\":\"ACCEL\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("ACCEL", data.getScheme());
    }

    @Test
    void shouldDeserializePartnerFraudStatus() {
        final String json = "{\"partner_fraud_status\":\"Pending\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("Pending", data.getPartnerFraudStatus());
    }

    @Test
    void shouldDeserializePartnerMerchantAdviceCode() {
        final String json = "{\"partner_merchant_advice_code\":\"24\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("24", data.getPartnerMerchantAdviceCode());
    }

    @Test
    void shouldDeserializeAccommodationData() {
        final String json = "{\"accommodation_data\":[{\"name\":\"Grand Hotel\"}]}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertNotNull(data.getAccommodationData());
        assertEquals(1, data.getAccommodationData().size());
        assertEquals("Grand Hotel", data.getAccommodationData().get(0).getName());
    }

    @Test
    void shouldDeserializeAirlineData() {
        final String json = "{\"airline_data\":[{\"ticket\":{\"number\":\"045-21351455613\"}}]}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertNotNull(data.getAirlineData());
        assertEquals(1, data.getAirlineData().size());
        assertNotNull(data.getAirlineData().get(0).getTicket());
        assertEquals("045-21351455613", data.getAirlineData().get(0).getTicket().getNumber());
    }

    @Test
    void shouldDeserializeFallbackSourceUsed() {
        final String json = "{\"fallback_source_used\":true}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals(Boolean.TRUE, data.getFallbackSourceUsed());
    }

    @Test
    void shouldDeserializeFailureCode() {
        final String json = "{\"failure_code\":\"partner_error\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("partner_error", data.getFailureCode());
    }

    @Test
    void shouldDeserializePartnerCode() {
        final String json = "{\"partner_code\":\"999111\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("999111", data.getPartnerCode());
    }

    @Test
    void shouldDeserializePartnerResponseCode() {
        final String json = "{\"partner_response_code\":\"ER_WRONG_TICKET\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("ER_WRONG_TICKET", data.getPartnerResponseCode());
    }

    @Test
    void shouldDeserializeAllNewFieldsTogether() {
        final String json = "{"
                + "\"scheme\":\"VISA\","
                + "\"partner_fraud_status\":\"Accepted\","
                + "\"partner_merchant_advice_code\":\"24\","
                + "\"fallback_source_used\":false,"
                + "\"failure_code\":\"partner_error\","
                + "\"partner_code\":\"902111\","
                + "\"partner_response_code\":\"DECLINED\""
                + "}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("VISA", data.getScheme());
        assertEquals("Accepted", data.getPartnerFraudStatus());
        assertEquals("24", data.getPartnerMerchantAdviceCode());
        assertEquals(Boolean.FALSE, data.getFallbackSourceUsed());
        assertEquals("partner_error", data.getFailureCode());
        assertEquals("902111", data.getPartnerCode());
        assertEquals("DECLINED", data.getPartnerResponseCode());
    }

    @Test
    void shouldLeaveNewFieldsNullWhenAbsent() {
        final String json = "{\"locale\":\"en-GB\"}";

        final ProcessingData data = serializer.fromJson(json, ProcessingData.class);

        assertNotNull(data);
        assertEquals("en-GB", data.getLocale());
        assertNull(data.getScheme());
        assertNull(data.getPartnerFraudStatus());
        assertNull(data.getPartnerMerchantAdviceCode());
        assertNull(data.getAccommodationData());
        assertNull(data.getAirlineData());
        assertNull(data.getFallbackSourceUsed());
        assertNull(data.getFailureCode());
        assertNull(data.getPartnerCode());
        assertNull(data.getPartnerResponseCode());
    }
}
