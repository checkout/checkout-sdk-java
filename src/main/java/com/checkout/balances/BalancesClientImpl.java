package com.checkout.balances;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

public class BalancesClientImpl extends AbstractClient implements BalancesClient {

    private static final String BALANCES_PATH = "balances";

    public BalancesClientImpl(final ApiClient apiClient,
                              final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<BalancesResponse> retrieveEntityBalances(final String entityId, final BalancesQuery balancesQuery) {
        validateEntityIdAndBalancesQuery(entityId, balancesQuery);
        return apiClient.queryAsync(buildPath(BALANCES_PATH, entityId), sdkAuthorization(), balancesQuery, BalancesResponse.class);
    }

    // Synchronous methods
    @Override
    public BalancesResponse retrieveEntityBalancesSync(final String entityId, final BalancesQuery balancesQuery) {
        validateEntityIdAndBalancesQuery(entityId, balancesQuery);
        return apiClient.query(buildPath(BALANCES_PATH, entityId), sdkAuthorization(), balancesQuery, BalancesResponse.class);
    }

    // Common methods
    protected void validateEntityIdAndBalancesQuery(final String entityId, final BalancesQuery balancesQuery) {
        com.checkout.common.CheckoutUtils.validateParams("entityId", entityId, "balancesQuery", balancesQuery);
    }
}
