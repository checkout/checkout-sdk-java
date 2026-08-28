package com.checkout;

import com.checkout.issuing.controls.requests.VelocityLimit;
import com.checkout.issuing.controls.requests.VelocityWindow;
import com.checkout.issuing.controls.requests.VelocityWindowType;
import com.checkout.issuing.controls.requests.controlgroup.CreateControlGroupRequest;
import com.checkout.issuing.controls.requests.controlgroup.VelocityControlGroupControl;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class ScratchDiscriminatorProbe {

    private final GsonSerializer serializer = new GsonSerializer();

    private void probe(final String label, final Object value) {
        try {
            System.out.println("PROBE " + label + " => OK: " + serializer.toJson(value));
        } catch (final RuntimeException e) {
            System.out.println("PROBE " + label + " => THROWS: " + e.getMessage());
        }
    }

    private void probeRoundTrip(final String label, final String json, final Class<?> type) {
        try {
            final Object parsed = serializer.fromJson(json, type);
            probe(label, parsed);
        } catch (final RuntimeException e) {
            System.out.println("PROBE " + label + " => READ-THROWS: " + e.getMessage());
        }
    }

    @Test
    void probeAll() {
        // 1. REQUEST path - issuing control groups
        probe("CreateControlGroupRequest(request!)", CreateControlGroupRequest.builder()
                .description("g")
                .controls(Collections.singletonList(VelocityControlGroupControl.builder()
                        .description("c")
                        .velocityLimit(VelocityLimit.builder()
                                .amountLimit(1000)
                                .velocityWindow(VelocityWindow.builder().type(VelocityWindowType.DAILY).build())
                                .build())
                        .build()))
                .build());

        // 2. customers - List<GetInstrumentResponse>
        probeRoundTrip("CustomerResponse.instruments",
                "{\"id\":\"cus_1\",\"instruments\":[{\"type\":\"card\",\"id\":\"src_1\",\"fingerprint\":\"f\"}]}",
                com.checkout.customers.CustomerResponse.class);

        // 3. workflows - List<WorkflowActionResponse> + List<WorkflowConditionResponse>
        probeRoundTrip("GetWorkflowResponse.actions+conditions",
                "{\"id\":\"wf_1\",\"name\":\"n\","
                        + "\"conditions\":[{\"type\":\"event\",\"events\":{}}],"
                        + "\"actions\":[{\"type\":\"webhook\",\"url\":\"https://x.test\"}]}",
                com.checkout.workflows.GetWorkflowResponse.class);

        // 4. accounts payout schedule - ScheduleResponse recurrence
        probeRoundTrip("CurrencySchedule.recurrence",
                "{\"enabled\":true,\"threshold\":100,\"recurrence\":{\"frequency\":\"daily\"}}",
                com.checkout.accounts.payout.schedule.response.CurrencySchedule.class);

        // 5. issuing cardholder cards - List<CardDetailsResponse>
        probeRoundTrip("CardholderCardsResponse.cards",
                "{\"cards\":[{\"type\":\"virtual\",\"id\":\"crd_1\"}]}",
                com.checkout.issuing.cardholders.CardholderCardsResponse.class);

        // 6. issuing card controls query - List<CardControlResponse>
        probeRoundTrip("CardControlsQueryResponse.controls",
                "{\"controls\":[{\"control_type\":\"velocity_limit\",\"id\":\"ctr_1\"}]}",
                com.checkout.issuing.controls.responses.query.CardControlsQueryResponse.class);

        // 7. issuing control group response - List<ControlGroupControl>
        probeRoundTrip("ControlGroupResponse.controls",
                "{\"id\":\"cg_1\",\"controls\":[{\"control_type\":\"velocity_limit\",\"description\":\"d\"}]}",
                com.checkout.issuing.controls.responses.controlgroup.ControlGroupResponse.class);

        // 8. the payments source hierarchy touched by this branch
        probeRoundTrip("RequestAPaymentOrPayoutResponseCreated.source",
                "{\"id\":\"pay_1\",\"source\":{\"type\":\"ach\",\"id\":\"src_ach_1\"}}",
                com.checkout.handlepaymentsandpayouts.payments.postpayments.responses
                        .requestapaymentorpayoutresponsecreated.RequestAPaymentOrPayoutResponseCreated.class);
    }
}
