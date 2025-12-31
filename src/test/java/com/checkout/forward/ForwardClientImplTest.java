package com.checkout.forward;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.forward.requests.ForwardRequest;
import com.checkout.forward.responses.ForwardAnApiResponse;
import com.checkout.forward.responses.GetForwardResponse;
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
public class ForwardClientImplTest {

    private ForwardClient client;

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
        client = new ForwardClientImpl(apiClient, configuration);
    }

    @Test
    void shouldForwardAnApiRequest() throws ExecutionException, InterruptedException {

        final ForwardRequest request = mock(ForwardRequest.class);
        final ForwardAnApiResponse response = mock(ForwardAnApiResponse.class);

        when(apiClient.postAsync(eq("forward"), eq(authorization), eq(ForwardAnApiResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ForwardAnApiResponse> future = client.forwardAnApiRequest(request);

        validateForwardAnApiResponse(response, future.get());
    }

    @Test
    void shouldGetForwardRequest() throws ExecutionException, InterruptedException {
        final String forwardId = "forward_id";
        final GetForwardResponse response = mock(GetForwardResponse.class);

        when(apiClient.getAsync(eq("forward/" + forwardId), eq(authorization), eq(GetForwardResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<GetForwardResponse> future = client.getForwardRequest(forwardId);

        validateForwardResponse(response, future.get());
    }

    // Synchronous methods
    @Test
    void shouldForwardAnApiRequestSync() throws ExecutionException, InterruptedException {

        final ForwardRequest request = mock(ForwardRequest.class);
        final ForwardAnApiResponse response = mock(ForwardAnApiResponse.class);

        when(apiClient.post(eq("forward"), eq(authorization), eq(ForwardAnApiResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final ForwardAnApiResponse result = client.forwardAnApiRequestSync(request);

        validateForwardAnApiResponse(response, result);
    }

    @Test
    void shouldGetForwardRequestSync() throws ExecutionException, InterruptedException {
        final String forwardId = "forward_id";
        final GetForwardResponse response = mock(GetForwardResponse.class);

        when(apiClient.get(eq("forward/" + forwardId), eq(authorization), eq(GetForwardResponse.class)))
                .thenReturn(response);

        final GetForwardResponse result = client.getForwardRequestSync(forwardId);

        validateForwardResponse(response, result);
    }

    // Common methods
    private void validateForwardAnApiResponse(final ForwardAnApiResponse response, final ForwardAnApiResponse result) {
        assertNotNull(result);
        assertEquals(response, result);
    }

    private void validateForwardResponse(final GetForwardResponse response, final GetForwardResponse result) {
        assertNotNull(result);
        assertEquals(response, result);
    }

}
