package com.checkout;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.retry.RetryConfig;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.checkout.TestHelper.VALID_DEFAULT_PK;
import static com.checkout.TestHelper.VALID_DEFAULT_SK;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CheckoutSdkBuilderSynchronousTest {

    @Test
    void shouldCreateCheckoutApiWithSynchronousMode() {
        final CheckoutApi checkoutApi = new CheckoutSdkBuilder().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .synchronous(true)
                .build();

        assertNotNull(checkoutApi);
    }

    @Test
    void shouldCreateCheckoutApiWithResilience4jConfiguration() {
        final Resilience4jConfiguration resilience4jConfig = Resilience4jConfiguration.builder()
                .withDefaultCircuitBreaker()
                .withDefaultRetry()
                .build();

        final CheckoutApi checkoutApi = new CheckoutSdkBuilder().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .resilience4jConfiguration(resilience4jConfig)
                .build();

        assertNotNull(checkoutApi);
    }

    @Test
    void shouldCreateCheckoutApiWithSynchronousAndResilience4j() {
        final Resilience4jConfiguration resilience4jConfig = Resilience4jConfiguration.defaultConfiguration();

        final CheckoutApi checkoutApi = new CheckoutSdkBuilder().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .synchronous(true)
                .resilience4jConfiguration(resilience4jConfig)
                .build();

        assertNotNull(checkoutApi);
    }

    @Test
    void shouldCreateCheckoutApiWithoutNewParameters() {
        // Backward compatibility test - should work without new parameters
        final CheckoutApi checkoutApi = new CheckoutSdkBuilder().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .build();

        assertNotNull(checkoutApi);
    }

    @Test
    void shouldCreateCheckoutApiWithCustomResilience4jConfiguration() {
        final CircuitBreakerConfig cbConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .slidingWindowSize(10)
                .build();

        final RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(5)
                .waitDuration(Duration.ofMillis(1000))
                .build();

        final Resilience4jConfiguration resilience4jConfig = Resilience4jConfiguration.builder()
                .withCircuitBreaker(cbConfig)
                .withRetry(retryConfig)
                .build();

        final CheckoutApi checkoutApi = new CheckoutSdkBuilder().staticKeys()
                .publicKey(VALID_DEFAULT_PK)
                .secretKey(VALID_DEFAULT_SK)
                .environment(Environment.SANDBOX)
                .synchronous(true)
                .resilience4jConfiguration(resilience4jConfig)
                .build();

        assertNotNull(checkoutApi);
    }

}

