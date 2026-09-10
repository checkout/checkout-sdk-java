package com.checkout.balances;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

public class BalancesClientImpl extends AbstractClient implements BalancesClient {

    private static final String BALANCES_PATH = "balances";
    private static final String ENTITIES_PATH = "entities";
    private static final String CURRENCY_ACCOUNTS_PATH = "currency-accounts";
    private static final String TOP_UP_INSTRUCTIONS_PATH = "top-up-instructions";

    public BalancesClientImpl(final ApiClient apiClient,
                              final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<BalancesResponse> retrieveEntityBalances(final String entityId, final BalancesQuery balancesQuery) {
        validateEntityIdAndBalancesQuery(entityId, balancesQuery);
        return apiClient.queryAsync(buildPath(BALANCES_PATH, entityId), sdkAuthorization(), balancesQuery, BalancesResponse.class);
    }

    @Override
    public CompletableFuture<TopUpInstructionsResponse> retrieveTopUpInstructions(final String entityId, final String currencyAccountId) {
        validateEntityIdAndCurrencyAccountId(entityId, currencyAccountId);
        return apiClient.getAsync(topUpInstructionsPath(entityId, currencyAccountId), sdkAuthorization(), TopUpInstructionsResponse.class);
    }

    // Synchronous methods
    @Override
    public BalancesResponse retrieveEntityBalancesSync(final String entityId, final BalancesQuery balancesQuery) {
        validateEntityIdAndBalancesQuery(entityId, balancesQuery);
        return apiClient.query(buildPath(BALANCES_PATH, entityId), sdkAuthorization(), balancesQuery, BalancesResponse.class);
    }

    @Override
    public TopUpInstructionsResponse retrieveTopUpInstructionsSync(final String entityId, final String currencyAccountId) {
        validateEntityIdAndCurrencyAccountId(entityId, currencyAccountId);
        return apiClient.get(topUpInstructionsPath(entityId, currencyAccountId), sdkAuthorization(), TopUpInstructionsResponse.class);
    }

    // Common methods
    protected void validateEntityIdAndBalancesQuery(final String entityId, final BalancesQuery balancesQuery) {
        com.checkout.common.CheckoutUtils.validateParams("entityId", entityId, "balancesQuery", balancesQuery);
    }

    private void validateEntityIdAndCurrencyAccountId(final String entityId, final String currencyAccountId) {
        com.checkout.common.CheckoutUtils.validateParams("entityId", entityId, "currencyAccountId", currencyAccountId);
    }

    private static String topUpInstructionsPath(final String entityId, final String currencyAccountId) {
        return buildPath(ENTITIES_PATH, entityId, CURRENCY_ACCOUNTS_PATH, currencyAccountId, TOP_UP_INSTRUCTIONS_PATH);
    }
}
