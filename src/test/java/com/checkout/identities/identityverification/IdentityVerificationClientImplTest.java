package com.checkout.identities.identityverification;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.identities.identityverification.requests.CreateAndOpenIdentityVerificationRequest;
import com.checkout.identities.identityverification.requests.IdentityVerificationRequest;
import com.checkout.identities.identityverification.requests.IdentityVerificationAttemptRequest;
import com.checkout.identities.identityverification.responses.IdentityVerificationAttemptResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationAttemptsResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationReportResponse;
import com.checkout.identities.identityverification.responses.IdentityVerificationResponse;
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
class IdentityVerificationClientImplTest {

    private IdentityVerificationClient client;

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
        client = new IdentityVerificationClientImpl(apiClient, configuration);
    }

    // Async methods

    @Test
    void shouldCreateAndOpenIdentityVerificationAsync() throws ExecutionException, InterruptedException {
        final CreateAndOpenIdentityVerificationRequest request = createCreateAndOpenIdentityVerificationRequest();
        final IdentityVerificationResponse response = createIdentityVerificationResponse();

        when(apiClient.postAsync(eq("create-and-open-idv"), eq(authorization), eq(IdentityVerificationResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdentityVerificationResponse> future = client.createAndOpenIdentityVerificationAsync(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldCreateIdentityVerificationAsync() throws ExecutionException, InterruptedException {
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse response = createIdentityVerificationResponse();

        when(apiClient.postAsync(eq("identity-verifications"), eq(authorization), eq(IdentityVerificationResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdentityVerificationResponse> future = client.createIdentityVerificationAsync(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetIdentityVerificationAsync() throws ExecutionException, InterruptedException {
        final String identityVerificationId = "idv_test_123456789";
        final IdentityVerificationResponse response = createIdentityVerificationResponse();

        when(apiClient.getAsync(eq("identity-verifications/" + identityVerificationId), eq(authorization), 
                eq(IdentityVerificationResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdentityVerificationResponse> future = client.getIdentityVerificationAsync(identityVerificationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldAnonymizeIdentityVerificationAsync() throws ExecutionException, InterruptedException {
        final String identityVerificationId = "idv_test_123456789";
        final IdentityVerificationResponse response = createIdentityVerificationResponse();

        when(apiClient.postAsync(eq("identity-verifications/" + identityVerificationId + "/anonymize"), eq(authorization), 
                eq(IdentityVerificationResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdentityVerificationResponse> future = client.anonymizeIdentityVerificationAsync(identityVerificationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetIdentityVerificationAttemptsAsync() throws ExecutionException, InterruptedException {
        final String identityVerificationId = "idv_test_123456789";
        final IdentityVerificationAttemptsResponse response = createIdentityVerificationAttemptsResponse();

        when(apiClient.getAsync(eq("identity-verifications/" + identityVerificationId + "/attempts"), eq(authorization), 
                eq(IdentityVerificationAttemptsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdentityVerificationAttemptsResponse> future = client.getIdentityVerificationAttemptsAsync(identityVerificationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldCreateIdentityVerificationAttemptAsync() throws ExecutionException, InterruptedException {
        final String identityVerificationId = "idv_test_123456789";
        final IdentityVerificationAttemptRequest request = createIdentityVerificationAttemptRequest();
        final IdentityVerificationAttemptResponse response = createIdentityVerificationAttemptResponse();

        when(apiClient.postAsync(eq("identity-verifications/" + identityVerificationId + "/attempts"), eq(authorization), 
                eq(IdentityVerificationAttemptResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdentityVerificationAttemptResponse> future = client.createIdentityVerificationAttemptAsync(identityVerificationId, request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetIdentityVerificationAttemptAsync() throws ExecutionException, InterruptedException {
        final String identityVerificationId = "idv_test_123456789";
        final String attemptId = "idva_test_987654321";
        final IdentityVerificationAttemptResponse response = createIdentityVerificationAttemptResponse();

        when(apiClient.getAsync(eq("identity-verifications/" + identityVerificationId + "/attempts/" + attemptId), 
                eq(authorization), eq(IdentityVerificationAttemptResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdentityVerificationAttemptResponse> future = client.getIdentityVerificationAttemptAsync(identityVerificationId, attemptId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGenerateIdentityVerificationReportAsync() throws ExecutionException, InterruptedException {
        final String identityVerificationId = "idv_test_123456789";
        final IdentityVerificationReportResponse response = createIdentityVerificationReportResponse();

        when(apiClient.getAsync(eq("identity-verifications/" + identityVerificationId + "/pdf-report"), eq(authorization), 
                eq(IdentityVerificationReportResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdentityVerificationReportResponse> future = client.generateIdentityVerificationReportAsync(identityVerificationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    // Synchronous methods tests

    @Test
    void shouldCreateAndOpenIdentityVerification() {
        final CreateAndOpenIdentityVerificationRequest request = createCreateAndOpenIdentityVerificationRequest();
        final IdentityVerificationResponse response = createIdentityVerificationResponse();

        when(apiClient.post(eq("create-and-open-idv"), eq(authorization), eq(IdentityVerificationResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final IdentityVerificationResponse result = client.createAndOpenIdentityVerification(request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldCreateIdentityVerification() {
        final IdentityVerificationRequest request = createIdentityVerificationRequest();
        final IdentityVerificationResponse response = createIdentityVerificationResponse();

        when(apiClient.post(eq("identity-verifications"), eq(authorization), eq(IdentityVerificationResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final IdentityVerificationResponse result = client.createIdentityVerification(request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetIdentityVerification() {
        final String identityVerificationId = "idv_test_123456789";
        final IdentityVerificationResponse response = createIdentityVerificationResponse();

        when(apiClient.get(eq("identity-verifications/" + identityVerificationId), eq(authorization), 
                eq(IdentityVerificationResponse.class)))
                .thenReturn(response);

        final IdentityVerificationResponse result = client.getIdentityVerification(identityVerificationId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldAnonymizeIdentityVerification() {
        final String identityVerificationId = "idv_test_123456789";
        final IdentityVerificationResponse response = createIdentityVerificationResponse();

        when(apiClient.post(eq("identity-verifications/" + identityVerificationId + "/anonymize"), eq(authorization), 
                eq(IdentityVerificationResponse.class), isNull(), isNull()))
                .thenReturn(response);

        final IdentityVerificationResponse result = client.anonymizeIdentityVerification(identityVerificationId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetIdentityVerificationAttempts() {
        final String identityVerificationId = "idv_test_123456789";
        final IdentityVerificationAttemptsResponse response = createIdentityVerificationAttemptsResponse();

        when(apiClient.get(eq("identity-verifications/" + identityVerificationId + "/attempts"), eq(authorization), 
                eq(IdentityVerificationAttemptsResponse.class)))
                .thenReturn(response);

        final IdentityVerificationAttemptsResponse result = client.getIdentityVerificationAttempts(identityVerificationId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldCreateIdentityVerificationAttempt() {
        final String identityVerificationId = "idv_test_123456789";
        final IdentityVerificationAttemptRequest request = createIdentityVerificationAttemptRequest();
        final IdentityVerificationAttemptResponse response = createIdentityVerificationAttemptResponse();

        when(apiClient.post(eq("identity-verifications/" + identityVerificationId + "/attempts"), eq(authorization), 
                eq(IdentityVerificationAttemptResponse.class), eq(request), isNull()))
                .thenReturn(response);

        final IdentityVerificationAttemptResponse result = client.createIdentityVerificationAttempt(identityVerificationId, request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetIdentityVerificationAttempt() {
        final String identityVerificationId = "idv_test_123456789";
        final String attemptId = "idva_test_987654321";
        final IdentityVerificationAttemptResponse response = createIdentityVerificationAttemptResponse();

        when(apiClient.get(eq("identity-verifications/" + identityVerificationId + "/attempts/" + attemptId), 
                eq(authorization), eq(IdentityVerificationAttemptResponse.class)))
                .thenReturn(response);

        final IdentityVerificationAttemptResponse result = client.getIdentityVerificationAttempt(identityVerificationId, attemptId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGenerateIdentityVerificationReport() {
        final String identityVerificationId = "idv_test_123456789";
        final IdentityVerificationReportResponse response = createIdentityVerificationReportResponse();

        when(apiClient.get(eq("identity-verifications/" + identityVerificationId + "/pdf-report"), eq(authorization), 
                eq(IdentityVerificationReportResponse.class)))
                .thenReturn(response);

        final IdentityVerificationReportResponse result = client.generateIdentityVerificationReport(identityVerificationId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    // Common methods

    private CreateAndOpenIdentityVerificationRequest createCreateAndOpenIdentityVerificationRequest() {
        return mock(CreateAndOpenIdentityVerificationRequest.class);
    }

    private IdentityVerificationRequest createIdentityVerificationRequest() {
        return mock(IdentityVerificationRequest.class);
    }

    private IdentityVerificationAttemptRequest createIdentityVerificationAttemptRequest() {
        return mock(IdentityVerificationAttemptRequest.class);
    }

    private IdentityVerificationResponse createIdentityVerificationResponse() {
        return mock(IdentityVerificationResponse.class);
    }

    private IdentityVerificationAttemptResponse createIdentityVerificationAttemptResponse() {
        return mock(IdentityVerificationAttemptResponse.class);
    }

    private IdentityVerificationAttemptsResponse createIdentityVerificationAttemptsResponse() {
        return mock(IdentityVerificationAttemptsResponse.class);
    }

    private IdentityVerificationReportResponse createIdentityVerificationReportResponse() {
        return mock(IdentityVerificationReportResponse.class);
    }
}