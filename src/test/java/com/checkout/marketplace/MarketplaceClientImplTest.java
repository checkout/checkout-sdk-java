package com.checkout.marketplace;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.Currency;
import com.checkout.common.IdResponse;
import com.checkout.marketplace.balances.BalancesQuery;
import com.checkout.marketplace.balances.BalancesResponse;
import com.checkout.marketplace.payout.schedule.request.UpdateScheduleRequest;
import com.checkout.marketplace.payout.schedule.response.GetScheduleResponse;
import com.checkout.marketplace.payout.schedule.response.VoidResponse;
import com.checkout.marketplace.transfers.CreateTransferRequest;
import com.checkout.marketplace.transfers.CreateTransferResponse;
import com.checkout.marketplace.transfers.TransferDetailsResponse;
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
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarketplaceClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private MarketplaceClient marketplaceClient;

    @BeforeEach
    void setUp() {
        lenient().when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        lenient().when(checkoutConfiguration.getSdkCredentials()).thenReturn(sdkCredentials);
        lenient().when(checkoutConfiguration.getHttpClientBuilder()).thenReturn(HttpClientBuilder.create());
        lenient().when(checkoutConfiguration.getExecutor()).thenReturn(Executors.newSingleThreadExecutor());
        this.marketplaceClient = new MarketplaceClientImpl(apiClient, apiClient, apiClient, apiClient,
                checkoutConfiguration);
    }

    @Test
    void shouldCreateEntity() throws ExecutionException, InterruptedException {

        final OnboardEntityResponse response = mock(OnboardEntityResponse.class);

        when(apiClient.postAsync(eq("marketplace/entities"), eq(authorization), eq(OnboardEntityResponse.class), any(OnboardEntityRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<OnboardEntityResponse> future = marketplaceClient.createEntity(OnboardEntityRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetEntity() throws ExecutionException, InterruptedException {

        final OnboardEntityDetailsResponse response = mock(OnboardEntityDetailsResponse.class);

        when(apiClient.getAsync("marketplace/entities/entity_id", authorization, OnboardEntityDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<OnboardEntityDetailsResponse> future = marketplaceClient.getEntity("entity_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldUpdateEntity() throws ExecutionException, InterruptedException {

        final OnboardEntityResponse response = mock(OnboardEntityResponse.class);

        when(apiClient.putAsync(eq("marketplace/entities/entity_id"), eq(authorization), eq(OnboardEntityResponse.class), any(OnboardEntityRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<OnboardEntityResponse> future = marketplaceClient.updateEntity(OnboardEntityRequest.builder().build(), "entity_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldCreatePaymentInstrument() throws ExecutionException, InterruptedException {

        final EmptyResponse response = mock(EmptyResponse.class);

        when(apiClient.postAsync(eq("marketplace/entities/entity_id/instruments"), eq(authorization), eq(EmptyResponse.class), any(MarketplacePaymentInstrument.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = marketplaceClient.createPaymentInstrument(MarketplacePaymentInstrument.builder().build(), "entity_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldSubmitFile() throws ExecutionException, InterruptedException {

        final IdResponse response = mock(IdResponse.class);

        when(apiClient.submitFileAsync(eq("files"), eq(authorization), any(MarketplaceFileRequest.class), eq(IdResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdResponse> future = marketplaceClient.submitFile(MarketplaceFileRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldInitiateTransferOfFunds() throws ExecutionException, InterruptedException {

        final CreateTransferResponse response = mock(CreateTransferResponse.class);

        when(apiClient.postAsync(eq("transfers"), eq(authorization), eq(CreateTransferResponse.class), any(CreateTransferRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CreateTransferResponse> future = marketplaceClient.initiateTransferOfFunds(CreateTransferRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRetrieveEntityBalances() throws ExecutionException, InterruptedException {

        final BalancesResponse balancesResponse = mock(BalancesResponse.class);
        when(apiClient.queryAsync(eq("balances/entity_id"), any(SdkAuthorization.class), any(BalancesQuery.class), eq(BalancesResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(balancesResponse));

        final CompletableFuture<BalancesResponse> future = marketplaceClient.retrieveEntityBalances("entity_id", BalancesQuery.builder().build());

        assertNotNull(future.get());
        assertEquals(balancesResponse, future.get());

    }

    @Test
    void shouldUpdatePayoutSchedule() throws ExecutionException, InterruptedException {
        final UpdateScheduleRequest request = mock(UpdateScheduleRequest.class);
        final VoidResponse response = mock(VoidResponse.class);

        when(apiClient.putAsync(eq("marketplace/entities/entity_id/payout-schedules"), eq(authorization), eq(VoidResponse.class), anyMap()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<VoidResponse> future = marketplaceClient.updatePayoutSchedule("entity_id", Currency.USD, request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldRetrievePayoutSchedule() throws ExecutionException, InterruptedException {

        final GetScheduleResponse response = mock(GetScheduleResponse.class);

        when(apiClient.getAsync("marketplace/entities/entity_id/payout-schedules", authorization, GetScheduleResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<GetScheduleResponse> future = marketplaceClient.retrievePayoutSchedule("entity_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRetrieveATransfer() throws ExecutionException, InterruptedException {

        final TransferDetailsResponse response = mock(TransferDetailsResponse.class);

        when(apiClient.getAsync("transfers/transfer_id", authorization, TransferDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<TransferDetailsResponse> future = marketplaceClient.retrieveATransfer("transfer_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

}
