package com.checkout.accounts;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorizationType;
import com.checkout.accounts.payout.schedule.request.UpdateScheduleRequest;
import com.checkout.accounts.payout.schedule.response.GetScheduleResponse;
import com.checkout.accounts.payout.schedule.response.VoidResponse;
import com.checkout.common.Currency;
import com.checkout.common.IdResponse;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class AccountsClientImpl extends AbstractClient implements AccountsClient {

    private static final String ACCOUNTS_PATH = "accounts";
    private static final String ENTITIES_PATH = "entities";
    private static final String INSTRUMENTS_PATH = "instruments";
    private static final String FILES_PATH = "files";
    private static final String PAYOUT_SCHEDULES_PATH = "payout-schedules";
    private static final String PAYMENT_INSTRUMENTS_PATH = "payment-instruments";

    private final ApiClient filesClient;

    public AccountsClientImpl(final ApiClient apiClient,
                              final ApiClient filesClient,
                              final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
        this.filesClient = filesClient;
    }

    @Override
    public CompletableFuture<IdResponse> submitFile(final AccountsFileRequest accountsFileRequest) {
        validateParams("accountsFileRequest", accountsFileRequest);
        return filesClient.submitFileAsync(
                FILES_PATH,
                sdkAuthorization(),
                accountsFileRequest,
                IdResponse.class);
    }

    @Override
    public CompletableFuture<OnboardEntityResponse> createEntity(final OnboardEntityRequest entityRequest) {
        validateParams("entityRequest", entityRequest);
        return apiClient.postAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH),
                sdkAuthorization(),
                OnboardEntityResponse.class,
                entityRequest,
                null);
    }

    @Override
    public CompletableFuture<PaymentInstrumentDetailsResponse> retrievePaymentInstrumentDetails(final String entityId, final String paymentInstrumentId) {
        validateParams("entityId", entityId, "paymentInstrumentId", paymentInstrumentId);
        return apiClient.getAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYMENT_INSTRUMENTS_PATH, paymentInstrumentId),
                sdkAuthorization(),
                PaymentInstrumentDetailsResponse.class);
    }

    @Override
    public CompletableFuture<OnboardEntityDetailsResponse> getEntity(final String entityId) {
        validateParams("entityId", entityId);
        return apiClient.getAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId),
                sdkAuthorization(),
                OnboardEntityDetailsResponse.class);
    }

    @Override
    public CompletableFuture<OnboardEntityResponse> updateEntity(final OnboardEntityRequest entityRequest, final String entityId) {
        validateParams("entityRequest", entityRequest, "entityId", entityId);
        return apiClient.putAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId),
                sdkAuthorization(),
                OnboardEntityResponse.class,
                entityRequest);
    }

    @Override
    public CompletableFuture<EmptyResponse> createPaymentInstrument(final AccountsPaymentInstrument accountsPaymentInstrument, final String entityId) {
        validateParams("accountsPaymentInstrument", accountsPaymentInstrument, "entityId", entityId);
        return apiClient.postAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, INSTRUMENTS_PATH),
                sdkAuthorization(),
                EmptyResponse.class,
                accountsPaymentInstrument,
                null);
    }

    @Override
    public CompletableFuture<IdResponse> createPaymentInstrument(final String entityId, final PaymentInstrumentRequest paymentInstrumentRequest) {
        validateParams("entityId", entityId, "paymentInstrumentRequest", paymentInstrumentRequest);
        return apiClient.postAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYMENT_INSTRUMENTS_PATH),
                sdkAuthorization(),
                IdResponse.class,
                paymentInstrumentRequest,
                null
        );
    }

    @Override
    public CompletableFuture<PaymentInstrumentQueryResponse> queryPaymentInstruments(final String entityId, final PaymentInstrumentsQuery query) {
        validateParams("entityId", entityId, "query", query);
        return apiClient.queryAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYMENT_INSTRUMENTS_PATH),
                sdkAuthorization(),
                query,
                PaymentInstrumentQueryResponse.class
        );
    }

    @Override
    public CompletableFuture<GetScheduleResponse> retrievePayoutSchedule(final String entityId) {
        validateParams("entityId", entityId);
        return apiClient.getAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYOUT_SCHEDULES_PATH),
                sdkAuthorization(),
                GetScheduleResponse.class);
    }

    @Override
    public CompletableFuture<VoidResponse> updatePayoutSchedule(final String entityId, final Currency currency, final UpdateScheduleRequest updateScheduleRequest) {
        validateParams("entityId", entityId, "currency", currency, "updateScheduleRequest", updateScheduleRequest);
        final Map<Currency, UpdateScheduleRequest> request = new EnumMap<>(Currency.class);
        request.put(currency, updateScheduleRequest);
        return apiClient.putAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYOUT_SCHEDULES_PATH),
                sdkAuthorization(),
                VoidResponse.class,
                request);
    }
}
