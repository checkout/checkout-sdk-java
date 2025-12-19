package com.checkout.payments.hosted;

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
class HostedPaymentsClientImplTest {

    private HostedPaymentsClient client;

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
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new HostedPaymentsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldCreateHostedPaymentsPageSession() throws ExecutionException, InterruptedException {
        final HostedPaymentRequest request = createMockHostedPaymentRequest();
        final HostedPaymentResponse expectedResponse = createMockHostedPaymentResponse();

        when(apiClient.postAsync(eq("hosted-payments"), eq(authorization), eq(HostedPaymentResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<HostedPaymentResponse> future = client.createHostedPaymentsPageSession(request);
        final HostedPaymentResponse actualResponse = future.get();
        
        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetHostedPaymentsPageDetails() throws ExecutionException, InterruptedException {
        final HostedPaymentDetailsResponse expectedResponse = createMockHostedPaymentDetailsResponse();

        when(apiClient.getAsync(
                "hosted-payments/hosted_payment_id",
                authorization,
                HostedPaymentDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<HostedPaymentDetailsResponse> future = client.getHostedPaymentsPageDetails("hosted_payment_id");
        final HostedPaymentDetailsResponse actualResponse = future.get();
        
        validateResponse(expectedResponse, actualResponse);
    }

    // Synchronous methods
    @Test
    void shouldCreateHostedPaymentsPageSessionSync() {
        final HostedPaymentRequest request = createMockHostedPaymentRequest();
        final HostedPaymentResponse expectedResponse = createMockHostedPaymentResponse();

        when(apiClient.post(eq("hosted-payments"), eq(authorization), eq(HostedPaymentResponse.class),
                eq(request), isNull()))
                .thenReturn(expectedResponse);

        final HostedPaymentResponse actualResponse = client.createHostedPaymentsPageSessionSync(request);
        
        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetHostedPaymentsPageDetailsSync() {
        final HostedPaymentDetailsResponse expectedResponse = createMockHostedPaymentDetailsResponse();

        when(apiClient.get(
                "hosted-payments/hosted_payment_id",
                authorization,
                HostedPaymentDetailsResponse.class))
                .thenReturn(expectedResponse);

        final HostedPaymentDetailsResponse actualResponse = client.getHostedPaymentsPageDetailsSync("hosted_payment_id");
        
        validateResponse(expectedResponse, actualResponse);
    }

    // Common methods
    private HostedPaymentRequest createMockHostedPaymentRequest() {
        return mock(HostedPaymentRequest.class);
    }

    private HostedPaymentResponse createMockHostedPaymentResponse() {
        return mock(HostedPaymentResponse.class);
    }

    private HostedPaymentDetailsResponse createMockHostedPaymentDetailsResponse() {
        return mock(HostedPaymentDetailsResponse.class);
    }

    private <T> void validateResponse(T expectedResponse, T actualResponse) {
        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse);
    }
}