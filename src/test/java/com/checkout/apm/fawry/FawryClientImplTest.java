package com.checkout.apm.fawry;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FawryClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @Mock
    private EmptyResponse emptyResponse;

    private FawryClient fawryClient;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        this.fawryClient = new FawryClientImpl(apiClient, configuration);
    }

    @Test
    void shouldApprovePayment() throws ExecutionException, InterruptedException {

        when(apiClient.putAsync(eq("fawry/payments/reference/approval"), eq(authorization), eq(EmptyResponse.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(emptyResponse));

        final CompletableFuture<EmptyResponse> future = fawryClient.approve("reference");

        assertNotNull(future.get());
        assertEquals(emptyResponse, future.get());

    }

    @Test
    void shouldCancelPayment() throws ExecutionException, InterruptedException {

        when(apiClient.putAsync(eq("fawry/payments/reference/cancellation"), eq(authorization), eq(EmptyResponse.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(emptyResponse));

        final CompletableFuture<EmptyResponse> future = fawryClient.cancel("reference");

        assertNotNull(future.get());
        assertEquals(emptyResponse, future.get());

    }

}
