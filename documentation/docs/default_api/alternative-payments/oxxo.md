---
id: oxxo
title: Oxxo
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/oxxo).

## Request a "Redirect" Oxxo payment

```java
RequestOxxoSource source = RequestOxxoSource.builder()
        .country()
        .description()
        .payer(Payer.builder().build())
        .build();

PaymentRequest request = PaymentRequest.oxxo(source, Currency.MXN, 10L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();
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
