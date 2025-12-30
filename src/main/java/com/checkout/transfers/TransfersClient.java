package com.checkout.transfers;

import java.util.concurrent.CompletableFuture;

public interface TransfersClient {

    /**
     * @deprecated Transfers of funds always requires idempotency key
     */
    @Deprecated
    CompletableFuture<CreateTransferResponse> initiateTransferOfFunds(CreateTransferRequest createTransferRequest);

    CompletableFuture<CreateTransferResponse> initiateTransferOfFunds(CreateTransferRequest createTransferRequest, String idempotencyKey);

    CompletableFuture<TransferDetailsResponse> retrieveATransfer(String transferId);

    // Synchronous methods
    /**
     * @deprecated Transfers of funds always requires idempotency key
     */
    @Deprecated
    CreateTransferResponse initiateTransferOfFundsSync(CreateTransferRequest createTransferRequest);

    CreateTransferResponse initiateTransferOfFundsSync(CreateTransferRequest createTransferRequest, String idempotencyKey);

    TransferDetailsResponse retrieveATransferSync(String transferId);

}
