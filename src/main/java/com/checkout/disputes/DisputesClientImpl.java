package com.checkout.disputes;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.FileDetailsResponse;
import com.checkout.common.FileRequest;
import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

public class DisputesClientImpl extends AbstractClient implements DisputesClient {

    private static final String DISPUTES = "/disputes";
    private static final String FILES = "/files";
    private static final String ACCEPT = "accept";
    private static final String EVIDENCE = "evidence";

    public DisputesClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<DisputesQueryResponse> query(final DisputesQueryFilter queryFilter) {
        return apiClient.queryAsync(DISPUTES, sdkAuthorization(), queryFilter, DisputesQueryResponse.class);
    }

    @Override
    public CompletableFuture<DisputeDetailsResponse> getDisputeDetails(final String id) {
        return apiClient.getAsync(buildPath(DISPUTES, id), sdkAuthorization(), DisputeDetailsResponse.class);
    }

    @Override
    public CompletableFuture<Void> accept(final String id) {
        return apiClient.postAsync(buildPath(DISPUTES, id, ACCEPT), sdkAuthorization(), Void.class, null, null);
    }

    @Override
    public CompletableFuture<Void> putEvidence(final String id, final DisputeEvidenceRequest disputeEvidence) {
        return apiClient.putAsync(buildPath(DISPUTES, id, EVIDENCE), sdkAuthorization(), Void.class, disputeEvidence);
    }

    @Override
    public CompletableFuture<DisputeEvidenceResponse> getEvidence(final String id) {
        return apiClient.getAsync(buildPath(DISPUTES, id, EVIDENCE), sdkAuthorization(), DisputeEvidenceResponse.class);
    }

    @Override
    public CompletableFuture<Void> submitEvidence(final String id) {
        return apiClient.postAsync(buildPath(DISPUTES, id, EVIDENCE), sdkAuthorization(), Void.class, null, null);
    }

    @Override
    public CompletableFuture<IdResponse> uploadFile(final FileRequest request) {
        return apiClient.submitFileAsync(FILES, sdkAuthorization(), request, IdResponse.class);
    }

    @Override
    public CompletableFuture<FileDetailsResponse> getFileDetails(final String id) {
        return apiClient.getAsync(buildPath(FILES, id), sdkAuthorization(), FileDetailsResponse.class);
    }

}
