package com.checkout.schema;

import com.checkout.GsonSerializer;
import com.checkout.accounts.payout.schedule.response.CurrencySchedule;
import com.checkout.customers.CustomerResponse;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.RequestAPaymentOrPayoutResponseCreated;
import com.checkout.issuing.cardholders.CardholderCardsResponse;
import com.checkout.issuing.controls.requests.VelocityLimit;
import com.checkout.issuing.controls.requests.VelocityWindow;
import com.checkout.issuing.controls.requests.VelocityWindowType;
import com.checkout.issuing.controls.requests.controlgroup.CreateControlGroupRequest;
import com.checkout.issuing.controls.requests.controlgroup.VelocityControlGroupControl;
import com.checkout.issuing.controls.responses.controlgroup.ControlGroupResponse;
import com.checkout.issuing.controls.responses.query.CardControlsQueryResponse;
import com.checkout.workflows.GetWorkflowResponse;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Regression tests for polymorphic types nested inside a field or collection whose declared type is
 * the abstract base.
 *
 * <p>Every polymorphic base in this SDK declares its discriminator as a real field, so that callers
 * get a typed getter. The factories for these hierarchies are registered without
 * {@code maintainType}, which means the factory owns the discriminator on the wire: it strips the
 * field on read and re-injects it from the registered label on write. Upstream Gson refuses to
 * serialize a subtype that already declares that field, so before the local modification in
 * {@link com.google.gson.typeadapters.RuntimeTypeAdapterFactory} every case below failed with
 *
 * <pre>   {@code
 *   JsonParseException: cannot serialize <subtype> because it already defines a field named type
 * }</pre>
 *
 * <p>Deserialization was never affected, which is why the SDK's response handling hid the defect.
 * The write path is reached only when the declared type is the base, so serializing a subtype
 * directly always worked and the failure surfaced only through the containers exercised here.
 * {@link CreateControlGroupRequest} is the case that mattered most: it is an outbound request body,
 * so the exception was thrown before the HTTP call and made the endpoint unusable.
 *
 * <p>Each test asserts the discriminator survives exactly once, with the value of the registered
 * label, and that no other property is lost in the round trip.
 */
class PolymorphicDiscriminatorSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    // ------------------------------------------------------------------
    // Outbound request bodies
    // ------------------------------------------------------------------

    @Test
    void shouldSerializeControlGroupRequestWithPolymorphicControls() {
        final CreateControlGroupRequest request = CreateControlGroupRequest.builder()
                .description("Velocity control group")
                .controls(Collections.singletonList(VelocityControlGroupControl.builder()
                        .description("Daily spend cap")
                        .velocityLimit(VelocityLimit.builder()
                                .amountLimit(1000)
                                .velocityWindow(VelocityWindow.builder()
                                        .type(VelocityWindowType.DAILY)
                                        .build())
                                .build())
                        .build()))
                .build();

        final String json = serializer.toJson(request);

        assertEquals(1, occurrences(json, "\"control_type\""));
        assertTrue(json.contains("\"control_type\":\"velocity_limit\""));
        assertTrue(json.contains("\"description\":\"Velocity control group\""));
        assertTrue(json.contains("\"description\":\"Daily spend cap\""));
        assertTrue(json.contains("\"amount_limit\":1000"));
        assertTrue(json.contains("\"type\":\"daily\""));
    }

    // ------------------------------------------------------------------
    // Response bodies, round-tripped
    // ------------------------------------------------------------------

    @Test
    void shouldRoundTripPaymentResponseWithPolymorphicSource() {
        final String json = roundTrip(
                "{\"id\":\"pay_1\",\"source\":{\"type\":\"ach\",\"id\":\"src_ach_1\"}}",
                RequestAPaymentOrPayoutResponseCreated.class);

        assertEquals(1, occurrences(json, "\"type\""));
        assertTrue(json.contains("\"type\":\"ach\""));
        assertTrue(json.contains("\"id\":\"src_ach_1\""));
    }

    @Test
    void shouldRoundTripCustomerResponseWithPolymorphicInstruments() {
        final String json = roundTrip(
                "{\"id\":\"cus_1\",\"instruments\":[{\"type\":\"card\",\"id\":\"src_1\","
                        + "\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\"}]}",
                CustomerResponse.class);

        assertEquals(1, occurrences(json, "\"type\""));
        assertTrue(json.contains("\"type\":\"card\""));
        assertTrue(json.contains("\"fingerprint\":\"vnsdrvikkvre3dtrjjvlm5du4q\""));
    }

    @Test
    void shouldRoundTripWorkflowResponseWithPolymorphicActionsAndConditions() {
        final String json = roundTrip(
                "{\"id\":\"wf_1\",\"name\":\"n\","
                        + "\"conditions\":[{\"type\":\"event\",\"events\":{}}],"
                        + "\"actions\":[{\"type\":\"webhook\",\"url\":\"https://example.test\"}]}",
                GetWorkflowResponse.class);

        assertEquals(2, occurrences(json, "\"type\""));
        assertTrue(json.contains("\"type\":\"event\""));
        assertTrue(json.contains("\"type\":\"webhook\""));
        assertTrue(json.contains("\"url\":\"https://example.test\""));
    }

    @Test
    void shouldRoundTripCurrencyScheduleWithPolymorphicRecurrence() {
        final String json = roundTrip(
                "{\"enabled\":true,\"threshold\":100,\"recurrence\":{\"frequency\":\"Daily\"}}",
                CurrencySchedule.class);

        assertEquals(1, occurrences(json, "\"frequency\""));
        assertTrue(json.contains("\"frequency\":\"Daily\""));
        assertTrue(json.contains("\"threshold\":100"));
    }

    @Test
    void shouldRoundTripCardholderCardsWithPolymorphicCardDetails() {
        final String json = roundTrip(
                "{\"cards\":[{\"type\":\"virtual\",\"id\":\"crd_1\"}]}",
                CardholderCardsResponse.class);

        assertEquals(1, occurrences(json, "\"type\""));
        assertTrue(json.contains("\"type\":\"virtual\""));
        assertTrue(json.contains("\"id\":\"crd_1\""));
    }

    @Test
    void shouldRoundTripCardControlsQueryWithPolymorphicControls() {
        final String json = roundTrip(
                "{\"controls\":[{\"control_type\":\"velocity_limit\",\"id\":\"ctr_1\"}]}",
                CardControlsQueryResponse.class);

        assertEquals(1, occurrences(json, "\"control_type\""));
        assertTrue(json.contains("\"control_type\":\"velocity_limit\""));
        assertTrue(json.contains("\"id\":\"ctr_1\""));
    }

    @Test
    void shouldRoundTripControlGroupResponseWithPolymorphicControls() {
        final String json = roundTrip(
                "{\"id\":\"cg_1\",\"controls\":[{\"control_type\":\"velocity_limit\","
                        + "\"description\":\"Daily spend cap\"}]}",
                ControlGroupResponse.class);

        assertEquals(1, occurrences(json, "\"control_type\""));
        assertTrue(json.contains("\"control_type\":\"velocity_limit\""));
        assertTrue(json.contains("\"description\":\"Daily spend cap\""));
    }

    private <T> String roundTrip(final String json, final Class<T> type) {
        return serializer.toJson(serializer.fromJson(json, type));
    }

    private int occurrences(final String json, final String needle) {
        int count = 0;
        int from = json.indexOf(needle);
        while (from != -1) {
            count++;
            from = json.indexOf(needle, from + needle.length());
        }
        return count;
    }
}
