package com.checkout.events;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
import com.checkout.AbstractClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EventsClientImpl extends AbstractClient implements EventsClient {

    private static final String EVENTS = "events/";

    public EventsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, SecretKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<List<EventTypesResponse>> retrieveAllEventTypes(final String version) {
        String path = "event-types";
        if (version != null) {
            path += "?version=" + version;
        }
        return apiClient.getAsync(path, apiCredentials, EventTypesResponse[].class)
                .thenApply(it -> it == null ? new EventTypesResponse[0] : it)
                .thenApply(Arrays::asList);
    }

    @Override
    public CompletableFuture<EventsPageResponse> retrieveEvents(final Instant from, final Instant to, final Integer limit, final Integer skip, final String paymentId) {
        String path = "events";
        final List<String> parameters = new ArrayList<>();
        if (from != null) {
            parameters.add("from=" + from.toString());
        }
        if (to != null) {
            parameters.add("to=" + to.toString());
        }
        if (limit != null) {
            parameters.add("limit=" + limit.toString());
        }
        if (skip != null) {
            parameters.add("skip=" + skip.toString());
        }
        if (paymentId != null) {
            parameters.add("payment_id=" + paymentId);
        }

        if (!parameters.isEmpty()) {
            path += "?" + String.join("&", parameters);
        }

        return apiClient.getAsync(path, apiCredentials, EventsPageResponse.class);
    }

    @Override
    public CompletableFuture<EventResponse> retrieveEvent(final String eventId) {
        return apiClient.getAsync(EVENTS + eventId, apiCredentials, EventResponse.class);
    }

    @Override
    public CompletableFuture<EventNotificationResponse> retrieveEventNotification(final String eventId, final String notificationId) {
        return apiClient.getAsync(EVENTS + eventId + "/notifications/" + notificationId, apiCredentials, EventNotificationResponse.class);
    }

    @Override
    public CompletableFuture<Void> retryWebhook(final String eventId, final String webhookId) {
        return apiClient.postAsync(EVENTS + eventId + "/webhooks/" + webhookId + "/retry", apiCredentials, Void.class, null, null);
    }

    @Override
    public CompletableFuture<Void> retryAllWebhooks(final String eventId) {
        return apiClient.postAsync(EVENTS + eventId + "/webhooks/retry", apiCredentials, Void.class, null, null);
    }
}
