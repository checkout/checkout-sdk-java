package com.checkout.apm.giropay;

import com.checkout.ApiClient;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.CheckoutConfiguration;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiropayClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @Mock
    private BanksResponse banksResponse;

    private GiropayClient giropayClient;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        this.giropayClient = new GiropayClientImpl(apiClient, configuration);
    }

    @Test
    void shouldGetBanks() throws ExecutionException, InterruptedException {

        when(apiClient.getAsync(eq("/giropay/banks"), eq(authorization), eq(BanksResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(banksResponse));

        final CompletableFuture<BanksResponse> future = giropayClient.getBanks();

        assertNotNull(future.get());
        assertEquals(banksResponse, future.get());

    }

    @Test
    void shouldGetEpsBanks() throws ExecutionException, InterruptedException {

        when(apiClient.getAsync(eq("/giropay/eps/banks"), eq(authorization), eq(BanksResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(banksResponse));

        final CompletableFuture<BanksResponse> future = giropayClient.getEpsBanks();

        assertNotNull(future.get());
        assertEquals(banksResponse, future.get());

    }

}
