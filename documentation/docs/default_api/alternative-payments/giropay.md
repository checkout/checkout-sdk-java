---
id: giropay
title: Giropay
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/bank-transfers/giropay).

## Request a Giropay payment

```java
GiropaySource giropaySource = GiropaySource.builder()
        .bic()
        .purpose()
        .build();

PaymentRequest<GiropaySource> request = PaymentRequest.giropay(giropaySource, com.checkout.common.Currency.EUR, 1000L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();

String redirectURL = response.getPending().getRedirectLink().getHref()
```
## Get Giropay list of supported Banks

```java
BanksResponse banksResponse = api.giropayClient().getBanks().get();
```

## Get Giropay list of supported EPS Banks

```java
BanksResponse banksResponse = api.giropayClient().getEpsBanks().get();
```
