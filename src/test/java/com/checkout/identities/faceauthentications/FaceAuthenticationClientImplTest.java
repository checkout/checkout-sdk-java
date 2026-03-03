package com.checkout.identities.faceauthentications;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.identities.faceauthentications.requests.FaceAuthenticationAttemptRequest;
import com.checkout.identities.faceauthentications.requests.FaceAuthenticationRequest;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationAttemptResponse;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationAttemptsResponse;
import com.checkout.identities.faceauthentications.responses.FaceAuthenticationResponse;
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
class FaceAuthenticationClientImplTest {

    private FaceAuthenticationClient client;

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
        client = new FaceAuthenticationClientImpl(apiClient, configuration);
    }

    @Test
    void shouldCreateFaceAuthentication() throws ExecutionException, InterruptedException {
        final FaceAuthenticationRequest request = mock(FaceAuthenticationRequest.class);
        final FaceAuthenticationResponse response = mock(FaceAuthenticationResponse.class);

        when(apiClient.postAsync(eq("face-authentications"), eq(authorization), eq(FaceAuthenticationResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<FaceAuthenticationResponse> future = client.createFaceAuthentication(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetFaceAuthentication() throws ExecutionException, InterruptedException {
        final String faceAuthenticationId = "fav_test_123456789";
        final FaceAuthenticationResponse response = mock(FaceAuthenticationResponse.class);

        when(apiClient.getAsync(eq("face-authentications/" + faceAuthenticationId), eq(authorization), eq(FaceAuthenticationResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<FaceAuthenticationResponse> future = client.getFaceAuthentication(faceAuthenticationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldAnonymizeFaceAuthentication() throws ExecutionException, InterruptedException {
        final String faceAuthenticationId = "fav_test_123456789";
        final FaceAuthenticationResponse response = mock(FaceAuthenticationResponse.class);

        when(apiClient.postAsync(eq("face-authentications/" + faceAuthenticationId + "/anonymize"), eq(authorization), 
                eq(FaceAuthenticationResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<FaceAuthenticationResponse> future = client.anonymizeFaceAuthentication(faceAuthenticationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldCreateFaceAuthenticationAttempt() throws ExecutionException, InterruptedException {
        final String faceAuthenticationId = "fav_test_123456789";
        final FaceAuthenticationAttemptRequest request = mock(FaceAuthenticationAttemptRequest.class);
        final FaceAuthenticationAttemptResponse response = mock(FaceAuthenticationAttemptResponse.class);

        when(apiClient.postAsync(eq("face-authentications/" + faceAuthenticationId + "/attempts"), eq(authorization), 
                eq(FaceAuthenticationAttemptResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<FaceAuthenticationAttemptResponse> future = 
                client.createFaceAuthenticationAttempt(faceAuthenticationId, request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetFaceAuthenticationAttempts() throws ExecutionException, InterruptedException {
        final String faceAuthenticationId = "fav_test_123456789";
        final FaceAuthenticationAttemptsResponse response = mock(FaceAuthenticationAttemptsResponse.class);

        when(apiClient.getAsync(eq("face-authentications/" + faceAuthenticationId + "/attempts"), eq(authorization), 
                eq(FaceAuthenticationAttemptsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<FaceAuthenticationAttemptsResponse> future = 
                client.getFaceAuthenticationAttempts(faceAuthenticationId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetFaceAuthenticationAttempt() throws ExecutionException, InterruptedException {
        final String faceAuthenticationId = "fav_test_123456789";
        final String attemptId = "fatp_test_987654321";
        final FaceAuthenticationAttemptResponse response = mock(FaceAuthenticationAttemptResponse.class);

        when(apiClient.getAsync(eq("face-authentications/" + faceAuthenticationId + "/attempts/" + attemptId), 
                eq(authorization), eq(FaceAuthenticationAttemptResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<FaceAuthenticationAttemptResponse> future = 
                client.getFaceAuthenticationAttempt(faceAuthenticationId, attemptId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    // Synchronous methods tests

    @Test
    void shouldCreateFaceAuthenticationSync() {
        final FaceAuthenticationRequest request = mock(FaceAuthenticationRequest.class);
        final FaceAuthenticationResponse response = mock(FaceAuthenticationResponse.class);

        when(apiClient.post(eq("face-authentications"), eq(authorization), eq(FaceAuthenticationResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final FaceAuthenticationResponse result = client.createFaceAuthenticationSync(request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetFaceAuthenticationSync() {
        final String faceAuthenticationId = "fav_test_123456789";
        final FaceAuthenticationResponse response = mock(FaceAuthenticationResponse.class);

        when(apiClient.get(eq("face-authentications/" + faceAuthenticationId), eq(authorization), eq(FaceAuthenticationResponse.class)))
                .thenReturn(response);

        final FaceAuthenticationResponse result = client.getFaceAuthenticationSync(faceAuthenticationId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldAnonymizeFaceAuthenticationSync() {
        final String faceAuthenticationId = "fav_test_123456789";
        final FaceAuthenticationResponse response = mock(FaceAuthenticationResponse.class);

        when(apiClient.post(eq("face-authentications/" + faceAuthenticationId + "/anonymize"), eq(authorization), 
                eq(FaceAuthenticationResponse.class), isNull(), isNull()))
                .thenReturn(response);

        final FaceAuthenticationResponse result = client.anonymizeFaceAuthenticationSync(faceAuthenticationId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldCreateFaceAuthenticationAttemptSync() {
        final String faceAuthenticationId = "fav_test_123456789";
        final FaceAuthenticationAttemptRequest request = mock(FaceAuthenticationAttemptRequest.class);
        final FaceAuthenticationAttemptResponse response = mock(FaceAuthenticationAttemptResponse.class);

        when(apiClient.post(eq("face-authentications/" + faceAuthenticationId + "/attempts"), eq(authorization), 
                eq(FaceAuthenticationAttemptResponse.class), eq(request), isNull()))
                .thenReturn(response);

        final FaceAuthenticationAttemptResponse result = 
                client.createFaceAuthenticationAttemptSync(faceAuthenticationId, request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetFaceAuthenticationAttemptsSync() {
        final String faceAuthenticationId = "fav_test_123456789";
        final FaceAuthenticationAttemptsResponse response = mock(FaceAuthenticationAttemptsResponse.class);

        when(apiClient.get(eq("face-authentications/" + faceAuthenticationId + "/attempts"), eq(authorization), 
                eq(FaceAuthenticationAttemptsResponse.class)))
                .thenReturn(response);

        final FaceAuthenticationAttemptsResponse result = 
                client.getFaceAuthenticationAttemptsSync(faceAuthenticationId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetFaceAuthenticationAttemptSync() {
        final String faceAuthenticationId = "fav_test_123456789";
        final String attemptId = "fatp_test_987654321";
        final FaceAuthenticationAttemptResponse response = mock(FaceAuthenticationAttemptResponse.class);

        when(apiClient.get(eq("face-authentications/" + faceAuthenticationId + "/attempts/" + attemptId), 
                eq(authorization), eq(FaceAuthenticationAttemptResponse.class)))
                .thenReturn(response);

        final FaceAuthenticationAttemptResponse result = 
                client.getFaceAuthenticationAttemptSync(faceAuthenticationId, attemptId);

        assertNotNull(result);
        assertEquals(response, result);
    }
}