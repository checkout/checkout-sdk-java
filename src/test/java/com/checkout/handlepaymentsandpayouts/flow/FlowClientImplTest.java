package com.checkout.handlepaymentsandpayouts.flow;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionCreateRequest;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionSubmitRequest;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionCompleteRequest;
import com.checkout.handlepaymentsandpayouts.flow.responses.PaymentSessionResponse;
import com.checkout.handlepaymentsandpayouts.flow.responses.PaymentSubmissionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlowClientImplTest {

    private FlowClient client;

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @BeforeEach
    void setUp() {
        setUpAuthorizationMocks();
        client = new FlowClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRequestPaymentSession() throws ExecutionException, InterruptedException {
        final PaymentSessionCreateRequest request = createMockPaymentSessionCreateRequest();
        final PaymentSessionResponse expectedResponse = mock(PaymentSessionResponse.class);

        when(apiClient.postAsync(eq("payment-sessions"), eq(authorization), eq(PaymentSessionResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<PaymentSessionResponse> future = client.requestPaymentSession(request);
        final PaymentSessionResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldSubmitPaymentSession() throws ExecutionException, InterruptedException {
        final String sessionId = "ps_test_123456789";
        final PaymentSessionSubmitRequest request = createMockPaymentSessionSubmitRequest();
        final PaymentSubmissionResponse expectedResponse = mock(PaymentSubmissionResponse.class);

        when(apiClient.postAsync(eq("payment-sessions/" + sessionId + "/submit"), eq(authorization), eq(PaymentSubmissionResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<PaymentSubmissionResponse> future = client.submitPaymentSession(sessionId, request);
        final PaymentSubmissionResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRequestPaymentSessionWithPayment() throws ExecutionException, InterruptedException {
        final PaymentSessionCompleteRequest request = createMockPaymentSessionCompleteRequest();
        final PaymentSubmissionResponse expectedResponse = mock(PaymentSubmissionResponse.class);

        when(apiClient.postAsync(eq("payment-sessions/complete"), eq(authorization), eq(PaymentSubmissionResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<PaymentSubmissionResponse> future = client.requestPaymentSessionWithPayment(request);
        final PaymentSubmissionResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    // Synchronous methods
    @Test
    void shouldRequestPaymentSessionSync() {
        final PaymentSessionCreateRequest request = createMockPaymentSessionCreateRequest();
        final PaymentSessionResponse expectedResponse = mock(PaymentSessionResponse.class);

        when(apiClient.post(eq("payment-sessions"), eq(authorization), eq(PaymentSessionResponse.class),
                eq(request), isNull()))
                .thenReturn(expectedResponse);

        final PaymentSessionResponse actualResponse = client.requestPaymentSessionSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldSubmitPaymentSessionSync() {
        final String sessionId = "ps_test_123456789";
        final PaymentSessionSubmitRequest request = createMockPaymentSessionSubmitRequest();
        final PaymentSubmissionResponse expectedResponse = mock(PaymentSubmissionResponse.class);

        when(apiClient.post(eq("payment-sessions/" + sessionId + "/submit"), eq(authorization), eq(PaymentSubmissionResponse.class),
                eq(request), isNull()))
                .thenReturn(expectedResponse);

        final PaymentSubmissionResponse actualResponse = client.submitPaymentSessionSync(sessionId, request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRequestPaymentSessionWithPaymentSync() {
        final PaymentSessionCompleteRequest request = createMockPaymentSessionCompleteRequest();
        final PaymentSubmissionResponse expectedResponse = mock(PaymentSubmissionResponse.class);

        when(apiClient.post(eq("payment-sessions/complete"), eq(authorization), eq(PaymentSubmissionResponse.class),
                eq(request), isNull()))
                .thenReturn(expectedResponse);

        final PaymentSubmissionResponse actualResponse = client.requestPaymentSessionWithPaymentSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    // Common methods
    private void setUpAuthorizationMocks() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
    }

    private PaymentSessionCreateRequest createMockPaymentSessionCreateRequest() {
        return mock(PaymentSessionCreateRequest.class);
    }

    private PaymentSessionSubmitRequest createMockPaymentSessionSubmitRequest() {
        return mock(PaymentSessionSubmitRequest.class);
    }

    private PaymentSessionCompleteRequest createMockPaymentSessionCompleteRequest() {
        return mock(PaymentSessionCompleteRequest.class);
    }

    private <T> void validateResponse(T expectedResponse, T actualResponse) {
        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse);
    }
}