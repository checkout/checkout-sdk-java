package com.checkout.workflows.four;

import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.workflows.four.events.SubjectEvent;
import com.checkout.workflows.four.reflow.ReflowByEventsRequest;
import com.checkout.workflows.four.reflow.ReflowBySubjectsRequest;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class WorkflowReflowTestIT extends AbstractWorkflowTestIT {

    @Test
    void shouldReflowByEvent() {

        createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        assertNotNull(paymentApprovedEvent);

        assertNull(blocking(() -> fourApi.workflowsClient().reflowByEvent(paymentApprovedEvent.getId())));

    }

    @Test
    void shouldReflowBySubject() {

        createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        assertNull(blocking(() -> fourApi.workflowsClient().reflowBySubject(payment.getId())));

    }

    @Test
    void shouldReflowByEventAndWorkflow() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        assertNull(blocking(() -> fourApi.workflowsClient().reflowByEventAndWorkflow(
                paymentApprovedEvent.getId(),
                createWorkflowResponse.getId()))
        );

    }

    @Test
    void shouldReflowBySubjectAndWorkflow() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        assertNull(blocking(() -> fourApi.workflowsClient().reflowBySubjectAndWorkflow(
                payment.getId(),
                createWorkflowResponse.getId()))
        );

    }

    @Test
    void shouldReflowByEvents() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        final ReflowByEventsRequest request = ReflowByEventsRequest.builder()
                .events(Collections.singletonList(paymentApprovedEvent.getId()))
                .workflows(Collections.singletonList(createWorkflowResponse.getId())).build();

        assertNull(blocking(() -> fourApi.workflowsClient().reflow(request)));

    }

    @Test
    void shouldReflowSubjects() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final ReflowBySubjectsRequest request = ReflowBySubjectsRequest.builder()
                .subjects(Collections.singletonList(payment.getId()))
                .workflows(Collections.singletonList(createWorkflowResponse.getId())).build();

        assertNull(blocking(() -> fourApi.workflowsClient().reflow(request)));

    }

}
