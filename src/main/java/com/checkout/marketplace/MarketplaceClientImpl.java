package com.checkout.marketplace;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.Currency;
import com.checkout.common.IdResponse;
import com.checkout.marketplace.balances.BalancesQuery;
import com.checkout.marketplace.balances.BalancesResponse;
import com.checkout.marketplace.payout.schedule.request.UpdateScheduleRequest;
import com.checkout.marketplace.payout.schedule.response.GetScheduleResponseDeserializer;
import com.checkout.marketplace.payout.schedule.response.VoidResponse;
import com.checkout.marketplace.transfers.CreateTransferRequest;
import com.checkout.marketplace.transfers.CreateTransferResponse;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class MarketplaceClientImpl extends AbstractClient implements MarketplaceClient {

    private static final String MARKETPLACE_PATH = "marketplace";
    private static final String ENTITIES_PATH = "entities";
    private static final String INSTRUMENTS_PATH = "instruments";
    private static final String FILES_PATH = "files";
    private static final String TRANSFERS_PATH = "transfers";
    private static final String BALANCES_PATH = "balances";
    private static final String PAYOUT_SCHEDULES = "payout-schedules";

    private final ApiClient filesClient;
    private final ApiClient transfersClient;
    private final ApiClient balancesAClient;

    public MarketplaceClientImpl(final ApiClient apiClient,
                                 final ApiClient filesClient,
                                 final ApiClient transfersClient,
                                 final ApiClient balancesAClient,
                                 final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.OAUTH);
        this.filesClient = filesClient;
        this.transfersClient = transfersClient;
        this.balancesAClient = balancesAClient;
    }

    @Override
    public CompletableFuture<OnboardEntityResponse> createEntity(final OnboardEntityRequest entityRequest) {
        validateParams("entityRequest", entityRequest);
        return apiClient.postAsync(buildPath(MARKETPLACE_PATH, ENTITIES_PATH), sdkAuthorization(), OnboardEntityResponse.class, entityRequest, null);
    }

    @Override
    public CompletableFuture<OnboardEntityDetailsResponse> getEntity(final String entityId) {
        validateParams("entityId", entityId);
        return apiClient.getAsync(buildPath(MARKETPLACE_PATH, ENTITIES_PATH, entityId), sdkAuthorization(), OnboardEntityDetailsResponse.class);
    }

    @Override
    public CompletableFuture<OnboardEntityResponse> updateEntity(final OnboardEntityRequest entityRequest, final String entityId) {
        validateParams("entityRequest", entityRequest, "entityId", entityId);
        return apiClient.putAsync(buildPath(MARKETPLACE_PATH, ENTITIES_PATH, entityId), sdkAuthorization(), OnboardEntityResponse.class, entityRequest);
    }

    @Override
    public CompletableFuture<Void> createPaymentInstrument(final MarketplacePaymentInstrument marketplacePaymentInstrument, final String entityId) {
        validateParams("marketplacePaymentInstrument", marketplacePaymentInstrument, "entityId", entityId);
        return apiClient.postAsync(buildPath(MARKETPLACE_PATH, ENTITIES_PATH, entityId, INSTRUMENTS_PATH), sdkAuthorization(), Void.class, marketplacePaymentInstrument, null);
    }

    @Override
    public CompletableFuture<IdResponse> submitFile(final MarketplaceFileRequest marketplaceFileRequest) {
        validateParams("marketplaceFileRequest", marketplaceFileRequest);
        return filesClient.submitFileAsync(FILES_PATH, sdkAuthorization(), marketplaceFileRequest, IdResponse.class);
    }

    @Override
    public CompletableFuture<CreateTransferResponse> initiateTransferOfFunds(final CreateTransferRequest createTransferRequest) {
        validateParams("createTransferRequest", createTransferRequest);
        return transfersClient.postAsync(TRANSFERS_PATH, sdkAuthorization(), CreateTransferResponse.class, createTransferRequest, null);
    }

    @Override
    public CompletableFuture<BalancesResponse> retrieveEntityBalances(final String entityId, final BalancesQuery balancesQuery) {
        validateParams("entityId", entityId, "balancesQuery", balancesQuery);
        return balancesAClient.queryAsync(buildPath(BALANCES_PATH, entityId), sdkAuthorization(), balancesQuery, BalancesResponse.class);
    }

    @Override
    public CompletableFuture<VoidResponse> updatePayoutSchedule(final String entityId, final Currency currency, final UpdateScheduleRequest updateScheduleRequest) {
        validateParams("entityId", entityId, "currency", currency, "updateScheduleRequest", updateScheduleRequest);
        final Map<Currency, UpdateScheduleRequest> request = new EnumMap<>(Currency.class);
        request.put(currency, updateScheduleRequest);
        return apiClient.putAsync(buildPath(MARKETPLACE_PATH, ENTITIES_PATH, entityId, PAYOUT_SCHEDULES), sdkAuthorization(), VoidResponse.class, request);
    }

    @Override
    public CompletableFuture<GetScheduleResponseDeserializer> retrievePayoutSchedule(final String entityId) {
        validateParams("entityId", entityId);
        return apiClient.getAsync(buildPath(MARKETPLACE_PATH, ENTITIES_PATH, entityId, PAYOUT_SCHEDULES), sdkAuthorization(), GetScheduleResponseDeserializer.class);
    }
}
