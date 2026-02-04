package com.checkout.webhooks.previous;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.checkout.ItemsResponse;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;

@Disabled("unavailable")
class WebhooksTestIT extends SandboxTestFixture {

    protected WebhooksTestIT() {
        super(PlatformType.PREVIOUS);
    }

    private static final List<String> GATEWAY_EVENT_TYPES = Arrays.asList("payment_approved", "payment_pending",
            "payment_declined", "payment_expired", "payment_canceled", "payment_voided", "payment_void_declined",
            "payment_captured", "payment_capture_declined", "payment_capture_pending", "payment_refunded",
            "payment_refund_declined", "payment_refund_pending");

    @BeforeEach
    protected void cleanup() {
        final ItemsResponse<WebhookResponse> webhookResponses = previousApi.webhooksClient().retrieveWebhooksSync();
        webhookResponses.getItems().forEach(webhookResponse -> previousApi.webhooksClient().removeWebhookSync(webhookResponse.getId()));
    }

    @Test
    void shouldRegisterWebhook() {
        final WebhookRequest webhookRequest = createWebhookRequest();
        final WebhookResponse webhookResponse = blocking(() -> previousApi.webhooksClient().registerWebhook(webhookRequest));
        
        validateWebhookResponse(webhookResponse);
    }

    @Test
    void shouldRetrieveWebhook() {
        final WebhookRequest webhookRequest = createWebhookRequest();

        final WebhookResponse webhookResponse = blocking(() -> previousApi.webhooksClient().registerWebhook(webhookRequest));
        final WebhookResponse webhook = blocking(() -> previousApi.webhooksClient().retrieveWebhook(webhookResponse.getId()));
        final ItemsResponse<WebhookResponse> response = blocking(() -> previousApi.webhooksClient().retrieveWebhooks());
        
        validateRetrievedWebhook(webhook, response);
    }

    @Test
    void shouldUpdateWebhook() {
        final WebhookRequest webhookRequest = createWebhookRequest();
        final WebhookResponse webhookResponse = blocking(() -> previousApi.webhooksClient().registerWebhook(webhookRequest));
        
        final WebhookRequest updateRequest = createUpdateWebhookRequest(webhookResponse);
        final WebhookResponse webhook = blocking(() -> previousApi.webhooksClient().updateWebhook(webhookResponse.getId(), updateRequest));
        
        validateUpdatedWebhook(webhook, updateRequest);
    }

    @Test
    void canDeleteWebhook() {
        final WebhookRequest webhookRequest = createWebhookRequest();
        final WebhookResponse webhookResponse = blocking(() -> previousApi.webhooksClient().registerWebhook(webhookRequest));

        final ItemsResponse<WebhookResponse> responseBeforeRemoval = blocking(() -> previousApi.webhooksClient().retrieveWebhooks());
        blocking(() -> previousApi.webhooksClient().removeWebhook(webhookResponse.getId()));
        final ItemsResponse<WebhookResponse> responseAfterRemoval = blocking(() -> previousApi.webhooksClient().retrieveWebhooks());
        
        assertEquals(responseBeforeRemoval.getItems().size() - 1, responseAfterRemoval.getItems().size());
        cleanupRemainingWebhooks(responseAfterRemoval);
        
        final ItemsResponse<WebhookResponse> emptyResponse = blocking(() -> previousApi.webhooksClient().retrieveWebhooks());
        assertTrue(emptyResponse.getItems().isEmpty());
    }

    // Sync methods
    @Test
    void shouldRegisterWebhookSync() {
        final WebhookRequest webhookRequest = createWebhookRequest();
        final WebhookResponse webhookResponse = previousApi.webhooksClient().registerWebhookSync(webhookRequest);
        
        validateWebhookResponse(webhookResponse);
    }

    @Test
    void shouldRetrieveWebhookSync() {
        final WebhookRequest webhookRequest = createWebhookRequest();

        final WebhookResponse webhookResponse = previousApi.webhooksClient().registerWebhookSync(webhookRequest);
        final WebhookResponse webhook = previousApi.webhooksClient().retrieveWebhookSync(webhookResponse.getId());
        final ItemsResponse<WebhookResponse> response = previousApi.webhooksClient().retrieveWebhooksSync();
        
        validateRetrievedWebhook(webhook, response);
    }

    @Test
    void shouldUpdateWebhookSync() {
        final WebhookRequest webhookRequest = createWebhookRequest();
        final WebhookResponse webhookResponse = previousApi.webhooksClient().registerWebhookSync(webhookRequest);
        
        final WebhookRequest updateRequest = createUpdateWebhookRequest(webhookResponse);
        final WebhookResponse webhook = previousApi.webhooksClient().updateWebhookSync(webhookResponse.getId(), updateRequest);
        
        validateUpdatedWebhook(webhook, updateRequest);
    }

    @Test
    void canDeleteWebhookSync() {
        final WebhookRequest webhookRequest = createWebhookRequest();
        final WebhookResponse webhookResponse = previousApi.webhooksClient().registerWebhookSync(webhookRequest);

        final ItemsResponse<WebhookResponse> responseBeforeRemoval = previousApi.webhooksClient().retrieveWebhooksSync();
        previousApi.webhooksClient().removeWebhookSync(webhookResponse.getId());
        final ItemsResponse<WebhookResponse> responseAfterRemoval = previousApi.webhooksClient().retrieveWebhooksSync();
        
        assertEquals(responseBeforeRemoval.getItems().size() - 1, responseAfterRemoval.getItems().size());
        cleanupRemainingWebhooksSync(responseAfterRemoval);
        
        final ItemsResponse<WebhookResponse> emptyResponse = previousApi.webhooksClient().retrieveWebhooksSync();
        assertTrue(emptyResponse.getItems().isEmpty());
    }

    // Common methods
    private WebhookRequest createWebhookRequest() {
        final WebhookRequest webhookRequest = WebhookRequest.builder()
                .url("https://google.com/fail")
                .contentType("json")
                .headers(Collections.singletonMap("authorization", "Something"))
                .build();

        webhookRequest.setEventTypes(GATEWAY_EVENT_TYPES);
        return webhookRequest;
    }

    private WebhookRequest createUpdateWebhookRequest(final WebhookResponse webhookResponse) {
        final WebhookRequest webhookRequest = WebhookRequest.builder()
                .headers(webhookResponse.getHeaders())
                .active(webhookResponse.isActive())
                .contentType(webhookResponse.getContentType())
                .url("https://google.com/fail2")
                .build();

        webhookRequest.getEventTypes().add("payment_approved");
        webhookRequest.getEventTypes().add("payment_captured");
        return webhookRequest;
    }

    private void validateWebhookResponse(final WebhookResponse webhookResponse) {
        assertNotNull(webhookResponse);
        assertNotNull(webhookResponse.getId());
        assertEquals("https://google.com/fail", webhookResponse.getUrl());
        assertTrue(webhookResponse.isActive());
        assertFalse(webhookResponse.getHeaders().isEmpty());
        assertEquals(GATEWAY_EVENT_TYPES, webhookResponse.getEventTypes());
        assertNotNull(webhookResponse.getLink("self"));
    }

    private void validateRetrievedWebhook(final WebhookResponse webhook, final ItemsResponse<WebhookResponse> response) {
        assertEquals("https://google.com/fail", webhook.getUrl());
        assertEquals(GATEWAY_EVENT_TYPES, webhook.getEventTypes());
        assertTrue(webhook.isActive());
        assertEquals("json", webhook.getContentType());
        assertEquals(1, webhook.getHeaders().size());
        assertTrue(webhook.getHeaders().containsKey("authorization"));
        assertNotNull(webhook.getHeaders().get("authorization"));

        assertNotNull(response);
        assertTrue(!response.getItems().isEmpty());

        final WebhookResponse webhook2 = response.getItems().stream()
                .filter(it -> webhook.getId().equals(it.getId()))
                .findFirst().orElse(null);
        assertNotNull(webhook2);
        assertEquals("https://google.com/fail", webhook2.getUrl());
        assertEquals(GATEWAY_EVENT_TYPES, webhook2.getEventTypes());
    }

    private void validateUpdatedWebhook(final WebhookResponse webhook, final WebhookRequest updateRequest) {
        assertEquals(updateRequest.getUrl(), webhook.getUrl());
        assertEquals(updateRequest.getEventTypes(), webhook.getEventTypes());
    }

    private void cleanupRemainingWebhooks(final ItemsResponse<WebhookResponse> response) {
        response.getItems().stream()
                .map(WebhookResponse::getId)
                .forEach(it -> {
                    try {
                        previousApi.webhooksClient().removeWebhook(it).get();
                    } catch (final Exception e) {
                        fail(e.getCause());
                    }
                });
    }

    private void cleanupRemainingWebhooksSync(final ItemsResponse<WebhookResponse> response) {
        response.getItems().forEach(webhookResponse -> 
                previousApi.webhooksClient().removeWebhookSync(webhookResponse.getId()));
    }

}
