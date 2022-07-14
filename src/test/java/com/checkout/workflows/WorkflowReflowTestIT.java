package com.checkout.workflows;

import com.checkout.payments.response.PaymentResponse;
import com.checkout.workflows.events.SubjectEvent;
import com.checkout.workflows.reflow.ReflowByEventsRequest;
import com.checkout.workflows.reflow.ReflowBySubjectsRequest;
import com.checkout.workflows.reflow.ReflowResponse;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkflowReflowTestIT extends AbstractWorkflowTestIT {

    @Test
    void shouldReflowByEvent() {

        createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        assertNotNull(paymentApprovedEvent);

        final ReflowResponse response = blocking(() -> checkoutApi.workflowsClient().reflowByEvent(paymentApprovedEvent.getId()));
        assertNotNull(response);
        assertNotNull(response.getHttpStatusCode());
        assertNotNull(response.getResponseHeaders());
        assertTrue(StringUtils.isBlank(response.getBody()));

    }

    @Test
    void shouldReflowBySubject() {

        createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final ReflowResponse response = blocking(() -> checkoutApi.workflowsClient().reflowBySubject(payment.getId()));
        assertNotNull(response);
        assertNotNull(response.getHttpStatusCode());
        assertNotNull(response.getResponseHeaders());
        assertTrue(StringUtils.isBlank(response.getBody()));

    }

    @Test
    void shouldReflowByEventAndWorkflow() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        final ReflowResponse response = blocking(() -> checkoutApi.workflowsClient().reflowByEventAndWorkflow(
                paymentApprovedEvent.getId(),
                createWorkflowResponse.getId()));
        assertNotNull(response);
        assertNotNull(response.getHttpStatusCode());
        assertNotNull(response.getResponseHeaders());
        assertTrue(StringUtils.isBlank(response.getBody()));

    }

    @Test
    void shouldReflowBySubjectAndWorkflow() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final ReflowResponse response = blocking(() -> checkoutApi.workflowsClient().reflowBySubjectAndWorkflow(
                payment.getId(),
                createWorkflowResponse.getId()));
        assertNotNull(response);
        assertNotNull(response.getHttpStatusCode());
        assertNotNull(response.getResponseHeaders());
        assertTrue(StringUtils.isBlank(response.getBody()));

    }

    @Test
    void shouldReflowByEvents() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        final ReflowByEventsRequest request = ReflowByEventsRequest.builder()
                .events(Collections.singletonList(paymentApprovedEvent.getId()))
                .workflows(Collections.singletonList(createWorkflowResponse.getId())).build();

        final ReflowResponse response = blocking(() -> checkoutApi.workflowsClient().reflow(request));
        assertNotNull(response);
        assertNotNull(response.getHttpStatusCode());
        assertNotNull(response.getResponseHeaders());
        assertTrue(StringUtils.isBlank(response.getBody()));

    }

    @Test
    void shouldReflowSubjects() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final ReflowBySubjectsRequest request = ReflowBySubjectsRequest.builder()
                .subjects(Collections.singletonList(payment.getId()))
                .workflows(Collections.singletonList(createWorkflowResponse.getId())).build();

        final ReflowResponse response = blocking(() -> checkoutApi.workflowsClient().reflow(request));
        assertNotNull(response);
        assertNotNull(response.getHttpStatusCode());
        assertNotNull(response.getResponseHeaders());
        assertTrue(StringUtils.isBlank(response.getBody()));

    }

}
