# Checkout.com Java SDK

![build-status](https://github.com/checkout/checkout-sdk-java/workflows/build-master/badge.svg) 
[![GitHub license](https://img.shields.io/github/license/checkout/checkout-sdk-java.svg)](https://github.com/checkout/checkout-sdk-java/blob/master/LICENSE) 
[![GitHub release](https://img.shields.io/github/release/checkout/checkout-sdk-java.svg)](https://GitHub.com/checkout/checkout-sdk-java/releases/)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=checkout_checkout-sdk-java&metric=alert_status)](https://sonarcloud.io/summary/overall?id=checkout_checkout-sdk-java)
[![OWASP-Dependency-Check](https://github.com/checkout/checkout-sdk-java/actions/workflows/dependency-check.yaml/badge.svg?branch=master)](https://github.com/checkout/checkout-sdk-java/actions/workflows/dependency-check.yaml)

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

Please check in [GitHub releases](https://github.com/checkout/checkout-sdk-java/releases) for all the versions available.

##### Note

Version 5.0.0 is shipped with new Payment Client implementations. The new approach focuses on a more standardized, easy and reliable way of making payment related operations. 

If you're having problems migrating to the new version, please create an [issue](https://github.com/checkout/checkout-sdk-java/issues/new/choose).

## How to use the SDK

This SDK can be used with two different pair of API keys provided by Checkout. However, using different API keys imply using specific API features. Please find in the table below the types of keys that can be used within this SDK.

| Account System | Public Key (example)                    | Secret Key (example)                    |
| -------------- | --------------------------------------- | --------------------------------------- |
| default        | pk_g650ff27-7c42-4ce1-ae90-5691a188ee7b | sk_gk3517a8-3z01-45fq-b4bd-4282384b0a64 |
| Four           | pk_pkhpdtvabcf7hdgpwnbhw7r2uic          | sk_m73dzypy7cf3gf5d2xr4k7sxo4e          |

Note: sandbox keys have a `test_` or `sbox_` identifier, for Default and Four accounts respectively.

If you don't have your own API keys, you can sign up for a test account [here](https://www.checkout.com/get-test-account).

**PLEASE NEVER SHARE OR PUBLISH YOUR CHECKOUT CREDENTIALS.**

## Default

Default keys client instantiation can be done as follows:

```java
import com.checkout.CheckoutApi;

public static void main(String[] args) {

    CheckoutApi checkoutApi = CheckoutSdk.defaultSdk()
        .staticKeys()
        .publicKey("public_key") // optional, only required for operations related with tokens
        .secretKey("secret_key")
        .environment(Environment.SANDBOX) // required
        .build();

    PaymentsClient paymentsClient = checkoutApi.paymentsClient();
    CompletableFuture<RefundResponse> refundResponse = paymentsClient.refundPayment("payment_id");
    
}
```

### Four

If your pair of keys matches the Four type, this is how the SDK should be used:

```java
import com.checkout.four.CheckoutApi;

public static void main(String[] args) {

    CheckoutApi checkoutApi = CheckoutSdk.fourSdk()
        .staticKeys()
        .publicKey("public_key") // optional, only required for operations related with tokens
        .secretKey("secret_key")
        .environment(Environment.SANDBOX) // required
        .httpClientBuilder() // for a custom HTTP Client Builder
        .executor() // for a custom Executor Service
        .build();

    PaymentsClient paymentsClient = checkoutApi.paymentsClient();
    CompletableFuture<RefundResponse> refundResponse = paymentsClient.refundPayment("payment_id");
}
```
The SDK supports client credentials OAuth, when initialized as follows:

```java
import com.checkout.four.CheckoutApi;

public static void main(String[] args) {

    CheckoutApi checkoutApi = CheckoutSdk.fourSdk()
        .oAuth()
        .clientCredentials("client_id", "client_secret")
        // for a specific authorization endpoint
        //.clientCredentials(new URI("https://access.sandbox.checkout.com/connect/token"), "client_id", "client_secret")
        .scopes(FourOAuthScope.GATEWAY, FourOAuthScope.VAULT, FourOAuthScope.FX)
        .environment(Environment.SANDBOX) // required
        .httpClientBuilder() // for a custom HTTP Client Builder
        .executor() // for a custom Executor Service
        .build()

    PaymentsClient paymentsClient = checkoutApi.paymentsClient();
    CompletableFuture<RefundResponse> refundResponse = paymentsClient.refundPayment("payment_id");

}
```

## Exception handling

All the API responses that do not fall in the 2** status codes will cause a `CheckoutApiException`. The exception encapsulates 
the `requestId`, `httpStatusCode` and a map of `errorDetails`, if available.

More documentation related to Checkout API and the SDK is available at:

* [API Reference (Default)](https://api-reference.checkout.com/)
* [API Reference (Four)](https://api-reference.checkout.com/preview/crusoe/)
* [Official Docs (Default)](https://docs.checkout.com/)
* [Official Docs (Four)](https://docs.checkout.com/four)

## Building from source

Once you checkout the code from GitHub, the project can be built using Gradle:

```
gradlew build

# skip tests
gradlew build -x test
```

The execution of integration tests require the following environment variables set in your system:

* For Default account systems: `CHECKOUT_PUBLIC_KEY` & `CHECKOUT_SECRET_KEY`
* For Four account systems: `CHECKOUT_FOUR_PUBLIC_KEY` & `CHECKOUT_FOUR_SECRET_KEY`
* For Four account systems (OAuth): `CHECKOUT_FOUR_OAUTH_CLIENT_ID` & `CHECKOUT_FOUR_OAUTH_CLIENT_SECRET`

## Code of Conduct

Please refer to [Code of Conduct](CODE_OF_CONDUCT.md)

## Licensing

[MIT](LICENSE.md)
