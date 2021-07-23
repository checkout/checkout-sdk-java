package com.checkout.webhooks;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class WebhooksTestIT extends SandboxTestFixture {

    public WebhooksTestIT() {
        super(PlatformType.CLASSIC);
    }

    @Test
    public void can_create_webhook() throws Exception {
        WebhookRequest webhookRequest = WebhookRequest.builder()
                .url("https://google.com/fail")
                .eventTypes(Collections.singletonList("payment_captured"))
                .build();
        WebhookResponse webhookResponse = getApi().webhooksClient().registerWebhook(webhookRequest).get();
        Assert.assertNotNull(webhookResponse);

        String id = webhookResponse.getId();
        Assert.assertNotNull(id);
    }

    @Test
    public void can_retrieve_webhook() throws Exception {
        WebhookRequest webhookRequest = WebhookRequest.builder()
                .url("https://google.com/fail")
                .eventTypes(Collections.singletonList("payment_captured"))
                .build();
        WebhookResponse webhookResponse = getApi().webhooksClient().registerWebhook(webhookRequest).get();
        String id = webhookResponse.getId();

        WebhookResponse webhook = getApi().webhooksClient().retrieveWebhook(id).get();
        Assert.assertEquals(webhookRequest.getUrl(), webhook.getUrl());
        Assert.assertEquals(webhookRequest.getEventTypes(), webhook.getEventTypes());
        Assert.assertTrue(webhook.isActive());
        Assert.assertEquals("json", webhook.getContentType());
        Assert.assertEquals(1, webhook.getHeaders().size());
        Assert.assertTrue(webhook.getHeaders().containsKey("authorization"));
        Assert.assertNotNull(webhook.getHeaders().get("authorization"));

        List<WebhookResponse> response = getApi().webhooksClient().retrieveWebhooks().get();
        Assert.assertNotNull(response);

        webhook = response.stream().filter(it -> id.equals(it.getId())).findFirst().orElse(null);
        Assert.assertNotNull(webhook);
        Assert.assertEquals(webhookRequest.getUrl(), webhook.getUrl());
        Assert.assertEquals(webhookRequest.getEventTypes(), webhook.getEventTypes());
    }

    @Test
    public void can_update_webhook() throws Exception {
        WebhookRequest webhookRequest = WebhookRequest.builder()
                .url("https://google.com/fail")
                .eventTypes(Collections.singletonList("payment_captured"))
                .build();
        WebhookResponse webhookResponse = getApi().webhooksClient().registerWebhook(webhookRequest).get();
        String id = webhookResponse.getId();

        webhookRequest = webhookResponse.toRequest();
        webhookRequest.setUrl("https://google.com/fail2");

        WebhookResponse webhook = getApi().webhooksClient().updateWebhook(id, webhookRequest).get();
        Assert.assertEquals(webhookRequest.getUrl(), webhook.getUrl());
        Assert.assertEquals(webhookRequest.getEventTypes(), webhook.getEventTypes());
    }

    @Test
    public void can_delete_webhook() throws Exception {
        WebhookRequest webhookRequest = WebhookRequest.builder()
                .url("https://google.com/fail")
                .eventTypes(Collections.singletonList("payment_captured"))
                .build();
        WebhookResponse webhookResponse = getApi().webhooksClient().registerWebhook(webhookRequest).get();
        String id = webhookResponse.getId();

        List<WebhookResponse> responseBeforeRemoval = getApi().webhooksClient().retrieveWebhooks().get();
        getApi().webhooksClient().removeWebhook(id).get();
        List<WebhookResponse> responseAfterRemoval = getApi().webhooksClient().retrieveWebhooks().get();
        Assert.assertEquals(responseBeforeRemoval.size() - 1, responseAfterRemoval.size());

        responseAfterRemoval.stream()
                .map(WebhookResponse::getId)
                .forEach(it -> {
                    try {
                        getApi().webhooksClient().removeWebhook(it).get();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                });
        List<WebhookResponse> emptyResponse = getApi().webhooksClient().retrieveWebhooks().get();
        Assert.assertTrue(emptyResponse.isEmpty());
    }
}
