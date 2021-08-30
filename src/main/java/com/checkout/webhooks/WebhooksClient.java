package com.checkout.webhooks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface WebhooksClient {

    CompletableFuture<List<WebhookResponse>> retrieveWebhooks();

    CompletableFuture<WebhookResponse> registerWebhook(WebhookRequest webhookRequest);

    CompletableFuture<WebhookResponse> registerWebhook(WebhookRequest webhookRequest, String idempotencyKey);

    CompletableFuture<WebhookResponse> retrieveWebhook(String webhookId);

    CompletableFuture<WebhookResponse> updateWebhook(String webhookId, WebhookRequest webhookRequest);

    CompletableFuture<Void> removeWebhook(String webhookId);

}
