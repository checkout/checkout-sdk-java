---
id: boleto
title: Boleto
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/boleto-bancario).

## Request a "Redirect" Baloto payment

```java
RequestBoletoSource boletoSource = RequestBoletoSource.builder()
        .country()
        .description()
        .integrationType(IntegrationType.REDIRECT)
        .payer(RequestBoletoSource.Payer.builder().build())
        .build();

PaymentRequest request = PaymentRequest.boleto(boletoSource, Currency.BRL, 100L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();
```

## Request a "Direct" Baloto payment

```java
RequestBoletoSource boletoSource = RequestBoletoSource.builder()
        .country()
        .description()
        .integrationType(IntegrationType.DIRECT)
        .payer(RequestBoletoSource.Payer.builder().build())
        .build();

PaymentRequest request = PaymentRequest.boleto(boletoSource, Currency.BRL, 100L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();
```
