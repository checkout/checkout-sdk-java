package com.checkout.financial;


import java.util.concurrent.CompletableFuture;

public interface FinancialClient {
    CompletableFuture<FinancialActionsQueryResponse> query(FinancialActionsQueryFilter queryFilter);
}
