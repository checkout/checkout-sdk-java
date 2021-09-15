package com.checkout.reconciliation;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.QueryFilterDateRange;

import java.util.concurrent.CompletableFuture;

public class ReconciliationClientImpl extends AbstractClient implements ReconciliationClient {

    private static final String REPORTING = "reporting";
    private static final String PAYMENTS = "payments";
    private static final String STATEMENTS = "statements";
    private static final String DOWNLOAD = "download";

    public ReconciliationClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<ReconciliationPaymentReportResponse> queryPaymentsReport(final ReconciliationQueryPaymentsFilter filter) {
        return apiClient.queryAsync(buildPath(REPORTING, PAYMENTS), sdkAuthorization(), filter, ReconciliationPaymentReportResponse.class);
    }

    @Override
    public CompletableFuture<ReconciliationPaymentReportResponse> singlePaymentReportAsync(final String paymentId) {
        return apiClient.getAsync(buildPath(REPORTING, PAYMENTS, paymentId), sdkAuthorization(), ReconciliationPaymentReportResponse.class);
    }

    @Override
    public CompletableFuture<StatementReportResponse> queryStatementsReport(final QueryFilterDateRange filter) {
        return apiClient.queryAsync(buildPath(REPORTING, STATEMENTS), sdkAuthorization(), filter, StatementReportResponse.class);
    }

    @Override
    public CompletableFuture<String> retrieveCSVPaymentReport(final String targetFile) {
        return apiClient.retrieveFileAsync(buildPath(REPORTING, PAYMENTS, DOWNLOAD), sdkAuthorization(), targetFile);
    }

    @Override
    public CompletableFuture<String> retrieveCSVSingleStatementReport(final String statementId, final String targetFile) {
        return apiClient.retrieveFileAsync(buildPath(REPORTING, STATEMENTS, statementId, PAYMENTS, DOWNLOAD), sdkAuthorization(), targetFile);
    }

    @Override
    public CompletableFuture<String> retrieveCSVStatementsReport(final String targetFile) {
        return apiClient.retrieveFileAsync(buildPath(REPORTING, STATEMENTS, DOWNLOAD), sdkAuthorization(), targetFile);
    }

}
