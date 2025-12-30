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
        final CreateTransferRequest request = createTransferRequest();
        final CreateTransferResponse expectedResponse = mock(CreateTransferResponse.class);

        when(apiClient.postAsync(eq("transfers"), eq(authorization), eq(CreateTransferResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<CreateTransferResponse> future = transfersClient.initiateTransferOfFunds(request);

        final CreateTransferResponse actualResponse = future.get();

        validateCreateTransferResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldInitiateTransferOfFundsWithIdempotencyKey() throws ExecutionException, InterruptedException {
        final CreateTransferRequest request = createTransferRequest();
        final String idempotencyKey = "idempotency_key";
        final CreateTransferResponse expectedResponse = mock(CreateTransferResponse.class);

        when(apiClient.postAsync(eq("transfers"), eq(authorization), eq(CreateTransferResponse.class), eq(request), eq(idempotencyKey)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<CreateTransferResponse> future =
                transfersClient.initiateTransferOfFunds(request, idempotencyKey);

        final CreateTransferResponse actualResponse = future.get();

        validateCreateTransferResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRetrieveATransfer() throws ExecutionException, InterruptedException {
        final String transferId = "transfer_id";
        final TransferDetailsResponse expectedResponse = mock(TransferDetailsResponse.class);

        when(apiClient.getAsync(eq("transfers/" + transferId), eq(authorization), eq(TransferDetailsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<TransferDetailsResponse> future = transfersClient.retrieveATransfer(transferId);

        final TransferDetailsResponse actualResponse = future.get();

        validateTransferDetailsResponse(expectedResponse, actualResponse);
    }

    // Synchronous methods
    @Test
    void shouldInitiateTransferOfFundsSync() {
        final CreateTransferRequest request = createTransferRequest();
        final CreateTransferResponse expectedResponse = mock(CreateTransferResponse.class);

        when(apiClient.post(eq("transfers"), eq(authorization), eq(CreateTransferResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final CreateTransferResponse actualResponse = transfersClient.initiateTransferOfFundsSync(request);

        validateCreateTransferResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldInitiateTransferOfFundsWithIdempotencyKeySync() {
        final CreateTransferRequest request = createTransferRequest();
        final String idempotencyKey = "idempotency_key";
        final CreateTransferResponse expectedResponse = mock(CreateTransferResponse.class);

        when(apiClient.post(eq("transfers"), eq(authorization), eq(CreateTransferResponse.class), eq(request), eq(idempotencyKey)))
                .thenReturn(expectedResponse);

        final CreateTransferResponse actualResponse =
                transfersClient.initiateTransferOfFundsSync(request, idempotencyKey);

        validateCreateTransferResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRetrieveATransferSync() {
        final String transferId = "transfer_id";
        final TransferDetailsResponse expectedResponse = mock(TransferDetailsResponse.class);

        when(apiClient.get(eq("transfers/" + transferId), eq(authorization), eq(TransferDetailsResponse.class)))
                .thenReturn(expectedResponse);

        final TransferDetailsResponse actualResponse = transfersClient.retrieveATransferSync(transferId);

        validateTransferDetailsResponse(expectedResponse, actualResponse);
    }

    // Common methods
    private CreateTransferRequest createTransferRequest() {
        return CreateTransferRequest.builder().build();
    }

    private void validateCreateTransferResponse(final CreateTransferResponse expectedResponse,
                                                final CreateTransferResponse actualResponse) {
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

    private void validateTransferDetailsResponse(final TransferDetailsResponse expectedResponse,
                                                 final TransferDetailsResponse actualResponse) {
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }
}