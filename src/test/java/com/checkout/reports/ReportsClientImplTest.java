package com.checkout.reports;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.ContentResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportsClientImplTest {

    private ReportsClient client;

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new ReportsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldGetAllReports() throws ExecutionException, InterruptedException {
        final ReportsQuery query = createReportsQuery();
        final ReportsResponse expectedResponse = mock(ReportsResponse.class);

        when(apiClient.queryAsync(eq("reports"), any(SdkAuthorization.class), eq(query), eq(ReportsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<ReportsResponse> future = client.getAllReports(query);

        final ReportsResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetReportDetails() throws ExecutionException, InterruptedException {
        final ReportDetailsResponse expectedResponse = mock(ReportDetailsResponse.class);

        when(apiClient.getAsync(eq("reports/rpt_1234"), any(SdkAuthorization.class), eq(ReportDetailsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<ReportDetailsResponse> future = client.getReportDetails("rpt_1234");

        final ReportDetailsResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetReportFile() throws ExecutionException, InterruptedException {
        final ContentResponse expectedResponse = mock(ContentResponse.class);

        when(apiClient.queryCsvContentAsync(eq("reports/rpt_1234/files/file_1234"),
                any(SdkAuthorization.class), eq(null), eq(null)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<ContentResponse> future = client.getReportFile("rpt_1234", "file_1234");

        final ContentResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    // Synchronous methods
    @Test
    void shouldGetAllReportsSync() {
        final ReportsQuery query = createReportsQuery();
        final ReportsResponse expectedResponse = mock(ReportsResponse.class);

        when(apiClient.query(eq("reports"), any(SdkAuthorization.class), eq(query), eq(ReportsResponse.class)))
                .thenReturn(expectedResponse);

        final ReportsResponse actualResponse = client.getAllReportsSync(query);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetReportDetailsSync() {
        final ReportDetailsResponse expectedResponse = mock(ReportDetailsResponse.class);

        when(apiClient.get(eq("reports/rpt_1234"), any(SdkAuthorization.class), eq(ReportDetailsResponse.class)))
                .thenReturn(expectedResponse);

        final ReportDetailsResponse actualResponse = client.getReportDetailsSync("rpt_1234");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetReportFileSync() {
        final ContentResponse expectedResponse = mock(ContentResponse.class);

        when(apiClient.queryCsvContent(eq("reports/rpt_1234/files/file_1234"),
                any(SdkAuthorization.class), eq(null), eq(null)))
                .thenReturn(expectedResponse);

        final ContentResponse actualResponse = client.getReportFileSync("rpt_1234", "file_1234");

        validateResponse(expectedResponse, actualResponse);
    }

    // Common methods
    private ReportsQuery createReportsQuery() {
        return mock(ReportsQuery.class);
    }

    private <T> void validateResponse(final T expectedResponse, final T actualResponse) {
        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse);
    }
}
