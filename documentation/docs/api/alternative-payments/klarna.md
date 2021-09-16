---
id: klarna
title: klarna
---

More information on this topic can be found in the [official docs](https://docs.checkout.com/payments/payment-methods/invoice-and-pay-later/klarna).

## Request a Klarna payment
Use the details below to set up your request.
```java
KlarnaSource source = KlarnaSource.builder()
        .authorizationToken("b4bd3423-24e3")
        .locale("en-GB")
        .purchaseCountry(CountryCode.GB)
        .taxAmount(0)
        .billingAddress(KlarnaSource.BillingAddress.builder()
            .givenName("John")
            .familyName("Doe")
            .email("johndoe@email.com")
            .title("Mr")
            .streetAddress("13 New Burlington St")
            .streetAddress2("Apt 214")
            .postalCode("W13 3BG")
            .city("London")
            .phone("01895808221")
            .country(CountryCode.GB)
            .build())
        .customer(KlarnaSource.Customer.builder()
            .dateOfBirth("1970-01-01")
            .gender("male")
            .build())
        .products(List.of(KlarnaProduct.builder()
            .name("Battery Power Pack")
            .quantity(1)
            .unitPrice(1000)
            .taxRate(0)
            .totalAmount(1000)
            .totalTaxAmount(0)
            .build()))
        .build();

PaymentRequest<KlarnaSource> request = PaymentRequest.klarna(source, Currency.EUR, 1000L);

PaymentResponse response = api.paymentsClient().requestAsync(request).get();
```
## Create a credit session
First, you need to create a Klarna session for your customer.

You should provide all known order details at this stage to enable pre-assessment and to personalize the experience for your customer.

If, however, your checkout process is designed in a way that you don't have all the customer's details at this point, you can add them later on when you load the Klarna widget or when you come to authorize the payment.

When you create a session, you will receive the available payment_method_categories, a session_id and a client_token. You can use the session_id to update the session using our API, and the client_token should be passed to the browser. A session is valid for 48 hours.

```java
CreditSessionRequest creditSessionRequest = CreditSessionRequest.builder()
        .purchaseCountry(CountryCode.GB)
        .currency(Currency.GBP)
        .locale("en-GB")
        .amount(1000)
        .taxAmount(0)
        .products(List.of(KlarnaProduct.builder()
            .name("Battery Power Pack")
            .quantity(1)
            .unitPrice(1000)
            .taxRate(0)
            .totalAmount(1000)
            .totalTaxAmount(0)
            .build()))
        .build();

api.klarnaClient().createCreditSession(creditSessionRequest);
```

## Get a credit session
You will receive the available payment_method_categories, a session_id and a client_token. You can use the session_id to update the session using our API, and the client_token should be passed to the browser. A session is valid for 48 hours.

```java
api.klarnaClient().getCreditSession("session_id");
```

## Capture the payment session
Once you've shipped your goods to the customer, you can capture the payment. If, however, you provide the customer with immediate access to your product (e.g., you provide them with an online service), you can capture the payment as soon as the order is placed.

If you're shipping goods, it's a good idea to include the shipping_info for transparency as Klarna will include it in the correspondence with your customer.

```java
OrderCaptureRequest captureRequest = OrderCaptureRequest.builder()
        .amount(1000L)
        .reference("ORD-5023-4E89")
        .metadata(null)
        .klarna(OrderCaptureRequest.Klarna.builder()
            .description("Your order with Checkout.com")
            .products(List.of(KlarnaProduct.builder()
                .name("Battery Power Pack")
                .quantity(1)
                .unitPrice(1000)
                .taxRate(0)
                .totalAmount(1000)
                .totalTaxAmount(0)
                .build()))
            .build())
        .shippingInfo(OrderCaptureRequest.ShippingInfo.builder()
            .shippingCompany("DHL US")
            .shippingMethod("PickUpStore")
            .trackingNumber("63456415674545679874")
            .trackingUri("http://shipping.example/findmypackage?63456415674545679874")
            .returnShippingCompany("DHL US")
            .returnTrackingNumber("93456415674545679888")
            .returnTrackingUri("http://shipping.example/findmypackage?93456415674545679888")
            .build())
        .shippingDelay(0L)
        .build();

api.klarnaClient().capturePayment("payment_id", captureRequest);
```

## Void a payment

```java
VoidRequest voidRequest = VoidRequest.builder()
        .reference("ORD-5023-4E89")
        .build();

api.klarnaClient().voidCapture("payment_id", voidRequest);
```
