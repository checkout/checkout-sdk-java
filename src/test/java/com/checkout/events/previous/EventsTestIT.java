package com.checkout.events.previous;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.checkout.ItemsResponse;
import com.checkout.payments.previous.AbstractPaymentsTestIT;
import com.checkout.payments.previous.response.PaymentResponse;
import com.checkout.webhooks.previous.WebhookRequest;
import com.checkout.webhooks.previous.WebhookResponse;

@Disabled("Temporarily skipped")
class EventsTestIT extends AbstractPaymentsTestIT {

    private static final List<String> GATEWAY_EVENT_TYPES = Arrays.asList("payment_approved", "payment_pending",
            "payment_declined", "payment_expired", "payment_canceled", "payment_voided", "payment_void_declined",
            "payment_captured", "payment_capture_declined", "payment_capture_pending", "payment_refunded",
            "payment_refund_declined", "payment_refund_pending");

    @BeforeEach
    protected void cleanup() {
        final ItemsResponse<WebhookResponse> webhookResponses = blocking(() -> previousApi.webhooksClient().retrieveWebhooks());
        webhookResponses.getItems().forEach(webhookResponse -> blocking(() -> previousApi.webhooksClient().removeWebhook(webhookResponse.getId())));
    }

    @Test
    void retrieveDefaultEventTypes() {
        final ItemsResponse<EventTypes> allEventTypesResponses = blocking(() -> previousApi.eventsClient().retrieveAllEventTypes(null));
        
        validateEventTypesResponse(allEventTypesResponses, 2);
    }

    @Test
    void retrieveV1EventTypes() {
        final ItemsResponse<EventTypes> eventTypesResponses = blocking(() -> previousApi.eventsClient().retrieveAllEventTypes("1.0"));
        
        validateEventTypesResponse(eventTypesResponses, 1);
        
        final EventTypes eventTypesResponse = eventTypesResponses.getItems().get(0);
        validateV1EventTypes(eventTypesResponse);
    }

    @Test
    @Disabled("unstable")
    void shouldRetrieveEventsByPaymentId_andRetrieveEventById_andGetNotification() {
        createAndRegisterWebhook();
        final String paymentId = createTestPayment().getId();
        final RetrieveEventsRequest retrieveEventsRequest = createRetrieveEventsRequest(paymentId);

        // Retrieve Events
        final EventsPageResponse eventsPageResponse = blocking(() -> previousApi.eventsClient().retrieveEvents(retrieveEventsRequest), IsNull.notNullValue(EventsPageResponse.class));

        validateEventsPageResponse(eventsPageResponse);

        final EventSummaryResponse eventSummaryResponse = eventsPageResponse.getData().get(0);
        validateEventSummaryResponse(eventSummaryResponse);

        // Retrieve Event
        final EventResponse eventResponse = blocking(() -> previousApi.eventsClient().retrieveEvent(eventSummaryResponse.getId()), new HasNotifications(1));

        validateEventResponse(eventResponse, eventSummaryResponse);

        // Get Notification
        final EventNotificationResponse eventNotificationResponse = blocking(() -> previousApi.eventsClient()
                .retrieveEventNotification(eventSummaryResponse.getId(), eventResponse.getNotifications().get(0).getId()));

        validateEventNotificationResponse(eventNotificationResponse);
    }

    @Test
    @Disabled("unstable")
    void shouldRetryWebhook() {
        final WebhookResponse webhookResponse = createAndRegisterWebhook();
        final String paymentId = createTestPayment().getId();
        final RetrieveEventsRequest retrieveEventsRequest = createRetrieveEventsRequestWithLimits(paymentId);

        // Retrieve Events
        final EventsPageResponse eventsPageResponse = blocking(() -> previousApi.eventsClient().retrieveEvents(retrieveEventsRequest), new EventsPageResponseHasItems());
        
        validateEventsPageResponseWithTimestamps(eventsPageResponse);

        final EventSummaryResponse eventSummaryResponse = eventsPageResponse.getData().get(0);
        assertNotNull(eventSummaryResponse.getId());

        // Retrieve Event
        final EventResponse eventResponse = blocking(() -> previousApi.eventsClient().retrieveEvent(eventSummaryResponse.getId()));
        assertNotNull(eventResponse);

        // Retry Webhooks
        blocking(() -> previousApi.eventsClient().retryWebhook(eventSummaryResponse.getId(), webhookResponse.getId()));
        blocking(() -> previousApi.eventsClient().retryAllWebhooks(eventSummaryResponse.getId()));
    }

    // Synchronous methods
    @Test
    void retrieveDefaultEventTypesSync() {
        final ItemsResponse<EventTypes> allEventTypesResponses = previousApi.eventsClient().retrieveAllEventTypesSync(null);
        
        validateEventTypesResponse(allEventTypesResponses, 2);
    }

    @Test
    void retrieveV1EventTypesSync() {
        final ItemsResponse<EventTypes> eventTypesResponses = previousApi.eventsClient().retrieveAllEventTypesSync("1.0");
        
        validateEventTypesResponse(eventTypesResponses, 1);
        
        final EventTypes eventTypesResponse = eventTypesResponses.getItems().get(0);
        validateV1EventTypes(eventTypesResponse);
    }

    @Test
    @Disabled("unstable")
    void shouldRetrieveEventsByPaymentIdSync_andRetrieveEventByIdSync_andGetNotificationSync() {
        createAndRegisterWebhook();
        final String paymentId = createTestPayment().getId();
        final RetrieveEventsRequest retrieveEventsRequest = createRetrieveEventsRequest(paymentId);

        // Retrieve Events
        final EventsPageResponse eventsPageResponse = waitForEventsPageResponse(() -> previousApi.eventsClient().retrieveEventsSync(retrieveEventsRequest));

        validateEventsPageResponse(eventsPageResponse);

        final EventSummaryResponse eventSummaryResponse = eventsPageResponse.getData().get(0);
        validateEventSummaryResponse(eventSummaryResponse);

        // Retrieve Event
        final EventResponse eventResponse = waitForEventWithNotifications(() -> previousApi.eventsClient().retrieveEventSync(eventSummaryResponse.getId()));

        validateEventResponse(eventResponse, eventSummaryResponse);

        // Get Notification
        final EventNotificationResponse eventNotificationResponse = 
                previousApi.eventsClient().retrieveEventNotificationSync(
                        eventSummaryResponse.getId(), 
                        eventResponse.getNotifications().get(0).getId());

        validateEventNotificationResponse(eventNotificationResponse);
    }

    @Test
    @Disabled("unstable")
    void shouldRetryWebhookSync() {
        final WebhookResponse webhookResponse = createAndRegisterWebhook();
        final String paymentId = createTestPayment().getId();
        final RetrieveEventsRequest retrieveEventsRequest = createRetrieveEventsRequestWithLimits(paymentId);

        // Retrieve Events
        final EventsPageResponse eventsPageResponse = waitForEventsPageResponseWithData(() -> previousApi.eventsClient().retrieveEventsSync(retrieveEventsRequest));
        
        validateEventsPageResponseWithTimestamps(eventsPageResponse);

        final EventSummaryResponse eventSummaryResponse = eventsPageResponse.getData().get(0);
        assertNotNull(eventSummaryResponse.getId());

        // Retrieve Event
        final EventResponse eventResponse = previousApi.eventsClient().retrieveEventSync(eventSummaryResponse.getId());
        assertNotNull(eventResponse);

        // Retry Webhooks
        previousApi.eventsClient().retryWebhookSync(eventSummaryResponse.getId(), webhookResponse.getId());
        previousApi.eventsClient().retryAllWebhooksSync(eventSummaryResponse.getId());
    }

    // Common methods
    private RetrieveEventsRequest createRetrieveEventsRequest(String paymentId) {
        return RetrieveEventsRequest.builder()
                .paymentId(paymentId)
                .build();
    }

    private RetrieveEventsRequest createRetrieveEventsRequestWithLimits(String paymentId) {
        return RetrieveEventsRequest.builder()
                .limit(15)
                .skip(0)
                .paymentId(paymentId)
                .build();
    }

    private WebhookResponse createAndRegisterWebhook() {
        final WebhookRequest webhookRequest = WebhookRequest.builder()
                .url("https://google.com/fail")
                .build();

        webhookRequest.setEventTypes(GATEWAY_EVENT_TYPES);

        return blocking(() -> previousApi.webhooksClient().registerWebhook(webhookRequest));
    }

    private PaymentResponse createTestPayment() {
        return makeCardPayment(false, 10L);
    }

    private void validateEventTypesResponse(ItemsResponse<EventTypes> response, int expectedSize) {
        assertNotNull(response);
        assertEquals(expectedSize, response.getItems().size());
    }

    private void validateV1EventTypes(EventTypes eventTypesResponse) {
        assertEquals("1.0", eventTypesResponse.getVersion());
        assertNotNull(eventTypesResponse.getEventTypes());
        assertFalse(eventTypesResponse.getEventTypes().isEmpty());
    }

    private void validateEventsPageResponse(EventsPageResponse eventsPageResponse) {
        assertNotNull(eventsPageResponse);
        assertEquals(1, eventsPageResponse.getTotalCount());
    }

    private void validateEventSummaryResponse(EventSummaryResponse eventSummaryResponse) {
        assertNotNull(eventSummaryResponse.getId());
        assertNotNull(eventSummaryResponse.getCreatedOn());
        assertEquals("payment_approved", eventSummaryResponse.getType());
        assertNotNull(eventSummaryResponse.getLink("self"));
        assertNotNull(eventSummaryResponse.getLink("webhooks-retry"));
    }

    private void validateEventResponse(EventResponse eventResponse, EventSummaryResponse eventSummaryResponse) {
        assertNotNull(eventResponse);
        assertNotNull(eventResponse.getId());
        assertNotNull(eventResponse.getData());
        assertEquals(1, eventResponse.getNotifications().size());
        assertEquals("payment_approved", eventSummaryResponse.getType());
        assertNotNull(eventResponse.getLink("self"));
        assertNotNull(eventResponse.getLink("webhooks-retry"));
    }

    private void validateEventNotificationResponse(EventNotificationResponse eventNotificationResponse) {
        assertNotNull(eventNotificationResponse);
        assertNotNull(eventNotificationResponse.getId());
        assertNotNull(eventNotificationResponse.getUrl());
        assertFalse(eventNotificationResponse.getSuccess());
        assertFalse(eventNotificationResponse.getAttempts().isEmpty());
        assertNotNull(eventNotificationResponse.getLink("self"));
        assertNotNull(eventNotificationResponse.getLink("webhook-retry"));
    }

    private void validateEventsPageResponseWithTimestamps(EventsPageResponse eventsPageResponse) {
        assertNotNull(eventsPageResponse);
        assertEquals(eventsPageResponse.getTo().truncatedTo(ChronoUnit.SECONDS), eventsPageResponse.getTo());
        assertEquals(eventsPageResponse.getFrom().truncatedTo(ChronoUnit.SECONDS), eventsPageResponse.getFrom());
    }

    private EventsPageResponse waitForEventsPageResponse(java.util.function.Supplier<EventsPageResponse> supplier) {
        return blocking(() -> java.util.concurrent.CompletableFuture.completedFuture(supplier.get()), IsNull.notNullValue(EventsPageResponse.class));
    }

    private EventsPageResponse waitForEventsPageResponseWithData(java.util.function.Supplier<EventsPageResponse> supplier) {
        return blocking(() -> java.util.concurrent.CompletableFuture.completedFuture(supplier.get()), new EventsPageResponseHasItems());
    }

    private EventResponse waitForEventWithNotifications(java.util.function.Supplier<EventResponse> supplier) {
        return blocking(() -> java.util.concurrent.CompletableFuture.completedFuture(supplier.get()), new HasNotifications(1));
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

    protected static class EventsPageResponseHasItems extends BaseMatcher<EventsPageResponse> {

        @Override
        public boolean matches(final Object actual) {
            return ((EventsPageResponse) actual).getData() != null;
        }

        @Override
        public void describeTo(final Description description) {
            throw new UnsupportedOperationException();
        }
    }

}
