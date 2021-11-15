package com.checkout.events;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EventsClient {

    CompletableFuture<List<EventTypesResponse>> retrieveAllEventTypes(String version);

    CompletableFuture<EventsPageResponse> retrieveEvents(RetrieveEventsRequest retrieveEventsRequest);

    CompletableFuture<EventResponse> retrieveEvent(String eventId);

    CompletableFuture<EventNotificationResponse> retrieveEventNotification(String eventId, String notificationId);

    CompletableFuture<Void> retryWebhook(String eventId, String webhookId);

    CompletableFuture<Void> retryAllWebhooks(String eventId);

}
