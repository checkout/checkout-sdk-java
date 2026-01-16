package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.handlepaymentsandpayouts.setups.requests.PaymentSetupsRequest;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsConfirmResponse;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsResponse;
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
class PaymentSetupsClientImplTest {

    private PaymentSetupsClient client;

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
        client = new PaymentSetupsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldCreatePaymentSetup() throws ExecutionException, InterruptedException {
        final PaymentSetupsRequest request = mock(PaymentSetupsRequest.class);
        final PaymentSetupsResponse response = mock(PaymentSetupsResponse.class);

        when(apiClient.postAsync(eq("payments/setups"), eq(authorization), eq(PaymentSetupsResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentSetupsResponse> future = client.createPaymentSetup(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldUpdatePaymentSetup() throws ExecutionException, InterruptedException {
        final String paymentSetupId = "ps_test_123456789";
        final PaymentSetupsRequest request = mock(PaymentSetupsRequest.class);
        final PaymentSetupsResponse response = mock(PaymentSetupsResponse.class);

        when(apiClient.putAsync(eq("payments/setups/" + paymentSetupId), eq(authorization), eq(PaymentSetupsResponse.class), eq(request)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentSetupsResponse> future = client.updatePaymentSetup(paymentSetupId, request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetPaymentSetup() throws ExecutionException, InterruptedException {
        final String paymentSetupId = "ps_test_123456789";
        final PaymentSetupsResponse response = mock(PaymentSetupsResponse.class);

        when(apiClient.getAsync(eq("payments/setups/" + paymentSetupId), eq(authorization), eq(PaymentSetupsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentSetupsResponse> future = client.getPaymentSetup(paymentSetupId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldConfirmPaymentSetup() throws ExecutionException, InterruptedException {
        final String paymentSetupId = "ps_test_123456789";
        final String paymentMethodOptionId = "pmo_test_987654321";
        final PaymentSetupsConfirmResponse response = mock(PaymentSetupsConfirmResponse.class);

        when(apiClient.postAsync(eq("payments/setups/" + paymentSetupId + "/confirm/" + paymentMethodOptionId), 
                eq(authorization), eq(PaymentSetupsConfirmResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentSetupsConfirmResponse> future = 
                client.confirmPaymentSetup(paymentSetupId, paymentMethodOptionId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }
}