---
id: boleto
title: Boleto
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/boleto-bancario).

## Request a "Redirect" Baloto payment

```java
BoletoSource boletoSource = BoletoSource.builder()
        .country()
        .description()
        .integrationType(IntegrationType.REDIRECT)
        .payer(BoletoSource.Payer.builder().build())
        .build();

PaymentRequest<BoletoSource> request = PaymentRequest.boleto(boletoSource, com.checkout.common.Currency.BRL, 100L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();

String redirectURL = response.getPending().getRedirectLink().getHref()
```

## Request a "Direct" Baloto payment

```java
BoletoSource boletoSource = BoletoSource.builder()
        .country()
        .description()
        .integrationType(IntegrationType.DIRECT)
        .payer(BoletoSource.Payer.builder().build())
        .build();

PaymentRequest<BoletoSource> request = PaymentRequest.boleto(boletoSource, com.checkout.common.Currency.BRL, 100L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();
```
