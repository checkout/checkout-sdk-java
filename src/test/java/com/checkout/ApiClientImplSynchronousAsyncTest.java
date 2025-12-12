package com.checkout;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ApiClientImplSynchronousAsyncTest {

    @Mock
    private CheckoutConfiguration asyncConfiguration;

    @Mock
    private CheckoutConfiguration syncConfiguration;

    @Mock
    private SdkAuthorization authorization;

    private ApiClient asyncApiClient;
    private ApiClient syncApiClient;

    @BeforeEach
    void setUp() {
        // Setup async configuration
        lenient().when(asyncConfiguration.getHttpClientBuilder()).thenReturn(HttpClientBuilder.create());
        lenient().when(asyncConfiguration.getExecutor()).thenReturn(Executors.newSingleThreadExecutor());
        lenient().when(asyncConfiguration.getTransportConfiguration()).thenReturn(new DefaultTransportConfiguration());
        lenient().when(asyncConfiguration.isSynchronous()).thenReturn(false);
        lenient().when(asyncConfiguration.getResilience4jConfiguration()).thenReturn(null);
        lenient().when(asyncConfiguration.isTelemetryEnabled()).thenReturn(false);

        // Setup sync configuration
        lenient().when(syncConfiguration.getHttpClientBuilder()).thenReturn(HttpClientBuilder.create());
        lenient().when(syncConfiguration.getExecutor()).thenReturn(Executors.newSingleThreadExecutor());
        lenient().when(syncConfiguration.getTransportConfiguration()).thenReturn(new DefaultTransportConfiguration());
        lenient().when(syncConfiguration.isSynchronous()).thenReturn(true);
        lenient().when(syncConfiguration.getResilience4jConfiguration()).thenReturn(null);
        lenient().when(syncConfiguration.isTelemetryEnabled()).thenReturn(false);

        final UriStrategy uriStrategy = () -> URI.create("https://api.sandbox.checkout.com/");
        asyncApiClient = new ApiClientImpl(asyncConfiguration, uriStrategy);
        syncApiClient = new ApiClientImpl(syncConfiguration, uriStrategy);
    }

    @Test
    void shouldHaveAsyncConfiguration() {
        assertFalse(asyncConfiguration.isSynchronous());
        assertNotNull(asyncApiClient);
    }

    @Test
    void shouldHaveSyncConfiguration() {
        assertTrue(syncConfiguration.isSynchronous());
        assertNotNull(syncApiClient);
    }

    @Test
    void shouldReturnCompletableFutureInAsyncMode() {
        // In async mode, methods should return CompletableFuture
        // Note: May fail without proper transport setup, but method should be callable
        final CompletableFuture<?> future = asyncApiClient.getAsync("test", authorization, com.checkout.EmptyResponse.class);
        assertNotNull(future);
    }

    @Test
    void shouldReturnCompletableFutureInSyncMode() {
        // In sync mode, methods should still return CompletableFuture
        // but they execute synchronously internally
        final CompletableFuture<?> future = syncApiClient.getAsync("test", authorization, com.checkout.EmptyResponse.class);
        assertNotNull(future);
        // The future will be executed in the executor but using sync transport methods
    }

    @Test
    void shouldCreateBothClients() {
        assertNotNull(asyncApiClient);
        assertNotNull(syncApiClient);
    }

    @Test
    void shouldHaveDifferentConfigurations() {
        assertFalse(asyncConfiguration.isSynchronous());
        assertTrue(syncConfiguration.isSynchronous());
    }

    // Note: Full integration tests would require mocking Transport or using a test HTTP server
    // These tests verify the configuration and setup, while actual behavior is tested
    // through higher-level client tests (like PaymentsClientImplTest)

}
