package com.checkout.payments.request;

import com.checkout.GsonSerializer;
import com.checkout.common.Currency;
import com.checkout.payments.AuthorizationType;
import com.checkout.payments.PaymentType;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentRequestSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeRequiredCurrencyField() {
        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.USD)
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"currency\":\"USD\""));
    }

    @Test
    void shouldSerializePaymentContextId() {
        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.USD)
                .paymentContextId("ctx_abc123")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"payment_context_id\":\"ctx_abc123\""));
    }

    @Test
    void shouldSerializeMerchantInitiated() {
        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.USD)
                .merchantInitiated(true)
                .paymentType(PaymentType.RECURRING)
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"merchant_initiated\":true"));
        assertTrue(json.contains("\"payment_type\":\"Recurring\""));
    }

    @Test
    void shouldSerializePaymentTypeEnum() {
        for (final PaymentType paymentType : PaymentType.values()) {
            final PaymentRequest request = PaymentRequest.builder()
                    .currency(Currency.USD)
                    .paymentType(paymentType)
                    .build();

            assertDoesNotThrow(() -> serializer.toJson(request));
        }
    }

    @Test
    void shouldSerializeAuthorizationType() {
        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.USD)
                .authorizationType(AuthorizationType.ESTIMATED)
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"authorization_type\":\"Estimated\""));
    }

    @Test
    void shouldSerializeCaptureFields() {
        final Instant captureOn = Instant.parse("2026-08-01T14:00:00Z");
        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.USD)
                .capture(true)
                .captureOn(captureOn)
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"capture\":true"));
        assertTrue(json.contains("\"capture_on\""));
    }

    @Test
    void shouldSerializeProcessingChannelId() {
        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.GBP)
                .processingChannelId("pc_abcdefghijklmnopqrstuvwxyz")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"processing_channel_id\":\"pc_abcdefghijklmnopqrstuvwxyz\""));
    }

    @Test
    void shouldSerializePreviousPaymentId() {
        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.USD)
                .previousPaymentId("pay_cr4hxwizzp6k7biycuk2ibltnm")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"previous_payment_id\":\"pay_cr4hxwizzp6k7biycuk2ibltnm\""));
    }

    @Test
    void shouldSerializeSuccessAndFailureUrls() {
        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.USD)
                .successUrl("https://example.com/success")
                .failureUrl("https://example.com/failure")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"success_url\":\"https://example.com/success\""));
        assertTrue(json.contains("\"failure_url\":\"https://example.com/failure\""));
    }

    @Test
    void shouldSerializeReferenceAndDescription() {
        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.EUR)
                .reference("ORD-001")
                .description("Test order")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"reference\":\"ORD-001\""));
        assertTrue(json.contains("\"description\":\"Test order\""));
    }

    @Test
    void shouldSerializeAmount() {
        final PaymentRequest request = PaymentRequest.builder()
                .amount(4999L)
                .currency(Currency.USD)
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"amount\":4999"));
    }

    @Test
    void shouldDeserializeKeyFields() {
        final String json = "{"
                + "\"payment_context_id\":\"ctx_001\","
                + "\"amount\":1500,"
                + "\"currency\":\"GBP\","
                + "\"payment_type\":\"Unscheduled\","
                + "\"merchant_initiated\":true,"
                + "\"reference\":\"REF-001\","
                + "\"description\":\"Test\","
                + "\"processing_channel_id\":\"pc_test\","
                + "\"previous_payment_id\":\"pay_abc\","
                + "\"success_url\":\"https://example.com/success\","
                + "\"failure_url\":\"https://example.com/failure\","
                + "\"capture\":false"
                + "}";

        final PaymentRequest request = serializer.fromJson(json, PaymentRequest.class);

        assertNotNull(request);
        assertEquals("ctx_001", request.getPaymentContextId());
        assertEquals(1500L, request.getAmount());
        assertEquals(Currency.GBP, request.getCurrency());
        assertEquals(PaymentType.UNSCHEDULED, request.getPaymentType());
        assertEquals(Boolean.TRUE, request.getMerchantInitiated());
        assertEquals("REF-001", request.getReference());
        assertEquals("Test", request.getDescription());
        assertEquals("pc_test", request.getProcessingChannelId());
        assertEquals("pay_abc", request.getPreviousPaymentId());
        assertEquals("https://example.com/success", request.getSuccessUrl());
        assertEquals("https://example.com/failure", request.getFailureUrl());
        assertEquals(Boolean.FALSE, request.getCapture());
    }

    @Test
    void shouldRoundTripSerialize() {
        final PaymentRequest original = PaymentRequest.builder()
                .amount(2000L)
                .currency(Currency.USD)
                .paymentType(PaymentType.MOTO)
                .reference("RT-001")
                .description("Round-trip")
                .processingChannelId("pc_abcdefghijklmnopqrstuvwxyz")
                .previousPaymentId("pay_prev")
                .successUrl("https://example.com/success")
                .failureUrl("https://example.com/failure")
                .capture(true)
                .merchantInitiated(false)
                .build();

        final String json = serializer.toJson(original);
        final PaymentRequest deserialized = serializer.fromJson(json, PaymentRequest.class);

        assertNotNull(deserialized);
        assertEquals(2000L, deserialized.getAmount());
        assertEquals(Currency.USD, deserialized.getCurrency());
        assertEquals(PaymentType.MOTO, deserialized.getPaymentType());
        assertEquals("RT-001", deserialized.getReference());
        assertEquals("pc_abcdefghijklmnopqrstuvwxyz", deserialized.getProcessingChannelId());
        assertEquals("pay_prev", deserialized.getPreviousPaymentId());
        assertEquals("https://example.com/success", deserialized.getSuccessUrl());
        assertEquals("https://example.com/failure", deserialized.getFailureUrl());
        assertEquals(Boolean.TRUE, deserialized.getCapture());
    }

    @Test
    void shouldNotSerializeNullOptionalFields() {
        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.USD)
                .build();

        final String json = serializer.toJson(request);

        assertFalse(json.contains("\"payment_context_id\""));
        assertFalse(json.contains("\"previous_payment_id\""));
        assertFalse(json.contains("\"success_url\""));
        assertFalse(json.contains("\"failure_url\""));
        assertFalse(json.contains("\"merchant_initiated\""));
    }

    @Test
    void shouldHandleAbsentOptionalFields() {
        final String json = "{\"currency\":\"USD\"}";

        final PaymentRequest request = serializer.fromJson(json, PaymentRequest.class);

        assertNotNull(request);
        assertDoesNotThrow(() -> serializer.toJson(request));
        assertNull(request.getPaymentContextId());
        assertNull(request.getAmount());
        assertNull(request.getMerchantInitiated());
        assertNull(request.getPreviousPaymentId());
    }
}
