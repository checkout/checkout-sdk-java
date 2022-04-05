package com.checkout.workflows.four;

import com.checkout.payments.four.AbstractPaymentsTestIT;
import com.checkout.workflows.four.actions.WebhookSignature;
import com.checkout.workflows.four.actions.request.WebhookWorkflowActionRequest;
import com.checkout.workflows.four.conditions.request.EntityWorkflowConditionRequest;
import com.checkout.workflows.four.conditions.request.EventWorkflowConditionRequest;
import com.checkout.workflows.four.conditions.request.ProcessingChannelWorkflowConditionRequest;
import com.checkout.workflows.four.events.SubjectEvent;
import com.checkout.workflows.four.events.SubjectEventsResponse;
import org.junit.jupiter.api.AfterEach;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

abstract class AbstractWorkflowTestIT extends AbstractPaymentsTestIT {

    protected static final String WORKFLOW_ENTITY_ID = "ent_kidtcgc3ge5unf4a5i6enhnr5m";
    protected static final String TESTING = "testing";
    protected static final String GATEWAY = "gateway";
    protected static final String PAYMENT_CAPTURED = "payment_captured";
    protected static final String PAYMENT_APPROVED = "payment_approved";
    protected static final String PROCESSING_CHANNEL_ID = "pc_5jp2az55l3cuths25t5p3xhwru";

    protected static final List<String> GATEWAY_EVENT_TYPES = Arrays.asList(PAYMENT_APPROVED, "payment_declined",
            PAYMENT_CAPTURED, "payment_capture_declined", "payment_refunded", "payment_refund_declined", "payment_voided",
            "payment_void_declined", "card_verified", "card_verification_declined", "payment_authorization_incremented",
            "payment_authorization_increment_declined");

    private static final List<String> DISPUTE_EVENT_TYPES = Arrays.asList("dispute_evidence_required", "dispute_won",
            "dispute_expired", "dispute_lost", "dispute_resolved");

    protected final WebhookWorkflowActionRequest baseWorkflowActionRequest = WebhookWorkflowActionRequest.builder()
            .url("https://google.com/fail")
            .headers(new HashMap<>())
            .signature(WebhookSignature.builder().key("8V8x0dLK%AyD*DNS8JJr").method("HMACSHA256").build())
            .build();

    final Map<String, Set<String>> events = new HashMap<>();

    protected final EventWorkflowConditionRequest baseEventWorkflowConditionRequest = EventWorkflowConditionRequest.builder()
            .events(events)
            .build();

    protected final EntityWorkflowConditionRequest baseEntityWorkflowConditionRequest = EntityWorkflowConditionRequest.builder()
            .entities(Collections.singletonList(WORKFLOW_ENTITY_ID))
            .build();

    protected final ProcessingChannelWorkflowConditionRequest processingChannelWorkflowConditionRequest = ProcessingChannelWorkflowConditionRequest.builder()
            .processingChannels(Collections.singletonList(PROCESSING_CHANNEL_ID))
            .build();

    private final Set<String> workflows = new HashSet<>();

    public AbstractWorkflowTestIT() {
        events.put(GATEWAY, new HashSet<>(GATEWAY_EVENT_TYPES));
        events.put("dispute", new HashSet<>(DISPUTE_EVENT_TYPES));
    }

    @AfterEach
    protected void cleanup() {
        workflows.forEach(workflow -> blocking(() -> fourApi.workflowsClient().removeWorkflow(workflow)));
    }

    protected void queueWorkflowCleanup(final String workflowId) {
        this.workflows.add(workflowId);
    }

    protected CreateWorkflowResponse createWorkflow() {

        final CreateWorkflowRequest request = CreateWorkflowRequest.builder()
                .name(TESTING)
                .actions(Collections.singletonList(baseWorkflowActionRequest))
                .conditions(Arrays.asList(baseEventWorkflowConditionRequest, baseEntityWorkflowConditionRequest, processingChannelWorkflowConditionRequest))
                .build();

        final CreateWorkflowResponse createWorkflowResponse = blocking(() -> fourApi.workflowsClient().createWorkflow(request));
        assertNotNull(createWorkflowResponse);
        assertNotNull(createWorkflowResponse);
        assertNotNull(createWorkflowResponse.getId());
        assertNotNull(createWorkflowResponse.getLink(SELF));

        queueWorkflowCleanup(createWorkflowResponse.getId());

        return createWorkflowResponse;

    }

    protected SubjectEvent getSubjectEvent(final String subjectId) {

        final SubjectEventsResponse subjectEventsResponse = blocking(() -> fourApi.workflowsClient().getSubjectEvents(subjectId));

        assertNotNull(subjectEventsResponse);
        assertEquals(1, subjectEventsResponse.getEvents().size());

        final SubjectEvent paymentApprovedEvent = subjectEventsResponse.getEvents().stream()
                .filter(event -> event.getType().equals(PAYMENT_APPROVED))
                .findFirst()
                .orElse(null);

        assertNotNull(paymentApprovedEvent);

        return paymentApprovedEvent;
    }

}
