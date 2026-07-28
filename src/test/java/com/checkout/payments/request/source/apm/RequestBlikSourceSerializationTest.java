package com.checkout.payments.request.source.apm;

import com.checkout.GsonSerializer;
import com.checkout.common.PaymentSourceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestBlikSourceSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeWithRequiredFieldsOnly() {
        final RequestBlikSource source = new RequestBlikSource();

        final String json = serializer.toJson(source);

        assertTrue(json.contains("\"type\":\"blik\""),
                "type should serialize as 'blik'. Got: " + json);
        assertEquals(PaymentSourceType.BLIK, source.getType());
    }

    @Test
    void shouldSerializePartnerAgreementId() {
        final RequestBlikSource source = RequestBlikSource.builder()
                .partnerAgreementId("blik_payid_123456789")
                .build();

        final String json = serializer.toJson(source);

        assertTrue(json.contains("\"partner_agreement_id\":\"blik_payid_123456789\""),
                "partner_agreement_id should serialize in snake_case. Got: " + json);
        assertDoesNotThrow(() -> serializer.toJson(source));
    }

    @Test
    void shouldRoundTripSerialize() {
        final RequestBlikSource original = RequestBlikSource.builder()
                .partnerAgreementId("blik_payid_123456789")
                .build();

        final String json = serializer.toJson(original);
        final RequestBlikSource deserialized = serializer.fromJson(json, RequestBlikSource.class);

        assertNotNull(deserialized);
        assertEquals(PaymentSourceType.BLIK, deserialized.getType());
        assertEquals("blik_payid_123456789", deserialized.getPartnerAgreementId());
    }

    @Test
    void shouldDeserializeSwaggerExample() {
        final String swaggerJson = "{"
                + "\"type\":\"blik\","
                + "\"partner_agreement_id\":\"blik_payid_123456789\""
                + "}";

        final RequestBlikSource source = serializer.fromJson(swaggerJson, RequestBlikSource.class);

        assertNotNull(source);
        assertEquals(PaymentSourceType.BLIK, source.getType());
        assertEquals("blik_payid_123456789", source.getPartnerAgreementId());
    }

    @Test
    void shouldDeserializeCustomerInitiatedSource() {
        final String swaggerJson = "{\"type\":\"blik\"}";

        final RequestBlikSource source = serializer.fromJson(swaggerJson, RequestBlikSource.class);

        assertNotNull(source);
        assertEquals(PaymentSourceType.BLIK, source.getType());
        assertNull(source.getPartnerAgreementId());
    }
}
