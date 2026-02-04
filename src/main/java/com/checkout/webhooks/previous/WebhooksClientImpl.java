package com.checkout.webhooks.previous;

import static com.checkout.common.CheckoutUtils.validateParams;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorizationType;
import com.google.gson.reflect.TypeToken;

public class WebhooksClientImpl extends AbstractClient implements WebhooksClient {

    private static final String WEBHOOKS_PATH = "webhooks";

    private static final Type WEBHOOKS_TYPE = new TypeToken<ItemsResponse<WebhookResponse>>() {
    }.getType();

    public WebhooksClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<ItemsResponse<WebhookResponse>> retrieveWebhooks() {
        return apiClient.getAsync(WEBHOOKS_PATH, sdkAuthorization(), WEBHOOKS_TYPE);
    }

    @Override
    public CompletableFuture<WebhookResponse> registerWebhook(final WebhookRequest webhookRequest) {
        return registerWebhook(webhookRequest, null);
    }

    @Override
    public CompletableFuture<WebhookResponse> registerWebhook(final WebhookRequest webhookRequest, final String idempotencyKey) {
        validateWebhookRequest(webhookRequest);
        return apiClient.postAsync(WEBHOOKS_PATH, sdkAuthorization(), WebhookResponse.class, webhookRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<WebhookResponse> retrieveWebhook(final String webhookId) {
        validateWebhookId(webhookId);
        return apiClient.getAsync(buildPath(WEBHOOKS_PATH, webhookId), sdkAuthorization(), WebhookResponse.class);
    }

    @Override
    public CompletableFuture<WebhookResponse> updateWebhook(final String webhookId, final WebhookRequest webhookRequest) {
        validateWebhookIdAndRequest(webhookId, webhookRequest);
        return apiClient.putAsync(buildPath(WEBHOOKS_PATH, webhookId), sdkAuthorization(), WebhookResponse.class, webhookRequest);
    }

    @Override
    public CompletableFuture<EmptyResponse> removeWebhook(final String webhookId) {
        validateWebhookId(webhookId);
        return apiClient.deleteAsync(buildPath(WEBHOOKS_PATH, webhookId), sdkAuthorization());
    }

    // Sync methods
    @Override
    public ItemsResponse<WebhookResponse> retrieveWebhooksSync() {
        return apiClient.get(WEBHOOKS_PATH, sdkAuthorization(), WEBHOOKS_TYPE);
    }

    @Override
    public WebhookResponse registerWebhookSync(final WebhookRequest webhookRequest) {
        return registerWebhookSync(webhookRequest, null);
    }

    @Override
    public WebhookResponse registerWebhookSync(final WebhookRequest webhookRequest, final String idempotencyKey) {
        validateWebhookRequest(webhookRequest);
        return apiClient.post(WEBHOOKS_PATH, sdkAuthorization(), WebhookResponse.class, webhookRequest, idempotencyKey);
    }

    @Override
    public WebhookResponse retrieveWebhookSync(final String webhookId) {
        validateWebhookId(webhookId);
        return apiClient.get(buildPath(WEBHOOKS_PATH, webhookId), sdkAuthorization(), WebhookResponse.class);
    }

    @Override
    public WebhookResponse updateWebhookSync(final String webhookId, final WebhookRequest webhookRequest) {
        validateWebhookIdAndRequest(webhookId, webhookRequest);
        return apiClient.put(buildPath(WEBHOOKS_PATH, webhookId), sdkAuthorization(), WebhookResponse.class, webhookRequest);
    }

    @Override
    public EmptyResponse removeWebhookSync(final String webhookId) {
        validateWebhookId(webhookId);
        return apiClient.delete(buildPath(WEBHOOKS_PATH, webhookId), sdkAuthorization());
    }

    // Common methods
    private void validateWebhookRequest(final WebhookRequest webhookRequest) {
        validateParams("webhookRequest", webhookRequest);
    }

    private void validateWebhookId(final String webhookId) {
        validateParams("webhookId", webhookId);
    }

    private void validateWebhookIdAndRequest(final String webhookId, final WebhookRequest webhookRequest) {
        validateParams("webhookId", webhookId, "webhookRequest", webhookRequest);
    }

}
