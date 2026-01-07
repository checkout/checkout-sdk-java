package com.checkout.sources.previous;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.checkout.ApiClient;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;

@ExtendWith(MockitoExtension.class)
class SourcesClientImplTest {

    private SourcesClient client;

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
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new SourcesClientImpl(apiClient, configuration);
    }

    @Test
    void shouldThrowException_whenRequestIsNull() {
        try {
            client.createSepaSource(null);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("sepaSourceRequest cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldCreateSepaSource() throws ExecutionException, InterruptedException {
        final SepaSourceRequest request = createSepaSourceRequest();
        final SepaSourceResponse expectedResponse = createSepaSourceResponse();

        when(apiClient.postAsync(eq("sources"), eq(authorization), eq(SepaSourceResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<SepaSourceResponse> future = client.createSepaSource(request);
        final SepaSourceResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    // Synchronous methods
    @Test
    void shouldCreateSepaSourceSync() {
        final SepaSourceRequest request = createSepaSourceRequest();
        final SepaSourceResponse expectedResponse = createSepaSourceResponse();

        when(apiClient.post(eq("sources"), eq(authorization), eq(SepaSourceResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final SepaSourceResponse actualResponse = client.createSepaSourceSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    // Common methods
    private SepaSourceRequest createSepaSourceRequest() {
        return mock(SepaSourceRequest.class);
    }

    private SepaSourceResponse createSepaSourceResponse() {
        return mock(SepaSourceResponse.class);
    }
    
    private void validateResponse(final SepaSourceResponse expectedResponse, final SepaSourceResponse actualResponse) {
        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse);
    }

}
