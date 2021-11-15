---
id: klarna
title: klarna
---

The full list of request body parameters and possible outcomes can be found [here](https://docs.checkout.com/payments/payment-methods/invoice-and-pay-later/klarna).

## Request a Klarna payment

```java
RequestKlarnaSource source = RequestKlarnaSource.builder()
        .authorizationToken()
        .locale()
        .purchaseCountry()
        .taxAmount()
        .billingAddress(RequestKlarnaSource.BillingAddress.builder().build())
        .customer(RequestKlarnaSource.Customer.builder().build())
        .products(List.of(KlarnaProduct.builder().build()))
        .build();

PaymentRequest request = PaymentRequest.klarna(source, Currency.EUR, 10L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();
```
## Create a credit session

First, you need to create a Klarna session for your customer.

You should provide all known order details at this stage to enable pre-assessment and to personalize the experience for your customer.

If, however, your checkout process is designed in a way that you don't have all the customer's details at this point, you can add them later on when you load the Klarna widget or when you come to authorize the payment.

When you create a session, you will receive the available payment_method_categories, a session_id and a client_token. You can use the session_id to update the session using our API, and the client_token should be passed to the browser. A session is valid for 48 hours.

```java
CreditSessionRequest creditSessionRequest = CreditSessionRequest.builder()
        .purchaseCountry()
        .currency()
        .locale()
        .amount()
        .taxAmount()
        .products(List.of(KlarnaProduct.builder().build()))
        .build();

CreditSessionResponse response = api.klarnaClient().createCreditSession(creditSessionRequest).get();
```

## Get a credit session

You will receive the available payment_method_categories, a session_id and a client_token. You can use the session_id to update the session using our API, and the client_token should be passed to the browser. A session is valid for 48 hours.

```java
CreditSession response = api.klarnaClient().getCreditSession("session_id").get();
```

## Capture the payment session

Once you've shipped your goods to the customer, you can capture the payment. If, however, you provide the customer with immediate access to your product (e.g., you provide them with an online service), you can capture the payment as soon as the order is placed.

If you're shipping goods, it's a good idea to include the shipping_info for transparency as Klarna will include it in the correspondence with your customer.

```java
OrderCaptureRequest captureRequest = OrderCaptureRequest.builder()
        .amount()
        .reference()
        .metadata()
        .klarna(OrderCaptureRequest.Klarna.builder().build()))
        .shippingInfo(OrderCaptureRequest.ShippingInfo.builder().build())
        .shippingDelay()
        .build();

CaptureResponse response = api.klarnaClient().capturePayment("payment_id", captureRequest).get();
```

## Void a payment

```java
VoidRequest voidRequest = VoidRequest.builder()
        .reference()
        .build();

VoidResponse response = api.klarnaClient().voidPayment("payment_id", voidRequest).get();
```
