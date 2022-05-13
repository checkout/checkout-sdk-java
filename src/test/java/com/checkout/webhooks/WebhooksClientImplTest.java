package com.checkout.webhooks;

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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebhooksClientImplTest {

    private static final String WEBHOOKS = "webhooks";

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
        this.webhooksClient = new WebhooksClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRetrieveWebhooks() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final ItemsResponse response = mock(ItemsResponse.class);

        when(apiClient.getAsync(eq(WEBHOOKS), any(SdkAuthorization.class), eq(WEBHOOKS_TYPE)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ItemsResponse<WebhookResponse>> webhooks = webhooksClient.retrieveWebhooks();

        assertNotNull(webhooks.get());
        assertEquals(response, webhooks.get());

    }

    @Test
    void shouldRetrieveWebhooks_nullResponse() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final ItemsResponse response = mock(ItemsResponse.class);

        when(apiClient.getAsync(eq(WEBHOOKS), any(SdkAuthorization.class), eq(WEBHOOKS_TYPE)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ItemsResponse<WebhookResponse>> webhooks = webhooksClient.retrieveWebhooks();

        assertNotNull(webhooks.get());
        assertTrue(webhooks.get().getItems().isEmpty());
    }

    @Test
    void shouldRegisterWebhook() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final WebhookRequest webhookRequest = mock(WebhookRequest.class);
        final WebhookResponse webhookResponse = mock(WebhookResponse.class);

        when(apiClient.postAsync(eq(WEBHOOKS), any(SdkAuthorization.class), eq(WebhookResponse.class), eq(webhookRequest), isNull()))
                .thenReturn(CompletableFuture.completedFuture(webhookResponse));

        final CompletableFuture<WebhookResponse> webhooks = webhooksClient.registerWebhook(webhookRequest);

        assertNotNull(webhooks.get());
        assertEquals(webhookResponse, webhooks.get());

    }

    @Test
    void registerWebhook_shouldThrowOnNullRequest() {

        try {
            webhooksClient.registerWebhook(null);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookRequest cannot be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void shouldRegisterWebhook_idempotencyKey() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final WebhookRequest webhookRequest = mock(WebhookRequest.class);
        final WebhookResponse webhookResponse = mock(WebhookResponse.class);

        when(apiClient.postAsync(eq(WEBHOOKS), any(SdkAuthorization.class), eq(WebhookResponse.class), eq(webhookRequest), eq("ik")))
                .thenReturn(CompletableFuture.completedFuture(webhookResponse));

        final CompletableFuture<WebhookResponse> webhooks = webhooksClient.registerWebhook(webhookRequest, "ik");

        assertNotNull(webhooks.get());
        assertEquals(webhookResponse, webhooks.get());

    }

    @Test
    void shouldRetrieveWebhook_idempotencyKey() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final WebhookResponse webhookResponse = mock(WebhookResponse.class);

        when(apiClient.getAsync(eq(WEBHOOKS + "/webhook_id"), any(SdkAuthorization.class), eq(WebhookResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(webhookResponse));

        final CompletableFuture<WebhookResponse> webhooks = webhooksClient.retrieveWebhook("webhook_id");

        assertNotNull(webhooks.get());
        assertEquals(webhookResponse, webhooks.get());

    }

    @Test
    void retrieveWebhook_shouldThrowOnInvalidId() {

        try {
            webhooksClient.retrieveWebhook("");
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookId cannot be blank", e.getMessage());
        }

        try {
            webhooksClient.retrieveWebhook(null);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookId cannot be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void shouldUpdateWebhook() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final WebhookRequest webhookRequest = mock(WebhookRequest.class);
        final WebhookResponse webhookResponse = mock(WebhookResponse.class);

        when(apiClient.putAsync(eq(WEBHOOKS + "/webhook_id"), any(SdkAuthorization.class), eq(WebhookResponse.class), eq(webhookRequest)))
                .thenReturn(CompletableFuture.completedFuture(webhookResponse));

        final CompletableFuture<WebhookResponse> webhooks = webhooksClient.updateWebhook("webhook_id", webhookRequest);

        assertNotNull(webhooks.get());
        assertEquals(webhookResponse, webhooks.get());

    }

    @Test
    void updateWebhook_shouldThrowOnNullRequest() {

        try {
            webhooksClient.updateWebhook("id", null);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookRequest cannot be null", e.getMessage());
        }

        try {
            webhooksClient.updateWebhook("", new WebhookRequest());
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookId cannot be blank", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void shouldRemoveWebhook() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        when(apiClient.deleteAsync(eq(WEBHOOKS + "/webhook_id"), any(SdkAuthorization.class)))
                .thenReturn(CompletableFuture.completedFuture(mock(EmptyResponse.class)));

        final CompletableFuture<EmptyResponse> webhooks = webhooksClient.removeWebhook("webhook_id");

        assertNotNull(webhooks.get());

    }

    @Test
    void removeWebhook_shouldThrowOnInvalidId() {

        try {
            webhooksClient.removeWebhook(null);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookId cannot be null", e.getMessage());
        }

        try {
            webhooksClient.removeWebhook("");
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookId cannot be blank", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

}