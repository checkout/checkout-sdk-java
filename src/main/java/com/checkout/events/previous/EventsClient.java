package com.checkout.events.previous;

import java.util.concurrent.CompletableFuture;

import com.checkout.EmptyResponse;
import com.checkout.ItemsResponse;

public interface EventsClient {

    CompletableFuture<ItemsResponse<EventTypes>> retrieveAllEventTypes(String version);

    CompletableFuture<EventsPageResponse> retrieveEvents(RetrieveEventsRequest retrieveEventsRequest);

    CompletableFuture<EventResponse> retrieveEvent(String eventId);

    CompletableFuture<EventNotificationResponse> retrieveEventNotification(String eventId, String notificationId);

    CompletableFuture<EmptyResponse> retryWebhook(String eventId, String webhookId);

    CompletableFuture<EmptyResponse> retryAllWebhooks(String eventId);

    // Synchronous methods
    ItemsResponse<EventTypes> retrieveAllEventTypesSync(String version);

    EventsPageResponse retrieveEventsSync(RetrieveEventsRequest retrieveEventsRequest);

    EventResponse retrieveEventSync(String eventId);

    EventNotificationResponse retrieveEventNotificationSync(String eventId, String notificationId);

    EmptyResponse retryWebhookSync(String eventId, String webhookId);

    EmptyResponse retryAllWebhooksSync(String eventId);

}
