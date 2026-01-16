package com.checkout.instruments;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.instruments.create.CreateInstrumentBankAccountRequest;
import com.checkout.instruments.create.CreateInstrumentBankAccountResponse;
import com.checkout.instruments.create.CreateInstrumentResponse;
import com.checkout.instruments.get.BankAccountFieldQuery;
import com.checkout.instruments.get.BankAccountFieldResponse;
import com.checkout.instruments.get.GetBankAccountInstrumentResponse;
import com.checkout.instruments.get.GetInstrumentResponse;
import com.checkout.instruments.update.UpdateInstrumentCardRequest;
import com.checkout.instruments.update.UpdateInstrumentCardResponse;
import com.checkout.instruments.update.UpdateInstrumentResponse;
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
import static org.mockito.Mockito.lenient;
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
        lenient().when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        this.instrumentsClient = new InstrumentsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldCreateInstrument() throws ExecutionException, InterruptedException {
        final CreateInstrumentBankAccountRequest request = createMockBankAccountRequest();
        final CreateInstrumentBankAccountResponse expectedResponse = Mockito.mock(CreateInstrumentBankAccountResponse.class);

        when(apiClient.postAsync(eq(INSTRUMENTS), any(SdkAuthorization.class), eq(CREATE_TYPE), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<CreateInstrumentBankAccountResponse> future = instrumentsClient.create(request);
        final CreateInstrumentBankAccountResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetInstrument() throws ExecutionException, InterruptedException {
        final GetInstrumentResponse expectedResponse = Mockito.mock(GetBankAccountInstrumentResponse.class);

        when(apiClient.getAsync(eq(INSTRUMENTS + "/" + "123"), any(SdkAuthorization.class), eq(GET_TYPE)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<GetInstrumentResponse> future = instrumentsClient.get("123");
        final GetInstrumentResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldUpdateInstrument() throws ExecutionException, InterruptedException {
        final UpdateInstrumentCardRequest request = createMockUpdateCardRequest();
        final UpdateInstrumentCardResponse expectedResponse = Mockito.mock(UpdateInstrumentCardResponse.class);

        when(apiClient.patchAsync(eq(INSTRUMENTS + "/" + "123"), any(SdkAuthorization.class), eq(UPDATE_TYPE), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<UpdateInstrumentCardResponse> future = instrumentsClient.update("123", request);
        final UpdateInstrumentCardResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldDeleteInstrument() throws ExecutionException, InterruptedException {
        final EmptyResponse expectedResponse = Mockito.mock(EmptyResponse.class);

        when(apiClient.deleteAsync(eq(INSTRUMENTS + "/" + "123"), any(SdkAuthorization.class)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<EmptyResponse> future = instrumentsClient.delete("123");
        final EmptyResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetBankAccountFieldFormatting() throws ExecutionException, InterruptedException {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        final BankAccountFieldResponse expectedResponse = new BankAccountFieldResponse();
        final BankAccountFieldQuery query = createMockBankAccountFieldQuery();
        
        when(apiClient.queryAsync(eq("validation/bank-accounts/GB/GBP"), any(SdkAuthorization.class), eq(query), eq(BankAccountFieldResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<BankAccountFieldResponse> future = instrumentsClient.getBankAccountFieldFormatting(CountryCode.GB, Currency.GBP, query);
        final BankAccountFieldResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    // Synchronous methods
    @Test
    void shouldCreateInstrumentSync() {
        final CreateInstrumentBankAccountRequest request = createMockBankAccountRequest();
        final CreateInstrumentBankAccountResponse expectedResponse = Mockito.mock(CreateInstrumentBankAccountResponse.class);

        when(apiClient.post(eq(INSTRUMENTS), any(SdkAuthorization.class), eq(CREATE_TYPE), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final CreateInstrumentBankAccountResponse actualResponse = instrumentsClient.createSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetInstrumentSync() {
        final GetInstrumentResponse expectedResponse = Mockito.mock(GetBankAccountInstrumentResponse.class);

        when(apiClient.get(eq(INSTRUMENTS + "/" + "123"), any(SdkAuthorization.class), eq(GET_TYPE)))
                .thenReturn(expectedResponse);

        final GetInstrumentResponse actualResponse = instrumentsClient.getSync("123");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldUpdateInstrumentSync() {
        final UpdateInstrumentCardRequest request = createMockUpdateCardRequest();
        final UpdateInstrumentCardResponse expectedResponse = Mockito.mock(UpdateInstrumentCardResponse.class);

        when(apiClient.patch(eq(INSTRUMENTS + "/" + "123"), any(SdkAuthorization.class), eq(UPDATE_TYPE), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final UpdateInstrumentCardResponse actualResponse = instrumentsClient.updateSync("123", request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldDeleteInstrumentSync() {
        final EmptyResponse expectedResponse = Mockito.mock(EmptyResponse.class);

        when(apiClient.delete(eq(INSTRUMENTS + "/" + "123"), any(SdkAuthorization.class)))
                .thenReturn(expectedResponse);

        final EmptyResponse actualResponse = instrumentsClient.deleteSync("123");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetBankAccountFieldFormattingSync() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(authorization);
        final BankAccountFieldResponse expectedResponse = new BankAccountFieldResponse();
        final BankAccountFieldQuery query = createMockBankAccountFieldQuery();
        
        when(apiClient.query(eq("validation/bank-accounts/GB/GBP"), any(SdkAuthorization.class), eq(query), eq(BankAccountFieldResponse.class)))
                .thenReturn(expectedResponse);

        final BankAccountFieldResponse actualResponse = instrumentsClient.getBankAccountFieldFormattingSync(CountryCode.GB, Currency.GBP, query);

        validateResponse(expectedResponse, actualResponse);
    }

    // Common methods
    private CreateInstrumentBankAccountRequest createMockBankAccountRequest() {
        return Mockito.mock(CreateInstrumentBankAccountRequest.class);
    }

    private UpdateInstrumentCardRequest createMockUpdateCardRequest() {
        return Mockito.mock(UpdateInstrumentCardRequest.class);
    }

    private BankAccountFieldQuery createMockBankAccountFieldQuery() {
        return BankAccountFieldQuery.builder().build();
    }

    private <T> void validateResponse(T expectedResponse, T actualResponse) {
        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse);
    }

}