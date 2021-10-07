package com.checkout.events;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class EventsClientImpl extends AbstractClient implements EventsClient {

    private static final String EVENT_TYPES = "event-types";
    private static final String EVENTS = "events";
    private static final String NOTIFICATIONS = "notifications";
    private static final String WEBHOOKS = "webhooks";
    private static final String EVENT_ID = "eventId";

    public EventsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<List<EventTypesResponse>> retrieveAllEventTypes(final String version) {
        final StringBuilder path = new StringBuilder(EVENT_TYPES);
        if (version != null) {
            path.append("?version=").append(version);
        }
        return apiClient.getAsync(path.toString(), sdkAuthorization(), EventTypesResponse[].class)
                .thenApply(it -> it == null ? new EventTypesResponse[0] : it)
                .thenApply(Arrays::asList);
    }

    /**
     * @deprecated Please use {@link #retrieveEvents(RetrieveEventsRequest)}
     */
    @Deprecated
    @Override
    public CompletableFuture<EventsPageResponse> retrieveEvents(final Instant from,
                                                                final Instant to,
                                                                final Integer limit,
                                                                final Integer skip,
                                                                final String paymentId) {
        final RetrieveEventsRequest retrieveEventsRequest = RetrieveEventsRequest.builder()
                .from(from)
                .to(to)
                .limit(limit)
                .skip(skip)
                .paymentId(paymentId)
                .build();
        return apiClient.queryAsync(EVENTS, sdkAuthorization(), retrieveEventsRequest, EventsPageResponse.class);
    }

    @Override
    public CompletableFuture<EventsPageResponse> retrieveEvents(final RetrieveEventsRequest retrieveEventsRequest) {
        validateParams("retrieveEventsRequest", retrieveEventsRequest);
        return apiClient.queryAsync(EVENTS, sdkAuthorization(), retrieveEventsRequest, EventsPageResponse.class);
    }

    @Override
    public CompletableFuture<EventResponse> retrieveEvent(final String eventId) {
        validateParams(EVENT_ID, eventId);
        return apiClient.getAsync(buildPath(EVENTS, eventId), sdkAuthorization(), EventResponse.class);
    }

    @Override
    public CompletableFuture<EventNotificationResponse> retrieveEventNotification(final String eventId, final String notificationId) {
        validateParams(EVENT_ID, eventId, "notificationId", notificationId);
        return apiClient.getAsync(buildPath(EVENTS, eventId, NOTIFICATIONS, notificationId), sdkAuthorization(), EventNotificationResponse.class);
    }

    @Override
    public CompletableFuture<Void> retryWebhook(final String eventId, final String webhookId) {
        validateParams(EVENT_ID, eventId, "webhookId", webhookId);
        return apiClient.postAsync(buildPath(EVENTS, eventId, WEBHOOKS, webhookId, "retry"), sdkAuthorization(), Void.class, null, null);
    }

    @Override
    public CompletableFuture<Void> retryAllWebhooks(final String eventId) {
        validateParams(EVENT_ID, eventId);
        return apiClient.postAsync(buildPath(EVENTS, eventId, WEBHOOKS, "retry"), sdkAuthorization(), Void.class, null, null);
    }

}
