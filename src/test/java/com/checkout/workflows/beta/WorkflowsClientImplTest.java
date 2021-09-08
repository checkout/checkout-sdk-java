package com.checkout.workflows.beta;

import com.checkout.ApiClient;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
import com.checkout.workflows.beta.actions.request.WorkflowActionRequest;
import com.checkout.workflows.beta.conditions.request.WorkflowConditionRequest;
import com.checkout.workflows.beta.events.EventTypesResponse;
import com.checkout.workflows.beta.events.GetEventResponse;
import com.checkout.workflows.beta.events.SubjectEventsResponse;
import com.checkout.workflows.beta.reflow.ReflowBySubjectsRequest;
import com.checkout.workflows.beta.reflow.ReflowResponse;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkflowsClientImplTest {

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

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;


    private WorkflowsClient workflowsClient;

    @BeforeEach
    void setUp() {
        this.workflowsClient = new WorkflowsClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldCreateWorkflow() throws ExecutionException, InterruptedException {

        final CreateWorkflowRequest request = Mockito.mock(CreateWorkflowRequest.class);
        final CreateWorkflowResponse response = Mockito.mock(CreateWorkflowResponse.class);

        when(apiClient.postAsync(eq(WORKFLOWS), any(SecretKeyCredentials.class), eq(CreateWorkflowResponse.class), eq(request), isNull()))
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

        final GetWorkflowsResponse response = Mockito.mock(GetWorkflowsResponse.class);

        when(apiClient.getAsync(eq(WORKFLOWS), any(SecretKeyCredentials.class), eq(GetWorkflowsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<GetWorkflowsResponse> future = workflowsClient.getWorkflows();

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetWorkflowById() throws ExecutionException, InterruptedException {

        final GetWorkflowResponse response = Mockito.mock(GetWorkflowResponse.class);

        when(apiClient.getAsync(eq(WORKFLOWS + "/workflow_id"), any(SecretKeyCredentials.class), eq(GetWorkflowResponse.class)))
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

        final UpdateWorkflowRequest request = Mockito.mock(UpdateWorkflowRequest.class);
        final UpdateWorkflowResponse response = Mockito.mock(UpdateWorkflowResponse.class);

        when(apiClient.patchAsync(eq(WORKFLOWS + "/workflow_id"), any(SecretKeyCredentials.class), eq(UpdateWorkflowResponse.class), eq(request), isNull()))
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

        final Void response = Mockito.mock(Void.class);

        when(apiClient.deleteAsync(eq(WORKFLOWS + "/workflow_id"), any(SecretKeyCredentials.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<Void> future = workflowsClient.removeWorkflow("workflow_id");

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
    void shouldUpdateWorkflowAction() throws ExecutionException, InterruptedException {

        final Void response = Mockito.mock(Void.class);
        final WorkflowActionRequest action = Mockito.mock(WorkflowActionRequest.class);

        when(apiClient.putAsync(eq(WORKFLOWS + "/workflow_id/" + ACTIONS + "/action_id"), any(SecretKeyCredentials.class), eq(Void.class), eq(action)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<Void> future = workflowsClient.updateWorkflowAction("workflow_id", "action_id", action);

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
    void shouldUpdateWorkflowCondition() throws ExecutionException, InterruptedException {

        final Void response = Mockito.mock(Void.class);
        final WorkflowConditionRequest condition = Mockito.mock(WorkflowConditionRequest.class);

        when(apiClient.putAsync(eq(WORKFLOWS + "/workflow_id/" + CONDITIONS + "/condition_id"), any(SecretKeyCredentials.class), eq(Void.class), eq(condition)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<Void> future = workflowsClient.updateWorkflowCondition("workflow_id", "condition_id", condition);

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

        final List<EventTypesResponse> response = Mockito.mock(List.class);

        when(apiClient.getAsync(eq(WORKFLOWS + "/" + EVENT_TYPES), any(SecretKeyCredentials.class), eq(EVENT_TYPES_RESPONSE)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<List<EventTypesResponse>> future = workflowsClient.getEventTypes();

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetSubjectEvents() throws ExecutionException, InterruptedException {

        final SubjectEventsResponse response = Mockito.mock(SubjectEventsResponse.class);

        when(apiClient.getAsync(eq(WORKFLOWS + "/" + EVENTS + "/" + SUBJECT + "/subject_id"), any(SecretKeyCredentials.class), eq(SubjectEventsResponse.class)))
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

        final GetEventResponse response = Mockito.mock(GetEventResponse.class);

        when(apiClient.getAsync(eq(WORKFLOWS + "/" + EVENTS + "/event_id"), any(SecretKeyCredentials.class), eq(GetEventResponse.class)))
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

        final ReflowResponse response = Mockito.mock(ReflowResponse.class);

        when(apiClient.postAsync(eq(WORKFLOWS + "/" + EVENTS + "/event_id/" + REFLOW), any(SecretKeyCredentials.class), eq(ReflowResponse.class), isNull(), isNull()))
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

        final ReflowResponse response = Mockito.mock(ReflowResponse.class);

        when(apiClient.postAsync(eq(WORKFLOWS + "/" + EVENTS + "/" + SUBJECT + "/subject_id/" + REFLOW), any(SecretKeyCredentials.class), eq(ReflowResponse.class), isNull(), isNull()))
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

        final ReflowResponse response = Mockito.mock(ReflowResponse.class);

        when(apiClient.postAsync(eq(WORKFLOWS + "/" + EVENTS + "/event_id/" + WORKFLOW + "/workflow_id/" + REFLOW), any(SecretKeyCredentials.class), eq(ReflowResponse.class), isNull(), isNull()))
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

        final ReflowResponse response = Mockito.mock(ReflowResponse.class);

        when(apiClient.postAsync(eq(WORKFLOWS + "/" + EVENTS + "/" + SUBJECT + "/subject_id/" + WORKFLOW + "/workflow_id/" + REFLOW), any(SecretKeyCredentials.class), eq(ReflowResponse.class), isNull(), isNull()))
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

        final ReflowBySubjectsRequest request = Mockito.mock(ReflowBySubjectsRequest.class);
        final ReflowResponse response = Mockito.mock(ReflowResponse.class);

        when(apiClient.postAsync(eq(WORKFLOWS + "/" + EVENTS + "/" + REFLOW), any(SecretKeyCredentials.class), eq(ReflowResponse.class), eq(request), isNull()))
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

}
