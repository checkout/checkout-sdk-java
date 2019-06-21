[![Build Status](https://travis-ci.com/checkout/checkout-sdk-java.svg?branch=master)](https://travis-ci.com/checkout/checkout-sdk-java)

# Checkout.com Java SDK
Built with Java 8 and Gradle 5

## How to use

#### Importing from Maven Central
Coming soon!

#### Using the SDK

Here's a simple example of how to create the client and which values it needs to bootstrap.
```java
import com.checkout.CheckoutApi;
import com.checkout.CheckoutApiImpl;
import com.checkout.payments.RefundResponse;

import java.util.concurrent.CompletableFuture;

class Main {
    public static void main(String[] args) {
        String secretKey = "my_checkout_secret_key";
        String publicKey = "my_checkout_public_key";
        boolean useSandbox = true;
        
        CheckoutApi checkoutApi = CheckoutApiImpl.create(secretKey, useSandbox, publicKey);
        
        String paymentId = "my_test_payment_id";
        
        CompletableFuture<RefundResponse> refundResponse = checkoutApi.paymentsClient().refundAsync(paymentId);
        // etc...
    }
}
```

Please see the tests for more examples. Samples will be provided in due course.

## Dependencies
This project is compiled with Gson 2.8.5 and depends on a compatible version of Gson at runtime. If you are adding the jar directly to your project you will need to provide Gson as well.

## To Build

The tests require a sandbox account to connect to. Once you have one you can specify the `CHECKOUT_PUBLIC_KEY` and `CHECKOUT_SECRET_KEY` using environment variables.
Then just run:
```
gradlew build
```
