---
id: payments
title: Payments
---

The full list of request body parameters and possible outcomes can be found [here](https://api-reference.checkout.com/#tag/Payments).

## Request a card payment with a card token

```java
TokenSource tokenSource = new TokenSource(token);

CustomerRequest customer = new CustomerRequest();

PaymentRequest<TokenSource> paymentRequest = PaymentRequest.fromSource(tokenSource, Currency.USD, 1000); 
paymentRequest.setCustomer(customer);
paymentRequest.setReference();

// Optionally, pass arbitrary metadata. Checkout will pass that metadata to the `payment_captured` webhook if the payment succeeds.
Map<String, Object> metadata = new HashMap<String, Object>();
metadata.put("my_custom_key", "my_custom_value"); 
paymentRequest.setMetadata(metadata);

// Optionally, a custom success and failure url can be passed (for cases where 3DS is invoked)
paymentRequest.setSuccessUrl("https://my-website.com/some-page/success");
paymentRequest.setFailureUrl("https://my-website.com/some-page/failure");

PaymentResponse response = api.paymentsClient().requestAsync(paymentRequest).get();
```

## Request a card payment with full card details

```java
CardSource cardSource = new CardSource("4242424242424242", 6, 2025);
cardSource.setCvv("100");

CustomerRequest customer = new CustomerRequest();
customer.setEmail();
customer.setName();

PaymentRequest<CardSource> paymentRequest = PaymentRequest.fromSource(cardSource, Currency.USD, 1000); 
paymentRequest.setCustomer(customer);
paymentRequest.setReference();

PaymentResponse response = api.paymentsClient().requestAsync(paymentRequest).get();
```

## Request a card payment with an existing card

After you perform at least one successful payment for a customer, Checkout.com will return a source.id. This id does not expire, so it can be used to perform subsequent payments without needing the customer to enter the card details again.

```java
IdSource idSource = new IdSource("src_vg3tm54ndfbefotjlmgrrvbxli");

CustomerRequest customer = new CustomerRequest();
customer.setEmail();
customer.setName();

PaymentRequest<IdSource> paymentRequest =
  PaymentRequest.fromSource(idSource, Currency.USD, 1000); 
paymentRequest.setCustomer(customer);
paymentRequest.setReference();

PaymentResponse response = api.paymentsClient().requestAsync(paymentRequest).get();
```

## Request a card payment with an existing customer

After you perform at least one successful payment for a customer, Checkout.com will return a <Highlight color="#02b48f">customer.id</Highlight>. This id can be used perform payments without needing the customer to enter the card details again.

```java
CustomerSource customerSource = new CustomerSource(customerId);

PaymentRequest<CustomerSource> paymentRequest = PaymentRequest.fromSource(customerSource, Currency.USD, 1000); 
paymentRequest.setReference();

PaymentResponse response = api.paymentsClient().requestAsync(paymentRequest).get();
```

## Request a 3D Secure payment

You have the ability to authenticate with 3DS in a payment request. The request body is similar to normal card payments, but with some additional parameters. [Read more about 3DS](https://docs.checkout.com/docs/3d-secure-payments)

```java
TokenSource tokenSource = new TokenSource(token);

CustomerRequest customer = new CustomerRequest();
customer.setEmail();
customer.setName();

PaymentRequest<TokenSource> paymentRequest = PaymentRequest.fromSource(tokenSource, Currency.USD, 1000); 
paymentRequest.setCustomer(customer);
paymentRequest.setReference();
paymentRequest.setThreeDS(ThreeDSRequest.from(true));
paymentRequest.setSuccessUrl("https://my-website.com/some-page/success");
paymentRequest.setFailureUrl("https://my-website.com/some-page/failure");

PaymentResponse response = api.paymentsClient().requestAsync(paymentRequest).get();
```

Similar to local payment options, 3D Secure payments will return a redirection URL. Here is an example of a 3DS response:

```json
{
  "id": "pay_hehfmlkpykeupofyxf7nbr6yyy",
  "status": "Pending",
  "customer": {
    "id": "cus_u4a4zosnrw7ehhzr7jipbkdzo4"
  },
  "3ds": {
    "downgraded": false,
    "enrolled": "Y"
  },
  "_links": {
    "self": {
      "href": "https://api.sandbox.checkout.com/payments/pay_hehfmlkpykeupofyxf7nbr6yyy"
    },
    "redirect": {
      "href": "https://sandbox.checkout.com/api2/v2/3ds/acs/sid_feixbit6us3utfedjulm6egnsu"
    }
  }
}
```

You can access the redirection URL via the SDK like so:

```java
PaymentPending pendingPayment = response.getPending();
String redirectLink = pendingPayment.getRedirectLink();
```

## Handle card payment responses

Payment requests are influenced by risk rules, there are situations where a card payment is requested without 3D Secure, but upgraded by a risk rule, resulting in a 3D Secure response. You are free to handle the response in any way you want, but bellow you have a good example for a starting point:

```java
try {
  PaymentResponse response = api.paymentsClient().requestAsync(...).get();
  if (response.isPending()) {
    // Local/3DS payment. Redirect the customer to payment.getPending().getRedirectLink()
  } else if (response.getPayment().isApproved() && !response.getPayment().getRisk().isFlagged()) {
    // The payment was successful and not flagged by any risk rule
  } else if (response.getPayment().isApproved() && response.getPayment().getRisk().isFlagged()) {
    /* The payment was successful but it was flagged by a risk rule;
       this means you need to manually decide if you want to capture it or void it */
  } else if (!response.getPayment().isApproved()) {
    // The payment was declined
  }
} catch (Exception e) {
  LOG.severe("Payment response could not be handled");
  throw e;
}
```

## Get payment details

Returns the details of the payment with the specified identifier string.

```java
GetPaymentResponse payment = api.paymentsClient().getAsync(paymentOrSessionId).get();
```

## Get payment actions

Returns all the actions associated with a payment ordered by processing date in descending order (latest first).

```java
List<PaymentAction> paymentActions = api.paymentsClient().getActionsAsync(paymentId).get();
```

## Capture a payment

Captures a payment if supported by the payment method.

```java
// Full capture
CaptureResponse response = api.paymentsClient().captureAsync("pay_je5hbbb4u3oe7k4u3lbwlu3zkq").get();

// Or partial capture
CaptureRequest captureRequest = CaptureRequest.builder()
  .reference()
  .amount()
  .build();

CaptureResponse response = api.paymentsClient().captureAsync(paymentId, captureRequest).get();
```

## Refund a payment

Refunds a payment if supported by the payment method.

```java
// Full refund
RefundResponse response = api.paymentsClient().refundAsync(paymentId).get();

// Or partial refund
RefundRequest refundRequest = RefundRequest.builder()
  .reference()
  .amount(100)
  .build();

RefundResponse response = api.paymentsClient().refundAsync(paymentId, refundRequest).get();
```

## Void a payment

Voids a payment if supported by the payment method.

```java
VoidResponse response = api.paymentsClient().voidAsync(paymentId).get();
```

## Create a Payment Link

Create a Payment Link to accept and process payment details.

```java
//Create payment link request
PaymentLinkRequest request = PaymentLinkRequest.builder().build();

PaymentLinkResponse response = api.paymentLinksClient().createAsync(request).get();
```

## Get Payment Link Details

Retrieve details about a specific Payment Link using its ID returned when the link was created. In the response, you will see the status of the Payment Link.

```java
PaymentLinkDetailsResponse response = api.paymentLinksClient().getAsync("paymentLinkId").get();
```

## Create a Hosted Payments Page session

Create a Hosted Payments Page session and pass through all the payment information, like the amount, currency, country and reference.

```java
HostedPaymentRequest request = HostedPaymentRequest.builder().build();

PaymentLinkResponse response = api.hostedPaymentsClient().createAsync(request).get();
```
