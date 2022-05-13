package com.checkout.reconciliation;


import com.checkout.ContentResponse;
import com.checkout.common.QueryFilterDateRange;

import java.util.concurrent.CompletableFuture;

public interface ReconciliationClient {

    CompletableFuture<ReconciliationPaymentReportResponse> queryPaymentsReport(ReconciliationQueryPaymentsFilter filter);

    CompletableFuture<ReconciliationPaymentReportResponse> singlePaymentReportAsync(String paymentId);

    CompletableFuture<StatementReportResponse> queryStatementsReport(QueryFilterDateRange filter);

    /**
     * @param targetFile Optional parameter that specifies the path where a file with the content returned is saved. If
     *                   the file does not exist, the client will attempt to create a new one, otherwise the existing
     *                   file will be rewritten.
     * @return CSV content
     */
    CompletableFuture<ContentResponse> retrieveCSVPaymentReport(final QueryFilterDateRange filter, final String targetFile);

    /**
     * @param targetFile Optional parameter that specifies the path where a file with the content returned is saved. If
     *                   the file does not exist, the client will attempt to create a new one, otherwise the existing
     *                   file will be rewritten.
     * @return CSV content
     */
    CompletableFuture<ContentResponse> retrieveCSVSingleStatementReport(final String statementId, final String targetFile);

    /**
     * @param targetFile Optional parameter that specifies the path where a file with the content returned is saved. If
     *                   the file does not exist, the client will attempt to create a new one, otherwise the existing
     *                   file will be rewritten.
     * @return CSV content
     */
    CompletableFuture<ContentResponse> retrieveCSVStatementsReport(final QueryFilterDateRange filter, final String targetFile);

}
