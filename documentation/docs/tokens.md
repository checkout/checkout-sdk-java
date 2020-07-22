---
id: tokens
title: Tokens
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

You can find a list of request body parameters and possible outcomes [here](https://api-reference.checkout.com/#tag/Tokens).

## Request a token for <Highlight color="#25c2a0">Apple Pay</Highlight>

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

WalletTokenRequest walletTokenRequest =
    new WalletTokenRequest('applepay', tokenData);
try {
    TokenResponse tokenRequest =
        api.tokensClient().requestAsync(walletTokenRequest).get();

    String token  = tokenRequest.getToken();
    return token;
} catch (CheckoutValidationException e) {
    return validationError(e.getError());
} catch (CheckoutApiException e) {
    LOG.severe("Token request failed with status code " + e.getHttpStatusCode());
    throw e;
}
```

## Request a token for <Highlight color="#25c2a0">Google Pay</Highlight>

```java
Map<String, Object> tokenData = new HashMap<String, Object>();
tokenData.put("protocolVersion", "ECv1");
tokenData.put("signature", "XXX");
tokenData.put("signedMessage", "XXX");

WalletTokenRequest walletTokenRequest =
    new WalletTokenRequest('googlepay', tokenData);
try {
    TokenResponse tokenRequest =
        api.tokensClient().requestAsync(walletTokenRequest).get();
    
    String token  = tokenRequest.getToken();
    return token;
} catch (CheckoutValidationException e) {
    return validationError(e.getError());
} catch (CheckoutApiException e) {
    LOG.severe("Token request failed with status code " + e.getHttpStatusCode());
    throw e;
}
```

## Request a token for <Highlight color="#25c2a0">raw card details</Highlight>

:::warning

If you do not have SAQ-D level PCI Compliance, this interaction can only be done in the Sandbox environment. This is in case you want to unit test your code and need a token to make a payment. In the Production environment, you need to use an integrated solution such as **[Frames](https://docs.checkout.com/quickstart/integrate/frames)** to generate the token for you.

:::

```java
CardTokenRequest request = new CardTokenRequest("4242424242424242", 6, 2028);
request.setCvv("100");

CardTokenResponse token = getApi().tokensClient().requestAsync(request).get();
```
