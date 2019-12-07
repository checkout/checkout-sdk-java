package com.checkout.events;

import com.checkout.ApiTestFixture;
import com.checkout.TestHelper;
import com.checkout.payments.PaymentResponse;
import com.checkout.webhooks.WebhookRequest;
import com.checkout.webhooks.WebhookResponse;
import org.junit.Assert;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class EventsTests extends ApiTestFixture {

    @Test
    public void retrieve_all_event_types() throws Exception {
        List<EventTypesResponse> allEventTypesResponses = getApi().eventsClient().retrieveAllEventTypes(null).get();
        Assert.assertNotNull(allEventTypesResponses);
        Assert.assertEquals(2, allEventTypesResponses.size());
    }

    @Test
    public void retrieve_v1_event_types() throws Exception {
        List<EventTypesResponse> v1EventTypesResponses = getApi().eventsClient().retrieveAllEventTypes("1.0").get();
        Assert.assertNotNull(v1EventTypesResponses);
        Assert.assertEquals(1, v1EventTypesResponses.size());
        Assert.assertEquals("1.0", v1EventTypesResponses.get(0).getVersion());
        Assert.assertNotNull(v1EventTypesResponses.get(0).getEventTypes());
        Assert.assertFalse(v1EventTypesResponses.get(0).getEventTypes().isEmpty());
    }

    @Test
    public void retrieve_v2_event_types() throws Exception {
        List<EventTypesResponse> v2EventTypesResponses = getApi().eventsClient().retrieveAllEventTypes("2.0").get();
        Assert.assertNotNull(v2EventTypesResponses);
        Assert.assertEquals(1, v2EventTypesResponses.size());
        Assert.assertEquals("2.0", v2EventTypesResponses.get(0).getVersion());
        Assert.assertNotNull(v2EventTypesResponses.get(0).getEventTypes());
        Assert.assertFalse(v2EventTypesResponses.get(0).getEventTypes().isEmpty());
    }

    @Test
    public void retrieve_all_events() throws Exception {
        EventsPageResponse eventsPageResponse = getApi().eventsClient().retrieveEvents(null, null, null, null, null).get();
        Assert.assertNotNull(eventsPageResponse);
        Assert.assertNotNull(eventsPageResponse.getFrom());
        Assert.assertNotNull(eventsPageResponse.getTo());
        Assert.assertTrue(eventsPageResponse.getFrom().isBefore(eventsPageResponse.getTo()));
        Assert.assertTrue(0 < eventsPageResponse.getTotalCount());
        Assert.assertEquals(0, eventsPageResponse.getSkip());
        Assert.assertEquals(10, eventsPageResponse.getLimit());
        Assert.assertNotNull(eventsPageResponse.getData());
        Assert.assertEquals(10, eventsPageResponse.getData().size());
        Assert.assertNotNull(eventsPageResponse.getData().get(0).getId());
        Assert.assertNotNull(eventsPageResponse.getData().get(0).getType());
        Assert.assertNotNull(eventsPageResponse.getData().get(0).getCreatedOn());
        Assert.assertEquals(eventsPageResponse.getData().get(0).getCreatedOn(), eventsPageResponse.getTo());
    }

    @Test
    public void retrieve_some_events() throws Exception {
        Instant from = Instant.now().minus(90, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);
        Instant to = Instant.now().minus(7, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);
        int limit = 5;
        int skip = 2;
        EventsPageResponse eventsPageResponse = getApi().eventsClient().retrieveEvents(from, to, limit, skip, null).get();
        Assert.assertNotNull(eventsPageResponse);
        Assert.assertEquals(from, eventsPageResponse.getFrom());
        Assert.assertTrue(0 < eventsPageResponse.getTotalCount());
        Assert.assertEquals(skip, eventsPageResponse.getSkip());
        Assert.assertEquals(limit, eventsPageResponse.getLimit());
        Assert.assertNotNull(eventsPageResponse.getData());
        Assert.assertEquals(limit, eventsPageResponse.getData().size());
        Assert.assertNotNull(eventsPageResponse.getData().get(0).getId());
        Assert.assertNotNull(eventsPageResponse.getData().get(0).getType());
        Assert.assertNotNull(eventsPageResponse.getData().get(0).getCreatedOn());
        Assert.assertFalse(to.isBefore(eventsPageResponse.getData().get(0).getCreatedOn()));
        Assert.assertEquals(eventsPageResponse.getData().get(0).getCreatedOn(), eventsPageResponse.getTo());
    }

    @Test
    public void retrieve_specific_event() throws Exception {
        EventsPageResponse eventsPageResponse = getApi().eventsClient().retrieveEvents(null, null, null, null, null).get();
        String id = eventsPageResponse.getData().get(0).getId();

        EventResponse event = getApi().eventsClient().retrieveEvent(id).get();
        Assert.assertNotNull(event);
    }

    @Test
    public void retrieve_event_notification() throws Exception {
        String webhookUrl = "https://checkout.com/fail5";
        WebhookResponse webhook = getApi().webhooksClient().registerWebhook(WebhookRequest.builder()
                .url(webhookUrl)
                .eventTypes(getApi().eventsClient().retrieveAllEventTypes("2.0").get().get(0).getEventTypes())
                .build()).get();
        try {
            PaymentResponse payment = getApi().paymentsClient().requestAsync(TestHelper.createCardPaymentRequest()).get();
            final String paymentId = payment.getPayment().getId();

            // Sometimes the events aren't available immediately
            Thread.sleep(5000);
            final String eventId = getApi().eventsClient().retrieveEvents(null, null, null, null, paymentId).get().getData().get(0).getId();
            EventResponse event = getApi().eventsClient().retrieveEvent(eventId).get();
            Assert.assertNotNull(event.getNotifications());
            Assert.assertFalse(event.getNotifications().isEmpty());

            final String notificationId = event.getNotifications().get(0).getId();
            EventNotificationResponse notification = getApi().eventsClient().retrieveEventNotification(eventId, notificationId).get();
            Assert.assertNotNull(notification);
            Assert.assertNotNull(notification.getId());
            Assert.assertNotNull(notification.getSuccess());
            Assert.assertNotNull(notification.getContentType());
            Assert.assertNotNull(notification.getAttempts());
            Assert.assertFalse(notification.getAttempts().isEmpty());

            AttemptSummaryResponse attempt = notification.getAttempts().get(0);
            Assert.assertNotNull(attempt);
            Assert.assertNotNull(attempt.getTimestamp());
            Assert.assertNotNull(attempt.getResponseBody());
            Assert.assertNotNull(attempt.getRetryMode());
        } finally {
            getApi().webhooksClient().removeWebhook(webhook.getId()).get();
        }
    }

    @Test
    public void test_retry_specific_webhook() throws Exception {
        String webhookUrl = "https://checkout.com/fail3";
        WebhookResponse webhook = getApi().webhooksClient().registerWebhook(WebhookRequest.builder()
                .url(webhookUrl)
                .eventTypes(getApi().eventsClient().retrieveAllEventTypes("2.0").get().get(0).getEventTypes())
                .build()).get();
        try {
            PaymentResponse payment = getApi().paymentsClient().requestAsync(TestHelper.createCardPaymentRequest()).get();
            final String paymentId = payment.getPayment().getId();

            // Sometimes the events aren't available immediately
            Thread.sleep(5000);
            final String eventId = getApi().eventsClient().retrieveEvents(null, null, null, null, paymentId).get().getData().get(0).getId();
            EventResponse originalEvent = getApi().eventsClient().retrieveEvent(eventId).get();
            long initialAttempts = originalEvent.getNotifications().stream()
                    .filter(it -> webhookUrl.equals(it.getUrl()))
                    .count();

            getApi().eventsClient().retryWebhook(eventId, webhook.getId()).get();

            Thread.sleep(5000);
            EventResponse retriedEvent = getApi().eventsClient().retrieveEvent(eventId).get();
            long retriedAttempts = retriedEvent.getNotifications().stream()
                    .filter(it -> webhookUrl.equals(it.getUrl()))
                    .count();
            Assert.assertEquals(initialAttempts + 1, retriedAttempts);
        } finally {
            getApi().webhooksClient().removeWebhook(webhook.getId()).get();
        }
    }

    @Test
    public void test_retry_all_webhooks() throws Exception {
        String webhookUrl = "https://checkout.com/fail4";
        WebhookResponse webhook = getApi().webhooksClient().registerWebhook(WebhookRequest.builder()
                .url(webhookUrl)
                .eventTypes(getApi().eventsClient().retrieveAllEventTypes("2.0").get().get(0).getEventTypes())
                .build()).get();
        try {
            PaymentResponse payment = getApi().paymentsClient().requestAsync(TestHelper.createCardPaymentRequest()).get();
            final String paymentId = payment.getPayment().getId();

            // Sometimes the events aren't available immediately
            Thread.sleep(5000);
            final String eventId = getApi().eventsClient().retrieveEvents(null, null, null, null, paymentId).get().getData().get(0).getId();
            EventResponse originalEvent = getApi().eventsClient().retrieveEvent(eventId).get();
            long initialAttempts = originalEvent.getNotifications().stream()
                    .filter(it -> webhookUrl.equals(it.getUrl()))
                    .count();

            getApi().eventsClient().retryAllWebhooks(eventId).get();

            Thread.sleep(5000);
            EventResponse retriedEvent = getApi().eventsClient().retrieveEvent(eventId).get();
            long retriedAttempts = retriedEvent.getNotifications().stream()
                    .filter(it -> webhookUrl.equals(it.getUrl()))
                    .count();
            Assert.assertEquals(initialAttempts + 1, retriedAttempts);
        } finally {
            getApi().webhooksClient().removeWebhook(webhook.getId()).get();
        }
    }
}