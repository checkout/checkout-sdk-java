package com.checkout.workflows;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorizationType;
import com.checkout.workflows.actions.request.WorkflowActionRequest;
import com.checkout.workflows.actions.response.WorkflowActionInvocationsResponse;
import com.checkout.workflows.conditions.request.WorkflowConditionRequest;
import com.checkout.workflows.events.GetEventResponse;
import com.checkout.workflows.events.SubjectEventsResponse;
import com.checkout.workflows.events.WorkflowEventTypes;
import com.checkout.workflows.reflow.ReflowRequest;
import com.checkout.workflows.reflow.ReflowResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class WorkflowsClientImpl extends AbstractClient implements WorkflowsClient {

    private static final String WORKFLOWS_PATH = "workflows";
    private static final String WORKFLOW_PATH = "workflow";
    private static final String ACTIONS_PATH = "actions";
    private static final String CONDITIONS_PATH = "conditions";
    private static final String EVENT_TYPES_PATH = "event-types";
    private static final String EVENTS_PATH = "events";
    private static final String REFLOW_PATH = "reflow";
    private static final String SUBJECT_PATH = "subject";
    private static final String WORKFLOW_ID = "workflowId";

    private static final Type WORKFLOWS_EVENT_TYPES_TYPE = new TypeToken<ItemsResponse<WorkflowEventTypes>>() {
    }.getType();

    public WorkflowsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<CreateWorkflowResponse> createWorkflow(final CreateWorkflowRequest createWorkflowRequest) {
        validateParams("createWorkflowRequest", createWorkflowRequest);
        return apiClient.postAsync(WORKFLOWS_PATH, sdkAuthorization(), CreateWorkflowResponse.class, createWorkflowRequest, null);
    }

    @Override
    public CompletableFuture<GetWorkflowsResponse> getWorkflows() {
        return apiClient.getAsync(WORKFLOWS_PATH, sdkAuthorization(), GetWorkflowsResponse.class);
    }

    @Override
    public CompletableFuture<GetWorkflowResponse> getWorkflow(final String workflowId) {
        validateParams(WORKFLOW_ID, workflowId);
        return apiClient.getAsync(buildPath(WORKFLOWS_PATH, workflowId), sdkAuthorization(), GetWorkflowResponse.class);
    }

    @Override
    public CompletableFuture<UpdateWorkflowResponse> updateWorkflow(final String workflowId, final UpdateWorkflowRequest updateWorkflowRequest) {
        validateParams(WORKFLOW_ID, workflowId, "updateWorkflowRequest", updateWorkflowRequest);
        return apiClient.patchAsync(buildPath(WORKFLOWS_PATH, workflowId), sdkAuthorization(), UpdateWorkflowResponse.class, updateWorkflowRequest, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> removeWorkflow(final String workflowId) {
        validateParams(WORKFLOW_ID, workflowId);
        return apiClient.deleteAsync(buildPath(WORKFLOWS_PATH, workflowId), sdkAuthorization());
    }

    @Override
    public CompletableFuture<EmptyResponse> updateWorkflowAction(final String workflowId, final String actionId, final WorkflowActionRequest workflowActionRequest) {
        validateParams(WORKFLOW_ID, workflowId, "actionId", actionId, "workflowActionRequest", workflowActionRequest);
        return apiClient.putAsync(buildPath(WORKFLOWS_PATH, workflowId, ACTIONS_PATH, actionId), sdkAuthorization(), EmptyResponse.class, workflowActionRequest);
    }

    @Override
    public CompletableFuture<EmptyResponse> updateWorkflowCondition(final String workflowId, final String conditionId, final WorkflowConditionRequest workflowConditionRequest) {
        validateParams(WORKFLOW_ID, workflowId, "conditionId", conditionId, "workflowConditionRequest", workflowConditionRequest);
        return apiClient.putAsync(buildPath(WORKFLOWS_PATH, workflowId, CONDITIONS_PATH, conditionId), sdkAuthorization(), EmptyResponse.class, workflowConditionRequest);
    }

    @Override
    public CompletableFuture<ItemsResponse<WorkflowEventTypes>> getEventTypes() {
        return apiClient.getAsync(buildPath(WORKFLOWS_PATH, EVENT_TYPES_PATH), sdkAuthorization(), WORKFLOWS_EVENT_TYPES_TYPE);
    }

    @Override
    public CompletableFuture<SubjectEventsResponse> getSubjectEvents(final String subjectId) {
        validateParams("subjectId", subjectId);
        return apiClient.getAsync(buildPath(WORKFLOWS_PATH, EVENTS_PATH, SUBJECT_PATH, subjectId), sdkAuthorization(), SubjectEventsResponse.class);
    }

    @Override
    public CompletableFuture<GetEventResponse> getEvent(final String eventId) {
        validateParams("eventId", eventId);
        return apiClient.getAsync(buildPath(WORKFLOWS_PATH, EVENTS_PATH, eventId), sdkAuthorization(), GetEventResponse.class);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowByEvent(final String eventId) {
        validateParams("eventId", eventId);
        return apiClient.postAsync(buildPath(WORKFLOWS_PATH, EVENTS_PATH, eventId, REFLOW_PATH), sdkAuthorization(), ReflowResponse.class, null, null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowBySubject(final String subjectId) {
        validateParams("subjectId", subjectId);
        return apiClient.postAsync(buildPath(WORKFLOWS_PATH, EVENTS_PATH, SUBJECT_PATH, subjectId, REFLOW_PATH), sdkAuthorization(), ReflowResponse.class, null, null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowByEventAndWorkflow(final String eventId, final String workflowId) {
        validateParams("eventId", eventId, WORKFLOW_ID, workflowId);
        return apiClient.postAsync(buildPath(WORKFLOWS_PATH, EVENTS_PATH, eventId, WORKFLOW_PATH, workflowId, REFLOW_PATH), sdkAuthorization(), ReflowResponse.class, null, null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowBySubjectAndWorkflow(final String subjectId, final String workflowId) {
        validateParams("subjectId", subjectId, WORKFLOW_ID, workflowId);
        return apiClient.postAsync(buildPath(WORKFLOWS_PATH, EVENTS_PATH, SUBJECT_PATH, subjectId, WORKFLOW_PATH, workflowId, REFLOW_PATH), sdkAuthorization(), ReflowResponse.class, null, null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflow(final ReflowRequest reflowRequest) {
        validateParams("reflowRequest", reflowRequest);
        return apiClient.postAsync(buildPath(WORKFLOWS_PATH, EVENTS_PATH, REFLOW_PATH), sdkAuthorization(), ReflowResponse.class, reflowRequest, null);
    }

    @Override
    public CompletableFuture<WorkflowActionInvocationsResponse> getActionInvocations(final String eventId, final String actionId) {
        validateParams("eventId", eventId, "actionId", actionId);
        return apiClient.getAsync(buildPath(WORKFLOWS_PATH, EVENTS_PATH, eventId, ACTIONS_PATH, actionId), sdkAuthorization(), WorkflowActionInvocationsResponse.class);
    }
}
