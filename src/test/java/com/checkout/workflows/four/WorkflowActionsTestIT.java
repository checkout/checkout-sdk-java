package com.checkout.workflows.four;

import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.workflows.four.actions.response.WorkflowActionInvocationsResponse;
import com.checkout.workflows.four.events.SubjectEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkflowActionsTestIT extends AbstractWorkflowTestIT {

    @Test
    void shouldGetActionInvocations() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        final GetWorkflowResponse getWorkflowResponse1 = blocking(() -> fourApi.workflowsClient().getWorkflow(createWorkflowResponse.getId()));
        assertNotNull(getWorkflowResponse1);
        assertEquals(1, getWorkflowResponse1.getActions().size());

        final String actionId = getWorkflowResponse1.getActions().get(0).getId();

        final WorkflowActionInvocationsResponse invocationsResponse = blocking(() -> fourApi.workflowsClient().getActionInvocations(paymentApprovedEvent.getId(), actionId));

        assertNotNull(invocationsResponse);
        assertEquals(createWorkflowResponse.getId(), invocationsResponse.getWorkflowId());
        assertEquals(paymentApprovedEvent.getId(), invocationsResponse.getEventId());
        assertEquals(actionId, invocationsResponse.getWorkflowActionId());
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
