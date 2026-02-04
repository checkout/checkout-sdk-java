package com.checkout.reports;

import com.checkout.ContentResponse;

import java.util.concurrent.CompletableFuture;

public interface ReportsClient {

    CompletableFuture<ReportsResponse> getAllReports(ReportsQuery query);

    CompletableFuture<ReportDetailsResponse> getReportDetails(String reportId);

    CompletableFuture<ContentResponse> getReportFile(String reportId, String fileId);

    // Synchronous methods
    ReportsResponse getAllReportsSync(ReportsQuery query);

    ReportDetailsResponse getReportDetailsSync(String reportId);

    ContentResponse getReportFileSync(String reportId, String fileId);
}
