package com.checkout.issuing;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.issuing.cardholders.CardholderDetailsResponse;
import com.checkout.issuing.cardholders.CardholderRequest;
import com.checkout.issuing.cardholders.CardholderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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
public class IssuingClientImplTest {

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

    @Nested
    @DisplayName("Cardholders")
    class Cardholders {
        @Test
        void shouldCreateCardholder() throws ExecutionException, InterruptedException {
            final CardholderRequest request = mock(CardholderRequest.class);
            final CardholderResponse response = mock(CardholderResponse.class);

            when(apiClient.postAsync(
                    "issuing/cardholders",
                    authorization,
                    CardholderResponse.class,
                    request,
                    null
            )).thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardholderResponse> future = client.createCardholder(request);

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldGetCardholderDetails() throws ExecutionException, InterruptedException {
            final CardholderDetailsResponse response = mock(CardholderDetailsResponse.class);

            when(apiClient.getAsync(
                    "issuing/cardholders/cardholder_id",
                    authorization,
                    CardholderDetailsResponse.class))
            .thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardholderDetailsResponse> future = client.getCardholder("cardholder_id");

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }

        @Test
        void shouldGetCardholderCards() throws ExecutionException, InterruptedException {
            final CardholderDetailsResponse response = mock(CardholderDetailsResponse.class);

            when(apiClient.getAsync(
                    "issuing/cardholders/cardholder_id",
                    authorization,
                    CardholderDetailsResponse.class))
                    .thenReturn(CompletableFuture.completedFuture(response));

            final CompletableFuture<CardholderDetailsResponse> future = client.getCardholder("cardholder_id");

            assertNotNull(future.get());
            assertEquals(response, future.get());
        }
    }
}
