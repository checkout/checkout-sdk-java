package com.checkout.instruments;

import com.checkout.ApiClient;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.CheckoutConfiguration;
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

    private static final String INSTRUMENTS = "instruments";

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

        when(apiClient.postAsync(eq(INSTRUMENTS), eq(authorization), eq(CreateInstrumentResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CreateInstrumentResponse> future = client.createInstrument(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetInstrument() throws ExecutionException, InterruptedException {

        final InstrumentDetailsResponse response = mock(InstrumentDetailsResponse.class);
        when(apiClient.getAsync(eq(INSTRUMENTS + "/src_wmlfc3zyhqzehihu7giusaaawu"), eq(authorization),
                eq(InstrumentDetailsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<InstrumentDetailsResponse> future = client.getInstrument("src_wmlfc3zyhqzehihu7giusaaawu");

        assertNotNull(future.get());

    }

    @Test
    void shouldUpdateInstrument() throws ExecutionException, InterruptedException {

        final UpdateInstrumentRequest request = mock(UpdateInstrumentRequest.class);
        final UpdateInstrumentResponse response = mock(UpdateInstrumentResponse.class);

        when(apiClient.patchAsync(eq(INSTRUMENTS + "/src_wmlfc3zyhqzehihu7giusaaawu123"), eq(authorization),
                eq(UpdateInstrumentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<UpdateInstrumentResponse> future = client.updateInstrument("src_wmlfc3zyhqzehihu7giusaaawu123", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldDeleteInstrument() throws ExecutionException, InterruptedException {

        final Void response = mock(Void.class);

        when(apiClient.deleteAsync(eq(INSTRUMENTS + "/UpdateInstrumentResponse"), eq(authorization)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<Void> future = client.deleteInstrument("UpdateInstrumentResponse");

        assertNotNull(future.get());

    }

}