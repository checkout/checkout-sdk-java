package com.checkout.workflows.four;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.workflows.four.actions.request.WorkflowActionRequest;
import com.checkout.workflows.four.conditions.request.WorkflowConditionRequest;
import com.checkout.workflows.four.events.EventTypesResponse;
import com.checkout.workflows.four.events.GetEventResponse;
import com.checkout.workflows.four.events.SubjectEventsResponse;
import com.checkout.workflows.four.reflow.ReflowRequest;
import com.checkout.workflows.four.reflow.ReflowResponse;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class WorkflowsClientImpl extends AbstractClient implements WorkflowsClient {

    private static final String WORKFLOWS = "workflows";
    private static final String WORKFLOW = "workflow";
    private static final String ACTIONS = "actions";
    private static final String CONDITIONS = "conditions";
    private static final String EVENT_TYPES = "event-types";
    private static final String EVENTS = "events";
    private static final String REFLOW = "reflow";
    private static final String SUBJECT = "subject";
    private static final String WORKFLOW_ID = "workflowId";

    private static final Type EVENT_TYPES_RESPONSE = new TypeToken<List<EventTypesResponse>>() {
    }.getType();

    public WorkflowsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<CreateWorkflowResponse> createWorkflow(final CreateWorkflowRequest createWorkflowRequest) {
        validateParams("createWorkflowRequest", createWorkflowRequest);
        return apiClient.postAsync(WORKFLOWS, sdkAuthorization(), CreateWorkflowResponse.class, createWorkflowRequest, null);
    }

    @Override
    public CompletableFuture<GetWorkflowsResponse> getWorkflows() {
        return apiClient.getAsync(WORKFLOWS, sdkAuthorization(), GetWorkflowsResponse.class);
    }

    @Override
    public CompletableFuture<GetWorkflowResponse> getWorkflow(final String workflowId) {
        validateParams(WORKFLOW_ID, workflowId);
        return apiClient.getAsync(buildPath(WORKFLOWS, workflowId), sdkAuthorization(), GetWorkflowResponse.class);
    }

    @Override
    public CompletableFuture<UpdateWorkflowResponse> updateWorkflow(final String workflowId, final UpdateWorkflowRequest updateWorkflowRequest) {
        validateParams(WORKFLOW_ID, workflowId, "updateWorkflowRequest", updateWorkflowRequest);
        return apiClient.patchAsync(buildPath(WORKFLOWS, workflowId), sdkAuthorization(), UpdateWorkflowResponse.class, updateWorkflowRequest, null);
    }

    @Override
    public CompletableFuture<Void> removeWorkflow(final String workflowId) {
        validateParams(WORKFLOW_ID, workflowId);
        return apiClient.deleteAsync(buildPath(WORKFLOWS, workflowId), sdkAuthorization());
    }

    @Override
    public CompletableFuture<Void> updateWorkflowAction(final String workflowId, final String actionId, final WorkflowActionRequest workflowActionRequest) {
        validateParams(WORKFLOW_ID, workflowId, "actionId", actionId, "workflowActionRequest", workflowActionRequest);
        return apiClient.putAsync(buildPath(WORKFLOWS, workflowId, ACTIONS, actionId), sdkAuthorization(), Void.class, workflowActionRequest);
    }

    @Override
    public CompletableFuture<Void> updateWorkflowCondition(final String workflowId, final String conditionId, final WorkflowConditionRequest workflowConditionRequest) {
        validateParams(WORKFLOW_ID, workflowId, "conditionId", conditionId, "workflowConditionRequest", workflowConditionRequest);
        return apiClient.putAsync(buildPath(WORKFLOWS, workflowId, CONDITIONS, conditionId), sdkAuthorization(), Void.class, workflowConditionRequest);
    }

    @Override
    public CompletableFuture<List<EventTypesResponse>> getEventTypes() {
        return apiClient.getAsync(buildPath(WORKFLOWS, EVENT_TYPES), sdkAuthorization(), EVENT_TYPES_RESPONSE);
    }

    @Override
    public CompletableFuture<SubjectEventsResponse> getSubjectEvents(final String subjectId) {
        validateParams("subjectId", subjectId);
        return apiClient.getAsync(buildPath(WORKFLOWS, EVENTS, SUBJECT, subjectId), sdkAuthorization(), SubjectEventsResponse.class);
    }

    @Override
    public CompletableFuture<GetEventResponse> getEvent(final String eventId) {
        validateParams("eventId", eventId);
        return apiClient.getAsync(buildPath(WORKFLOWS, EVENTS, eventId), sdkAuthorization(), GetEventResponse.class);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowByEvent(final String eventId) {
        validateParams("eventId", eventId);
        return apiClient.postAsync(buildPath(WORKFLOWS, EVENTS, eventId, REFLOW), sdkAuthorization(), ReflowResponse.class, null, null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowBySubject(final String subjectId) {
        validateParams("subjectId", subjectId);
        return apiClient.postAsync(buildPath(WORKFLOWS, EVENTS, SUBJECT, subjectId, REFLOW), sdkAuthorization(), ReflowResponse.class, null, null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowByEventAndWorkflow(final String eventId, final String workflowId) {
        validateParams("eventId", eventId, WORKFLOW_ID, workflowId);
        return apiClient.postAsync(buildPath(WORKFLOWS, EVENTS, eventId, WORKFLOW, workflowId, REFLOW), sdkAuthorization(), ReflowResponse.class, null, null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowBySubjectAndWorkflow(final String subjectId, final String workflowId) {
        validateParams("subjectId", subjectId, WORKFLOW_ID, workflowId);
        return apiClient.postAsync(buildPath(WORKFLOWS, EVENTS, SUBJECT, subjectId, WORKFLOW, workflowId, REFLOW), sdkAuthorization(), ReflowResponse.class, null, null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflow(final ReflowRequest reflowRequest) {
        validateParams("reflowRequest", reflowRequest);
        return apiClient.postAsync(buildPath(WORKFLOWS, EVENTS, REFLOW), sdkAuthorization(), ReflowResponse.class, reflowRequest, null);
    }

}
