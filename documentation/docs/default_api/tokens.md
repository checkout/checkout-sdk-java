---
id: tokens
title: Tokens
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/#tag/Tokens).

## Request a token (Apple Pay)

```java
Map<String, Object> headerData = new HashMap<String, Object>();
headerData.put("ephemeralPublicKey", "XXX");
headerData.put("publicKeyHash", "XXX");
headerData.put("transactionId", "XXX");
Map<String, Object> tokenData = new HashMap<String, Object>();
tokenData.put("version", "EC_v1");
tokenData.put("data", "XXX");
tokenData.put("signature", "XXX");
tokenData.put("header", headerData);

WalletTokenRequest walletTokenRequest = new WalletTokenRequest('applepay', tokenData);

TokenResponse tokenRequest = api.tokensClient().requestAsync(walletTokenRequest).get();

```

## Request a token (Google Pay)

```java
Map<String, Object> tokenData = new HashMap<String, Object>();
tokenData.put("protocolVersion", "ECv1");
tokenData.put("signature", "XXX");
tokenData.put("signedMessage", "XXX");

WalletTokenRequest walletTokenRequest = new WalletTokenRequest('googlepay', tokenData);

TokenResponse tokenRequest = api.tokensClient().requestAsync(walletTokenRequest).get();
```

## Request a token (card details)

If you do not have SAQ-D level PCI Compliance, this interaction can only be done in the Sandbox environment. This is in case you want to unit test your code and need a token to make a payment. In the Production environment, you need to use an integrated solution such as **[Frames](https://docs.checkout.com/quickstart/integrate/frames)** to generate the token for you.

:::

```java
CardTokenRequest request = new CardTokenRequest("4242424242424242", 6, 2028);
request.setCvv("100");

CardTokenResponse token = api.tokensClient().requestAsync(request).get();
```
