package com.checkout.payments.newfields;

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
 * Unit tests verifying that new fields on {@link PaymentRequest} and
 * {@link GetPaymentResponse} are correctly modelled (builder, getters, equals).
 */
class PaymentNewFieldsUnitTest {

    // ─── PaymentRouting ──────────────────────────────────────────────────────

    @Test
    void shouldBuildPaymentRoutingWithMultipleAttempts() {
        final PaymentRouting routing = PaymentRouting.builder()
                .attempts(Arrays.asList(
                        PaymentRouting.RoutingAttempt.builder().scheme(PaymentRoutingScheme.VISA).build(),
                        PaymentRouting.RoutingAttempt.builder().scheme(PaymentRoutingScheme.MASTERCARD).build(),
                        PaymentRouting.RoutingAttempt.builder().scheme(PaymentRoutingScheme.AMEX).build()
                ))
                .build();

        assertNotNull(routing.getAttempts());
        assertEquals(3, routing.getAttempts().size());
        assertEquals(PaymentRoutingScheme.VISA, routing.getAttempts().get(0).getScheme());
        assertEquals(PaymentRoutingScheme.MASTERCARD, routing.getAttempts().get(1).getScheme());
        assertEquals(PaymentRoutingScheme.AMEX, routing.getAttempts().get(2).getScheme());
    }

    @Test
    void shouldBuildPaymentRoutingAttempt() {
        final PaymentRouting.RoutingAttempt attempt =
                PaymentRouting.RoutingAttempt.builder().scheme(PaymentRoutingScheme.JCB).build();

        assertEquals(PaymentRoutingScheme.JCB, attempt.getScheme());
    }

    @Test
    void shouldAllowEmptyRoutingAttemptsList() {
        final PaymentRouting routing = PaymentRouting.builder()
                .attempts(Collections.emptyList())
                .build();

        assertNotNull(routing);
        assertTrue(routing.getAttempts().isEmpty());
    }

    // ─── PaymentSubscription ─────────────────────────────────────────────────

    @Test
    void shouldBuildPaymentSubscription() {
        final PaymentSubscription subscription = PaymentSubscription.builder()
                .id("sub_monthly_plan_abc")
                .build();

        assertNotNull(subscription);
        assertEquals("sub_monthly_plan_abc", subscription.getId());
    }

    @Test
    void shouldAllowNullSubscriptionId() {
        final PaymentSubscription subscription = PaymentSubscription.builder().build();

        assertNull(subscription.getId());
    }

    // ─── PaymentRequest new fields ───────────────────────────────────────────

    @Test
    void shouldSetExpireOnInPaymentRequest() {
        final Instant expiry = Instant.parse("2025-12-31T23:59:59Z");

        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.USD)
                .expireOn(expiry)
                .build();

        assertEquals(expiry, request.getExpireOn());
    }

    @Test
    void shouldSetRoutingInPaymentRequest() {
        final PaymentRouting routing = PaymentRouting.builder()
                .attempts(Collections.singletonList(
                        PaymentRouting.RoutingAttempt.builder().scheme(PaymentRoutingScheme.VISA).build()))
                .build();

        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.USD)
                .routing(routing)
                .build();

        assertNotNull(request.getRouting());
        assertEquals(1, request.getRouting().getAttempts().size());
        assertEquals(PaymentRoutingScheme.VISA, request.getRouting().getAttempts().get(0).getScheme());
    }

    @Test
    void shouldSetSubscriptionInPaymentRequest() {
        final PaymentSubscription subscription = PaymentSubscription.builder()
                .id("sub_ref_001")
                .build();

        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.EUR)
                .subscription(subscription)
                .build();

        assertNotNull(request.getSubscription());
        assertEquals("sub_ref_001", request.getSubscription().getId());
    }

    @Test
    void shouldHaveNullNewFieldsByDefault() {
        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.GBP)
                .amount(500L)
                .build();

        assertNull(request.getExpireOn());
        assertNull(request.getRouting());
        assertNull(request.getSubscription());
    }

    // ─── GetPaymentResponse.authentication ───────────────────────────────────

    @Test
    void shouldSetAuthenticationOnGetPaymentResponse() {
        final PaymentResponseAuthentication auth = new PaymentResponseAuthentication();
        auth.setExperience(AuthenticationExperience.GOOGLE_SPA);

        final GetPaymentResponse response = new GetPaymentResponse();
        response.setAuthentication(auth);

        assertNotNull(response.getAuthentication());
        assertEquals(AuthenticationExperience.GOOGLE_SPA, response.getAuthentication().getExperience());
    }

    @Test
    void shouldBuildPaymentResponseAuthentication() {
        final PaymentResponseAuthentication auth = new PaymentResponseAuthentication();
        auth.setExperience(AuthenticationExperience.THREE_DS);

        assertEquals(AuthenticationExperience.THREE_DS, auth.getExperience());
    }

    @Test
    void shouldHaveNullAuthenticationOnGetPaymentResponseByDefault() {
        final GetPaymentResponse response = new GetPaymentResponse();
        assertNull(response.getAuthentication());
    }
}
