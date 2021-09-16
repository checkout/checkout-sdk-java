---
id: sepa
title: SEPA
---

More information on this topic can be found in the [official docs](https://docs.checkout.com/payments/payment-methods/direct-debit/sepa-direct-debit).

## Request a SEPA payment

```java
SepaSource source = new SepaSource("src_a3wfgafsyedefaobbqadqw34vy");

PaymentRequest<RapiPagoSource> request = PaymentRequest.sepa(source, Currency.ARS, 1000L, "referece");

PaymentResponse response = api.paymentsClient().requestAsync(request).get();

String redirectURL = response.getPending().getRedirectLink().getHref()
```

## Cancel a mandate

```java
api.sepaClient().cancelMandate("mandate_id");
```

## Get a mandate

```java
api.sepaClient().getMandate("mandate_id");
```

## Cancel a mandate via PPRO

```java
api.sepaClient().cancelMandateViaPPRO("mandate_id");
```

## Get a mandate via PPRO

```java
api.sepaClient().getMandateViaPPRO("mandate_id");
```