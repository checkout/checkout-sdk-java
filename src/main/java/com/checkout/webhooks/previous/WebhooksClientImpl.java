package com.checkout.webhooks.previous;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorizationType;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

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
        validateParams("webhookRequest", webhookRequest);
        return apiClient.postAsync(WEBHOOKS_PATH, sdkAuthorization(), WebhookResponse.class, webhookRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<WebhookResponse> retrieveWebhook(final String webhookId) {
        validateParams("webhookId", webhookId);
        return apiClient.getAsync(buildPath(WEBHOOKS_PATH, webhookId), sdkAuthorization(), WebhookResponse.class);
    }

    @Override
    public CompletableFuture<WebhookResponse> updateWebhook(final String webhookId, final WebhookRequest webhookRequest) {
        validateParams("webhookId", webhookId, "webhookRequest", webhookRequest);
        return apiClient.putAsync(buildPath(WEBHOOKS_PATH, webhookId), sdkAuthorization(), WebhookResponse.class, webhookRequest);
    }

    @Override
    public CompletableFuture<EmptyResponse> removeWebhook(final String webhookId) {
        validateParams("webhookId", webhookId);
        return apiClient.deleteAsync(buildPath(WEBHOOKS_PATH, webhookId), sdkAuthorization());
    }

}
