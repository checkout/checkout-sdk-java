---
id: ideal
title: ideal
---

More information on this topic can be found in the [official docs](https://docs.checkout.com/payments/payment-methods/bank-transfers/ideal).

## Request an Ideal payment
Use the details below to set up your request.
```java
IdealSource source = IdealSource.builder()
        .bic("INGBNL2A")
        .description("ORD50234E89")
        .language("nl")
        .build();

PaymentRequest<IdealSource> request = PaymentRequest.ideal(source, Currency.EUR, 1000L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();
```
## Get a list of supported issuers
Get an up-to-date list of all issuers supporting iDEAL payments.

```java
api.idealClient().getIssuers();
```


