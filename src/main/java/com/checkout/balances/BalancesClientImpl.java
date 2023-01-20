package com.checkout.balances;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class BalancesClientImpl extends AbstractClient implements BalancesClient {

    private static final String BALANCES_PATH = "balances";

    public BalancesClientImpl(final ApiClient apiClient,
                              final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<BalancesResponse> retrieveEntityBalances(final String entityId, final BalancesQuery balancesQuery) {
        validateParams("entityId", entityId, "balancesQuery", balancesQuery);
        return apiClient.queryAsync(buildPath(BALANCES_PATH, entityId), sdkAuthorization(), balancesQuery, BalancesResponse.class);
    }
}
