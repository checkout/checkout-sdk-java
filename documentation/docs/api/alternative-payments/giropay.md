---
id: giropay
title: Giropay
---

More information on this topic can be found in the [official docs](https://docs.checkout.com/payments/payment-methods/bank-transfers/giropay).

## Request a Giropay payment

```java
GiropaySource giropaySource = GiropaySource.builder()
        .bic("TESTDETT421")
        .purpose("CKO Giropay test")
        .build();

PaymentRequest<GiropaySource> request = PaymentRequest.giropay(giropaySource, com.checkout.common.beta.Currency.EUR, 1000L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();

redirectURL = response.getPending().getRedirectLink().getHref()
```
## Get Giropay list of supported Banks

```java
BanksResponse banksResponse = api.giropayClient().getBanks().get();
```

## Get Giropay list of supported EPS Banks

```java
BanksResponse banksResponse = api.giropayClient().getEpsBanks().get();
```
