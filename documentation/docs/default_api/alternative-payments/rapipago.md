---
id: rapipago
title: Rapi Pago
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/rapipago).

## Request a "Redirect" Rapi Pago payment

```java
RapiPagoSource source = RapiPagoSource.builder()
        .country()
        .description()
        .payer(Payer.builder().build())
        .build();

PaymentRequest<RapiPagoSource> request = PaymentRequest.rapiPago(source, Currency.ARS, 1000L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();

String redirectURL = response.getPending().getRedirectLink().getHref()
```

## Succeed a Rapi pago payment

This functionality only works in sandbox environment.

```java
api.rapiPagoClient().succeed("payment_id");
```

## Expire a Rapi pago payment

```java
api.rapiPagoClient().expire("payment_id");
```
