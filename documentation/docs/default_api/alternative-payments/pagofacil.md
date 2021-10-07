---
id: pagofacil
title: Pago Facil
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/pago-facil).

## Request a "Redirect" Oxxo payment

```java
PagoFacilSource source = PagoFacilSource.builder()
        .country()
        .description()
        .payer(Payer.builder().build())
        .build();

PaymentRequest<PagoFacilSource> request = PaymentRequest.pagoFacil(source, Currency.ARS, 1000L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();

String redirectURL = response.getPending().getRedirectLink().getHref()
```

## Succeed a Pago facil payment

This functionality only works in sandbox environment.

```java
api.pagoFacilClient().succeed("payment_id");
```

## Expire a Pago facil payment

This functionality only works in sandbox environment.

```java
api.pagoFacilClient().expire("payment_id");
```
