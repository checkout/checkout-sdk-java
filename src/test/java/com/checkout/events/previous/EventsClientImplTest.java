package com.checkout.events.previous;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.checkout.ApiClient;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.google.gson.reflect.TypeToken;

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
        eventsClient = new EventsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRetrieveAllEventTypes_nullVersion() throws ExecutionException, InterruptedException {
        final ItemsResponse response = createMockItemsResponse();

        createMockSdkCredentials();
        when(apiClient.getAsync("event-types", authorization, EVENT_TYPES_TYPE))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ItemsResponse<EventTypes>> eventTypes = eventsClient.retrieveAllEventTypes(null);

        validateItemsResponse(response, eventTypes.get());
    }

    @Test
    void shouldRetrieveAllEventTypes_withVersion() throws ExecutionException, InterruptedException {
        final ItemsResponse response = createMockItemsResponse();

        createMockSdkCredentials();
        when(apiClient.getAsync("event-types?version=v2", authorization, EVENT_TYPES_TYPE))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ItemsResponse<EventTypes>> eventTypes = eventsClient.retrieveAllEventTypes("v2");

        validateItemsResponse(response, eventTypes.get());
    }

    @Test
    void shouldRetrieveEvents() throws ExecutionException, InterruptedException {
        final RetrieveEventsRequest retrieveEventsRequest = createMockRetrieveEventsRequest();
        final EventsPageResponse eventsPageResponse = createMockEventsPageResponse();

        createMockSdkCredentials();
        when(apiClient.queryAsync("events", authorization, retrieveEventsRequest, EventsPageResponse.class))
                .thenReturn(CompletableFuture.completedFuture(eventsPageResponse));

        final CompletableFuture<EventsPageResponse> response = eventsClient.retrieveEvents(retrieveEventsRequest);

        validateEventsPageResponse(eventsPageResponse, response.get());
    }

    @Test
    void retrieveEvents_shouldThrowOnNullRequest() {
        try {
            eventsClient.retrieveEvents(null);
        } catch (final Exception e) {
            validateArgumentException(e, "retrieveEventsRequest cannot be null");
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRetrieveEvent() throws ExecutionException, InterruptedException {
        final EventResponse eventResponse = createMockEventResponse();

        createMockSdkCredentials();
        when(apiClient.getAsync("events/eventId", authorization, EventResponse.class))
                .thenReturn(CompletableFuture.completedFuture(eventResponse));

        final CompletableFuture<EventResponse> response = eventsClient.retrieveEvent("eventId");

        validateEventResponse(eventResponse, response.get());
    }

    @Test
    void retrieveEvent_shouldThrowIfEventIdIsNullOrEmpty() {
        try {
            eventsClient.retrieveEvent(null);
        } catch (final Exception e) {
            validateArgumentException(e, "eventId cannot be null");
        }

        try {
            eventsClient.retrieveEvent("");
        } catch (final Exception e) {
            validateArgumentException(e, "eventId cannot be blank");
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRetrieveEventNotification() throws ExecutionException, InterruptedException {
        final EventNotificationResponse eventNotificationResponse = createMockEventNotificationResponse();

        createMockSdkCredentials();
        when(apiClient.getAsync("events/eventId/notifications/notificationId", authorization, EventNotificationResponse.class))
                .thenReturn(CompletableFuture.completedFuture(eventNotificationResponse));

        final CompletableFuture<EventNotificationResponse> response = eventsClient.retrieveEventNotification("eventId", "notificationId");

        validateEventNotificationResponse(eventNotificationResponse, response.get());
    }

    @Test
    void retrieveEventNotification_shouldThrowIfNotificationIdIsNullOrEmpty() {
        try {
            eventsClient.retrieveEventNotification("eventId", null);
        } catch (final Exception e) {
            validateArgumentException(e, "notificationId cannot be null");
        }

        try {
            eventsClient.retrieveEventNotification("eventId", "");
        } catch (final Exception e) {
            validateArgumentException(e, "notificationId cannot be blank");
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRetryWebhook() throws ExecutionException, InterruptedException {
        final EmptyResponse emptyResponse = createMockEmptyResponse();

        createMockSdkCredentials();
        when(apiClient.postAsync(eq("events/eventId/webhooks/webhookId/retry"), eq(authorization),
                eq(EmptyResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(emptyResponse));

        final CompletableFuture<EmptyResponse> response = eventsClient.retryWebhook("eventId", "webhookId");

        validateEmptyResponse(emptyResponse, response.get());
    }

    @Test
    void retryWebhook_shouldThrowIfEventIdIsNullOrEmpty() {
        try {
            eventsClient.retryWebhook(null, "webhookId");
        } catch (final Exception e) {
            validateArgumentException(e, "eventId cannot be null");
        }

        try {
            eventsClient.retryWebhook("", "webhookId");
        } catch (final Exception e) {
            validateArgumentException(e, "eventId cannot be blank");
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void retryWebhook_shouldThrowIfWebhookIdIsNullOrEmpty() {
        try {
            eventsClient.retryWebhook("eventId", null);
        } catch (final Exception e) {
            validateArgumentException(e, "webhookId cannot be null");
        }

        try {
            eventsClient.retryWebhook("eventId", "");
        } catch (final Exception e) {
            validateArgumentException(e, "webhookId cannot be blank");
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRetryAllWebhooks() throws ExecutionException, InterruptedException {
        final EmptyResponse emptyResponse = createMockEmptyResponse();

        createMockSdkCredentials();
        when(apiClient.postAsync(eq("events/eventId/webhooks/retry"), eq(authorization),
                eq(EmptyResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(emptyResponse));

        final CompletableFuture<EmptyResponse> response = eventsClient.retryAllWebhooks("eventId");

        validateEmptyResponse(emptyResponse, response.get());
    }

    // Synchronous methods
    @Test
    void shouldRetrieveAllEventTypesSync_nullVersion() {
        final ItemsResponse response = createMockItemsResponse();

        createMockSdkCredentials();
        when(apiClient.get("event-types", authorization, EVENT_TYPES_TYPE))
                .thenReturn(response);

        final ItemsResponse<EventTypes> eventTypes = eventsClient.retrieveAllEventTypesSync(null);

        validateItemsResponse(response, eventTypes);
    }

    @Test
    void shouldRetrieveAllEventTypesSync_withVersion() {
        final ItemsResponse response = createMockItemsResponse();

        createMockSdkCredentials();
        when(apiClient.get("event-types?version=v2", authorization, EVENT_TYPES_TYPE))
                .thenReturn(response);

        final ItemsResponse<EventTypes> eventTypes = eventsClient.retrieveAllEventTypesSync("v2");

        validateItemsResponse(response, eventTypes);
    }

    @Test
    void shouldRetrieveEventsSync() {
        final RetrieveEventsRequest retrieveEventsRequest = createMockRetrieveEventsRequest();
        final EventsPageResponse eventsPageResponse = createMockEventsPageResponse();

        createMockSdkCredentials();
        when(apiClient.query("events", authorization, retrieveEventsRequest, EventsPageResponse.class))
                .thenReturn(eventsPageResponse);

        final EventsPageResponse response = eventsClient.retrieveEventsSync(retrieveEventsRequest);

        validateEventsPageResponse(eventsPageResponse, response);
    }

    @Test
    void retrieveEventsSync_shouldThrowOnNullRequest() {
        try {
            eventsClient.retrieveEventsSync(null);
        } catch (final Exception e) {
            validateArgumentException(e, "retrieveEventsRequest cannot be null");
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRetrieveEventSync() {
        final EventResponse eventResponse = createMockEventResponse();

        createMockSdkCredentials();
        when(apiClient.get("events/eventId", authorization, EventResponse.class))
                .thenReturn(eventResponse);

        final EventResponse response = eventsClient.retrieveEventSync("eventId");

        validateEventResponse(eventResponse, response);
    }

    @Test
    void retrieveEventSync_shouldThrowIfEventIdIsNullOrEmpty() {
        try {
            eventsClient.retrieveEventSync(null);
        } catch (final Exception e) {
            validateArgumentException(e, "eventId cannot be null");
        }

        try {
            eventsClient.retrieveEventSync("");
        } catch (final Exception e) {
            validateArgumentException(e, "eventId cannot be blank");
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRetrieveEventNotificationSync() {
        final EventNotificationResponse eventNotificationResponse = createMockEventNotificationResponse();

        createMockSdkCredentials();
        when(apiClient.get("events/eventId/notifications/notificationId", authorization, EventNotificationResponse.class))
                .thenReturn(eventNotificationResponse);

        final EventNotificationResponse response = eventsClient.retrieveEventNotificationSync("eventId", "notificationId");

        validateEventNotificationResponse(eventNotificationResponse, response);
    }

    @Test
    void retrieveEventNotificationSync_shouldThrowIfNotificationIdIsNullOrEmpty() {
        try {
            eventsClient.retrieveEventNotificationSync("eventId", null);
        } catch (final Exception e) {
            validateArgumentException(e, "notificationId cannot be null");
        }

        try {
            eventsClient.retrieveEventNotificationSync("eventId", "");
        } catch (final Exception e) {
            validateArgumentException(e, "notificationId cannot be blank");
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRetryWebhookSync() {
        final EmptyResponse emptyResponse = createMockEmptyResponse();

        createMockSdkCredentials();
        when(apiClient.post(eq("events/eventId/webhooks/webhookId/retry"), eq(authorization),
                eq(EmptyResponse.class), isNull(), isNull()))
                .thenReturn(emptyResponse);

        final EmptyResponse response = eventsClient.retryWebhookSync("eventId", "webhookId");

        validateEmptyResponse(emptyResponse, response);
    }

    @Test
    void retryWebhookSync_shouldThrowIfEventIdIsNullOrEmpty() {
        try {
            eventsClient.retryWebhookSync(null, "webhookId");
        } catch (final Exception e) {
            validateArgumentException(e, "eventId cannot be null");
        }

        try {
            eventsClient.retryWebhookSync("", "webhookId");
        } catch (final Exception e) {
            validateArgumentException(e, "eventId cannot be blank");
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void retryWebhookSync_shouldThrowIfWebhookIdIsNullOrEmpty() {
        try {
            eventsClient.retryWebhookSync("eventId", null);
        } catch (final Exception e) {
            validateArgumentException(e, "webhookId cannot be null");
        }

        try {
            eventsClient.retryWebhookSync("eventId", "");
        } catch (final Exception e) {
            validateArgumentException(e, "webhookId cannot be blank");
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRetryAllWebhooksSync() {
        final EmptyResponse emptyResponse = createMockEmptyResponse();

        createMockSdkCredentials();
        when(apiClient.post(eq("events/eventId/webhooks/retry"), eq(authorization),
                eq(EmptyResponse.class), isNull(), isNull()))
                .thenReturn(emptyResponse);

        final EmptyResponse response = eventsClient.retryAllWebhooksSync("eventId");

        validateEmptyResponse(emptyResponse, response);
    }

    // Common methods
    private void createMockSdkCredentials() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);        
    }
    private RetrieveEventsRequest createMockRetrieveEventsRequest() {
        return RetrieveEventsRequest.builder()
                .limit(15)
                .skip(0)
                .paymentId("paymentId")
                .chargeId("chargeId")
                .trackId("trackId")
                .reference("reference")
                .build();
    }

    private ItemsResponse createMockItemsResponse() {
        return mock(ItemsResponse.class);
    }

    private EventsPageResponse createMockEventsPageResponse() {
        return mock(EventsPageResponse.class);
    }

    private EventResponse createMockEventResponse() {
        return mock(EventResponse.class);
    }

    private EventNotificationResponse createMockEventNotificationResponse() {
        return mock(EventNotificationResponse.class);
    }

    private EmptyResponse createMockEmptyResponse() {
        return mock(EmptyResponse.class);
    }

    private void validateItemsResponse(ItemsResponse expected, ItemsResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateEventsPageResponse(EventsPageResponse expected, EventsPageResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateEventResponse(EventResponse expected, EventResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateEventNotificationResponse(EventNotificationResponse expected, EventNotificationResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateEmptyResponse(EmptyResponse expected, EmptyResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateArgumentException(Exception exception, String expectedMessage) {
        assertTrue(exception instanceof CheckoutArgumentException);
        assertEquals(expectedMessage, exception.getMessage());
    }

}