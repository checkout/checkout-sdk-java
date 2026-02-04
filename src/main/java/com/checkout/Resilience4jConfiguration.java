package com.checkout;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import java.time.Duration;

/**
 * Configuration for Resilience4j components (Circuit Breaker, Retry, Rate Limiter)
 * to be used with synchronous methods.
 * All components are optional. If not configured, they won't be applied.
 */
public class Resilience4jConfiguration {

    private CircuitBreaker circuitBreaker;
    private Retry retry;
    private RateLimiter rateLimiter;

    private Resilience4jConfiguration() {
    }

    /**
     * Creates a builder for Resilience4jConfiguration
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a default configuration with basic circuit breaker and retry settings
     */
    public static Resilience4jConfiguration defaultConfiguration() {
        return builder()
                .withCircuitBreaker(CircuitBreakerConfig.custom()
                        .failureRateThreshold(50)
                        .waitDurationInOpenState(Duration.ofSeconds(30))
                        .slidingWindowSize(10)
                        .build())
                .withRetry(RetryConfig.custom()
                        .maxAttempts(3)
                        .waitDuration(Duration.ofMillis(500))
                        .build())
                .build();
    }

    public CircuitBreaker getCircuitBreaker() {
        return circuitBreaker;
    }

    public Retry getRetry() {
        return retry;
    }

    public RateLimiter getRateLimiter() {
        return rateLimiter;
    }

    public boolean hasCircuitBreaker() {
        return circuitBreaker != null;
    }

    public boolean hasRetry() {
        return retry != null;
    }

    public boolean hasRateLimiter() {
        return rateLimiter != null;
    }

    public static class Builder {
        private CircuitBreaker circuitBreaker;
        private Retry retry;
        private RateLimiter rateLimiter;

        /**
         * Configures a Circuit Breaker with the provided configuration
         */
        public Builder withCircuitBreaker(CircuitBreakerConfig config) {
            this.circuitBreaker = CircuitBreaker.of("checkout-sdk-circuit-breaker", config);
            return this;
        }

        /**
         * Configures a Circuit Breaker with default settings
         */
        public Builder withDefaultCircuitBreaker() {
            return withCircuitBreaker(CircuitBreakerConfig.custom()
                    .failureRateThreshold(50)
                    .waitDurationInOpenState(Duration.ofSeconds(30))
                    .slidingWindowSize(10)
                    .build());
        }

        /**
         * Configures a Retry with the provided configuration
         */
        public Builder withRetry(RetryConfig config) {
            this.retry = Retry.of("checkout-sdk-retry", config);
            return this;
        }

        /**
         * Configures a Retry with default settings
         */
        public Builder withDefaultRetry() {
            return withRetry(RetryConfig.custom()
                    .maxAttempts(3)
                    .waitDuration(Duration.ofMillis(500))
                    .build());
        }

        /**
         * Configures a Rate Limiter with the provided configuration
         */
        public Builder withRateLimiter(RateLimiterConfig config) {
            this.rateLimiter = RateLimiter.of("checkout-sdk-rate-limiter", config);
            return this;
        }

        /**
         * Configures a Rate Limiter with default settings (100 requests per second)
         */
        public Builder withDefaultRateLimiter() {
            return withRateLimiter(RateLimiterConfig.custom()
                    .limitForPeriod(100)
                    .limitRefreshPeriod(Duration.ofSeconds(1))
                    .timeoutDuration(Duration.ofSeconds(5))
                    .build());
        }

        /**
         * Uses a pre-configured Circuit Breaker instance
         */
        public Builder circuitBreaker(CircuitBreaker circuitBreaker) {
            this.circuitBreaker = circuitBreaker;
            return this;
        }

        /**
         * Uses a pre-configured Retry instance
         */
        public Builder retry(Retry retry) {
            this.retry = retry;
            return this;
        }

        /**
         * Uses a pre-configured Rate Limiter instance
         */
        public Builder rateLimiter(RateLimiter rateLimiter) {
            this.rateLimiter = rateLimiter;
            return this;
        }

        public Resilience4jConfiguration build() {
            Resilience4jConfiguration config = new Resilience4jConfiguration();
            config.circuitBreaker = this.circuitBreaker;
            config.retry = this.retry;
            config.rateLimiter = this.rateLimiter;
            return config;
        }
    }
}

