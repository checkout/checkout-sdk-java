# Checkout.com Java SDK

![build-status](https://github.com/checkout/checkout-sdk-java/workflows/build-master/badge.svg) [![GitHub license](https://img.shields.io/github/license/checkout/checkout-sdk-java.svg)](https://github.com/checkout/checkout-sdk-java/blob/master/LICENSE) [![GitHub release](https://img.shields.io/github/release/checkout/checkout-sdk-java.svg)](https://GitHub.com/checkout/checkout-sdk-java/releases/)

<p><img src="https://i.ibb.co/6FrwfWt/Screenshot-2020-07-17-at-18-13-39.png" width="20%"></p>

## Getting started

Binaries, Javadoc, and sources are all available from [Maven Central](https://search.maven.org/artifact/com.checkout/checkout-sdk-java).

#### Gradle

```groovy
dependencies {
    implementation 'com.checkout:checkout-sdk-java:<version>'
}
```
#### Maven

```xml
<dependency>
    <groupId>com.checkout</groupId>
    <artifactId>checkout-sdk-java</artifactId>
    <version>version</version>
</dependency>
```

Please check in GitHub releases for all the versions available.

## How to use the SDK

This SDK can be used with two different pair of API keys provided by Checkout. However, using different API keys imply using specific API features. Please find in the table below the types of keys that can be used within this SDK.

| Account System | Public Key (example)                         | Secret Key (example)                         |
| -------------- | -------------------------------------------- | -------------------------------------------- |
| default        | pk_test_fe70ff27-7c42-4ce1-ae90-5691a188ee7b | sk_test_fde517a8-3z01-41ef-b4bd-4282384b0a64 |
| Four           | pk_sbox_pkhpdtvmkgf7hdgpwnbhw7r2uic          | sk_sbox_m73dzypy7cf3gfd46xr4yj5xo4e          |

If you don't have your own API keys, you can sign up for a test account [here](https://www.checkout.com/get-test-account).

```java
public static void main(String[] args) {

    boolean useSandbox = true;

    CheckoutApi checkoutApi = CheckoutApiImpl.create("secret_key", useSandbox, "public_key");

    PaymentsClient paymentsClient = checkoutApi.paymentsClient();
    CompletableFuture<RefundResponse> refundResponse = paymentsClient.refundAsync("payment_id");
    
}
```

### Four (BETA)

If your pair of keys matches the Four type, this is how the SDK should be used:

```java
public static void main(String[] args) {

    CheckoutApi checkoutApi = Checkout.staticKeys()
                .environment(Environment.SANDBOX)
                .build();

    PaymentsClient paymentsClient = checkoutApi.paymentsClient();
    CompletableFuture<RefundResponse> refundResponse = paymentsClient.refundPayment("payment_id");
}
```

Please note that at the moment, support for Four API keys is quite limited. Future releases will provide a bigger feature set for this credential type.

More examples can be found in the following places:

* Samples available under [samples](/samples)
* Integration tests in this repo, located under [src/test](/src/test)

## Building from source

Once you checkout the code from GitHub, the project can be built using Gradle:

```
gradlew build

# skip tests
gradlew build -x test
```

Integration tests execution requires two pairs of keys, and the following environment variables set in your system:

* For Classic account systems: `CHECKOUT_PUBLIC_KEY` & `CHECKOUT_SECRET_KEY`
* For Four account systems: `CHECKOUT_FOUR_PUBLIC_KEY` & `CHECKOUT_FOUR_SECRET_KEY`

## Code of Conduct

Please refer to [Code of Conduct](CODE_OF_CONDUCT.md)

## Licensing

[MIT](https://github.com/checkout/checkout-sdk-java/blob/dev/LICENSE.md)

