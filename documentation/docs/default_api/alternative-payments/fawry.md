---
id: fawry
title: Fawry
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/fawry).

## Request a Fawry payment

```java
FawrySource fawrySource = FawrySource.builder()
        .description()
        .customerEmail()
        .customerMobile()
        .products(Collections.singletonList(
                FawrySource.Product.builder().build()
        ))
        .build();

PaymentRequest<FawrySource> request = PaymentRequest.fawry(fawrySource, com.checkout.common.Currency.EGP, 1000L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();

String approvalURL = response.getPending().getLink("approve").getHref();
String cancellationURL = response.getPending().getLink("cancel").getHref();
```
## Approve a Fawry payment

This functionality only works in sandbox environment.

```java
api.fawryClient().approve(paymentReference).get();
```

## Expire a Fawry payment

This functionality only works in sandbox environment.

```java
api.fawryClient().cancel(paymentReference).get();
```
