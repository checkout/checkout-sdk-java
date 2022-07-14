package com.checkout.apm.previous.sepa;

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
class SepaClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private SepaClient sepaClient;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(checkoutConfiguration.getSdkCredentials()).thenReturn(sdkCredentials);
        this.sepaClient = new SepaClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldGetMandate() throws ExecutionException, InterruptedException {

        final MandateResponse response = mock(MandateResponse.class);

        when(apiClient.getAsync("sepa/mandates/mandate_id", authorization, MandateResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<MandateResponse> future = sepaClient.getMandate("mandate_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldCancelMandate() throws ExecutionException, InterruptedException {

        final SepaResource response = mock(SepaResource.class);

        when(apiClient.postAsync(eq("sepa/mandates/mandate_id/cancel"), eq(authorization), eq(SepaResource.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<SepaResource> future = sepaClient.cancelMandate("mandate_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetMandateViaPPRO() throws ExecutionException, InterruptedException {

        final MandateResponse response = mock(MandateResponse.class);

        when(apiClient.getAsync("ppro/sepa/mandates/mandate_id", authorization, MandateResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<MandateResponse> future = sepaClient.getMandateViaPPRO("mandate_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldCancelMandateViaPPRO() throws ExecutionException, InterruptedException {

        final SepaResource response = mock(SepaResource.class);

        when(apiClient.postAsync(eq("ppro/sepa/mandates/mandate_id/cancel"), eq(authorization), eq(SepaResource.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<SepaResource> future = sepaClient.cancelMandateViaPPRO("mandate_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

}