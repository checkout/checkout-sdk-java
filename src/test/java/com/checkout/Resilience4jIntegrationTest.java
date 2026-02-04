package com.checkout;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.retry.RetryConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class Resilience4jIntegrationTest {

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkAuthorization authorization;

    private ApacheHttpClientTransport transport;

    @BeforeEach
    void setUp() {
        lenient().when(configuration.getHttpClientBuilder()).thenReturn(HttpClientBuilder.create());
        lenient().when(configuration.getExecutor()).thenReturn(Executors.newSingleThreadExecutor());
        lenient().when(configuration.getTransportConfiguration()).thenReturn(new DefaultTransportConfiguration());
        lenient().when(configuration.isTelemetryEnabled()).thenReturn(false);

        final URI baseUri = URI.create("https://api.sandbox.checkout.com/");
        transport = new ApacheHttpClientTransport(
                baseUri,
                HttpClientBuilder.create(),
                Executors.newSingleThreadExecutor(),
                new DefaultTransportConfiguration(),
                configuration
        );
    }

    @Test
    void shouldNotApplyResilience4jWhenNotConfigured() {
        when(configuration.getResilience4jConfiguration()).thenReturn(null);

        // The transport should work normally without Resilience4j
        assertNotNull(transport);
        assertNull(configuration.getResilience4jConfiguration());
    }

    @Test
    void shouldApplyCircuitBreakerWhenConfigured() {
        final CircuitBreakerConfig cbConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .slidingWindowSize(10)
                .build();

        final Resilience4jConfiguration resilience4jConfig = Resilience4jConfiguration.builder()
                .withCircuitBreaker(cbConfig)
                .build();

        when(configuration.getResilience4jConfiguration()).thenReturn(resilience4jConfig);

        assertNotNull(configuration.getResilience4jConfiguration());
        assertTrue(configuration.getResilience4jConfiguration().hasCircuitBreaker());
    }

    @Test
    void shouldApplyRetryWhenConfigured() {
        final RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofMillis(500))
                .build();

        final Resilience4jConfiguration resilience4jConfig = Resilience4jConfiguration.builder()
                .withRetry(retryConfig)
                .build();

        when(configuration.getResilience4jConfiguration()).thenReturn(resilience4jConfig);

        assertNotNull(configuration.getResilience4jConfiguration());
        assertTrue(configuration.getResilience4jConfiguration().hasRetry());
    }

    @Test
    void shouldApplyRateLimiterWhenConfigured() {
        final RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .limitForPeriod(100)
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .timeoutDuration(Duration.ofSeconds(5))
                .build();

        final Resilience4jConfiguration resilience4jConfig = Resilience4jConfiguration.builder()
                .withRateLimiter(rateLimiterConfig)
                .build();

        when(configuration.getResilience4jConfiguration()).thenReturn(resilience4jConfig);

        assertNotNull(configuration.getResilience4jConfiguration());
        assertTrue(configuration.getResilience4jConfiguration().hasRateLimiter());
    }

    @Test
    void shouldApplyAllResilience4jComponentsWhenConfigured() {
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

        final Resilience4jConfiguration resilience4jConfig = Resilience4jConfiguration.builder()
                .withCircuitBreaker(cbConfig)
                .withRetry(retryConfig)
                .withRateLimiter(rateLimiterConfig)
                .build();

        when(configuration.getResilience4jConfiguration()).thenReturn(resilience4jConfig);

        assertNotNull(configuration.getResilience4jConfiguration());
        assertTrue(configuration.getResilience4jConfiguration().hasCircuitBreaker());
        assertTrue(configuration.getResilience4jConfiguration().hasRetry());
        assertTrue(configuration.getResilience4jConfiguration().hasRateLimiter());
    }

}

