---
id: fawry
title: Fawry
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/fawry).

## Request a Fawry payment

```java
RequestFawrySource fawrySource = RequestFawrySource.builder()
        .description()
        .customerEmail()
        .customerMobile()
        .products(Collections.singletonList(
                RequestFawrySource.Product.builder().build()
        ))
        .build();

PaymentRequest request = PaymentRequest.fawry(fawrySource, Currency.EGP, 10L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();
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
