package com.checkout.webhooks;

import com.checkout.ApiClient;
import com.checkout.ApiCredentials;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WebhooksClientImpl implements WebhooksClient {

    private final ApiClient apiClient;
    private final ApiCredentials credentials;

    public WebhooksClientImpl(ApiClient apiClient, CheckoutConfiguration configuration) {
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
    public CompletableFuture<List<WebhookResponse>> retrieveWebhooks() {
        return apiClient.getAsync("webhooks", credentials, WebhookResponse[].class)
                .thenApply(it -> it == null ? new WebhookResponse[0] : it)
                .thenApply(Arrays::asList);
    }

    @Override
    public CompletableFuture<WebhookResponse> registerWebhook(WebhookRequest webhookRequest) {
        return registerWebhook(webhookRequest, null);
    }

    @Override
    public CompletableFuture<WebhookResponse> registerWebhook(WebhookRequest webhookRequest, String idempotencyKey) {
        return apiClient.postAsync("webhooks", credentials, WebhookResponse.class, webhookRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<WebhookResponse> retrieveWebhook(String id) {
        return apiClient.getAsync("webhooks/" + id, credentials, WebhookResponse.class);
    }

    @Override
    public CompletableFuture<WebhookResponse> updateWebhook(String id, WebhookRequest webhookRequest) {
        return apiClient.putAsync("webhooks/" + id, credentials, WebhookResponse.class, webhookRequest);
    }

    @Override
    public CompletableFuture<Void> removeWebhook(String id) {
        return apiClient.deleteAsync("webhooks/" + id, credentials);
    }
}
