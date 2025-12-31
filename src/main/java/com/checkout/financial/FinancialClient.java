package com.checkout.financial;


import java.util.concurrent.CompletableFuture;

public interface FinancialClient {
    CompletableFuture<FinancialActionsQueryResponse> query(FinancialActionsQueryFilter queryFilter);

    // Synchronous methods
    FinancialActionsQueryResponse querySync(FinancialActionsQueryFilter queryFilter);
}
