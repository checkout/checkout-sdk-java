---
id: sofort
title: Sofort
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/bank-transfers/sofort).

## Request an Sofort payment

```java
PaymentRequest request = PaymentRequest.sofort(Currency.EUR, 10L)
    .build();

PaymentResponse response = api.paymentsClient().requestPayment(request).get();
```