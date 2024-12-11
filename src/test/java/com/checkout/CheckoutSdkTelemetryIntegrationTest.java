package com.checkout;

import org.apache.http.Header;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicStatusLine;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static com.checkout.Environment.SANDBOX;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class CheckoutSdkTelemetryIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(CheckoutSdkTelemetryIntegrationTest.class);

    @Test
    void shouldSendTelemetryByDefault() throws Exception {
        // Mock CloseableHttpClient and response
        CloseableHttpClient httpClientMock = mock(CloseableHttpClient.class);
        CloseableHttpResponse responseMock = mock(CloseableHttpResponse.class);

        // Mock the response status line as 200 OK
        when(responseMock.getStatusLine()).thenReturn(
                new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 200, "OK")
        );

        // Mock response headers
        when(responseMock.getAllHeaders()).thenReturn(new Header[]{
                new BasicHeader("Content-Type", "application/json"),
                new BasicHeader("Cko-Request-Id", "test-request-id")
        });

        // Mock response entity (simulated JSON data)
        StringEntity entity = new StringEntity("{\"workflows\": [{\"id\": \"wf_123\", \"name\": \"Test Workflow\"}]}");
        when(responseMock.getEntity()).thenReturn(entity);

        // Configure the HTTP client mock to return the mock response
        when(httpClientMock.execute(any(HttpUriRequest.class))).thenReturn(responseMock);

        // Mock HttpClientBuilder to return the mocked HttpClient
        HttpClientBuilder httpClientBuilderMock = mock(HttpClientBuilder.class);
        when(httpClientBuilderMock.setRedirectStrategy(any())).thenReturn(httpClientBuilderMock);
        when(httpClientBuilderMock.build()).thenReturn(httpClientMock);

        // Build CheckoutApi with mocked components
        CheckoutApi checkoutApi = CheckoutSdk.builder()
                .staticKeys()
                .publicKey(requireNonNull(System.getenv("CHECKOUT_DEFAULT_PUBLIC_KEY")))
                .secretKey(requireNonNull(System.getenv("CHECKOUT_DEFAULT_SECRET_KEY")))
                .environment(SANDBOX)
                .httpClientBuilder(httpClientBuilderMock)
                .build();

        // Execute some requests to test telemetry
        checkoutApi.workflowsClient().getWorkflows().get();
        checkoutApi.workflowsClient().getWorkflows().get();
        checkoutApi.workflowsClient().getWorkflows().get();

        // Capture and verify requests
        ArgumentCaptor<HttpUriRequest> requestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClientMock, atLeastOnce()).execute(requestCaptor.capture());

        // Assert that the telemetry header is present by default
        boolean telemetryHeaderFound = requestCaptor.getAllValues().stream()
                .anyMatch(req -> req.containsHeader("cko-sdk-telemetry"));

        assertTrue(telemetryHeaderFound, "The telemetry header should be present by default");
    }

    @Test
    void shouldNotSendTelemetryWhenOptedOut() throws Exception {
        // Mock CloseableHttpClient and response
        CloseableHttpClient httpClientMock = mock(CloseableHttpClient.class);
        CloseableHttpResponse responseMock = mock(CloseableHttpResponse.class);

        // Mock the response status line as 200 OK
        when(responseMock.getStatusLine()).thenReturn(
                new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 200, "OK")
        );

        // Mock response headers
        when(responseMock.getAllHeaders()).thenReturn(new Header[]{
                new BasicHeader("Content-Type", "application/json"),
                new BasicHeader("Cko-Request-Id", "test-request-id")
        });

        // Mock response entity (simulated JSON data)
        StringEntity entity = new StringEntity("{\"workflows\": [{\"id\": \"wf_123\", \"name\": \"Test Workflow\"}]}");
        when(responseMock.getEntity()).thenReturn(entity);

        // Configure the HTTP client mock to return the mock response
        when(httpClientMock.execute(any(HttpUriRequest.class))).thenReturn(responseMock);

        // Mock HttpClientBuilder to return the mocked HttpClient
        HttpClientBuilder httpClientBuilderMock = mock(HttpClientBuilder.class);
        when(httpClientBuilderMock.setRedirectStrategy(any())).thenReturn(httpClientBuilderMock);
        when(httpClientBuilderMock.build()).thenReturn(httpClientMock);

        // Build CheckoutApi with mocked components and telemetry disabled
        CheckoutApi checkoutApi = CheckoutSdk.builder()
                .staticKeys()
                .publicKey(requireNonNull(System.getenv("CHECKOUT_DEFAULT_PUBLIC_KEY")))
                .secretKey(requireNonNull(System.getenv("CHECKOUT_DEFAULT_SECRET_KEY")))
                .recordTelemetry(false) // Disable telemetry
                .environment(SANDBOX)
                .httpClientBuilder(httpClientBuilderMock)
                .build();

        // Execute some requests to test telemetry
        checkoutApi.workflowsClient().getWorkflows().get();
        checkoutApi.workflowsClient().getWorkflows().get();

        // Capture and verify requests
        ArgumentCaptor<HttpUriRequest> requestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClientMock, atLeastOnce()).execute(requestCaptor.capture());

        // Assert that the telemetry header is not present
        boolean telemetryHeaderFound = requestCaptor.getAllValues().stream()
                .anyMatch(req -> req.containsHeader("cko-sdk-telemetry"));

        assertFalse(telemetryHeaderFound, "The telemetry header should not be present when telemetry is disabled");
    }

    @Test
    void shouldHandleConcurrentRequests() throws Exception {
        // Mock CloseableHttpClient and response
        CloseableHttpClient httpClientMock = mock(CloseableHttpClient.class);
        CloseableHttpResponse responseMock = mock(CloseableHttpResponse.class);

        // Mock the response status line as 200 OK
        when(responseMock.getStatusLine()).thenReturn(
                new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 200, "OK")
        );

        // Mock response headers
        when(responseMock.getAllHeaders()).thenReturn(new Header[]{
                new BasicHeader("Content-Type", "application/json"),
                new BasicHeader("Cko-Request-Id", "test-request-id")
        });

        // Mock response entity
        StringEntity entity = new StringEntity("{\"workflows\": [{\"id\": \"wf_123\", \"name\": \"Test Workflow\"}]}");
        when(responseMock.getEntity()).thenReturn(entity);

        // Configure the HTTP client mock to return the mock response
        when(httpClientMock.execute(any(HttpUriRequest.class))).thenReturn(responseMock);

        // Mock HttpClientBuilder to return the mocked HttpClient
        HttpClientBuilder httpClientBuilderMock = mock(HttpClientBuilder.class);
        when(httpClientBuilderMock.setRedirectStrategy(any())).thenReturn(httpClientBuilderMock);
        when(httpClientBuilderMock.build()).thenReturn(httpClientMock);

        // Build CheckoutApi with mocked components
        CheckoutApi checkoutApi = CheckoutSdk.builder()
                .staticKeys()
                .publicKey(requireNonNull(System.getenv("CHECKOUT_DEFAULT_PUBLIC_KEY")))
                .secretKey(requireNonNull(System.getenv("CHECKOUT_DEFAULT_SECRET_KEY")))
                .environment(SANDBOX)
                .httpClientBuilder(httpClientBuilderMock)
                .build();

        // Prepare a concurrent test environment
        int threadCount = 10;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        // Submit concurrent tasks
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    startLatch.await(); // Wait for all threads to be ready
                    checkoutApi.workflowsClient().getWorkflows().get();
                } catch (Exception e) {
                    log.error("Error occurred during concurrent request: {}", e.getMessage(), e);
                } finally {
                    doneLatch.countDown(); // Signal that the thread has completed
                }
            });
        }

        // Start all threads simultaneously
        startLatch.countDown();
        doneLatch.await(); // Wait for all threads to finish
        executorService.shutdown();

        // Capture and verify requests
        ArgumentCaptor<HttpUriRequest> requestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClientMock, times(threadCount)).execute(requestCaptor.capture());

        List<HttpUriRequest> requests = requestCaptor.getAllValues();
        assertFalse(requests.isEmpty(), "requests mustn't be empty.");

        // Ensure telemetry header exists and is unique for all concurrent requests
        List<String> telemetryHeaders = requests.stream()
                .map(req -> req.getFirstHeader("cko-sdk-telemetry").getValue())
                .collect(Collectors.toList());

        assertEquals(
                telemetryHeaders.stream().distinct().count(),
                threadCount,
                "All concurrent requests should have unique telemetry headers"
        );
    }

    @Test
    void shouldHandleHighLoadRequestsConcurrently() throws Exception {
        // Mock CloseableHttpClient and response
        CloseableHttpClient httpClientMock = mock(CloseableHttpClient.class);
        CloseableHttpResponse responseMock = mock(CloseableHttpResponse.class);

        // Mock the response status line as 200 OK
        when(responseMock.getStatusLine()).thenReturn(
                new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 200, "OK")
        );

        // Mock response headers
        when(responseMock.getAllHeaders()).thenReturn(new Header[]{
                new BasicHeader("Content-Type", "application/json"),
                new BasicHeader("Cko-Request-Id", "test-request-id")
        });

        // Mock response entity
        StringEntity entity = new StringEntity("{\"workflows\": [{\"id\": \"wf_123\", \"name\": \"Test Workflow\"}]}");
        when(responseMock.getEntity()).thenReturn(entity);

        // Configure the HTTP client mock to return the mock response
        when(httpClientMock.execute(any(HttpUriRequest.class))).thenReturn(responseMock);

        // Mock HttpClientBuilder to return the mocked HttpClient
        HttpClientBuilder httpClientBuilderMock = mock(HttpClientBuilder.class);
        when(httpClientBuilderMock.setRedirectStrategy(any())).thenReturn(httpClientBuilderMock);
        when(httpClientBuilderMock.build()).thenReturn(httpClientMock);

        // Build CheckoutApi with mocked components
        CheckoutApi checkoutApi = CheckoutSdk.builder()
                .staticKeys()
                .publicKey(requireNonNull(System.getenv("CHECKOUT_DEFAULT_PUBLIC_KEY")))
                .secretKey(requireNonNull(System.getenv("CHECKOUT_DEFAULT_SECRET_KEY")))
                .environment(SANDBOX)
                .httpClientBuilder(httpClientBuilderMock)
                .build();

        // Simulate high load
        int requestCount = 1000; // Total number of requests
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(requestCount);
        ExecutorService executorService = Executors.newFixedThreadPool(50); // 50 threads

        for (int i = 0; i < requestCount; i++) {
            executorService.submit(() -> {
                try {
                    startLatch.await(); // Wait for all threads to be ready
                    checkoutApi.workflowsClient().getWorkflows().get();
                } catch (Exception e) {
                    log.error("Error occurred during concurrent request: {}", e.getMessage(), e);
                } finally {
                    doneLatch.countDown(); // Signal completion
                }
            });
        }

        // Start all threads and wait for them to finish
        startLatch.countDown();
        doneLatch.await(); // Wait for all threads to complete
        executorService.shutdown();

        // Capture and verify requests
        ArgumentCaptor<HttpUriRequest> requestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClientMock, times(requestCount)).execute(requestCaptor.capture());

        List<HttpUriRequest> requests = requestCaptor.getAllValues();
        assertFalse(requests.isEmpty(), "Requests mustn't be empty.");

        // Ensure all telemetry headers are present and correctly formed
        List<String> telemetryHeaders = requests.stream()
                .map(req -> req.getFirstHeader("cko-sdk-telemetry").getValue())
                .collect(Collectors.toList());

        assertEquals(requestCount, telemetryHeaders.size(), "All requests must include telemetry headers.");
        assertTrue(telemetryHeaders.stream().allMatch(header -> header.contains("\"requestId\"")),
                "Each telemetry header must include a 'requestId'.");
    }
}