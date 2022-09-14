package com.checkout.reports;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        final ReportsResponse reportsResponse = blocking(() -> checkoutApi.reportsClient().getAllReports(queryFilterDateRange));
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
        final ReportsResponse reportsResponse = blocking(() -> checkoutApi.reportsClient().getAllReports(queryFilterDateRange));
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
}
