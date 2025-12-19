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
        final PaymentContextsRequestResponse response = testRequestPaymentContextsAsync();
        
        assertNotNull(response);
    }

    @Test
    void shouldGetAPaymentContext() throws ExecutionException, InterruptedException {
        final PaymentContextDetailsResponse response = testGetPaymentContextDetailsAsync();
        
        assertNotNull(response);
    }

    // Synchronous methods
    @Test
    void shouldRequestPaymentContextsSync() {
        final PaymentContextsRequestResponse response = testRequestPaymentContextsSync();
        
        assertNotNull(response);
    }

    @Test
    void shouldGetAPaymentContextSync() {
        final PaymentContextDetailsResponse response = testGetPaymentContextDetailsSync();
        
        assertNotNull(response);
    }

    // Common test logic methods
    private PaymentContextsRequestResponse testRequestPaymentContextsAsync() throws ExecutionException, InterruptedException {
        final PaymentContextsRequest request = createMockPaymentContextsRequest();
        final PaymentContextsRequestResponse expectedResponse = mock(PaymentContextsRequestResponse.class);

        when(apiClient.postAsync(eq("payment-contexts"), eq(authorization), eq(PaymentContextsRequestResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<PaymentContextsRequestResponse> future = client.requestPaymentContexts(request);
        final PaymentContextsRequestResponse actualResponse = future.get();
        
        assertEquals(expectedResponse, actualResponse);
        return actualResponse;
    }

    private PaymentContextsRequestResponse testRequestPaymentContextsSync() {
        final PaymentContextsRequest request = createMockPaymentContextsRequest();
        final PaymentContextsRequestResponse expectedResponse = mock(PaymentContextsRequestResponse.class);

        when(apiClient.post(eq("payment-contexts"), eq(authorization), eq(PaymentContextsRequestResponse.class),
                eq(request), isNull()))
                .thenReturn(expectedResponse);

        final PaymentContextsRequestResponse actualResponse = client.requestPaymentContextsSync(request);
        
        assertEquals(expectedResponse, actualResponse);
        return actualResponse;
    }

    private PaymentContextDetailsResponse testGetPaymentContextDetailsAsync() throws ExecutionException, InterruptedException {
        final PaymentContextDetailsResponse expectedResponse = mock(PaymentContextDetailsResponse.class);

        when(apiClient.getAsync(
                "payment-contexts/payment_context_id",
                authorization,
                PaymentContextDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<PaymentContextDetailsResponse> future = client.getPaymentContextDetails("payment_context_id");
        final PaymentContextDetailsResponse actualResponse = future.get();
        
        assertEquals(expectedResponse, actualResponse);
        return actualResponse;
    }

    private PaymentContextDetailsResponse testGetPaymentContextDetailsSync() {
        final PaymentContextDetailsResponse expectedResponse = mock(PaymentContextDetailsResponse.class);

        when(apiClient.get(
                "payment-contexts/payment_context_id",
                authorization,
                PaymentContextDetailsResponse.class))
                .thenReturn(expectedResponse);

        final PaymentContextDetailsResponse actualResponse = client.getPaymentContextDetailsSync("payment_context_id");
        
        assertEquals(expectedResponse, actualResponse);
        return actualResponse;
    }

    private PaymentContextsRequest createMockPaymentContextsRequest() {
        return mock(PaymentContextsRequest.class);
    }

}
