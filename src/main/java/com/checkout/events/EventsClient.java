package com.checkout.events;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EventsClient {

    CompletableFuture<List<EventTypesResponse>> retrieveAllEventTypes(String version);

    /**
     * Any parameter can be set to null, and the default will be used as per the REST API documentation
     */
    CompletableFuture<EventsPageResponse> retrieveEvents(Instant from, Instant to, Integer limit, Integer skip, String paymentId);

    CompletableFuture<EventResponse> retrieveEvent(String eventId);

    CompletableFuture<EventNotificationResponse> retrieveEventNotification(String eventId, String notificationId);

    CompletableFuture<Void> retryWebhook(String eventId, String webhookId);

    CompletableFuture<Void> retryAllWebhooks(String eventId);
}