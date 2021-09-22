---
id: boleto
title: Boleto
---

More information on this topic can be found in the [official docs](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/boleto-bancario).

## Request a "Redirect" Baloto payment

```java
BoletoSource boletoSource = BoletoSource.builder()
        .country("BR")
        .description("boleto payment")
        .integrationType(IntegrationType.REDIRECT)
        .payer(BoletoSource.Payer.builder()
                    .email("john@doe-enterprises.com")
                    .name("John Doe")
                    .document("53033315550").build())
        .build();

PaymentRequest<BoletoSource> request = PaymentRequest.boleto(boletoSource, com.checkout.common.Currency.BRL, 100L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();

String redirectURL = response.getPending().getRedirectLink().getHref()
```

## Request a "Direct" Baloto payment

```java
BoletoSource boletoSource = BoletoSource.builder()
        .country("BR")
        .description("boleto payment")
        .integrationType(IntegrationType.DIRECT)
        .payer(BoletoSource.Payer.builder()
                    .email("john@doe-enterprises.com")
                    .name("John Doe")
                    .document("53033315550").build())
        .build();

PaymentRequest<BoletoSource> request = PaymentRequest.boleto(boletoSource, com.checkout.common.Currency.BRL, 100L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();
```
