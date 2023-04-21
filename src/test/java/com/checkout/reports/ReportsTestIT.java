package com.checkout.reports;

import com.checkout.ContentResponse;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReportsTestIT extends SandboxTestFixture {

    public ReportsTestIT() {
        super(PlatformType.DEFAULT);
    }

    private final ReportsQuery queryFilterDateRange = ReportsQuery.builder()
            .createdAfter(LocalDateTime.now().minus(1, ChronoUnit.WEEKS).toInstant(ZoneOffset.UTC))
            .createdBefore(Instant.now())
            .build();

    @Test
    void shouldGetAllReports() {
        final ReportsResponse reportsResponse = getAllReports();
        assertNotNull(reportsResponse);
        if (reportsResponse.getData() != null && !reportsResponse.getData().isEmpty()) {
            reportsResponse.getData().forEach(detailsResponse -> {
                assertNotNull(detailsResponse.getId());
                assertNotNull(detailsResponse.getCreatedOn());
                assertNotNull(detailsResponse.getType());
                assertNotNull(detailsResponse.getAccount());
                assertNotNull(detailsResponse.getAccount().getClientId());
                assertNotNull(detailsResponse.getAccount().getEntityId());
                assertNotNull(detailsResponse.getFrom());
                assertNotNull(detailsResponse.getTo());
                assertNotNull(detailsResponse.getFiles());
            });
        }
    }

    @Test
    void shouldGetReportDetails() {
        final ReportsResponse reportsResponse = getAllReports();
        assertNotNull(reportsResponse);
        if (reportsResponse.getData() != null && !reportsResponse.getData().isEmpty()) {
            final ReportDetailsResponse reportDetails = reportsResponse.getData().get(0);
            final ReportDetailsResponse detailsResponse = blocking(() -> checkoutApi.reportsClient().getReportDetails(reportDetails.getId()));
            assertNotNull(detailsResponse);
            assertEquals(reportDetails.getId(), detailsResponse.getId());
            assertEquals(reportDetails.getType(), detailsResponse.getType());
            assertEquals(reportDetails.getCreatedOn(), detailsResponse.getCreatedOn());
            assertEquals(reportDetails.getDescription(), detailsResponse.getDescription());
            assertEquals(reportDetails.getAccount(), detailsResponse.getAccount());
            assertEquals(reportDetails.getFrom(), detailsResponse.getFrom());
            assertEquals(reportDetails.getTo(), detailsResponse.getTo());
        }
    }

    @Disabled("unstable")
    @Test
    void shouldGetReportFile() throws ExecutionException, InterruptedException {
        final ReportsResponse reportsResponse = getAllReports();
        assertNotNull(reportsResponse);
        if (reportsResponse.getData() != null && !reportsResponse.getData().isEmpty()) {
            final ReportDetailsResponse reportDetails = reportsResponse.getData().get(0);
            final ContentResponse contentResponse = checkoutApi.reportsClient().getReportFile(reportDetails.getId(), reportDetails.getFiles().get(0).getId()).get();
            assertNotNull(contentResponse);
            assertNotNull(contentResponse.getContent());
            assertTrue(contentResponse.getContent().contains("Entity ID,Entity Name"));
        }
    }

    private ReportsResponse getAllReports() {
        return blocking(() -> checkoutApi.reportsClient().getAllReports(queryFilterDateRange));
    }
}
