package com.checkout.apm.pagofacil;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagoFacilClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @Mock
    private EmptyResponse emptyResponse;

    private PagoFacilClient pagoFacilClient;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        this.pagoFacilClient = new PagoFacilImpl(apiClient, configuration);
    }

    @Test
    void shouldSucceedPayment() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("apms/pagofacil/payments/payment_id/succeed"), eq(authorization), eq(EmptyResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(emptyResponse));

        final CompletableFuture<EmptyResponse> future = pagoFacilClient.succeed("payment_id");

        assertNotNull(future.get());
        assertEquals(emptyResponse, future.get());

    }

    @Test
    void shouldExpirePayment() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("apms/pagofacil/payments/payment_id/expire"), eq(authorization), eq(EmptyResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(emptyResponse));

        final CompletableFuture<EmptyResponse> future = pagoFacilClient.expire("payment_id");

        assertNotNull(future.get());
        assertEquals(emptyResponse, future.get());

    }

}