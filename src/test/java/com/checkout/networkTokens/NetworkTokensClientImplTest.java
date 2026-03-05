package com.checkout.networkTokens;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.networkTokens.entities.DeletionReason;
import com.checkout.networkTokens.entities.InitiatedBy;
import com.checkout.networkTokens.entities.TransactionType;
import com.checkout.networkTokens.requests.DeleteNetworkTokenRequest;
import com.checkout.networkTokens.requests.ProvisionNetworkTokenRequest;
import com.checkout.networkTokens.requests.RequestCryptogramRequest;
import com.checkout.networkTokens.requests.sources.IdNetworkTokenSource;
import com.checkout.networkTokens.responses.CryptogramResponse;
import com.checkout.networkTokens.responses.NetworkTokenResponse;
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
public class NetworkTokensClientImplTest {

    private NetworkTokensClient client;

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
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new NetworkTokensClientImpl(apiClient, configuration);
    }

    @Test
    void shouldProvisionNetworkToken() throws ExecutionException, InterruptedException {
        final ProvisionNetworkTokenRequest request = createProvisionNetworkTokenRequest();
        final NetworkTokenResponse response = mock(NetworkTokenResponse.class);

        when(apiClient.postAsync(eq("network-tokens"), eq(authorization), eq(NetworkTokenResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<NetworkTokenResponse> future = client.provisionNetworkToken(request);

        validateNetworkTokenResponse(response, future.get());
    }

    @Test
    void shouldGetNetworkToken() throws ExecutionException, InterruptedException {
        final String networkTokenId = "nt_xgu3isllqfyu7ktpk5z2yxbwna";
        final NetworkTokenResponse response = mock(NetworkTokenResponse.class);

        when(apiClient.getAsync(eq("network-tokens/" + networkTokenId), eq(authorization), eq(NetworkTokenResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<NetworkTokenResponse> future = client.getNetworkToken(networkTokenId);

        validateNetworkTokenResponse(response, future.get());
    }

    @Test
    void shouldRequestCryptogram() throws ExecutionException, InterruptedException {
        final String networkTokenId = "nt_xgu3isllqfyu7ktpk5z2yxbwna";
        final RequestCryptogramRequest request = createRequestCryptogramRequest();
        final CryptogramResponse response = mock(CryptogramResponse.class);

        when(apiClient.postAsync(eq("network-tokens/" + networkTokenId + "/cryptograms"), eq(authorization), eq(CryptogramResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CryptogramResponse> future = client.requestCryptogram(networkTokenId, request);

        validateCryptogramResponse(response, future.get());
    }

    @Test
    void shouldDeleteNetworkToken() throws ExecutionException, InterruptedException {
        final String networkTokenId = "nt_xgu3isllqfyu7ktpk5z2yxbwna";
        final DeleteNetworkTokenRequest request = createDeleteNetworkTokenRequest();
        final EmptyResponse response = mock(EmptyResponse.class);

        when(apiClient.patchAsync(eq("network-tokens/" + networkTokenId + "/delete"), eq(authorization), eq(EmptyResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = client.deleteNetworkToken(networkTokenId, request);

        validateEmptyResponse(response, future.get());
    }

    // Synchronous methods
    @Test
    void shouldProvisionNetworkTokenSync() throws ExecutionException, InterruptedException {
        final ProvisionNetworkTokenRequest request = createProvisionNetworkTokenRequest();
        final NetworkTokenResponse response = mock(NetworkTokenResponse.class);

        when(apiClient.post(eq("network-tokens"), eq(authorization), eq(NetworkTokenResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final NetworkTokenResponse result = client.provisionNetworkTokenSync(request);

        validateNetworkTokenResponse(response, result);
    }

    @Test
    void shouldGetNetworkTokenSync() throws ExecutionException, InterruptedException {
        final String networkTokenId = "nt_xgu3isllqfyu7ktpk5z2yxbwna";
        final NetworkTokenResponse response = mock(NetworkTokenResponse.class);

        when(apiClient.get(eq("network-tokens/" + networkTokenId), eq(authorization), eq(NetworkTokenResponse.class)))
                .thenReturn(response);

        final NetworkTokenResponse result = client.getNetworkTokenSync(networkTokenId);

        validateNetworkTokenResponse(response, result);
    }

    @Test
    void shouldRequestCryptogramSync() throws ExecutionException, InterruptedException {
        final String networkTokenId = "nt_xgu3isllqfyu7ktpk5z2yxbwna";
        final RequestCryptogramRequest request = createRequestCryptogramRequest();
        final CryptogramResponse response = mock(CryptogramResponse.class);

        when(apiClient.post(eq("network-tokens/" + networkTokenId + "/cryptograms"), eq(authorization), eq(CryptogramResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final CryptogramResponse result = client.requestCryptogramSync(networkTokenId, request);

        validateCryptogramResponse(response, result);
    }

    @Test
    void shouldDeleteNetworkTokenSync() throws ExecutionException, InterruptedException {
        final String networkTokenId = "nt_xgu3isllqfyu7ktpk5z2yxbwna";
        final DeleteNetworkTokenRequest request = createDeleteNetworkTokenRequest();
        final EmptyResponse response = mock(EmptyResponse.class);

        when(apiClient.patch(eq("network-tokens/" + networkTokenId + "/delete"), eq(authorization), eq(EmptyResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final EmptyResponse result = client.deleteNetworkTokenSync(networkTokenId, request);

        validateEmptyResponse(response, result);
    }

    // Common methods
    private ProvisionNetworkTokenRequest createProvisionNetworkTokenRequest() {
        final IdNetworkTokenSource source = IdNetworkTokenSource.builder()
                .id("src_wmlfc3zyhqzehihu7giusaaawu")
                .build();

        return ProvisionNetworkTokenRequest.builder()
                .source(source)
                .build();
    }

    private RequestCryptogramRequest createRequestCryptogramRequest() {
        return RequestCryptogramRequest.builder()
                .transactionType(TransactionType.ECOM)
                .build();
    }

    private DeleteNetworkTokenRequest createDeleteNetworkTokenRequest() {
        return DeleteNetworkTokenRequest.builder()
                .initiatedBy(InitiatedBy.TOKEN_REQUESTOR)
                .reason(DeletionReason.OTHER)
                .build();
    }

    private void validateNetworkTokenResponse(final NetworkTokenResponse response, final NetworkTokenResponse result) {
        assertNotNull(result);
        assertEquals(response, result);
    }

    private void validateCryptogramResponse(final CryptogramResponse response, final CryptogramResponse result) {
        assertNotNull(result);
        assertEquals(response, result);
    }

    private void validateEmptyResponse(final EmptyResponse response, final EmptyResponse result) {
        assertNotNull(result);
        assertEquals(response, result);
    }

}