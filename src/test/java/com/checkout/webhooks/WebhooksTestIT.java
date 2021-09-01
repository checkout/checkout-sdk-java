package com.checkout.webhooks;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebhooksTestIT extends SandboxTestFixture {

    public WebhooksTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    public void can_create_webhook() throws Exception {
        final WebhookRequest webhookRequest = WebhookRequest.builder()
                .url("https://google.com/fail")
                .eventTypes(Collections.singletonList("payment_captured"))
                .build();
        final WebhookResponse webhookResponse = getApi().webhooksClient().registerWebhook(webhookRequest).get();
        assertNotNull(webhookResponse);

        final String id = webhookResponse.getId();
        assertNotNull(id);
    }

    @Test
    public void can_retrieve_webhook() throws Exception {
        final WebhookRequest webhookRequest = WebhookRequest.builder()
                .url("https://google.com/fail")
                .eventTypes(Collections.singletonList("payment_captured"))
                .build();
        final WebhookResponse webhookResponse = getApi().webhooksClient().registerWebhook(webhookRequest).get();
        final String id = webhookResponse.getId();

        WebhookResponse webhook = getApi().webhooksClient().retrieveWebhook(id).get();
        assertEquals(webhookRequest.getUrl(), webhook.getUrl());
        assertEquals(webhookRequest.getEventTypes(), webhook.getEventTypes());
        assertTrue(webhook.isActive());
        assertEquals("json", webhook.getContentType());
        assertEquals(1, webhook.getHeaders().size());
        assertTrue(webhook.getHeaders().containsKey("authorization"));
        assertNotNull(webhook.getHeaders().get("authorization"));

        final List<WebhookResponse> response = getApi().webhooksClient().retrieveWebhooks().get();
        assertNotNull(response);

        webhook = response.stream().filter(it -> id.equals(it.getId())).findFirst().orElse(null);
        assertNotNull(webhook);
        assertEquals(webhookRequest.getUrl(), webhook.getUrl());
        assertEquals(webhookRequest.getEventTypes(), webhook.getEventTypes());
    }

    @Test
    public void can_update_webhook() throws Exception {
        WebhookRequest webhookRequest = WebhookRequest.builder()
                .url("https://google.com/fail")
                .eventTypes(Collections.singletonList("payment_captured"))
                .build();
        final WebhookResponse webhookResponse = getApi().webhooksClient().registerWebhook(webhookRequest).get();
        final String id = webhookResponse.getId();

        webhookRequest = webhookResponse.toRequest();
        webhookRequest.setUrl("https://google.com/fail2");

        final WebhookResponse webhook = getApi().webhooksClient().updateWebhook(id, webhookRequest).get();
        assertEquals(webhookRequest.getUrl(), webhook.getUrl());
        assertEquals(webhookRequest.getEventTypes(), webhook.getEventTypes());
    }

    @Test
    public void can_delete_webhook() throws Exception {
        final WebhookRequest webhookRequest = WebhookRequest.builder()
                .url("https://google.com/fail")
                .eventTypes(Collections.singletonList("payment_captured"))
                .build();
        final WebhookResponse webhookResponse = getApi().webhooksClient().registerWebhook(webhookRequest).get();
        final String id = webhookResponse.getId();

        final List<WebhookResponse> responseBeforeRemoval = getApi().webhooksClient().retrieveWebhooks().get();
        getApi().webhooksClient().removeWebhook(id).get();
        final List<WebhookResponse> responseAfterRemoval = getApi().webhooksClient().retrieveWebhooks().get();
        assertEquals(responseBeforeRemoval.size() - 1, responseAfterRemoval.size());

        responseAfterRemoval.stream()
                .map(WebhookResponse::getId)
                .forEach(it -> {
                    try {
                        getApi().webhooksClient().removeWebhook(it).get();
                    } catch (final Exception e) {
                        throw new IllegalStateException(e);
                    }
                });
        final List<WebhookResponse> emptyResponse = getApi().webhooksClient().retrieveWebhooks().get();
        assertTrue(emptyResponse.isEmpty());
    }
}
