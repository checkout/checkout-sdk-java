package com.checkout.webhooks.previous;

import com.checkout.EmptyResponse;
import com.checkout.ItemsResponse;

import java.util.concurrent.CompletableFuture;

public interface WebhooksClient {

    CompletableFuture<ItemsResponse<WebhookResponse>> retrieveWebhooks();

    CompletableFuture<WebhookResponse> registerWebhook(WebhookRequest webhookRequest);

    CompletableFuture<WebhookResponse> registerWebhook(WebhookRequest webhookRequest, String idempotencyKey);

    CompletableFuture<WebhookResponse> retrieveWebhook(String webhookId);

    CompletableFuture<WebhookResponse> updateWebhook(String webhookId, WebhookRequest webhookRequest);

    CompletableFuture<EmptyResponse> removeWebhook(String webhookId);

    // Sync methods

    ItemsResponse<WebhookResponse> retrieveWebhooksSync();

    WebhookResponse registerWebhookSync(WebhookRequest webhookRequest);

    WebhookResponse registerWebhookSync(WebhookRequest webhookRequest, String idempotencyKey);

    WebhookResponse retrieveWebhookSync(String webhookId);

    WebhookResponse updateWebhookSync(String webhookId, WebhookRequest webhookRequest);

    EmptyResponse removeWebhookSync(String webhookId);

}
