package com.checkout.events.previous;

import static com.checkout.common.CheckoutUtils.validateParams;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorizationType;
import com.google.gson.reflect.TypeToken;

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
        final StringBuilder path = buildEventTypesPath(version);
        return apiClient.getAsync(path.toString(), sdkAuthorization(), EVENT_TYPES_TYPE);
    }

    @Override
    public CompletableFuture<EventsPageResponse> retrieveEvents(final RetrieveEventsRequest retrieveEventsRequest) {
        validateRetrieveEventsRequest(retrieveEventsRequest);
        return apiClient.queryAsync(EVENTS_PATH, sdkAuthorization(), retrieveEventsRequest, EventsPageResponse.class);
    }

    @Override
    public CompletableFuture<EventResponse> retrieveEvent(final String eventId) {
        validateEventId(eventId);
        return apiClient.getAsync(buildPath(EVENTS_PATH, eventId), sdkAuthorization(), EventResponse.class);
    }

    @Override
    public CompletableFuture<EventNotificationResponse> retrieveEventNotification(final String eventId, final String notificationId) {
        validateEventIdAndNotificationId(eventId, notificationId);
        return apiClient.getAsync(buildPath(EVENTS_PATH, eventId, NOTIFICATIONS_PATH, notificationId), sdkAuthorization(), EventNotificationResponse.class);
    }

    @Override
    public CompletableFuture<EmptyResponse> retryWebhook(final String eventId, final String webhookId) {
        validateEventIdAndWebhookId(eventId, webhookId);
        return apiClient.postAsync(buildPath(EVENTS_PATH, eventId, WEBHOOKS_PATH, webhookId, "retry"), sdkAuthorization(), EmptyResponse.class, null, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> retryAllWebhooks(final String eventId) {
        validateEventId(eventId);
        return apiClient.postAsync(buildPath(EVENTS_PATH, eventId, WEBHOOKS_PATH, "retry"), sdkAuthorization(), EmptyResponse.class, null, null);
    }

    // Synchronous methods
    @Override
    public ItemsResponse<EventTypes> retrieveAllEventTypesSync(final String version) {
        final StringBuilder path = buildEventTypesPath(version);
        return apiClient.get(path.toString(), sdkAuthorization(), EVENT_TYPES_TYPE);
    }

    @Override
    public EventsPageResponse retrieveEventsSync(final RetrieveEventsRequest retrieveEventsRequest) {
        validateRetrieveEventsRequest(retrieveEventsRequest);
        return apiClient.query(EVENTS_PATH, sdkAuthorization(), retrieveEventsRequest, EventsPageResponse.class);
    }

    @Override
    public EventResponse retrieveEventSync(final String eventId) {
        validateEventId(eventId);
        return apiClient.get(buildPath(EVENTS_PATH, eventId), sdkAuthorization(), EventResponse.class);
    }

    @Override
    public EventNotificationResponse retrieveEventNotificationSync(final String eventId, final String notificationId) {
        validateEventIdAndNotificationId(eventId, notificationId);
        return apiClient.get(buildPath(EVENTS_PATH, eventId, NOTIFICATIONS_PATH, notificationId), sdkAuthorization(), EventNotificationResponse.class);
    }

    @Override
    public EmptyResponse retryWebhookSync(final String eventId, final String webhookId) {
        validateEventIdAndWebhookId(eventId, webhookId);
        return apiClient.post(buildPath(EVENTS_PATH, eventId, WEBHOOKS_PATH, webhookId, "retry"), sdkAuthorization(), EmptyResponse.class, null, null);
    }

    @Override
    public EmptyResponse retryAllWebhooksSync(final String eventId) {
        validateEventId(eventId);
        return apiClient.post(buildPath(EVENTS_PATH, eventId, WEBHOOKS_PATH, "retry"), sdkAuthorization(), EmptyResponse.class, null, null);
    }

    // Common methods
    private StringBuilder buildEventTypesPath(final String version) {
        final StringBuilder path = new StringBuilder(EVENT_TYPES_PATH);
        if (version != null) {
            path.append("?version=").append(version);
        }
        return path;
    }

    protected void validateRetrieveEventsRequest(final RetrieveEventsRequest retrieveEventsRequest) {
        validateParams("retrieveEventsRequest", retrieveEventsRequest);
    }

    protected void validateEventId(final String eventId) {
        validateParams(EVENT_ID, eventId);
    }

    protected void validateEventIdAndNotificationId(final String eventId, final String notificationId) {
        validateParams(EVENT_ID, eventId, "notificationId", notificationId);
    }

    protected void validateEventIdAndWebhookId(final String eventId, final String webhookId) {
        validateParams(EVENT_ID, eventId, "webhookId", webhookId);
    }

}

