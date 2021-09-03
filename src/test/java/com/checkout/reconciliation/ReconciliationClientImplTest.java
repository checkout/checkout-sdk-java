package com.checkout.reconciliation;

import com.checkout.ApiClient;
import com.checkout.ApiCredentials;
import com.checkout.CheckoutConfiguration;
import com.checkout.common.Currency;
import com.checkout.common.QueryFilterDateRange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReconciliationClientImplTest {

    private static final Instant FROM = Instant.from(ZonedDateTime.of(2020, 2, 22, 12, 31, 44, 0, ZoneOffset.UTC));
    private static final Instant TO = Instant.from(ZonedDateTime.of(2020, 2, 26, 13, 21, 34, 0, ZoneOffset.UTC));
    private static final String PAYMENT_ID = "pay_nezg6bx2k22utmk4xm5s2ughxi";
    private static final String REFERENCE = "ORD-5023-4E89";
    private static final String ACTION_ID = "act_nezg6bx2k22utmk4xm5s2ughxi";
    private static final String ACTION_TYPE = "Authorization";
    private static final String BREAKDOWN_TYPE = "Gateway Fee Tax ARE USD/GBP@0.7640412612";
    private static final String STATEMENT_ID = "190110B107654";

    private ReconciliationClient client;

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private ReconciliationPaymentReportResponse reconciliationPaymentReportResponse;

    @Mock
    private PaymentReportData paymentReportData;

    @Mock
    private Action action;

    @Mock
    private Breakdown breakdown;

    @Mock
    private StatementReportResponse statementReportResponse;

    @Mock
    private StatementData statementData;

    @Mock
    private PayoutStatement payout;

    @Mock
    private CompletableFuture<ReconciliationPaymentReportResponse> disputesQueryResponseFuture;

    @Mock
    private CompletableFuture<StatementReportResponse> statementReportResponseFuture;

    @Mock
    private CompletableFuture<String> completableFileFuture;

    @BeforeEach
    void setUp() {
        disputesQueryResponseFuture = CompletableFuture.completedFuture(reconciliationPaymentReportResponse);
        statementReportResponseFuture = CompletableFuture.completedFuture(statementReportResponse);
        client = new ReconciliationClientImpl(apiClient, configuration);
    }

    private void setUpPaymentReport() {
        when(reconciliationPaymentReportResponse.getCount()).thenReturn(1);
        when(reconciliationPaymentReportResponse.getData()).thenReturn(Collections.singletonList(paymentReportData));
        when(paymentReportData.getId()).thenReturn(PAYMENT_ID);
        when(paymentReportData.getProcessingCurrency()).thenReturn(Currency.USD);
        when(paymentReportData.getPayoutCurrency()).thenReturn(Currency.GBP);
        when(paymentReportData.getReference()).thenReturn(REFERENCE);
        when(paymentReportData.getActions()).thenReturn(Collections.singletonList(action));
        when(action.getId()).thenReturn(ACTION_ID);
        when(action.getType()).thenReturn(ACTION_TYPE);
        when(action.getBreakdown()).thenReturn(Collections.singletonList(breakdown));
        when(breakdown.getType()).thenReturn(BREAKDOWN_TYPE);

    }

    private void setUpStatementResponse() {
        when(statementReportResponse.getCount()).thenReturn(1);
        when(statementReportResponse.getData()).thenReturn(Collections.singletonList(statementData));
        when(statementData.getId()).thenReturn(STATEMENT_ID);
        when(statementData.getPeriodStart()).thenReturn(FROM);
        when(statementData.getPeriodEnd()).thenReturn(TO);
        when(statementData.getDate()).thenReturn(TO);
        when(statementData.getPayouts()).thenReturn(Collections.singletonList(payout));
        when(payout.getCurrency()).thenReturn(Currency.GBP);
        when(payout.getPeriodStart()).thenReturn(FROM);
        when(payout.getPeriodEnd()).thenReturn(TO);
    }

    @Test
    void shouldQueryPaymentsReport() throws ExecutionException, InterruptedException {
        setUpPaymentReport();
        doReturn(disputesQueryResponseFuture)
                .when(apiClient)
                .queryAsync(eq("reporting/payments"), any(ApiCredentials.class),
                        any(ReconciliationQueryPaymentsFilter.class), eq(ReconciliationPaymentReportResponse.class));
        final ReconciliationQueryPaymentsFilter filter = ReconciliationQueryPaymentsFilter
                .builder()
                .from(FROM.toString())
                .to(TO.toString())
                .reference(PAYMENT_ID)
                .build();
        final ReconciliationPaymentReportResponse response = client.queryPaymentsReport(filter).get();
        assertNotNull(response);
        assertEquals(1, response.getCount());
        assertNotNull(response.getData());
        final PaymentReportData reportData = response.getData().get(0);
        assertEquals(PAYMENT_ID, reportData.getId());
        assertEquals(Currency.USD, reportData.getProcessingCurrency());
        assertEquals(Currency.GBP, reportData.getPayoutCurrency());
        assertEquals(REFERENCE, reportData.getReference());
        assertNotNull(reportData.getActions());
        final Action action = reportData.getActions().get(0);
        assertEquals(ACTION_ID, action.getId());
        assertEquals(ACTION_TYPE, action.getType());
        assertNotNull(action.getBreakdown());
        assertEquals(BREAKDOWN_TYPE, action.getBreakdown().get(0).getType());
    }

    @Test
    void shouldSinglePaymentReportAsync() throws ExecutionException, InterruptedException {
        setUpPaymentReport();
        doReturn(disputesQueryResponseFuture)
                .when(apiClient)
                .getAsync(eq("reporting/payments/" + PAYMENT_ID), any(ApiCredentials.class),
                        eq(ReconciliationPaymentReportResponse.class));
        final ReconciliationPaymentReportResponse response = client.singlePaymentReportAsync(PAYMENT_ID).get();
        assertNotNull(response);
        assertEquals(1, response.getCount());
        assertNotNull(response.getData());
        final PaymentReportData reportData = response.getData().get(0);
        assertEquals(PAYMENT_ID, reportData.getId());
        assertEquals(Currency.USD, reportData.getProcessingCurrency());
        assertEquals(Currency.GBP, reportData.getPayoutCurrency());
        assertEquals(REFERENCE, reportData.getReference());
        assertNotNull(reportData.getActions());
        final Action action = reportData.getActions().get(0);
        assertEquals(ACTION_ID, action.getId());
        assertEquals(ACTION_TYPE, action.getType());
        assertNotNull(action.getBreakdown());
        assertEquals(BREAKDOWN_TYPE, action.getBreakdown().get(0).getType());
    }

    @Test
    void shouldQueryStatementsReport() throws ExecutionException, InterruptedException {
        setUpStatementResponse();
        doReturn(statementReportResponseFuture)
                .when(apiClient)
                .queryAsync(eq("reporting/statements"), any(ApiCredentials.class),
                        any(QueryFilterDateRange.class), eq(StatementReportResponse.class));
        final QueryFilterDateRange filter = QueryFilterDateRange
                .builder()
                .from(FROM.toString())
                .to(TO.toString())
                .build();
        final StatementReportResponse response = client.queryStatementsReport(filter).get();
        assertNotNull(response);
        assertEquals(1, statementReportResponse.getCount());
        assertNotNull(statementReportResponse.getData());
        final StatementData statementData = statementReportResponse.getData().get(0);
        assertNotNull(statementData);
        assertEquals(STATEMENT_ID, statementData.getId());
        assertEquals(FROM, statementData.getPeriodStart());
        assertEquals(TO, statementData.getPeriodEnd());
        assertEquals(TO, statementData.getDate());
        assertNotNull(statementData.getPayouts());
        final PayoutStatement payout = statementData.getPayouts().get(0);
        assertNotNull(payout);
        assertEquals(Currency.GBP, payout.getCurrency());
        assertEquals(FROM, payout.getPeriodStart());
        assertEquals(TO, payout.getPeriodEnd());
    }

    @Test
    void shouldRetrieveCSVPaymentReport() throws ExecutionException, InterruptedException {
        final String report = "/etc/foo/payment_report.csv";
        completableFileFuture = CompletableFuture.completedFuture(report);
        doReturn(completableFileFuture)
                .when(apiClient)
                .retrieveFileAsync(eq("reporting/payments/download"), any(ApiCredentials.class), eq(report));
        final String file = client.retrieveCSVPaymentReport(report).get();
        assertNotNull(file);
        assertEquals(report, file);
    }

    @Test
    void shouldRetrieveCSVSingleStatementReport() throws ExecutionException, InterruptedException {
        final String report = "/etc/foo/single_statement_report.csv";
        completableFileFuture = CompletableFuture.completedFuture(report);
        doReturn(completableFileFuture)
                .when(apiClient)
                .retrieveFileAsync(eq(String.format("reporting/statements/%s/payments/download", STATEMENT_ID)),
                        any(ApiCredentials.class), eq(report));
        final String file = client.retrieveCSVSingleStatementReport(STATEMENT_ID, report).get();
        assertNotNull(file);
        assertEquals(report, file);
    }

    @Test
    void shouldRetrieveCSVStatementsReport() throws ExecutionException, InterruptedException {
        final String report = "/etc/foo/statement_report.csv";
        completableFileFuture = CompletableFuture.completedFuture(report);
        doReturn(completableFileFuture)
                .when(apiClient)
                .retrieveFileAsync(eq("reporting/statements/download"),
                        any(ApiCredentials.class), eq(report));
        final String file = client.retrieveCSVStatementsReport(report).get();
        assertNotNull(file);
        assertEquals(report, file);
    }

}

