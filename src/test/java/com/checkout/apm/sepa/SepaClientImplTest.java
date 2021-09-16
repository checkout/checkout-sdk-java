package com.checkout.apm.sepa;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
import com.checkout.common.Resource;
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
class SepaClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private MandateResponse mandateResponse;

    @Mock
    private Resource resourceResponse;

    private SepaClient sepaClient;

    @BeforeEach
    void setUp() {
        this.sepaClient = new SepaClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldGetMandate() throws ExecutionException, InterruptedException {

        when(apiClient.getAsync(eq("sepa/mandates/mandate_id"), any(SecretKeyCredentials.class), eq(MandateResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(mandateResponse));

        final CompletableFuture<MandateResponse> future = sepaClient.getMandate("mandate_id");

        assertNotNull(future.get());
        assertEquals(mandateResponse, future.get());

    }

    @Test
    void shouldCancelMandate() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("sepa/mandates/mandate_id/cancel"), any(SecretKeyCredentials.class), eq(Resource.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(resourceResponse));

        final CompletableFuture<Resource> future = sepaClient.cancelMandate("mandate_id");

        assertNotNull(future.get());
        assertEquals(resourceResponse, future.get());

    }

    @Test
    void shouldGetMandateViaPPRO() throws ExecutionException, InterruptedException {

        when(apiClient.getAsync(eq("/ppro/sepa/mandates/mandate_id"), any(SecretKeyCredentials.class), eq(MandateResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(mandateResponse));

        final CompletableFuture<MandateResponse> future = sepaClient.getMandateViaPPRO("mandate_id");

        assertNotNull(future.get());
        assertEquals(mandateResponse, future.get());

    }

    @Test
    void shouldCancelMandateViaPPRO() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("/ppro/sepa/mandates/mandate_id/cancel"), any(SecretKeyCredentials.class), eq(Resource.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(resourceResponse));

        final CompletableFuture<Resource> future = sepaClient.cancelMandateViaPPRO("mandate_id");

        assertNotNull(future.get());
        assertEquals(resourceResponse, future.get());

    }

}