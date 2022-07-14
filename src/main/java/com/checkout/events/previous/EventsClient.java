package com.checkout.events.previous;

import com.checkout.EmptyResponse;
import com.checkout.ItemsResponse;

import java.util.concurrent.CompletableFuture;

public interface EventsClient {

    CompletableFuture<ItemsResponse<EventTypes>> retrieveAllEventTypes(String version);

    CompletableFuture<EventsPageResponse> retrieveEvents(RetrieveEventsRequest retrieveEventsRequest);

    CompletableFuture<EventResponse> retrieveEvent(String eventId);

    CompletableFuture<EventNotificationResponse> retrieveEventNotification(String eventId, String notificationId);

    CompletableFuture<EmptyResponse> retryWebhook(String eventId, String webhookId);

    CompletableFuture<EmptyResponse> retryAllWebhooks(String eventId);

}
