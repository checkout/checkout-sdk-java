package com.checkout.handlepaymentsandpayouts.flow;

import com.checkout.GsonSerializer;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionCompleteRequest;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionCreateRequest;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionSubmitRequest;
import com.checkout.payments.AmountVariabilityType;
import com.checkout.payments.AuthorizationType;
import com.checkout.payments.LocaleType;
import com.checkout.payments.PaymentPlan;
import com.checkout.payments.PaymentType;
import com.checkout.payments.RiskRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Regression tests for the capture and payment_type defaults on the Flow payment session requests.
 *
 * The session creation requests (POST /payment-sessions and POST /payment-sessions/complete) must
 * keep sending the API defaults, because the caller is supplying the payment's values in that same
 * call. The submit request (POST /payment-sessions/{id}/submit) must send neither field unless the
 * caller sets it, because any value present in the submit body is applied to the payment attempt
 * and would overwrite the value provided when the payment session was created.
 */
class PaymentSessionCaptureDefaultsTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ---------------------------------------------------------------
    //  Submit: nothing is sent unless the caller sets it
    // ---------------------------------------------------------------

    @Test
    void submitRequestBuiltWithBuilderShouldOnlySerializeSessionData() {
        PaymentSessionSubmitRequest request = PaymentSessionSubmitRequest.builder()
                .sessionData("SD")
                .build();

        assertEquals("{\"session_data\":\"SD\"}", serializer.toJson(request));
    }

    @Test
    void submitRequestBuiltWithNoArgsConstructorShouldOnlySerializeSessionData() {
        PaymentSessionSubmitRequest request = new PaymentSessionSubmitRequest();
        request.setSessionData("SD");

        assertEquals("{\"session_data\":\"SD\"}", serializer.toJson(request));
    }

    @Test
    void submitRequestShouldLeaveCaptureAndPaymentTypeNullWhenNotSet() {
        PaymentSessionSubmitRequest fromBuilder = PaymentSessionSubmitRequest.builder()
                .sessionData("SD")
                .build();
        PaymentSessionSubmitRequest fromConstructor = new PaymentSessionSubmitRequest();

        assertNull(fromBuilder.getCapture(), "capture must not be defaulted on the submit request");
        assertNull(fromBuilder.getPaymentType(), "payment_type must not be defaulted on the submit request");
        assertNull(fromConstructor.getCapture(), "capture must not be defaulted by the no-args constructor");
        assertNull(fromConstructor.getPaymentType(), "payment_type must not be defaulted by the no-args constructor");
    }

    // ---------------------------------------------------------------
    //  Submit: both fields remain usable when the caller wants them
    // ---------------------------------------------------------------

    @Test
    void submitRequestShouldSerializeCaptureFalseWhenExplicitlySet() {
        PaymentSessionSubmitRequest request = PaymentSessionSubmitRequest.builder()
                .sessionData("SD")
                .capture(false)
                .build();

        String json = serializer.toJson(request);

        assertTrue(json.contains("\"capture\":false"), "an explicit capture of false must be sent");
    }

    @Test
    void submitRequestShouldSerializeCaptureTrueAndPaymentTypeWhenExplicitlySet() {
        PaymentSessionSubmitRequest request = PaymentSessionSubmitRequest.builder()
                .sessionData("SD")
                .capture(true)
                .paymentType(PaymentType.RECURRING)
                .build();

        String json = serializer.toJson(request);

        assertTrue(json.contains("\"capture\":true"), "an explicit capture of true must be sent");
        assertTrue(json.contains("\"payment_type\":\"Recurring\""), "an explicit payment_type must be sent");
    }

    @Test
    void submitRequestShouldRoundTripEveryLocalField() {
        PaymentSessionSubmitRequest original = PaymentSessionSubmitRequest.builder()
                .sessionData("SD")
                .capture(false)
                .paymentType(PaymentType.MOTO)
                .amount(2500L)
                .currency(Currency.USD)
                .reference("ORD-456")
                .processingChannelId("pc_abcdefghijklmnopqrstuvwxyz")
                .build();

        PaymentSessionSubmitRequest roundTripped =
                serializer.fromJson(serializer.toJson(original), PaymentSessionSubmitRequest.class);

        assertEquals("SD", roundTripped.getSessionData());
        assertEquals(false, roundTripped.getCapture());
        assertEquals(PaymentType.MOTO, roundTripped.getPaymentType());
        assertEquals(2500L, roundTripped.getAmount());
        assertEquals(Currency.USD, roundTripped.getCurrency());
        assertEquals("ORD-456", roundTripped.getReference());
        assertEquals("pc_abcdefghijklmnopqrstuvwxyz", roundTripped.getProcessingChannelId());
    }

    @Test
    void submitRequestShouldNotSerializeSessionCreationOnlyFields() {
        PaymentSessionSubmitRequest request = PaymentSessionSubmitRequest.builder()
                .sessionData("SD")
                .amount(1000L)
                .currency(Currency.GBP)
                .capture(true)
                .paymentType(PaymentType.REGULAR)
                .build();

        String json = serializer.toJson(request);

        assertFalse(json.contains("\"locale\""), "locale is not accepted by the submit endpoint");
        assertFalse(json.contains("\"description\""), "description is not accepted by the submit endpoint");
        assertFalse(json.contains("\"display_name\""), "display_name is not accepted by the submit endpoint");
        assertFalse(json.contains("\"authorization_type\""), "authorization_type is not accepted by the submit endpoint");
        assertFalse(json.contains("\"payment_plan\""), "payment_plan is not accepted by the submit endpoint");
        assertFalse(json.contains("\"risk\""), "risk is not accepted by the submit endpoint");
    }

    // ---------------------------------------------------------------
    //  Create: the defaults are kept
    // ---------------------------------------------------------------

    @Test
    void createRequestShouldSerializeTheApiDefaults() {
        PaymentSessionCreateRequest request = PaymentSessionCreateRequest.builder()
                .amount(1000L)
                .currency(Currency.GBP)
                .build();

        String json = serializer.toJson(request);

        assertTrue(json.contains("\"capture\":true"), "create must keep the capture default");
        assertTrue(json.contains("\"payment_type\":\"Regular\""), "create must keep the payment_type default");
        assertTrue(json.contains("\"locale\":\"en-GB\""), "create must keep the locale default");
    }

    @Test
    void completeRequestShouldSerializeTheApiDefaults() {
        PaymentSessionCompleteRequest request = PaymentSessionCompleteRequest.builder()
                .sessionData("SD")
                .amount(2000L)
                .currency(Currency.USD)
                .build();

        String json = serializer.toJson(request);

        assertTrue(json.contains("\"capture\":true"), "complete must keep the capture default");
        assertTrue(json.contains("\"payment_type\":\"Regular\""), "complete must keep the payment_type default");
        assertTrue(json.contains("\"locale\":\"en-GB\""), "complete must keep the locale default");
    }

    @Test
    void createRequestShouldStillAllowOverridingTheDefaults() {
        PaymentSessionCreateRequest request = PaymentSessionCreateRequest.builder()
                .amount(1000L)
                .currency(Currency.GBP)
                .capture(false)
                .paymentType(PaymentType.UNSCHEDULED)
                .locale(LocaleType.FR_FR)
                .build();

        String json = serializer.toJson(request);

        assertTrue(json.contains("\"capture\":false"));
        assertTrue(json.contains("\"payment_type\":\"Unscheduled\""));
        assertTrue(json.contains("\"locale\":\"fr-FR\""));
    }

    @Test
    void createRequestShouldRoundTripEverySessionCreationOnlyField() {
        PaymentSessionCreateRequest original = PaymentSessionCreateRequest.builder()
                .capture(false)
                .paymentType(PaymentType.INSTALLMENT)
                .locale(LocaleType.DE_DE)
                .authorizationType(AuthorizationType.ESTIMATED)
                .description("Payment for gold necklace")
                .displayName("Example Store")
                .paymentPlan(PaymentPlan.builder()
                        .amountVariabilityType(AmountVariabilityType.FIXED)
                        .daysBetweenPayments(28)
                        .build())
                .risk(RiskRequest.builder()
                        .enabled(false)
                        .build())
                .build();

        PaymentSessionCreateRequest roundTripped =
                serializer.fromJson(serializer.toJson(original), PaymentSessionCreateRequest.class);

        assertEquals(false, roundTripped.getCapture());
        assertEquals(PaymentType.INSTALLMENT, roundTripped.getPaymentType());
        assertEquals(LocaleType.DE_DE, roundTripped.getLocale());
        assertEquals(AuthorizationType.ESTIMATED, roundTripped.getAuthorizationType());
        assertEquals("Payment for gold necklace", roundTripped.getDescription());
        assertEquals("Example Store", roundTripped.getDisplayName());
        assertEquals(AmountVariabilityType.FIXED, roundTripped.getPaymentPlan().getAmountVariabilityType());
        assertEquals(28, roundTripped.getPaymentPlan().getDaysBetweenPayments());
        assertEquals(false, roundTripped.getRisk().getEnabled());
    }

    // ---------------------------------------------------------------
    //  The documented submit example from the API reference
    // ---------------------------------------------------------------

    @Test
    void shouldDeserializeTheDocumentedSubmitExample() {
        String json = "{"
                + "\"session_data\":\"{SESSION_DATA_FROM_FLOW}\","
                + "\"3ds\":{\"enabled\":true}"
                + "}";

        PaymentSessionSubmitRequest request = serializer.fromJson(json, PaymentSessionSubmitRequest.class);

        assertEquals("{SESSION_DATA_FROM_FLOW}", request.getSessionData());
        assertTrue(request.getThreeDS().getEnabled());
        assertNull(request.getCapture(), "the documented example does not carry capture");
        assertNull(request.getPaymentType(), "the documented example does not carry payment_type");
    }
}
