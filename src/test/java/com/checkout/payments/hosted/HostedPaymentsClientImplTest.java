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
import static org.mockito.ArgumentMatchers.any;
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
    void shouldCreateHostedPayments() throws ExecutionException, InterruptedException {

        final HostedPaymentRequest request = mock(HostedPaymentRequest.class);
        final HostedPaymentResponse response = mock(HostedPaymentResponse.class);

        when(apiClient.postAsync(eq("hosted-payments"), eq(authorization), eq(HostedPaymentResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<HostedPaymentResponse> future = client.createAsync(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetHostedPayment() throws ExecutionException, InterruptedException {

        final HostedPaymentsClient client = new HostedPaymentsClientImpl(apiClient, configuration);
        when(apiClient.getAsync(eq("hosted-payments/hosted_id"), any(SdkAuthorization.class), eq(HostedPaymentDetailsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(new HostedPaymentDetailsResponse()));

        final HostedPaymentDetailsResponse response = client.get("hosted_id").get();
        assertNotNull(response);
    }

}
