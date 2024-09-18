package com.checkout.workflows;

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

import java.util.concurrent.CompletableFuture;

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

}
