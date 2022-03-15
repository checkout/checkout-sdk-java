package com.checkout.events;

import com.checkout.payments.AbstractPaymentsTestIT;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.webhooks.WebhookRequest;
import com.checkout.webhooks.WebhookResponse;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventsTestIT extends AbstractPaymentsTestIT {

    private static final List<String> GATEWAY_EVENT_TYPES = Arrays.asList("payment_approved", "payment_pending",
            "payment_declined", "payment_expired", "payment_canceled", "payment_voided", "payment_void_declined",
            "payment_captured", "payment_capture_declined", "payment_capture_pending", "payment_refunded",
            "payment_refund_declined", "payment_refund_pending");

    @BeforeEach
    protected void cleanup() {
        final List<WebhookResponse> webhookResponses = blocking(() -> defaultApi.webhooksClient().retrieveWebhooks());
        webhookResponses.forEach(webhookResponse -> blocking(() -> defaultApi.webhooksClient().removeWebhook(webhookResponse.getId())));
    }

    @Test
    void retrieveDefaultEventTypes() {
        final List<EventTypesResponse> allEventTypesResponses = blocking(() -> defaultApi.eventsClient().retrieveAllEventTypes(null));
        assertNotNull(allEventTypesResponses);
        assertEquals(2, allEventTypesResponses.size());
    }

    @Test
    void retrieveV1EventTypes() {

        final List<EventTypesResponse> eventTypesResponses = blocking(() -> defaultApi.eventsClient().retrieveAllEventTypes("1.0"));
        assertNotNull(eventTypesResponses);
        assertEquals(1, eventTypesResponses.size());

        final EventTypesResponse eventTypesResponse = eventTypesResponses.get(0);
        assertEquals("1.0", eventTypesResponse.getVersion());
        assertNotNull(eventTypesResponse.getEventTypes());
        assertFalse(eventTypesResponse.getEventTypes().isEmpty());

    }

    @Test
    void retrieveV2EventTypes() {

        final List<EventTypesResponse> eventTypesResponses = blocking(() -> defaultApi.eventsClient().retrieveAllEventTypes("2.0"));
        assertNotNull(eventTypesResponses);
        assertEquals(1, eventTypesResponses.size());

        final EventTypesResponse eventTypesResponse = eventTypesResponses.get(0);

        assertEquals("2.0", eventTypesResponse.getVersion());
        assertNotNull(eventTypesResponse.getEventTypes());
        assertFalse(eventTypesResponse.getEventTypes().isEmpty());

    }

    @Test
    @Disabled("unstable")
    void shouldRetrieveEventsByPaymentId_andRetrieveEventById_andGetNotification() {

        registerWebhook();

        final String paymentId = makeCardPayment().getId();

        final RetrieveEventsRequest retrieveEventsRequest = RetrieveEventsRequest.builder()
                .paymentId(paymentId)
                .build();

        // Retrieve Events
        final EventsPageResponse eventsPageResponse = blocking(() -> defaultApi.eventsClient().retrieveEvents(retrieveEventsRequest), IsNull.notNullValue(EventsPageResponse.class));

        assertNotNull(eventsPageResponse);
        assertEquals(1, eventsPageResponse.getTotalCount());

        final EventSummaryResponse eventSummaryResponse = eventsPageResponse.getData().get(0);
        assertNotNull(eventSummaryResponse.getId());
        assertNotNull(eventSummaryResponse.getCreatedOn());
        assertEquals("payment_approved", eventSummaryResponse.getType());
        assertNotNull(eventSummaryResponse.getLink("self"));
        assertNotNull(eventSummaryResponse.getLink("webhooks-retry"));

        // Retrieve Event
        final EventResponse eventResponse = blocking(() -> defaultApi.eventsClient().retrieveEvent(eventSummaryResponse.getId()), new HasNotifications(1));

        assertNotNull(eventResponse);
        assertNotNull(eventResponse.getId());
        assertNotNull(eventResponse.getData());
        assertEquals(1, eventResponse.getNotifications().size());
        assertEquals("payment_approved", eventSummaryResponse.getType());
        assertNotNull(eventResponse.getLink("self"));
        assertNotNull(eventResponse.getLink("webhooks-retry"));

        // Get Notification
        final EventNotificationResponse eventNotificationResponse = blocking(() -> defaultApi.eventsClient()
                .retrieveEventNotification(eventSummaryResponse.getId(), eventResponse.getNotifications().get(0).getId()));

        assertNotNull(eventNotificationResponse);
        assertNotNull(eventNotificationResponse.getId());
        assertNotNull(eventNotificationResponse.getUrl());
        assertFalse(eventNotificationResponse.getSuccess());
        assertFalse(eventNotificationResponse.getAttempts().isEmpty());
        assertNotNull(eventNotificationResponse.getLink("self"));
        assertNotNull(eventNotificationResponse.getLink("webhook-retry"));

    }

    protected static class HasNotifications extends BaseMatcher<EventResponse> {

        private final int count;

        public HasNotifications(final int count) {
            this.count = count;
        }

        @Override
        public boolean matches(final Object actual) {
            if (!(actual instanceof EventResponse)) {
                throw new IllegalStateException("not a EventResponse!");
            }
            return ((EventResponse) actual).getNotifications().size() == count;
        }

        @Override
        public void describeTo(final Description description) {
            throw new UnsupportedOperationException();
        }

    }

    @Test
    void shouldRetryWebhook() {

        final WebhookResponse webhookResponse = registerWebhook();

        final String paymentId = makeCardPayment().getId();

        final RetrieveEventsRequest retrieveEventsRequest = RetrieveEventsRequest.builder()
                .to(Instant.now().plus(5, ChronoUnit.SECONDS))
                .from(LocalDateTime.now().minusMonths(6).toInstant(ZoneOffset.UTC))
                .limit(15)
                .skip(0)
                .paymentId(paymentId)
                .build();

        // Retrieve Events
        final EventsPageResponse eventsPageResponse = blocking(() -> defaultApi.eventsClient().retrieveEvents(retrieveEventsRequest), IsNull.notNullValue(EventsPageResponse.class));
        assertNotNull(eventsPageResponse);
        assertEquals(eventsPageResponse.getTo().truncatedTo(ChronoUnit.SECONDS), eventsPageResponse.getTo());
        assertEquals(eventsPageResponse.getFrom().truncatedTo(ChronoUnit.SECONDS), eventsPageResponse.getFrom());

        final EventSummaryResponse eventSummaryResponse = eventsPageResponse.getData().get(0);
        assertNotNull(eventSummaryResponse.getId());

        // Retrieve Event
        final EventResponse eventResponse = blocking(() -> defaultApi.eventsClient().retrieveEvent(eventSummaryResponse.getId()));

        // Retry Webhooks
        // Webhooks are not being re attempted. Adding the call to ensure.
        blocking(() -> defaultApi.eventsClient().retryWebhook(eventSummaryResponse.getId(), webhookResponse.getId()));

        blocking(() -> defaultApi.eventsClient().retryAllWebhooks(eventSummaryResponse.getId()));

    }

    protected WebhookResponse registerWebhook() {

        final WebhookRequest webhookRequest = WebhookRequest.builder()
                .url("https://google.com/fail")
                .build();

        webhookRequest.setEventTypes(GATEWAY_EVENT_TYPES);

        return blocking(() -> defaultApi.webhooksClient().registerWebhook(webhookRequest));

    }

    private PaymentResponse makeCardPayment() {
        return makeCardPayment(false, 10L);
    }

}
