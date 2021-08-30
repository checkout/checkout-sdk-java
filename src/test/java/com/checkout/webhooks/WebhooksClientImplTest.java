package com.checkout.webhooks;

import com.checkout.ApiClient;
import com.checkout.ApiCredentials;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.checkout.TestHelper.mockDefaultConfiguration;
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
public class WebhooksClientImplTest {

    private static final String WEBHOOKS = "webhooks";

    @Mock
    private ApiClient apiClient;

    private WebhooksClient webhooksClient;

    @BeforeEach
    public void setup() {
        final CheckoutConfiguration checkoutConfiguration = mockDefaultConfiguration();
        this.webhooksClient = new WebhooksClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    public void shouldRetrieveWebhooks() throws ExecutionException, InterruptedException {

        final WebhookResponse[] webhookResponses = new WebhookResponse[2];
        webhookResponses[0] = mock(WebhookResponse.class);
        webhookResponses[1] = mock(WebhookResponse.class);

        when(apiClient.getAsync(eq(WEBHOOKS), any(ApiCredentials.class), eq(WebhookResponse[].class)))
                .thenReturn(CompletableFuture.completedFuture(webhookResponses));

        final CompletableFuture<List<WebhookResponse>> webhooks = webhooksClient.retrieveWebhooks();

        assertNotNull(webhooks.get());
        assertTrue(webhooks.get().contains(webhookResponses[0]));
        assertTrue(webhooks.get().contains(webhookResponses[1]));

    }

    @Test
    public void shouldRetrieveWebhooks_nullResponse() throws ExecutionException, InterruptedException {

        when(apiClient.getAsync(eq(WEBHOOKS), any(ApiCredentials.class), eq(WebhookResponse[].class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        final CompletableFuture<List<WebhookResponse>> webhooks = webhooksClient.retrieveWebhooks();

        assertNotNull(webhooks.get());
        assertEquals(new ArrayList<>(), webhooks.get());

    }

    @Test
    public void shouldRegisterWebhook() throws ExecutionException, InterruptedException {

        final WebhookRequest webhookRequest = mock(WebhookRequest.class);
        final WebhookResponse webhookResponse = mock(WebhookResponse.class);

        when(apiClient.postAsync(eq(WEBHOOKS), any(ApiCredentials.class), eq(WebhookResponse.class), eq(webhookRequest), isNull()))
                .thenReturn(CompletableFuture.completedFuture(webhookResponse));

        final CompletableFuture<WebhookResponse> webhooks = webhooksClient.registerWebhook(webhookRequest);

        assertNotNull(webhooks.get());
        assertEquals(webhookResponse, webhooks.get());

    }

    @Test
    public void registerWebhook_shouldThrowOnNullRequest() {

        try {
            webhooksClient.registerWebhook(null);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookRequest must be not be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    public void shouldRegisterWebhook_idempotencyKey() throws ExecutionException, InterruptedException {

        final WebhookRequest webhookRequest = mock(WebhookRequest.class);
        final WebhookResponse webhookResponse = mock(WebhookResponse.class);

        when(apiClient.postAsync(eq(WEBHOOKS), any(ApiCredentials.class), eq(WebhookResponse.class), eq(webhookRequest), eq("ik")))
                .thenReturn(CompletableFuture.completedFuture(webhookResponse));

        final CompletableFuture<WebhookResponse> webhooks = webhooksClient.registerWebhook(webhookRequest, "ik");

        assertNotNull(webhooks.get());
        assertEquals(webhookResponse, webhooks.get());

    }

    @Test
    public void shouldRetrieveWebhook_idempotencyKey() throws ExecutionException, InterruptedException {

        final WebhookResponse webhookResponse = mock(WebhookResponse.class);

        when(apiClient.getAsync(eq(WEBHOOKS + "/webhook_id"), any(ApiCredentials.class), eq(WebhookResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(webhookResponse));

        final CompletableFuture<WebhookResponse> webhooks = webhooksClient.retrieveWebhook("webhook_id");

        assertNotNull(webhooks.get());
        assertEquals(webhookResponse, webhooks.get());

    }

    @Test
    public void retrieveWebhook_shouldThrowOnInvalidId() {

        try {
            webhooksClient.retrieveWebhook("");
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookId must be not be blank", e.getMessage());
        }

        try {
            webhooksClient.retrieveWebhook(null);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookId must be not be blank", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    public void shouldUpdateWebhook() throws ExecutionException, InterruptedException {

        final WebhookRequest webhookRequest = mock(WebhookRequest.class);
        final WebhookResponse webhookResponse = mock(WebhookResponse.class);

        when(apiClient.putAsync(eq(WEBHOOKS + "/webhook_id"), any(ApiCredentials.class), eq(WebhookResponse.class), eq(webhookRequest)))
                .thenReturn(CompletableFuture.completedFuture(webhookResponse));

        final CompletableFuture<WebhookResponse> webhooks = webhooksClient.updateWebhook("webhook_id", webhookRequest);

        assertNotNull(webhooks.get());
        assertEquals(webhookResponse, webhooks.get());

    }

    @Test
    public void updateWebhook_shouldThrowOnNullRequest() {

        try {
            webhooksClient.updateWebhook("id", null);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookRequest must be not be null", e.getMessage());
        }

        try {
            webhooksClient.updateWebhook("", new WebhookRequest());
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookId must be not be blank", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void shouldRemoveWebhook() throws ExecutionException, InterruptedException {

        when(apiClient.deleteAsync(eq(WEBHOOKS + "/webhook_id"), any(ApiCredentials.class)))
                .thenReturn(CompletableFuture.completedFuture(mock(Void.class)));

        final CompletableFuture<Void> webhooks = webhooksClient.removeWebhook("webhook_id");

        assertNotNull(webhooks.get());

    }

    @Test
    public void removeWebhook_shouldThrowOnInvalidId() {

        try {
            webhooksClient.removeWebhook(null);
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookId must be not be blank", e.getMessage());
        }

        try {
            webhooksClient.removeWebhook("");
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("webhookId must be not be blank", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

}