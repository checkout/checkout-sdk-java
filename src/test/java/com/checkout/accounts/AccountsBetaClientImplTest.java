package com.checkout.accounts;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.accounts.payout.schedule.request.UpdateScheduleRequest;
import com.checkout.accounts.payout.schedule.response.GetScheduleResponse;
import com.checkout.accounts.payout.schedule.response.VoidResponse;
import com.checkout.common.Currency;
import com.checkout.common.IdResponse;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountsBetaClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private AccountsClient accountsBetaClient;

    @BeforeEach
    void setUp() {
        lenient().when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        lenient().when(checkoutConfiguration.getSdkCredentials()).thenReturn(sdkCredentials);
        lenient().when(checkoutConfiguration.getHttpClientBuilder()).thenReturn(HttpClientBuilder.create());
        lenient().when(checkoutConfiguration.getExecutor()).thenReturn(Executors.newSingleThreadExecutor());
        this.accountsBetaClient = new AccountsBetaClientImpl(apiClient, apiClient, checkoutConfiguration);
    }

    @Test
    void shouldCreateEntity() throws ExecutionException, InterruptedException {
        final OnboardEntityResponse response = mock(OnboardEntityResponse.class);

        when(apiClient.postAsync(eq("accounts-beta/entities"), eq(authorization), eq(OnboardEntityResponse.class), any(OnboardEntityRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<OnboardEntityResponse> future = accountsBetaClient.createEntity(OnboardEntityRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetEntity() throws ExecutionException, InterruptedException {

        final OnboardEntityDetailsResponse response = mock(OnboardEntityDetailsResponse.class);

        when(apiClient.getAsync("accounts-beta/entities/entity_id", authorization, OnboardEntityDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<OnboardEntityDetailsResponse> future = accountsBetaClient.getEntity("entity_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldUpdateEntity() throws ExecutionException, InterruptedException {

        final OnboardEntityResponse response = mock(OnboardEntityResponse.class);

        when(apiClient.putAsync(eq("accounts-beta/entities/entity_id"), eq(authorization), eq(OnboardEntityResponse.class), any(OnboardEntityRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<OnboardEntityResponse> future = accountsBetaClient.updateEntity(OnboardEntityRequest.builder().build(), "entity_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

}
