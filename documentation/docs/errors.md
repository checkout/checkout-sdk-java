---
id: errors
title: Error Handling
---

You can handle validation and API errors like so:

```java
CheckoutApi api = CheckoutApiImpl.create("sk_XXXX", true, "pk_XXXX");

try {
    // some async request made with the SDK
    PaymentResponse response = api.paymentsClient().requestAsync(...).get();
    ...
} catch (CheckoutValidationException e) {
    return validationError(e.getError());
} catch (CheckoutApiException e) {
    LOG.severe("Request failed with status code " + e.getHttpStatusCode());
    throw e;
}
```

## SDK errors

The errors follow the Checkout.com [API Reference](https://api-reference.checkout.com/).
The errors will be formatted like this:

#### Validation error

```ssh
A validation error of type {error type} occurred with error codes {error codes}.
```

#### API error

```ssh
The API response status code {HTTP status code} does not indicate success...
```

## How errors are determined

The errors above are triggered by status codes that do not fall in the 20* status codes. This means that statuses such as 202 or 204 will not throw an exception.

:::tip

It's important to understand that Declines, or 3D Secure responses do not throw an exception, since the status code associated with them are in the 20* range. In the [Payments](payments.md) section you will see some examples of best practices when it comes to handling responses.

:::
