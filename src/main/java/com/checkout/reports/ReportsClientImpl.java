package com.checkout.reports;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.ContentResponse;
import com.checkout.SdkAuthorizationType;

import java.util.concurrent.CompletableFuture;

public class ReportsClientImpl extends AbstractClient implements ReportsClient {

    private static final String REPORTS_PATH = "reports";
    private static final String FILES_PATH = "files";

    public ReportsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<ReportsResponse> getAllReports(final ReportsQuery query) {
        return apiClient.queryAsync(REPORTS_PATH, sdkAuthorization(), query, ReportsResponse.class);
    }

    @Override
    public CompletableFuture<ReportDetailsResponse> getReportDetails(final String reportId) {
        return apiClient.getAsync(buildPath(REPORTS_PATH, reportId), sdkAuthorization(), ReportDetailsResponse.class);
    }

    @Override
    public CompletableFuture<ContentResponse> getReportFile(final String reportId, final String fileId) {
        return apiClient.queryCsvContentAsync(
                buildPath(REPORTS_PATH, reportId, FILES_PATH, fileId),
                sdkAuthorization(),
                null,
                null);
    }
}
