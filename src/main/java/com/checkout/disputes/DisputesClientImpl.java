package com.checkout.disputes;

import static com.checkout.common.CheckoutUtils.validateParams;

import java.util.concurrent.CompletableFuture;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.FileDetailsResponse;
import com.checkout.common.FileRequest;
import com.checkout.common.IdResponse;

public class DisputesClientImpl extends AbstractClient implements DisputesClient {

    private static final String DISPUTES_PATH = "disputes";
    private static final String FILES_PATH = "files";
    private static final String ACCEPT_PATH = "accept";
    private static final String EVIDENCE_PATH = "evidence";
    private static final String SUBMITTED_PATH = "submitted";
    private static final String ARBITRATION_PATH = "arbitration";
    private static final String SCHEME_FILES_PATH = "schemefiles";

    public DisputesClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration, final SdkAuthorizationType sdkAuthorizationType) {
        super(apiClient, configuration, sdkAuthorizationType);
    }

    @Override
    public CompletableFuture<DisputesQueryResponse> query(final DisputesQueryFilter queryFilter) {
        validateQueryFilter(queryFilter);
        return apiClient.queryAsync(
                DISPUTES_PATH,
                sdkAuthorization(),
                queryFilter,
                DisputesQueryResponse.class);
    }

    @Override
    public CompletableFuture<DisputeDetailsResponse> getDisputeDetails(final String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.getAsync(
                buildPath(DISPUTES_PATH, disputeId),
                sdkAuthorization(),
                DisputeDetailsResponse.class);
    }

    @Override
    public CompletableFuture<EmptyResponse> accept(final String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.postAsync(
                buildPath(DISPUTES_PATH, disputeId, ACCEPT_PATH),
                sdkAuthorization(),
                EmptyResponse.class,
                null,
                null);
    }

    @Override
    public CompletableFuture<EmptyResponse> putEvidence(final String disputeId, final DisputeEvidenceRequest disputeEvidence) {
        validateDisputeIdAndEvidence(disputeId, disputeEvidence);
        return apiClient.putAsync(
                buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH),
                sdkAuthorization(),
                EmptyResponse.class,
                disputeEvidence);
    }

    @Override
    public CompletableFuture<DisputeEvidenceResponse> getEvidence(final String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.getAsync(
                buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH),
                sdkAuthorization(),
                DisputeEvidenceResponse.class);
    }

    @Override
    public CompletableFuture<EmptyResponse> submitEvidence(final String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.postAsync(
                buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH),
                sdkAuthorization(),
                EmptyResponse.class,
                null,
                null);
    }

    @Override
    public CompletableFuture<EmptyResponse> submitArbitrationEvidence(String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.postAsync(
                buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH, ARBITRATION_PATH),
                sdkAuthorization(),
                EmptyResponse.class,
                null,
                null);
    }

    @Override
    public CompletableFuture<DisputeCompiledSubmittedEvidenceResponse> getCompiledSubmittedEvidence(String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.getAsync(
                buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH, SUBMITTED_PATH),
                sdkAuthorization(),
                DisputeCompiledSubmittedEvidenceResponse.class
        );
    }

    @Override
    public CompletableFuture<DisputeCompiledSubmittedEvidenceResponse> getCompiledSubmittedArbitrationEvidence(String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.getAsync(
                buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH, ARBITRATION_PATH, SUBMITTED_PATH),
                sdkAuthorization(),
                DisputeCompiledSubmittedEvidenceResponse.class
        );
    }

    @Override
    public CompletableFuture<SchemeFileResponse> getDisputeSchemeFiles(final String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.getAsync(
                buildPath(DISPUTES_PATH, disputeId, SCHEME_FILES_PATH),
                sdkAuthorization(),
                SchemeFileResponse.class
        );
    }

    @Override
    public CompletableFuture<IdResponse> uploadFile(final FileRequest fileRequest) {
        validateFileRequest(fileRequest);
        return apiClient.submitFileAsync(
                FILES_PATH,
                sdkAuthorization(),
                fileRequest,
                IdResponse.class);
    }

    @Override
    public CompletableFuture<FileDetailsResponse> getFileDetails(final String fileId) {
        validateFileId(fileId);
        return apiClient.getAsync(
                buildPath(FILES_PATH, fileId),
                sdkAuthorization(),
                FileDetailsResponse.class);
    }

    // Synchronous methods
    @Override
    public DisputesQueryResponse querySync(final DisputesQueryFilter queryFilter) {
        validateQueryFilter(queryFilter);
        return apiClient.query(
                DISPUTES_PATH,
                sdkAuthorization(),
                queryFilter,
                DisputesQueryResponse.class);
    }

    @Override
    public DisputeDetailsResponse getDisputeDetailsSync(final String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.get(
                buildPath(DISPUTES_PATH, disputeId),
                sdkAuthorization(),
                DisputeDetailsResponse.class);
    }

    @Override
    public EmptyResponse acceptSync(final String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.post(
                buildPath(DISPUTES_PATH, disputeId, ACCEPT_PATH),
                sdkAuthorization(),
                EmptyResponse.class,
                null,
                null);
    }

    @Override
    public EmptyResponse putEvidenceSync(final String disputeId, final DisputeEvidenceRequest disputeEvidence) {
        validateDisputeIdAndEvidence(disputeId, disputeEvidence);
        return apiClient.put(
                buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH),
                sdkAuthorization(),
                EmptyResponse.class,
                disputeEvidence);
    }

    @Override
    public DisputeEvidenceResponse getEvidenceSync(final String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.get(
                buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH),
                sdkAuthorization(),
                DisputeEvidenceResponse.class);
    }

    @Override
    public EmptyResponse submitEvidenceSync(final String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.post(
                buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH),
                sdkAuthorization(),
                EmptyResponse.class,
                null,
                null);
    }

    @Override
    public EmptyResponse submitArbitrationEvidenceSync(String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.post(
                buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH, ARBITRATION_PATH),
                sdkAuthorization(),
                EmptyResponse.class,
                null,
                null);
    }

    @Override
    public DisputeCompiledSubmittedEvidenceResponse getCompiledSubmittedEvidenceSync(String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.get(
                buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH, SUBMITTED_PATH),
                sdkAuthorization(),
                DisputeCompiledSubmittedEvidenceResponse.class
        );
    }

    @Override
    public DisputeCompiledSubmittedEvidenceResponse getCompiledSubmittedArbitrationEvidenceSync(String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.get(
                buildPath(DISPUTES_PATH, disputeId, EVIDENCE_PATH, ARBITRATION_PATH, SUBMITTED_PATH),
                sdkAuthorization(),
                DisputeCompiledSubmittedEvidenceResponse.class
        );
    }

    @Override
    public SchemeFileResponse getDisputeSchemeFilesSync(final String disputeId) {
        validateDisputeId(disputeId);
        return apiClient.get(
                buildPath(DISPUTES_PATH, disputeId, SCHEME_FILES_PATH),
                sdkAuthorization(),
                SchemeFileResponse.class
        );
    }

    @Override
    public IdResponse uploadFileSync(final FileRequest fileRequest) {
        validateFileRequest(fileRequest);
        return apiClient.submitFile(
                FILES_PATH,
                sdkAuthorization(),
                fileRequest,
                IdResponse.class);
    }

    @Override
    public FileDetailsResponse getFileDetailsSync(final String fileId) {
        validateFileId(fileId);
        return apiClient.get(
                buildPath(FILES_PATH, fileId),
                sdkAuthorization(),
                FileDetailsResponse.class);
    }

    // Common methods
    protected void validateQueryFilter(final DisputesQueryFilter queryFilter) {
        validateParams("queryFilter", queryFilter);
    }

    protected void validateDisputeId(final String disputeId) {
        validateParams("disputeId", disputeId);
    }

    protected void validateDisputeIdAndEvidence(final String disputeId, final DisputeEvidenceRequest disputeEvidence) {
        validateParams("disputeId", disputeId, "disputeEvidence", disputeEvidence);
    }

    protected void validateFileRequest(final FileRequest fileRequest) {
        validateParams("fileRequest", fileRequest);
    }

    protected void validateFileId(final String fileId) {
        validateParams("fileId", fileId);
    }

}
