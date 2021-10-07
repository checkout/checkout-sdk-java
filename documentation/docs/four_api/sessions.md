---
id: sessions
title: Sessions
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/preview/crusoe/#tag/Sessions).

## Request a session

Create a payment session to authenticate a cardholder before requesting a payment. Payment sessions can be linked to one or more payments (in the case of recurring and other merchant-initiated payments).

```java
ChannelData channelData = BrowserSession.builder() // other channel data types available
      .acceptHeader("Accept:  *.*, q=0.1")
      .build();

SessionRequest sessionRequest = SessionRequest.builder()
    .source(SessionCardSource.builder().build()) // more sources available
    .amount()
    .currency()
    .processingChannelId()
    .marketplace(MarketplaceData.builder().subEntityId().build())
    .authenticationType()
    .authenticationCategory()
    .challengeIndicator()
    .billingDescriptor(SessionsBillingDescriptor.builder().build())
    .reference()
    .transactionType()
    .shippingAddress(SessionAddress.builderSessionAddress().build())
    .completion(NonHostedCompletionInfo.builder().build())
    .channelData(channelData)
    .build();

SessionResponse response = fourApi.sessionsClient().requestSession(sessionRequest).get();
```

## Get session details

Returns the details of the session with the specified identifier string.

Note: a similar operation can be used with session secret authentication.

```java
GetSessionResponse response = fourApi.sessionsClient().getSessionDetails(sessionId).get();
```

## Update a session

Update a session by providing information about the environment.

Note: a similar operation can be used with session secret authentication.

```java
ChannelData channelData = BrowserSession.builder() // other channel data types available
      .acceptHeader()
      .build();

GetSessionResponse response = fourApi.sessionsClient().updateSession(sessionId, channelData).get();
```

## Complete a session

Completes a session by posting the following request to the callback URL (only relevant for non hosted sessions).

Note: a similar operation can be used with session secret authentication.

```java
fourApi.sessionsClient().completeSession(sessionId).get()
```
