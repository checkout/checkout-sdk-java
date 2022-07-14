package com.checkout.payments.previous.links;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.payments.links.PaymentLinkDetailsResponse;
import com.checkout.payments.links.PaymentLinkRequest;
import com.checkout.payments.links.PaymentLinkResponse;
import com.checkout.payments.links.PaymentLinksClient;
import com.checkout.payments.links.PaymentLinksClientImpl;
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
class PaymentLinksClientImplTest {

    private PaymentLinksClient client;

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
        client = new PaymentLinksClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRetrievePaymentsLink() throws ExecutionException, InterruptedException {

        final PaymentLinkDetailsResponse response = mock(PaymentLinkDetailsResponse.class);

        when(apiClient.getAsync("payment-links/reference", authorization, PaymentLinkDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentLinkDetailsResponse> future = client.getPaymentLink("reference");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldCreatePaymentsLink() throws ExecutionException, InterruptedException {

        final PaymentLinkRequest request = mock(PaymentLinkRequest.class);
        final PaymentLinkResponse response = mock(PaymentLinkResponse.class);

        when(apiClient.postAsync(eq("payment-links"), eq(authorization), eq(PaymentLinkResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentLinkResponse> future = client.createPaymentLink(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

}
