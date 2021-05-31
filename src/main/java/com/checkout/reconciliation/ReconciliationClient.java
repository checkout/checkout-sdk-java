package com.checkout.reconciliation;


import java.time.Instant;
import java.util.concurrent.CompletableFuture;

public interface ReconciliationClient {
    CompletableFuture<PaymentReportResponse> paymentsReportAsync(Instant from, Instant to, String reference, Integer limit);
    CompletableFuture<PaymentReportResponse> singlePaymentReportAsync(String paymentId);
    CompletableFuture<StatementReportResponse> statementsReportAsync(Instant from, Instant to);

}
