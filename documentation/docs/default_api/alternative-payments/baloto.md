---
id: baloto
title: Baloto
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/via-baloto).

## Request a Baloto payment

```java
BalotoSource balotoSource = BalotoSource.builder()
        .country()
        .description()
        .payer(BalotoSource.Payer.builder().build())
        .build();

PaymentRequest<BalotoSource> request = PaymentRequest.baloto(balotoSource, com.checkout.common.Currency.COP, 100000L);

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

