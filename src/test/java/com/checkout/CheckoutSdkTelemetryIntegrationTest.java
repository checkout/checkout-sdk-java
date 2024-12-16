package com.checkout;

import com.google.gson.Gson;
import lombok.val;
import org.apache.http.Header;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicStatusLine;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
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
import static org.mockito.Mockito.*;

class CheckoutSdkTelemetryIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(CheckoutSdkTelemetryIntegrationTest.class);

    private CloseableHttpClient setupHttpClientMock() throws Exception {
        CloseableHttpClient httpClientMock = mock(CloseableHttpClient.class);
        CloseableHttpResponse responseMock = mock(CloseableHttpResponse.class);

        when(responseMock.getStatusLine()).thenReturn(
                new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 200, "OK")
        );
        when(responseMock.getAllHeaders()).thenReturn(new Header[]{
                new BasicHeader("Content-Type", "application/json"),
                new BasicHeader("Cko-Request-Id", "test-request-id")
        });

        when(responseMock.getEntity()).thenReturn(new StringEntity("{\"workflows\": [{\"id\": \"wf_123\", \"name\": \"Test Workflow\"}]}"));
        when(httpClientMock.execute(any(HttpUriRequest.class))).thenReturn(responseMock);

        return httpClientMock;
    }

    private CheckoutApi buildCheckoutApi(CloseableHttpClient httpClientMock, boolean telemetryEnabled) {
        HttpClientBuilder httpClientBuilderMock = mock(HttpClientBuilder.class);
        when(httpClientBuilderMock.setRedirectStrategy(any())).thenReturn(httpClientBuilderMock);
        when(httpClientBuilderMock.build()).thenReturn(httpClientMock);

        return CheckoutSdk.builder()
                .staticKeys()
                .publicKey(requireNonNull(System.getenv("CHECKOUT_DEFAULT_PUBLIC_KEY")))
                .secretKey(requireNonNull(System.getenv("CHECKOUT_DEFAULT_SECRET_KEY")))
                .recordTelemetry(telemetryEnabled)
                .environment(SANDBOX)
                .httpClientBuilder(httpClientBuilderMock)
                .build();
    }

    private void assertTelemetryHeaderPresent(ArgumentCaptor<HttpUriRequest> requestCaptor) {
        boolean telemetryHeaderFound = requestCaptor.getAllValues().stream()
                .anyMatch(req -> req.containsHeader("cko-sdk-telemetry"));
        assertTrue(telemetryHeaderFound, "The telemetry header should be present by default");
    }

    private void assertTelemetryHeaderContent(String telemetryHeader) {
        Gson gson = new Gson();
        val telemetryData = gson.fromJson(telemetryHeader, Map.class);

        assertTrue(telemetryData.containsKey("request_id"), "Telemetry header must contain 'request_id'");
        assertTrue(telemetryData.containsKey("prev_request_id"), "Telemetry header must contain 'prev_request_id'");
        assertTrue(telemetryData.containsKey("prev_request_duration"), "Telemetry header must contain 'prev_request_duration'");
        assertEquals(3, telemetryData.size(), "Telemetry header must only contain 'request_id', 'prev_request_id', and 'prev_request_duration'");
    }

    @Test
    void shouldSendTelemetryByDefault() throws Exception {
        CloseableHttpClient httpClientMock = setupHttpClientMock();
        CheckoutApi checkoutApi = buildCheckoutApi(httpClientMock, true);

        checkoutApi.workflowsClient().getWorkflows().get();
        checkoutApi.workflowsClient().getWorkflows().get();
        checkoutApi.workflowsClient().getWorkflows().get();

        ArgumentCaptor<HttpUriRequest> requestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClientMock, atLeastOnce()).execute(requestCaptor.capture());

        assertTelemetryHeaderPresent(requestCaptor);
    }

    @Test
    void shouldNotSendTelemetryWhenOptedOut() throws Exception {
        CloseableHttpClient httpClientMock = setupHttpClientMock();
        CheckoutApi checkoutApi = buildCheckoutApi(httpClientMock, false);

        checkoutApi.workflowsClient().getWorkflows().get();
        checkoutApi.workflowsClient().getWorkflows().get();

        ArgumentCaptor<HttpUriRequest> requestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClientMock, atLeastOnce()).execute(requestCaptor.capture());

        boolean telemetryHeaderFound = requestCaptor.getAllValues().stream()
                .anyMatch(req -> req.containsHeader("cko-sdk-telemetry"));
        assertFalse(telemetryHeaderFound, "The telemetry header should not be present when telemetry is disabled");
    }

    @Test
    void shouldValidateTelemetryHeaderContent() throws Exception {
        CloseableHttpClient httpClientMock = setupHttpClientMock();
        CheckoutApi checkoutApi = buildCheckoutApi(httpClientMock, true);

        checkoutApi.workflowsClient().getWorkflows().get();

        ArgumentCaptor<HttpUriRequest> requestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClientMock, times(1)).execute(requestCaptor.capture());

        String telemetryHeader = requestCaptor.getValue().getFirstHeader("cko-sdk-telemetry").getValue();
        assertTelemetryHeaderContent(telemetryHeader);
    }

    @Test
    void shouldHandleConcurrentRequests() throws Exception {
        CloseableHttpClient httpClientMock = setupHttpClientMock();
        CheckoutApi checkoutApi = buildCheckoutApi(httpClientMock, true);

        int threadCount = 10;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    startLatch.await();
                    checkoutApi.workflowsClient().getWorkflows().get();
                } catch (Exception e) {
                    log.error("Error during concurrent request", e);
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        doneLatch.await();
        executorService.shutdown();

        ArgumentCaptor<HttpUriRequest> requestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClientMock, times(threadCount)).execute(requestCaptor.capture());

        List<String> telemetryHeaders = requestCaptor.getAllValues().stream()
                .map(req -> req.getFirstHeader("cko-sdk-telemetry").getValue())
                .collect(Collectors.toList());

        assertEquals(threadCount, telemetryHeaders.size(), "All requests must include telemetry headers");
        assertEquals(threadCount, telemetryHeaders.stream().distinct().count(), "All requests should have unique telemetry headers");
    }

    @Test
    @Disabled("run as needed")
    void shouldHandleHighLoadRequestsConcurrently() throws Exception {
        CloseableHttpClient httpClientMock = setupHttpClientMock();
        CheckoutApi checkoutApi = buildCheckoutApi(httpClientMock, true);

        int requestCount = 1000;
        int threadCount = 50;
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(requestCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < requestCount; i++) {
            executorService.submit(() -> {
                try {
                    startLatch.await();
                    checkoutApi.workflowsClient().getWorkflows().get();
                } catch (Exception e) {
                    log.error("Error during high-load request", e);
                } finally {
                    doneLatch.countDown();
                }
            });
        }

        startLatch.countDown();
        doneLatch.await();
        executorService.shutdown();

        ArgumentCaptor<HttpUriRequest> requestCaptor = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClientMock, times(requestCount)).execute(requestCaptor.capture());

        List<HttpUriRequest> requests = requestCaptor.getAllValues();
        assertFalse(requests.isEmpty(), "Requests must not be empty");

        List<String> telemetryHeaders = requests.stream()
                .map(req -> req.getFirstHeader("cko-sdk-telemetry").getValue())
                .collect(Collectors.toList());

        assertEquals(requestCount, telemetryHeaders.size(), "All requests must include telemetry headers");
        assertEquals(requestCount, telemetryHeaders.stream().distinct().count(),
                "Each request should have a unique telemetry header");
    }
}
