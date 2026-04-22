package com.checkout.payments.hosted;

import com.checkout.GsonSerializer;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.PaymentType;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HostedPaymentRequestSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeRequiredFields() {
        final HostedPaymentRequest request = HostedPaymentRequest.builder()
                .currency(Currency.USD)
                .billing(BillingInformation.builder()
                        .address(Address.builder()
                                .addressLine1("123 Main St")
                                .city("New York")
                                .country(CountryCode.US)
                                .build())
                        .build())
                .successUrl("https://example.com/success")
                .cancelUrl("https://example.com/cancel")
                .failureUrl("https://example.com/failure")
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"currency\":\"USD\""));
        assertTrue(json.contains("\"billing\""));
        assertTrue(json.contains("\"success_url\":\"https://example.com/success\""));
        assertTrue(json.contains("\"cancel_url\":\"https://example.com/cancel\""));
        assertTrue(json.contains("\"failure_url\":\"https://example.com/failure\""));
    }

    @Test
    void shouldSerializeAmount() {
        final HostedPaymentRequest request = HostedPaymentRequest.builder()
                .currency(Currency.GBP)
                .amount(1000L)
                .successUrl("https://example.com/success")
                .cancelUrl("https://example.com/cancel")
                .failureUrl("https://example.com/failure")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"amount\":1000"));
        assertTrue(json.contains("\"currency\":\"GBP\""));
    }

    @Test
    void shouldSerializePaymentType() {
        final HostedPaymentRequest request = HostedPaymentRequest.builder()
                .currency(Currency.EUR)
                .paymentType(PaymentType.RECURRING)
                .successUrl("https://example.com/success")
                .cancelUrl("https://example.com/cancel")
                .failureUrl("https://example.com/failure")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"payment_type\":\"Recurring\""));
    }

    @Test
    void shouldSerializeProcessingChannelId() {
        final HostedPaymentRequest request = HostedPaymentRequest.builder()
                .currency(Currency.USD)
                .processingChannelId("pc_abcdefghijklmnopqrstuvwxyz")
                .successUrl("https://example.com/success")
                .cancelUrl("https://example.com/cancel")
                .failureUrl("https://example.com/failure")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"processing_channel_id\":\"pc_abcdefghijklmnopqrstuvwxyz\""));
    }

    @Test
    void shouldSerializeCaptureFields() {
        final Instant captureOn = Instant.parse("2026-06-01T12:00:00Z");
        final HostedPaymentRequest request = HostedPaymentRequest.builder()
                .currency(Currency.USD)
                .capture(true)
                .captureOn(captureOn)
                .successUrl("https://example.com/success")
                .cancelUrl("https://example.com/cancel")
                .failureUrl("https://example.com/failure")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"capture\":true"));
        assertTrue(json.contains("\"capture_on\""));
    }

    @Test
    void shouldSerializeReferenceAndDescription() {
        final HostedPaymentRequest request = HostedPaymentRequest.builder()
                .currency(Currency.USD)
                .reference("ORD-12345")
                .description("Test payment")
                .displayName("My Shop")
                .successUrl("https://example.com/success")
                .cancelUrl("https://example.com/cancel")
                .failureUrl("https://example.com/failure")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"reference\":\"ORD-12345\""));
        assertTrue(json.contains("\"description\":\"Test payment\""));
        assertTrue(json.contains("\"display_name\":\"My Shop\""));
    }

    @Test
    void shouldDeserializeRequiredFields() {
        final String json = "{"
                + "\"currency\":\"USD\","
                + "\"success_url\":\"https://example.com/success\","
                + "\"cancel_url\":\"https://example.com/cancel\","
                + "\"failure_url\":\"https://example.com/failure\","
                + "\"amount\":500"
                + "}";

        final HostedPaymentRequest request = serializer.fromJson(json, HostedPaymentRequest.class);

        assertNotNull(request);
        assertEquals(Currency.USD, request.getCurrency());
        assertEquals("https://example.com/success", request.getSuccessUrl());
        assertEquals("https://example.com/cancel", request.getCancelUrl());
        assertEquals("https://example.com/failure", request.getFailureUrl());
        assertEquals(500L, request.getAmount());
    }

    @Test
    void shouldDeserializePaymentType() {
        final String json = "{\"payment_type\":\"Recurring\",\"currency\":\"GBP\"}";

        final HostedPaymentRequest request = serializer.fromJson(json, HostedPaymentRequest.class);

        assertNotNull(request);
        assertEquals(PaymentType.RECURRING, request.getPaymentType());
    }

    @Test
    void shouldRoundTripSerialize() {
        final HostedPaymentRequest original = HostedPaymentRequest.builder()
                .currency(Currency.EUR)
                .amount(2500L)
                .paymentType(PaymentType.REGULAR)
                .reference("REF-001")
                .description("Round trip test")
                .processingChannelId("pc_abcdefghijklmnopqrstuvwxyz")
                .successUrl("https://example.com/success")
                .cancelUrl("https://example.com/cancel")
                .failureUrl("https://example.com/failure")
                .build();

        final String json = serializer.toJson(original);
        final HostedPaymentRequest deserialized = serializer.fromJson(json, HostedPaymentRequest.class);

        assertNotNull(deserialized);
        assertEquals(Currency.EUR, deserialized.getCurrency());
        assertEquals(2500L, deserialized.getAmount());
        assertEquals("REF-001", deserialized.getReference());
        assertEquals("Round trip test", deserialized.getDescription());
        assertEquals("pc_abcdefghijklmnopqrstuvwxyz", deserialized.getProcessingChannelId());
        assertEquals("https://example.com/success", deserialized.getSuccessUrl());
        assertEquals("https://example.com/cancel", deserialized.getCancelUrl());
        assertEquals("https://example.com/failure", deserialized.getFailureUrl());
    }

    @Test
    void shouldNotSerializeNullOptionalFields() {
        final HostedPaymentRequest request = HostedPaymentRequest.builder()
                .currency(Currency.USD)
                .successUrl("https://example.com/success")
                .cancelUrl("https://example.com/cancel")
                .failureUrl("https://example.com/failure")
                .build();

        final String json = serializer.toJson(request);

        assertFalse(json.contains("\"amount\""));
        assertFalse(json.contains("\"description\""));
        assertFalse(json.contains("\"processing_channel_id\""));
        assertFalse(json.contains("\"capture_on\""));
    }

    @Test
    void shouldHandleAbsentOptionalFields() {
        final String json = "{\"currency\":\"USD\"}";

        final HostedPaymentRequest request = serializer.fromJson(json, HostedPaymentRequest.class);

        assertNotNull(request);
        assertDoesNotThrow(() -> serializer.toJson(request));
        assertNull(request.getAmount());
        assertNull(request.getReference());
        assertNull(request.getSuccessUrl());
        assertNull(request.getCancelUrl());
        assertNull(request.getFailureUrl());
    }
}
