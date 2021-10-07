---
id: webhooks
title: Webhooks
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/#tag/Webhooks).

## Retrieve webhooks

Retrieves the webhooks configured for the channel identified by your API key.

```java
List<WebhookResponse> response = api.webhooksClient().retrieveWebhooks().get();
```

## Register webhook

Register a new webhook endpoint that Checkout.com will post all or selected events to.

```java
List<String> eventTypes = Arrays.asList("payment_captured", "payment_approved", "payment_declined");

WebhookRequest webhookRequest = WebhookRequest.builder()
  .url("https://example.com/webhook")
  .eventTypes(eventTypes)
  .build();

WebhookResponse response = api.webhooksClient().registerWebhook(webhookRequest).get();
```

## Retrieve webhook

Retrieves the webhook with the specified identifier string.

```java
WebhookResponse webhook = api.webhooksClient().retrieveWebhook(webhookId).get();
```

## Update webhook

Updates an existing webhook.

```java
List<String> eventTypes = Arrays.asList("payment_captured", "payment_approved", "payment_declined");

WebhookRequest webhookRequest = WebhookRequest.builder()
  .url("https://example.com/webhooks/updated")
  .eventTypes(eventTypes)
  .build();

WebhookResponse webhook = api.webhooksClient().updateWebhook(webhookId, webhookRequest).get();
```

## Partially update webhook

Updates all or some of the registered webhook details.

```java
WebhookRequest webhookRequest = oldWebhook.toRequest();
webhookRequest.setUrl("https://example.com/webhooks/updated");

WebhookResponse newWebhook = api.webhooksClient().updateWebhook(oldWebhook.getId(), webhookRequest).get();
```

## Remove webhook

Removes an existing webhook.

```java
api.webhooksClient().removeWebhook(webhookId).get();
```
