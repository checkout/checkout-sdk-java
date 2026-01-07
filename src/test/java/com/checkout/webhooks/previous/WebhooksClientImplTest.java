package com.checkout.webhooks.previous;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.lenient;
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
class WebhooksClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private WebhooksClient webhooksClient;

    private static final Type WEBHOOKS_TYPE = new TypeToken<ItemsResponse<WebhookResponse>>() {
    }.getType();

    @BeforeEach
    void setup() {
        lenient().when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        lenient().when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        this.webhooksClient = new WebhooksClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRetrieveWebhooks() throws ExecutionException, InterruptedException {
        final ItemsResponse response = createWebhooksResponse();
        when(apiClient.getAsync(eq("webhooks"), any(SdkAuthorization.class), eq(WEBHOOKS_TYPE)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ItemsResponse<WebhookResponse>> webhooks = webhooksClient.retrieveWebhooks();

        validateWebhooksResponse(webhooks.get(), response);
    }

    @Test
    void shouldRetrieveWebhooks_nullResponse() throws ExecutionException, InterruptedException {
        final ItemsResponse<WebhookResponse> response = createWebhooksResponse();
        when(apiClient.getAsync(eq("webhooks"), any(SdkAuthorization.class), eq(WEBHOOKS_TYPE)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ItemsResponse<WebhookResponse>> webhooks = webhooksClient.retrieveWebhooks();

        assertNotNull(webhooks.get());
        assertTrue(webhooks.get().getItems().isEmpty());
    }

    @Test
    void shouldRegisterWebhook() throws ExecutionException, InterruptedException {
        final WebhookRequest webhookRequest = createWebhookRequest();
        final WebhookResponse webhookResponse = createWebhookResponse();
        when(apiClient.postAsync(eq("webhooks"), any(SdkAuthorization.class), eq(WebhookResponse.class), eq(webhookRequest), isNull()))
                .thenReturn(CompletableFuture.completedFuture(webhookResponse));

        final CompletableFuture<WebhookResponse> webhooks = webhooksClient.registerWebhook(webhookRequest);

        validateWebhookResponse(webhooks.get(), webhookResponse);
    }

    @Test
    void registerWebhook_shouldThrowOnNullRequest() {
        validateExceptionMessage(() -> webhooksClient.registerWebhook(null), "webhookRequest cannot be null");
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRegisterWebhook_idempotencyKey() throws ExecutionException, InterruptedException {
        final WebhookRequest webhookRequest = createWebhookRequest();
        final WebhookResponse webhookResponse = createWebhookResponse();
        final String idempotencyKey = "ik";
        when(apiClient.postAsync(eq("webhooks"), any(SdkAuthorization.class), eq(WebhookResponse.class), eq(webhookRequest), eq(idempotencyKey)))
                .thenReturn(CompletableFuture.completedFuture(webhookResponse));

        final CompletableFuture<WebhookResponse> webhooks = webhooksClient.registerWebhook(webhookRequest, idempotencyKey);

        validateWebhookResponse(webhooks.get(), webhookResponse);
    }

    @Test
    void shouldRetrieveWebhook_idempotencyKey() throws ExecutionException, InterruptedException {
        final WebhookResponse webhookResponse = createWebhookResponse();
        final String webhookId = "webhook_id";
        when(apiClient.getAsync(eq("webhooks/" + webhookId), any(SdkAuthorization.class), eq(WebhookResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(webhookResponse));

        final CompletableFuture<WebhookResponse> webhooks = webhooksClient.retrieveWebhook(webhookId);

        validateWebhookResponse(webhooks.get(), webhookResponse);
    }

    @Test
    void retrieveWebhook_shouldThrowOnInvalidId() {
        validateExceptionMessage(() -> webhooksClient.retrieveWebhook(""), "webhookId cannot be blank");
        validateExceptionMessage(() -> webhooksClient.retrieveWebhook(null), "webhookId cannot be null");
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldUpdateWebhook() throws ExecutionException, InterruptedException {
        final String webhookId = "webhook_id";
        final WebhookRequest webhookRequest = createWebhookRequest();
        final WebhookResponse webhookResponse = createWebhookResponse();
        when(apiClient.putAsync(eq("webhooks/" + webhookId), any(SdkAuthorization.class), eq(WebhookResponse.class), eq(webhookRequest)))
                .thenReturn(CompletableFuture.completedFuture(webhookResponse));

        final CompletableFuture<WebhookResponse> webhooks = webhooksClient.updateWebhook(webhookId, webhookRequest);

        validateWebhookResponse(webhooks.get(), webhookResponse);
    }

    @Test
    void updateWebhook_shouldThrowOnNullRequest() {
        validateExceptionMessage(() -> webhooksClient.updateWebhook("id", null), "webhookRequest cannot be null");
        validateExceptionMessage(() -> webhooksClient.updateWebhook("", new WebhookRequest()), "webhookId cannot be blank");
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRemoveWebhook() throws ExecutionException, InterruptedException {
        final String webhookId = "webhook_id";
        final EmptyResponse emptyResponse = createEmptyResponse();
        when(apiClient.deleteAsync(eq("webhooks/" + webhookId), any(SdkAuthorization.class)))
                .thenReturn(CompletableFuture.completedFuture(emptyResponse));

        final CompletableFuture<EmptyResponse> webhooks = webhooksClient.removeWebhook(webhookId);

        validateEmptyResponse(webhooks.get());
    }

    @Test
    void removeWebhook_shouldThrowOnInvalidId() {
        validateExceptionMessage(() -> webhooksClient.removeWebhook(null), "webhookId cannot be null");
        validateExceptionMessage(() -> webhooksClient.removeWebhook(""), "webhookId cannot be blank");
        verifyNoInteractions(apiClient);
    }

    // Sync methods
    @Test
    void shouldRetrieveWebhooksSync() {
        final ItemsResponse<WebhookResponse> response = createWebhooksResponse();
        when(apiClient.get(eq("webhooks"), any(SdkAuthorization.class), eq(WEBHOOKS_TYPE)))
                .thenReturn(response);

        final ItemsResponse<WebhookResponse> webhooks = webhooksClient.retrieveWebhooksSync();

        validateWebhooksResponse(webhooks, response);
    }

    @Test
    void shouldRegisterWebhookSync() {
        final WebhookRequest webhookRequest = createWebhookRequest();
        final WebhookResponse webhookResponse = createWebhookResponse();
        when(apiClient.post(eq("webhooks"), any(SdkAuthorization.class), eq(WebhookResponse.class), eq(webhookRequest), isNull()))
                .thenReturn(webhookResponse);

        final WebhookResponse webhook = webhooksClient.registerWebhookSync(webhookRequest);

        validateWebhookResponse(webhook, webhookResponse);
    }

    @Test
    void registerWebhookSync_shouldThrowOnNullRequest() {
        validateExceptionMessage(() -> webhooksClient.registerWebhookSync(null), "webhookRequest cannot be null");
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRegisterWebhookSync_idempotencyKey() {
        final WebhookRequest webhookRequest = createWebhookRequest();
        final WebhookResponse webhookResponse = createWebhookResponse();
        final String idempotencyKey = "ik";
        when(apiClient.post(eq("webhooks"), any(SdkAuthorization.class), eq(WebhookResponse.class), eq(webhookRequest), eq(idempotencyKey)))
                .thenReturn(webhookResponse);

        final WebhookResponse webhook = webhooksClient.registerWebhookSync(webhookRequest, idempotencyKey);

        validateWebhookResponse(webhook, webhookResponse);
    }

    @Test
    void shouldRetrieveWebhookSync() {
        final WebhookResponse webhookResponse = createWebhookResponse();
        final String webhookId = "webhook_id";
        when(apiClient.get(eq("webhooks/" + webhookId), any(SdkAuthorization.class), eq(WebhookResponse.class)))
                .thenReturn(webhookResponse);

        final WebhookResponse webhook = webhooksClient.retrieveWebhookSync(webhookId);

        validateWebhookResponse(webhook, webhookResponse);
    }

    @Test
    void retrieveWebhookSync_shouldThrowOnInvalidId() {
        validateExceptionMessage(() -> webhooksClient.retrieveWebhookSync(""), "webhookId cannot be blank");
        validateExceptionMessage(() -> webhooksClient.retrieveWebhookSync(null), "webhookId cannot be null");
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldUpdateWebhookSync() {
        final String webhookId = "webhook_id";
        final WebhookRequest webhookRequest = createWebhookRequest();
        final WebhookResponse webhookResponse = createWebhookResponse();
        when(apiClient.put(eq("webhooks/" + webhookId), any(SdkAuthorization.class), eq(WebhookResponse.class), eq(webhookRequest)))
                .thenReturn(webhookResponse);

        final WebhookResponse webhook = webhooksClient.updateWebhookSync(webhookId, webhookRequest);

        validateWebhookResponse(webhook, webhookResponse);
    }

    @Test
    void updateWebhookSync_shouldThrowOnNullRequest() {
        validateExceptionMessage(() -> webhooksClient.updateWebhookSync("id", null), "webhookRequest cannot be null");
        validateExceptionMessage(() -> webhooksClient.updateWebhookSync("", new WebhookRequest()), "webhookId cannot be blank");
        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRemoveWebhookSync() {
        final String webhookId = "webhook_id";
        final EmptyResponse emptyResponse = createEmptyResponse();
        when(apiClient.delete(eq("webhooks/" + webhookId), any(SdkAuthorization.class)))
                .thenReturn(emptyResponse);

        final EmptyResponse result = webhooksClient.removeWebhookSync(webhookId);

        validateEmptyResponse(result);
    }

    @Test
    void removeWebhookSync_shouldThrowOnInvalidId() {
        validateExceptionMessage(() -> webhooksClient.removeWebhookSync(null), "webhookId cannot be null");
        validateExceptionMessage(() -> webhooksClient.removeWebhookSync(""), "webhookId cannot be blank");
        verifyNoInteractions(apiClient);
    }

    // Common methods
    private ItemsResponse<WebhookResponse> createWebhooksResponse() {
        return mock(ItemsResponse.class);
    }

    private WebhookRequest createWebhookRequest() {
        return mock(WebhookRequest.class);
    }

    private WebhookResponse createWebhookResponse() {
        return mock(WebhookResponse.class);
    }

    private EmptyResponse createEmptyResponse() {
        return mock(EmptyResponse.class);
    }

    private void validateWebhooksResponse(final ItemsResponse<WebhookResponse> actual, final ItemsResponse<WebhookResponse> expected) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateWebhookResponse(final WebhookResponse actual, final WebhookResponse expected) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateEmptyResponse(final EmptyResponse actual) {
        assertNotNull(actual);
    }

    private void validateExceptionMessage(final Runnable action, final String expectedMessage) {
        try {
            action.run();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals(expectedMessage, e.getMessage());
        }
    }

}