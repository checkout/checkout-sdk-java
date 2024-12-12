package com.checkout;

import static java.net.URI.create;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

import org.apache.http.HttpStatus;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

class DefaultCheckoutConfigurationTest {

    private static final HttpClientBuilder DEFAULT_CLIENT_BUILDER = HttpClientBuilder.create();
    private static final Executor DEFAULT_EXECUTOR = ForkJoinPool.commonPool();
    private static final TransportConfiguration DEFAULT_TRANSPORT_CONFIGURATION = new DefaultTransportConfiguration();

    @Test
    void shouldFailCreatingConfiguration() {
        try {
            final StaticKeysSdkCredentials credentials = Mockito.mock(StaticKeysSdkCredentials.class);
            new DefaultCheckoutConfiguration(credentials, null, null, null, null, false);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("environment cannot be null", e.getMessage());
        }
    }

    @Test
    void shouldCreateConfiguration() {

        final StaticKeysSdkCredentials credentials = Mockito.mock(StaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, DEFAULT_TRANSPORT_CONFIGURATION, false);
        assertEquals(Environment.PRODUCTION, configuration.getEnvironment());

    }

    @ParameterizedTest
    @ValueSource(strings = {"a", "ab", "abc", "abc1", "12345domain"})
    void shouldCreateConfigurationWithSubdomain(String subdomain) {

        final StaticKeysSdkCredentials credentials = Mockito.mock(StaticKeysSdkCredentials.class);
        final EnvironmentSubdomain environmentSubdomain = new EnvironmentSubdomain(Environment.SANDBOX, subdomain);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.SANDBOX, environmentSubdomain, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, DEFAULT_TRANSPORT_CONFIGURATION, false);
        assertEquals("https://" + subdomain + ".api.sandbox.checkout.com/", configuration.getEnvironmentSubdomain().getCheckoutApi().toString());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", " - ", "a b", "ab c1"})
    void shouldCreateConfigurationWithBadSubdomain(String subdomain) {

        final StaticKeysSdkCredentials credentials = Mockito.mock(StaticKeysSdkCredentials.class);
        final EnvironmentSubdomain environmentSubdomain = new EnvironmentSubdomain(Environment.SANDBOX, subdomain);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.SANDBOX, environmentSubdomain, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, DEFAULT_TRANSPORT_CONFIGURATION, false);
        assertEquals("https://api.sandbox.checkout.com/", configuration.getEnvironmentSubdomain().getCheckoutApi().toString());
    }

    @Test
    void shouldCreateConfiguration_defaultHttpClientBuilderAndExecutor() {

        final StaticKeysSdkCredentials credentials = Mockito.mock(StaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, DEFAULT_TRANSPORT_CONFIGURATION, false);

        assertEquals(Environment.PRODUCTION, configuration.getEnvironment());
        assertNotNull(configuration.getHttpClientBuilder());
        assertEquals(ForkJoinPool.commonPool(), configuration.getExecutor());
    }

    @Test
    void shouldCreateConfiguration_customHttpClientBuilderAndExecutor() {

        final StaticKeysSdkCredentials credentials = Mockito.mock(StaticKeysSdkCredentials.class);

        final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        final ExecutorService executorService = Executors.newFixedThreadPool(4);
        final TransportConfiguration transportConfiguration = CustomTransportConfiguration.builder()
                .defaultHttpStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .build();

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, httpClientBuilder, executorService, transportConfiguration, false);

        assertEquals(Environment.PRODUCTION, configuration.getEnvironment());
        assertEquals(httpClientBuilder, configuration.getHttpClientBuilder());
        assertEquals(executorService, configuration.getExecutor());
    }

    @Test
    void shouldCreateConfigurationForProd() {

        final StaticKeysSdkCredentials credentials = Mockito.mock(StaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, DEFAULT_TRANSPORT_CONFIGURATION, false);
        assertEquals(Environment.PRODUCTION, configuration.getEnvironment());
    }

    @Test
    void shouldCreateConfigurationWithCustomEnvironment() {

        final CustomEnvironment environment = CustomEnvironment.builder()
                .checkoutApi(create("https://the.base.uri/"))
                .oAuthAuthorizationApi(create("https://the.oauth.uri/connect/token"))
                .filesApi(create("https://the.files.uri/"))
                .transfersApi(create("https://the.transfers.uri/"))
                .balancesApi(create("https://the.balances.uri/"))
                .build();

        final StaticKeysSdkCredentials credentials = Mockito.mock(StaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, environment, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, DEFAULT_TRANSPORT_CONFIGURATION, false);
        assertEquals(environment, configuration.getEnvironment());
        assertEquals(environment.getCheckoutApi(), configuration.getEnvironment().getCheckoutApi());
        assertEquals(environment.getOAuthAuthorizationApi(), configuration.getEnvironment().getOAuthAuthorizationApi());
        assertEquals(environment.getFilesApi(), configuration.getEnvironment().getFilesApi());
        assertEquals(environment.getTransfersApi(), configuration.getEnvironment().getTransfersApi());
        assertEquals(environment.getBalancesApi(), configuration.getEnvironment().getBalancesApi());
    }

    /**
     * Test concurrent creation of configurations to ensure thread-safety or at least no exceptions.
     * This is a simple concurrency test that tries to create multiple configurations in parallel.
     */
    @Test
    void shouldCreateConfigurationsConcurrently() throws InterruptedException, ExecutionException {
        final StaticKeysSdkCredentials credentials = Mockito.mock(StaticKeysSdkCredentials.class);
        int numberOfThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        List<Future<CheckoutConfiguration>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            futures.add(executorService.submit(() -> new DefaultCheckoutConfiguration(
                    credentials, Environment.PRODUCTION, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, DEFAULT_TRANSPORT_CONFIGURATION, true)));
        }

        for (Future<CheckoutConfiguration> future : futures) {
            CheckoutConfiguration config = future.get();
            assertNotNull(config);
            assertEquals(Environment.PRODUCTION, config.getEnvironment());
        }

        executorService.shutdown();
    }

    /**
     * Test to ensure telemetry flag works in concurrency as well.
     * This test tries to create configurations with different telemetry flags and checks them.
     */
    @Test
    void shouldHandleTelemetryFlagConcurrently() throws InterruptedException, ExecutionException {
        final StaticKeysSdkCredentials credentials = Mockito.mock(StaticKeysSdkCredentials.class);
        int numberOfThreads = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        List<Future<Boolean>> futures = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            final boolean flag = (i % 2 == 0);
            futures.add(executorService.submit(() -> {
                CheckoutConfiguration config = new DefaultCheckoutConfiguration(
                        credentials, Environment.PRODUCTION, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, DEFAULT_TRANSPORT_CONFIGURATION, flag);
                return config.isTelemetryEnabled();
            }));
        }

        for (int i = 0; i < numberOfThreads; i++) {
            Boolean result = futures.get(i).get();
            if (i % 2 == 0) {
                assertTrue(result, "Expected telemetry to be enabled for even i=" + i);
            } else {
                assertFalse(result, "Expected telemetry to be disabled for odd i=" + i);
            }
        }

        executorService.shutdown();
    }

}
