package com.checkout.reconciliation.previous;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.ContentResponse;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.QueryFilterDateRange;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class ReconciliationClientImpl extends AbstractClient implements ReconciliationClient {

    private static final String REPORTING_PATH = "reporting";
    private static final String PAYMENTS_PATH = "payments";
    private static final String STATEMENTS_PATH = "statements";
    private static final String DOWNLOAD_PATH = "download";

    public ReconciliationClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<ReconciliationPaymentReportResponse> queryPaymentsReport(final ReconciliationQueryPaymentsFilter filter) {
        validateParams("filter", filter);
        return apiClient.queryAsync(
                buildPath(REPORTING_PATH, PAYMENTS_PATH),
                sdkAuthorization(),
                filter,
                ReconciliationPaymentReportResponse.class
        );
    }

    @Override
    public CompletableFuture<ReconciliationPaymentReportResponse> singlePaymentReportAsync(final String paymentId) {
        validateParams("paymentId", paymentId);
        return apiClient.getAsync(
                buildPath(REPORTING_PATH, PAYMENTS_PATH, paymentId),
                sdkAuthorization(),
                ReconciliationPaymentReportResponse.class
        );
    }

    @Override
    public CompletableFuture<StatementReportResponse> queryStatementsReport(final StatementsQueryFilter filter) {
        validateParams("filter", filter);
        return apiClient.queryAsync(
                buildPath(REPORTING_PATH, STATEMENTS_PATH),
                sdkAuthorization(),
                filter,
                StatementReportResponse.class
        );
    }

    @Override
    public CompletableFuture<StatementReportResponse> getStatementsReportById(final String statementId) {
        validateParams("statementId", statementId);
        return apiClient.getAsync(
                buildPath(REPORTING_PATH, STATEMENTS_PATH, statementId, PAYMENTS_PATH),
                sdkAuthorization(),
                StatementReportResponse.class
        );
    }

    @Override
    public CompletableFuture<StatementReportResponse> getStatementsReportByIdQuery(final String statementId, final StatementsQueryFilter filter) {
        validateParams("statementId", statementId, "filter", filter);
        return apiClient.queryAsync(
                buildPath(REPORTING_PATH, STATEMENTS_PATH, statementId, PAYMENTS_PATH),
                sdkAuthorization(),
                filter,
                StatementReportResponse.class
        );
    }

    @Override
    public CompletableFuture<ContentResponse> retrieveCSVPaymentReport(final QueryFilterDateRange filter, final String targetFile) {
        validateParams("filter", filter);
        return apiClient.queryCsvContentAsync(
                buildPath(REPORTING_PATH, PAYMENTS_PATH, DOWNLOAD_PATH),
                sdkAuthorization(),
                filter,
                targetFile
        );
    }

    @Override
    public CompletableFuture<ContentResponse> retrieveCSVSingleStatementReport(final String statementId, final String targetFile) {
        validateParams("statementId", statementId);
        return apiClient.queryCsvContentAsync(
                buildPath(REPORTING_PATH, STATEMENTS_PATH, statementId, PAYMENTS_PATH, DOWNLOAD_PATH),
                sdkAuthorization(),
                null,
                targetFile
        );
    }

    @Override
    public CompletableFuture<ContentResponse> retrieveCSVStatementsReport(final StatementsQueryFilter filter, final String targetFile) {
        validateParams("filter", filter);
        return apiClient.queryCsvContentAsync(
                buildPath(REPORTING_PATH, STATEMENTS_PATH, DOWNLOAD_PATH),
                sdkAuthorization(),
                filter,
                targetFile
        );
    }

}
