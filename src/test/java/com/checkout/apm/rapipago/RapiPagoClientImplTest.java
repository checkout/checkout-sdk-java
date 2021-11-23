package com.checkout.apm.rapipago;

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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RapiPagoClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @Mock
    private Void voidResponse;

    private RapiPagoClient rapiPagoClient;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(checkoutConfiguration.getSdkCredentials()).thenReturn(sdkCredentials);
        this.rapiPagoClient = new RapiPagoClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldSucceedPayment() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("/apms/rapipago/payments/payment_id/succeed"), eq(authorization), eq(Void.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(voidResponse));

        final CompletableFuture<Void> future = rapiPagoClient.succeed("payment_id");

        assertNotNull(future.get());
        assertEquals(voidResponse, future.get());

    }

    @Test
    void shouldExpirePayment() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("/apms/rapipago/payments/payment_id/expire"), eq(authorization), eq(Void.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(voidResponse));

        final CompletableFuture<Void> future = rapiPagoClient.expire("payment_id");

        assertNotNull(future.get());
        assertEquals(voidResponse, future.get());

    }

}