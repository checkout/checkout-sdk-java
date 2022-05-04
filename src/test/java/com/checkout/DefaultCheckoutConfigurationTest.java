package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class DefaultCheckoutConfigurationTest {

    private static final HttpClientBuilder DEFAULT_CLIENT_BUILDER = HttpClientBuilder.create();
    private static final Executor DEFAULT_EXECUTOR = ForkJoinPool.commonPool();

    @Test
    void shouldFailCreatingConfiguration() {
        try {
            final FourStaticKeysSdkCredentials credentials = Mockito.mock(FourStaticKeysSdkCredentials.class);
            new DefaultCheckoutConfiguration(credentials, null, null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("environment cannot be null", e.getMessage());
        }
    }

    @Test
    void shouldCreateConfiguration() {

        final FourStaticKeysSdkCredentials credentials = Mockito.mock(FourStaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR);
        assertEquals(Environment.PRODUCTION, configuration.getEnvironment());

    }

    @Test
    void shouldCreateConfiguration_defaultHttpClientBuilderAndExecutor() {

        final FourStaticKeysSdkCredentials credentials = Mockito.mock(FourStaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR);

        assertEquals(Environment.PRODUCTION, configuration.getEnvironment());
        assertNotNull(configuration.getHttpClientBuilder());
        assertEquals(ForkJoinPool.commonPool(), configuration.getExecutor());
    }

    @Test
    void shouldCreateConfiguration_customHttpClientBuilderAndExecutor() {

        final FourStaticKeysSdkCredentials credentials = Mockito.mock(FourStaticKeysSdkCredentials.class);

        final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        final ExecutorService executorService = Executors.newFixedThreadPool(4);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, httpClientBuilder, executorService);

        assertEquals(Environment.PRODUCTION, configuration.getEnvironment());
        assertEquals(httpClientBuilder, configuration.getHttpClientBuilder());
        assertEquals(executorService, configuration.getExecutor());
    }

    @Test
    void shouldCreateConfigurationForProd() {

        final FourStaticKeysSdkCredentials credentials = Mockito.mock(FourStaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR);
        assertEquals(Environment.PRODUCTION, configuration.getEnvironment());

    }

}
