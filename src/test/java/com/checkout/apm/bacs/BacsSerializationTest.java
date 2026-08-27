package com.checkout.apm.bacs;

import com.checkout.GsonSerializer;
import com.checkout.common.Currency;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Schema validation tests for the Bacs Direct Debit pre-notification endpoint.
 */
class BacsSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeAllPropertiesToSnakeCaseKeys() {
        final String json = serializer.toJson(fullyPopulatedRequest());

        assertTrue(json.contains("\"source_id\":\"src_wmlfc3zyhqzehihu7giusaaawu\""));
        assertTrue(json.contains("\"notification_type\":\"advance_notice\""));
        assertTrue(json.contains("\"collection_date\":\"2026-07-15\""));
        assertTrue(json.contains("\"amount\":4999"));
        assertTrue(json.contains("\"currency\":\"GBP\""));
        assertTrue(json.contains("\"reference\":\"INV-12345\""));
        assertTrue(json.contains("\"customer_email\":\"customer@example.com\""));
        assertTrue(json.contains("\"billing_descriptor\":\"CHECKOUT\""));
        assertTrue(json.contains("\"support_email\":\"support@test.com\""));
        assertTrue(json.contains("\"support_phone\":\"+447700900123\""));
    }

    @Test
    void shouldRoundTripAllProperties() {
        final BacsNotificationRequest original = fullyPopulatedRequest();

        final BacsNotificationRequest result =
                serializer.fromJson(serializer.toJson(original), BacsNotificationRequest.class);

        assertEquals(original.getSourceId(), result.getSourceId());
        assertEquals(original.getNotificationType(), result.getNotificationType());
        assertEquals(original.getCollectionDate(), result.getCollectionDate());
        assertEquals(original.getAmount(), result.getAmount());
        assertEquals(original.getCurrency(), result.getCurrency());
        assertEquals(original.getReference(), result.getReference());
        assertEquals(original.getCustomerEmail(), result.getCustomerEmail());
        assertEquals(original.getBillingDescriptor(), result.getBillingDescriptor());
        assertEquals(original.getSupportEmail(), result.getSupportEmail());
        assertEquals(original.getSupportPhone(), result.getSupportPhone());
    }

    @Test
    void shouldDeserializeSwaggerExample() {
        final String json = "{"
                + "\"source_id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"notification_type\":\"advance_notice\","
                + "\"collection_date\":\"2026-07-15\","
                + "\"amount\":4999,"
                + "\"currency\":\"GBP\","
                + "\"reference\":\"INV-12345\","
                + "\"customer_email\":\"customer@example.com\","
                + "\"billing_descriptor\":\"CHECKOUT\","
                + "\"support_email\":\"support@test.com\","
                + "\"support_phone\":\"+447700900123\""
                + "}";

        final BacsNotificationRequest request = serializer.fromJson(json, BacsNotificationRequest.class);

        assertNotNull(request);
        assertEquals("src_wmlfc3zyhqzehihu7giusaaawu", request.getSourceId());
        assertEquals(BacsNotificationType.ADVANCE_NOTICE, request.getNotificationType());
        assertEquals(LocalDate.of(2026, 7, 15), request.getCollectionDate());
        assertEquals(4999L, request.getAmount());
        assertEquals(Currency.GBP, request.getCurrency());
        assertEquals("INV-12345", request.getReference());
        assertEquals("customer@example.com", request.getCustomerEmail());
        assertEquals("CHECKOUT", request.getBillingDescriptor());
        assertEquals("support@test.com", request.getSupportEmail());
        assertEquals("+447700900123", request.getSupportPhone());
    }

    @Test
    void shouldOmitOptionalPropertiesWhenNotSet() {
        final BacsNotificationRequest request = BacsNotificationRequest.builder()
                .sourceId("src_wmlfc3zyhqzehihu7giusaaawu")
                .notificationType(BacsNotificationType.ADVANCE_NOTICE)
                .collectionDate(LocalDate.of(2026, 7, 15))
                .amount(4999L)
                .currency(Currency.GBP)
                .customerEmail("customer@example.com")
                .billingDescriptor("CHECKOUT")
                .supportEmail("support@test.com")
                .build();

        final String json = serializer.toJson(request);

        assertFalse(json.contains("reference"));
        assertFalse(json.contains("support_phone"));
        assertTrue(json.contains("\"source_id\""));
    }

    @Test
    void shouldHandleAbsentOptionalProperties() {
        final String json = "{"
                + "\"source_id\":\"src_wmlfc3zyhqzehihu7giusaaawu\","
                + "\"notification_type\":\"advance_notice\","
                + "\"collection_date\":\"2026-07-15\","
                + "\"amount\":1,"
                + "\"currency\":\"GBP\","
                + "\"customer_email\":\"customer@example.com\","
                + "\"billing_descriptor\":\"CHECKOUT\","
                + "\"support_email\":\"support@test.com\""
                + "}";

        final BacsNotificationRequest request = serializer.fromJson(json, BacsNotificationRequest.class);

        assertNull(request.getReference());
        assertNull(request.getSupportPhone());
        assertEquals(1L, request.getAmount());
    }

    @Test
    void shouldDeserializeNotificationResponse() {
        final BacsNotificationResponse response = serializer.fromJson(
                "{\"event_id\":\"evt_lzr4csdtddwetactr6phd3kea4\"}", BacsNotificationResponse.class);

        assertNotNull(response);
        assertEquals("evt_lzr4csdtddwetactr6phd3kea4", response.getEventId());
    }

    @Test
    void shouldSerializeNotificationTypeBothDirections() {
        assertEquals(1, BacsNotificationType.values().length);
        assertEquals("\"advance_notice\"", serializer.toJson(BacsNotificationType.ADVANCE_NOTICE));
        assertEquals(BacsNotificationType.ADVANCE_NOTICE,
                serializer.fromJson("\"advance_notice\"", BacsNotificationType.class));
    }

    private BacsNotificationRequest fullyPopulatedRequest() {
        return BacsNotificationRequest.builder()
                .sourceId("src_wmlfc3zyhqzehihu7giusaaawu")
                .notificationType(BacsNotificationType.ADVANCE_NOTICE)
                .collectionDate(LocalDate.of(2026, 7, 15))
                .amount(4999L)
                .currency(Currency.GBP)
                .reference("INV-12345")
                .customerEmail("customer@example.com")
                .billingDescriptor("CHECKOUT")
                .supportEmail("support@test.com")
                .supportPhone("+447700900123")
                .build();
    }
}
