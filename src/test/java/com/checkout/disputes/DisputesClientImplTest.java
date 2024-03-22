package com.checkout.disputes;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.FileDetailsResponse;
import com.checkout.common.FileRequest;
import com.checkout.common.IdResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        final DisputesQueryFilter request = mock(DisputesQueryFilter.class);
        final DisputesQueryResponse response = mock(DisputesQueryResponse.class);

        when(apiClient.queryAsync("disputes", authorization, request, DisputesQueryResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<DisputesQueryResponse> future = client.query(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetDisputeDetails() throws ExecutionException, InterruptedException {

        final DisputeDetailsResponse response = mock(DisputeDetailsResponse.class);

        when(apiClient.getAsync("disputes/dispute_id", authorization, DisputeDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<DisputeDetailsResponse> future = client.getDisputeDetails("dispute_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldAcceptDispute() throws ExecutionException, InterruptedException {

        final DisputesQueryFilter request = mock(DisputesQueryFilter.class);
        final EmptyResponse response = mock(EmptyResponse.class);

        when(apiClient.postAsync(eq("disputes/dispute_id/accept"), eq(authorization), eq(EmptyResponse.class),
                isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = client.accept("dispute_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldPuDisputeEvidence() throws ExecutionException, InterruptedException {

        final DisputeEvidenceRequest request = mock(DisputeEvidenceRequest.class);
        final EmptyResponse response = mock(EmptyResponse.class);

        when(apiClient.putAsync("disputes/dispute_id/evidence", authorization, EmptyResponse.class, request))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = client.putEvidence("dispute_id", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetDisputeEvidence() throws ExecutionException, InterruptedException {

        final DisputeEvidenceResponse response = mock(DisputeEvidenceResponse.class);

        when(apiClient.getAsync("disputes/dispute_id/evidence", authorization, DisputeEvidenceResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<DisputeEvidenceResponse> future = client.getEvidence("dispute_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetCompiledSubmittedEvidence() throws ExecutionException, InterruptedException {
        final DisputeCompiledSubmittedEvidenceResponse response = mock(DisputeCompiledSubmittedEvidenceResponse.class);

        when(apiClient.getAsync("disputes/dispute_id/evidence/submitted", authorization, DisputeCompiledSubmittedEvidenceResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<DisputeCompiledSubmittedEvidenceResponse> future = client.getCompiledSubmittedEvidence("dispute_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldSubmitDisputeEvidence() throws ExecutionException, InterruptedException {

        final EmptyResponse response = mock(EmptyResponse.class);

        when(apiClient.postAsync(eq("disputes/dispute_id/evidence"), eq(authorization), eq(EmptyResponse.class),
                isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = client.submitEvidence("dispute_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldUploadFile() throws ExecutionException, InterruptedException {

        final FileRequest request = mock(FileRequest.class);
        final IdResponse response = mock(IdResponse.class);

        when(apiClient.submitFileAsync("files", authorization, request, IdResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdResponse> future = client.uploadFile(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetFileDetails() throws ExecutionException, InterruptedException {

        final FileDetailsResponse response = mock(FileDetailsResponse.class);

        when(apiClient.getAsync("files/file_id", authorization, FileDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<FileDetailsResponse> future = client.getFileDetails("file_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetDisputeSchemeFiles() throws ExecutionException, InterruptedException {
        final SchemeFileResponse response = mock(SchemeFileResponse.class);

        when(apiClient.getAsync("disputes/dispute_id/schemefiles", authorization, SchemeFileResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<SchemeFileResponse> future = client.getDisputeSchemeFiles("dispute_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

}