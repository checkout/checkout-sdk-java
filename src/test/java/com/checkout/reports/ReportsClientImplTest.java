package com.checkout.reports;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
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
        final ReportsQuery query = mock(ReportsQuery.class);
        final ReportsResponse response = mock(ReportsResponse.class);

        when(apiClient.queryAsync(eq("reports"), any(SdkAuthorization.class), eq(query), eq(ReportsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReportsResponse> future = client.getAllReports(query);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetReportDetails() throws ExecutionException, InterruptedException {
        final ReportDetailsResponse response = mock(ReportDetailsResponse.class);

        when(apiClient.getAsync(eq("reports/rpt_1234"), any(SdkAuthorization.class), eq(ReportDetailsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReportDetailsResponse> future = client.getReportDetails("rpt_1234");

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }
}
