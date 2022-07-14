package com.checkout.events.previous;

import com.checkout.ApiClient;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventsClientImplTest {

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @Mock
    private ApiClient apiClient;

    private EventsClient eventsClient;

    private static final Type EVENT_TYPES_TYPE = new TypeToken<ItemsResponse<EventTypes>>() {
    }.getType();

    @BeforeEach
    void setup() {
        this.eventsClient = new EventsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRetrieveAllEventTypes_nullVersion() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final ItemsResponse response = mock(ItemsResponse.class);

        when(apiClient.getAsync("event-types", authorization, EVENT_TYPES_TYPE))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ItemsResponse<EventTypes>> eventTypes = eventsClient.retrieveAllEventTypes(null);

        assertNotNull(eventTypes.get());
        assertTrue(eventTypes.get().getItems().isEmpty());

    }

    @Test
    void shouldRetrieveAllEventTypes_withVersion() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final ItemsResponse response = mock(ItemsResponse.class);

        when(apiClient.getAsync("event-types?version=v2", authorization, EVENT_TYPES_TYPE))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ItemsResponse<EventTypes>> eventTypes = eventsClient.retrieveAllEventTypes("v2");

        assertNotNull(eventTypes.get());
        assertTrue(eventTypes.get().getItems().isEmpty());

    }

    @Test
    void shouldRetrieveAllEventTypes_nullResponse() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final ItemsResponse response = mock(ItemsResponse.class);

        when(apiClient.getAsync("event-types", authorization, EVENT_TYPES_TYPE))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ItemsResponse<EventTypes>> eventTypes = eventsClient.retrieveAllEventTypes(null);

        assertNotNull(eventTypes.get());
        assertTrue(eventTypes.get().getItems().isEmpty());

    }

    @Test
    void shouldRetrieveEvents() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final Instant from = LocalDateTime.now().minusYears(2).toInstant(ZoneOffset.UTC);
        final Instant to = LocalDateTime.now().toInstant(ZoneOffset.UTC);

        final RetrieveEventsRequest retrieveEventsRequest = RetrieveEventsRequest.builder()
                .from(from)
                .to(to)
                .limit(15)
                .skip(0)
                .paymentId("paymentId")
                .chargeId("chargeId")
                .trackId("trackId")
                .reference("reference")
                .build();

        final EventsPageResponse eventsPageResponse = mock(EventsPageResponse.class);

        when(apiClient.queryAsync("events", authorization, retrieveEventsRequest, EventsPageResponse.class))
                .thenReturn(CompletableFuture.completedFuture(eventsPageResponse));

        final CompletableFuture<EventsPageResponse> response = eventsClient.retrieveEvents(retrieveEventsRequest);

        assertNotNull(response.get());
        assertEquals(eventsPageResponse, response.get());

    }

    @Test
    void retrieveEvents_shouldThrowOnNullRequest() {

        try {
            eventsClient.retrieveEvents(null);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("retrieveEventsRequest cannot be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void shouldRetrieveEvent() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final EventResponse eventResponse = mock(EventResponse.class);

        when(apiClient.getAsync("events/eventId", authorization, EventResponse.class))
                .thenReturn(CompletableFuture.completedFuture(eventResponse));

        final CompletableFuture<EventResponse> response = eventsClient.retrieveEvent("eventId");

        assertNotNull(response.get());
        assertEquals(eventResponse, response.get());

    }

    @Test
    void retrieveEvent_shouldThrowIfEventIdIsNullOrEmpty() {

        try {
            eventsClient.retrieveEvent(null);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("eventId cannot be null", e.getMessage());
        }

        try {
            eventsClient.retrieveEvent("");
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("eventId cannot be blank", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void retrieveEventNotification_shouldThrowIfNotificationIdIsNullOrEmpty() {

        try {
            eventsClient.retrieveEventNotification("eventId", null);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("notificationId cannot be null", e.getMessage());
        }

        try {
            eventsClient.retrieveEventNotification("eventId", "");
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("notificationId cannot be blank", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }


    @Test
    void shouldRetrieveEventNotification() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final EventNotificationResponse eventNotificationResponse = mock(EventNotificationResponse.class);

        when(apiClient.getAsync("events/eventId/notifications/notificationId", authorization, EventNotificationResponse.class))
                .thenReturn(CompletableFuture.completedFuture(eventNotificationResponse));

        final CompletableFuture<EventNotificationResponse> response = eventsClient.retrieveEventNotification("eventId", "notificationId");

        assertNotNull(response.get());
        assertEquals(eventNotificationResponse, response.get());

    }

    @Test
    void shouldRetryWebhook() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        when(apiClient.postAsync(eq("events/eventId/webhooks/webhookId/retry"), eq(authorization), eq(EmptyResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(mock(EmptyResponse.class)));

        final CompletableFuture<EmptyResponse> response = eventsClient.retryWebhook("eventId", "webhookId");

        assertNotNull(response.get());

    }

    @Test
    void retryWebhook_shouldThrowIfEventIdIsNullOrEmpty() {

        try {
            eventsClient.retryWebhook(null, "notificationId");
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("eventId cannot be null", e.getMessage());
        }

        try {
            eventsClient.retryWebhook("", "notificationId");
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("eventId cannot be blank", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void retryWebhook_shouldThrowIfWebhookIdIsNullOrEmpty() {

        try {
            eventsClient.retryWebhook("eventId", null);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookId cannot be null", e.getMessage());
        }

        try {
            eventsClient.retryWebhook("eventId", "");
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookId cannot be blank", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void shouldRetryWebhooks() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        when(apiClient.postAsync(eq("events/eventId/webhooks/retry"), eq(authorization),
                eq(EmptyResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(mock(EmptyResponse.class)));

        final CompletableFuture<EmptyResponse> response = eventsClient.retryAllWebhooks("eventId");

        assertNotNull(response.get());

    }

}