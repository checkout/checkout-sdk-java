---
id: webhooks
title: Webhooks
---

export const Highlight = ({children, color}) => (
<span
style={{
      color: color,
      padding: '0.2rem',
    }}>
{children}
</span>
);

You can find a list of request body parameters and possible outcomes [here](https://api-reference.checkout.com/#tag/Webhooks).

## Retrieve webhooks

```java
List<WebhookResponse> response = api.webhooksClient().retrieveWebhooks().get();
```

## Register webhook

```java
List<String> eventTypes =
  Arrays.asList("payment_captured", "payment_approved", "payment_declined");

WebhookRequest webhookRequest = WebhookRequest.builder()
  .url("https://example.com/webhook")
  .eventTypes(eventTypes)
  .build();

WebhookResponse webhookResponse =
  api.webhooksClient().registerWebhook(webhookRequest).get();
```

## Retrieve webhook

```java
WebhookResponse webhook =
  api.webhooksClient().retrieveWebhook("wh_tdt72zlbe7cudogxdgit6nwk6i").get();
```

## Update webhook

```java
List<String> eventTypes = Arrays.asList("payment_captured", "payment_approved", "payment_declined");

WebhookRequest webhookRequest = WebhookRequest.builder()
  .url("https://example.com/webhooks/updated")
  .eventTypes(eventTypes)
  .build();

WebhookResponse webhook =
  api.webhooksClient().updateWebhook("wh_ahun3lg7bf4e3lohbhni65335u", webhookRequest).get();
```

## Partially update webhook

```java
WebhookResponse oldWebhook =
  api.webhooksClient().retrieveWebhook("wh_ahun3lg7bf4e3lohbhni65335u").get();
    
WebhookRequest webhookRequest = oldWebhook.toRequest();
webhookRequest.setUrl("https://example.com/webhooks/updated");

WebhookResponse newWebhook =
  api.webhooksClient().updateWebhook(oldWebhook.getId(), webhookRequest).get();
```

## Remove webhook

```java
api.webhooksClient().removeWebhook("wh_ahun3lg7bf4e3lohbhni65335u").get();
```
