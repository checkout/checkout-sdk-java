package com.checkout.balances;

import java.util.concurrent.CompletableFuture;

public interface BalancesClient {

    CompletableFuture<BalancesResponse> retrieveEntityBalances(String entityId, BalancesQuery balancesQuery);

    // Synchronous methods
    BalancesResponse retrieveEntityBalancesSync(String entityId, BalancesQuery balancesQuery);

}
