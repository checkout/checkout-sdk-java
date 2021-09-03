package com.checkout.reconciliation;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
import com.checkout.common.QueryFilterDateRange;

import java.util.concurrent.CompletableFuture;

public class ReconciliationClientImpl extends AbstractClient implements ReconciliationClient {

    private static final String REPORTING = "reporting";
    private static final String PAYMENTS = "payments";
    private static final String STATEMENTS = "statements";
    private static final String DOWNLOAD = "download";

    public ReconciliationClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, SecretKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<ReconciliationPaymentReportResponse> queryPaymentsReport(final ReconciliationQueryPaymentsFilter filter) {
        return apiClient.queryAsync(buildPath(REPORTING, PAYMENTS), apiCredentials, filter, ReconciliationPaymentReportResponse.class);
    }

    @Override
    public CompletableFuture<ReconciliationPaymentReportResponse> singlePaymentReportAsync(final String paymentId) {
        return apiClient.getAsync(buildPath(REPORTING, PAYMENTS, paymentId), apiCredentials, ReconciliationPaymentReportResponse.class);
    }

    @Override
    public CompletableFuture<StatementReportResponse> queryStatementsReport(final QueryFilterDateRange filter) {
        return apiClient.queryAsync(buildPath(REPORTING, STATEMENTS), apiCredentials, filter, StatementReportResponse.class);
    }

    @Override
    public CompletableFuture<String> retrieveCSVPaymentReport(final String targetFile) {
        return apiClient.retrieveFileAsync(buildPath(REPORTING, PAYMENTS, DOWNLOAD), apiCredentials, targetFile);
    }

    @Override
    public CompletableFuture<String> retrieveCSVSingleStatementReport(final String statementId, final String targetFile) {
        return apiClient.retrieveFileAsync(buildPath(REPORTING, STATEMENTS, statementId, PAYMENTS, DOWNLOAD),
                apiCredentials, targetFile);
    }

    @Override
    public CompletableFuture<String> retrieveCSVStatementsReport(final String targetFile) {
        return apiClient.retrieveFileAsync(buildPath(REPORTING, STATEMENTS, DOWNLOAD), apiCredentials, targetFile);
    }

}
