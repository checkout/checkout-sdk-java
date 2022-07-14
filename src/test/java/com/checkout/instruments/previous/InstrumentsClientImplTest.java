package com.checkout.instruments.previous;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstrumentsClientImplTest {

    private InstrumentsClientImpl client;

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
        client = new InstrumentsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldCreateInstrument() throws ExecutionException, InterruptedException {

        final CreateInstrumentRequest request = mock(CreateInstrumentRequest.class);
        final CreateInstrumentResponse response = mock(CreateInstrumentResponse.class);

        when(apiClient.postAsync(eq("instruments"), eq(authorization), eq(CreateInstrumentResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CreateInstrumentResponse> future = client.create(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetInstrument() throws ExecutionException, InterruptedException {

        final InstrumentDetailsResponse response = mock(InstrumentDetailsResponse.class);
        when(apiClient.getAsync("instruments/src_wmlfc3zyhqzehihu7giusaaawu", authorization,
                InstrumentDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<InstrumentDetailsResponse> future = client.get("src_wmlfc3zyhqzehihu7giusaaawu");

        assertNotNull(future.get());

    }

    @Test
    void shouldUpdateInstrument() throws ExecutionException, InterruptedException {

        final UpdateInstrumentRequest request = mock(UpdateInstrumentRequest.class);
        final UpdateInstrumentResponse response = mock(UpdateInstrumentResponse.class);

        when(apiClient.patchAsync(eq("instruments/src_wmlfc3zyhqzehihu7giusaaawu123"), eq(authorization),
                eq(UpdateInstrumentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<UpdateInstrumentResponse> future = client.update("src_wmlfc3zyhqzehihu7giusaaawu123", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldDeleteInstrument() throws ExecutionException, InterruptedException {

        final EmptyResponse response = mock(EmptyResponse.class);

        when(apiClient.deleteAsync("instruments/UpdateInstrumentResponse", authorization))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = client.delete("UpdateInstrumentResponse");

        assertNotNull(future.get());

    }

}