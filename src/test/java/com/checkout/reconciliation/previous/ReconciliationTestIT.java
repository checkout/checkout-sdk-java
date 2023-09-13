package com.checkout.reconciliation.previous;

import com.checkout.CheckoutSdk;
import com.checkout.ContentResponse;
import com.checkout.Environment;
import com.checkout.common.QueryFilterDateRange;
import com.checkout.previous.CheckoutApi;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ExecutionException;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReconciliationTestIT {

    private final QueryFilterDateRange queryFilterDateRange = QueryFilterDateRange.builder()
            .from(LocalDateTime.now().minus(1, ChronoUnit.MONTHS).toInstant(ZoneOffset.UTC))
            .to(Instant.now())
            .build();

    @Test
    @Disabled("Only works in production")
    void shouldQueryPaymentsReport() throws ExecutionException, InterruptedException {

        final ReconciliationQueryPaymentsFilter queryFilterDateRange = ReconciliationQueryPaymentsFilter.builder()
                .from(LocalDateTime.now().minus(1, ChronoUnit.MONTHS).toInstant(ZoneOffset.UTC))
                .to(Instant.now())
                .build();

        final ReconciliationPaymentReportResponse response = getProductionCheckoutApi().reconciliationClient().queryPaymentsReport(queryFilterDateRange).get();

        assertNotNull(response);
        assertNotNull(response.getLinks());
        assertTrue(response.getCount() >= 1);
        response.getData().forEach(data -> {
            assertNotNull(data.getId());
            assertNotNull(data.getProcessingCurrency());
            assertNotNull(data.getPayoutCurrency());
            assertNotNull(data.getRequestedOn());
            assertNotNull(data.getChannelName());
            assertNotNull(data.getReference());
            assertNotNull(data.getPaymentMethod());
            assertNotNull(data.getMerchantCountry());
            assertNotNull(data.getRegion());
            assertNotNull(data.getActions());
            assertTrue(data.getActions().size() >= 1);
            data.getActions().forEach(action -> {
                assertNotNull(action.getType());
                assertNotNull(action.getId());
                assertNotNull(action.getProcessedOn());
                assertNotNull(action.getResponseCode());
                assertNotNull(action.getResponseDescription());
                assertTrue(action.getBreakdown().size() >= 1);
                action.getBreakdown().forEach(breakdown -> {
                    assertNotNull(breakdown.getType());
                    assertNotNull(breakdown.getDate());
                    assertNotNull(breakdown.getProcessingCurrencyAmount());
                    assertNotNull(breakdown.getPayoutCurrencyAmount());
                });
            });
        });
    }

    @Test
    @Disabled("Only works in production")
    void shouldGetSinglePaymentReport() throws ExecutionException, InterruptedException {

        final ReconciliationPaymentReportResponse response = getProductionCheckoutApi().reconciliationClient().singlePaymentReportAsync("id").get();

        assertNotNull(response);
        assertNotNull(response.getLinks());
        assertTrue(response.getCount() >= 1);
        response.getData().forEach(data -> {
            assertNotNull(data.getId());
            assertNotNull(data.getProcessingCurrency());
            assertNotNull(data.getPayoutCurrency());
            assertNotNull(data.getRequestedOn());
            assertNotNull(data.getChannelName());
            assertNotNull(data.getReference());
            assertNotNull(data.getPaymentMethod());
            assertNotNull(data.getMerchantCountry());
            assertNotNull(data.getRegion());
            assertNotNull(data.getActions());
            assertTrue(data.getActions().size() >= 1);
            data.getActions().forEach(action -> {
                assertNotNull(action.getType());
                assertNotNull(action.getId());
                assertNotNull(action.getProcessedOn());
                assertNotNull(action.getResponseCode());
                assertNotNull(action.getResponseDescription());
                assertTrue(action.getBreakdown().size() >= 1);
                action.getBreakdown().forEach(breakdown -> {
                    assertNotNull(breakdown.getType());
                    assertNotNull(breakdown.getDate());
                    assertNotNull(breakdown.getProcessingCurrencyAmount());
                    assertNotNull(breakdown.getPayoutCurrencyAmount());
                });
            });
        });
    }

    @Test
    @Disabled("Only works in production")
    void shouldQueryStatementsReport() throws ExecutionException, InterruptedException {

        final StatementReportResponse response = getProductionCheckoutApi().reconciliationClient().queryStatementsReport(queryFilterDateRange).get();

        assertNotNull(response);
        assertNotNull(response.getLinks());
        assertTrue(response.getCount() >= 1);
        response.getData().forEach(statementData -> {
            assertNotNull(statementData.getId());
            assertNotNull(statementData.getPeriodStart());
            assertNotNull(statementData.getPeriodEnd());
            assertNotNull(statementData.getDate());
            assertNotNull(statementData.getPayouts());
            assertNotNull(statementData.getLinks());
            statementData.getPayouts().forEach(payoutStatement -> {
                assertNotNull(payoutStatement.getCurrency());
                assertNotNull(payoutStatement.getCarriedForwardAmount());
                assertNotNull(payoutStatement.getCurrentPeriodAmount());
                assertNotNull(payoutStatement.getNetAmount());
                assertNotNull(payoutStatement.getPeriodStart());
                assertNotNull(payoutStatement.getPeriodEnd());
                assertNotNull(payoutStatement.getId());
                assertNotNull(payoutStatement.getStatus());
                assertNotNull(payoutStatement.getPayoutFee());
                assertNotNull(payoutStatement.getLinks());
            });
        });
    }

    @Test
    @Disabled("Only works in production")
    void shouldGetStatementsReportById() throws ExecutionException, InterruptedException {

        final StatementReportResponse response = getProductionCheckoutApi().reconciliationClient().getStatementsReportById("statement_id").get();

        assertNotNull(response);
        assertNotNull(response.getLinks());
        assertTrue(response.getCount() >= 1);
        response.getData().forEach(statementData -> {
            assertNotNull(statementData.getId());
            assertNotNull(statementData.getPeriodStart());
            assertNotNull(statementData.getPeriodEnd());
            assertNotNull(statementData.getDate());
            assertNotNull(statementData.getPayouts());
            assertNotNull(statementData.getLinks());
            statementData.getPayouts().forEach(payoutStatement -> {
                assertNotNull(payoutStatement.getCurrency());
                assertNotNull(payoutStatement.getCarriedForwardAmount());
                assertNotNull(payoutStatement.getCurrentPeriodAmount());
                assertNotNull(payoutStatement.getNetAmount());
                assertNotNull(payoutStatement.getPeriodStart());
                assertNotNull(payoutStatement.getPeriodEnd());
                assertNotNull(payoutStatement.getId());
                assertNotNull(payoutStatement.getStatus());
                assertNotNull(payoutStatement.getPayoutFee());
                assertNotNull(payoutStatement.getLinks());
            });
        });
    }

    @Test
    @Disabled("Only works in production")
    void shouldRetrieveCsvPaymentReport() throws ExecutionException, InterruptedException {

        final QueryFilterDateRange queryFilterDateRange = QueryFilterDateRange.builder()
                .from(LocalDateTime.now().minus(1, ChronoUnit.MONTHS).toInstant(ZoneOffset.UTC))
                .to(Instant.now())
                .build();

        final ContentResponse ContentResponse = getProductionCheckoutApi().reconciliationClient().retrieveCSVPaymentReport(queryFilterDateRange, null).get();

        assertNotNull(ContentResponse);
        assertFalse(ContentResponse.getContent().isEmpty());
        assertNotNull(ContentResponse.getResponseHeaders());
        assertNotNull(ContentResponse.getBody());
        assertNotNull(ContentResponse.getHttpStatusCode());

    }

    @Test
    @Disabled("Only works in production")
    void shouldRetrieveCsvPaymentReport_saveFile() throws ExecutionException, InterruptedException {

        final QueryFilterDateRange queryFilterDateRange = QueryFilterDateRange.builder()
                .from(LocalDateTime.now().minus(1, ChronoUnit.MONTHS).toInstant(ZoneOffset.UTC))
                .to(Instant.now())
                .build();

        final ContentResponse ContentResponse = getProductionCheckoutApi().reconciliationClient().retrieveCSVPaymentReport(queryFilterDateRange, "/tmp/report.csv").get();

        assertNotNull(ContentResponse);
        assertFalse(ContentResponse.getContent().isEmpty());
        assertNotNull(ContentResponse.getResponseHeaders());
        assertNotNull(ContentResponse.getBody());
    }

    @Test
    @Disabled("Only works in production")
    void shouldRetrieveCsvSingleStatementReport() throws ExecutionException, InterruptedException {

        final ContentResponse ContentResponse = getProductionCheckoutApi().reconciliationClient().retrieveCSVSingleStatementReport("statement_id", null).get();

        assertNotNull(ContentResponse);
        assertFalse(ContentResponse.getContent().isEmpty());
        assertNotNull(ContentResponse.getResponseHeaders());
        assertNotNull(ContentResponse.getBody());
        assertNotNull(ContentResponse.getHttpStatusCode());
    }

    @Test
    @Disabled("Only works in production")
    void shouldRetrieveCsvSingleStatementReport_saveFile() throws ExecutionException, InterruptedException {

        final ContentResponse ContentResponse = getProductionCheckoutApi().reconciliationClient().retrieveCSVSingleStatementReport("statement_id", "file_path").get();

        assertNotNull(ContentResponse);
        assertFalse(ContentResponse.getContent().isEmpty());
        assertNotNull(ContentResponse.getResponseHeaders());
        assertNotNull(ContentResponse.getBody());
        assertNotNull(ContentResponse.getHttpStatusCode());
    }

    @Test
    @Disabled("Only works in production")
    void shouldRetrieveCsvStatementsReport() throws ExecutionException, InterruptedException {

        final ContentResponse ContentResponse = getProductionCheckoutApi().reconciliationClient().retrieveCSVStatementsReport(queryFilterDateRange, null).get();

        assertNotNull(ContentResponse);
        assertFalse(ContentResponse.getContent().isEmpty());
        assertNotNull(ContentResponse.getResponseHeaders());
        assertNotNull(ContentResponse.getBody());
        assertNotNull(ContentResponse.getHttpStatusCode());
    }

    @Test
    @Disabled("Only works in production")
    void shouldRetrieveCsvStatementsReport_saveFile() throws ExecutionException, InterruptedException {

        final ContentResponse ContentResponse = getProductionCheckoutApi().reconciliationClient().retrieveCSVStatementsReport(queryFilterDateRange, "file_path").get();

        assertNotNull(ContentResponse);
        assertFalse(ContentResponse.getContent().isEmpty());
        assertNotNull(ContentResponse.getResponseHeaders());
        assertNotNull(ContentResponse.getBody());
        assertNotNull(ContentResponse.getHttpStatusCode());
    }

    private CheckoutApi getProductionCheckoutApi() {
        return CheckoutSdk.builder().previous()
                .staticKeys()
                .secretKey(requireNonNull(System.getenv("CHECKOUT_PREVIOUS_SECRET_KEY_PROD")))
                .environment(Environment.PRODUCTION)
                .build();
    }

}
