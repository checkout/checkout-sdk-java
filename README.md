# Checkout.com Java SDK

[![build-status](https://github.com/checkout/checkout-sdk-java/workflows/build-master/badge.svg)](https://github.com/checkout/checkout-sdk-java/actions/workflows/build-master.yml)
![CodeQL](https://github.com/checkout/checkout-sdk-java/workflows/CodeQL/badge.svg)
[![OWASP-Dependency-Check](https://github.com/checkout/checkout-sdk-java/actions/workflows/dependency-check.yaml/badge.svg?branch=master)](https://github.com/checkout/checkout-sdk-java/actions/workflows/dependency-check.yaml)

![build-status](https://github.com/checkout/checkout-sdk-java/workflows/build-release/badge.svg)
[![GitHub release](https://img.shields.io/github/release/checkout/checkout-sdk-java.svg)](https://GitHub.com/checkout/checkout-sdk-java/releases/)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.checkout/checkout-sdk-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.checkout/checkout-sdk-java/)

[![GitHub license](https://img.shields.io/github/license/checkout/checkout-sdk-java.svg)](https://github.com/checkout/checkout-sdk-java/blob/master/LICENSE.md)

## Getting started

> **Version 6.0.0 is here!**
>  <br/><br/>
> We improved the initialization of SDK making it easier to understand the available options. <br/>
> Now `NAS` accounts are the default instance for the SDK and `ABC` structure was moved to a `previous` prefixes. <br/>
> If you have been using this SDK before, you may find the following important changes:
> * Marketplace module was moved to Accounts module, same for classes and references.
> * In most cases, IDE can help you determine from where to import, but if you’re still having issues don't hesitate to open a [ticket](https://github.com/checkout/checkout-sdk-java/issues/new/choose).

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

### :rocket: Please check in [GitHub releases](https://github.com/checkout/checkout-sdk-java/releases) for all the versions available.

### :book: Checkout our official documentation.

* [Official Docs (Default)](https://docs.checkout.com/)
* [Official Docs (Previous)](https://docs.checkout.com/previous)

### :books: Check out our official API documentation guide, where you can also find more usage examples.

* [API Reference (Default)](https://api-reference.checkout.com/)
* [API Reference (Previous)](https://api-reference.checkout.com/previous)


## How to use the SDK

This SDK can be used with two different pair of API keys provided by Checkout. However, using different API keys imply using specific API features. </br>
Please find in the table below the types of keys that can be used within this SDK.

| Account System | Public Key (example)                    | Secret Key (example)                    |
|----------------|-----------------------------------------|-----------------------------------------|
| Default        | pk_abcdef123456ghijkl789mnopqr          | sk_123456ghijklm7890abcdefxyz           |
| Previous       | pk_12345678-abcd-efgh-ijkl-mnopqrstuvwx | sk_abcdef12-3456-ghij-klmn-opqrstuvwxyz |

Note: sandbox keys have a `sbox_` or `test_` identifier, for Default and Previous accounts respectively.

If you don't have your own API keys, you can sign up for a test account [here](https://www.checkout.com/get-test-account).

**PLEASE NEVER SHARE OR PUBLISH YOUR CHECKOUT CREDENTIALS.**

### Default

Default keys client instantiation can be done as follows:

```java
import com.checkout.CheckoutApi;

public static void main(String[] args) {
    
    final CheckoutApi checkoutApi = CheckoutSdk.builder()
            .staticKeys()
            .publicKey("public_key")  // optional, only required for operations related with tokens
            .secretKey("secret_key")
            .environment(Environment.PRODUCTION)  // required
            .environmentSubdomain("subdomain") // optional, Merchant-specific DNS name
            .executor() // optional for a custom Executor Service
            .build();
    
    final PaymentsClient client = checkoutApi.paymentsClient();
    
    final CompletableFuture<RefundResponse> refundPayment = client.refundPayment("payment_id");
}
```

### Default OAuth

The SDK supports client credentials OAuth, when initialized as follows:

```java
import com.checkout.CheckoutApi;

public static void main(String[] args) {

final CheckoutApi checkoutApi = CheckoutSdk.builder()
        .oAuth()
        .clientCredentials("client_id", "client_secret")
        // for a specific authorization endpoint
        //.clientCredentials(new URI("https://access.sandbox.checkout.com/connect/token"), "client_id", "client_secret")
        .scopes(OAuthScope.GATEWAY, OAuthScope.VAULT, OAuthScope.FX)
        .environment(Environment.PRODUCTION)  // required
        .environmentSubdomain("subdomain") // optional, Merchant-specific DNS name
        .executor() // optional for a custom Executor Service
        .build();

final PaymentsClient client = checkoutApi.paymentsClient();

final CompletableFuture<RefundResponse> refundPayment = client.refundPayment("payment_id");
}
```

### Previous

If your pair of keys matches the Previous type, this is how the SDK should be used:

```java
import com.checkout.previous.CheckoutApi;

public static void main(String[] args) {
    final CheckoutApi checkoutApi = CheckoutSdk.builder()
            .previous()
            .staticKeys()
            .publicKey("public_key")  // optional, only required for operations related with tokens
            .secretKey("secret_key")
            .environment(Environment.PRODUCTION)  // required
            .environmentSubdomain("subdomain") // optional, Merchant-specific DNS name
            .executor() // optional for a custom Executor Service
            .build();
    
    final PaymentsClient client = checkoutApi.paymentsClient();
    
    final CompletableFuture<RefundResponse> refundPayment = client.refundPayment("payment_id");
        }
```

## Custom Environment

In case that you want to use an integrator or mock server, you can specify your own configuration as follows:
```java
final CustomEnvironment environment = CustomEnvironment.builder()
        .checkoutApi(create("https://the.base.uri/")) // the uri for all Checkout operations
        .oAuthAuthorizationApi(create("https://the.oauth.uri/connect/token")) // the uri used for OAUTH authorization, only required for OAuth operations
        .filesApi(create("https://the.files.uri/")) // the uri used for Files operations, only required for Accounts module
        .transfersApi(create("https://the.transfers.uri/")) // the uri used for Transfer operations, only required for Transfers module
        .balancesApi(create("https://the.balances.uri/")) // the uri used for Balances operations, only required for Balances module
        .sandbox(false) // flag to determine if Sanbox or Production configured, default false
        .build();

final CheckoutApi checkoutApi = CheckoutSdk.builder()
        .staticKeys()
        .secretKey("secret_key")
        .environment(environment)  // required
        .executor(customHttpClient) // optional for a custom Executor Service
        .build();
```

You don't need to specify all the previous URI's, only the ones for the modules that you're going to use.

## Custom HttpClient

Sometimes you need a custom thread pool, or any custom http property, so you can provide your own httpClient configuration as follows.

```java
final HttpClientBuilder customHttpClient = HttpClientBuilder.create()
        .setProxy(HttpHost.create("https://proxy"))
        .setConnectionTimeToLive(3, TimeUnit.SECONDS);

final CheckoutApi checkoutApi = CheckoutSdk.builder()
        .staticKeys()
        .secretKey("secret_key")
        .environment(Environment.PRODUCTION)  // required
        .environmentSubdomain("subdomain") // optional, Merchant-specific DNS name
        .httpClientBuilder(customHttpClient) // optional for a custom HttpClient
        .build();
```

## Logging

The SDK supports SLF4J as logger provider, you need to provide your configuration file through `resources` folder.

## Exception handling

All the API responses that do not fall in the 2** status codes will cause a `CheckoutApiException`. The exception encapsulates
the `responseHeaders`, `httpStatusCode` and a map of `errorDetails`, if available.

## Building from source

Once you check out the code from GitHub, the project can be built using Gradle:

```
gradlew build

# skip tests
gradlew build -x test
```

The execution of integration tests require the following environment variables set in your system:

* For default account systems (NAS): `CHECKOUT_DEFAULT_PUBLIC_KEY` & `CHECKOUT_DEFAULT_SECRET_KEY`
* For default account systems (OAuth): `CHECKOUT_DEFAULT_OAUTH_CLIENT_ID` & `CHECKOUT_DEFAULT_OAUTH_CLIENT_SECRET`
* For Previous account systems (ABC): `CHECKOUT_PREVIOUS_PUBLIC_KEY` & `CHECKOUT_PREVIOUS_SECRET_KEY`

## Telemetry
Request telemetry is enabled by default in the Java SDK. Request latency is included in the telemetry data. Recording the request latency allows Checkout.com to continuously monitor and improve the merchant experience.

Request telemetry can be disabled by opting out during CheckoutSdk builder step:
```java
final CheckoutApi checkoutApi = CheckoutSdk.builder()
        .staticKeys()
        .secretKey("secret_key")
        .environment(Environment.PRODUCTION)
        .recordTelemetry(false)
        .build();
```

## Code of Conduct

Please refer to [Code of Conduct](CODE_OF_CONDUCT.md)

## Licensing

[MIT](LICENSE.md)
