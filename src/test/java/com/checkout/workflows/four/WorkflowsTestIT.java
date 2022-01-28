package com.checkout.workflows.four;

import com.checkout.workflows.four.actions.request.WebhookWorkflowActionRequest;
import com.checkout.workflows.four.actions.response.WebhookWorkflowActionResponse;
import com.checkout.workflows.four.conditions.WorkflowConditionType;
import com.checkout.workflows.four.conditions.request.EventWorkflowConditionRequest;
import com.checkout.workflows.four.conditions.response.EntityWorkflowConditionResponse;
import com.checkout.workflows.four.conditions.response.EventWorkflowConditionResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkflowsTestIT extends AbstractWorkflowTestIT {

    @Test
    void shouldCreateAndGetWorkflows() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        // Get by Id
        final GetWorkflowResponse getWorkflowResponse = blocking(() -> fourApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));

        assertNotNull(getWorkflowResponse);
        assertNotNull(getWorkflowResponse.getId());
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
        final GetWorkflowsResponse getWorkflowsResponse = blocking(() -> fourApi.workflowsClient().getWorkflows());

        assertNotNull(getWorkflowsResponse);
        assertNotNull(getWorkflowsResponse.getWorkflows());
        assertFalse(getWorkflowsResponse.getWorkflows().isEmpty());
        for (final Workflow workflow : getWorkflowsResponse.getWorkflows()) {
            assertEquals(TESTING, workflow.getName());
            assertNotNull(workflow.getId());
            assertNotNull(workflow.getLink(SELF));
            queueWorkflowCleanup(workflow.getId());
        }

    }

    @Test
    void shouldCreateAndUpdateWorkflow() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final UpdateWorkflowRequest updateWorkflowRequest = UpdateWorkflowRequest.builder()
                .name("testing_2")
                .build();

        final UpdateWorkflowResponse updateWorkflowResponse = blocking(() -> fourApi.workflowsClient().updateWorkflow(createWorkflowResponse.getId(), updateWorkflowRequest));

        assertNotNull(updateWorkflowResponse);
        assertEquals("testing_2", updateWorkflowResponse.getName());

    }

    @Test
    void shouldUpdateWorkflowAction() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final GetWorkflowResponse getWorkflowResponse1 = blocking(() -> fourApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
        assertNotNull(getWorkflowResponse1);
        assertEquals(1, getWorkflowResponse1.getActions().size());

        final String actionId = getWorkflowResponse1.getActions().get(0).getId();

        final WebhookWorkflowActionRequest updateAction = WebhookWorkflowActionRequest.builder()
                .signature(baseWorkflowActionRequest.getSignature())
                .headers(baseWorkflowActionRequest.getHeaders())
                .url(baseWorkflowActionRequest.getUrl() + "/fake")
                .build();

        blocking(() -> fourApi.workflowsClient().updateWorkflowAction(getWorkflowResponse1.getId(), actionId, updateAction));

        final GetWorkflowResponse getWorkflowResponse2 = blocking(() -> fourApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
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

        final GetWorkflowResponse getWorkflowResponse = blocking(() -> fourApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
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

        blocking(() -> fourApi.workflowsClient().updateWorkflowCondition(getWorkflowResponse.getId(), eventConditionResponse.getId(), updateEventCondition));

        final GetWorkflowResponse updatedWorkflow = blocking(() -> fourApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
        assertNotNull(updatedWorkflow);
        assertEquals(3, updatedWorkflow.getConditions().size());

        final EventWorkflowConditionResponse updatedEventConditionResponse = getWorkflowResponse.getConditions().stream()
                .filter(condition -> WorkflowConditionType.EVENT.equals(condition.getType()))
                .map(type -> (EventWorkflowConditionResponse) type)
                .findFirst().orElse(null);

        assertNotNull(updatedEventConditionResponse);
        assertNotNull(updatedEventConditionResponse.getEvents());
        assertEquals(new HashSet<>(GATEWAY_EVENT_TYPES), updatedEventConditionResponse.getEvents().get(GATEWAY));

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

}
