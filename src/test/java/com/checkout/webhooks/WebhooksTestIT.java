package com.checkout.webhooks;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class WebhooksTestIT extends SandboxTestFixture {

    protected WebhooksTestIT() {
        super(PlatformType.DEFAULT);
    }

    @BeforeEach
    protected void cleanup() {
        final List<WebhookResponse> webhookResponses = blocking(getApi().webhooksClient().retrieveWebhooks());
        webhookResponses.forEach(
                webhookResponse -> blocking(getApi().webhooksClient().removeWebhook(webhookResponse.getId())));
    }

    @Test
    void shouldRegisterWebhook() {

        final WebhookResponse webhookResponse = registerWebhook();
        assertNotNull(webhookResponse);
        assertNotNull(webhookResponse.getId());
        assertEquals("https://google.com/fail", webhookResponse.getUrl());
        assertTrue(webhookResponse.isActive());
        assertFalse(webhookResponse.getHeaders().isEmpty());
        assertEquals(Stream.of(EventType.values()).map(EventType::getCode).collect(Collectors.toList()), webhookResponse.getEventTypes());
        assertTrue(webhookResponse.hasLink("self"));

    }

    @Test
    void shouldRetrieveWebhook() {

        final WebhookResponse webhookResponse = registerWebhook();

        final WebhookResponse webhook = blocking(getApi().webhooksClient().retrieveWebhook(webhookResponse.getId()));
        assertEquals("https://google.com/fail", webhook.getUrl());
        assertEquals(Stream.of(EventType.values()).map(EventType::getCode).collect(Collectors.toList()), webhook.getEventTypes());
        assertTrue(webhook.isActive());
        assertEquals("json", webhook.getContentType());
        assertEquals(1, webhook.getHeaders().size());
        assertTrue(webhook.getHeaders().containsKey("authorization"));
        assertNotNull(webhook.getHeaders().get("authorization"));

        final List<WebhookResponse> response = blocking(getApi().webhooksClient().retrieveWebhooks());
        assertNotNull(response);
        assertTrue(response.size() >= 1);

        final WebhookResponse webhook2 = response.stream().filter(it -> webhook.getId().equals(it.getId())).findFirst().orElse(null);
        assertNotNull(webhook2);
        assertEquals("https://google.com/fail", webhook2.getUrl());
        assertEquals(Stream.of(EventType.values()).map(EventType::getCode).collect(Collectors.toList()), webhook2.getEventTypes());

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

        webhookRequest.addEventTypes(EventType.PAYMENT_APPROVED, EventType.PAYMENT_CAPTURED);

        final WebhookResponse webhook = blocking(getApi().webhooksClient().updateWebhook(webhookResponse.getId(), webhookRequest));
        assertEquals(webhookRequest.getUrl(), webhook.getUrl());
        assertEquals(webhookRequest.getEventTypes(), webhook.getEventTypes());

    }

    @Test
    void canDeleteWebhook() {

        final WebhookResponse webhookResponse = registerWebhook();

        final List<WebhookResponse> responseBeforeRemoval = blocking(getApi().webhooksClient().retrieveWebhooks());
        blocking(getApi().webhooksClient().removeWebhook(webhookResponse.getId()));
        final List<WebhookResponse> responseAfterRemoval = blocking(getApi().webhooksClient().retrieveWebhooks());
        assertEquals(responseBeforeRemoval.size() - 1, responseAfterRemoval.size());

        responseAfterRemoval.stream()
                .map(WebhookResponse::getId)
                .forEach(it -> {
                    try {
                        getApi().webhooksClient().removeWebhook(it).get();
                    } catch (final Exception e) {
                        fail(e.getCause());
                    }
                });
        final List<WebhookResponse> emptyResponse = blocking(getApi().webhooksClient().retrieveWebhooks());
        assertTrue(emptyResponse.isEmpty());

    }

    protected WebhookResponse registerWebhook() {

        final WebhookRequest webhookRequest = WebhookRequest.builder()
                .url("https://google.com/fail")
                .build();

        webhookRequest.addEventTypes(EventType.values());

        return blocking(getApi().webhooksClient().registerWebhook(webhookRequest));

    }

}
