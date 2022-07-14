package com.checkout.apm.ideal;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdealClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @Mock
    private IssuerResponse issuerResponse;

    private IdealClient idealClient;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        this.idealClient = new IdealClientImpl(apiClient, configuration);
    }

    @Test
    void shouldGetIssuers() throws ExecutionException, InterruptedException {

        when(apiClient.getAsync("/ideal-external/issuers", authorization, IssuerResponse.class))
                .thenReturn(CompletableFuture.completedFuture(issuerResponse));

        final CompletableFuture<IssuerResponse> future = idealClient.getIssuers();

        assertNotNull(future.get());
        assertEquals(issuerResponse, future.get());

    }

}