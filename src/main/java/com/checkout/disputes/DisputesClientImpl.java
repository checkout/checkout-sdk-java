package com.checkout.disputes;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.FileDetailsResponse;
import com.checkout.common.FileRequest;
import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class DisputesClientImpl extends AbstractClient implements DisputesClient {

    private static final String DISPUTES_PATH = "disputes";
    private static final String FILES_PATH = "files";
    private static final String ACCEPT_PATH = "accept";
    private static final String EVIDENCE_PATH = "evidence";

    public DisputesClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration, final SdkAuthorizationType sdkAuthorizationType) {
        super(apiClient, configuration, sdkAuthorizationType);
    }

    @Override
    public CompletableFuture<DisputesQueryResponse> query(final DisputesQueryFilter queryFilter) {
        validateParams("queryFilter", queryFilter);
        return apiClient.queryAsync(DISPUTES_PATH, sdkAuthorization(), queryFilter, DisputesQueryResponse.class);
    }

    @Override
    public CompletableFuture<DisputeDetailsResponse> getDisputeDetails(final String disputeId) {
        validateParams("disputeId", disputeId);
        return apiClient.getAsync(buildPath(DISPUTES_PATH, disputeId), sdkAuthorization(), DisputeDetailsResponse.class);
    }

    @Override
    public CompletableFuture<EmptyResponse> accept(final String disputeId) {
        validateParams("disputeId", disputeId);
        return apiClient.postAsync(buildPath(DISPUTES_PATH, disputeId, ACCEPT_PATH), sdkAuthorization(), EmptyResponse.class, null, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> putEvidence(final String disputeId, final DisputeEvidenceRequest disputeEvidence) {
        validateParams("disputeId", disputeId, "disputeEvidence", disputeEvidence);
        return apiClient.putAsync(buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH), sdkAuthorization(), EmptyResponse.class, disputeEvidence);
    }

    @Override
    public CompletableFuture<DisputeEvidenceResponse> getEvidence(final String disputeId) {
        validateParams("disputeId", disputeId);
        return apiClient.getAsync(buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH), sdkAuthorization(), DisputeEvidenceResponse.class);
    }

    @Override
    public CompletableFuture<EmptyResponse> submitEvidence(final String disputeId) {
        validateParams("disputeId", disputeId);
        return apiClient.postAsync(buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH), sdkAuthorization(), EmptyResponse.class, null, null);
    }

    @Override
    public CompletableFuture<IdResponse> uploadFile(final FileRequest fileRequest) {
        validateParams("fileRequest", fileRequest);
        return apiClient.submitFileAsync(FILES_PATH, sdkAuthorization(), fileRequest, IdResponse.class);
    }

    @Override
    public CompletableFuture<FileDetailsResponse> getFileDetails(final String fileId) {
        validateParams("fileId", fileId);
        return apiClient.getAsync(buildPath(FILES_PATH, fileId), sdkAuthorization(), FileDetailsResponse.class);
    }

}
