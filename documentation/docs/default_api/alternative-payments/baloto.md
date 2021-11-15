---
id: baloto
title: Baloto
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/via-baloto).

## Request a Baloto payment

```java
RequestBalotoSource balotoSource = RequestBalotoSource.builder()
        .country()
        .description()
        .payer(RequestBalotoSource.Payer.builder().build())
        .build();

PaymentRequest request = PaymentRequest.baloto(balotoSource, Currency.COP, 10L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();
```
## Succeed a Baloto payment

This functionality only works in sandbox environment.

```java
api.balotoClient().succeed(paymentId).get();
```

## Expire a Baloto payment

This functionality only works in sandbox environment.

```java
api.balotoClient().expire(paymentId).get();
```

