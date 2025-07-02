package com.checkout.handlepaymentsandpayouts.flow;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.PaymentSessionRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.responses.PaymentSessionResponse;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.PaymentSessionWithPaymentRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.responses.PaymentSessionWithPaymentResponse;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.requests.SubmitPaymentSessionRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.responses.SubmitPaymentSessionResponse;
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
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new FlowClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRequestPaymentSessions() throws ExecutionException, InterruptedException {

        final PaymentSessionRequest request = mock(PaymentSessionRequest.class);
        final PaymentSessionResponse response = mock(PaymentSessionResponse.class);

        when(apiClient.postAsync(eq("payment-sessions"), eq(authorization), eq(PaymentSessionResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentSessionResponse> future = client.requestPaymentSession(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldSubmitPaymentSessions() throws ExecutionException, InterruptedException {
        final String paymentId = "pay_mbabizu24mvu3mela5njyhpit4";
        final SubmitPaymentSessionRequest request = mock(SubmitPaymentSessionRequest.class);
        final SubmitPaymentSessionResponse response = mock(SubmitPaymentSessionResponse.class);

        when(apiClient.postAsync(eq("payment-sessions/" + paymentId + "/submit"), eq(authorization), eq(SubmitPaymentSessionResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<SubmitPaymentSessionResponse> future =
                client.submitPaymentSessions(paymentId, request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldRequestPaymentSessionWithPayment() throws ExecutionException, InterruptedException {
        final PaymentSessionWithPaymentRequest request = mock(PaymentSessionWithPaymentRequest.class);
        final PaymentSessionWithPaymentResponse response = mock(PaymentSessionWithPaymentResponse.class);

        when(apiClient.postAsync(eq("payment-sessions/complete"), eq(authorization), eq(PaymentSessionWithPaymentResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentSessionWithPaymentResponse> future =
                client.requestPaymentSessionWithPayment(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }
}
