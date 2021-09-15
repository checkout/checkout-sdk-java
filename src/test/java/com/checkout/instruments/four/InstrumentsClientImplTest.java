package com.checkout.instruments.four;

import com.checkout.ApiClient;
import com.checkout.SdkAuthorizationType;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkCredentials;
import com.checkout.instruments.four.create.CreateInstrumentBankAccountRequest;
import com.checkout.instruments.four.create.CreateInstrumentBankAccountResponse;
import com.checkout.instruments.four.create.CreateInstrumentResponse;
import com.checkout.instruments.four.get.GetBankAccountInstrumentResponse;
import com.checkout.instruments.four.get.GetInstrumentResponse;
import com.checkout.instruments.four.update.UpdateInstrumentCardRequest;
import com.checkout.instruments.four.update.UpdateInstrumentCardResponse;
import com.checkout.instruments.four.update.UpdateInstrumentResponse;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstrumentsClientImplTest {

    private static final String INSTRUMENTS = "instruments";

    private static final Type CREATE_TYPE = TypeToken.get(CreateInstrumentResponse.class).getType();
    private static final Type UPDATE_TYPE = TypeToken.get(UpdateInstrumentResponse.class).getType();
    private static final Type GET_TYPE = TypeToken.get(GetInstrumentResponse.class).getType();

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private InstrumentsClient instrumentsClient;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        this.instrumentsClient = new InstrumentsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldCreateInstrument() throws ExecutionException, InterruptedException {

        final CreateInstrumentBankAccountRequest request = Mockito.mock(CreateInstrumentBankAccountRequest.class);
        final CreateInstrumentBankAccountResponse response = Mockito.mock(CreateInstrumentBankAccountResponse.class);

        when(apiClient.postAsync(eq(INSTRUMENTS), any(SdkAuthorization.class), eq(CREATE_TYPE), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CreateInstrumentBankAccountResponse> future = instrumentsClient.create(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetInstrument() throws ExecutionException, InterruptedException {

        final GetInstrumentResponse response = Mockito.mock(GetBankAccountInstrumentResponse.class);

        when(apiClient.getAsync(eq(INSTRUMENTS + "/" + "123"), any(SdkAuthorization.class), eq(GET_TYPE)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<GetInstrumentResponse> future = instrumentsClient.get("123");

        assertNotNull(future.get());

    }

    @Test
    void shouldUpdateInstrument() throws ExecutionException, InterruptedException {

        final UpdateInstrumentCardRequest request = Mockito.mock(UpdateInstrumentCardRequest.class);
        final UpdateInstrumentCardResponse response = Mockito.mock(UpdateInstrumentCardResponse.class);

        when(apiClient.patchAsync(eq(INSTRUMENTS + "/" + "123"), any(SdkAuthorization.class), eq(UPDATE_TYPE), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<UpdateInstrumentCardResponse> future = instrumentsClient.update("123", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldDeleteInstrument() throws ExecutionException, InterruptedException {

        final Void response = Mockito.mock(Void.class);

        when(apiClient.deleteAsync(eq(INSTRUMENTS + "/" + "123"), any(SdkAuthorization.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<Void> future = instrumentsClient.delete("123");

        assertNotNull(future.get());

    }

}