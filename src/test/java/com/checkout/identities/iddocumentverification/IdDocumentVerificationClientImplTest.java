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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
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
    void shouldCreateIdDocumentVerificationAsync() throws ExecutionException, InterruptedException {
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse response = createIdDocumentVerificationResponse();

        when(apiClient.postAsync(eq("id-document-verifications"), eq(authorization), eq(IdDocumentVerificationResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdDocumentVerificationResponse> future = client.createIdDocumentVerificationAsync(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetIdDocumentVerificationAsync() throws ExecutionException, InterruptedException {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationResponse response = createIdDocumentVerificationResponse();

        when(apiClient.getAsync(eq("id-document-verifications/" + idDocumentVerificationId), eq(authorization), 
                eq(IdDocumentVerificationResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdDocumentVerificationResponse> future = client.getIdDocumentVerificationAsync(idDocumentVerificationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldAnonymizeIdDocumentVerificationAsync() throws ExecutionException, InterruptedException {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationResponse response = createIdDocumentVerificationResponse();

        when(apiClient.postAsync(eq("id-document-verifications/" + idDocumentVerificationId + "/anonymize"), eq(authorization), 
                eq(IdDocumentVerificationResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdDocumentVerificationResponse> future = client.anonymizeIdDocumentVerificationAsync(idDocumentVerificationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldCreateIdDocumentVerificationAttemptAsync() throws ExecutionException, InterruptedException {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationAttemptRequest request = createIdDocumentVerificationAttemptRequest();
        final IdDocumentVerificationAttemptResponse response = createIdDocumentVerificationAttemptResponse();

        when(apiClient.postAsync(eq("id-document-verifications/" + idDocumentVerificationId + "/attempts"), eq(authorization), 
                eq(IdDocumentVerificationAttemptResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdDocumentVerificationAttemptResponse> future = client.createIdDocumentVerificationAttemptAsync(idDocumentVerificationId, request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetIdDocumentVerificationAttemptsAsync() throws ExecutionException, InterruptedException {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationAttemptsResponse response = createIdDocumentVerificationAttemptsResponse();

        when(apiClient.getAsync(eq("id-document-verifications/" + idDocumentVerificationId + "/attempts"), eq(authorization), 
                eq(IdDocumentVerificationAttemptsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdDocumentVerificationAttemptsResponse> future = client.getIdDocumentVerificationAttemptsAsync(idDocumentVerificationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetIdDocumentVerificationAttemptAsync() throws ExecutionException, InterruptedException {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final String attemptId = "datp_test_123456789";
        final IdDocumentVerificationAttemptResponse response = createIdDocumentVerificationAttemptResponse();

        when(apiClient.getAsync(eq("id-document-verifications/" + idDocumentVerificationId + "/attempts/" + attemptId), eq(authorization), 
                eq(IdDocumentVerificationAttemptResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdDocumentVerificationAttemptResponse> future = client.getIdDocumentVerificationAttemptAsync(idDocumentVerificationId, attemptId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetIdDocumentVerificationReportAsync() throws ExecutionException, InterruptedException {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationReportResponse response = createIdDocumentVerificationReportResponse();

        when(apiClient.getAsync(eq("id-document-verifications/" + idDocumentVerificationId + "/pdf-report"), eq(authorization), 
                eq(IdDocumentVerificationReportResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdDocumentVerificationReportResponse> future = client.getIdDocumentVerificationReportAsync(idDocumentVerificationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    // Synchronous methods tests
    @Test
    void shouldCreateIdDocumentVerification() {
        final IdDocumentVerificationRequest request = createIdDocumentVerificationRequest();
        final IdDocumentVerificationResponse response = createIdDocumentVerificationResponse();

        when(apiClient.post(eq("id-document-verifications"), eq(authorization), eq(IdDocumentVerificationResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final IdDocumentVerificationResponse result = client.createIdDocumentVerification(request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetIdDocumentVerification() {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationResponse response = createIdDocumentVerificationResponse();

        when(apiClient.get(eq("id-document-verifications/" + idDocumentVerificationId), eq(authorization), 
                eq(IdDocumentVerificationResponse.class)))
                .thenReturn(response);

        final IdDocumentVerificationResponse result = client.getIdDocumentVerification(idDocumentVerificationId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldAnonymizeIdDocumentVerification() {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationResponse response = createIdDocumentVerificationResponse();

        when(apiClient.post(eq("id-document-verifications/" + idDocumentVerificationId + "/anonymize"), eq(authorization), 
                eq(IdDocumentVerificationResponse.class), isNull(), isNull()))
                .thenReturn(response);

        final IdDocumentVerificationResponse result = client.anonymizeIdDocumentVerification(idDocumentVerificationId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldCreateIdDocumentVerificationAttempt() {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationAttemptRequest request = createIdDocumentVerificationAttemptRequest();
        final IdDocumentVerificationAttemptResponse response = createIdDocumentVerificationAttemptResponse();

        when(apiClient.post(eq("id-document-verifications/" + idDocumentVerificationId + "/attempts"), eq(authorization), 
                eq(IdDocumentVerificationAttemptResponse.class), eq(request), isNull()))
                .thenReturn(response);

        final IdDocumentVerificationAttemptResponse result = client.createIdDocumentVerificationAttempt(idDocumentVerificationId, request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetIdDocumentVerificationAttempts() {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationAttemptsResponse response = createIdDocumentVerificationAttemptsResponse();

        when(apiClient.get(eq("id-document-verifications/" + idDocumentVerificationId + "/attempts"), eq(authorization), 
                eq(IdDocumentVerificationAttemptsResponse.class)))
                .thenReturn(response);

        final IdDocumentVerificationAttemptsResponse result = client.getIdDocumentVerificationAttempts(idDocumentVerificationId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetIdDocumentVerificationAttempt() {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final String attemptId = "datp_test_123456789";
        final IdDocumentVerificationAttemptResponse response = createIdDocumentVerificationAttemptResponse();

        when(apiClient.get(eq("id-document-verifications/" + idDocumentVerificationId + "/attempts/" + attemptId), eq(authorization), 
                eq(IdDocumentVerificationAttemptResponse.class)))
                .thenReturn(response);

        final IdDocumentVerificationAttemptResponse result = client.getIdDocumentVerificationAttempt(idDocumentVerificationId, attemptId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetIdDocumentVerificationReport() {
        final String idDocumentVerificationId = "iddv_test_123456789";
        final IdDocumentVerificationReportResponse response = createIdDocumentVerificationReportResponse();

        when(apiClient.get(eq("id-document-verifications/" + idDocumentVerificationId + "/pdf-report"), eq(authorization), 
                eq(IdDocumentVerificationReportResponse.class)))
                .thenReturn(response);

        final IdDocumentVerificationReportResponse result = client.getIdDocumentVerificationReport(idDocumentVerificationId);

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