---
id: baloto
title: Baloto
---

More information on this topic can be found in the [official docs](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/via-baloto).

## Request a Baloto payment

```java
BalotoSource balotoSource = BalotoSource.builder()
        .country("CO")
        .description("simulate Via Baloto Demo Payment")
        .payer(BalotoSource.Payer.builder()
               .email("bruce@wayne-enterprises.com")
               .name("Bruce Wayne")
               .build())
        .build();

PaymentRequest<BalotoSource> request = PaymentRequest.baloto(balotoSource, com.checkout.common.beta.Currency.COP, 100000L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();
```
## Succeed a Baloto payment

```java
api.balotoClient().succeed(paymentId).get();
```

## Expire a Baloto payment

```java
api.balotoClient().expire(paymentId).get();
```

