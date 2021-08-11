package com.checkout.disputes;

import com.checkout.common.FileDetailsResponse;
import com.checkout.common.FileRequest;
import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

public interface DisputesClient {

    CompletableFuture<DisputesQueryResponse> query(DisputesQueryFilter queryFilter);

    CompletableFuture<DisputeDetailsResponse> getDisputeDetails(String id);

    CompletableFuture<Void> accept(String id);

    CompletableFuture<Void> putEvidence(String id, DisputeEvidenceRequest disputeEvidence);

    CompletableFuture<DisputeEvidenceResponse> getEvidence(String id);

    CompletableFuture<Void> submitEvidence(String id);

    CompletableFuture<IdResponse> uploadFile(FileRequest request);

    CompletableFuture<FileDetailsResponse> getFileDetails(String id);

}
