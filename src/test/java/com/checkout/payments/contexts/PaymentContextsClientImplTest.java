package com.checkout.payments.contexts;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
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
class PaymentContextsClientImplTest {

    private PaymentContextsClient client;

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
        client = new PaymentContextsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRequestPaymentContexts() throws ExecutionException, InterruptedException {

        final PaymentContextsRequest request = mock(PaymentContextsRequest.class);
        final PaymentContextsRequestResponse response = mock(PaymentContextsRequestResponse.class);

        when(apiClient.postAsync(eq("payment-contexts"), eq(authorization), eq(PaymentContextsRequestResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentContextsRequestResponse> future = client.requestPaymentContexts(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetAPaymentContext() throws ExecutionException, InterruptedException {

        final PaymentContextDetailsResponse response = mock(PaymentContextDetailsResponse.class);

        when(apiClient.getAsync(
                "payment-contexts/payment_context_id",
                authorization,
                PaymentContextDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentContextDetailsResponse> future = client.getPaymentContextDetails("payment_context_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

}
