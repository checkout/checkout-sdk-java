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

This SDK can be used with two different pair of API keys provided by Checkout. However, using different API keys implies using specific API features. </br>
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

The SDK allows you to customize the underlying HTTP client and executor configurations to meet your specific needs, such as custom connection pooling, proxy settings, timeout configurations, or multithreaded operations.

### Basic HttpClient Configuration

You can provide your own `HttpClientBuilder` to customize HTTP connection properties:

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

### Custom Executor

You can provide a custom `Executor` for handling asynchronous operations. By default, the SDK uses `ForkJoinPool.commonPool()`.

```java
final Executor customExecutor = Executors.newSingleThreadExecutor();

final CheckoutApi checkoutApi = CheckoutSdk.builder()
        .staticKeys()
        .secretKey("secret_key")
        .environment(Environment.PRODUCTION)  // required
        .executor(customExecutor) // optional for a custom Executor Service
        .build();
```

### Multithreaded Configuration for High-Performance Applications

For applications requiring high concurrency and performance, you can configure both a custom executor with a larger thread pool and an HTTP client optimized for connection pooling:

```java
// Configure HttpClient for high concurrency
final HttpClientBuilder multithreadedHttpClient = HttpClientBuilder.create()
        .setMaxConnTotal(200)                             // Maximum total connections
        .setMaxConnPerRoute(100)                          // Maximum connections per route/destination
        .setConnectionTimeToLive(60, TimeUnit.SECONDS)    // Connection time-to-live
        .evictIdleConnections(30, TimeUnit.SECONDS);      // Evict idle connections after 30 seconds

// Configure a fixed thread pool executor
final Executor multithreadedExecutor = Executors.newFixedThreadPool(100);

final CheckoutApi checkoutApi = CheckoutSdk.builder()
        .staticKeys()
        .secretKey("secret_key")
        .environment(Environment.PRODUCTION)  // required
        .executor(multithreadedExecutor)      // custom thread pool for async operations
        .httpClientBuilder(multithreadedHttpClient) // custom HTTP client for connection pooling
        .build();
```

**Configuration Options Explained:**

- **`executor(Executor)`**: Configures the executor service for handling asynchronous operations. Default is `ForkJoinPool.commonPool()`.
- **`httpClientBuilder(HttpClientBuilder)`**: Configures the Apache HttpClient settings. Default is `HttpClientBuilder.create()`.

**Common HttpClient Settings:**

- **`setMaxConnTotal(int)`**: Maximum number of total connections across all routes
- **`setMaxConnPerRoute(int)`**: Maximum number of connections per specific route/destination
- **`setConnectionTimeToLive(long, TimeUnit)`**: Connection lifetime before being evicted from the pool
- **`evictIdleConnections(long, TimeUnit)`**: Time after which idle connections are evicted
- **`setProxy(HttpHost)`**: Configure HTTP proxy server
- **`setDefaultConnectionConfig(ConnectionConfig)`**: Set default connection configuration
- **`setDefaultSocketConfig(SocketConfig)`**: Set socket configuration including timeouts

## Advanced Customization for Different Throughput Requirements

This section provides specific configuration patterns for different application requirements, focusing on rate limiting and HTTP connection pool optimization.

### HTTP Connection Pool Optimization by Throughput

Configure your HTTP connection pool based on your application's expected throughput and concurrency requirements.

### Performance Tuning Guidelines

#### Connection Pool Sizing Rules of Thumb

- **MaxConnTotal**: Should be at least 2-3x your expected concurrent requests
- **MaxConnPerRoute**: Usually 50-80% of MaxConnTotal for single-endpoint applications
- **Thread Pool Size**: Should match or slightly exceed MaxConnPerRoute
- **Connection TTL**: Shorter for high-load (10-30s), longer for low-load (60-300s)

#### Monitoring and Observability

When using custom configurations, monitor these metrics:

- **Connection Pool Utilization**: Ensure pools aren't exhausted
- **Request Rate**: Verify rate limiting effectiveness  
- **Response Times**: Monitor impact of connection pooling
- **Error Rates**: Track timeouts and connection failures
- **Memory Usage**: Watch for connection pool memory impact

## Retry Strategy and Resilience Configuration

The SDK includes built-in resilience patterns using the [Resilience4j](https://github.com/resilience4j/resilience4j) library to handle transient errors and improve reliability.

### Supported Resilience Patterns

The SDK supports three main resilience patterns:
- **Retry**: Automatically retries failed requests
- **Rate Limiter**: Controls the rate of requests to prevent overloading
- **Circuit Breaker**: Prevents cascading failures by temporarily stopping requests to failing services

### Libraries Used

- **Resilience4j Retry**: `io.github.resilience4j:resilience4j-retry:1.7.1` 
- **Resilience4j Rate Limiter**: `io.github.resilience4j:resilience4j-ratelimiter:1.7.1`
- **Resilience4j Circuit Breaker**: `io.github.resilience4j:resilience4j-circuitbreaker:1.7.1`

### How It Works

The resilience patterns are applied as decorators around your HTTP requests in the following order:
1. **Rate Limiter** (if configured) - Controls request rate
2. **Retry** (if configured) - Retries failed requests
3. **Circuit Breaker** (if configured) - Wraps everything to prevent cascading failures (by default deactivated)

### Transient Errors Handled

The retry mechanism can handle various types of transient errors, including:
- Network connectivity issues (timeouts, connection refused)
- HTTP 5xx server errors (500, 502, 503, 504)
- Temporary service unavailability
- Rate limiting responses (429)

#### Individual Component Configuration

```java
final Resilience4jConfiguration resilience4jConfig = Resilience4jConfiguration.builder()
        .withDefaultRetry()
        // Deactivated by default
        //.withDefaultRateLimiter()
        // Deactivated by default
        //.withDefaultCircuitBreaker()
        .build();

final CheckoutApi checkoutApi = CheckoutSdk.builder()
        .staticKeys()
        .secretKey("secret_key")
        .environment(Environment.PRODUCTION)
        .synchronous(true)
        .resilience4jConfiguration(resilience4jConfig)
        .build();
```

### Custom Resilience Configuration
Default behavior: Asynchronous mode
Resilience4j: NOT applied by default
Transport: Uses async methods (transport.invoke(), transport.submitFile())
Performance: Faster, non-blocking execution but no automatic retries

To enable synchronous mode with Resilience4j patterns, you need to explicitly set:
```java
    CheckoutSdk.builder()    .synchronous(true)
```
So the SDK is designed to be async-first by default, which makes sense for most modern applications that prefer non-blocking operations.

```java
// SYNC transport (with Resilience4j)
@Override
public Response invokeSync(...) {
    // ... build request ...
    final Supplier<Response> callSupplier = () -> performCall(...);
    return executeWithResilience4j(callSupplier);  // ← Resilience4j applied here
}

// ASYNC transport (no Resilience4j)
@Override
public CompletableFuture<Response> invoke(...) {
    return CompletableFuture.supplyAsync(() -> {
        // ... build request ...
        return performCall(...);  // ← Direct call, no Resilience4j
    }, executor);
}
```

#### Custom Retry Configuration

```java
final RetryConfig retryConfig = RetryConfig.custom()
        .maxAttempts(5)                                    // Maximum retry attempts
        .waitDuration(Duration.ofMillis(1000))             // Wait time between retries
        .retryOnException(throwable -> 
            throwable instanceof CheckoutApiException &&   // Retry on API exceptions
            ((CheckoutApiException) throwable).getHttpStatusCode() >= 500) // Only 5xx errors
        .build();

final Resilience4jConfiguration resilience4jConfig = Resilience4jConfiguration.builder()
        .withRetry(retryConfig)
        .build();
```

#### Custom Rate Limiter Configuration
(deactivated by default)

```java
final RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
        .limitForPeriod(50)                                // 50 requests
        .limitRefreshPeriod(Duration.ofSeconds(1))         // Per second
        .timeoutDuration(Duration.ofSeconds(2))            // Wait up to 2 seconds for permission
        .build();

final Resilience4jConfiguration resilience4jConfig = Resilience4jConfiguration.builder()
        .withRateLimiter(rateLimiterConfig)
        .build();
```

#### Custom Circuit Breaker Configuration 
(deactivated by default)

```java
final CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
        .failureRateThreshold(60)                          // Open circuit at 60% failure rate
        .waitDurationInOpenState(Duration.ofSeconds(60))   // Wait 60 seconds before trying again
        .slidingWindowSize(20)                             // Consider last 20 requests
        .minimumNumberOfCalls(10)                          // Minimum calls before evaluation
        .permittedNumberOfCallsInHalfOpenState(5)          // Calls allowed in half-open state
        .build();

final Resilience4jConfiguration resilience4jConfig = Resilience4jConfiguration.builder()
        .withCircuitBreaker(circuitBreakerConfig)
        .build();
```

#### Complete Custom Configuration

```java
final Resilience4jConfiguration resilience4jConfig = Resilience4jConfiguration.builder()
        .withRetry(RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .retryOnException(throwable -> 
                    throwable instanceof CheckoutApiException &&
                    ((CheckoutApiException) throwable).getHttpStatusCode() >= 500)
                .build())
        // Deactivated by default
        //.withRateLimiter(RateLimiterConfig.custom()           
        //        .limitForPeriod(100)
        //        .limitRefreshPeriod(Duration.ofSeconds(1))
        //        .timeoutDuration(Duration.ofSeconds(5))
        //        .build())
        // Deactivated by default
        //.withCircuitBreaker(CircuitBreakerConfig.custom()
        //        .failureRateThreshold(50)
        //        .waitDurationInOpenState(Duration.ofSeconds(30))
        //        .slidingWindowSize(10)
        //        .build())
        .build();

final CheckoutApi checkoutApi = CheckoutSdk.builder()
        .staticKeys()
        .secretKey("secret_key")
        .environment(Environment.PRODUCTION)
        .synchronous(true)
        .resilience4jConfiguration(resilience4jConfig)
        .build();
```

**Important Notes:**
- Resilience4j configuration only works with **synchronous** mode (`synchronous(true)`)
- For asynchronous operations, implement your own retry logic using CompletableFuture patterns
- All resilience patterns are optional - configure only what you need
- Rate limiter helps respect API rate limits and prevent overwhelming the service

## Asynchronous and Synchronous Operations

The SDK provides flexibility in how you handle API operations by supporting both asynchronous and synchronous execution modes. **All client methods return `CompletableFuture`** regardless of the mode, maintaining a consistent API interface.

### Operation Modes

The SDK can be configured to operate in two different modes:

#### Asynchronous Mode (Default)
- **Configuration**: `synchronous(false)` or omit the setting (default)
- **Behavior**: HTTP requests are executed asynchronously using the configured executor
- **Transport**: Uses `Transport.invoke()` and `Transport.submitFile()` methods
- **Resilience**: Resilience4j patterns are **NOT** supported in async mode
- **Performance**: Non-blocking execution, better for high-throughput applications

#### Synchronous Mode
- **Configuration**: `synchronous(true)`
- **Behavior**: HTTP requests are executed synchronously but wrapped in `CompletableFuture` using the executor, even async ops will be executed synchronous is this mode is activated
- **Transport**: Uses `Transport.invokeSync()` and `Transport.submitFileSync()` methods  
- **Resilience**: Full Resilience4j support (retry, rate limiter, circuit breaker)
- **Performance**: Blocking execution with enhanced reliability patterns

### How the Transport Layer Works

#### Asynchronous Transport Behavior

In asynchronous mode, the transport layer:

1. **Immediate Return**: Methods return `CompletableFuture` immediately
2. **Async HTTP Execution**: HTTP calls are made asynchronously using Apache HttpClient
3. **Thread Pool**: Uses the configured executor for CompletableFuture operations
4. **Non-blocking**: The calling thread is not blocked during HTTP execution

```java
// Asynchronous mode configuration
final CheckoutApi asyncApi = CheckoutSdk.builder()
        .staticKeys()
        .secretKey("secret_key")
        .environment(Environment.PRODUCTION)
        .synchronous(false) // or omit (default)
        .executor(Executors.newFixedThreadPool(20)) // Custom thread pool for async operations
        .build();

// Usage - returns immediately, HTTP call happens asynchronously
CompletableFuture<PaymentResponse> future = asyncApi.paymentsClient().requestPayment(request);

// Process result when available
future.thenAccept(response -> {
    System.out.println("Payment ID: " + response.getId());
});
```

#### Synchronous Transport Behavior

In synchronous mode, the transport layer:

1. **Blocking HTTP Call**: HTTP request is made synchronously with resilience patterns applied
2. **Wrapped in CompletableFuture**: Result is wrapped in a `CompletableFuture` using the executor
3. **Resilience4j Applied**: Retry, rate limiting and circuit breaker are applied during the synchronous HTTP call
4. **Consistent API**: Same `CompletableFuture` return type as async mode

```java
// Synchronous mode configuration with resilience
final CheckoutApi syncApi = CheckoutSdk.builder()
        .staticKeys()
        .secretKey("secret_key")
        .environment(Environment.PRODUCTION)
        .synchronous(true) // Enable synchronous mode
        .resilience4jConfiguration(Resilience4jConfiguration.defaultConfiguration())
        .executor(Executors.newFixedThreadPool(10)) // Thread pool for wrapping sync calls
        .build();

// Usage - HTTP call executes synchronously with resilience patterns, then wrapped in CompletableFuture
CompletableFuture<PaymentResponse> future = syncApi.paymentsClient().requestPayment(request);

// Can still use async patterns
future.thenAccept(response -> {
    System.out.println("Payment ID: " + response.getId());
});
```

### Client Implementation Details

All client methods follow the same pattern regardless of mode:

```java
// Example from PaymentsClient - same signature for both modes
CompletableFuture<PaymentResponse> requestPayment(PaymentRequest paymentRequest);
```

#### Internal Implementation (ApiClient Layer)

The `ApiClientImpl.executeAsyncOrSync()` method determines the execution path:

```java
private <T> CompletableFuture<T> executeAsyncOrSync(
    Supplier<T> syncOperation,           // Calls transport.invokeSync() with Resilience4j
    Supplier<CompletableFuture<T>> asyncOperation // Calls transport.invoke() directly
) {
    if (configuration.isSynchronous()) {
        // Execute sync operation in executor to maintain async interface
        return CompletableFuture.supplyAsync(syncOperation, executor);
    }
    return asyncOperation.get(); // Direct async execution
}
```

### Performance Characteristics

#### Asynchronous Mode
- **Pros**:
  - Non-blocking execution
  - Better resource utilization for I/O bound operations
  - Suitable for high-throughput applications
  - Lower memory footprint per request
- **Cons**:
  - No built-in resilience patterns
  - Must implement retry logic manually
  - More complex error handling

#### Synchronous Mode  
- **Pros**:
  - Built-in resilience patterns (retry, rate limiting, circuit breaker)
  - Simplified error handling with automatic retries
  - Easier debugging and testing
  - Transient error handling out-of-the-box
- **Cons**:
  - Blocking HTTP execution (but mitigated by executor wrapping)
  - Higher resource usage due to resilience overhead
  - Not suitable for very high-throughput scenarios

### Choosing the Right Mode

#### Use Asynchronous Mode When:
- Implementing custom retry/resilience logic
- Working with reactive programming patterns
- Resource efficiency is critical
- You need fine-grained control over error handling

#### Use Synchronous Mode When:
- You need robust error handling and retries
- Working in distributed/microservice environments
- Reliability is more important than raw performance
- You want built-in transient error handling
- Implementing payment processing where consistency is critical

### Example Comparison

```java
// Both modes have identical client interfaces
PaymentRequest request = PaymentRequest.builder()
        .source(cardSource)
        .amount(1000L)
        .currency(Currency.USD)
        .build();

// Asynchronous - fast return, manual error handling
CompletableFuture<PaymentResponse> asyncResult = asyncApi.paymentsClient()
        .requestPayment(request)
        .exceptionally(throwable -> {
            // Manual retry logic if needed
            if (shouldRetry(throwable)) {
                return retryPayment(request);
            }
            throw new CompletionException(throwable);
        });

// Synchronous - built-in resilience, automatic retries
CompletableFuture<PaymentResponse> syncResult = syncApi.paymentsClient()
        .requestPayment(request);
        // Resilience4j automatically handles retries for transient errors

// Both return CompletableFuture - same API!
```

### Thread Pool Considerations

- **Async Mode**: Executor primarily used for CompletableFuture operations, HTTP I/O is non-blocking
- **Sync Mode**: Executor used to wrap synchronous operations, size should account for concurrent blocking calls
- **Recommendation**: Use larger thread pools for sync mode to handle blocking operations

### Error Handling Differences

#### Asynchronous Mode
```java
future.exceptionally(throwable -> {
    if (throwable instanceof CheckoutApiException) {
        CheckoutApiException apiEx = (CheckoutApiException) throwable;
        if (apiEx.getHttpStatusCode() >= 500) {
            // Manual retry logic
            return retryOperation();
        }
    }
    return handleError(throwable);
});
```

#### Synchronous Mode
```java
// Resilience4j automatically handles retries for configured conditions
future.thenAccept(response -> {
    // Success handling - retries already attempted if needed
}).exceptionally(throwable -> {
    // Only called after all retry attempts failed
    return handleFinalError(throwable);
});
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
