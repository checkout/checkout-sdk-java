---
id: events
title: Events
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

You can find a list of request body parameters and possible outcomes [here](https://api-reference.checkout.com/#tag/Events).

## Retrieve event types

```java
/* 
   Specify API version:
    "1.0" => Legacy API
    "2.0" => Unified Payments API
    null  => all versions
*/
List<EventTypesResponse> allEventTypesResponses =
  api.eventsClient().retrieveAllEventTypes(null).get();
```

## Retrieve events

```java
Instant from =
  Instant.now().minus(90, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);
Instant to =
  Instant.now().minus(7, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);
int limit = 5;
int skip = 2;
String paymentId = null;

EventsPageResponse eventsPageResponse =
  getApi().eventsClient().retrieveEvents(from, to, limit, skip, paymentId).get();
```

## Retrieve event

```java
EventResponse event =
  api.eventsClient().retrieveEvent("evt_c2qelfixai2u3es3ksovngkx3e").get();
```

## Retrieve event notification

```java
EventNotificationResponse notification =
  api.eventsClient().retrieveEventNotification("evt_c2qelfixai2u3es3ksovngkx3e", "ntf_wqjkqpgjy33uxoywcej4fnw6qm").get();
```

## Retry webhook

```java
api.eventsClient().retryWebhook("evt_c2qelfixai2u3es3ksovngkx3e", "wh_mpkyioafmajulnhjvwmrklenb4").get();
```

## Retry all webhooks

```java
api.eventsClient().retryAllWebhooks("evt_c2qelfixai2u3es3ksovngkx3e").get();
```
