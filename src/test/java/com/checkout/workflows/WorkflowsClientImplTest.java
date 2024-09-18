package com.checkout.workflows;

import com.checkout.ApiClient;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.IdResponse;
import com.checkout.workflows.actions.request.WorkflowActionRequest;
import com.checkout.workflows.actions.response.WorkflowActionInvocationsResponse;
import com.checkout.workflows.conditions.request.WorkflowConditionRequest;
import com.checkout.workflows.events.EventTypesRequest;
import com.checkout.workflows.events.GetEventResponse;
import com.checkout.workflows.events.SubjectEventsResponse;
import com.checkout.workflows.events.WorkflowEventTypes;
import com.checkout.workflows.reflow.ReflowBySubjectsRequest;
import com.checkout.workflows.reflow.ReflowResponse;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowsClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private WorkflowsClient workflowsClient;

    private static final Type WORKFLOWS_EVENT_TYPES_TYPE = new TypeToken<ItemsResponse<WorkflowEventTypes>>() {
    }.getType();

    @BeforeEach
    void setUp() {
        this.workflowsClient = new WorkflowsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldCreateWorkflow() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final CreateWorkflowRequest request = Mockito.mock(CreateWorkflowRequest.class);
        final CreateWorkflowResponse response = Mockito.mock(CreateWorkflowResponse.class);

        when(apiClient.postAsync(eq("workflows"), eq(authorization), eq(CreateWorkflowResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CreateWorkflowResponse> future = workflowsClient.createWorkflow(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldFailCreateWorkflow_invalidParams() {
        try {
            workflowsClient.createWorkflow(null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("createWorkflowRequest cannot be null", e.getMessage());
        }
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldGetWorkflows() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final GetWorkflowsResponse response = Mockito.mock(GetWorkflowsResponse.class);

        when(apiClient.getAsync("workflows", authorization, GetWorkflowsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<GetWorkflowsResponse> future = workflowsClient.getWorkflows();

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetWorkflowById() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final GetWorkflowResponse response = Mockito.mock(GetWorkflowResponse.class);

        when(apiClient.getAsync("workflows/workflow_id", authorization, GetWorkflowResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<GetWorkflowResponse> future = workflowsClient.getWorkflow("workflow_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldFailGetWorkflowById_invalidParams() {
        try {
            workflowsClient.getWorkflow("");
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("workflowId cannot be blank", e.getMessage());
        }
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldUpdateWorkflow() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final UpdateWorkflowRequest request = Mockito.mock(UpdateWorkflowRequest.class);
        final UpdateWorkflowResponse response = Mockito.mock(UpdateWorkflowResponse.class);

        when(apiClient.patchAsync(eq("workflows/workflow_id"), eq(authorization), eq(UpdateWorkflowResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<UpdateWorkflowResponse> future = workflowsClient.updateWorkflow("workflow_id", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldFailUpdateWorkflow_invalidParams() {
        try {
            workflowsClient.updateWorkflow("", null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("workflowId cannot be blank", e.getMessage());
        }
        try {
            workflowsClient.updateWorkflow("123", null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("updateWorkflowRequest cannot be null", e.getMessage());
        }
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRemoveWorkflow() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final EmptyResponse response = Mockito.mock(EmptyResponse.class);

        when(apiClient.deleteAsync("workflows/workflow_id", (authorization)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = workflowsClient.removeWorkflow("workflow_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldFailRemoveWorkflow_invalidParams() {
        try {
            workflowsClient.removeWorkflow("");
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("workflowId cannot be blank", e.getMessage());
        }
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldAddWorkflowAction() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final IdResponse response = Mockito.mock(IdResponse.class);
        final WorkflowActionRequest action = Mockito.mock(WorkflowActionRequest.class);

        when(apiClient.postAsync("workflows/workflow_id/actions", authorization, IdResponse.class, action, null))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdResponse> future = workflowsClient.addWorkflowAction("workflow_id", action);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldUpdateWorkflowAction() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final EmptyResponse response = Mockito.mock(EmptyResponse.class);
        final WorkflowActionRequest action = Mockito.mock(WorkflowActionRequest.class);

        when(apiClient.putAsync("workflows/workflow_id/actions/action_id", authorization, EmptyResponse.class, action))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = workflowsClient.updateWorkflowAction("workflow_id", "action_id", action);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRemoveWorkflowAction() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final EmptyResponse response = Mockito.mock(EmptyResponse.class);

        when(apiClient.deleteAsync("workflows/workflow_id/actions/action_id", (authorization)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = workflowsClient.removeWorkflowAction("workflow_id", "action_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldFailUpdateWorkflowAction_invalidParams() {
        try {
            workflowsClient.updateWorkflowAction("", "123", Mockito.mock(WorkflowActionRequest.class));
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("workflowId cannot be blank", e.getMessage());
        }
        try {
            workflowsClient.updateWorkflowAction("123", " ", Mockito.mock(WorkflowActionRequest.class));
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("actionId cannot be blank", e.getMessage());
        }
        try {
            workflowsClient.updateWorkflowAction("123", "456", null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("workflowActionRequest cannot be null", e.getMessage());
        }
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldAddWorkflowCondition() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final IdResponse response = Mockito.mock(IdResponse.class);
        final WorkflowConditionRequest condition = Mockito.mock(WorkflowConditionRequest.class);

        when(apiClient.postAsync("workflows/workflow_id/conditions", authorization, IdResponse.class, condition, null))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdResponse> future = workflowsClient.addWorkflowCondition("workflow_id", condition);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldUpdateWorkflowCondition() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final EmptyResponse response = Mockito.mock(EmptyResponse.class);
        final WorkflowConditionRequest condition = Mockito.mock(WorkflowConditionRequest.class);

        when(apiClient.putAsync("workflows/workflow_id/conditions/condition_id", authorization, EmptyResponse.class, condition))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = workflowsClient.updateWorkflowCondition("workflow_id", "condition_id", condition);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRemoveWorkflowCondition() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final EmptyResponse response = Mockito.mock(EmptyResponse.class);

        when(apiClient.deleteAsync("workflows/workflow_id/conditions/condition_id", (authorization)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = workflowsClient.removeWorkflowCondition("workflow_id", "condition_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldTestWorkflow() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final EmptyResponse response = Mockito.mock(EmptyResponse.class);
        final EventTypesRequest eventTypesRequest = Mockito.mock(EventTypesRequest.class);

        when(apiClient.postAsync("workflows/workflow_id/test", authorization, EmptyResponse.class, eventTypesRequest, null))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = workflowsClient.testWorkflow("workflow_id", eventTypesRequest);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldFailUpdateWorkflowCondition_invalidParams() {
        try {
            workflowsClient.updateWorkflowCondition("", "123", Mockito.mock(WorkflowConditionRequest.class));
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("workflowId cannot be blank", e.getMessage());
        }
        try {
            workflowsClient.updateWorkflowCondition("123", " ", Mockito.mock(WorkflowConditionRequest.class));
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("conditionId cannot be blank", e.getMessage());
        }
        try {
            workflowsClient.updateWorkflowCondition("123", "456", null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("workflowConditionRequest cannot be null", e.getMessage());
        }
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldGetEventTypes() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final ItemsResponse response = mock(ItemsResponse.class);

        when(apiClient.getAsync("workflows/event-types", authorization, WORKFLOWS_EVENT_TYPES_TYPE))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ItemsResponse<WorkflowEventTypes>> future = workflowsClient.getEventTypes();

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetSubjectEvents() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final SubjectEventsResponse response = Mockito.mock(SubjectEventsResponse.class);

        when(apiClient.getAsync("workflows/events/subject/subject_id", authorization, SubjectEventsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<SubjectEventsResponse> future = workflowsClient.getSubjectEvents("subject_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldFailGetSubjectEvents_invalidParams() {
        try {
            workflowsClient.getSubjectEvents("    ");
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("subjectId cannot be blank", e.getMessage());
        }
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldGetEvent() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final GetEventResponse response = Mockito.mock(GetEventResponse.class);

        when(apiClient.getAsync("workflows/events/event_id", authorization, GetEventResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<GetEventResponse> future = workflowsClient.getEvent("event_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldFailGetEvent_invalidParams() {
        try {
            workflowsClient.getEvent(null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("eventId cannot be null", e.getMessage());
        }
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldReflowByEvent() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final ReflowResponse response = Mockito.mock(ReflowResponse.class);

        when(apiClient.postAsync(eq("workflows/events/event_id/reflow"), eq(authorization), eq(ReflowResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReflowResponse> future = workflowsClient.reflowByEvent("event_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldFailReflowByEvent_invalidParams() {
        try {
            workflowsClient.reflowByEvent("");
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("eventId cannot be blank", e.getMessage());
        }
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldReflowBySubject() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final ReflowResponse response = Mockito.mock(ReflowResponse.class);

        when(apiClient.postAsync(eq("workflows/events/subject/subject_id/reflow"), eq(authorization), eq(ReflowResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReflowResponse> future = workflowsClient.reflowBySubject("subject_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldFailReflowBySubject_invalidParams() {
        try {
            workflowsClient.reflowBySubject(null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("subjectId cannot be null", e.getMessage());
        }
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldReflowByEventAndWorkflow() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final ReflowResponse response = Mockito.mock(ReflowResponse.class);

        when(apiClient.postAsync(eq("workflows/events/event_id/workflow/workflow_id/reflow"), eq(authorization), eq(ReflowResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReflowResponse> future = workflowsClient.reflowByEventAndWorkflow("event_id", "workflow_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldFailReflowByEventAndWorkflow_invalidParams() {
        try {
            workflowsClient.reflowByEventAndWorkflow(" ", "123");
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("eventId cannot be blank", e.getMessage());
        }
        try {
            workflowsClient.reflowByEventAndWorkflow("123", null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("workflowId cannot be null", e.getMessage());
        }
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldReflowBySubjectAndWorkflow() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final ReflowResponse response = Mockito.mock(ReflowResponse.class);

        when(apiClient.postAsync(eq("workflows/events/subject/subject_id/workflow/workflow_id/reflow"), eq(authorization), eq(ReflowResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReflowResponse> future = workflowsClient.reflowBySubjectAndWorkflow("subject_id", "workflow_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldFailReflowBySubjectAndWorkflow_invalidParams() {
        try {
            workflowsClient.reflowBySubjectAndWorkflow(" ", "123");
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("subjectId cannot be blank", e.getMessage());
        }
        try {
            workflowsClient.reflowBySubjectAndWorkflow("123", null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("workflowId cannot be null", e.getMessage());
        }
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldReflow() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final ReflowBySubjectsRequest request = Mockito.mock(ReflowBySubjectsRequest.class);
        final ReflowResponse response = Mockito.mock(ReflowResponse.class);

        when(apiClient.postAsync(eq("workflows/events/reflow"), eq(authorization), eq(ReflowResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReflowResponse> future = workflowsClient.reflow(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldFailReflow_invalidParams() {
        try {
            workflowsClient.reflow(null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("reflowRequest cannot be null", e.getMessage());
        }
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldGetActionInvocations() throws ExecutionException, InterruptedException {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final WorkflowActionInvocationsResponse response = mock(WorkflowActionInvocationsResponse.class);

        when(apiClient.getAsync("workflows/events/event_id/actions/action_id", authorization, WorkflowActionInvocationsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<WorkflowActionInvocationsResponse> future = workflowsClient.getActionInvocations("event_id", "action_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

}
