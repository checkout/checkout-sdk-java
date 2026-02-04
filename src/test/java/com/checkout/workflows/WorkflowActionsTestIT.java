package com.checkout.workflows;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.checkout.payments.response.PaymentResponse;
import com.checkout.workflows.actions.response.WorkflowActionInvocationsResponse;
import com.checkout.workflows.events.SubjectEvent;

@Disabled("unstable")
class WorkflowActionsTestIT extends AbstractWorkflowTestIT {

    @Test
    void shouldGetActionInvocations() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        final GetWorkflowResponse getWorkflowResponse1 = blocking(() -> checkoutApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
        assertNotNull(getWorkflowResponse1);
        assertEquals(1, getWorkflowResponse1.getActions().size());

        final String actionId = getWorkflowResponse1.getActions().get(0).getId();

        final WorkflowActionInvocationsResponse invocationsResponse = blocking(() -> checkoutApi.workflowsClient().getActionInvocations(paymentApprovedEvent.getId(), actionId));

        validateActionInvocationsResponse(invocationsResponse, createWorkflowResponse.getId(), paymentApprovedEvent.getId(), actionId);
    }

    // Synchronous methods
    @Test
    void shouldGetActionInvocationsSync() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        final GetWorkflowResponse getWorkflowResponse1 = checkoutApi.workflowsClient().getWorkflowSync(createWorkflowResponse.getId());
        assertNotNull(getWorkflowResponse1);
        assertEquals(1, getWorkflowResponse1.getActions().size());

        final String actionId = getWorkflowResponse1.getActions().get(0).getId();

        final WorkflowActionInvocationsResponse invocationsResponse = checkoutApi.workflowsClient().getActionInvocationsSync(paymentApprovedEvent.getId(), actionId);

        validateActionInvocationsResponse(invocationsResponse, createWorkflowResponse.getId(), paymentApprovedEvent.getId(), actionId);
    }

    // Common methods
    private void validateActionInvocationsResponse(WorkflowActionInvocationsResponse invocationsResponse, 
                                                   String expectedWorkflowId, 
                                                   String expectedEventId, 
                                                   String expectedActionId) {
        assertNotNull(invocationsResponse);
        assertEquals(expectedWorkflowId, invocationsResponse.getWorkflowId());
        assertEquals(expectedEventId, invocationsResponse.getEventId());
        assertEquals(expectedActionId, invocationsResponse.getWorkflowActionId());
        assertNotNull(invocationsResponse.getActionType());
        assertNotNull(invocationsResponse.getStatus());
        assertNotNull(invocationsResponse.getActionInvocations());
        assertTrue(invocationsResponse.getActionInvocations().size() > 0);
        invocationsResponse.getActionInvocations().forEach(workflowActionInvocation -> {
            assertNotNull(workflowActionInvocation.getInvocationId());
            assertNotNull(workflowActionInvocation.getTimestamp());
            assertNotNull(workflowActionInvocation.getRetry());
            assertNotNull(workflowActionInvocation.getSucceeded());
            assertNotNull(workflowActionInvocation.getFinalAttempt());
        });
    }

}
