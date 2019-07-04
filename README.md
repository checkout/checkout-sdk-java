[![Build Status](https://travis-ci.com/checkout/checkout-sdk-java.svg?branch=master)](https://travis-ci.com/checkout/checkout-sdk-java) [![GitHub license](https://img.shields.io/github/license/checkout/checkout-sdk-java.svg)](https://github.com/checkout/checkout-sdk-java/blob/master/LICENSE) [![GitHub release](https://img.shields.io/github/release/checkout/checkout-sdk-java.svg)](https://GitHub.com/checkout/checkout-sdk-java/releases/)

# Checkout.com Java SDK
Built with Java 8 and Gradle 5

See https://api-reference.checkout.com/ for the API docs on which this SDK is based.

*** Warning: While we approach version 1.0.0 there could be breaking changes between minor version releases ***

## How to use

#### Importing from Maven Central
The jar, javadoc, and sources are all available from Maven Central. You can import the library into your project like so:

Gradle
```groovy
dependencies {
    implementation 'com.checkout:checkout-sdk-java:0.1.0'
}
```
Maven
```xml
<dependency>
    <groupId>com.checkout</groupId>
    <artifactId>checkout-sdk-java</artifactId>
    <version>0.1.0</version>
</dependency>
```

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
