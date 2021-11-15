---
id: sofort
title: Sofort
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/four/payments/payment-methods/sofort).

## Request an Sofort payment

```java
PaymentRequest request = Payments.sofort(Currency.EUR, 10L)
    .build();

PaymentResponse response = api.paymentsClient().requestPayment(request).get();
```