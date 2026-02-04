package com.checkout.workflows;

import static com.checkout.common.CheckoutUtils.validateParams;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorizationType;
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
import com.google.gson.reflect.TypeToken;

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
    private static final String TEST_PATH = "test";

    private static final Type WORKFLOWS_EVENT_TYPES_TYPE = new TypeToken<ItemsResponse<WorkflowEventTypes>>() {
    }.getType();

    public WorkflowsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<GetWorkflowsResponse> getWorkflows() {
        return apiClient.getAsync(
                WORKFLOWS_PATH,
                sdkAuthorization(),
                GetWorkflowsResponse.class);
    }

    @Override
    public CompletableFuture<CreateWorkflowResponse> createWorkflow(final CreateWorkflowRequest createWorkflowRequest) {
        validateCreateWorkflowRequest(createWorkflowRequest);
        return apiClient.postAsync(
                WORKFLOWS_PATH,
                sdkAuthorization(),
                CreateWorkflowResponse.class,
                createWorkflowRequest,
                null);
    }

    @Override
    public CompletableFuture<GetWorkflowResponse> getWorkflow(final String workflowId) {
        validateWorkflowId(workflowId);
        return apiClient.getAsync(buildPath(WORKFLOWS_PATH, workflowId), sdkAuthorization(), GetWorkflowResponse.class);
    }

    @Override
    public CompletableFuture<EmptyResponse> removeWorkflow(final String workflowId) {
        validateWorkflowId(workflowId);
        return apiClient.deleteAsync(buildPath(WORKFLOWS_PATH, workflowId), sdkAuthorization());
    }

    @Override
    public CompletableFuture<UpdateWorkflowResponse> updateWorkflow(final String workflowId,
                                                                    final UpdateWorkflowRequest updateWorkflowRequest) {
        validateWorkflowIdAndUpdateRequest(workflowId, updateWorkflowRequest);
        return apiClient.patchAsync(
                buildPath(WORKFLOWS_PATH, workflowId),
                sdkAuthorization(),
                UpdateWorkflowResponse.class,
                updateWorkflowRequest,
                null);
    }

    @Override
    public CompletableFuture<IdResponse> addWorkflowAction(final String workflowId,
                                                           final WorkflowActionRequest workflowActionRequest) {
        validateWorkflowIdAndActionRequest(workflowId, workflowActionRequest);
        return apiClient.postAsync(
                buildPath(WORKFLOWS_PATH, workflowId, ACTIONS_PATH),
                sdkAuthorization(),
                IdResponse.class,
                workflowActionRequest,
                null
        );
    }

    @Override
    public CompletableFuture<EmptyResponse> updateWorkflowAction(final String workflowId,
                                                                 final String actionId,
                                                                 final WorkflowActionRequest workflowActionRequest) {
        validateWorkflowIdActionIdAndRequest(workflowId, actionId, workflowActionRequest);
        return apiClient.putAsync(
                buildPath(WORKFLOWS_PATH, workflowId, ACTIONS_PATH, actionId),
                sdkAuthorization(),
                EmptyResponse.class,
                workflowActionRequest);
    }

    @Override
    public CompletableFuture<EmptyResponse> removeWorkflowAction(final String workflowId,
                                                                 final String actionId) {
        validateWorkflowIdAndActionId(workflowId, actionId);
        return apiClient.deleteAsync(
                buildPath(WORKFLOWS_PATH, workflowId, ACTIONS_PATH, actionId),
                sdkAuthorization()
        );
    }

    @Override
    public CompletableFuture<IdResponse> addWorkflowCondition(final String workflowId,
                                                              final WorkflowConditionRequest workflowConditionRequest) {
        validateWorkflowIdAndConditionRequest(workflowId, workflowConditionRequest);
        return apiClient.postAsync(
                buildPath(WORKFLOWS_PATH, workflowId, CONDITIONS_PATH),
                sdkAuthorization(),
                IdResponse.class,
                workflowConditionRequest,
                null);
    }

    @Override
    public CompletableFuture<EmptyResponse> updateWorkflowCondition(final String workflowId,
                                                                    final String conditionId,
                                                                    final WorkflowConditionRequest workflowConditionRequest) {
        validateWorkflowIdConditionIdAndRequest(workflowId, conditionId, workflowConditionRequest);
        return apiClient.putAsync(
                buildPath(WORKFLOWS_PATH, workflowId, CONDITIONS_PATH, conditionId),
                sdkAuthorization(),
                EmptyResponse.class,
                workflowConditionRequest);
    }

    @Override
    public CompletableFuture<EmptyResponse> removeWorkflowCondition(final String workflowId, final String conditionId) {
        validateWorkflowIdAndConditionId(workflowId, conditionId);
        return apiClient.deleteAsync(
                buildPath(WORKFLOWS_PATH, workflowId, CONDITIONS_PATH, conditionId),
                sdkAuthorization());
    }

    @Override
    public CompletableFuture<EmptyResponse> testWorkflow(String workflowId, EventTypesRequest eventTypesRequest) {
        validateWorkflowIdAndEventTypesRequest(workflowId, eventTypesRequest);
        return apiClient.postAsync(
                buildPath(WORKFLOWS_PATH, workflowId, TEST_PATH),
                sdkAuthorization(),
                EmptyResponse.class,
                eventTypesRequest,
                null);
    }

    @Override
    public CompletableFuture<ItemsResponse<WorkflowEventTypes>> getEventTypes() {
        return apiClient.getAsync(
                buildPath(WORKFLOWS_PATH, EVENT_TYPES_PATH),
                sdkAuthorization(),
                WORKFLOWS_EVENT_TYPES_TYPE);
    }

    @Override
    public CompletableFuture<GetEventResponse> getEvent(final String eventId) {
        validateEventId(eventId);
        return apiClient.getAsync(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, eventId),
                sdkAuthorization(),
                GetEventResponse.class);
    }

    @Override
    public CompletableFuture<WorkflowActionInvocationsResponse> getActionInvocations(final String eventId, final String actionId) {
        validateEventIdAndActionId(eventId, actionId);
        return apiClient.getAsync(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, eventId, ACTIONS_PATH, actionId),
                sdkAuthorization(),
                WorkflowActionInvocationsResponse.class);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowByEvent(final String eventId) {
        validateEventId(eventId);
        return apiClient.postAsync(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, eventId, REFLOW_PATH),
                sdkAuthorization(),
                ReflowResponse.class,
                null,
                null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowByEventAndWorkflow(final String eventId,
                                                                      final String workflowId) {
        validateEventIdAndWorkflowId(eventId, workflowId);
        return apiClient.postAsync(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, eventId, WORKFLOW_PATH, workflowId, REFLOW_PATH),
                sdkAuthorization(),
                ReflowResponse.class,
                null,
                null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflow(final ReflowRequest reflowRequest) {
        validateReflowRequest(reflowRequest);
        return apiClient.postAsync(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, REFLOW_PATH),
                sdkAuthorization(),
                ReflowResponse.class,
                reflowRequest,
                null);
    }

    @Override
    public CompletableFuture<SubjectEventsResponse> getSubjectEvents(final String subjectId) {
        validateSubjectId(subjectId);
        return apiClient.getAsync(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, SUBJECT_PATH, subjectId),
                sdkAuthorization(),
                SubjectEventsResponse.class);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowBySubject(final String subjectId) {
        validateSubjectId(subjectId);
        return apiClient.postAsync(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, SUBJECT_PATH, subjectId, REFLOW_PATH),
                sdkAuthorization(),
                ReflowResponse.class,
                null,
                null);
    }

    @Override
    public CompletableFuture<ReflowResponse> reflowBySubjectAndWorkflow(final String subjectId,
                                                                        final String workflowId) {
        validateSubjectIdAndWorkflowId(subjectId, workflowId);
        return apiClient.postAsync(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, SUBJECT_PATH, subjectId, WORKFLOW_PATH, workflowId, REFLOW_PATH),
                sdkAuthorization(),
                ReflowResponse.class,
                null,
                null);
    }

    // Synchronous methods
    @Override
    public GetWorkflowsResponse getWorkflowsSync() {
        return apiClient.get(
                WORKFLOWS_PATH,
                sdkAuthorization(),
                GetWorkflowsResponse.class);
    }

    @Override
    public CreateWorkflowResponse createWorkflowSync(final CreateWorkflowRequest createWorkflowRequest) {
        validateCreateWorkflowRequest(createWorkflowRequest);
        return apiClient.post(
                WORKFLOWS_PATH,
                sdkAuthorization(),
                CreateWorkflowResponse.class,
                createWorkflowRequest,
                null);
    }

    @Override
    public GetWorkflowResponse getWorkflowSync(final String workflowId) {
        validateWorkflowId(workflowId);
        return apiClient.get(buildPath(WORKFLOWS_PATH, workflowId), sdkAuthorization(), GetWorkflowResponse.class);
    }

    @Override
    public EmptyResponse removeWorkflowSync(final String workflowId) {
        validateWorkflowId(workflowId);
        return apiClient.delete(buildPath(WORKFLOWS_PATH, workflowId), sdkAuthorization());
    }

    @Override
    public UpdateWorkflowResponse updateWorkflowSync(final String workflowId,
                                                     final UpdateWorkflowRequest updateWorkflowRequest) {
        validateWorkflowIdAndUpdateRequest(workflowId, updateWorkflowRequest);
        return apiClient.patch(
                buildPath(WORKFLOWS_PATH, workflowId),
                sdkAuthorization(),
                UpdateWorkflowResponse.class,
                updateWorkflowRequest,
                null);
    }

    @Override
    public IdResponse addWorkflowActionSync(final String workflowId,
                                            final WorkflowActionRequest workflowActionRequest) {
        validateWorkflowIdAndActionRequest(workflowId, workflowActionRequest);
        return apiClient.post(
                buildPath(WORKFLOWS_PATH, workflowId, ACTIONS_PATH),
                sdkAuthorization(),
                IdResponse.class,
                workflowActionRequest,
                null
        );
    }

    @Override
    public EmptyResponse updateWorkflowActionSync(final String workflowId,
                                                  final String actionId,
                                                  final WorkflowActionRequest workflowActionRequest) {
        validateWorkflowIdActionIdAndRequest(workflowId, actionId, workflowActionRequest);
        return apiClient.put(
                buildPath(WORKFLOWS_PATH, workflowId, ACTIONS_PATH, actionId),
                sdkAuthorization(),
                EmptyResponse.class,
                workflowActionRequest);
    }

    @Override
    public EmptyResponse removeWorkflowActionSync(final String workflowId,
                                                  final String actionId) {
        validateWorkflowIdAndActionId(workflowId, actionId);
        return apiClient.delete(
                buildPath(WORKFLOWS_PATH, workflowId, ACTIONS_PATH, actionId),
                sdkAuthorization()
        );
    }

    @Override
    public IdResponse addWorkflowConditionSync(final String workflowId,
                                               final WorkflowConditionRequest workflowConditionRequest) {
        validateWorkflowIdAndConditionRequest(workflowId, workflowConditionRequest);
        return apiClient.post(
                buildPath(WORKFLOWS_PATH, workflowId, CONDITIONS_PATH),
                sdkAuthorization(),
                IdResponse.class,
                workflowConditionRequest,
                null);
    }

    @Override
    public EmptyResponse updateWorkflowConditionSync(final String workflowId,
                                                     final String conditionId,
                                                     final WorkflowConditionRequest workflowConditionRequest) {
        validateWorkflowIdConditionIdAndRequest(workflowId, conditionId, workflowConditionRequest);
        return apiClient.put(
                buildPath(WORKFLOWS_PATH, workflowId, CONDITIONS_PATH, conditionId),
                sdkAuthorization(),
                EmptyResponse.class,
                workflowConditionRequest);
    }

    @Override
    public EmptyResponse removeWorkflowConditionSync(final String workflowId, final String conditionId) {
        validateWorkflowIdAndConditionId(workflowId, conditionId);
        return apiClient.delete(
                buildPath(WORKFLOWS_PATH, workflowId, CONDITIONS_PATH, conditionId),
                sdkAuthorization());
    }

    @Override
    public EmptyResponse testWorkflowSync(String workflowId, EventTypesRequest eventTypesRequest) {
        validateWorkflowIdAndEventTypesRequest(workflowId, eventTypesRequest);
        return apiClient.post(
                buildPath(WORKFLOWS_PATH, workflowId, TEST_PATH),
                sdkAuthorization(),
                EmptyResponse.class,
                eventTypesRequest,
                null);
    }

    @Override
    public ItemsResponse<WorkflowEventTypes> getEventTypesSync() {
        return apiClient.get(
                buildPath(WORKFLOWS_PATH, EVENT_TYPES_PATH),
                sdkAuthorization(),
                WORKFLOWS_EVENT_TYPES_TYPE);
    }

    @Override
    public GetEventResponse getEventSync(final String eventId) {
        validateEventId(eventId);
        return apiClient.get(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, eventId),
                sdkAuthorization(),
                GetEventResponse.class);
    }

    @Override
    public WorkflowActionInvocationsResponse getActionInvocationsSync(final String eventId, final String actionId) {
        validateEventIdAndActionId(eventId, actionId);
        return apiClient.get(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, eventId, ACTIONS_PATH, actionId),
                sdkAuthorization(),
                WorkflowActionInvocationsResponse.class);
    }

    @Override
    public ReflowResponse reflowByEventSync(final String eventId) {
        validateEventId(eventId);
        return apiClient.post(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, eventId, REFLOW_PATH),
                sdkAuthorization(),
                ReflowResponse.class,
                null,
                null);
    }

    @Override
    public ReflowResponse reflowByEventAndWorkflowSync(final String eventId,
                                                       final String workflowId) {
        validateEventIdAndWorkflowId(eventId, workflowId);
        return apiClient.post(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, eventId, WORKFLOW_PATH, workflowId, REFLOW_PATH),
                sdkAuthorization(),
                ReflowResponse.class,
                null,
                null);
    }

    @Override
    public ReflowResponse reflowSync(final ReflowRequest reflowRequest) {
        validateReflowRequest(reflowRequest);
        return apiClient.post(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, REFLOW_PATH),
                sdkAuthorization(),
                ReflowResponse.class,
                reflowRequest,
                null);
    }

    @Override
    public SubjectEventsResponse getSubjectEventsSync(final String subjectId) {
        validateSubjectId(subjectId);
        return apiClient.get(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, SUBJECT_PATH, subjectId),
                sdkAuthorization(),
                SubjectEventsResponse.class);
    }

    @Override
    public ReflowResponse reflowBySubjectSync(final String subjectId) {
        validateSubjectId(subjectId);
        return apiClient.post(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, SUBJECT_PATH, subjectId, REFLOW_PATH),
                sdkAuthorization(),
                ReflowResponse.class,
                null,
                null);
    }

    @Override
    public ReflowResponse reflowBySubjectAndWorkflowSync(final String subjectId,
                                                         final String workflowId) {
        validateSubjectIdAndWorkflowId(subjectId, workflowId);
        return apiClient.post(
                buildPath(WORKFLOWS_PATH, EVENTS_PATH, SUBJECT_PATH, subjectId, WORKFLOW_PATH, workflowId, REFLOW_PATH),
                sdkAuthorization(),
                ReflowResponse.class,
                null,
                null);
    }

    // Common methods
    protected void validateCreateWorkflowRequest(final CreateWorkflowRequest createWorkflowRequest) {
        validateParams("createWorkflowRequest", createWorkflowRequest);
    }

    protected void validateWorkflowId(final String workflowId) {
        validateParams(WORKFLOW_ID, workflowId);
    }

    protected void validateWorkflowIdAndUpdateRequest(final String workflowId, final UpdateWorkflowRequest updateWorkflowRequest) {
        validateParams(WORKFLOW_ID, workflowId, "updateWorkflowRequest", updateWorkflowRequest);
    }

    protected void validateWorkflowIdAndActionRequest(final String workflowId, final WorkflowActionRequest workflowActionRequest) {
        validateParams(WORKFLOW_ID, workflowId, "workflowActionRequest", workflowActionRequest);
    }

    protected void validateWorkflowIdAndActionId(final String workflowId, final String actionId) {
        validateParams(WORKFLOW_ID, workflowId, "actionId", actionId);
    }

    protected void validateWorkflowIdActionIdAndRequest(final String workflowId, final String actionId, final WorkflowActionRequest workflowActionRequest) {
        validateParams(WORKFLOW_ID, workflowId, "actionId", actionId, "workflowActionRequest", workflowActionRequest);
    }

    protected void validateWorkflowIdAndConditionRequest(final String workflowId, final WorkflowConditionRequest workflowConditionRequest) {
        validateParams(WORKFLOW_ID, workflowId, "workflowConditionRequest", workflowConditionRequest);
    }

    protected void validateWorkflowIdAndConditionId(final String workflowId, final String conditionId) {
        validateParams(WORKFLOW_ID, workflowId, "conditionId", conditionId);
    }

    protected void validateWorkflowIdConditionIdAndRequest(final String workflowId, final String conditionId, final WorkflowConditionRequest workflowConditionRequest) {
        validateParams(WORKFLOW_ID, workflowId, "conditionId", conditionId, "workflowConditionRequest", workflowConditionRequest);
    }

    protected void validateWorkflowIdAndEventTypesRequest(final String workflowId, final EventTypesRequest eventTypesRequest) {
        validateParams(WORKFLOW_ID, workflowId, "eventTypesRequest", eventTypesRequest);
    }

    protected void validateEventId(final String eventId) {
        validateParams("eventId", eventId);
    }

    protected void validateEventIdAndActionId(final String eventId, final String actionId) {
        validateParams("eventId", eventId, "actionId", actionId);
    }

    protected void validateEventIdAndWorkflowId(final String eventId, final String workflowId) {
        validateParams("eventId", eventId, WORKFLOW_ID, workflowId);
    }

    protected void validateReflowRequest(final ReflowRequest reflowRequest) {
        validateParams("reflowRequest", reflowRequest);
    }

    protected void validateSubjectId(final String subjectId) {
        validateParams("subjectId", subjectId);
    }

    protected void validateSubjectIdAndWorkflowId(final String subjectId, final String workflowId) {
        validateParams("subjectId", subjectId, WORKFLOW_ID, workflowId);
    }

}
