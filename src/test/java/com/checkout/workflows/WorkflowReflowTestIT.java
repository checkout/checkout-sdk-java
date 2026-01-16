package com.checkout.workflows;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import com.checkout.payments.response.PaymentResponse;
import com.checkout.workflows.events.SubjectEvent;
import com.checkout.workflows.reflow.ReflowByEventsRequest;
import com.checkout.workflows.reflow.ReflowBySubjectsRequest;
import com.checkout.workflows.reflow.ReflowResponse;

@Disabled("unstable")
class WorkflowReflowTestIT extends AbstractWorkflowTestIT {

    @Test
    void shouldReflowByEvent() {

        createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        assertNotNull(paymentApprovedEvent);

        final ReflowResponse response = blocking(() -> checkoutApi.workflowsClient().reflowByEvent(paymentApprovedEvent.getId()));
        validateReflowResponse(response);

    }

    @Test
    void shouldReflowBySubject() {

        createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final ReflowResponse response = blocking(() -> checkoutApi.workflowsClient().reflowBySubject(payment.getId()));
        validateReflowResponse(response);

    }

    @Test
    void shouldReflowByEventAndWorkflow() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        final ReflowResponse response = blocking(() -> checkoutApi.workflowsClient().reflowByEventAndWorkflow(
                paymentApprovedEvent.getId(),
                createWorkflowResponse.getId()));
        validateReflowResponse(response);

    }

    @Test
    void shouldReflowBySubjectAndWorkflow() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final ReflowResponse response = blocking(() -> checkoutApi.workflowsClient().reflowBySubjectAndWorkflow(
                payment.getId(),
                createWorkflowResponse.getId()));
        validateReflowResponse(response);

    }

    @Test
    void shouldReflowByEvents() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        final ReflowByEventsRequest request = createReflowByEventsRequest(paymentApprovedEvent, createWorkflowResponse);

        final ReflowResponse response = blocking(() -> checkoutApi.workflowsClient().reflow(request));
        validateReflowResponse(response);

    }

    @Test
    @Disabled("unstable")
    void shouldReflowSubjects() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final ReflowBySubjectsRequest request = createReflowBySubjectsRequest(payment, createWorkflowResponse);

        final ReflowResponse response = blocking(() -> checkoutApi.workflowsClient().reflow(request));
        validateReflowResponse(response);

    }

    // Synchronous methods
    @Test
    void shouldReflowByEventSync() {

        createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        assertNotNull(paymentApprovedEvent);

        final ReflowResponse response = checkoutApi.workflowsClient().reflowByEventSync(paymentApprovedEvent.getId());
        validateReflowResponse(response);

    }

    @Test
    void shouldReflowBySubjectSync() {

        createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final ReflowResponse response = checkoutApi.workflowsClient().reflowBySubjectSync(payment.getId());
        validateReflowResponse(response);

    }

    @Test
    void shouldReflowByEventAndWorkflowSync() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        final ReflowResponse response = checkoutApi.workflowsClient().reflowByEventAndWorkflowSync(
                paymentApprovedEvent.getId(),
                createWorkflowResponse.getId());
        validateReflowResponse(response);

    }

    @Test
    void shouldReflowBySubjectAndWorkflowSync() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final ReflowResponse response = checkoutApi.workflowsClient().reflowBySubjectAndWorkflowSync(
                payment.getId(),
                createWorkflowResponse.getId());
        validateReflowResponse(response);

    }

    @Test
    void shouldReflowByEventsSync() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final SubjectEvent paymentApprovedEvent = getSubjectEvent(payment.getId());

        final ReflowByEventsRequest request = createReflowByEventsRequest(paymentApprovedEvent, createWorkflowResponse);

        final ReflowResponse response = checkoutApi.workflowsClient().reflowSync(request);
        validateReflowResponse(response);

    }

    @Test
    @Disabled("unstable")
    void shouldReflowSubjectsSync() {

        final CreateWorkflowResponse createWorkflowResponse = createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        final ReflowBySubjectsRequest request = createReflowBySubjectsRequest(payment, createWorkflowResponse);

        final ReflowResponse response = checkoutApi.workflowsClient().reflowSync(request);
        validateReflowResponse(response);

    }

    // Common methods
    private void validateReflowResponse(ReflowResponse response) {
        assertNotNull(response);
        assertNotNull(response.getHttpStatusCode());
        assertNotNull(response.getResponseHeaders());
        assertTrue(StringUtils.isBlank(response.getBody()));
    }

    private ReflowByEventsRequest createReflowByEventsRequest(SubjectEvent paymentEvent, CreateWorkflowResponse workflow) {
        return ReflowByEventsRequest.builder()
                .events(Collections.singletonList(paymentEvent.getId()))
                .workflows(Collections.singletonList(workflow.getId())).build();
    }

    private ReflowBySubjectsRequest createReflowBySubjectsRequest(PaymentResponse payment, CreateWorkflowResponse workflow) {
        return ReflowBySubjectsRequest.builder()
                .subjects(Collections.singletonList(payment.getId()))
                .workflows(Collections.singletonList(workflow.getId())).build();
    }

}
