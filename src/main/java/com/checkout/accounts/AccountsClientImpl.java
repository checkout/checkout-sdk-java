package com.checkout.accounts;

import static com.checkout.common.CheckoutUtils.validateParams;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

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
        validateAccountsFileRequest(accountsFileRequest);
        return filesClient.submitFileAsync(
                FILES_PATH,
                sdkAuthorization(),
                accountsFileRequest,
                IdResponse.class);
    }

    @Override
    public CompletableFuture<OnboardEntityResponse> createEntity(final OnboardEntityRequest entityRequest) {
        validateEntityRequest(entityRequest);
        return apiClient.postAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH),
                sdkAuthorization(),
                OnboardEntityResponse.class,
                entityRequest,
                null);
    }

    @Override
    public CompletableFuture<PaymentInstrumentDetailsResponse> retrievePaymentInstrumentDetails(final String entityId, final String paymentInstrumentId) {
        validateEntityIdAndPaymentInstrumentId(entityId, paymentInstrumentId);
        return apiClient.getAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYMENT_INSTRUMENTS_PATH, paymentInstrumentId),
                sdkAuthorization(),
                PaymentInstrumentDetailsResponse.class);
    }

    @Override
    public CompletableFuture<OnboardEntityDetailsResponse> getEntity(final String entityId) {
        validateEntityId(entityId);
        return apiClient.getAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId),
                sdkAuthorization(),
                OnboardEntityDetailsResponse.class);
    }

    @Override
    public CompletableFuture<OnboardEntityResponse> updateEntity(final OnboardEntityRequest entityRequest, final String entityId) {
        validateEntityRequestAndId(entityRequest, entityId);
        return apiClient.putAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId),
                sdkAuthorization(),
                OnboardEntityResponse.class,
                entityRequest);
    }

    @Override
    public CompletableFuture<EmptyResponse> createPaymentInstrument(final AccountsPaymentInstrument accountsPaymentInstrument, final String entityId) {
        validateAccountsPaymentInstrumentAndEntityId(accountsPaymentInstrument, entityId);
        return apiClient.postAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, INSTRUMENTS_PATH),
                sdkAuthorization(),
                EmptyResponse.class,
                accountsPaymentInstrument,
                null);
    }

    @Override
    public CompletableFuture<IdResponse> createPaymentInstrument(final String entityId, final PaymentInstrumentRequest paymentInstrumentRequest) {
        validateEntityIdAndPaymentInstrumentRequest(entityId, paymentInstrumentRequest);
        return apiClient.postAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYMENT_INSTRUMENTS_PATH),
                sdkAuthorization(),
                IdResponse.class,
                paymentInstrumentRequest,
                null
        );
    }

    @Override
    public CompletableFuture<IdResponse> updatePaymentInstrumentDetails(final String entityId,
                                                                        final String instrumentId,
                                                                        final UpdatePaymentInstrumentRequest updatePaymentInstrumentRequest) {
        validateEntityIdInstrumentIdAndRequest(entityId, instrumentId, updatePaymentInstrumentRequest);
        return apiClient.patchAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYMENT_INSTRUMENTS_PATH, instrumentId),
                sdkAuthorization(),
                IdResponse.class,
                updatePaymentInstrumentRequest,
                null
        );
    }

    @Override
    public CompletableFuture<PaymentInstrumentQueryResponse> queryPaymentInstruments(final String entityId, final PaymentInstrumentsQuery query) {
        validateEntityIdAndQuery(entityId, query);
        return apiClient.queryAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYMENT_INSTRUMENTS_PATH),
                sdkAuthorization(),
                query,
                PaymentInstrumentQueryResponse.class
        );
    }

    @Override
    public CompletableFuture<GetScheduleResponse> retrievePayoutSchedule(final String entityId) {
        validateEntityId(entityId);
        return apiClient.getAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYOUT_SCHEDULES_PATH),
                sdkAuthorization(),
                GetScheduleResponse.class);
    }

    @Override
    public CompletableFuture<VoidResponse> updatePayoutSchedule(final String entityId, final Currency currency, final UpdateScheduleRequest updateScheduleRequest) {
        validateEntityIdCurrencyAndRequest(entityId, currency, updateScheduleRequest);
        final Map<Currency, UpdateScheduleRequest> request = buildScheduleRequestMap(currency, updateScheduleRequest);
        return apiClient.putAsync(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYOUT_SCHEDULES_PATH),
                sdkAuthorization(),
                VoidResponse.class,
                request);
    }

    // Synchronous methods
    @Override
    public IdResponse submitFileSync(final AccountsFileRequest accountsFileRequest) {
        validateAccountsFileRequest(accountsFileRequest);
        return filesClient.submitFile(
                FILES_PATH,
                sdkAuthorization(),
                accountsFileRequest,
                IdResponse.class);
    }

    @Override
    public OnboardEntityResponse createEntitySync(final OnboardEntityRequest entityRequest) {
        validateEntityRequest(entityRequest);
        return apiClient.post(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH),
                sdkAuthorization(),
                OnboardEntityResponse.class,
                entityRequest,
                null);
    }

    @Override
    public PaymentInstrumentDetailsResponse retrievePaymentInstrumentDetailsSync(final String entityId, final String paymentInstrumentId) {
        validateEntityIdAndPaymentInstrumentId(entityId, paymentInstrumentId);
        return apiClient.get(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYMENT_INSTRUMENTS_PATH, paymentInstrumentId),
                sdkAuthorization(),
                PaymentInstrumentDetailsResponse.class);
    }

    @Override
    public OnboardEntityDetailsResponse getEntitySync(final String entityId) {
        validateEntityId(entityId);
        return apiClient.get(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId),
                sdkAuthorization(),
                OnboardEntityDetailsResponse.class);
    }

    @Override
    public OnboardEntityResponse updateEntitySync(final OnboardEntityRequest entityRequest, final String entityId) {
        validateEntityRequestAndId(entityRequest, entityId);
        return apiClient.put(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId),
                sdkAuthorization(),
                OnboardEntityResponse.class,
                entityRequest);
    }

    @Override
    public EmptyResponse createPaymentInstrumentSync(final AccountsPaymentInstrument accountsPaymentInstrument, final String entityId) {
        validateAccountsPaymentInstrumentAndEntityId(accountsPaymentInstrument, entityId);
        return apiClient.post(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, INSTRUMENTS_PATH),
                sdkAuthorization(),
                EmptyResponse.class,
                accountsPaymentInstrument,
                null);
    }

    @Override
    public IdResponse createPaymentInstrumentSync(final String entityId, final PaymentInstrumentRequest paymentInstrumentRequest) {
        validateEntityIdAndPaymentInstrumentRequest(entityId, paymentInstrumentRequest);
        return apiClient.post(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYMENT_INSTRUMENTS_PATH),
                sdkAuthorization(),
                IdResponse.class,
                paymentInstrumentRequest,
                null
        );
    }

    @Override
    public IdResponse updatePaymentInstrumentDetailsSync(final String entityId,
                                                         final String instrumentId,
                                                         final UpdatePaymentInstrumentRequest updatePaymentInstrumentRequest) {
        validateEntityIdInstrumentIdAndRequest(entityId, instrumentId, updatePaymentInstrumentRequest);
        return apiClient.patch(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYMENT_INSTRUMENTS_PATH, instrumentId),
                sdkAuthorization(),
                IdResponse.class,
                updatePaymentInstrumentRequest,
                null
        );
    }

    @Override
    public PaymentInstrumentQueryResponse queryPaymentInstrumentsSync(final String entityId, final PaymentInstrumentsQuery query) {
        validateEntityIdAndQuery(entityId, query);
        return apiClient.query(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYMENT_INSTRUMENTS_PATH),
                sdkAuthorization(),
                query,
                PaymentInstrumentQueryResponse.class
        );
    }

    @Override
    public GetScheduleResponse retrievePayoutScheduleSync(final String entityId) {
        validateEntityId(entityId);
        return apiClient.get(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYOUT_SCHEDULES_PATH),
                sdkAuthorization(),
                GetScheduleResponse.class);
    }

    @Override
    public VoidResponse updatePayoutScheduleSync(final String entityId, final Currency currency, final UpdateScheduleRequest updateScheduleRequest) {
        validateEntityIdCurrencyAndRequest(entityId, currency, updateScheduleRequest);
        final Map<Currency, UpdateScheduleRequest> request = buildScheduleRequestMap(currency, updateScheduleRequest);
        return apiClient.put(
                buildPath(ACCOUNTS_PATH, ENTITIES_PATH, entityId, PAYOUT_SCHEDULES_PATH),
                sdkAuthorization(),
                VoidResponse.class,
                request);
    }

    // Common methods
    private Map<Currency, UpdateScheduleRequest> buildScheduleRequestMap(final Currency currency, final UpdateScheduleRequest updateScheduleRequest) {
        final Map<Currency, UpdateScheduleRequest> request = new EnumMap<>(Currency.class);
        request.put(currency, updateScheduleRequest);
        return request;
    }

    private void validateAccountsFileRequest(final AccountsFileRequest accountsFileRequest) {
        validateParams("accountsFileRequest", accountsFileRequest);
    }

    private void validateEntityRequest(final OnboardEntityRequest entityRequest) {
        validateParams("entityRequest", entityRequest);
    }

    private void validateEntityId(final String entityId) {
        validateParams("entityId", entityId);
    }

    private void validateEntityIdAndPaymentInstrumentId(final String entityId, final String paymentInstrumentId) {
        validateParams("entityId", entityId, "paymentInstrumentId", paymentInstrumentId);
    }

    private void validateEntityRequestAndId(final OnboardEntityRequest entityRequest, final String entityId) {
        validateParams("entityRequest", entityRequest, "entityId", entityId);
    }

    private void validateAccountsPaymentInstrumentAndEntityId(final AccountsPaymentInstrument accountsPaymentInstrument, final String entityId) {
        validateParams("accountsPaymentInstrument", accountsPaymentInstrument, "entityId", entityId);
    }

    private void validateEntityIdAndPaymentInstrumentRequest(final String entityId, final PaymentInstrumentRequest paymentInstrumentRequest) {
        validateParams("entityId", entityId, "paymentInstrumentRequest", paymentInstrumentRequest);
    }

    private void validateEntityIdInstrumentIdAndRequest(final String entityId, final String instrumentId, final UpdatePaymentInstrumentRequest updatePaymentInstrumentRequest) {
        validateParams("entityId", entityId, "instrumentId", instrumentId, "updatePaymentInstrumentRequest", updatePaymentInstrumentRequest);
    }

    private void validateEntityIdAndQuery(final String entityId, final PaymentInstrumentsQuery query) {
        validateParams("entityId", entityId, "query", query);
    }

    private void validateEntityIdCurrencyAndRequest(final String entityId, final Currency currency, final UpdateScheduleRequest updateScheduleRequest) {
        validateParams("entityId", entityId, "currency", currency, "updateScheduleRequest", updateScheduleRequest);
    }

}
