package com.checkout.disputes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.FileDetailsResponse;
import com.checkout.common.FileRequest;
import com.checkout.common.IdResponse;

@ExtendWith(MockitoExtension.class)
class DisputesClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private DisputesClient client;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new DisputesClientImpl(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Test
    void shouldQueryDisputes() throws ExecutionException, InterruptedException {
        final DisputesQueryFilter request = createDisputesQueryFilter();
        final DisputesQueryResponse expectedResponse = createDisputesQueryResponse();

        when(apiClient.queryAsync("disputes", authorization, request, DisputesQueryResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<DisputesQueryResponse> future = client.query(request);

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldGetDisputeDetails() throws ExecutionException, InterruptedException {
        final DisputeDetailsResponse expectedResponse = createDisputeDetailsResponse();

        when(apiClient.getAsync("disputes/dispute_id", authorization, DisputeDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<DisputeDetailsResponse> future = client.getDisputeDetails("dispute_id");

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldAcceptDispute() throws ExecutionException, InterruptedException {
        final EmptyResponse expectedResponse = createEmptyResponse();

        when(apiClient.postAsync(eq("disputes/dispute_id/accept"), eq(authorization), eq(EmptyResponse.class),
                isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<EmptyResponse> future = client.accept("dispute_id");

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldPutDisputeEvidence() throws ExecutionException, InterruptedException {
        final DisputeEvidenceRequest request = createDisputeEvidenceRequest();
        final EmptyResponse expectedResponse = createEmptyResponse();

        when(apiClient.putAsync("disputes/dispute_id/evidence", authorization, EmptyResponse.class, request))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<EmptyResponse> future = client.putEvidence("dispute_id", request);

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldGetDisputeEvidence() throws ExecutionException, InterruptedException {
        final DisputeEvidenceResponse expectedResponse = createDisputeEvidenceResponse();

        when(apiClient.getAsync("disputes/dispute_id/evidence", authorization, DisputeEvidenceResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<DisputeEvidenceResponse> future = client.getEvidence("dispute_id");

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldSubmitDisputeEvidence() throws ExecutionException, InterruptedException {
        final EmptyResponse expectedResponse = createEmptyResponse();

        when(apiClient.postAsync(eq("disputes/dispute_id/evidence"), eq(authorization), eq(EmptyResponse.class),
                isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<EmptyResponse> future = client.submitEvidence("dispute_id");

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldSubmitArbitrationEvidence() throws ExecutionException, InterruptedException {
        final EmptyResponse expectedResponse = createEmptyResponse();

        when(apiClient.postAsync(eq("disputes/dispute_id/evidence/arbitration"), eq(authorization), eq(EmptyResponse.class),
                isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<EmptyResponse> future = client.submitArbitrationEvidence("dispute_id");

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldGetCompiledSubmittedEvidence() throws ExecutionException, InterruptedException {
        final DisputeCompiledSubmittedEvidenceResponse expectedResponse = createDisputeCompiledSubmittedEvidenceResponse();

        when(apiClient.getAsync("disputes/dispute_id/evidence/submitted", authorization, DisputeCompiledSubmittedEvidenceResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<DisputeCompiledSubmittedEvidenceResponse> future = client.getCompiledSubmittedEvidence("dispute_id");

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldGetCompiledSubmittedArbitrationEvidence() throws ExecutionException, InterruptedException {
        final DisputeCompiledSubmittedEvidenceResponse expectedResponse = createDisputeCompiledSubmittedEvidenceResponse();

        when(apiClient.getAsync("disputes/dispute_id/evidence/arbitration/submitted", authorization, DisputeCompiledSubmittedEvidenceResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<DisputeCompiledSubmittedEvidenceResponse> future = client.getCompiledSubmittedArbitrationEvidence("dispute_id");

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldGetDisputeSchemeFiles() throws ExecutionException, InterruptedException {
        final SchemeFileResponse expectedResponse = createSchemeFileResponse();

        when(apiClient.getAsync("disputes/dispute_id/schemefiles", authorization, SchemeFileResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<SchemeFileResponse> future = client.getDisputeSchemeFiles("dispute_id");

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldUploadFile() throws ExecutionException, InterruptedException {
        final FileRequest request = createFileRequest();
        final IdResponse expectedResponse = createIdResponse();

        when(apiClient.submitFileAsync("files", authorization, request, IdResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<IdResponse> future = client.uploadFile(request);

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldGetFileDetails() throws ExecutionException, InterruptedException {
        final FileDetailsResponse expectedResponse = createFileDetailsResponse();

        when(apiClient.getAsync("files/file_id", authorization, FileDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<FileDetailsResponse> future = client.getFileDetails("file_id");

        validateResponse(expectedResponse, future.get());
    }

    // Synchronous methods
    @Test
    void shouldQueryDisputesSync() {
        final DisputesQueryFilter request = createDisputesQueryFilter();
        final DisputesQueryResponse expectedResponse = createDisputesQueryResponse();

        when(apiClient.query("disputes", authorization, request, DisputesQueryResponse.class))
                .thenReturn(expectedResponse);

        final DisputesQueryResponse actualResponse = client.querySync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetDisputeDetailsSync() {
        final DisputeDetailsResponse expectedResponse = createDisputeDetailsResponse();

        when(apiClient.get("disputes/dispute_id", authorization, DisputeDetailsResponse.class))
                .thenReturn(expectedResponse);

        final DisputeDetailsResponse actualResponse = client.getDisputeDetailsSync("dispute_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldAcceptDisputeSync() {
        final EmptyResponse expectedResponse = createEmptyResponse();

        when(apiClient.post(eq("disputes/dispute_id/accept"), eq(authorization), eq(EmptyResponse.class),
                isNull(), isNull()))
                .thenReturn(expectedResponse);

        final EmptyResponse actualResponse = client.acceptSync("dispute_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldPutDisputeEvidenceSync() {
        final DisputeEvidenceRequest request = createDisputeEvidenceRequest();
        final EmptyResponse expectedResponse = createEmptyResponse();

        when(apiClient.put("disputes/dispute_id/evidence", authorization, EmptyResponse.class, request))
                .thenReturn(expectedResponse);

        final EmptyResponse actualResponse = client.putEvidenceSync("dispute_id", request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetDisputeEvidenceSync() {
        final DisputeEvidenceResponse expectedResponse = createDisputeEvidenceResponse();

        when(apiClient.get("disputes/dispute_id/evidence", authorization, DisputeEvidenceResponse.class))
                .thenReturn(expectedResponse);

        final DisputeEvidenceResponse actualResponse = client.getEvidenceSync("dispute_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldSubmitDisputeEvidenceSync() {
        final EmptyResponse expectedResponse = createEmptyResponse();

        when(apiClient.post(eq("disputes/dispute_id/evidence"), eq(authorization), eq(EmptyResponse.class),
                isNull(), isNull()))
                .thenReturn(expectedResponse);

        final EmptyResponse actualResponse = client.submitEvidenceSync("dispute_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldSubmitArbitrationEvidenceSync() {
        final EmptyResponse expectedResponse = createEmptyResponse();

        when(apiClient.post(eq("disputes/dispute_id/evidence/arbitration"), eq(authorization), eq(EmptyResponse.class),
                isNull(), isNull()))
                .thenReturn(expectedResponse);

        final EmptyResponse actualResponse = client.submitArbitrationEvidenceSync("dispute_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetCompiledSubmittedEvidenceSync() {
        final DisputeCompiledSubmittedEvidenceResponse expectedResponse = createDisputeCompiledSubmittedEvidenceResponse();

        when(apiClient.get("disputes/dispute_id/evidence/submitted", authorization, DisputeCompiledSubmittedEvidenceResponse.class))
                .thenReturn(expectedResponse);

        final DisputeCompiledSubmittedEvidenceResponse actualResponse = client.getCompiledSubmittedEvidenceSync("dispute_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetCompiledSubmittedArbitrationEvidenceSync() {
        final DisputeCompiledSubmittedEvidenceResponse expectedResponse = createDisputeCompiledSubmittedEvidenceResponse();

        when(apiClient.get("disputes/dispute_id/evidence/arbitration/submitted", authorization, DisputeCompiledSubmittedEvidenceResponse.class))
                .thenReturn(expectedResponse);

        final DisputeCompiledSubmittedEvidenceResponse actualResponse = client.getCompiledSubmittedArbitrationEvidenceSync("dispute_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetDisputeSchemeFilesSync() {
        final SchemeFileResponse expectedResponse = createSchemeFileResponse();

        when(apiClient.get("disputes/dispute_id/schemefiles", authorization, SchemeFileResponse.class))
                .thenReturn(expectedResponse);

        final SchemeFileResponse actualResponse = client.getDisputeSchemeFilesSync("dispute_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldUploadFileSync() {
        final FileRequest request = createFileRequest();
        final IdResponse expectedResponse = createIdResponse();

        when(apiClient.submitFile("files", authorization, request, IdResponse.class))
                .thenReturn(expectedResponse);

        final IdResponse actualResponse = client.uploadFileSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetFileDetailsSync() {
        final FileDetailsResponse expectedResponse = createFileDetailsResponse();

        when(apiClient.get("files/file_id", authorization, FileDetailsResponse.class))
                .thenReturn(expectedResponse);

        final FileDetailsResponse actualResponse = client.getFileDetailsSync("file_id");

        validateResponse(expectedResponse, actualResponse);
    }

    // Common methods
    private DisputesQueryFilter createDisputesQueryFilter() {
        return mock(DisputesQueryFilter.class);
    }

    private DisputesQueryResponse createDisputesQueryResponse() {
        return mock(DisputesQueryResponse.class);
    }

    private DisputeDetailsResponse createDisputeDetailsResponse() {
        return mock(DisputeDetailsResponse.class);
    }

    private DisputeEvidenceRequest createDisputeEvidenceRequest() {
        return mock(DisputeEvidenceRequest.class);
    }

    private DisputeEvidenceResponse createDisputeEvidenceResponse() {
        return mock(DisputeEvidenceResponse.class);
    }

    private DisputeCompiledSubmittedEvidenceResponse createDisputeCompiledSubmittedEvidenceResponse() {
        return mock(DisputeCompiledSubmittedEvidenceResponse.class);
    }

    private SchemeFileResponse createSchemeFileResponse() {
        return mock(SchemeFileResponse.class);
    }

    private FileRequest createFileRequest() {
        return mock(FileRequest.class);
    }

    private FileDetailsResponse createFileDetailsResponse() {
        return mock(FileDetailsResponse.class);
    }

    private IdResponse createIdResponse() {
        return mock(IdResponse.class);
    }

    private EmptyResponse createEmptyResponse() {
        return mock(EmptyResponse.class);
    }

    private <T> void validateResponse(T expectedResponse, T actualResponse) {
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

}