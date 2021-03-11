[![Build Status](https://travis-ci.com/checkout/checkout-sdk-java.svg?branch=master)](https://travis-ci.com/checkout/checkout-sdk-java) [![GitHub license](https://img.shields.io/github/license/checkout/checkout-sdk-java.svg)](https://github.com/checkout/checkout-sdk-java/blob/master/LICENSE) [![GitHub release](https://img.shields.io/github/release/checkout/checkout-sdk-java.svg)](https://GitHub.com/checkout/checkout-sdk-java/releases/)

<p align="center"><img src="https://i.ibb.co/6FrwfWt/Screenshot-2020-07-17-at-18-13-39.png" width="20%"></p>

# Checkout.com Java SDK
Built with Java 8 and Gradle 5

# :computer: Import

The jar, javadoc, and sources are all available from [Maven Central](https://search.maven.org/artifact/com.checkout/checkout-sdk-java).

### Gradle

```groovy
dependencies {
    implementation 'com.checkout:checkout-sdk-java:3.3.0'
}
```

### Maven

```xml
<dependency>
    <groupId>com.checkout</groupId>
    <artifactId>checkout-sdk-java</artifactId>
    <version>3.3.0</version>
</dependency>
```

> If you don't have your own API keys, you can sign up for a test account [here](https://www.checkout.com/get-test-account).

# :book: Documentation

You can see the [SDK documentation here](https://checkout.github.io/checkout-sdk-java/getting_started/).

# :heavy_plus_sign: Dependencies
 - gson 2.8.5
 - slf4j-api 1.7.26
 - apache httpclient 4.5.12

# :dash: Quickstart

Here's a simple example of how to create the client and which values it needs to bootstrap:

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

Please see the tests for more examples. There is also a growing collection of samples available under [samples](/samples).

# :construction_worker: Building Locally

The tests require a sandbox account to connect to. Once you have one, you can specify the `CHECKOUT_PUBLIC_KEY` and `CHECKOUT_SECRET_KEY` using environment variables.

Then just run:
```
gradlew build
```

# :sparkles: Contributing and Release Process

This is based on the OSM Lab Atlas project, who have a very helpful document [here](https://github.com/osmlab/atlas/wiki/Gradle,-Travis-CI-and-Maven-Central).

`dev` is the default branch, and all commits/pull requests that are accepted should be merged into here. Travis will then automatically build and test the project, and if successful will merge into `master`. This is the only way code should end up in `master`.

To perform an actual release (and you need permissions on the project to do this, not just anyone can run this) you should:
- run the script `.travis/trigger-release.sh` locally - or just use the Travis UI if you prefer to manually trigger a build on `master`, with the parameters: `"before_script": "export MANUAL_RELEASE_TRIGGERED=true"`.
- This should build master, and publish to https://oss.sonatype.org a release. The version number is taken from the `version` field in the `gradle.properties` file, and the `-SNAPSHOT` suffix removed. 
- Finally a tag is made in Github with that version number. 
- As a manual next step, you should update references in the documentation to the new version number, turn the tag into a GitHub release with documentation on what has changed (and how to access that particular release), and increment the version number in `gradle.properties`, being sure to *keep* the `-SNAPSHOT` suffix.

# :warning: Note

Sometimes there can be random failures when uploading to https://oss.sonatype.org due to timeouts or other difficult to diagnose problems. Generally just trying again should work.
