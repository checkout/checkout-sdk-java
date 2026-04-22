package com.checkout.payments.request;

import com.checkout.GsonSerializer;
import com.checkout.common.Currency;
import com.checkout.payments.request.source.PayoutRequestCurrencyAccountSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PayoutRequestSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeCurrencyAndAmount() {
        final PayoutRequest request = PayoutRequest.builder()
                .currency(Currency.USD)
                .amount(5000L)
                .source(PayoutRequestCurrencyAccountSource.builder().id("ca_abc123").build())
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"currency\":\"USD\""));
        assertTrue(json.contains("\"amount\":5000"));
    }

    @Test
    void shouldSerializeReference() {
        final PayoutRequest request = PayoutRequest.builder()
                .currency(Currency.GBP)
                .reference("PO-2024-001")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"reference\":\"PO-2024-001\""));
    }

    @Test
    void shouldSerializeProcessingChannelId() {
        final PayoutRequest request = PayoutRequest.builder()
                .currency(Currency.EUR)
                .processingChannelId("pc_abcdefghijklmnopqrstuvwxyz")
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"processing_channel_id\":\"pc_abcdefghijklmnopqrstuvwxyz\""));
    }

    @Test
    void shouldSerializeBillingDescriptor() {
        final PayoutRequest request = PayoutRequest.builder()
                .currency(Currency.USD)
                .billingDescriptor(PayoutBillingDescriptor.builder()
                        .reference("Payout reference")
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"billing_descriptor\""));
        assertTrue(json.contains("\"reference\":\"Payout reference\""));
    }

    @Test
    void shouldSerializeInstruction() {
        final PayoutRequest request = PayoutRequest.builder()
                .currency(Currency.USD)
                .instruction(PaymentInstruction.builder()
                        .purpose("salary")
                        .build())
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"instruction\""));
        assertTrue(json.contains("\"purpose\":\"salary\""));
    }

    @Test
    void shouldDeserializeKeyFields() {
        final String json = "{"
                + "\"amount\":10000,"
                + "\"currency\":\"GBP\","
                + "\"reference\":\"PO-001\","
                + "\"processing_channel_id\":\"pc_test\""
                + "}";

        final PayoutRequest request = serializer.fromJson(json, PayoutRequest.class);

        assertNotNull(request);
        assertEquals(10000L, request.getAmount());
        assertEquals(Currency.GBP, request.getCurrency());
        assertEquals("PO-001", request.getReference());
        assertEquals("pc_test", request.getProcessingChannelId());
    }

    @Test
    void shouldRoundTripSerialize() {
        final PayoutRequest original = PayoutRequest.builder()
                .currency(Currency.EUR)
                .amount(7500L)
                .reference("RT-PAYOUT-001")
                .processingChannelId("pc_abcdefghijklmnopqrstuvwxyz")
                .billingDescriptor(PayoutBillingDescriptor.builder()
                        .reference("RT descriptor")
                        .build())
                .build();

        final String json = serializer.toJson(original);
        final PayoutRequest deserialized = serializer.fromJson(json, PayoutRequest.class);

        assertNotNull(deserialized);
        assertEquals(Currency.EUR, deserialized.getCurrency());
        assertEquals(7500L, deserialized.getAmount());
        assertEquals("RT-PAYOUT-001", deserialized.getReference());
        assertEquals("pc_abcdefghijklmnopqrstuvwxyz", deserialized.getProcessingChannelId());
        assertNotNull(deserialized.getBillingDescriptor());
        assertEquals("RT descriptor", deserialized.getBillingDescriptor().getReference());
    }

    @Test
    void shouldNotSerializeNullOptionalFields() {
        final PayoutRequest request = PayoutRequest.builder()
                .currency(Currency.USD)
                .build();

        final String json = serializer.toJson(request);

        assertFalse(json.contains("\"amount\""));
        assertFalse(json.contains("\"reference\""));
        assertFalse(json.contains("\"billing_descriptor\""));
        assertFalse(json.contains("\"processing_channel_id\""));
    }

    @Test
    void shouldHandleAbsentOptionalFields() {
        final String json = "{\"currency\":\"USD\"}";

        final PayoutRequest request = serializer.fromJson(json, PayoutRequest.class);

        assertNotNull(request);
        assertDoesNotThrow(() -> serializer.toJson(request));
        assertNull(request.getAmount());
        assertNull(request.getReference());
        assertNull(request.getBillingDescriptor());
    }
}
