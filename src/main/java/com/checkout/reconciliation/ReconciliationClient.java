package com.checkout.reconciliation;


import com.checkout.common.QueryFilterDateRange;

import java.util.concurrent.CompletableFuture;

public interface ReconciliationClient {

    CompletableFuture<ReconciliationPaymentReportResponse> queryPaymentsReport(ReconciliationQueryPaymentsFilter filter);

    CompletableFuture<ReconciliationPaymentReportResponse> singlePaymentReportAsync(String paymentId);

    CompletableFuture<StatementReportResponse> queryStatementsReport(QueryFilterDateRange filter);

    CompletableFuture<String> retrieveCSVPaymentReport(String targetFile);

    CompletableFuture<String> retrieveCSVSingleStatementReport(String statementId, String targetFile);

    CompletableFuture<String> retrieveCSVStatementsReport(String targetFile);


}
