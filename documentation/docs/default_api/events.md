---
id: events
title: Events
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/#tag/Events).

## Retrieve event types

Retrieve a list of event types grouped by their respective version that you can configure on your webhooks.

```java
/* 
   Specify API version:
    "1.0" => Legacy API
    "2.0" => Unified Payments API
    null  => all versions
*/
List<EventTypesResponse> response = api.eventsClient().retrieveAllEventTypes(null).get();
```

## Retrieve events

Retrieves events based on your query parameters.

```java
Instant from = Instant.now().minus(90, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);
Instant to = Instant.now().minus(7, ChronoUnit.DAYS).truncatedTo(ChronoUnit.SECONDS);
int limit = 5;
int skip = 2;
String paymentId = null;

EventsPageResponse eventsPageResponse = api.eventsClient().retrieveEvents(from, to, limit, skip, paymentId).get();
```

## Retrieve event

Retrieves the event with the specified identifier string. The event data includes the full event details, the schema of which will vary based on the type.

```java
EventResponse event = api.eventsClient().retrieveEvent(eventId).get();
```

## Retrieve event notification

Retrieves the attempts for a specific event notification.

```java
EventNotificationResponse notification = api.eventsClient().retrieveEventNotification(eventId, notificationId).get();
```

## Retry webhook

Retries a specific webhook notification for the given event.

```java
api.eventsClient().retryWebhook(eventId, webhookId).get();
```

## Retry all webhooks

Retries all webhook notifications configured for the specified event.

```java
api.eventsClient().retryAllWebhooks(eventId).get();
```

