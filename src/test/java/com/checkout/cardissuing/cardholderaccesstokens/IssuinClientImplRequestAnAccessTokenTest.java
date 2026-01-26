package com.checkout.cardissuing.cardholderaccesstokens;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.cardissuing.cardholderaccesstokens.requests.RequestAnAccessTokenRequest;
import com.checkout.cardissuing.cardholderaccesstokens.responses.RequestAnAccessTokenResponse;
import com.checkout.issuing.IssuingClient;
import com.checkout.issuing.IssuingClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class IssuinClientImplRequestAnAccessTokenTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private IssuingClient client;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new IssuingClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRequestAccessToken() throws ExecutionException, InterruptedException {
        RequestAnAccessTokenRequest request = mock(RequestAnAccessTokenRequest.class);
        RequestAnAccessTokenResponse response = mock(RequestAnAccessTokenResponse.class);

        when(apiClient.postAsync(
                "issuing/cardholder-access/token",
                authorization,
                RequestAnAccessTokenResponse.class,
                request,
                null
        )).thenReturn(CompletableFuture.completedFuture(response));

        CompletableFuture<RequestAnAccessTokenResponse> future = client.RequestAnAccessToken(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }
}
