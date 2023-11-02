package com.checkout;

import static java.net.URI.create;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import org.apache.http.HttpStatus;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DefaultCheckoutConfigurationTest {

    private static final HttpClientBuilder DEFAULT_CLIENT_BUILDER = HttpClientBuilder.create();
    private static final Executor DEFAULT_EXECUTOR = ForkJoinPool.commonPool();
    private static final TransportConfiguration DEFAULT_TRANSPORT_CONFIGURATION = new DefaultTransportConfiguration();

    @Test
    void shouldFailCreatingConfiguration() {
        try {
            final StaticKeysSdkCredentials credentials = Mockito.mock(StaticKeysSdkCredentials.class);
            new DefaultCheckoutConfiguration(credentials, null, null, null, null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("environment cannot be null", e.getMessage());
        }
    }

    @Test
    void shouldCreateConfiguration() {

        final StaticKeysSdkCredentials credentials = Mockito.mock(StaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, DEFAULT_TRANSPORT_CONFIGURATION);
        assertEquals(Environment.PRODUCTION, configuration.getEnvironment());

    }

    @Test
    void shouldCreateConfiguration_defaultHttpClientBuilderAndExecutor() {

        final StaticKeysSdkCredentials credentials = Mockito.mock(StaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, DEFAULT_TRANSPORT_CONFIGURATION);

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

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, httpClientBuilder, executorService, transportConfiguration);

        assertEquals(Environment.PRODUCTION, configuration.getEnvironment());
        assertEquals(httpClientBuilder, configuration.getHttpClientBuilder());
        assertEquals(executorService, configuration.getExecutor());
    }

    @Test
    void shouldCreateConfigurationForProd() {

        final StaticKeysSdkCredentials credentials = Mockito.mock(StaticKeysSdkCredentials.class);

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, Environment.PRODUCTION, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, DEFAULT_TRANSPORT_CONFIGURATION);
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

        final CheckoutConfiguration configuration = new DefaultCheckoutConfiguration(credentials, environment, DEFAULT_CLIENT_BUILDER, DEFAULT_EXECUTOR, DEFAULT_TRANSPORT_CONFIGURATION);
        assertEquals(environment, configuration.getEnvironment());
        assertEquals(environment.getCheckoutApi(), configuration.getEnvironment().getCheckoutApi());
        assertEquals(environment.getOAuthAuthorizationApi(), configuration.getEnvironment().getOAuthAuthorizationApi());
        assertEquals(environment.getFilesApi(), configuration.getEnvironment().getFilesApi());
        assertEquals(environment.getTransfersApi(), configuration.getEnvironment().getTransfersApi());
        assertEquals(environment.getBalancesApi(), configuration.getEnvironment().getBalancesApi());
    }

}
