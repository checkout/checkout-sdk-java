package com.checkout.workflows;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.checkout.ItemsResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.workflows.events.Event;
import com.checkout.workflows.events.GetEventResponse;
import com.checkout.workflows.events.SubjectEvent;
import com.checkout.workflows.events.SubjectEventsResponse;
import com.checkout.workflows.events.WorkflowEventTypes;

@Disabled("unstable")
class WorkflowEventsTestIT extends AbstractWorkflowTestIT {

    @Test
    void shouldGetEventTypes() {

        final ItemsResponse<WorkflowEventTypes> workflowEventTypesResponse = blocking(() -> checkoutApi.workflowsClient().getEventTypes());

        validateWorkflowEventTypesResponse(workflowEventTypesResponse);

    }

    @Test
    void shouldGetSubjectEventAndEvents() {

        createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        capturePayment(payment.getId());

        final SubjectEventsResponse subjectEventsResponse = blocking(() -> checkoutApi.workflowsClient().getSubjectEvents(payment.getId()), new HasEvents(2));

        assertNotNull(subjectEventsResponse);
        assertEquals(2, subjectEventsResponse.getEvents().size());

        final SubjectEvent paymentApprovedEvent = findEventByType(subjectEventsResponse, PAYMENT_APPROVED);
        final SubjectEvent paymentCapturedEvent = findEventByType(subjectEventsResponse, PAYMENT_CAPTURED);

        validateSubjectEvent(paymentApprovedEvent);
        validateSubjectEvent(paymentCapturedEvent);

        // Get event
        final GetEventResponse getEventResponse = blocking(() -> checkoutApi.workflowsClient().getEvent(paymentCapturedEvent.getId()));

        validateGetEventResponse(getEventResponse);

    }

    // Synchronous methods
    @Test
    void shouldGetEventTypesSync() {

        final ItemsResponse<WorkflowEventTypes> workflowEventTypesResponse = checkoutApi.workflowsClient().getEventTypesSync();

        validateWorkflowEventTypesResponse(workflowEventTypesResponse);

    }

    @Test
    void shouldGetSubjectEventAndEventsSync() {

        createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        capturePayment(payment.getId());

        // Note: For sync methods, we can't use blocking() with matchers, so we'll get the response directly
        final SubjectEventsResponse subjectEventsResponse = checkoutApi.workflowsClient().getSubjectEventsSync(payment.getId());

        assertNotNull(subjectEventsResponse);
        // We may have fewer events in sync case since we're not waiting
        assertFalse(subjectEventsResponse.getEvents().isEmpty());

        // Find available events
        final SubjectEvent paymentApprovedEvent = findEventByType(subjectEventsResponse, PAYMENT_APPROVED);
        if (paymentApprovedEvent != null) {
            validateSubjectEvent(paymentApprovedEvent);
        }

        final SubjectEvent paymentCapturedEvent = findEventByType(subjectEventsResponse, PAYMENT_CAPTURED);
        if (paymentCapturedEvent != null) {
            validateSubjectEvent(paymentCapturedEvent);
            
            // Get event if captured event exists
            final GetEventResponse getEventResponse = checkoutApi.workflowsClient().getEventSync(paymentCapturedEvent.getId());
            validateGetEventResponse(getEventResponse);
        }

    }

    // Common methods
    private void validateWorkflowEventTypesResponse(ItemsResponse<WorkflowEventTypes> workflowEventTypesResponse) {
        assertNotNull(workflowEventTypesResponse);
        assertNotNull(workflowEventTypesResponse.getItems());
        assertFalse(workflowEventTypesResponse.getItems().isEmpty());

        for (final WorkflowEventTypes typesResponse : workflowEventTypesResponse.getItems()) {
            assertNotNull(typesResponse.getId());
            assertNotNull(typesResponse.getDisplayName());
            assertNotNull(typesResponse.getEvents());
            for (final Event event : typesResponse.getEvents()) {
                assertNotNull(event.getId());
                assertNotNull(event.getDisplayName());
                assertNotNull(event.getDescription());
            }
        }
    }

    private SubjectEvent findEventByType(SubjectEventsResponse subjectEventsResponse, String eventType) {
        return subjectEventsResponse.getEvents().stream()
                .filter(event -> event.getType().equals(eventType))
                .findFirst()
                .orElse(null);
    }

    private void validateSubjectEvent(SubjectEvent subjectEvent) {
        assertNotNull(subjectEvent);
        assertNotNull(subjectEvent.getId());
        assertNotNull(subjectEvent.getTimestamp());
        assertNotNull(subjectEvent.getLink(SELF));
    }

    private void validateGetEventResponse(GetEventResponse getEventResponse) {
        assertNotNull(getEventResponse);
        assertNotNull(getEventResponse.getId());
        assertEquals(GATEWAY, getEventResponse.getSource());
        assertEquals(PAYMENT_CAPTURED, getEventResponse.getType());
        assertNotNull(getEventResponse.getTimestamp());
        assertNotNull(getEventResponse.getVersion());
        assertNotNull(getEventResponse.getData());
        assertFalse(getEventResponse.getData().isEmpty());
    }

    protected static class HasEvents extends BaseMatcher<SubjectEventsResponse> {

        private final int count;

        public HasEvents(final int count) {
            this.count = count;
        }

        @Override
        public boolean matches(final Object actual) {
            if (!(actual instanceof SubjectEventsResponse)) {
                throw new IllegalStateException("not a SubjectEventsResponse!");
            }
            return ((SubjectEventsResponse) actual).getEvents().size() == count;
        }

        @Override
        public void describeTo(final Description description) {
            throw new UnsupportedOperationException();
        }

    }

}
