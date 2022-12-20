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
    void shouldCreateEntity() throws ExecutionException, InterruptedException {
        final OnboardEntityResponse response = mock(OnboardEntityResponse.class);

        when(apiClient.postAsync(eq("accounts/entities"), eq(authorization), eq(OnboardEntityResponse.class), any(OnboardEntityRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<OnboardEntityResponse> future = accountsClient.createEntity(OnboardEntityRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetEntity() throws ExecutionException, InterruptedException {

        final OnboardEntityDetailsResponse response = mock(OnboardEntityDetailsResponse.class);

        when(apiClient.getAsync("accounts/entities/entity_id", authorization, OnboardEntityDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<OnboardEntityDetailsResponse> future = accountsClient.getEntity("entity_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldUpdateEntity() throws ExecutionException, InterruptedException {

        final OnboardEntityResponse response = mock(OnboardEntityResponse.class);

        when(apiClient.putAsync(eq("accounts/entities/entity_id"), eq(authorization), eq(OnboardEntityResponse.class), any(OnboardEntityRequest.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<OnboardEntityResponse> future = accountsClient.updateEntity(OnboardEntityRequest.builder().build(), "entity_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldCreatePaymentInstrumentDeprecated() throws ExecutionException, InterruptedException {

        final EmptyResponse response = mock(EmptyResponse.class);
        final AccountsPaymentInstrument request = mock(AccountsPaymentInstrument.class);
        final AccountsCorporateAccountHolder corporateAccountHolder = mock(AccountsCorporateAccountHolder.class);
        final AccountsIndividualAccountHolder individualAccountHolder = mock(AccountsIndividualAccountHolder.class);
        request.setAccountHolder(corporateAccountHolder);
        request.setAccountHolder(individualAccountHolder);

        when(apiClient.postAsync(eq("accounts/entities/entity_id/instruments"), eq(authorization), eq(EmptyResponse.class), any(AccountsPaymentInstrument.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = accountsClient.createPaymentInstrument(request, "entity_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldSubmitFile() throws ExecutionException, InterruptedException {

        final IdResponse response = mock(IdResponse.class);

        when(apiClient.submitFileAsync(eq("files"), eq(authorization), any(AccountsFileRequest.class), eq(IdResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdResponse> future = accountsClient.submitFile(AccountsFileRequest.builder().build());

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldUpdatePayoutSchedule() throws ExecutionException, InterruptedException {
        final UpdateScheduleRequest request = mock(UpdateScheduleRequest.class);
        final VoidResponse response = mock(VoidResponse.class);

        when(apiClient.putAsync(eq("accounts/entities/entity_id/payout-schedules"), eq(authorization), eq(VoidResponse.class), anyMap()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<VoidResponse> future = accountsClient.updatePayoutSchedule("entity_id", Currency.USD, request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldRetrievePayoutSchedule() throws ExecutionException, InterruptedException {

        final GetScheduleResponse response = mock(GetScheduleResponse.class);

        when(apiClient.getAsync("accounts/entities/entity_id/payout-schedules", authorization, GetScheduleResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<GetScheduleResponse> future = accountsClient.retrievePayoutSchedule("entity_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldCreatePaymentInstrument() throws ExecutionException, InterruptedException {

        final IdResponse response = mock(IdResponse.class);
        final PaymentInstrumentRequest request = mock(PaymentInstrumentRequest.class);

        when(apiClient.postAsync(eq("accounts/entities/entity_id/payment-instruments"), eq(authorization), eq(IdResponse.class), any(PaymentInstrumentRequest.class), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdResponse> future = accountsClient.createPaymentInstrument("entity_id", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldRetrievePaymentInstrumentDetails() throws ExecutionException, InterruptedException {

        final PaymentInstrumentDetailsResponse response = mock(PaymentInstrumentDetailsResponse.class);

        when(apiClient.getAsync("accounts/entities/entity_id/payment-instruments/instrument_id", authorization, PaymentInstrumentDetailsResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentInstrumentDetailsResponse> future = accountsClient.retrievePaymentInstrumentDetails("entity_id", "instrument_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldQueryPaymentInstruments() throws ExecutionException, InterruptedException {

        final PaymentInstrumentQueryResponse response = mock(PaymentInstrumentQueryResponse.class);

        when(apiClient.queryAsync(eq("accounts/entities/entity_id/payment-instruments"), eq(authorization), any(PaymentInstrumentsQuery.class), eq(PaymentInstrumentQueryResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentInstrumentQueryResponse> future = accountsClient.queryPaymentInstruments("entity_id", new PaymentInstrumentsQuery());

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

}
