package com.checkout.payments.hosted;

import com.checkout.ApiClient;
import com.checkout.ApiCredentials;
import com.checkout.CheckoutConfiguration;
import com.checkout.TestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HostedPaymentsClientImplTest {

    private static final String REFERENCE = "ORD-1234";

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private HostedPaymentResponse response;

    @Mock
    private CompletableFuture<HostedPaymentResponse> responseAsync;

    @Before
    public void setUp() {
        responseAsync = CompletableFuture.completedFuture(response);
        when(response.getReference()).thenReturn(REFERENCE);
        when(response.getLinks()).thenReturn(new HashMap<>());
    }

    @Test
    public void shouldCreateHostedPayments() throws ExecutionException, InterruptedException {
        final HostedPaymentRequest request = TestHelper.createHostedPaymentRequest(REFERENCE);
        final HostedPaymentsClient client = new HostedPaymentsClientImpl(apiClient, configuration);
        Mockito.doReturn(responseAsync)
                .when(apiClient).postAsync(eq(HostedPaymentsClientImpl.HOSTED_PAYMENTS), any(ApiCredentials.class),
                eq(HostedPaymentResponse.class), any(HostedPaymentRequest.class), any());
        final HostedPaymentResponse hostedPaymentResponse = client.createAsync(request).get();
        assertNotNull(hostedPaymentResponse);
        assertEquals(request.getReference(), hostedPaymentResponse.getReference());
        assertNotNull(hostedPaymentResponse.getLinks());
    }

}