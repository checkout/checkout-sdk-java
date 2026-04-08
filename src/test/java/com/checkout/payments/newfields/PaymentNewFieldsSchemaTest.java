package com.checkout.payments.newfields;

import com.checkout.GsonSerializer;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentRouting;
import com.checkout.payments.PaymentRoutingScheme;
import com.checkout.payments.PaymentSubscription;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.AuthenticationExperience;
import com.checkout.payments.response.PaymentResponseAuthentication;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Schema / serialization tests for new fields added to {@link PaymentRequest}
 * and {@link GetPaymentResponse} in the latest swagger update:
 * <ul>
 *   <li>{@code expire_on} ({@link Instant})</li>
 *   <li>{@code routing} ({@link PaymentRouting})</li>
 *   <li>{@code subscription} ({@link PaymentSubscription})</li>
 *   <li>{@code authentication} ({@link PaymentResponseAuthentication})</li>
 * </ul>
 */
class PaymentNewFieldsSchemaTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ─── PaymentRequest ─────────────────────────────────────────────────────

    @Test
    void shouldSerializeExpireOn() {
        final Instant expiry = Instant.parse("2025-01-31T10:20:30.456Z");

        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.EUR)
                .expireOn(expiry)
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"expire_on\""),
                "Should serialize expireOn as expire_on");
        assertTrue(json.contains("2025-01-31"),
                "Should contain the expiry date value");
    }

    @Test
    void shouldSerializePaymentRouting() {
        final PaymentRouting routing = PaymentRouting.builder()
                .attempts(Arrays.asList(
                        PaymentRouting.RoutingAttempt.builder().scheme(PaymentRoutingScheme.VISA).build(),
                        PaymentRouting.RoutingAttempt.builder().scheme(PaymentRoutingScheme.MASTERCARD).build()
                ))
                .build();

        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.USD)
                .routing(routing)
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"routing\""), "Should contain routing field");
        assertTrue(json.contains("\"attempts\""), "Should contain attempts array");
        assertTrue(json.contains("\"visa\""), "Should contain visa scheme");
        assertTrue(json.contains("\"mastercard\""), "Should contain mastercard scheme");
    }

    @Test
    void shouldSerializePaymentRoutingWithSingleAttempt() {
        final PaymentRouting routing = PaymentRouting.builder()
                .attempts(Collections.singletonList(
                        PaymentRouting.RoutingAttempt.builder().scheme(PaymentRoutingScheme.AMEX).build()
                ))
                .build();

        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.GBP)
                .routing(routing)
                .build();

        final String json = serializer.toJson(request);

        assertTrue(json.contains("\"amex\""));
    }

    @Test
    void shouldSerializePaymentSubscription() {
        final PaymentSubscription subscription = PaymentSubscription.builder()
                .id("sub_ref_12345678901234567890")
                .build();

        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.USD)
                .subscription(subscription)
                .build();

        final String json = serializer.toJson(request);

        assertNotNull(json);
        assertTrue(json.contains("\"subscription\""), "Should contain subscription field");
        assertTrue(json.contains("\"sub_ref_12345678901234567890\""), "Should contain subscription id");
    }

    @Test
    void shouldSerializeAllThreeNewFieldsTogether() {
        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.USD)
                .amount(1000L)
                .expireOn(Instant.parse("2025-06-30T23:59:59Z"))
                .routing(PaymentRouting.builder()
                        .attempts(Collections.singletonList(
                                PaymentRouting.RoutingAttempt.builder().scheme(PaymentRoutingScheme.VISA).build()))
                        .build())
                .subscription(PaymentSubscription.builder().id("sub_abc123").build())
                .build();

        assertDoesNotThrow(() -> {
            final String json = serializer.toJson(request);
            assertNotNull(json);
            assertTrue(json.contains("\"expire_on\""));
            assertTrue(json.contains("\"routing\""));
            assertTrue(json.contains("\"subscription\""));
        });
    }

    @Test
    void shouldNotSerializeNullNewFields() {
        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.USD)
                .amount(100L)
                .build();

        final String json = serializer.toJson(request);

        // Gson skips null fields by default
        assertFalse(json.contains("\"expire_on\""),
                "Should not include expire_on when null");
        assertFalse(json.contains("\"routing\""),
                "Should not include routing when null");
        assertFalse(json.contains("\"subscription\""),
                "Should not include subscription when null");
    }

    // ─── GetPaymentResponse ──────────────────────────────────────────────────

    @Test
    void shouldDeserializePaymentResponseAuthenticationWith3ds() {
        final String json = "{"
                + "\"id\":\"pay_abc123\","
                + "\"status\":\"Authorized\","
                + "\"authentication\":{\"experience\":\"3ds\"}"
                + "}";

        final GetPaymentResponse response = serializer.fromJson(json, GetPaymentResponse.class);

        assertNotNull(response);
        assertNotNull(response.getAuthentication(), "authentication field should not be null");
        assertEquals(AuthenticationExperience.THREE_DS, response.getAuthentication().getExperience());
    }

    @Test
    void shouldDeserializePaymentResponseAuthenticationWithGoogleSpa() {
        final String json = "{"
                + "\"id\":\"pay_xyz789\","
                + "\"status\":\"Authorized\","
                + "\"authentication\":{\"experience\":\"google_spa\"}"
                + "}";

        final GetPaymentResponse response = serializer.fromJson(json, GetPaymentResponse.class);

        assertNotNull(response);
        assertNotNull(response.getAuthentication());
        assertEquals(AuthenticationExperience.GOOGLE_SPA, response.getAuthentication().getExperience());
    }

    @Test
    void shouldDeserializeGetPaymentResponseWithoutAuthenticationField() {
        final String json = "{"
                + "\"id\":\"pay_no_auth\","
                + "\"status\":\"Authorized\","
                + "\"amount\":1000,"
                + "\"currency\":\"USD\""
                + "}";

        final GetPaymentResponse response = serializer.fromJson(json, GetPaymentResponse.class);

        assertNotNull(response);
        assertNull(response.getAuthentication(),
                "authentication should be null when absent from response");
    }

    @Test
    void shouldDeserializeGetPaymentResponseWithAllNewFields() {
        final String json = "{"
                + "\"id\":\"pay_full\","
                + "\"status\":\"Authorized\","
                + "\"amount\":5000,"
                + "\"currency\":\"EUR\","
                + "\"authentication\":{\"experience\":\"3ds\"},"
                + "\"scheme_id\":\"scheme_abc\","
                + "\"cko_network_token_available\":true"
                + "}";

        final GetPaymentResponse response = serializer.fromJson(json, GetPaymentResponse.class);

        assertNotNull(response);
        assertEquals("pay_full", response.getId());
        assertNotNull(response.getAuthentication());
        assertEquals(AuthenticationExperience.THREE_DS, response.getAuthentication().getExperience());
    }

    @Test
    void shouldRoundTripPaymentRouting() {
        final PaymentRouting original = PaymentRouting.builder()
                .attempts(Arrays.asList(
                        PaymentRouting.RoutingAttempt.builder().scheme(PaymentRoutingScheme.VISA).build(),
                        PaymentRouting.RoutingAttempt.builder().scheme(PaymentRoutingScheme.MASTERCARD).build()
                ))
                .build();

        final String json = serializer.toJson(original);
        final PaymentRouting deserialized = serializer.fromJson(json, PaymentRouting.class);

        assertNotNull(deserialized);
        assertEquals(2, deserialized.getAttempts().size());
        assertEquals(PaymentRoutingScheme.VISA, deserialized.getAttempts().get(0).getScheme());
        assertEquals(PaymentRoutingScheme.MASTERCARD, deserialized.getAttempts().get(1).getScheme());
    }

    @Test
    void shouldRoundTripPaymentSubscription() {
        final PaymentSubscription original = PaymentSubscription.builder()
                .id("sub_round_trip_test")
                .build();

        final String json = serializer.toJson(original);
        final PaymentSubscription deserialized = serializer.fromJson(json, PaymentSubscription.class);

        assertNotNull(deserialized);
        assertEquals("sub_round_trip_test", deserialized.getId());
    }

    @Test
    void shouldRoundTripPaymentResponseAuthentication() {
        final String json = "{\"experience\":\"google_spa\"}";
        final PaymentResponseAuthentication auth = serializer.fromJson(json, PaymentResponseAuthentication.class);

        final String reJson = serializer.toJson(auth);
        final PaymentResponseAuthentication reAuth = serializer.fromJson(reJson, PaymentResponseAuthentication.class);

        assertEquals(AuthenticationExperience.GOOGLE_SPA, reAuth.getExperience());
    }
}
