package com.checkout.payments.links;

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

class PaymentLinkRequestSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeRequiredFields() {
        final PaymentLinkRequest request = PaymentLinkRequest.builder()
                .amount(1500L)
                .currency(Currency.GBP)
                .billing(BillingInformation.builder()
                        .address(Address.builder()
                                .addressLine1("1 High Street")
                                .city("London")
                                .country(CountryCode.GB)
                                .build())
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"amount\":1500"));
        assertTrue(json.contains("\"currency\":\"GBP\""));
        assertTrue(json.contains("\"billing\""));
    }

    @Test
    void shouldSerializeExpiresIn() {
        final PaymentLinkRequest request = PaymentLinkRequest.builder()
                .amount(1000L)
                .currency(Currency.USD)
                .expiresIn(86400)
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"expires_in\":86400"));
    }

    @Test
    void shouldSerializeReturnUrl() {
        final PaymentLinkRequest request = PaymentLinkRequest.builder()
                .amount(1000L)
                .currency(Currency.USD)
                .returnUrl("https://example.com/return")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"return_url\":\"https://example.com/return\""));
    }

    @Test
    void shouldSerializeProcessingChannelId() {
        final PaymentLinkRequest request = PaymentLinkRequest.builder()
                .amount(1000L)
                .currency(Currency.EUR)
                .processingChannelId("pc_abcdefghijklmnopqrstuvwxyz")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"processing_channel_id\":\"pc_abcdefghijklmnopqrstuvwxyz\""));
    }

    @Test
    void shouldSerializePaymentType() {
        final PaymentLinkRequest request = PaymentLinkRequest.builder()
                .amount(500L)
                .currency(Currency.USD)
                .paymentType(PaymentType.RECURRING)
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"payment_type\":\"Recurring\""));
    }

    @Test
    void shouldSerializeCaptureFields() {
        final Instant captureOn = Instant.parse("2026-07-01T10:00:00Z");
        final PaymentLinkRequest request = PaymentLinkRequest.builder()
                .amount(1000L)
                .currency(Currency.USD)
                .capture(true)
                .captureOn(captureOn)
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"capture\":true"));
        assertTrue(json.contains("\"capture_on\""));
    }

    @Test
    void shouldSerializeReferenceAndDescription() {
        final PaymentLinkRequest request = PaymentLinkRequest.builder()
                .amount(750L)
                .currency(Currency.USD)
                .reference("ORD-2024-001")
                .description("Order payment")
                .displayName("My Store")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"reference\":\"ORD-2024-001\""));
        assertTrue(json.contains("\"description\":\"Order payment\""));
        assertTrue(json.contains("\"display_name\":\"My Store\""));
    }

    @Test
    void shouldDeserializeRequiredFields() {
        final String json = "{"
                + "\"amount\":2000,"
                + "\"currency\":\"EUR\","
                + "\"expires_in\":3600,"
                + "\"return_url\":\"https://example.com/return\","
                + "\"processing_channel_id\":\"pc_test\""
                + "}";

        final PaymentLinkRequest request = serializer.fromJson(json, PaymentLinkRequest.class);

        assertNotNull(request);
        assertEquals(2000L, request.getAmount());
        assertEquals(Currency.EUR, request.getCurrency());
        assertEquals(3600, request.getExpiresIn());
        assertEquals("https://example.com/return", request.getReturnUrl());
        assertEquals("pc_test", request.getProcessingChannelId());
    }

    @Test
    void shouldDeserializePaymentType() {
        final String json = "{\"payment_type\":\"Installment\",\"amount\":500,\"currency\":\"USD\"}";

        final PaymentLinkRequest request = serializer.fromJson(json, PaymentLinkRequest.class);

        assertNotNull(request);
        assertEquals(PaymentType.INSTALLMENT, request.getPaymentType());
    }

    @Test
    void shouldRoundTripSerialize() {
        final PaymentLinkRequest original = PaymentLinkRequest.builder()
                .amount(3000L)
                .currency(Currency.GBP)
                .expiresIn(7200)
                .returnUrl("https://example.com/return")
                .processingChannelId("pc_abcdefghijklmnopqrstuvwxyz")
                .reference("REF-RT-001")
                .description("Round-trip test")
                .paymentType(PaymentType.REGULAR)
                .capture(true)
                .build();

        final String json = serializer.toJson(original);
        final PaymentLinkRequest deserialized = serializer.fromJson(json, PaymentLinkRequest.class);

        assertNotNull(deserialized);
        assertEquals(3000L, deserialized.getAmount());
        assertEquals(Currency.GBP, deserialized.getCurrency());
        assertEquals(7200, deserialized.getExpiresIn());
        assertEquals("https://example.com/return", deserialized.getReturnUrl());
        assertEquals("pc_abcdefghijklmnopqrstuvwxyz", deserialized.getProcessingChannelId());
        assertEquals("REF-RT-001", deserialized.getReference());
        assertEquals("Round-trip test", deserialized.getDescription());
        assertTrue(deserialized.isCapture());
    }

    @Test
    void shouldNotSerializeNullOptionalFields() {
        final PaymentLinkRequest request = PaymentLinkRequest.builder()
                .amount(1000L)
                .currency(Currency.USD)
                .build();

        final String json = serializer.toJson(request);

        assertFalse(json.contains("\"expires_in\""));
        assertFalse(json.contains("\"return_url\""));
        assertFalse(json.contains("\"description\""));
        assertFalse(json.contains("\"processing_channel_id\""));
    }

    @Test
    void shouldHandleAbsentOptionalFields() {
        final String json = "{\"amount\":500,\"currency\":\"USD\"}";

        final PaymentLinkRequest request = serializer.fromJson(json, PaymentLinkRequest.class);

        assertNotNull(request);
        assertDoesNotThrow(() -> serializer.toJson(request));
        assertNull(request.getExpiresIn());
        assertNull(request.getReturnUrl());
        assertNull(request.getDescription());
    }
}
