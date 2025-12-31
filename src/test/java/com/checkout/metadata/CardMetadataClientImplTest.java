package com.checkout.metadata;

import com.checkout.*;
import com.checkout.metadata.card.CardMetadataRequest;
import com.checkout.metadata.card.CardMetadataResponse;
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
class CardMetadataClientImplTest {

    private MetadataClient client;

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
        client = new MetadataClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRequestCardMetadata() throws ExecutionException, InterruptedException {

        final CardMetadataRequest request = mock(CardMetadataRequest.class);
        final CardMetadataResponse response = mock(CardMetadataResponse.class);

        when(apiClient.postAsync(eq("metadata/card"), eq(authorization), eq(CardMetadataResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CardMetadataResponse> future = client.requestCardMetadata(request);

        validateCardMetadataResponse(response, future.get());
    }

    // Synchronous methods
    @Test
    void shouldRequestCardMetadataSync() throws ExecutionException, InterruptedException {

        final CardMetadataRequest request = mock(CardMetadataRequest.class);
        final CardMetadataResponse response = mock(CardMetadataResponse.class);

        when(apiClient.post(eq("metadata/card"), eq(authorization), eq(CardMetadataResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final CardMetadataResponse result = client.requestCardMetadataSync(request);

        validateCardMetadataResponse(response, result);
    }

    // Common methods
    private void validateCardMetadataResponse(final CardMetadataResponse response, final CardMetadataResponse result) {
        assertNotNull(result);
        assertEquals(response, result);
    }
}
