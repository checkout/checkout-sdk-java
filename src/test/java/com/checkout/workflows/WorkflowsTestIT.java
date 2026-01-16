package com.checkout.workflows;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.checkout.EmptyResponse;
import com.checkout.common.IdResponse;
import com.checkout.workflows.actions.request.WebhookWorkflowActionRequest;
import com.checkout.workflows.actions.response.WebhookWorkflowActionResponse;
import com.checkout.workflows.conditions.WorkflowConditionType;
import com.checkout.workflows.conditions.request.EventWorkflowConditionRequest;
import com.checkout.workflows.conditions.response.EntityWorkflowConditionResponse;
import com.checkout.workflows.conditions.response.EventWorkflowConditionResponse;
import com.checkout.workflows.events.EventTypesRequest;

@Disabled("unstable")
class WorkflowsTestIT extends AbstractWorkflowTestIT {

    @Test
    void shouldCreateAndGetWorkflows() {
        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        // Get by Id
        final GetWorkflowResponse getWorkflowResponse = blocking(() -> checkoutApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
        validateWorkflowResponse(getWorkflowResponse);
        validateWorkflowConditions(getWorkflowResponse);

        // Get all
        final GetWorkflowsResponse getWorkflowsResponse = blocking(() -> checkoutApi.workflowsClient().getWorkflows());
        validateWorkflowsResponse(getWorkflowsResponse);
    }

    @Test
    void shouldGetWorkflows() {
        final GetWorkflowsResponse getWorkflowsResponse = blocking(() -> checkoutApi.workflowsClient().getWorkflows());
        validateBasicWorkflowsResponse(getWorkflowsResponse);
    }

    @Test
    void shouldCreateAndUpdateWorkflow() {
        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();
        final UpdateWorkflowRequest updateWorkflowRequest = createUpdateWorkflowRequest();

        final UpdateWorkflowResponse updateWorkflowResponse = blocking(() -> checkoutApi.workflowsClient().updateWorkflow(createWorkflowResponse.getId(), updateWorkflowRequest));

        validateUpdateWorkflowResponse(updateWorkflowResponse);
    }

    @Test
    void shouldAddAndDeleteWorkflowAction() {
        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();
        final WebhookWorkflowActionRequest workflowActionRequest = createWorkflowActionRequest();

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
        final WebhookWorkflowActionRequest updateAction = createWorkflowActionRequest();

        blocking(() -> checkoutApi.workflowsClient().updateWorkflowAction(getWorkflowResponse1.getId(), actionId, updateAction));

        final GetWorkflowResponse getWorkflowResponse2 = blocking(() -> checkoutApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
        validateUpdatedWorkflowAction(getWorkflowResponse2, actionId);
    }

    @Test
    void shouldUpdateWorkflowCondition() {
        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final GetWorkflowResponse getWorkflowResponse = blocking(() -> checkoutApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
        assertNotNull(getWorkflowResponse);
        assertEquals(3, getWorkflowResponse.getConditions().size());

        final EventWorkflowConditionResponse eventConditionResponse = findEventCondition(getWorkflowResponse);
        assertNotNull(eventConditionResponse);

        final EventWorkflowConditionRequest updateEventCondition = createUpdateEventConditionRequest();

        blocking(() -> checkoutApi.workflowsClient().updateWorkflowCondition(getWorkflowResponse.getId(), eventConditionResponse.getId(), updateEventCondition));

        final GetWorkflowResponse updatedWorkflow = blocking(() -> checkoutApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
        validateUpdatedWorkflowCondition(updatedWorkflow);

        assertDoesNotThrow(() -> checkoutApi.workflowsClient().removeWorkflowCondition(getWorkflowResponse.getId(), eventConditionResponse.getId()));
    }

    @Test
    void shouldCreateAndTestWorkflows() {
        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();
        final EventTypesRequest eventTypesRequest = createEventTypesRequest();

        final EmptyResponse response = blocking(() -> checkoutApi.workflowsClient().testWorkflow(createWorkflowResponse.getId(), eventTypesRequest));
        assertNotNull(response);
    }

    // Synchronous methods
    @Test
    void shouldCreateAndGetWorkflowsSync() {
        final CreateWorkflowResponse createWorkflowResponse = createWorkflowSync();

        // Get by Id
        final GetWorkflowResponse getWorkflowResponse = checkoutApi.workflowsClient().getWorkflowSync(createWorkflowResponse.getId());
        validateWorkflowResponse(getWorkflowResponse);
        validateWorkflowConditions(getWorkflowResponse);

        // Get all
        final GetWorkflowsResponse getWorkflowsResponse = checkoutApi.workflowsClient().getWorkflowsSync();
        validateWorkflowsResponse(getWorkflowsResponse);
    }

    @Test
    void shouldGetWorkflowsSync() {
        final GetWorkflowsResponse getWorkflowsResponse = checkoutApi.workflowsClient().getWorkflowsSync();
        validateBasicWorkflowsResponse(getWorkflowsResponse);
    }

    @Test
    void shouldCreateAndUpdateWorkflowSync() {
        final CreateWorkflowResponse createWorkflowResponse = createWorkflowSync();
        final UpdateWorkflowRequest updateWorkflowRequest = createUpdateWorkflowRequest();

        final UpdateWorkflowResponse updateWorkflowResponse = checkoutApi.workflowsClient().updateWorkflowSync(createWorkflowResponse.getId(), updateWorkflowRequest);

        validateUpdateWorkflowResponse(updateWorkflowResponse);
    }

    @Test
    void shouldAddAndDeleteWorkflowActionSync() {
        final CreateWorkflowResponse createWorkflowResponse = createWorkflowSync();
        final WebhookWorkflowActionRequest workflowActionRequest = createWorkflowActionRequest();

        final IdResponse workflowAction = checkoutApi.workflowsClient().addWorkflowActionSync(createWorkflowResponse.getId(), workflowActionRequest);

        assertNotNull(workflowAction);

        assertDoesNotThrow(() -> checkoutApi.workflowsClient().removeWorkflowActionSync(createWorkflowResponse.getId(), workflowAction.getId()));
    }

    @Test
    void shouldUpdateWorkflowActionSync() {
        final CreateWorkflowResponse createWorkflowResponse = createWorkflowSync();

        final GetWorkflowResponse getWorkflowResponse1 = checkoutApi.workflowsClient().getWorkflowSync(createWorkflowResponse.getId());
        assertNotNull(getWorkflowResponse1);
        assertEquals(1, getWorkflowResponse1.getActions().size());

        final String actionId = getWorkflowResponse1.getActions().get(0).getId();
        final WebhookWorkflowActionRequest updateAction = createWorkflowActionRequest();

        checkoutApi.workflowsClient().updateWorkflowActionSync(getWorkflowResponse1.getId(), actionId, updateAction);

        final GetWorkflowResponse getWorkflowResponse2 = checkoutApi.workflowsClient().getWorkflowSync(createWorkflowResponse.getId());
        validateUpdatedWorkflowAction(getWorkflowResponse2, actionId);
    }

    @Test
    void shouldUpdateWorkflowConditionSync() {
        final CreateWorkflowResponse createWorkflowResponse = createWorkflowSync();

        final GetWorkflowResponse getWorkflowResponse = checkoutApi.workflowsClient().getWorkflowSync(createWorkflowResponse.getId());
        assertNotNull(getWorkflowResponse);
        assertEquals(3, getWorkflowResponse.getConditions().size());

        final EventWorkflowConditionResponse eventConditionResponse = findEventCondition(getWorkflowResponse);
        assertNotNull(eventConditionResponse);

        final EventWorkflowConditionRequest updateEventCondition = createUpdateEventConditionRequest();

        checkoutApi.workflowsClient().updateWorkflowConditionSync(getWorkflowResponse.getId(), eventConditionResponse.getId(), updateEventCondition);

        final GetWorkflowResponse updatedWorkflow = checkoutApi.workflowsClient().getWorkflowSync(createWorkflowResponse.getId());
        validateUpdatedWorkflowCondition(updatedWorkflow);

        assertDoesNotThrow(() -> checkoutApi.workflowsClient().removeWorkflowConditionSync(getWorkflowResponse.getId(), eventConditionResponse.getId()));
    }

    @Test
    void shouldCreateAndTestWorkflowsSync() {
        final CreateWorkflowResponse createWorkflowResponse = createWorkflowSync();
        final EventTypesRequest eventTypesRequest = createEventTypesRequest();

        final EmptyResponse response = checkoutApi.workflowsClient().testWorkflowSync(createWorkflowResponse.getId(), eventTypesRequest);
        assertNotNull(response);
    }

    // Common methods
    private void validateWorkflowResponse(GetWorkflowResponse getWorkflowResponse) {
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

        assertNotNull(getWorkflowResponse.getLink(SELF));
    }

    private void validateWorkflowConditions(GetWorkflowResponse getWorkflowResponse) {
        assertNotNull(getWorkflowResponse.getConditions());
        assertEquals(3, getWorkflowResponse.getConditions().size());
        assertTrue(getWorkflowResponse.getConditions().get(0) instanceof EventWorkflowConditionResponse);
        assertEquals(events, ((EventWorkflowConditionResponse) getWorkflowResponse.getConditions().get(0)).getEvents());

        assertTrue(getWorkflowResponse.getConditions().get(1) instanceof EntityWorkflowConditionResponse);
        assertEquals(WORKFLOW_ENTITY_ID, ((EntityWorkflowConditionResponse) getWorkflowResponse.getConditions().get(1)).getEntities().get(0));
    }

    private void validateWorkflowsResponse(GetWorkflowsResponse getWorkflowsResponse) {
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

    private void validateBasicWorkflowsResponse(GetWorkflowsResponse getWorkflowsResponse) {
        assertNotNull(getWorkflowsResponse);
        assertNotNull(getWorkflowsResponse.getWorkflows());
        assertFalse(getWorkflowsResponse.getWorkflows().isEmpty());
    }

    private void validateUpdateWorkflowResponse(UpdateWorkflowResponse updateWorkflowResponse) {
        assertNotNull(updateWorkflowResponse);
        assertEquals("testing_2", updateWorkflowResponse.getName());
        assertFalse(updateWorkflowResponse.getActive());
    }

    private void validateUpdatedWorkflowAction(GetWorkflowResponse getWorkflowResponse, String actionId) {
        assertNotNull(getWorkflowResponse);
        assertNotNull(getWorkflowResponse.getActions());
        assertEquals(1, getWorkflowResponse.getActions().size());
        assertTrue(getWorkflowResponse.getActions().get(0) instanceof WebhookWorkflowActionResponse);

        final WebhookWorkflowActionResponse webhookWorkflowAction = (WebhookWorkflowActionResponse) getWorkflowResponse.getActions().get(0);
        assertEquals(actionId, webhookWorkflowAction.getId());
        assertNotNull(webhookWorkflowAction.getSignature());
        assertEquals(baseWorkflowActionRequest.getHeaders(), webhookWorkflowAction.getHeaders());
        assertEquals(baseWorkflowActionRequest.getUrl() + "/fake", webhookWorkflowAction.getUrl());
    }

    private void validateUpdatedWorkflowCondition(GetWorkflowResponse updatedWorkflow) {
        assertNotNull(updatedWorkflow);
        assertEquals(3, updatedWorkflow.getConditions().size());

        final EventWorkflowConditionResponse updatedEventConditionResponse = findEventCondition(updatedWorkflow);
        assertNotNull(updatedEventConditionResponse);
        assertNotNull(updatedEventConditionResponse.getEvents());
        assertEquals(new HashSet<>(GATEWAY_EVENT_TYPES), updatedEventConditionResponse.getEvents().get(GATEWAY));
    }

    private EventWorkflowConditionResponse findEventCondition(GetWorkflowResponse workflow) {
        return workflow.getConditions().stream()
                .filter(condition -> WorkflowConditionType.EVENT.equals(condition.getType()))
                .map(type -> (EventWorkflowConditionResponse) type)
                .findFirst().orElse(null);
    }

    private WebhookWorkflowActionRequest createWorkflowActionRequest() {
        return WebhookWorkflowActionRequest.builder()
                .signature(baseWorkflowActionRequest.getSignature())
                .headers(baseWorkflowActionRequest.getHeaders())
                .url(baseWorkflowActionRequest.getUrl() + "/fake")
                .build();
    }

    private UpdateWorkflowRequest createUpdateWorkflowRequest() {
        return UpdateWorkflowRequest.builder()
                .name("testing_2")
                .active(Boolean.FALSE)
                .build();
    }

    private EventWorkflowConditionRequest createUpdateEventConditionRequest() {
        final Map<String, Set<String>> events = new HashMap<>();
        events.put(GATEWAY, new HashSet<>(GATEWAY_EVENT_TYPES));
        return EventWorkflowConditionRequest.builder()
                .events(events)
                .build();
    }

    private EventTypesRequest createEventTypesRequest() {
        return EventTypesRequest.builder()
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
    }

    private CreateWorkflowRequest createWorkflowRequest() {
        return CreateWorkflowRequest.builder()
                .name(TESTING)
                .active(Boolean.TRUE)
                .actions(Collections.singletonList(baseWorkflowActionRequest))
                .conditions(Arrays.asList(baseEventWorkflowConditionRequest, baseEntityWorkflowConditionRequest, processingChannelWorkflowConditionRequest))
                .build();
    }

    private CreateWorkflowResponse createWorkflowSync() {
        final CreateWorkflowRequest request = createWorkflowRequest();

        final CreateWorkflowResponse createWorkflowResponse = checkoutApi.workflowsClient().createWorkflowSync(request);
        assertNotNull(createWorkflowResponse);
        assertNotNull(createWorkflowResponse.getId());
        assertNotNull(createWorkflowResponse.getLink(SELF));

        queueWorkflowCleanup(createWorkflowResponse.getId());

        return createWorkflowResponse;
    }

    protected CreateWorkflowResponse createWorkflow() {
        final CreateWorkflowRequest request = createWorkflowRequest();

        final CreateWorkflowResponse createWorkflowResponse = blocking(() -> checkoutApi.workflowsClient().createWorkflow(request));
        assertNotNull(createWorkflowResponse);
        assertNotNull(createWorkflowResponse.getId());
        assertNotNull(createWorkflowResponse.getLink(SELF));

        queueWorkflowCleanup(createWorkflowResponse.getId());

        return createWorkflowResponse;
    }

}
