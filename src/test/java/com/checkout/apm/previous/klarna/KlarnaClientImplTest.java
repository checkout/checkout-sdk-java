package com.checkout.apm.previous.klarna;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.Environment;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.payments.CaptureResponse;
import com.checkout.payments.VoidRequest;
import com.checkout.payments.VoidResponse;
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
class KlarnaClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private KlarnaClient klarnaClient;

    @BeforeEach
    void setUp() {
        setUpAuthorizationMocks();
        this.klarnaClient = new KlarnaClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldCreateSession() throws ExecutionException, InterruptedException {
        final CreditSessionRequest request = createMockCreditSessionRequest();
        final CreditSessionResponse expectedResponse = mock(CreditSessionResponse.class);

        when(apiClient.postAsync(eq("klarna/credit-sessions"), eq(authorization),
                eq(CreditSessionResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<CreditSessionResponse> future = klarnaClient.createCreditSession(request);
        final CreditSessionResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCreateSession_sandbox() throws ExecutionException, InterruptedException {
        final CreditSessionRequest request = createMockCreditSessionRequest();
        final CreditSessionResponse expectedResponse = mock(CreditSessionResponse.class);

        setUpSandboxEnvironment();

        when(apiClient.postAsync(eq("klarna-external/credit-sessions"), eq(authorization),
                eq(CreditSessionResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<CreditSessionResponse> future = klarnaClient.createCreditSession(request);
        final CreditSessionResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetCreditSession() throws ExecutionException, InterruptedException {
        final CreditSession expectedResponse = mock(CreditSession.class);

        when(apiClient.getAsync("klarna/credit-sessions/session_id", authorization, CreditSession.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<CreditSession> future = klarnaClient.getCreditSession("session_id");
        final CreditSession actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetCreditSession_sandbox() throws ExecutionException, InterruptedException {
        final CreditSession expectedResponse = mock(CreditSession.class);

        setUpSandboxEnvironment();

        when(apiClient.getAsync("klarna-external/credit-sessions/session_id", authorization, CreditSession.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<CreditSession> future = klarnaClient.getCreditSession("session_id");
        final CreditSession actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCapturePayment() throws ExecutionException, InterruptedException {
        final OrderCaptureRequest request = createMockOrderCaptureRequest();
        final CaptureResponse expectedResponse = mock(CaptureResponse.class);

        when(apiClient.postAsync(eq("klarna/orders/payment_id/captures"), eq(authorization),
                eq(CaptureResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<CaptureResponse> future = klarnaClient.capturePayment("payment_id", request);
        final CaptureResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCapturePayment_sandbox() throws ExecutionException, InterruptedException {
        final OrderCaptureRequest request = createMockOrderCaptureRequest();
        final CaptureResponse expectedResponse = mock(CaptureResponse.class);

        setUpSandboxEnvironment();

        when(apiClient.postAsync(eq("klarna-external/orders/payment_id/captures"), eq(authorization),
                eq(CaptureResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<CaptureResponse> future = klarnaClient.capturePayment("payment_id", request);
        final CaptureResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldVoidCapturePayment() throws ExecutionException, InterruptedException {
        final VoidRequest request = createMockVoidRequest();
        final VoidResponse expectedResponse = mock(VoidResponse.class);

        when(apiClient.postAsync(eq("klarna/orders/payment_id/voids"), eq(authorization),
                eq(VoidResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<VoidResponse> future = klarnaClient.voidPayment("payment_id", request);
        final VoidResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldVoidCapturePayment_sandbox() throws ExecutionException, InterruptedException {
        final VoidRequest request = createMockVoidRequest();
        final VoidResponse expectedResponse = mock(VoidResponse.class);

        setUpSandboxEnvironment();

        when(apiClient.postAsync(eq("klarna-external/orders/payment_id/voids"), eq(authorization),
                eq(VoidResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<VoidResponse> future = klarnaClient.voidPayment("payment_id", request);
        final VoidResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    // Synchronous methods
    @Test
    void shouldCreateSessionSync() {
        final CreditSessionRequest request = createMockCreditSessionRequest();
        final CreditSessionResponse expectedResponse = mock(CreditSessionResponse.class);

        when(apiClient.post(eq("klarna/credit-sessions"), eq(authorization),
                eq(CreditSessionResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final CreditSessionResponse actualResponse = klarnaClient.createCreditSessionSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCreateSessionSync_sandbox() {
        final CreditSessionRequest request = createMockCreditSessionRequest();
        final CreditSessionResponse expectedResponse = mock(CreditSessionResponse.class);

        setUpSandboxEnvironment();

        when(apiClient.post(eq("klarna-external/credit-sessions"), eq(authorization),
                eq(CreditSessionResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final CreditSessionResponse actualResponse = klarnaClient.createCreditSessionSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetCreditSessionSync() {
        final CreditSession expectedResponse = mock(CreditSession.class);

        when(apiClient.get("klarna/credit-sessions/session_id", authorization, CreditSession.class))
                .thenReturn(expectedResponse);

        final CreditSession actualResponse = klarnaClient.getCreditSessionSync("session_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetCreditSessionSync_sandbox() {
        final CreditSession expectedResponse = mock(CreditSession.class);

        setUpSandboxEnvironment();

        when(apiClient.get("klarna-external/credit-sessions/session_id", authorization, CreditSession.class))
                .thenReturn(expectedResponse);

        final CreditSession actualResponse = klarnaClient.getCreditSessionSync("session_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCapturePaymentSync() {
        final OrderCaptureRequest request = createMockOrderCaptureRequest();
        final CaptureResponse expectedResponse = mock(CaptureResponse.class);

        when(apiClient.post(eq("klarna/orders/payment_id/captures"), eq(authorization),
                eq(CaptureResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final CaptureResponse actualResponse = klarnaClient.capturePaymentSync("payment_id", request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCapturePaymentSync_sandbox() {
        final OrderCaptureRequest request = createMockOrderCaptureRequest();
        final CaptureResponse expectedResponse = mock(CaptureResponse.class);

        setUpSandboxEnvironment();

        when(apiClient.post(eq("klarna-external/orders/payment_id/captures"), eq(authorization),
                eq(CaptureResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final CaptureResponse actualResponse = klarnaClient.capturePaymentSync("payment_id", request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldVoidCapturePaymentSync() {
        final VoidRequest request = createMockVoidRequest();
        final VoidResponse expectedResponse = mock(VoidResponse.class);

        when(apiClient.post(eq("klarna/orders/payment_id/voids"), eq(authorization),
                eq(VoidResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final VoidResponse actualResponse = klarnaClient.voidPaymentSync("payment_id", request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldVoidCapturePaymentSync_sandbox() {
        final VoidRequest request = createMockVoidRequest();
        final VoidResponse expectedResponse = mock(VoidResponse.class);

        setUpSandboxEnvironment();

        when(apiClient.post(eq("klarna-external/orders/payment_id/voids"), eq(authorization),
                eq(VoidResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final VoidResponse actualResponse = klarnaClient.voidPaymentSync("payment_id", request);

        validateResponse(expectedResponse, actualResponse);
    }

    // Common methods
    private void setUpAuthorizationMocks() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY)).thenReturn(authorization);
        when(checkoutConfiguration.getSdkCredentials()).thenReturn(sdkCredentials);
        when(checkoutConfiguration.getEnvironment()).thenReturn(Environment.PRODUCTION);
    }

    private void setUpSandboxEnvironment() {
        when(checkoutConfiguration.getEnvironment()).thenReturn(Environment.SANDBOX);
    }

    private CreditSessionRequest createMockCreditSessionRequest() {
        return CreditSessionRequest.builder().build();
    }

    private OrderCaptureRequest createMockOrderCaptureRequest() {
        return OrderCaptureRequest.builder().build();
    }

    private VoidRequest createMockVoidRequest() {
        return VoidRequest.builder().build();
    }

    private <T> void validateResponse(T expectedResponse, T actualResponse) {
        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse);
    }

}
