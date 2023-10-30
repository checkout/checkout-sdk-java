package com.checkout.reconciliation.previous;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.ContentResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.QueryFilterDateRange;
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
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReconciliationClientImplTest {

    private ReconciliationClient client;

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
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new ReconciliationClientImpl(apiClient, configuration);
    }

    @Test
    void shouldQueryPaymentsReport() throws ExecutionException, InterruptedException {
        final ReconciliationQueryPaymentsFilter request = mock(ReconciliationQueryPaymentsFilter.class);
        final ReconciliationPaymentReportResponse response = mock(ReconciliationPaymentReportResponse.class);

        when(apiClient.queryAsync(eq("reporting/payments"), any(SdkAuthorization.class), eq(request),
                eq(ReconciliationPaymentReportResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReconciliationPaymentReportResponse> future = client.queryPaymentsReport(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldSinglePaymentReportAsync() throws ExecutionException, InterruptedException {
        final ReconciliationPaymentReportResponse response = mock(ReconciliationPaymentReportResponse.class);

        when(apiClient.getAsync(eq("reporting/payments/payment_id"), any(SdkAuthorization.class), eq(ReconciliationPaymentReportResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReconciliationPaymentReportResponse> future = client.singlePaymentReportAsync("payment_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldQueryStatementsReport() throws ExecutionException, InterruptedException {
        final StatementsQueryFilter request = mock(StatementsQueryFilter.class);
        final StatementReportResponse response = mock(StatementReportResponse.class);

        when(apiClient.queryAsync(eq("reporting/statements"), any(SdkAuthorization.class), eq(request),
                eq(StatementReportResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<StatementReportResponse> future = client.queryStatementsReport(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetStatementsReportById() throws ExecutionException, InterruptedException {
        final ReconciliationPaymentReportResponse response = mock(ReconciliationPaymentReportResponse.class);

        when(apiClient.getAsync(eq("reporting/statements/statement_id/payments"), any(SdkAuthorization.class),
                eq(ReconciliationPaymentReportResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReconciliationPaymentReportResponse> future = client.getStatementsReportById("statement_id");

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetStatementsReportByIdWithQuery() throws ExecutionException, InterruptedException {
        final StatementsQueryFilter filter = mock(StatementsQueryFilter.class);
        final ReconciliationPaymentReportResponse response = mock(ReconciliationPaymentReportResponse.class);

        when(apiClient.queryAsync(eq("reporting/statements/statement_id/payments"), any(SdkAuthorization.class), eq(filter),
                eq(ReconciliationPaymentReportResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReconciliationPaymentReportResponse> future = client.getStatementsReportByIdQuery("statement_id", filter);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldRetrieveCSVPaymentReport() throws ExecutionException, InterruptedException {
        final String report = "/etc/foo/payment_report.csv";
        final QueryFilterDateRange request = mock(QueryFilterDateRange.class);
        final ContentResponse response = mock(ContentResponse.class);

        when(apiClient.queryCsvContentAsync(eq("reporting/payments/download"), any(SdkAuthorization.class), eq(request),
                eq(report)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ContentResponse> future = client.retrieveCSVPaymentReport(request, report);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldRetrieveCSVSingleStatementReport() throws ExecutionException, InterruptedException {
        final String report = "/etc/foo/single_statement_report.csv";
        final ContentResponse response = mock(ContentResponse.class);

        when(apiClient.queryCsvContentAsync(eq("reporting/statements/statement_id/payments/download"), any(SdkAuthorization.class), isNull(),
                eq(report)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ContentResponse> future = client.retrieveCSVSingleStatementReport("statement_id", report);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldRetrieveCSVStatementsReport() throws ExecutionException, InterruptedException {
        final String report = "/etc/foo/statement_report.csv";
        final StatementsQueryFilter request = mock(StatementsQueryFilter.class);
        final ContentResponse response = mock(ContentResponse.class);

        when(apiClient.queryCsvContentAsync(eq("reporting/statements/download"), any(SdkAuthorization.class), eq(request),
                eq(report)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ContentResponse> future = client.retrieveCSVStatementsReport(request, report);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

}

