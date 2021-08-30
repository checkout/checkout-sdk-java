package com.checkout.disputes.beta;

import com.checkout.common.FileDetailsResponse;
import com.checkout.common.FileRequest;
import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

public interface DisputesClient {

    CompletableFuture<DisputesQueryResponse> query(DisputesQueryFilter queryFilter);

    CompletableFuture<DisputeDetailsResponse> getDisputeDetails(String disputeId);

    CompletableFuture<Void> accept(String disputeId);

    CompletableFuture<Void> putEvidence(String disputeId, DisputeEvidenceRequest disputeEvidenceRequest);

    CompletableFuture<DisputeEvidenceResponse> getEvidence(String disputeId);

    CompletableFuture<Void> submitEvidence(String disputeId);

    CompletableFuture<IdResponse> uploadFile(FileRequest request);

    CompletableFuture<FileDetailsResponse> getFileDetails(String id);

}
