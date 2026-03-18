package com.checkout.standaloneaccountupdater;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.standaloneaccountupdater.entities.CardDetails;
import com.checkout.standaloneaccountupdater.entities.InstrumentReference;
import com.checkout.standaloneaccountupdater.entities.SourceOptions;
import com.checkout.standaloneaccountupdater.requests.GetUpdatedCardCredentialsRequest;
import com.checkout.standaloneaccountupdater.responses.GetUpdatedCardCredentialsResponse;
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
public class StandaloneAccountUpdaterClientImplTest {

    private StandaloneAccountUpdaterClient client;

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
        client = new StandaloneAccountUpdaterClientImpl(apiClient, configuration);
    }

    @Test
    void shouldGetUpdatedCardCredentials() throws ExecutionException, InterruptedException {

        final GetUpdatedCardCredentialsRequest request = createGetUpdatedCardCredentialsRequestWithCard();
        final GetUpdatedCardCredentialsResponse response = mock(GetUpdatedCardCredentialsResponse.class);

        when(apiClient.postAsync(eq("account-updater/cards"), eq(authorization), eq(GetUpdatedCardCredentialsResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<GetUpdatedCardCredentialsResponse> future = client.getUpdatedCardCredentials(request);

        validateGetUpdatedCardCredentialsResponse(response, future.get());
    }

    // Synchronous methods
    @Test
    void shouldGetUpdatedCardCredentialsSync() {

        final GetUpdatedCardCredentialsRequest request = createGetUpdatedCardCredentialsRequestWithCard();
        final GetUpdatedCardCredentialsResponse response = mock(GetUpdatedCardCredentialsResponse.class);

        when(apiClient.post(eq("account-updater/cards"), eq(authorization), eq(GetUpdatedCardCredentialsResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final GetUpdatedCardCredentialsResponse result = client.getUpdatedCardCredentialsSync(request);

        validateGetUpdatedCardCredentialsResponse(response, result);
    }

    // Common methods
    private void validateGetUpdatedCardCredentialsResponse(final GetUpdatedCardCredentialsResponse response, final GetUpdatedCardCredentialsResponse result) {
        assertNotNull(result);
        assertEquals(response, result);
    }

    private GetUpdatedCardCredentialsRequest createGetUpdatedCardCredentialsRequestWithCard() {
        final CardDetails cardDetails = CardDetails.builder()
                .number("5436424242424242")
                .expiryMonth(5)
                .expiryYear(2025)
                .build();

        final SourceOptions sourceOptions = SourceOptions.builder()
                .card(cardDetails)
                .build();

        return GetUpdatedCardCredentialsRequest.builder()
                .sourceOptions(sourceOptions)
                .build();
    }

    private GetUpdatedCardCredentialsRequest createGetUpdatedCardCredentialsRequestWithInstrument() {
        final InstrumentReference instrumentReference = InstrumentReference.builder()
                .id("src_nmukohhu7vbe5f55ndwqzwv2c4")
                .build();

        final SourceOptions sourceOptions = SourceOptions.builder()
                .instrument(instrumentReference)
                .build();

        return GetUpdatedCardCredentialsRequest.builder()
                .sourceOptions(sourceOptions)
                .build();
    }
}