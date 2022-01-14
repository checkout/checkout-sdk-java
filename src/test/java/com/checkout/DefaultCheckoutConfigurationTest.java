package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.net.URISyntaxException;
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
            new DefaultCheckoutConfiguration(credentials, (Environment) null, null, null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("environment cannot be null", e.getMessage());
        }
    }

    @Test
    void shouldFailCreatingConfiguration_invalidURI() {
        try {
            final FourStaticKeysSdkCredentials credentials = Mockito.mock(FourStaticKeysSdkCredentials.class);
            new DefaultCheckoutConfiguration(credentials, (URI) null, null, null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("uri cannot be null", e.getMessage());
        }
    }

    @Test
    void shouldCreateConfiguration() throws URISyntaxException {

        final FourStaticKeysSdkCredentials credentials = Mockito.mock(FourStaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, null);
        assertEquals(Environment.PRODUCTION.getUri(), configuration.getBaseUri());

        final CheckoutConfiguration configuration2 = new DefaultCheckoutConfiguration(credentials, new URI("https://www.test.checkout.com/"), null, null, null);
        assertEquals(URI.create("https://www.test.checkout.com/"), configuration2.getBaseUri());

    }

    @Test
    void shouldCreateConfiguration_defaultHttpClientBuilderAndExecutor() {

        final FourStaticKeysSdkCredentials credentials = Mockito.mock(FourStaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, null);

        assertEquals(Environment.PRODUCTION.getUri(), configuration.getBaseUri());
        assertNotNull(configuration.getHttpClientBuilder());
        assertEquals(ForkJoinPool.commonPool(), configuration.getExecutor());
    }

    @Test
    void shouldCreateConfiguration_customHttpClientBuilderAndExecutor() {

        final FourStaticKeysSdkCredentials credentials = Mockito.mock(FourStaticKeysSdkCredentials.class);

        final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        final ExecutorService executorService = Executors.newFixedThreadPool(4);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, httpClientBuilder, executorService, null);

        assertEquals(Environment.PRODUCTION.getUri(), configuration.getBaseUri());
        assertEquals(httpClientBuilder, configuration.getHttpClientBuilder());
        assertEquals(executorService, configuration.getExecutor());
    }

    @Test
    void shouldCreateConfigurationForProd() throws URISyntaxException {

        final FourStaticKeysSdkCredentials credentials = Mockito.mock(FourStaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, null);
        assertEquals(Environment.PRODUCTION.getUri(), configuration.getBaseUri());

        final CheckoutConfiguration configuration2 = new DefaultCheckoutConfiguration(credentials, new URI("https://www.test.checkout.com/"), null, null, null);
        assertEquals(URI.create("https://www.test.checkout.com/"), configuration2.getBaseUri());

    }

}
