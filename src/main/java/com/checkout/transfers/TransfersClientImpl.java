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
        validateCreateTransferRequest(createTransferRequest);
        return apiClient.postAsync(TRANSFERS_PATH, sdkAuthorization(), CreateTransferResponse.class, createTransferRequest, null);
    }

    @Override
    public CompletableFuture<CreateTransferResponse> initiateTransferOfFunds(final CreateTransferRequest createTransferRequest, final String idempotencyKey) {
        validateCreateTransferRequest(createTransferRequest);
        return apiClient.postAsync(TRANSFERS_PATH, sdkAuthorization(), CreateTransferResponse.class, createTransferRequest, idempotencyKey);
    }

    @Override
    public CompletableFuture<TransferDetailsResponse> retrieveATransfer(final String transferId) {
        validateTransferId(transferId);
        return apiClient.getAsync(buildPath(TRANSFERS_PATH, transferId), sdkAuthorization(), TransferDetailsResponse.class);
    }

    // Synchronous methods
    @Override
    public CreateTransferResponse initiateTransferOfFundsSync(final CreateTransferRequest createTransferRequest) {
        validateCreateTransferRequest(createTransferRequest);
        return apiClient.post(TRANSFERS_PATH, sdkAuthorization(), CreateTransferResponse.class, createTransferRequest, null);
    }

    @Override
    public CreateTransferResponse initiateTransferOfFundsSync(final CreateTransferRequest createTransferRequest, final String idempotencyKey) {
        validateCreateTransferRequest(createTransferRequest);
        return apiClient.post(TRANSFERS_PATH, sdkAuthorization(), CreateTransferResponse.class, createTransferRequest, idempotencyKey);
    }

    @Override
    public TransferDetailsResponse retrieveATransferSync(final String transferId) {
        validateTransferId(transferId);
        return apiClient.get(buildPath(TRANSFERS_PATH, transferId), sdkAuthorization(), TransferDetailsResponse.class);
    }

    // Common methods
    protected void validateCreateTransferRequest(final CreateTransferRequest createTransferRequest) {
        validateParams("createTransferRequest", createTransferRequest);
    }

    protected void validateTransferId(final String transferId) {
        validateParams("transferId", transferId);
    }
}
