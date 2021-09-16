package com.checkout.apm.pagofacil;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
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
class PagoFacilClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private Void voidResponse;

    private PagoFacilClient pagoFacilClient;

    @BeforeEach
    void setUp() {
        this.pagoFacilClient = new PagoFacilImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldSucceedPayment() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("/apms/pagofacil/payments/payment_id/succeed"), any(SecretKeyCredentials.class), eq(Void.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(voidResponse));

        final CompletableFuture<Void> future = pagoFacilClient.succeed("payment_id");

        assertNotNull(future.get());
        assertEquals(voidResponse, future.get());

    }

    @Test
    void shouldExpirePayment() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("/apms/pagofacil/payments/payment_id/expire"), any(SecretKeyCredentials.class), eq(Void.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(voidResponse));

        final CompletableFuture<Void> future = pagoFacilClient.expire("payment_id");

        assertNotNull(future.get());
        assertEquals(voidResponse, future.get());

    }

}