package com.checkout.events.previous;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorizationType;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class EventsClientImpl extends AbstractClient implements EventsClient {

    private static final String EVENT_TYPES_PATH = "event-types";
    private static final String EVENTS_PATH = "events";
    private static final String NOTIFICATIONS_PATH = "notifications";
    private static final String WEBHOOKS_PATH = "webhooks";
    private static final String EVENT_ID = "eventId";

    private static final Type EVENT_TYPES_TYPE = new TypeToken<ItemsResponse<EventTypes>>() {
    }.getType();

    public EventsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<ItemsResponse<EventTypes>> retrieveAllEventTypes(final String version) {
        final StringBuilder path = new StringBuilder(EVENT_TYPES_PATH);
        if (version != null) {
            path.append("?version=").append(version);
        }
        return apiClient.getAsync(path.toString(), sdkAuthorization(), EVENT_TYPES_TYPE);
    }

    @Override
    public CompletableFuture<EventsPageResponse> retrieveEvents(final RetrieveEventsRequest retrieveEventsRequest) {
        validateParams("retrieveEventsRequest", retrieveEventsRequest);
        return apiClient.queryAsync(EVENTS_PATH, sdkAuthorization(), retrieveEventsRequest, EventsPageResponse.class);
    }

    @Override
    public CompletableFuture<EventResponse> retrieveEvent(final String eventId) {
        validateParams(EVENT_ID, eventId);
        return apiClient.getAsync(buildPath(EVENTS_PATH, eventId), sdkAuthorization(), EventResponse.class);
    }

    @Override
    public CompletableFuture<EventNotificationResponse> retrieveEventNotification(final String eventId, final String notificationId) {
        validateParams(EVENT_ID, eventId, "notificationId", notificationId);
        return apiClient.getAsync(buildPath(EVENTS_PATH, eventId, NOTIFICATIONS_PATH, notificationId), sdkAuthorization(), EventNotificationResponse.class);
    }

    @Override
    public CompletableFuture<EmptyResponse> retryWebhook(final String eventId, final String webhookId) {
        validateParams(EVENT_ID, eventId, "webhookId", webhookId);
        return apiClient.postAsync(buildPath(EVENTS_PATH, eventId, WEBHOOKS_PATH, webhookId, "retry"), sdkAuthorization(), EmptyResponse.class, null, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> retryAllWebhooks(final String eventId) {
        validateParams(EVENT_ID, eventId);
        return apiClient.postAsync(buildPath(EVENTS_PATH, eventId, WEBHOOKS_PATH, "retry"), sdkAuthorization(), EmptyResponse.class, null, null);
    }

}
