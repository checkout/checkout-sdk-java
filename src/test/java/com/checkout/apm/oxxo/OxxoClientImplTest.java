package com.checkout.apm.oxxo;

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
class OxxoClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private Void voidResponse;

    private OxxoClient oxxoClient;

    @BeforeEach
    void setUp() {
        this.oxxoClient = new OxxoClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldSucceedPayment() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("/apms/oxxo/payments/payment_id/succeed"), any(SecretKeyCredentials.class), eq(Void.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(voidResponse));

        final CompletableFuture<Void> future = oxxoClient.succeed("payment_id");

        assertNotNull(future.get());
        assertEquals(voidResponse, future.get());

    }

    @Test
    void shouldExpirePayment() throws ExecutionException, InterruptedException {

        when(apiClient.postAsync(eq("/apms/oxxo/payments/payment_id/expire"), any(SecretKeyCredentials.class), eq(Void.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(voidResponse));

        final CompletableFuture<Void> future = oxxoClient.expire("payment_id");

        assertNotNull(future.get());
        assertEquals(voidResponse, future.get());

    }

}