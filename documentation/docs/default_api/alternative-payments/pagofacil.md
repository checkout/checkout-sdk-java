---
id: pagofacil
title: Pago Facil
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/pago-facil).

## Request a "Redirect" Oxxo payment

```java
RequestPagoFacilSource source = RequestPagoFacilSource.builder()
        .country()
        .description()
        .payer(Payer.builder().build())
        .build();

PaymentRequest request = PaymentRequest.pagoFacil(source, Currency.ARS, 10L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();
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
