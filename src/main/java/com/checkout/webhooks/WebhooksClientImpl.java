package com.checkout.webhooks;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
import com.checkout.AbstractClient;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WebhooksClientImpl extends AbstractClient implements WebhooksClient {

    private static final String WEBHOOKS = "webhooks";

    public WebhooksClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, new SecretKeyCredentials(configuration));
    }

    @Override
    public CompletableFuture<List<WebhookResponse>> retrieveWebhooks() {
        return apiClient.getAsync(WEBHOOKS, apiCredentials, WebhookResponse[].class)
                .thenApply(it -> it == null ? new WebhookResponse[0] : it)
                .thenApply(Arrays::asList);
    }

    @Override
    public CompletableFuture<WebhookResponse> registerWebhook(final WebhookRequest webhookRequest) {
        return registerWebhook(webhookRequest, null);
    }

    @Override
    public CompletableFuture<WebhookResponse> registerWebhook(final WebhookRequest webhookRequest, final String idempotencyKey) {
        return apiClient.postAsync(WEBHOOKS, apiCredentials, WebhookResponse.class, webhookRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<WebhookResponse> retrieveWebhook(final String id) {
        return apiClient.getAsync(WEBHOOKS + "/" + id, apiCredentials, WebhookResponse.class);
    }

    @Override
    public CompletableFuture<WebhookResponse> updateWebhook(final String id, final WebhookRequest webhookRequest) {
        return apiClient.putAsync(WEBHOOKS + "/" + id, apiCredentials, WebhookResponse.class, webhookRequest);
    }

    @Override
    public CompletableFuture<Void> removeWebhook(final String id) {
        return apiClient.deleteAsync(WEBHOOKS + "/" + id, apiCredentials);
    }
}
