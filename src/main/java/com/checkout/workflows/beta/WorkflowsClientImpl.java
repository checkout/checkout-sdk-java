package com.checkout.workflows.beta;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
import com.checkout.workflows.beta.actions.request.WorkflowActionRequest;
import com.checkout.workflows.beta.conditions.request.WorkflowConditionRequest;
import com.checkout.workflows.beta.events.EventTypesResponse;
import com.checkout.workflows.beta.events.GetEventResponse;
import com.checkout.workflows.beta.events.SubjectEventsResponse;
import com.checkout.workflows.beta.reflow.ReflowRequest;
import com.checkout.workflows.beta.reflow.ReflowResponse;
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

    private static final Type EVENT_TYPES_RESPONSE = new TypeToken<List<EventTypesResponse>>() {
    }.getType();

    public WorkflowsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, SecretKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<CreateWorkflowResponse> createWorkflow(final CreateWorkflowRequest createWorkflowRequest) {
        validateParams("createWorkflowRequest", createWorkflowRequest);
        return apiClient.postAsync(WORKFLOWS, apiCredentials, CreateWorkflowResponse.class, createWorkflowRequest, null);
    }

    @Override
    public CompletableFuture<GetWorkflowsResponse> getWorkflows() {
        return apiClient.getAsync(WORKFLOWS, apiCredentials, GetWorkflowsResponse.class);
    }

    @Override
    public CompletableFuture<GetWorkflowResponse> getWorkflow(final String workflowId) {
        validateParams("workflowId", workflowId);
        return apiClient.getAsync(buildPath(WORKFLOWS, workflowId), apiCredentials, GetWorkflowResponse.class);
    }

    @Override
    public CompletableFuture<UpdateWorkflowResponse> updateWorkflow(final String workflowId, final UpdateWorkflowRequest updateWorkflowRequest) {
        validateParams("workflowId", workflowId, "updateWorkflowRequest", updateWorkflowRequest);
        return apiClient.patchAsync(buildPath(WORKFLOWS, workflowId), apiCredentials, UpdateWorkflowResponse.class, updateWorkflowRequest, null);
    }

    @Override
    public CompletableFuture<Void> removeWorkflow(final String workflowId) {
        validateParams("workflowId", workflowId);
        return apiClient.deleteAsync(buildPath(WORKFLOWS, workflowId), apiCredentials);
    }

    @Override
    public CompletableFuture<Void> updateWorkflowAction(final String workflowId, final String actionId, final WorkflowActionRequest workflowActionRequest) {
        validateParams("workflowId", workflowId, "actionId", actionId, "workflowActionRequest", workflowActionRequest);
        return apiClient.putAsync(buildPath(WORKFLOWS, workflowId, ACTIONS, actionId), apiCredentials, Void.class, workflowActionRequest);
    }

    @Override
    public CompletableFuture<Void> updateWorkflowCondition(final String workflowId, final String conditionId, final WorkflowConditionRequest workflowConditionRequest) {
        validateParams("workflowId", workflowId, "conditionId", conditionId, "workflowConditionRequest", workflowConditionRequest);
        return apiClient.putAsync(buildPath(WORKFLOWS, workflowId, CONDITIONS, conditionId), apiCredentials, Void.class, workflowConditionRequest);
    }

    @Override
    public CompletableFuture<List<EventTypesResponse>> getEventTypes() {
        return apiClient.getAsync(buildPath(WORKFLOWS, EVENT_TYPES), apiCredentials, EVENT_TYPES_RESPONSE);
    }

    @Override
    public CompletableFuture<SubjectEventsResponse> getSubjectEvents(final String subjectId) {
        validateParams("subjectId", subjectId);
        return apiClient.getAsync(buildPath(WORKFLOWS, EVENTS, SUBJECT, subjectId), apiCredentials, SubjectEventsResponse.class);
    }

    @Override
    public CompletableFuture<GetEventResponse> getEvent(final String eventId) {
        validateParams("eventId", eventId);
        return apiClient.getAsync(buildPath(WORKFLOWS, EVENTS, eventId), apiCredentials, GetEventResponse.class);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowByEvent(final String eventId) {
        validateParams("eventId", eventId);
        return apiClient.postAsync(buildPath(WORKFLOWS, EVENTS, eventId, REFLOW), apiCredentials, ReflowResponse.class, null, null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowBySubject(final String subjectId) {
        validateParams("subjectId", subjectId);
        return apiClient.postAsync(buildPath(WORKFLOWS, EVENTS, SUBJECT, subjectId, REFLOW), apiCredentials, ReflowResponse.class, null, null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowByEventAndWorkflow(final String eventId, final String workflowId) {
        validateParams("eventId", eventId, "workflowId", workflowId);
        return apiClient.postAsync(buildPath(WORKFLOWS, EVENTS, eventId, WORKFLOW, workflowId, REFLOW), apiCredentials, ReflowResponse.class, null, null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowBySubjectAndWorkflow(final String subjectId, final String workflowId) {
        validateParams("subjectId", subjectId, "workflowId", workflowId);
        return apiClient.postAsync(buildPath(WORKFLOWS, EVENTS, SUBJECT, subjectId, WORKFLOW, workflowId, REFLOW), apiCredentials, ReflowResponse.class, null, null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflow(final ReflowRequest reflowRequest) {
        validateParams("reflowRequest", reflowRequest);
        return apiClient.postAsync(buildPath(WORKFLOWS, EVENTS, REFLOW), apiCredentials, ReflowResponse.class, reflowRequest, null);
    }

}
