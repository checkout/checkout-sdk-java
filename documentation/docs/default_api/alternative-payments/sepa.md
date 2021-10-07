---
id: sepa
title: SEPA
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/direct-debit/sepa-direct-debit).

## Request a SEPA payment

```java
SepaSource source = new SepaSource(sourceId);

PaymentRequest<RapiPagoSource> request = PaymentRequest.sepa(source, Currency.ARS, 1000L, "referece");

PaymentResponse response = api.paymentsClient().requestAsync(request).get();

String redirectURL = response.getPending().getRedirectLink().getHref()
```

## Cancel a mandate

```java
api.sepaClient().cancelMandate("mandateId");
```

## Get a mandate

```java
api.sepaClient().getMandate("mandateId");
```

## Cancel a mandate via PPRO

```java
api.sepaClient().cancelMandateViaPPRO("mandateId");
```

## Get a mandate via PPRO

```java
api.sepaClient().getMandateViaPPRO("mandateId");
```
