package com.checkout.events;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.requiresNonBlank;
import static com.checkout.common.CheckoutUtils.requiresNonNull;

public class EventsClientImpl extends AbstractClient implements EventsClient {

    private static final String EVENT_TYPES = "event-types";
    private static final String EVENTS = "events";
    private static final String NOTIFICATIONS = "notifications";
    private static final String WEBHOOKS = "webhooks";

    public EventsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, SecretKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<List<EventTypesResponse>> retrieveAllEventTypes(final String version) {
        final StringBuilder path = new StringBuilder(EVENT_TYPES);
        if (version != null) {
            path.append("?version=").append(version);
        }
        return apiClient.getAsync(path.toString(), apiCredentials, EventTypesResponse[].class)
                .thenApply(it -> it == null ? new EventTypesResponse[0] : it)
                .thenApply(Arrays::asList);
    }

    @Deprecated
    @Override
    public CompletableFuture<EventsPageResponse> retrieveEvents(final Instant from, final Instant to, final Integer limit, final Integer skip, final String paymentId) {
        final RetrieveEventsRequest retrieveEventsRequest = RetrieveEventsRequest.builder()
                .from(from)
                .to(to)
                .limit(limit)
                .skip(skip)
                .paymentId(paymentId)
                .build();
        return apiClient.queryAsync(EVENTS, apiCredentials, retrieveEventsRequest, EventsPageResponse.class);
    }

    @Override
    public CompletableFuture<EventsPageResponse> retrieveEvents(final RetrieveEventsRequest retrieveEventsRequest) {
        requiresNonNull("retrieveEventsRequest", retrieveEventsRequest);
        return apiClient.queryAsync(EVENTS, apiCredentials, retrieveEventsRequest, EventsPageResponse.class);
    }

    @Override
    public CompletableFuture<EventResponse> retrieveEvent(final String eventId) {
        requiresNonBlank("eventId", eventId);
        return apiClient.getAsync(constructApiPath(EVENTS, eventId), apiCredentials, EventResponse.class);
    }

    @Override
    public CompletableFuture<EventNotificationResponse> retrieveEventNotification(final String eventId, final String notificationId) {
        requiresNonBlank("eventId", eventId);
        requiresNonBlank("notificationId", notificationId);
        return apiClient.getAsync(constructApiPath(EVENTS, eventId, NOTIFICATIONS, notificationId), apiCredentials, EventNotificationResponse.class);
    }

    @Override
    public CompletableFuture<Void> retryWebhook(final String eventId, final String webhookId) {
        requiresNonBlank("eventId", eventId);
        requiresNonBlank("webhookId", webhookId);
        return apiClient.postAsync(constructApiPath(EVENTS, eventId, WEBHOOKS, webhookId, "retry"), apiCredentials, Void.class, null, null);
    }

    @Override
    public CompletableFuture<Void> retryAllWebhooks(final String eventId) {
        requiresNonBlank("eventId", eventId);
        return apiClient.postAsync(constructApiPath(EVENTS, eventId, WEBHOOKS, "retry"), apiCredentials, Void.class, null, null);
    }

}
