package com.checkout.transfers;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class TransfersClientImpl extends AbstractClient implements TransfersClient {

    private static final String TRANSFERS_PATH = "transfers";

    public TransfersClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<CreateTransferResponse> initiateTransferOfFunds(final CreateTransferRequest createTransferRequest) {
        return requestInitiateTransferOfFunds(createTransferRequest, null);
    }

    @Override
    public CompletableFuture<CreateTransferResponse> initiateTransferOfFunds(final CreateTransferRequest createTransferRequest, final String idempotencyKey) {
        return requestInitiateTransferOfFunds(createTransferRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<TransferDetailsResponse> retrieveATransfer(final String transferId) {
        validateParams("transferId", transferId);
        return apiClient.getAsync(buildPath(TRANSFERS_PATH, transferId), sdkAuthorization(), TransferDetailsResponse.class);
    }


    private CompletableFuture<CreateTransferResponse> requestInitiateTransferOfFunds(final CreateTransferRequest createTransferRequest, final String idempotencyKey) {
        validateParams("createTransferRequest", createTransferRequest);
        return apiClient.postAsync(TRANSFERS_PATH, sdkAuthorization(), CreateTransferResponse.class, createTransferRequest, idempotencyKey);
    }
}
