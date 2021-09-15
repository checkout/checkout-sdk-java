package com.checkout.workflows.four;

import com.checkout.workflows.four.actions.request.WorkflowActionRequest;
import com.checkout.workflows.four.conditions.request.WorkflowConditionRequest;
import com.checkout.workflows.four.events.EventTypesResponse;
import com.checkout.workflows.four.events.GetEventResponse;
import com.checkout.workflows.four.events.SubjectEventsResponse;
import com.checkout.workflows.four.reflow.ReflowRequest;
import com.checkout.workflows.four.reflow.ReflowResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface WorkflowsClient {

    CompletableFuture<CreateWorkflowResponse> createWorkflow(CreateWorkflowRequest createWorkflowRequest);

    CompletableFuture<GetWorkflowsResponse> getWorkflows();

    CompletableFuture<GetWorkflowResponse> getWorkflow(String workflowId);

    CompletableFuture<UpdateWorkflowResponse> updateWorkflow(String workflowId, UpdateWorkflowRequest updateWorkflowRequest);

    CompletableFuture<Void> removeWorkflow(String workflowId);

    CompletableFuture<Void> updateWorkflowAction(String workflowId, String actionId, WorkflowActionRequest workflowActionRequest);

    CompletableFuture<Void> updateWorkflowCondition(String workflowId, String conditionId, WorkflowConditionRequest workflowConditionRequest);

    CompletableFuture<List<EventTypesResponse>> getEventTypes();

    CompletableFuture<SubjectEventsResponse> getSubjectEvents(String subjectId);

    CompletableFuture<GetEventResponse> getEvent(String eventId);

    CompletableFuture<ReflowResponse> reflowByEvent(String eventId);

    CompletableFuture<ReflowResponse> reflowBySubject(String subjectId);

    CompletableFuture<ReflowResponse> reflowByEventAndWorkflow(String eventId, String workflowId);

    CompletableFuture<ReflowResponse> reflowBySubjectAndWorkflow(String subjectId, String workflowId);

    CompletableFuture<ReflowResponse> reflow(ReflowRequest reflowRequest);

}
