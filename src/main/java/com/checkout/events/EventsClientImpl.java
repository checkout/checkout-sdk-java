package com.checkout.events;

import com.checkout.ApiClient;
import com.checkout.ApiCredentials;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class EventsClientImpl implements EventsClient {

    private final ApiClient apiClient;
    private final ApiCredentials credentials;

    public EventsClientImpl(ApiClient apiClient, CheckoutConfiguration configuration) {
        if (apiClient == null) {
            throw new IllegalArgumentException("apiClient must not be null");
        }
        if (configuration == null) {
            throw new IllegalArgumentException("configuration must not be null");
        }

        this.apiClient = apiClient;
        credentials = new SecretKeyCredentials(configuration);
    }

    @Override
    public CompletableFuture<List<EventTypesResponse>> retrieveAllEventTypes(String version) {
        String path = "event-types";
        if (version != null) {
            path += "?version=" + version;
        }
        return apiClient.getAsync(path, credentials, EventTypesResponse[].class)
                .thenApply(it -> it == null ? new EventTypesResponse[0] : it)
                .thenApply(Arrays::asList);
    }

    @Override
    public CompletableFuture<EventsPageResponse> retrieveEvents(Instant from, Instant to, Integer limit, Integer skip, String paymentId) {
        String path = "events";
        List<String> parameters = new ArrayList<>();
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

        return apiClient.getAsync(path, credentials, EventsPageResponse.class);
    }

    @Override
    public CompletableFuture<EventResponse> retrieveEvent(String eventId) {
        return apiClient.getAsync("events/" + eventId, credentials, EventResponse.class);
    }

    @Override
    public CompletableFuture<EventNotificationResponse> retrieveEventNotification(String eventId, String notificationId) {
        return apiClient.getAsync("events/" + eventId + "/notifications/" + notificationId, credentials, EventNotificationResponse.class);
    }

    @Override
    public CompletableFuture<Void> retryWebhook(String eventId, String webhookId) {
        return apiClient.postAsync("events/" + eventId + "/webhooks/" + webhookId + "/retry", credentials, Void.class, null, null);
    }

    @Override
    public CompletableFuture<Void> retryAllWebhooks(String eventId) {
        return apiClient.postAsync("events/" + eventId + "/webhooks/retry", credentials, Void.class, null, null);
    }
}
