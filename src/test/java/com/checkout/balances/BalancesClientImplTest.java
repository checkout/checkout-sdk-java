package com.checkout.balances;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalancesClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private BalancesClient balancesClient;

    @BeforeEach
    void setUp() {
        lenient().when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        lenient().when(checkoutConfiguration.getSdkCredentials()).thenReturn(sdkCredentials);
        lenient().when(checkoutConfiguration.getHttpClientBuilder()).thenReturn(HttpClientBuilder.create());
        lenient().when(checkoutConfiguration.getExecutor()).thenReturn(Executors.newSingleThreadExecutor());
        this.balancesClient = new BalancesClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldRetrieveEntityBalances() throws ExecutionException, InterruptedException {

        final BalancesResponse balancesResponse = mock(BalancesResponse.class);
        when(apiClient.queryAsync(eq("balances/entity_id"), any(SdkAuthorization.class), any(BalancesQuery.class), eq(BalancesResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(balancesResponse));

        final CompletableFuture<BalancesResponse> future = balancesClient.retrieveEntityBalances("entity_id", BalancesQuery.builder().build());

        assertNotNull(future.get());
        assertEquals(balancesResponse, future.get());

    }

}