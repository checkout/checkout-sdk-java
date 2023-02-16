package com.checkout.webhooks.previous;

import com.checkout.ItemsResponse;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

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
        final ItemsResponse<WebhookResponse> webhookResponses = blocking(() -> previousApi.webhooksClient().retrieveWebhooks());
        webhookResponses.getItems().forEach(webhookResponse -> blocking(() -> previousApi.webhooksClient().removeWebhook(webhookResponse.getId())));
    }

    @Test
    void shouldRegisterWebhook() {

        final WebhookResponse webhookResponse = registerWebhook();
        assertNotNull(webhookResponse);
        assertNotNull(webhookResponse.getId());
        assertEquals("https://google.com/fail", webhookResponse.getUrl());
        assertTrue(webhookResponse.isActive());
        assertFalse(webhookResponse.getHeaders().isEmpty());
        assertEquals(GATEWAY_EVENT_TYPES, webhookResponse.getEventTypes());
        assertNotNull(webhookResponse.getLink("self"));

    }

    @Test
    void shouldRetrieveWebhook() {

        final WebhookResponse webhookResponse = registerWebhook();

        final WebhookResponse webhook = blocking(() -> previousApi.webhooksClient().retrieveWebhook(webhookResponse.getId()));
        assertEquals("https://google.com/fail", webhook.getUrl());
        assertEquals(GATEWAY_EVENT_TYPES, webhook.getEventTypes());
        assertTrue(webhook.isActive());
        assertEquals("json", webhook.getContentType());
        assertEquals(1, webhook.getHeaders().size());
        assertTrue(webhook.getHeaders().containsKey("authorization"));
        assertNotNull(webhook.getHeaders().get("authorization"));

        final ItemsResponse<WebhookResponse> response = blocking(() -> previousApi.webhooksClient().retrieveWebhooks());
        assertNotNull(response);
        assertTrue(response.getItems().size() >= 1);

        final WebhookResponse webhook2 = response.getItems().stream().filter(it -> webhook.getId().equals(it.getId())).findFirst().orElse(null);
        assertNotNull(webhook2);
        assertEquals("https://google.com/fail", webhook2.getUrl());
        assertEquals(GATEWAY_EVENT_TYPES, webhook2.getEventTypes());

    }

    @Test
    void shouldUpdateWebhook() {

        final WebhookResponse webhookResponse = registerWebhook();

        final WebhookRequest webhookRequest = WebhookRequest.builder()
                .headers(webhookResponse.getHeaders())
                .active(webhookResponse.isActive())
                .contentType(webhookResponse.getContentType())
                .url("https://google.com/fail2")
                .build();

        webhookRequest.getEventTypes().add("payment_approved");
        webhookRequest.getEventTypes().add("payment_captured");

        final WebhookResponse webhook = blocking(() -> previousApi.webhooksClient().updateWebhook(webhookResponse.getId(), webhookRequest));
        assertEquals(webhookRequest.getUrl(), webhook.getUrl());
        assertEquals(webhookRequest.getEventTypes(), webhook.getEventTypes());

    }

    @Test
    void canDeleteWebhook() {

        final WebhookResponse webhookResponse = registerWebhook();

        final ItemsResponse<WebhookResponse> responseBeforeRemoval = blocking(() -> previousApi.webhooksClient().retrieveWebhooks());
        blocking(() -> previousApi.webhooksClient().removeWebhook(webhookResponse.getId()));
        final ItemsResponse<WebhookResponse> responseAfterRemoval = blocking(() -> previousApi.webhooksClient().retrieveWebhooks());
        assertEquals(responseBeforeRemoval.getItems().size() - 1, responseAfterRemoval.getItems().size());

        responseAfterRemoval.getItems().stream()
                .map(WebhookResponse::getId)
                .forEach(it -> {
                    try {
                        previousApi.webhooksClient().removeWebhook(it).get();
                    } catch (final Exception e) {
                        fail(e.getCause());
                    }
                });
        final ItemsResponse<WebhookResponse> emptyResponse = blocking(() -> previousApi.webhooksClient().retrieveWebhooks());
        assertTrue(emptyResponse.getItems().isEmpty());

    }

    protected WebhookResponse registerWebhook() {

        final WebhookRequest webhookRequest = WebhookRequest.builder()
                .url("https://google.com/fail")
                .contentType("json")
                .headers(Collections.singletonMap("authorization", "Something"))
                .build();

        webhookRequest.setEventTypes(GATEWAY_EVENT_TYPES);

        return blocking(() -> previousApi.webhooksClient().registerWebhook(webhookRequest));

    }

}
