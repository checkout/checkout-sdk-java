package com.checkout.identities.iddocumentverification;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.identities.iddocumentverification.requests.IdDocumentVerificationAttemptRequest;
import com.checkout.identities.iddocumentverification.requests.IdDocumentVerificationRequest;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationAttemptResponse;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationAttemptsResponse;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationReportResponse;
import com.checkout.identities.iddocumentverification.responses.IdDocumentVerificationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdDocumentVerificationClientImplTest {

    private IdDocumentVerificationClient client;

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new IdDocumentVerificationClientImpl(apiClient, configuration);
    }

    @Test
    void shouldCreateIdDocumentVerification() throws ExecutionException, InterruptedException {
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse response = createIdDocumentVerificationResponse();

        when(apiClient.postAsync("id-document-verifications", authorization, IdDocumentVerificationResponse.class,
                request, null))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdDocumentVerificationResponse> future = client.createIdDocumentVerification(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetIdDocumentVerification() throws ExecutionException, InterruptedException {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationResponse response = createIdDocumentVerificationResponse();

        when(apiClient.getAsync("id-document-verifications/" + idDocumentVerificationId, authorization, 
                IdDocumentVerificationResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdDocumentVerificationResponse> future = client.getIdDocumentVerification(idDocumentVerificationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldAnonymizeIdDocumentVerification() throws ExecutionException, InterruptedException {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationResponse response = createIdDocumentVerificationResponse();

        when(apiClient.postAsync("id-document-verifications/" + idDocumentVerificationId + "/anonymize", authorization, 
                IdDocumentVerificationResponse.class, null, null))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdDocumentVerificationResponse> future = client.anonymizeIdDocumentVerification(idDocumentVerificationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldCreateIdDocumentVerificationAttempt() throws ExecutionException, InterruptedException {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationAttemptRequest request = createIdDocumentVerificationAttemptRequest();
        final IdDocumentVerificationAttemptResponse response = createIdDocumentVerificationAttemptResponse();

        when(apiClient.postAsync("id-document-verifications/" + idDocumentVerificationId + "/attempts", authorization, 
                IdDocumentVerificationAttemptResponse.class, request, null))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdDocumentVerificationAttemptResponse> future = client.createIdDocumentVerificationAttempt(idDocumentVerificationId, request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetIdDocumentVerificationAttempts() throws ExecutionException, InterruptedException {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationAttemptsResponse response = createIdDocumentVerificationAttemptsResponse();

        when(apiClient.getAsync("id-document-verifications/" + idDocumentVerificationId + "/attempts", authorization, 
                IdDocumentVerificationAttemptsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdDocumentVerificationAttemptsResponse> future = client.getIdDocumentVerificationAttempts(idDocumentVerificationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetIdDocumentVerificationAttempt() throws ExecutionException, InterruptedException {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final String attemptId = "datp_test_123456789";
        final IdDocumentVerificationAttemptResponse response = createIdDocumentVerificationAttemptResponse();

        when(apiClient.getAsync("id-document-verifications/" + idDocumentVerificationId + "/attempts/" + attemptId, authorization, 
                IdDocumentVerificationAttemptResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdDocumentVerificationAttemptResponse> future = client.getIdDocumentVerificationAttempt(idDocumentVerificationId, attemptId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetIdDocumentVerificationReport() throws ExecutionException, InterruptedException {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationReportResponse response = createIdDocumentVerificationReportResponse();

        when(apiClient.getAsync("id-document-verifications/" + idDocumentVerificationId + "/pdf-report", authorization, 
                IdDocumentVerificationReportResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdDocumentVerificationReportResponse> future = client.getIdDocumentVerificationReport(idDocumentVerificationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    // Synchronous methods tests
    @Test
    void shouldCreateIdDocumentVerificationSync() {
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse response = createIdDocumentVerificationResponse();

        when(apiClient.post("id-document-verifications", authorization, IdDocumentVerificationResponse.class,
                request, null))
                .thenReturn(response);

        final IdDocumentVerificationResponse result = client.createIdDocumentVerificationSync(request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetIdDocumentVerificationSync() {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationResponse response = createIdDocumentVerificationResponse();

        when(apiClient.get("id-document-verifications/" + idDocumentVerificationId, authorization, 
                IdDocumentVerificationResponse.class))
                .thenReturn(response);

        final IdDocumentVerificationResponse result = client.getIdDocumentVerificationSync(idDocumentVerificationId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldAnonymizeIdDocumentVerificationSync() {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationResponse response = createIdDocumentVerificationResponse();

        when(apiClient.post("id-document-verifications/" + idDocumentVerificationId + "/anonymize", authorization, 
                IdDocumentVerificationResponse.class, null, null))
                .thenReturn(response);

        final IdDocumentVerificationResponse result = client.anonymizeIdDocumentVerificationSync(idDocumentVerificationId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldCreateIdDocumentVerificationAttemptSync() {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationAttemptRequest request = createIdDocumentVerificationAttemptRequest();
        final IdDocumentVerificationAttemptResponse response = createIdDocumentVerificationAttemptResponse();

        when(apiClient.post("id-document-verifications/" + idDocumentVerificationId + "/attempts", authorization, 
                IdDocumentVerificationAttemptResponse.class, request, null))
                .thenReturn(response);

        final IdDocumentVerificationAttemptResponse result = client.createIdDocumentVerificationAttemptSync(idDocumentVerificationId, request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetIdDocumentVerificationAttemptsSync() {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationAttemptsResponse response = createIdDocumentVerificationAttemptsResponse();

        when(apiClient.get("id-document-verifications/" + idDocumentVerificationId + "/attempts", authorization, 
                IdDocumentVerificationAttemptsResponse.class))
                .thenReturn(response);

        final IdDocumentVerificationAttemptsResponse result = client.getIdDocumentVerificationAttemptsSync(idDocumentVerificationId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetIdDocumentVerificationAttemptSync() {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final String attemptId = "datp_test_123456789";
        final IdDocumentVerificationAttemptResponse response = createIdDocumentVerificationAttemptResponse();

        when(apiClient.get("id-document-verifications/" + idDocumentVerificationId + "/attempts/" + attemptId, authorization, 
                IdDocumentVerificationAttemptResponse.class))
                .thenReturn(response);

        final IdDocumentVerificationAttemptResponse result = client.getIdDocumentVerificationAttemptSync(idDocumentVerificationId, attemptId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetIdDocumentVerificationReportSync() {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationReportResponse response = createIdDocumentVerificationReportResponse();

        when(apiClient.get("id-document-verifications/" + idDocumentVerificationId + "/pdf-report", authorization, 
                IdDocumentVerificationReportResponse.class))
                .thenReturn(response);

        final IdDocumentVerificationReportResponse result = client.getIdDocumentVerificationReportSync(idDocumentVerificationId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    // Common methods
    private IdDocumentVerificationRequest createIdDocumentVerificationRequest() {
        return mock(IdDocumentVerificationRequest.class);
    }

    private IdDocumentVerificationAttemptRequest createIdDocumentVerificationAttemptRequest() {
        return mock(IdDocumentVerificationAttemptRequest.class);
    }

    private IdDocumentVerificationResponse createIdDocumentVerificationResponse() {
        return mock(IdDocumentVerificationResponse.class);
    }

    private IdDocumentVerificationAttemptResponse createIdDocumentVerificationAttemptResponse() {
        return mock(IdDocumentVerificationAttemptResponse.class);
    }

    private IdDocumentVerificationAttemptsResponse createIdDocumentVerificationAttemptsResponse() {
        return mock(IdDocumentVerificationAttemptsResponse.class);
    }

    private IdDocumentVerificationReportResponse createIdDocumentVerificationReportResponse() {
        return mock(IdDocumentVerificationReportResponse.class);
    }
}