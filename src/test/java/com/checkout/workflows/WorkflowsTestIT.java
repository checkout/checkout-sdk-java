package com.checkout.workflows;

import com.checkout.EmptyResponse;
import com.checkout.common.IdResponse;
import com.checkout.workflows.actions.request.WebhookWorkflowActionRequest;
import com.checkout.workflows.actions.response.WebhookWorkflowActionResponse;
import com.checkout.workflows.conditions.WorkflowConditionType;
import com.checkout.workflows.conditions.request.EventWorkflowConditionRequest;
import com.checkout.workflows.conditions.response.EntityWorkflowConditionResponse;
import com.checkout.workflows.conditions.response.EventWorkflowConditionResponse;
import com.checkout.workflows.events.EventTypesRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("unstable")
class WorkflowsTestIT extends AbstractWorkflowTestIT {

    @Test
    void shouldCreateAndGetWorkflows() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        // Get by Id
        final GetWorkflowResponse getWorkflowResponse = blocking(() -> checkoutApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
        assertNotNull(getWorkflowResponse);
        assertNotNull(getWorkflowResponse.getId());
        assertTrue(getWorkflowResponse.getActive());
        assertEquals(TESTING, getWorkflowResponse.getName());

        assertNotNull(getWorkflowResponse.getActions());
        assertEquals(1, getWorkflowResponse.getActions().size());
        assertTrue(getWorkflowResponse.getActions().get(0) instanceof WebhookWorkflowActionResponse);

        final WebhookWorkflowActionResponse webhookWorkflowAction = (WebhookWorkflowActionResponse) getWorkflowResponse.getActions().get(0);
        assertNotNull(webhookWorkflowAction.getId());
        assertNotNull(webhookWorkflowAction.getHeaders());
        assertNotNull(webhookWorkflowAction.getSignature());
        assertNotNull(webhookWorkflowAction.getUrl());

        assertNotNull(getWorkflowResponse.getConditions());
        assertEquals(3, getWorkflowResponse.getConditions().size());
        assertTrue(getWorkflowResponse.getConditions().get(0) instanceof EventWorkflowConditionResponse);
        assertEquals(events, ((EventWorkflowConditionResponse) getWorkflowResponse.getConditions().get(0)).getEvents());

        assertTrue(getWorkflowResponse.getConditions().get(1) instanceof EntityWorkflowConditionResponse);
        assertEquals(WORKFLOW_ENTITY_ID, ((EntityWorkflowConditionResponse) getWorkflowResponse.getConditions().get(1)).getEntities().get(0));

        assertNotNull(getWorkflowResponse.getLink(SELF));

        // Get all
        final GetWorkflowsResponse getWorkflowsResponse = blocking(() -> checkoutApi.workflowsClient().getWorkflows());

        assertNotNull(getWorkflowsResponse);
        assertNotNull(getWorkflowsResponse.getWorkflows());
        assertFalse(getWorkflowsResponse.getWorkflows().isEmpty());
        for (final Workflow workflow : getWorkflowsResponse.getWorkflows()) {
            assertEquals(TESTING, workflow.getName());
            assertNotNull(workflow.getId());
            assertNotNull(workflow.getLink(SELF));
            assertTrue(workflow.getActive());
            queueWorkflowCleanup(workflow.getId());
        }

    }

    @Test
    void shouldCreateAndUpdateWorkflow() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final UpdateWorkflowRequest updateWorkflowRequest = UpdateWorkflowRequest.builder()
                .name("testing_2")
                .active(Boolean.FALSE)
                .build();

        final UpdateWorkflowResponse updateWorkflowResponse = blocking(() -> checkoutApi.workflowsClient().updateWorkflow(createWorkflowResponse.getId(), updateWorkflowRequest));

        assertNotNull(updateWorkflowResponse);
        assertEquals("testing_2", updateWorkflowResponse.getName());
        assertFalse(updateWorkflowResponse.getActive());

    }

    @Test
    void shouldAddAndDeleteWorkflowAction() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final WebhookWorkflowActionRequest workflowActionRequest = WebhookWorkflowActionRequest.builder()
                .signature(baseWorkflowActionRequest.getSignature())
                .headers(baseWorkflowActionRequest.getHeaders())
                .url(baseWorkflowActionRequest.getUrl() + "/fake")
                .build();

        final IdResponse workflowAction = blocking(() -> checkoutApi.workflowsClient().addWorkflowAction(createWorkflowResponse.getId(), workflowActionRequest));

        assertNotNull(workflowAction);

        assertDoesNotThrow(() -> checkoutApi.workflowsClient().removeWorkflowAction(createWorkflowResponse.getId(), workflowAction.getId()));

    }

    @Test
    void shouldUpdateWorkflowAction() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final GetWorkflowResponse getWorkflowResponse1 = blocking(() -> checkoutApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
        assertNotNull(getWorkflowResponse1);
        assertEquals(1, getWorkflowResponse1.getActions().size());

        final String actionId = getWorkflowResponse1.getActions().get(0).getId();

        final WebhookWorkflowActionRequest updateAction = WebhookWorkflowActionRequest.builder()
                .signature(baseWorkflowActionRequest.getSignature())
                .headers(baseWorkflowActionRequest.getHeaders())
                .url(baseWorkflowActionRequest.getUrl() + "/fake")
                .build();

        blocking(() -> checkoutApi.workflowsClient().updateWorkflowAction(getWorkflowResponse1.getId(), actionId, updateAction));

        final GetWorkflowResponse getWorkflowResponse2 = blocking(() -> checkoutApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
        assertNotNull(getWorkflowResponse2);
        assertNotNull(getWorkflowResponse2.getActions());
        assertEquals(1, getWorkflowResponse2.getActions().size());
        assertTrue(getWorkflowResponse2.getActions().get(0) instanceof WebhookWorkflowActionResponse);

        final WebhookWorkflowActionResponse webhookWorkflowAction = (WebhookWorkflowActionResponse) getWorkflowResponse2.getActions().get(0);
        assertEquals(actionId, webhookWorkflowAction.getId());
        assertNotNull(webhookWorkflowAction.getSignature());
        assertEquals(baseWorkflowActionRequest.getHeaders(), webhookWorkflowAction.getHeaders());
        assertEquals(baseWorkflowActionRequest.getUrl() + "/fake", webhookWorkflowAction.getUrl());

    }

    @Test
    void shouldUpdateWorkflowCondition() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final GetWorkflowResponse getWorkflowResponse = blocking(() -> checkoutApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
        assertNotNull(getWorkflowResponse);
        assertEquals(3, getWorkflowResponse.getConditions().size());

        final EventWorkflowConditionResponse eventConditionResponse = getWorkflowResponse.getConditions().stream()
                .filter(condition -> WorkflowConditionType.EVENT.equals(condition.getType()))
                .map(type -> (EventWorkflowConditionResponse) type)
                .findFirst().orElse(null);

        assertNotNull(eventConditionResponse);

        final Map<String, Set<String>> events = new HashMap<>();
        events.put(GATEWAY, new HashSet<>(GATEWAY_EVENT_TYPES));

        final EventWorkflowConditionRequest updateEventCondition = EventWorkflowConditionRequest.builder()
                .events(events)
                .build();

        blocking(() -> checkoutApi.workflowsClient().updateWorkflowCondition(getWorkflowResponse.getId(), eventConditionResponse.getId(), updateEventCondition));

        final GetWorkflowResponse updatedWorkflow = blocking(() -> checkoutApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
        assertNotNull(updatedWorkflow);
        assertEquals(3, updatedWorkflow.getConditions().size());

        final EventWorkflowConditionResponse updatedEventConditionResponse = getWorkflowResponse.getConditions().stream()
                .filter(condition -> WorkflowConditionType.EVENT.equals(condition.getType()))
                .map(type -> (EventWorkflowConditionResponse) type)
                .findFirst().orElse(null);

        assertNotNull(updatedEventConditionResponse);
        assertNotNull(updatedEventConditionResponse.getEvents());
        assertEquals(new HashSet<>(GATEWAY_EVENT_TYPES), updatedEventConditionResponse.getEvents().get(GATEWAY));

        assertDoesNotThrow(() -> checkoutApi.workflowsClient().removeWorkflowCondition(getWorkflowResponse.getId(), eventConditionResponse.getId()));

    }

    @Test
    void shouldCreateAndTestWorkflows() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();
        final EventTypesRequest eventTypesRequest = EventTypesRequest.builder()
                .eventTypes(Arrays.asList("payment_approved",
                        "payment_declined",
                        "card_verification_declined",
                        "card_verified",
                        "payment_authorization_incremented",
                        "payment_authorization_increment_declined",
                        "payment_capture_declined",
                        "payment_captured",
                        "payment_refund_declined",
                        "payment_refunded",
                        "payment_void_declined",
                        "payment_voided",
                        "dispute_canceled",
                        "dispute_evidence_required",
                        "dispute_expired",
                        "dispute_lost",
                        "dispute_resolved",
                        "dispute_won"))
                .build();

        final EmptyResponse response = blocking(() -> checkoutApi.workflowsClient().testWorkflow(createWorkflowResponse.getId(), eventTypesRequest));
        assertNotNull(response);

    }

    protected CreateWorkflowResponse createWorkflow() {

        final CreateWorkflowRequest request = CreateWorkflowRequest.builder()
                .name(TESTING)
                .active(Boolean.TRUE)
                .actions(Collections.singletonList(baseWorkflowActionRequest))
                .conditions(Arrays.asList(baseEventWorkflowConditionRequest, baseEntityWorkflowConditionRequest, processingChannelWorkflowConditionRequest))
                .build();

        final CreateWorkflowResponse createWorkflowResponse = blocking(() -> checkoutApi.workflowsClient().createWorkflow(request));
        assertNotNull(createWorkflowResponse);
        assertNotNull(createWorkflowResponse.getId());
        assertNotNull(createWorkflowResponse.getLink(SELF));

        queueWorkflowCleanup(createWorkflowResponse.getId());

        return createWorkflowResponse;

    }

}
