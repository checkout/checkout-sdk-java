package com.checkout.transfers;

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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransfersClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private TransfersClient transfersClient;

    @BeforeEach
    void setUp() {
        lenient().when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        lenient().when(checkoutConfiguration.getSdkCredentials()).thenReturn(sdkCredentials);
        lenient().when(checkoutConfiguration.getHttpClientBuilder()).thenReturn(HttpClientBuilder.create());
        lenient().when(checkoutConfiguration.getExecutor()).thenReturn(Executors.newSingleThreadExecutor());
        this.transfersClient = new TransfersClientImpl(apiClient, checkoutConfiguration);
    }


    @Test
    void shouldInitiateTransferOfFunds() throws ExecutionException, InterruptedException {

        final CreateTransferResponse response = mock(CreateTransferResponse.class);

        when(apiClient.postAsync(eq("transfers"), eq(authorization), eq(CreateTransferResponse.class), any(CreateTransferRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CreateTransferResponse> future = transfersClient.initiateTransferOfFunds(CreateTransferRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRetrieveATransfer() throws ExecutionException, InterruptedException {

        final TransferDetailsResponse response = mock(TransferDetailsResponse.class);

        when(apiClient.getAsync("transfers/transfer_id", authorization, TransferDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<TransferDetailsResponse> future = transfersClient.retrieveATransfer("transfer_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

}