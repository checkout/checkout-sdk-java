package com.checkout.apm.giropay;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
import com.checkout.apm.giropay.BanksResponse;
import com.checkout.apm.giropay.GiropayClient;
import com.checkout.apm.giropay.GiropayClientImpl;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiropayClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private BanksResponse banksResponse;

    private GiropayClient giropayClient;

    @BeforeEach
    void setUp() {
        this.giropayClient = new GiropayClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldGetBanks() throws ExecutionException, InterruptedException {

        when(apiClient.getAsync(eq("/giropay/banks"), any(SecretKeyCredentials.class), eq(BanksResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(banksResponse));

        final CompletableFuture<BanksResponse> future = giropayClient.getBanks();

        assertNotNull(future.get());
        assertEquals(banksResponse, future.get());

    }

    @Test
    void shouldGetEpsBanks() throws ExecutionException, InterruptedException {

        when(apiClient.getAsync(eq("/giropay/eps/banks"), any(SecretKeyCredentials.class), eq(BanksResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(banksResponse));

        final CompletableFuture<BanksResponse> future = giropayClient.getEpsBanks();

        assertNotNull(future.get());
        assertEquals(banksResponse, future.get());

    }

}
