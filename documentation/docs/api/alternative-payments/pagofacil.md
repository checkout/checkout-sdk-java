---
id: pagofacil
title: Pago Facil
---

More information on this topic can be found in the [official docs](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/pago-facil).

## Request a "Redirect" Oxxo payment

```java
PagoFacilSource source = PagoFacilSource.builder()
        .country(CountryCode.AR)
        .description("Simulate Via Pago Facil Demo Payment")
        .payer(Payer.builder()
        .email("bruce@wayne-enterprises.com")
        .name("Bruce Wayne")
        .build())
        .build();

PaymentRequest<PagoFacilSource> request = PaymentRequest.pagoFacil(source, Currency.ARS, 1000L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();

String redirectURL = response.getPending().getRedirectLink().getHref()
```

## Succeed a Pago facil payment

```java
api.pagoFacilClient().succeed("payment_id");
```

## Expire a Pago facil payment

```java
api.pagoFacilClient().expire("payment_id");
```
