package com.checkout.payments;

import com.checkout.GsonSerializer;
import com.checkout.common.CardType;
import com.checkout.common.CountryCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProcessingSettingsSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializePanPreferenceFpan() {
        final ProcessingSettings settings = ProcessingSettings.builder()
                .panPreference(PanProcessedType.FPAN)
                .build();

        final String json = serializer.toJson(settings);

        assertTrue(json.contains("\"pan_preference\":\"fpan\""));
    }

    @Test
    void shouldSerializePanPreferenceDpan() {
        final ProcessingSettings settings = ProcessingSettings.builder()
                .panPreference(PanProcessedType.DPAN)
                .build();

        final String json = serializer.toJson(settings);

        assertTrue(json.contains("\"pan_preference\":\"dpan\""));
    }

    @Test
    void shouldSerializeServiceTypeSameDay() {
        final ProcessingSettings settings = ProcessingSettings.builder()
                .serviceType(AchServiceType.SAME_DAY)
                .build();

        final String json = serializer.toJson(settings);

        assertTrue(json.contains("\"service_type\":\"same_day\""));
    }

    @Test
    void shouldSerializeServiceTypeStandard() {
        final ProcessingSettings settings = ProcessingSettings.builder()
                .serviceType(AchServiceType.STANDARD)
                .build();

        final String json = serializer.toJson(settings);

        assertTrue(json.contains("\"service_type\":\"standard\""));
    }

    @Test
    void shouldSerializeAffiliateFields() {
        final ProcessingSettings settings = ProcessingSettings.builder()
                .affiliateId("AFFILIATE123")
                .affiliateUrl("https://affiliate.example.com")
                .build();

        final String json = serializer.toJson(settings);

        assertTrue(json.contains("\"affiliate_id\":\"AFFILIATE123\""));
        assertTrue(json.contains("\"affiliate_url\":\"https://affiliate.example.com\""));
    }

    @Test
    void shouldSerializeCardTypeCredit() {
        final ProcessingSettings settings = ProcessingSettings.builder()
                .cardType(CardType.CREDIT)
                .build();

        final String json = serializer.toJson(settings);

        assertTrue(json.contains("\"card_type\":\"Credit\""));
    }

    @Test
    void shouldSerializeCardTypeDebit() {
        final ProcessingSettings settings = ProcessingSettings.builder()
                .cardType(CardType.DEBIT)
                .build();

        final String json = serializer.toJson(settings);

        assertTrue(json.contains("\"card_type\":\"Debit\""));
    }

    @Test
    void shouldSerializeForeignRetailerAmount() {
        final ProcessingSettings settings = ProcessingSettings.builder()
                .foreignRetailerAmount(500L)
                .build();

        final String json = serializer.toJson(settings);

        assertTrue(json.contains("\"foreign_retailer_amount\":500"));
    }

    @Test
    void shouldSerializeReconciliationId() {
        final ProcessingSettings settings = ProcessingSettings.builder()
                .reconciliationId("recon_abc123")
                .build();

        final String json = serializer.toJson(settings);

        assertTrue(json.contains("\"reconciliation_id\":\"recon_abc123\""));
    }

    @Test
    void shouldDeserializePanPreference() {
        final String json = "{\"pan_preference\":\"fpan\",\"service_type\":\"standard\"}";

        final ProcessingSettings settings = serializer.fromJson(json, ProcessingSettings.class);

        assertNotNull(settings);
        assertEquals(PanProcessedType.FPAN, settings.getPanPreference());
        assertEquals(AchServiceType.STANDARD, settings.getServiceType());
    }

    @Test
    void shouldDeserializeAffiliateAndCardType() {
        final String json = "{"
                + "\"affiliate_id\":\"AFF001\","
                + "\"affiliate_url\":\"https://aff.example.com\","
                + "\"card_type\":\"Debit\","
                + "\"foreign_retailer_amount\":1000,"
                + "\"reconciliation_id\":\"rec_001\""
                + "}";

        final ProcessingSettings settings = serializer.fromJson(json, ProcessingSettings.class);

        assertNotNull(settings);
        assertEquals("AFF001", settings.getAffiliateId());
        assertEquals("https://aff.example.com", settings.getAffiliateUrl());
        assertEquals(CardType.DEBIT, settings.getCardType());
        assertEquals(1000L, settings.getForeignRetailerAmount());
        assertEquals("rec_001", settings.getReconciliationId());
    }

    @Test
    void shouldRoundTripSerialize() {
        final ProcessingSettings original = ProcessingSettings.builder()
                .panPreference(PanProcessedType.DPAN)
                .serviceType(AchServiceType.SAME_DAY)
                .affiliateId("AFF123")
                .affiliateUrl("https://example.com")
                .cardType(CardType.CREDIT)
                .foreignRetailerAmount(250L)
                .reconciliationId("rec_xyz")
                .hubModelOriginationCountry(CountryCode.GB)
                .build();

        final String json = serializer.toJson(original);
        final ProcessingSettings deserialized = serializer.fromJson(json, ProcessingSettings.class);

        assertNotNull(deserialized);
        assertEquals(PanProcessedType.DPAN, deserialized.getPanPreference());
        assertEquals(AchServiceType.SAME_DAY, deserialized.getServiceType());
        assertEquals("AFF123", deserialized.getAffiliateId());
        assertEquals("https://example.com", deserialized.getAffiliateUrl());
        assertEquals(CardType.CREDIT, deserialized.getCardType());
        assertEquals(250L, deserialized.getForeignRetailerAmount());
        assertEquals("rec_xyz", deserialized.getReconciliationId());
        assertEquals(CountryCode.GB, deserialized.getHubModelOriginationCountry());
    }

    @Test
    void shouldNotSerializeNullOptionalFields() {
        final ProcessingSettings settings = ProcessingSettings.builder()
                .orderId("order_001")
                .build();

        final String json = serializer.toJson(settings);

        assertFalse(json.contains("\"pan_preference\""));
        assertFalse(json.contains("\"service_type\""));
        assertFalse(json.contains("\"affiliate_id\""));
        assertFalse(json.contains("\"card_type\""));
    }

    @Test
    void shouldHandleAbsentOptionalFields() {
        final String json = "{}";

        final ProcessingSettings settings = serializer.fromJson(json, ProcessingSettings.class);

        assertNotNull(settings);
        assertDoesNotThrow(() -> serializer.toJson(settings));
        assertNull(settings.getPanPreference());
        assertNull(settings.getServiceType());
        assertNull(settings.getAffiliateId());
        assertNull(settings.getCardType());
    }
}
