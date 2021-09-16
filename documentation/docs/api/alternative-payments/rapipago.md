---
id: rapipago
title: Rapi Pago
---

More information on this topic can be found in the [official docs](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/rapipago).

## Request a "Redirect" Rapi Pago payment

```java
RapiPagoSource source = RapiPagoSource.builder()
        .country(CountryCode.AR)
        .description("Simulate Via Rapi pago Demo Payment")
        .payer(Payer.builder()
        .email("bruce@wayne-enterprises.com")
        .name("Bruce Wayne")
        .build())
        .build();

PaymentRequest<RapiPagoSource> request = PaymentRequest.rapiPago(source, Currency.ARS, 1000L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();

String redirectURL = response.getPending().getRedirectLink().getHref()
```

## Succeed a Rapi pago payment

```java
api.rapiPagoClient().succeed("payment_id");
```

## Expire a Rapi pago payment

```java
api.rapiPagoClient().expire("payment_id");
```
