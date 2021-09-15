---
id: fawry
title: Fawry
---

More information on this topic can be found in the [official docs](https://docs.checkout.com/payments/payment-methods/cash-and-atm-payment/fawry).

## Request a Fawry payment

```java
FawrySource fawrySource = FawrySource.builder()
        .description("Fawry Demo Payment")
        .customerEmail("bruce@wayne-enterprises.com")
        .customerMobile("01058375055")
        .products(Collections.singletonList(
                FawrySource.Product.builder()
                        .id("0123456789")
                        .description("Fawry Demo Product")
                        .price(1000L)
                        .quantity(1L)
                        .build()
        ))
        .build();

PaymentRequest<FawrySource> request = PaymentRequest.fawry(fawrySource, com.checkout.common.beta.Currency.EGP, 1000L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();

String approvalURL = response.getPending().getLink("approve").getHref();
String cancellationURL = response.getPending().getLink("cancel").getHref();
```
## Approve a Fawry payment

```java
api.fawryClient().approve(paymentReference).get();
```

## Expire a Fawry payment

```java
api.fawryClient().cancel(paymentReference).get();
```
