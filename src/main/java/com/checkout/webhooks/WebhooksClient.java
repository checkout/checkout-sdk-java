package com.checkout.webhooks;

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

}
