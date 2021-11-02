package com.checkout.events;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.payments.CardSource;
import com.checkout.payments.PaymentProcessed;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.PaymentResponse;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.webhooks.WebhookRequest;
import com.checkout.webhooks.WebhookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventsTestIT extends SandboxTestFixture {

    private static final List<String> GATEWAY_EVENT_TYPES = Arrays.asList("payment_approved", "payment_pending",
            "payment_declined", "payment_expired", "payment_canceled", "payment_voided", "payment_void_declined",
            "payment_captured", "payment_capture_declined", "payment_capture_pending", "payment_refunded",
            "payment_refund_declined", "payment_refund_pending");

    public EventsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @BeforeEach
    protected void cleanup() {
        final List<WebhookResponse> webhookResponses = blocking(defaultApi.webhooksClient().retrieveWebhooks());
        webhookResponses.forEach(webhookResponse -> blocking(defaultApi.webhooksClient().removeWebhook(webhookResponse.getId())));
    }

    @Test
    void retrieveDefaultEventTypes() {
        final List<EventTypesResponse> allEventTypesResponses = blocking(defaultApi.eventsClient().retrieveAllEventTypes(null));
        assertNotNull(allEventTypesResponses);
        assertEquals(2, allEventTypesResponses.size());
    }

    @Test
    void retrieveV1EventTypes() {

        final List<EventTypesResponse> eventTypesResponses = blocking(defaultApi.eventsClient().retrieveAllEventTypes("1.0"));
        assertNotNull(eventTypesResponses);
        assertEquals(1, eventTypesResponses.size());

        final EventTypesResponse eventTypesResponse = eventTypesResponses.get(0);
        assertEquals("1.0", eventTypesResponse.getVersion());
        assertNotNull(eventTypesResponse.getEventTypes());
        assertFalse(eventTypesResponse.getEventTypes().isEmpty());

    }

    @Test
    void retrieveV2EventTypes() {

        final List<EventTypesResponse> eventTypesResponses = blocking(defaultApi.eventsClient().retrieveAllEventTypes("2.0"));
        assertNotNull(eventTypesResponses);
        assertEquals(1, eventTypesResponses.size());

        final EventTypesResponse eventTypesResponse = eventTypesResponses.get(0);

        assertEquals("2.0", eventTypesResponse.getVersion());
        assertNotNull(eventTypesResponse.getEventTypes());
        assertFalse(eventTypesResponse.getEventTypes().isEmpty());

    }

    @Test
    void shouldRetrieveEventsByPaymentId_deprecated() {

        registerWebhook();

        final String paymentId = makeCardPayment().getId();

        nap(15);

        final EventsPageResponse eventsPageResponse = blocking(
                defaultApi.eventsClient().retrieveEvents(
                        LocalDateTime.now().minusYears(2).toInstant(ZoneOffset.UTC),
                        LocalDateTime.now().toInstant(ZoneOffset.UTC),
                        10, 0, paymentId));

        assertNotNull(eventsPageResponse);
        assertEquals(1, eventsPageResponse.getTotalCount());
        assertEquals(10, eventsPageResponse.getLimit());
        assertEquals(0, eventsPageResponse.getSkip());
        assertEquals(1, eventsPageResponse.getData().size());

        final EventSummaryResponse eventSummaryResponse = eventsPageResponse.getData().get(0);
        assertNotNull(eventSummaryResponse.getId());
        assertNotNull(eventSummaryResponse.getCreatedOn());
        assertEquals("payment_approved", eventSummaryResponse.getType());
        assertNotNull(eventSummaryResponse.getLink("self"));
        assertNotNull(eventSummaryResponse.getLink("webhooks-retry"));

    }

    @Test
    void shouldRetrieveEventsByPaymentId_andRetrieveEventById_andGetNotification() {

        registerWebhook();

        final String paymentId = makeCardPayment().getId();

        nap(15);

        final RetrieveEventsRequest retrieveEventsRequest = RetrieveEventsRequest.builder()
                .from(LocalDateTime.now().minusYears(2).toInstant(ZoneOffset.UTC))
                .to(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .limit(15)
                .skip(0)
                .paymentId(paymentId)
                .build();

        // Retrieve Events
        final EventsPageResponse eventsPageResponse = blocking(defaultApi.eventsClient().retrieveEvents(retrieveEventsRequest));

        assertNotNull(eventsPageResponse);
        assertEquals(1, eventsPageResponse.getTotalCount());
        assertEquals(15, eventsPageResponse.getLimit());
        assertEquals(0, eventsPageResponse.getSkip());
        assertEquals(1, eventsPageResponse.getData().size());

        final EventSummaryResponse eventSummaryResponse = eventsPageResponse.getData().get(0);
        assertNotNull(eventSummaryResponse.getId());
        assertNotNull(eventSummaryResponse.getCreatedOn());
        assertEquals("payment_approved", eventSummaryResponse.getType());
        assertNotNull(eventSummaryResponse.getLink("self"));
        assertNotNull(eventSummaryResponse.getLink("webhooks-retry"));

        // Retrieve Event
        final EventResponse eventResponse = blocking(defaultApi.eventsClient().retrieveEvent(eventSummaryResponse.getId()));

        assertNotNull(eventResponse);
        assertNotNull(eventResponse.getId());
        assertNotNull(eventResponse.getData());
        assertEquals(1, eventResponse.getNotifications().size());
        assertEquals("payment_approved", eventSummaryResponse.getType());
        assertNotNull(eventResponse.getLink("self"));
        assertNotNull(eventResponse.getLink("webhooks-retry"));

        // Get Notification
        final EventNotificationResponse eventNotificationResponse = blocking(defaultApi.eventsClient()
                .retrieveEventNotification(eventSummaryResponse.getId(), eventResponse.getNotifications().get(0).getId()));

        assertNotNull(eventNotificationResponse);
        assertNotNull(eventNotificationResponse.getId());
        assertNotNull(eventNotificationResponse.getUrl());
        assertFalse(eventNotificationResponse.getSuccess());
        assertFalse(eventNotificationResponse.getAttempts().isEmpty());
        assertNotNull(eventNotificationResponse.getLink("self"));
        assertNotNull(eventNotificationResponse.getLink("webhook-retry"));

    }

    @Test
    void shouldRetryWebhook() {

        final WebhookResponse webhookResponse = registerWebhook();

        final String paymentId = makeCardPayment().getId();

        nap(15);

        final RetrieveEventsRequest retrieveEventsRequest = RetrieveEventsRequest.builder()
                .from(LocalDateTime.now().minusYears(2).toInstant(ZoneOffset.UTC))
                .to(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .limit(15)
                .skip(0)
                .paymentId(paymentId)
                .build();

        // Retrieve Events
        final EventsPageResponse eventsPageResponse = blocking(defaultApi.eventsClient().retrieveEvents(retrieveEventsRequest));
        assertNotNull(eventsPageResponse);

        final EventSummaryResponse eventSummaryResponse = eventsPageResponse.getData().get(0);
        assertNotNull(eventSummaryResponse.getId());

        // Retrieve Event
        final EventResponse eventResponse = blocking(defaultApi.eventsClient().retrieveEvent(eventSummaryResponse.getId()));

        // Retry Webhooks
        // Webhooks are not being re attempted. Adding the call to ensure.
        blocking(defaultApi.eventsClient().retryWebhook(eventSummaryResponse.getId(), webhookResponse.getId()));

        blocking(defaultApi.eventsClient().retryAllWebhooks(eventSummaryResponse.getId()));

    }

    protected WebhookResponse registerWebhook() {

        final WebhookRequest webhookRequest = WebhookRequest.builder()
                .url("https://google.com/fail")
                .build();

        webhookRequest.setEventTypes(GATEWAY_EVENT_TYPES);

        return blocking(defaultApi.webhooksClient().registerWebhook(webhookRequest));

    }

    private PaymentProcessed makeCardPayment() {
        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setThreeDS(new ThreeDSRequest());
        final PaymentResponse paymentResponse = blocking(defaultApi.paymentsClient().requestAsync(paymentRequest));
        assertNotNull(paymentResponse.getPayment());
        return paymentResponse.getPayment();

    }

}