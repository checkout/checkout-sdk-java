package com.checkout.workflows;

import java.util.concurrent.CompletableFuture;

import com.checkout.EmptyResponse;
import com.checkout.ItemsResponse;
import com.checkout.common.IdResponse;
import com.checkout.workflows.actions.request.WorkflowActionRequest;
import com.checkout.workflows.actions.response.WorkflowActionInvocationsResponse;
import com.checkout.workflows.conditions.request.WorkflowConditionRequest;
import com.checkout.workflows.events.EventTypesRequest;
import com.checkout.workflows.events.GetEventResponse;
import com.checkout.workflows.events.SubjectEventsResponse;
import com.checkout.workflows.events.WorkflowEventTypes;
import com.checkout.workflows.reflow.ReflowRequest;
import com.checkout.workflows.reflow.ReflowResponse;

public interface WorkflowsClient {

    CompletableFuture<GetWorkflowsResponse> getWorkflows();

    CompletableFuture<CreateWorkflowResponse> createWorkflow(CreateWorkflowRequest createWorkflowRequest);

    CompletableFuture<GetWorkflowResponse> getWorkflow(String workflowId);

    CompletableFuture<EmptyResponse> removeWorkflow(String workflowId);

    CompletableFuture<UpdateWorkflowResponse> updateWorkflow(String workflowId, UpdateWorkflowRequest updateWorkflowRequest);

    CompletableFuture<IdResponse> addWorkflowAction(String workflowId, WorkflowActionRequest workflowActionRequest);

    CompletableFuture<EmptyResponse> updateWorkflowAction(String workflowId, String actionId, WorkflowActionRequest workflowActionRequest);

    CompletableFuture<EmptyResponse> removeWorkflowAction(String workflowId, String actionId);

    CompletableFuture<IdResponse> addWorkflowCondition(String workflowId, WorkflowConditionRequest workflowConditionRequest);

    CompletableFuture<EmptyResponse> updateWorkflowCondition(String workflowId, String conditionId, WorkflowConditionRequest workflowConditionRequest);

    CompletableFuture<EmptyResponse> removeWorkflowCondition(String workflowId, String conditionId);

    CompletableFuture<EmptyResponse> testWorkflow(String workflowId, EventTypesRequest eventTypesRequest);

    CompletableFuture<ItemsResponse<WorkflowEventTypes>> getEventTypes();

    CompletableFuture<GetEventResponse> getEvent(String eventId);

    CompletableFuture<WorkflowActionInvocationsResponse> getActionInvocations(String eventId, String actionId);

    CompletableFuture<ReflowResponse> reflowByEvent(String eventId);

    CompletableFuture<ReflowResponse> reflowByEventAndWorkflow(String eventId, String workflowId);

    CompletableFuture<ReflowResponse> reflow(ReflowRequest reflowRequest);

    CompletableFuture<SubjectEventsResponse> getSubjectEvents(String subjectId);

    CompletableFuture<ReflowResponse> reflowBySubject(String subjectId);

    CompletableFuture<ReflowResponse> reflowBySubjectAndWorkflow(String subjectId, String workflowId);

    // Synchronous methods
    GetWorkflowsResponse getWorkflowsSync();

    CreateWorkflowResponse createWorkflowSync(CreateWorkflowRequest createWorkflowRequest);

    GetWorkflowResponse getWorkflowSync(String workflowId);

    EmptyResponse removeWorkflowSync(String workflowId);

    UpdateWorkflowResponse updateWorkflowSync(String workflowId, UpdateWorkflowRequest updateWorkflowRequest);

    IdResponse addWorkflowActionSync(String workflowId, WorkflowActionRequest workflowActionRequest);

    EmptyResponse updateWorkflowActionSync(String workflowId, String actionId, WorkflowActionRequest workflowActionRequest);

    EmptyResponse removeWorkflowActionSync(String workflowId, String actionId);

    IdResponse addWorkflowConditionSync(String workflowId, WorkflowConditionRequest workflowConditionRequest);

    EmptyResponse updateWorkflowConditionSync(String workflowId, String conditionId, WorkflowConditionRequest workflowConditionRequest);

    EmptyResponse removeWorkflowConditionSync(String workflowId, String conditionId);

    EmptyResponse testWorkflowSync(String workflowId, EventTypesRequest eventTypesRequest);

    ItemsResponse<WorkflowEventTypes> getEventTypesSync();

    GetEventResponse getEventSync(String eventId);

    WorkflowActionInvocationsResponse getActionInvocationsSync(String eventId, String actionId);

    ReflowResponse reflowByEventSync(String eventId);

    ReflowResponse reflowByEventAndWorkflowSync(String eventId, String workflowId);

    ReflowResponse reflowSync(ReflowRequest reflowRequest);

    SubjectEventsResponse getSubjectEventsSync(String subjectId);

    ReflowResponse reflowBySubjectSync(String subjectId);

    ReflowResponse reflowBySubjectAndWorkflowSync(String subjectId, String workflowId);

}
