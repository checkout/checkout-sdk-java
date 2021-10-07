---
id: oxxo
title: Oxxo
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/oxxo).

## Request a "Redirect" Oxxo payment

```java
OxxoSource source = OxxoSource.builder()
        .country()
        .description()
        .payer(Payer.builder().build())
        .build();

PaymentRequest<OxxoSource> request = PaymentRequest.oxxo(source, Currency.MXN, 1000L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();

String redirectURL = response.getPending().getRedirectLink().getHref()
```

## Succeed a Baloto payment

This functionality only works in sandbox environment.

```java
api.oxxoClient().succeed("payment_id");
```

## Expire a Baloto payment

This functionality only works in sandbox environment.

```java
api.oxxoClient().expire("payment_id");
```
