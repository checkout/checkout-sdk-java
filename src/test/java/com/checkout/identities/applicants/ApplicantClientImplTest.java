package com.checkout.identities.applicants;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.identities.applicants.requests.ApplicantRequest;
import com.checkout.identities.applicants.requests.ApplicantUpdateRequest;
import com.checkout.identities.applicants.responses.ApplicantResponse;
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
class ApplicantClientImplTest {

    private ApplicantClient client;

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
        client = new ApplicantClientImpl(apiClient, configuration);
    }

    @Test
    void shouldCreateApplicant() throws ExecutionException, InterruptedException {
        final ApplicantRequest request = mock(ApplicantRequest.class);
        final ApplicantResponse response = mock(ApplicantResponse.class);

        when(apiClient.postAsync("applicants", authorization, ApplicantResponse.class,
                request, null))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ApplicantResponse> future = client.createApplicant(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetApplicant() throws ExecutionException, InterruptedException {
        final String applicantId = "aplt_test_123456789";
        final ApplicantResponse response = mock(ApplicantResponse.class);

        when(apiClient.getAsync("applicants/" + applicantId, authorization, ApplicantResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ApplicantResponse> future = client.getApplicant(applicantId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldUpdateApplicant() throws ExecutionException, InterruptedException {
        final String applicantId = "aplt_test_123456789";
        final ApplicantUpdateRequest request = mock(ApplicantUpdateRequest.class);
        final ApplicantResponse response = mock(ApplicantResponse.class);

        when(apiClient.patchAsync("applicants/" + applicantId, authorization, 
                ApplicantResponse.class, request, null))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ApplicantResponse> future = client.updateApplicant(applicantId, request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldAnonymizeApplicant() throws ExecutionException, InterruptedException {
        final String applicantId = "aplt_test_123456789";
        final ApplicantResponse response = mock(ApplicantResponse.class);

        when(apiClient.postAsync("applicants/" + applicantId + "/anonymize", authorization, 
                ApplicantResponse.class, null, null))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ApplicantResponse> future = client.anonymizeApplicant(applicantId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    // Synchronous methods tests
    @Test
    void shouldCreateApplicantSync() {
        final ApplicantRequest request = mock(ApplicantRequest.class);
        final ApplicantResponse response = mock(ApplicantResponse.class);

        when(apiClient.post("applicants", authorization, ApplicantResponse.class,
                request, null))
                .thenReturn(response);

        final ApplicantResponse result = client.createApplicantSync(request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetApplicantSync() {
        final String applicantId = "aplt_test_123456789";
        final ApplicantResponse response = mock(ApplicantResponse.class);

        when(apiClient.get("applicants/" + applicantId, authorization, ApplicantResponse.class))
                .thenReturn(response);

        final ApplicantResponse result = client.getApplicantSync(applicantId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldUpdateApplicantSync() {
        final String applicantId = "aplt_test_123456789";
        final ApplicantUpdateRequest request = mock(ApplicantUpdateRequest.class);
        final ApplicantResponse response = mock(ApplicantResponse.class);

        when(apiClient.patch("applicants/" + applicantId, authorization, 
                ApplicantResponse.class, request, null))
                .thenReturn(response);

        final ApplicantResponse result = client.updateApplicantSync(applicantId, request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldAnonymizeApplicantSync() {
        final String applicantId = "aplt_test_123456789";
        final ApplicantResponse response = mock(ApplicantResponse.class);

        when(apiClient.post("applicants/" + applicantId + "/anonymize", authorization, 
                ApplicantResponse.class, null, null))
                .thenReturn(response);

        final ApplicantResponse result = client.anonymizeApplicantSync(applicantId);

        assertNotNull(result);
        assertEquals(response, result);
    }
}