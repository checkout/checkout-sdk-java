package com.checkout.agenticcommerce;

import com.checkout.GsonSerializer;
import com.checkout.agenticcommerce.request.AllowanceReason;
import com.checkout.agenticcommerce.request.CardFundingType;
import com.checkout.agenticcommerce.request.CardNumberType;
import com.checkout.agenticcommerce.request.DelegatePaymentAllowance;
import com.checkout.agenticcommerce.request.DelegatePaymentBillingAddress;
import com.checkout.agenticcommerce.request.DelegatePaymentMethod;
import com.checkout.agenticcommerce.request.DelegatePaymentRequest;
import com.checkout.agenticcommerce.request.RiskSignal;
import com.checkout.agenticcommerce.response.DelegatePaymentResponse;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for GSON serialization/deserialization of Agentic Commerce request and response objects.
 * Validates snake_case field naming, enum serialization, and Instant handling.
 */
class AgenticCommerceSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeDelegatePaymentRequestWithCorrectFieldNames() {
        final DelegatePaymentRequest request = buildFullRequest();

        assertDoesNotThrow(() -> {
            final String json = serializer.toJson(request);

            assertNotNull(json);
            // Top-level fields
            assertTrue(json.contains("\"payment_method\""), "Should contain payment_method");
            assertTrue(json.contains("\"allowance\""), "Should contain allowance");
            assertTrue(json.contains("\"billing_address\""), "Should contain billing_address");
            assertTrue(json.contains("\"risk_signals\""), "Should contain risk_signals");
            assertTrue(json.contains("\"metadata\""), "Should contain metadata");

            // payment_method fields
            assertTrue(json.contains("\"card_number_type\""), "Should contain card_number_type");
            assertTrue(json.contains("\"fpan\""), "Should serialize CardNumberType.FPAN as fpan");
            assertTrue(json.contains("\"exp_month\""), "Should contain exp_month");
            assertTrue(json.contains("\"exp_year\""), "Should contain exp_year");
            assertTrue(json.contains("\"checks_performed\""), "Should contain checks_performed");
            assertTrue(json.contains("\"display_card_funding_type\""), "Should contain display_card_funding_type");
            assertTrue(json.contains("\"credit\""), "Should serialize CardFundingType.CREDIT as credit");
            assertTrue(json.contains("\"display_brand\""), "Should contain display_brand");
            assertTrue(json.contains("\"display_last4\""), "Should contain display_last4");

            // allowance fields
            assertTrue(json.contains("\"max_amount\""), "Should contain max_amount");
            assertTrue(json.contains("\"merchant_id\""), "Should contain merchant_id");
            assertTrue(json.contains("\"checkout_session_id\""), "Should contain checkout_session_id");
            assertTrue(json.contains("\"expires_at\""), "Should contain expires_at");
            assertTrue(json.contains("\"one_time\""), "Should serialize AllowanceReason.ONE_TIME as one_time");

            // billing_address fields
            assertTrue(json.contains("\"line_one\""), "Should contain line_one");
            assertTrue(json.contains("\"postal_code\""), "Should contain postal_code");
        });
    }

    @Test
    void shouldSerializeNetworkTokenCardNumberType() {
        final DelegatePaymentMethod method = DelegatePaymentMethod.builder()
                .type("card")
                .cardNumberType(CardNumberType.NETWORK_TOKEN)
                .number("4242424242424242")
                .metadata(Collections.emptyMap())
                .build();

        final String json = serializer.toJson(method);

        assertTrue(json.contains("\"network_token\""), "Should serialize NETWORK_TOKEN as network_token");
    }

    @Test
    void shouldSerializeAllCardFundingTypes() {
        for (final CardFundingType type : CardFundingType.values()) {
            final DelegatePaymentMethod method = DelegatePaymentMethod.builder()
                    .type("card")
                    .cardNumberType(CardNumberType.FPAN)
                    .number("4111111111111111")
                    .displayCardFundingType(type)
                    .metadata(Collections.emptyMap())
                    .build();

            final String json = serializer.toJson(method);
            assertNotNull(json);
            assertTrue(json.contains(type.name().toLowerCase()), "Should serialize " + type + " as lowercase");
        }
    }

    @Test
    void shouldDeserializeDelegatePaymentResponse() {
        final String json = "{"
                + "\"id\":\"vt_abc123def456ghi789\","
                + "\"created\":\"2026-03-11T10:30:00Z\","
                + "\"metadata\":{\"psp\":\"checkout.com\"}"
                + "}";

        final DelegatePaymentResponse response = serializer.fromJson(json, DelegatePaymentResponse.class);

        assertNotNull(response);
        assertEquals("vt_abc123def456ghi789", response.getId());
        assertNotNull(response.getCreated());
        assertEquals(Instant.parse("2026-03-11T10:30:00Z"), response.getCreated());
        assertNotNull(response.getMetadata());
        assertEquals("checkout.com", response.getMetadata().get("psp"));
    }

    @Test
    void shouldDeserializeResponseWithoutMetadata() {
        final String json = "{"
                + "\"id\":\"vt_xyz\","
                + "\"created\":\"2026-01-01T00:00:00Z\""
                + "}";

        final DelegatePaymentResponse response = serializer.fromJson(json, DelegatePaymentResponse.class);

        assertNotNull(response);
        assertEquals("vt_xyz", response.getId());
        assertNotNull(response.getCreated());
    }

    @Test
    void shouldSerializeRequestWithoutOptionalFields() {
        final DelegatePaymentRequest minimal = DelegatePaymentRequest.builder()
                .paymentMethod(DelegatePaymentMethod.builder()
                        .type("card")
                        .cardNumberType(CardNumberType.FPAN)
                        .number("4242424242424242")
                        .metadata(Collections.emptyMap())
                        .build())
                .allowance(DelegatePaymentAllowance.builder()
                        .reason(AllowanceReason.ONE_TIME)
                        .maxAmount(1000L)
                        .currency("USD")
                        .merchantId("cli_123")
                        .checkoutSessionId("sess_456")
                        .expiresAt(Instant.parse("2027-01-01T00:00:00Z"))
                        .build())
                .riskSignals(Collections.singletonList(RiskSignal.builder()
                        .type("velocity")
                        .score(0)
                        .action("allow")
                        .build()))
                .build();

        assertDoesNotThrow(() -> {
            final String json = serializer.toJson(minimal);
            assertNotNull(json);
            assertTrue(json.contains("\"payment_method\""));
            assertTrue(json.contains("\"allowance\""));
            assertTrue(json.contains("\"risk_signals\""));
        });
    }

    @Test
    void shouldSerializeRiskSignals() {
        final RiskSignal signal1 = RiskSignal.builder().type("card_testing").score(10).action("blocked").build();
        final RiskSignal signal2 = RiskSignal.builder().type("velocity").score(5).action("allow").build();

        final DelegatePaymentRequest request = DelegatePaymentRequest.builder()
                .riskSignals(Arrays.asList(signal1, signal2))
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"card_testing\""));
        assertTrue(json.contains("\"blocked\""));
        assertTrue(json.contains("\"velocity\""));
        assertTrue(json.contains("\"allow\""));
    }

    @Test
    void shouldRoundTripDelegatePaymentRequest() {
        final DelegatePaymentRequest original = buildFullRequest();

        final String json = serializer.toJson(original);
        final DelegatePaymentRequest deserialized = serializer.fromJson(json, DelegatePaymentRequest.class);

        assertNotNull(deserialized);
        // payment_method
        assertEquals(original.getPaymentMethod().getType(), deserialized.getPaymentMethod().getType());
        assertEquals(original.getPaymentMethod().getCardNumberType(), deserialized.getPaymentMethod().getCardNumberType());
        assertEquals(original.getPaymentMethod().getNumber(), deserialized.getPaymentMethod().getNumber());
        assertEquals(original.getPaymentMethod().getExpMonth(), deserialized.getPaymentMethod().getExpMonth());
        assertEquals(original.getPaymentMethod().getExpYear(), deserialized.getPaymentMethod().getExpYear());
        assertEquals(original.getPaymentMethod().getName(), deserialized.getPaymentMethod().getName());
        assertEquals(original.getPaymentMethod().getCvc(), deserialized.getPaymentMethod().getCvc());
        assertEquals(original.getPaymentMethod().getIin(), deserialized.getPaymentMethod().getIin());
        assertEquals(original.getPaymentMethod().getDisplayCardFundingType(), deserialized.getPaymentMethod().getDisplayCardFundingType());
        assertEquals(original.getPaymentMethod().getDisplayBrand(), deserialized.getPaymentMethod().getDisplayBrand());
        assertEquals(original.getPaymentMethod().getDisplayLast4(), deserialized.getPaymentMethod().getDisplayLast4());
        assertEquals(original.getPaymentMethod().getChecksPerformed(), deserialized.getPaymentMethod().getChecksPerformed());
        assertEquals(original.getPaymentMethod().getMetadata(), deserialized.getPaymentMethod().getMetadata());
        // allowance
        assertEquals(original.getAllowance().getReason(), deserialized.getAllowance().getReason());
        assertEquals(original.getAllowance().getMaxAmount(), deserialized.getAllowance().getMaxAmount());
        assertEquals(original.getAllowance().getCurrency(), deserialized.getAllowance().getCurrency());
        assertEquals(original.getAllowance().getMerchantId(), deserialized.getAllowance().getMerchantId());
        assertEquals(original.getAllowance().getCheckoutSessionId(), deserialized.getAllowance().getCheckoutSessionId());
        // billing_address
        assertEquals(original.getBillingAddress().getName(), deserialized.getBillingAddress().getName());
        assertEquals(original.getBillingAddress().getLineOne(), deserialized.getBillingAddress().getLineOne());
        assertEquals(original.getBillingAddress().getLineTwo(), deserialized.getBillingAddress().getLineTwo());
        assertEquals(original.getBillingAddress().getCity(), deserialized.getBillingAddress().getCity());
        assertEquals(original.getBillingAddress().getState(), deserialized.getBillingAddress().getState());
        assertEquals(original.getBillingAddress().getPostalCode(), deserialized.getBillingAddress().getPostalCode());
        assertEquals(original.getBillingAddress().getCountry(), deserialized.getBillingAddress().getCountry());
        // risk_signals
        assertEquals(original.getRiskSignals().size(), deserialized.getRiskSignals().size());
        assertEquals(original.getRiskSignals().get(0).getType(), deserialized.getRiskSignals().get(0).getType());
        assertEquals(original.getRiskSignals().get(0).getScore(), deserialized.getRiskSignals().get(0).getScore());
        assertEquals(original.getRiskSignals().get(0).getAction(), deserialized.getRiskSignals().get(0).getAction());
        // metadata
        assertEquals(original.getMetadata(), deserialized.getMetadata());
    }

    @Test
    void shouldRoundTripDelegatePaymentResponse() {
        final DelegatePaymentResponse original = new DelegatePaymentResponse();
        // DelegatePaymentResponse has no builder/setters besides Lombok @Data
        final String json = "{\"id\":\"vt_round\",\"created\":\"2026-05-01T12:00:00Z\",\"metadata\":{\"key\":\"val\"}}";
        final DelegatePaymentResponse deserialized = serializer.fromJson(json, DelegatePaymentResponse.class);

        final String reJson = serializer.toJson(deserialized);
        final DelegatePaymentResponse reDeserialized = serializer.fromJson(reJson, DelegatePaymentResponse.class);

        assertEquals(deserialized.getId(), reDeserialized.getId());
        assertEquals(deserialized.getCreated(), reDeserialized.getCreated());
        assertEquals(deserialized.getMetadata(), reDeserialized.getMetadata());
    }

    // --- helper ---

    private static DelegatePaymentRequest buildFullRequest() {
        return DelegatePaymentRequest.builder()
                .paymentMethod(DelegatePaymentMethod.builder()
                        .type("card")
                        .cardNumberType(CardNumberType.FPAN)
                        .number("4242424242424242")
                        .expMonth("11")
                        .expYear("2026")
                        .name("Jane Doe")
                        .cvc("123")
                        .iin("424242")
                        .displayCardFundingType(CardFundingType.CREDIT)
                        .displayBrand("Visa")
                        .displayLast4("4242")
                        .checksPerformed(Arrays.asList("avs", "cvv"))
                        .metadata(Collections.singletonMap("issuing_bank", "test"))
                        .build())
                .allowance(DelegatePaymentAllowance.builder()
                        .reason(AllowanceReason.ONE_TIME)
                        .maxAmount(10000L)
                        .currency("USD")
                        .merchantId("cli_vkuhvk4vjn2edkps7dfsq6emqm")
                        .checkoutSessionId("1PQrsT")
                        .expiresAt(Instant.parse("2026-10-09T07:20:50.52Z"))
                        .build())
                .billingAddress(DelegatePaymentBillingAddress.builder()
                        .name("John Doe")
                        .lineOne("123 Fake St.")
                        .lineTwo("Unit 1")
                        .city("San Francisco")
                        .state("CA")
                        .postalCode("12345")
                        .country("US")
                        .build())
                .riskSignals(Collections.singletonList(RiskSignal.builder()
                        .type("card_testing")
                        .score(10)
                        .action("blocked")
                        .build()))
                .metadata(Collections.singletonMap("campaign", "q4"))
                .build();
    }
}
