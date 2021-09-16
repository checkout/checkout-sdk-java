---
id: oxxo
title: oxxo
---

More information on this topic can be found in the [official docs](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/oxxo).

## Request a "Redirect" Oxxo payment

```java
OxxoSource source = OxxoSource.builder()
        .country(CountryCode.MX)
        .description("Simulate Via Oxxo Demo Payment")
        .payer(Payer.builder()
        .email("bruce@wayne-enterprises.com")
        .name("Bruce Wayne")
        .build())
        .build();

PaymentRequest<OxxoSource> request = PaymentRequest.oxxo(source, Currency.MXN, 1000L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();

String redirectURL = response.getPending().getRedirectLink().getHref()
```

## Succeed a Baloto payment

```java
api.oxxoClient().succeed("payment_id");
```

## Expire a Baloto payment

```java
api.oxxoClient().expire("payment_id");
```
