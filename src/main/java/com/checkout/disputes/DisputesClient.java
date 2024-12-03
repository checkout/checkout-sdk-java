package com.checkout.disputes;

import com.checkout.EmptyResponse;
import com.checkout.common.FileDetailsResponse;
import com.checkout.common.FileRequest;
import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

public interface DisputesClient {

    CompletableFuture<DisputesQueryResponse> query(DisputesQueryFilter queryFilter);

    CompletableFuture<DisputeDetailsResponse> getDisputeDetails(String disputeId);

    CompletableFuture<EmptyResponse> accept(String disputeId);

    CompletableFuture<EmptyResponse> putEvidence(String disputeId, DisputeEvidenceRequest disputeEvidence);

    CompletableFuture<DisputeEvidenceResponse> getEvidence(String disputeId);

    CompletableFuture<EmptyResponse> submitEvidence(String disputeId);

    CompletableFuture<EmptyResponse> submitArbitrationEvidence(String disputeId);

    CompletableFuture<DisputeCompiledSubmittedEvidenceResponse> getCompiledSubmittedEvidence(String disputeId);

    CompletableFuture<DisputeCompiledSubmittedEvidenceResponse> getCompiledSubmittedArbitrationEvidence(String disputeId);

    CompletableFuture<SchemeFileResponse> getDisputeSchemeFiles(String disputeId);

    CompletableFuture<IdResponse> uploadFile(FileRequest fileRequest);

    CompletableFuture<FileDetailsResponse> getFileDetails(String fileId);

}
