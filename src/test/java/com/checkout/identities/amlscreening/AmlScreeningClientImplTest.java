package com.checkout.identities.amlscreening;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.identities.amlscreening.requests.AmlScreeningRequest;
import com.checkout.identities.amlscreening.responses.AmlScreeningResponse;
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
class AmlScreeningClientImplTest {

    private AmlScreeningClient client;

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
        client = new AmlScreeningClientImpl(apiClient, configuration);
    }

    // Async methods

    @Test
    void shouldCreateAmlScreening() throws ExecutionException, InterruptedException {
        final AmlScreeningRequest request = createAmlScreeningRequest();
        final AmlScreeningResponse response = createAmlScreeningResponse();

        when(apiClient.postAsync(eq("aml-verifications"), eq(authorization), eq(AmlScreeningResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<AmlScreeningResponse> future = client.createAmlScreeningAsync(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetAmlScreening() throws ExecutionException, InterruptedException {
        final String amlScreeningId = "amlv_test_123456789";
        final AmlScreeningResponse response = createAmlScreeningResponse();

        when(apiClient.getAsync("aml-verifications/" + amlScreeningId, authorization, 
                AmlScreeningResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<AmlScreeningResponse> future = client.getAmlScreeningAsync(amlScreeningId);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    // Sync methods
    @Test
    void shouldCreateAmlScreeningSync() {
        final AmlScreeningRequest request = createAmlScreeningRequest();
        final AmlScreeningResponse response = createAmlScreeningResponse();

        when(apiClient.post(eq("aml-verifications"), eq(authorization), eq(AmlScreeningResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final AmlScreeningResponse result = client.createAmlScreening(request);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldGetAmlScreeningSync() {
        final String amlScreeningId = "amlv_test_123456789";
        final AmlScreeningResponse response = createAmlScreeningResponse();

        when(apiClient.get("aml-verifications/" + amlScreeningId, authorization, 
                AmlScreeningResponse.class))
                .thenReturn(response);

        final AmlScreeningResponse result = client.getAmlScreening(amlScreeningId);

        assertNotNull(result);
        assertEquals(response, result);
    }

    // Common methods
    private AmlScreeningRequest createAmlScreeningRequest() {
        return mock(AmlScreeningRequest.class);
    }

    private AmlScreeningResponse createAmlScreeningResponse() {
        return mock(AmlScreeningResponse.class);
    }
}