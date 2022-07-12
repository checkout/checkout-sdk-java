package com.checkout.transfers;

import java.util.concurrent.CompletableFuture;

public interface TransfersClient {

    CompletableFuture<CreateTransferResponse> initiateTransferOfFunds(CreateTransferRequest createTransferRequest);

    CompletableFuture<CreateTransferResponse> initiateTransferOfFunds(CreateTransferRequest createTransferRequest, String idempotencyKey);

    CompletableFuture<TransferDetailsResponse> retrieveATransfer(String transferId);

}
