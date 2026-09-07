package com.checkout.balances;

import java.util.concurrent.CompletableFuture;

public interface BalancesClient {

    /**
     * Retrieves the balances for each sub-account belonging to an entity.
     *
     * @param entityId      the ID of the entity
     * @param balancesQuery the query filter
     * @return a future with the balances response
     */
    CompletableFuture<BalancesResponse> retrieveEntityBalances(String entityId, BalancesQuery balancesQuery);

    /**
     * Retrieves the bank details required to top up a sub-account, along with the payment
     * reference that attributes an incoming payment to that sub-account.
     * Note: The sub-account is referred to as currency account in the API.
     *
     * @param entityId          the ID of the entity that owns the sub-account, or of an entity
     *                          above it in your hierarchy. A platform can use its own entity ID
     *                          to reach the sub-accounts of any entity beneath it
     * @param currencyAccountId the ID of the sub-account to retrieve top-up instructions for
     * @return a future with the top-up instructions response
     */
    CompletableFuture<TopUpInstructionsResponse> retrieveTopUpInstructions(String entityId, String currencyAccountId);

    // Synchronous methods

    /**
     * Retrieves the balances for each sub-account belonging to an entity.
     *
     * @param entityId      the ID of the entity
     * @param balancesQuery the query filter
     * @return the balances response
     */
    BalancesResponse retrieveEntityBalancesSync(String entityId, BalancesQuery balancesQuery);

    /**
     * Retrieves the bank details required to top up a sub-account, along with the payment
     * reference that attributes an incoming payment to that sub-account.
     * Note: The sub-account is referred to as currency account in the API.
     *
     * @param entityId          the ID of the entity that owns the sub-account, or of an entity
     *                          above it in your hierarchy. A platform can use its own entity ID
     *                          to reach the sub-accounts of any entity beneath it
     * @param currencyAccountId the ID of the sub-account to retrieve top-up instructions for
     * @return the top-up instructions response
     */
    TopUpInstructionsResponse retrieveTopUpInstructionsSync(String entityId, String currencyAccountId);

}
