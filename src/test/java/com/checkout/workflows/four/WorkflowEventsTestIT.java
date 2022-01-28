package com.checkout.workflows.four;

import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.workflows.four.events.Event;
import com.checkout.workflows.four.events.EventTypesResponse;
import com.checkout.workflows.four.events.GetEventResponse;
import com.checkout.workflows.four.events.SubjectEvent;
import com.checkout.workflows.four.events.SubjectEventsResponse;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WorkflowEventsTestIT extends AbstractWorkflowTestIT {

    @Test
    void shouldGetEventTypes() {

        final List<EventTypesResponse> eventTypesResponse = blocking(() -> fourApi.workflowsClient().getEventTypes());

        assertNotNull(eventTypesResponse);
        assertFalse(eventTypesResponse.isEmpty());

        for (final EventTypesResponse typesResponse : eventTypesResponse) {
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

    @Test
    void shouldGetSubjectEventAndEvents() {

        createWorkflow();

        final PaymentResponse payment = makeCardPayment(false);

        capturePayment(payment.getId());

        final SubjectEventsResponse subjectEventsResponse = blocking(() -> fourApi.workflowsClient().getSubjectEvents(payment.getId()), new HasEvents(2));

        assertNotNull(subjectEventsResponse);
        assertEquals(2, subjectEventsResponse.getEvents().size());

        final SubjectEvent paymentApprovedEvent = subjectEventsResponse.getEvents().stream()
                .filter(event -> event.getType().equals(PAYMENT_APPROVED))
                .findFirst()
                .orElse(null);

        assertNotNull(paymentApprovedEvent);
        assertNotNull(paymentApprovedEvent.getId());
        assertNotNull(paymentApprovedEvent.getTimestamp());
        assertNotNull(paymentApprovedEvent.getLink(SELF));

        final SubjectEvent paymentCapturedEvent = subjectEventsResponse.getEvents().stream()
                .filter(event -> event.getType().equals(PAYMENT_CAPTURED))
                .findFirst()
                .orElse(null);

        assertNotNull(paymentCapturedEvent);
        assertNotNull(paymentCapturedEvent.getId());
        assertNotNull(paymentCapturedEvent.getTimestamp());
        assertNotNull(paymentCapturedEvent.getLink(SELF));

        // Get event
        final GetEventResponse getEventResponse = blocking(() -> fourApi.workflowsClient().getEvent(paymentCapturedEvent.getId()));

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
