---
id: giropay
title: Giropay
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/bank-transfers/giropay).

## Request a Giropay payment

```java
RequestGiropaySource giropaySource = RequestGiropaySource.builder()
        .bic()
        .purpose()
        .build();

PaymentRequest request = PaymentRequest.giropay(giropaySource, Currency.EUR, 10L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();
```
