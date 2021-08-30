package com.checkout.events;

import com.checkout.TestHelper;
import com.checkout.payments.CardSource;
import com.checkout.payments.PaymentProcessed;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.PaymentResponse;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.webhooks.EventType;
import com.checkout.webhooks.WebhookResponse;
import com.checkout.webhooks.WebhooksTestIT;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventsTestIT extends WebhooksTestIT {

    @Test
    public void retrieveDefaultEventTypes() {
        final List<EventTypesResponse> allEventTypesResponses = blocking(getApi().eventsClient().retrieveAllEventTypes(null));
        assertNotNull(allEventTypesResponses);
        assertEquals(2, allEventTypesResponses.size());
    }

    @Test
    public void retrieveV1EventTypes() {

        final List<EventTypesResponse> eventTypesResponses = blocking(getApi().eventsClient().retrieveAllEventTypes("1.0"));
        assertNotNull(eventTypesResponses);
        assertEquals(1, eventTypesResponses.size());

        final EventTypesResponse eventTypesResponse = eventTypesResponses.get(0);
        assertEquals("1.0", eventTypesResponse.getVersion());
        assertNotNull(eventTypesResponse.getEventTypes());
        assertFalse(eventTypesResponse.getEventTypes().isEmpty());

    }

    @Test
    public void retrieveV2EventTypes() {

        final List<EventTypesResponse> eventTypesResponses = blocking(getApi().eventsClient().retrieveAllEventTypes("2.0"));
        assertNotNull(eventTypesResponses);
        assertEquals(1, eventTypesResponses.size());

        final EventTypesResponse eventTypesResponse = eventTypesResponses.get(0);

        assertEquals("2.0", eventTypesResponse.getVersion());
        assertNotNull(eventTypesResponse.getEventTypes());
        assertFalse(eventTypesResponse.getEventTypes().isEmpty());

    }

    @Test
    public void shouldRetrieveEventsByPaymentId_deprecated() {

        registerWebhook();

        final String paymentId = makeCardPayment().getId();

        nap(15);

        final EventsPageResponse eventsPageResponse = blocking(
                getApi().eventsClient().retrieveEvents(
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
        assertEquals(EventType.PAYMENT_APPROVED.getCode(), eventSummaryResponse.getType());
        assertTrue(eventSummaryResponse.hasLink("self"));
        assertTrue(eventSummaryResponse.hasLink("webhooks-retry"));

    }

    @Test
    public void shouldRetrieveEventsByPaymentId_andRetrieveEventById_andGetNotification() {

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
        final EventsPageResponse eventsPageResponse = blocking(getApi().eventsClient().retrieveEvents(retrieveEventsRequest));

        assertNotNull(eventsPageResponse);
        assertEquals(1, eventsPageResponse.getTotalCount());
        assertEquals(15, eventsPageResponse.getLimit());
        assertEquals(0, eventsPageResponse.getSkip());
        assertEquals(1, eventsPageResponse.getData().size());

        final EventSummaryResponse eventSummaryResponse = eventsPageResponse.getData().get(0);
        assertNotNull(eventSummaryResponse.getId());
        assertNotNull(eventSummaryResponse.getCreatedOn());
        assertEquals(EventType.PAYMENT_APPROVED.getCode(), eventSummaryResponse.getType());
        assertTrue(eventSummaryResponse.hasLink("self"));
        assertTrue(eventSummaryResponse.hasLink("webhooks-retry"));

        // Retrieve Event
        final EventResponse eventResponse = blocking(getApi().eventsClient().retrieveEvent(eventSummaryResponse.getId()));

        assertNotNull(eventResponse);
        assertNotNull(eventResponse.getId());
        assertNotNull(eventResponse.getData());
        assertEquals(1, eventResponse.getNotifications().size());
        assertEquals(EventType.PAYMENT_APPROVED.getCode(), eventSummaryResponse.getType());
        assertTrue(eventResponse.hasLink("self"));
        assertTrue(eventResponse.hasLink("webhooks-retry"));

        // Get Notification
        final EventNotificationResponse eventNotificationResponse = blocking(getApi().eventsClient()
                .retrieveEventNotification(eventSummaryResponse.getId(), eventResponse.getNotifications().get(0).getId()));

        assertNotNull(eventNotificationResponse);
        assertNotNull(eventNotificationResponse.getId());
        assertNotNull(eventNotificationResponse.getUrl());
        assertFalse(eventNotificationResponse.getSuccess());
        assertFalse(eventNotificationResponse.getAttempts().isEmpty());
        assertTrue(eventNotificationResponse.hasLink("self"));
        assertTrue(eventNotificationResponse.hasLink("webhook-retry"));

    }

    @Test
    public void shouldRetryWebhook() {

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
        final EventsPageResponse eventsPageResponse = blocking(getApi().eventsClient().retrieveEvents(retrieveEventsRequest));
        assertNotNull(eventsPageResponse);

        final EventSummaryResponse eventSummaryResponse = eventsPageResponse.getData().get(0);
        assertNotNull(eventSummaryResponse.getId());

        // Retrieve Event
        final EventResponse eventResponse = blocking(getApi().eventsClient().retrieveEvent(eventSummaryResponse.getId()));

        // Retry Webhooks
        // Webhooks are not being re attempted. Adding the call to ensure.
        blocking(getApi().eventsClient().retryWebhook(eventSummaryResponse.getId(), webhookResponse.getId()));

        blocking(getApi().eventsClient().retryAllWebhooks(eventSummaryResponse.getId()));

    }

    public PaymentProcessed makeCardPayment() {
        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setThreeDS(ThreeDSRequest.from(false));
        final PaymentResponse paymentResponse = blocking(getApi().paymentsClient().requestAsync(paymentRequest));
        assertNotNull(paymentResponse.getPayment());
        return paymentResponse.getPayment();

    }

}