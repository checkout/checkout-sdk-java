package com.checkout.apm.klarna;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KlarnaClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @Mock
    private CreditSessionResponse creditSessionResponse;

    @Mock
    private CreditSession creditSession;

    @Mock
    private VoidResponse voidResponse;

    @Mock
    private CaptureResponse captureResponse;

    private KlarnaClient klarnaClient;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.PUBLIC_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        this.klarnaClient = new KlarnaClientImpl(apiClient, configuration);
    }

    @Test
    void shouldCreateSession() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("klarna/credit-sessions"), eq(authorization),
                eq(CreditSessionResponse.class), any(CreditSessionRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(creditSessionResponse));

        final CompletableFuture<CreditSessionResponse> future = klarnaClient.createCreditSession(CreditSessionRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(creditSessionResponse, future.get());

    }

    @Test
    void shouldCreateSession_sandbox() throws ExecutionException, InterruptedException {

        when(configuration.getUri()).thenReturn(Environment.SANDBOX.getUri());

        when(apiClient.postAsync(eq("klarna-external/credit-sessions"), eq(authorization),
                eq(CreditSessionResponse.class), any(CreditSessionRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(creditSessionResponse));

        final CompletableFuture<CreditSessionResponse> future = klarnaClient.createCreditSession(CreditSessionRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(creditSessionResponse, future.get());

    }

    @Test
    void shouldGetCreditSession() throws ExecutionException, InterruptedException {

        when(apiClient.getAsync(eq("klarna/credit-sessions/session_id"), eq(authorization), eq(CreditSession.class)))
                .thenReturn(CompletableFuture.completedFuture(creditSession));

        final CompletableFuture<CreditSession> future = klarnaClient.getCreditSession("session_id");

        assertNotNull(future.get());
        assertEquals(creditSession, future.get());

    }

    @Test
    void shouldGetCreditSession_sandbox() throws ExecutionException, InterruptedException {

        when(configuration.getUri()).thenReturn(Environment.SANDBOX.getUri());

        when(apiClient.getAsync(eq("klarna-external/credit-sessions/session_id"), eq(authorization), eq(CreditSession.class)))
                .thenReturn(CompletableFuture.completedFuture(creditSession));

        final CompletableFuture<CreditSession> future = klarnaClient.getCreditSession("session_id");

        assertNotNull(future.get());
        assertEquals(creditSession, future.get());

    }

    @Test
    void shouldCapturePayment() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("klarna/orders/payment_id/captures"), eq(authorization),
                eq(CaptureResponse.class), any(OrderCaptureRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(captureResponse));

        final CompletableFuture<CaptureResponse> future = klarnaClient.capturePayment("payment_id", OrderCaptureRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(captureResponse, future.get());

    }

    @Test
    void shouldCapturePayment_sandbox() throws ExecutionException, InterruptedException {

        when(configuration.getUri()).thenReturn(Environment.SANDBOX.getUri());

        when(apiClient.postAsync(eq("klarna-external/orders/payment_id/captures"), eq(authorization),
                eq(CaptureResponse.class), any(OrderCaptureRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(captureResponse));

        final CompletableFuture<CaptureResponse> future = klarnaClient.capturePayment("payment_id", OrderCaptureRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(captureResponse, future.get());

    }

    @Test
    void shouldVoidCapturePayment() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("klarna/orders/payment_id/voids"), eq(authorization),
                eq(VoidResponse.class), any(VoidRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(voidResponse));

        final CompletableFuture<VoidResponse> future = klarnaClient.voidPayment("payment_id", VoidRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(voidResponse, future.get());

    }

    @Test
    void shouldVoidCapturePayment_sandbox() throws ExecutionException, InterruptedException {

        when(configuration.getUri()).thenReturn(Environment.SANDBOX.getUri());

        when(apiClient.postAsync(eq("klarna-external/orders/payment_id/voids"), eq(authorization),
                eq(VoidResponse.class), any(VoidRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(voidResponse));

        final CompletableFuture<VoidResponse> future = klarnaClient.voidPayment("payment_id", VoidRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(voidResponse, future.get());

    }

}
