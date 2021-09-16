package com.checkout.apm.klarna;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.PublicKeyCredentials;
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
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private CreditSessionResponse creditSessionResponse;

    @Mock
    private CreditSession creditSession;

    @Mock
    private VoidResponse voidResponse;

    private KlarnaClient klarnaClient;

    @BeforeEach
    void setUp() {
        this.klarnaClient = new KlarnaClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldCreateSession() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("klarna/credit-sessions"), any(PublicKeyCredentials.class),
                eq(CreditSessionResponse.class), any(CreditSessionRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(creditSessionResponse));

        final CompletableFuture<CreditSessionResponse> future = klarnaClient.createCreditSession(CreditSessionRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(creditSessionResponse, future.get());

    }

    @Test
    void shouldGetCreditSession() throws ExecutionException, InterruptedException {

        when(apiClient.getAsync(eq("klarna/credit-sessions/session_id"), any(PublicKeyCredentials.class), eq(CreditSession.class)))
                .thenReturn(CompletableFuture.completedFuture(creditSession));

        final CompletableFuture<CreditSession> future = klarnaClient.getCreditSession("session_id");

        assertNotNull(future.get());
        assertEquals(creditSession, future.get());

    }

    @Test
    void shouldCapturePayment() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("klarna/orders/payment_id/captures"), any(PublicKeyCredentials.class),
                eq(VoidResponse.class), any(OrderCaptureRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(voidResponse));

        final CompletableFuture<VoidResponse> future = klarnaClient.capturePayment("payment_id", OrderCaptureRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(voidResponse, future.get());

    }

    @Test
    void shouldVoidCapturePayment() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("klarna/orders/payment_id/voids"), any(PublicKeyCredentials.class),
                eq(VoidResponse.class), any(VoidRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(voidResponse));

        final CompletableFuture<VoidResponse> future = klarnaClient.voidCapture("payment_id", VoidRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(voidResponse, future.get());

    }

}