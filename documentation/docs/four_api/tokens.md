---
id: tokens
title: Tokens
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/#tag/Tokens).

## Request a token

Create a token that represents a card's details that you can later use to request a payment, without you having to process or store any sensitive information.

```java
CardTokenRequest request = CardTokenRequest.builder()
        .number()
        .expiryMonth()
        .expiryYear()
        .build();

CardTokenResponse response = fourApi.tokensClient().requestAsync(request).get();
```
