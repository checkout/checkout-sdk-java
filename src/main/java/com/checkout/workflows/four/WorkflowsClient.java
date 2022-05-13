package com.checkout.workflows.four;

import com.checkout.EmptyResponse;
import com.checkout.ItemsResponse;
import com.checkout.workflows.four.actions.request.WorkflowActionRequest;
import com.checkout.workflows.four.actions.response.WorkflowActionInvocationsResponse;
import com.checkout.workflows.four.conditions.request.WorkflowConditionRequest;
import com.checkout.workflows.four.events.GetEventResponse;
import com.checkout.workflows.four.events.SubjectEventsResponse;
import com.checkout.workflows.four.events.WorkflowEventTypes;
import com.checkout.workflows.four.reflow.ReflowRequest;
import com.checkout.workflows.four.reflow.ReflowResponse;

import java.util.concurrent.CompletableFuture;

public interface WorkflowsClient {

    CompletableFuture<CreateWorkflowResponse> createWorkflow(CreateWorkflowRequest createWorkflowRequest);

    CompletableFuture<GetWorkflowsResponse> getWorkflows();

    CompletableFuture<GetWorkflowResponse> getWorkflow(String workflowId);

    CompletableFuture<UpdateWorkflowResponse> updateWorkflow(String workflowId, UpdateWorkflowRequest updateWorkflowRequest);

    CompletableFuture<EmptyResponse> removeWorkflow(String workflowId);

    CompletableFuture<EmptyResponse> updateWorkflowAction(String workflowId, String actionId, WorkflowActionRequest workflowActionRequest);

    CompletableFuture<EmptyResponse> updateWorkflowCondition(String workflowId, String conditionId, WorkflowConditionRequest workflowConditionRequest);

    CompletableFuture<ItemsResponse<WorkflowEventTypes>> getEventTypes();

    CompletableFuture<SubjectEventsResponse> getSubjectEvents(String subjectId);

    CompletableFuture<GetEventResponse> getEvent(String eventId);

    CompletableFuture<ReflowResponse> reflowByEvent(String eventId);

    CompletableFuture<ReflowResponse> reflowBySubject(String subjectId);

    CompletableFuture<ReflowResponse> reflowByEventAndWorkflow(String eventId, String workflowId);

    CompletableFuture<ReflowResponse> reflowBySubjectAndWorkflow(String subjectId, String workflowId);

    CompletableFuture<ReflowResponse> reflow(ReflowRequest reflowRequest);

    CompletableFuture<WorkflowActionInvocationsResponse> getActionInvocations(String eventId, String actionId);

}
