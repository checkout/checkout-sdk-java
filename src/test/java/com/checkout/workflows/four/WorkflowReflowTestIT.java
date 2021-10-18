package com.checkout.workflows.four;

import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.payments.four.response.source.ResponseCardSource;
import com.checkout.workflows.four.events.SubjectEvent;
import com.checkout.workflows.four.events.SubjectEventsResponse;
import com.checkout.workflows.four.reflow.ReflowByEventsRequest;
import com.checkout.workflows.four.reflow.ReflowBySubjectsRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class WorkflowReflowTestIT extends AbstractWorkflowTestIT {

    @Disabled
    @Test
    void shouldReflowByEvent() {

        createWorkflow();

        final PaymentResponse<ResponseCardSource> payment = makeCardPayment(false);

        nap(10);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        assertNotNull(paymentApprovedEvent);

        assertNull(blocking(fourApi.workflowsClient().reflowByEvent(paymentApprovedEvent.getId())));

    }

    @Disabled
    @Test
    void shouldReflowBySubject() {

        createWorkflow();

        final PaymentResponse<ResponseCardSource> payment = makeCardPayment(false);

        nap(10);

        assertNull(blocking(fourApi.workflowsClient().reflowBySubject(payment.getId())));

    }

    @Disabled
    @Test
    void shouldReflowByEventAndWorkflow() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse<ResponseCardSource> payment = makeCardPayment(false);

        nap(10);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        assertNull(blocking(fourApi.workflowsClient().reflowByEventAndWorkflow(
                paymentApprovedEvent.getId(),
                createWorkflowResponse.getId()))
        );

    }

    @Disabled
    @Test
    void shouldReflowBySubjectAndWorkflow() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse<ResponseCardSource> payment = makeCardPayment(false);

        nap(10);

        assertNull(blocking(fourApi.workflowsClient().reflowBySubjectAndWorkflow(
                payment.getId(),
                createWorkflowResponse.getId()))
        );

    }

    @Disabled
    @Test
    void shouldReflowByEvents() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse<ResponseCardSource> payment = makeCardPayment(false);

        nap(10);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        final ReflowByEventsRequest request = ReflowByEventsRequest.builder()
                .events(Collections.singletonList(paymentApprovedEvent.getId()))
                .workflows(Collections.singletonList(createWorkflowResponse.getId())).build();

        assertNull(blocking(fourApi.workflowsClient().reflow(request)));

    }

    @Disabled
    @Test
    void shouldReflowSubjects() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse<ResponseCardSource> payment = makeCardPayment(false);

        nap(10);

        final ReflowBySubjectsRequest request = ReflowBySubjectsRequest.builder()
                .subjects(Collections.singletonList(payment.getId()))
                .workflows(Collections.singletonList(createWorkflowResponse.getId())).build();

        assertNull(blocking(fourApi.workflowsClient().reflow(request)));

    }

    private SubjectEvent getSubjectEvent(final String subjectId) {

        final SubjectEventsResponse subjectEventsResponse = blocking(fourApi.workflowsClient().getSubjectEvents(subjectId));

        assertNotNull(subjectEventsResponse);
        assertEquals(1, subjectEventsResponse.getEvents().size());

        final SubjectEvent paymentApprovedEvent = subjectEventsResponse.getEvents().stream()
                .filter(event -> event.getType().equals(PAYMENT_APPROVED))
                .findFirst()
                .orElse(null);

        assertNotNull(paymentApprovedEvent);

        return paymentApprovedEvent;
    }

}
