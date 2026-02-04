package com.checkout;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Resilience4jConfigurationTest {

    @Test
    void shouldCreateEmptyConfiguration() {
        final Resilience4jConfiguration config = Resilience4jConfiguration.builder().build();

        assertNotNull(config);
        assertFalse(config.hasCircuitBreaker());
        assertFalse(config.hasRetry());
        assertFalse(config.hasRateLimiter());
        assertNull(config.getCircuitBreaker());
        assertNull(config.getRetry());
        assertNull(config.getRateLimiter());
    }

    @Test
    void shouldCreateConfigurationWithCircuitBreaker() {
        final CircuitBreakerConfig cbConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .slidingWindowSize(10)
                .build();

        final Resilience4jConfiguration config = Resilience4jConfiguration.builder()
                .withCircuitBreaker(cbConfig)
                .build();

        assertNotNull(config);
        assertTrue(config.hasCircuitBreaker());
        assertNotNull(config.getCircuitBreaker());
        assertFalse(config.hasRetry());
        assertFalse(config.hasRateLimiter());
    }

    @Test
    void shouldCreateConfigurationWithDefaultCircuitBreaker() {
        final Resilience4jConfiguration config = Resilience4jConfiguration.builder()
                .withDefaultCircuitBreaker()
                .build();

        assertNotNull(config);
        assertTrue(config.hasCircuitBreaker());
        assertNotNull(config.getCircuitBreaker());
    }

    @Test
    void shouldCreateConfigurationWithRetry() {
        final RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .build();

        final Resilience4jConfiguration config = Resilience4jConfiguration.builder()
                .withRetry(retryConfig)
                .build();

        assertNotNull(config);
        assertTrue(config.hasRetry());
        assertNotNull(config.getRetry());
        assertFalse(config.hasCircuitBreaker());
        assertFalse(config.hasRateLimiter());
    }

    @Test
    void shouldCreateConfigurationWithDefaultRetry() {
        final Resilience4jConfiguration config = Resilience4jConfiguration.builder()
                .withDefaultRetry()
                .build();

        assertNotNull(config);
        assertTrue(config.hasRetry());
        assertNotNull(config.getRetry());
    }

    @Test
    void shouldCreateConfigurationWithRateLimiter() {
        final RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .limitForPeriod(100)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ofSeconds(5))
                .build();

        final Resilience4jConfiguration config = Resilience4jConfiguration.builder()
                .withRateLimiter(rateLimiterConfig)
                .build();

        assertNotNull(config);
        assertTrue(config.hasRateLimiter());
        assertNotNull(config.getRateLimiter());
        assertFalse(config.hasCircuitBreaker());
        assertFalse(config.hasRetry());
    }

    @Test
    void shouldCreateConfigurationWithDefaultRateLimiter() {
        final Resilience4jConfiguration config = Resilience4jConfiguration.builder()
                .withDefaultRateLimiter()
                .build();

        assertNotNull(config);
        assertTrue(config.hasRateLimiter());
        assertNotNull(config.getRateLimiter());
    }

    @Test
    void shouldCreateConfigurationWithAllComponents() {
        final CircuitBreakerConfig cbConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .slidingWindowSize(10)
                .build();

        final RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .build();

        final RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .limitForPeriod(100)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ofSeconds(5))
                .build();

        final Resilience4jConfiguration config = Resilience4jConfiguration.builder()
                .withCircuitBreaker(cbConfig)
                .withRetry(retryConfig)
                .withRateLimiter(rateLimiterConfig)
                .build();

        assertNotNull(config);
        assertTrue(config.hasCircuitBreaker());
        assertTrue(config.hasRetry());
        assertTrue(config.hasRateLimiter());
        assertNotNull(config.getCircuitBreaker());
        assertNotNull(config.getRetry());
        assertNotNull(config.getRateLimiter());
    }

    @Test
    void shouldCreateConfigurationWithPreConfiguredInstances() {
        final CircuitBreaker circuitBreaker = CircuitBreaker.of("test-cb", CircuitBreakerConfig.ofDefaults());
        final Retry retry = Retry.of("test-retry", RetryConfig.ofDefaults());
        final RateLimiter rateLimiter = RateLimiter.of("test-rl", RateLimiterConfig.ofDefaults());

        final Resilience4jConfiguration config = Resilience4jConfiguration.builder()
                .circuitBreaker(circuitBreaker)
                .retry(retry)
                .rateLimiter(rateLimiter)
                .build();

        assertNotNull(config);
        assertTrue(config.hasCircuitBreaker());
        assertTrue(config.hasRetry());
        assertTrue(config.hasRateLimiter());
        assertEquals(circuitBreaker, config.getCircuitBreaker());
        assertEquals(retry, config.getRetry());
        assertEquals(rateLimiter, config.getRateLimiter());
    }

    @Test
    void shouldCreateDefaultConfiguration() {
        final Resilience4jConfiguration config = Resilience4jConfiguration.defaultConfiguration();

        assertNotNull(config);
        assertTrue(config.hasCircuitBreaker());
        assertTrue(config.hasRetry());
        assertNotNull(config.getCircuitBreaker());
        assertNotNull(config.getRetry());
    }

}

