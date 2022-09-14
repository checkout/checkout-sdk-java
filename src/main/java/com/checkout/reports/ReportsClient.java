package com.checkout.reports;

import java.util.concurrent.CompletableFuture;

public interface ReportsClient {

    CompletableFuture<ReportsResponse> getAllReports(ReportsQuery query);

    CompletableFuture<ReportDetailsResponse> getReportDetails(String reportId);
}
