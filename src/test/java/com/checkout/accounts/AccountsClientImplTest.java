package com.checkout.accounts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.checkout.ApiClient;
import com.checkout.CheckoutArgumentException;
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

@ExtendWith(MockitoExtension.class)
class AccountsClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private AccountsClient accountsClient;

    @BeforeEach
    void setUp() {
        lenient().when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        lenient().when(checkoutConfiguration.getSdkCredentials()).thenReturn(sdkCredentials);
        lenient().when(checkoutConfiguration.getHttpClientBuilder()).thenReturn(HttpClientBuilder.create());
        lenient().when(checkoutConfiguration.getExecutor()).thenReturn(Executors.newSingleThreadExecutor());
        this.accountsClient = new AccountsClientImpl(apiClient, apiClient, checkoutConfiguration);
    }

    @Test
    void shouldThrowException_whenAccountsFileRequestIsNull() {
        try {
            accountsClient.submitFile(null);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("accountsFileRequest cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldThrowException_whenEntityRequestIsNull() {
        try {
            accountsClient.createEntity(null);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("entityRequest cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldThrowException_whenEntityIdIsNull() {
        try {
            accountsClient.getEntity(null);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("entityId cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldCreateEntity() throws ExecutionException, InterruptedException {
        final OnboardEntityRequest request = createOnboardEntityRequest();
        final OnboardEntityResponse expectedResponse = createOnboardEntityResponse();

        when(apiClient.postAsync(eq("accounts/entities"), eq(authorization), eq(OnboardEntityResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<OnboardEntityResponse> future = accountsClient.createEntity(request);

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldGetEntity() throws ExecutionException, InterruptedException {
        final OnboardEntityDetailsResponse expectedResponse = createOnboardEntityDetailsResponse();

        when(apiClient.getAsync("accounts/entities/entity_id", authorization, OnboardEntityDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<OnboardEntityDetailsResponse> future = accountsClient.getEntity("entity_id");

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldUpdateEntity() throws ExecutionException, InterruptedException {
        final OnboardEntityRequest request = createOnboardEntityRequest();
        final OnboardEntityResponse expectedResponse = createOnboardEntityResponse();

        when(apiClient.putAsync(eq("accounts/entities/entity_id"), eq(authorization), eq(OnboardEntityResponse.class), eq(request)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<OnboardEntityResponse> future = accountsClient.updateEntity(request, "entity_id");

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldCreatePaymentInstrumentDeprecated() throws ExecutionException, InterruptedException {
        final AccountsPaymentInstrument request = createAccountsPaymentInstrument();
        final EmptyResponse expectedResponse = createEmptyResponse();

        when(apiClient.postAsync(eq("accounts/entities/entity_id/instruments"), eq(authorization), eq(EmptyResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<EmptyResponse> future = accountsClient.createPaymentInstrument(request, "entity_id");

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldSubmitFile() throws ExecutionException, InterruptedException {
        final AccountsFileRequest request = createAccountsFileRequest();
        final IdResponse expectedResponse = createIdResponse();

        when(apiClient.submitFileAsync(eq("files"), eq(authorization), eq(request), eq(IdResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<IdResponse> future = accountsClient.submitFile(request);

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldUpdatePayoutSchedule() throws ExecutionException, InterruptedException {
        final UpdateScheduleRequest request = createUpdateScheduleRequest();
        final VoidResponse expectedResponse = createVoidResponse();

        when(apiClient.putAsync(eq("accounts/entities/entity_id/payout-schedules"), eq(authorization), eq(VoidResponse.class), anyMap()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<VoidResponse> future = accountsClient.updatePayoutSchedule("entity_id", Currency.USD, request);

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldRetrievePayoutSchedule() throws ExecutionException, InterruptedException {
        final GetScheduleResponse expectedResponse = createGetScheduleResponse();

        when(apiClient.getAsync("accounts/entities/entity_id/payout-schedules", authorization, GetScheduleResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<GetScheduleResponse> future = accountsClient.retrievePayoutSchedule("entity_id");

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldCreatePaymentInstrument() throws ExecutionException, InterruptedException {
        final PaymentInstrumentRequest request = createPaymentInstrumentRequest();
        final IdResponse expectedResponse = createIdResponse();

        when(apiClient.postAsync(eq("accounts/entities/entity_id/payment-instruments"), eq(authorization), eq(IdResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<IdResponse> future = accountsClient.createPaymentInstrument("entity_id", request);

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldUpdatePaymentInstrument() throws ExecutionException, InterruptedException {
        final UpdatePaymentInstrumentRequest request = createUpdatePaymentInstrumentRequest();
        final IdResponse expectedResponse = createIdResponse();

        when(apiClient.patchAsync(eq("accounts/entities/entity_id/payment-instruments/instrument_id"), eq(authorization), eq(IdResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<IdResponse> future = accountsClient.updatePaymentInstrumentDetails("entity_id", "instrument_id", request);

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldRetrievePaymentInstrumentDetails() throws ExecutionException, InterruptedException {
        final PaymentInstrumentDetailsResponse expectedResponse = createPaymentInstrumentDetailsResponse();

        when(apiClient.getAsync("accounts/entities/entity_id/payment-instruments/instrument_id", authorization, PaymentInstrumentDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<PaymentInstrumentDetailsResponse> future = accountsClient.retrievePaymentInstrumentDetails("entity_id", "instrument_id");

        validateResponse(expectedResponse, future.get());
    }

    @Test
    void shouldQueryPaymentInstruments() throws ExecutionException, InterruptedException {
        final PaymentInstrumentsQuery request = createPaymentInstrumentsQuery();
        final PaymentInstrumentQueryResponse expectedResponse = createPaymentInstrumentQueryResponse();

        when(apiClient.queryAsync(eq("accounts/entities/entity_id/payment-instruments"), eq(authorization), eq(request), eq(PaymentInstrumentQueryResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<PaymentInstrumentQueryResponse> future = accountsClient.queryPaymentInstruments("entity_id", request);

        validateResponse(expectedResponse, future.get());
    }

    // Synchronous methods
    @Test
    void shouldCreateEntitySync() {
        final OnboardEntityRequest request = createOnboardEntityRequest();
        final OnboardEntityResponse expectedResponse = createOnboardEntityResponse();

        when(apiClient.post(eq("accounts/entities"), eq(authorization), eq(OnboardEntityResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final OnboardEntityResponse actualResponse = accountsClient.createEntitySync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetEntitySync() {
        final OnboardEntityDetailsResponse expectedResponse = createOnboardEntityDetailsResponse();

        when(apiClient.get("accounts/entities/entity_id", authorization, OnboardEntityDetailsResponse.class))
                .thenReturn(expectedResponse);

        final OnboardEntityDetailsResponse actualResponse = accountsClient.getEntitySync("entity_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldUpdateEntitySync() {
        final OnboardEntityRequest request = createOnboardEntityRequest();
        final OnboardEntityResponse expectedResponse = createOnboardEntityResponse();

        when(apiClient.put(eq("accounts/entities/entity_id"), eq(authorization), eq(OnboardEntityResponse.class), eq(request)))
                .thenReturn(expectedResponse);

        final OnboardEntityResponse actualResponse = accountsClient.updateEntitySync(request, "entity_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCreatePaymentInstrumentDeprecatedSync() {
        final AccountsPaymentInstrument request = createAccountsPaymentInstrument();
        final EmptyResponse expectedResponse = createEmptyResponse();

        when(apiClient.post(eq("accounts/entities/entity_id/instruments"), eq(authorization), eq(EmptyResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final EmptyResponse actualResponse = accountsClient.createPaymentInstrumentSync(request, "entity_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldSubmitFileSync() {
        final AccountsFileRequest request = createAccountsFileRequest();
        final IdResponse expectedResponse = createIdResponse();

        when(apiClient.submitFile("files", authorization, request, IdResponse.class))
                .thenReturn(expectedResponse);

        final IdResponse actualResponse = accountsClient.submitFileSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldUpdatePayoutScheduleSync() {
        final UpdateScheduleRequest request = createUpdateScheduleRequest();
        final VoidResponse expectedResponse = createVoidResponse();

        when(apiClient.put(eq("accounts/entities/entity_id/payout-schedules"), eq(authorization), eq(VoidResponse.class), anyMap()))
                .thenReturn(expectedResponse);

        final VoidResponse actualResponse = accountsClient.updatePayoutScheduleSync("entity_id", Currency.USD, request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRetrievePayoutScheduleSync() {
        final GetScheduleResponse expectedResponse = createGetScheduleResponse();

        when(apiClient.get("accounts/entities/entity_id/payout-schedules", authorization, GetScheduleResponse.class))
                .thenReturn(expectedResponse);

        final GetScheduleResponse actualResponse = accountsClient.retrievePayoutScheduleSync("entity_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCreatePaymentInstrumentSync() {
        final PaymentInstrumentRequest request = createPaymentInstrumentRequest();
        final IdResponse expectedResponse = createIdResponse();

        when(apiClient.post(eq("accounts/entities/entity_id/payment-instruments"), eq(authorization), eq(IdResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final IdResponse actualResponse = accountsClient.createPaymentInstrumentSync("entity_id", request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldUpdatePaymentInstrumentSync() {
        final UpdatePaymentInstrumentRequest request = createUpdatePaymentInstrumentRequest();
        final IdResponse expectedResponse = createIdResponse();

        when(apiClient.patch(eq("accounts/entities/entity_id/payment-instruments/instrument_id"), eq(authorization), eq(IdResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final IdResponse actualResponse = accountsClient.updatePaymentInstrumentDetailsSync("entity_id", "instrument_id", request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRetrievePaymentInstrumentDetailsSync() {
        final PaymentInstrumentDetailsResponse expectedResponse = createPaymentInstrumentDetailsResponse();

        when(apiClient.get("accounts/entities/entity_id/payment-instruments/instrument_id", authorization, PaymentInstrumentDetailsResponse.class))
                .thenReturn(expectedResponse);

        final PaymentInstrumentDetailsResponse actualResponse = accountsClient.retrievePaymentInstrumentDetailsSync("entity_id", "instrument_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldQueryPaymentInstrumentsSync() {
        final PaymentInstrumentsQuery request = createPaymentInstrumentsQuery();
        final PaymentInstrumentQueryResponse expectedResponse = createPaymentInstrumentQueryResponse();

        when(apiClient.query(eq("accounts/entities/entity_id/payment-instruments"), eq(authorization), eq(request), eq(PaymentInstrumentQueryResponse.class)))
                .thenReturn(expectedResponse);

        final PaymentInstrumentQueryResponse actualResponse = accountsClient.queryPaymentInstrumentsSync("entity_id", request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldThrowException_whenAccountsFileRequestIsNullSync() {
        try {
            accountsClient.submitFileSync(null);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("accountsFileRequest cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldThrowException_whenEntityRequestIsNullSync() {
        try {
            accountsClient.createEntitySync(null);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("entityRequest cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldThrowException_whenEntityIdIsNullSync() {
        try {
            accountsClient.getEntitySync(null);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("entityId cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldThrowException_whenUpdateEntityRequestIsNullSync() {
        try {
            accountsClient.updateEntitySync(null, "entity_id");
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("entityRequest cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldThrowException_whenPaymentInstrumentRequestIsNullSync() {
        try {
            accountsClient.createPaymentInstrumentSync("entity_id", null);
            fail();
        } catch (final CheckoutArgumentException checkoutArgumentException) {
            assertEquals("paymentInstrumentRequest cannot be null", checkoutArgumentException.getMessage());
        }

        verifyNoInteractions(apiClient);
    }

    // Common methods
    private OnboardEntityRequest createOnboardEntityRequest() {
        return OnboardEntityRequest.builder().build();
    }

    private OnboardEntityResponse createOnboardEntityResponse() {
        return mock(OnboardEntityResponse.class);
    }

    private OnboardEntityDetailsResponse createOnboardEntityDetailsResponse() {
        return mock(OnboardEntityDetailsResponse.class);
    }

    private AccountsPaymentInstrument createAccountsPaymentInstrument() {
        final AccountsPaymentInstrument request = mock(AccountsPaymentInstrument.class);
        final AccountsCorporateAccountHolder corporateAccountHolder = mock(AccountsCorporateAccountHolder.class);
        final AccountsIndividualAccountHolder individualAccountHolder = mock(AccountsIndividualAccountHolder.class);
        request.setAccountHolder(corporateAccountHolder);
        request.setAccountHolder(individualAccountHolder);
        return request;
    }

    private AccountsFileRequest createAccountsFileRequest() {
        return AccountsFileRequest.builder().build();
    }

    private UpdateScheduleRequest createUpdateScheduleRequest() {
        return mock(UpdateScheduleRequest.class);
    }

    private PaymentInstrumentRequest createPaymentInstrumentRequest() {
        return mock(PaymentInstrumentRequest.class);
    }

    private UpdatePaymentInstrumentRequest createUpdatePaymentInstrumentRequest() {
        return mock(UpdatePaymentInstrumentRequest.class);
    }

    private PaymentInstrumentsQuery createPaymentInstrumentsQuery() {
        return new PaymentInstrumentsQuery();
    }

    private IdResponse createIdResponse() {
        return mock(IdResponse.class);
    }

    private EmptyResponse createEmptyResponse() {
        return mock(EmptyResponse.class);
    }

    private VoidResponse createVoidResponse() {
        return mock(VoidResponse.class);
    }

    private GetScheduleResponse createGetScheduleResponse() {
        return mock(GetScheduleResponse.class);
    }

    private PaymentInstrumentDetailsResponse createPaymentInstrumentDetailsResponse() {
        return mock(PaymentInstrumentDetailsResponse.class);
    }

    private PaymentInstrumentQueryResponse createPaymentInstrumentQueryResponse() {
        return mock(PaymentInstrumentQueryResponse.class);
    }

    private <T> void validateResponse(T expectedResponse, T actualResponse) {
        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
    }

}
