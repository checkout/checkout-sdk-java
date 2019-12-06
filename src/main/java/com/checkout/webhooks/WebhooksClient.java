package com.checkout.webhooks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface WebhooksClient {
    CompletableFuture<List<WebhookResponse>> retrieveWebhooks();

    CompletableFuture<WebhookResponse> registerWebhook(WebhookRequest webhookRequest);

    CompletableFuture<WebhookResponse> registerWebhook(WebhookRequest webhookRequest, String idempotencyKey);

    CompletableFuture<WebhookResponse> retrieveWebhook(String id);

    CompletableFuture<WebhookResponse> updateWebhook(String id, WebhookRequest webhookRequest);

    CompletableFuture<Void> removeWebhook(String id);
}
