package com.checkout.reconciliation;

import com.checkout.ApiClient;
import com.checkout.ApiCredentials;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ReconciliationClientImpl implements ReconciliationClient {

    private final ApiClient apiClient;
    private final ApiCredentials credentials;

    public ReconciliationClientImpl(ApiClient apiClient, CheckoutConfiguration configuration) {
        if (apiClient == null) {
            throw new IllegalArgumentException("apiClient must not be null");
        }
        if (configuration == null) {
            throw new IllegalArgumentException("configuration must not be null");
        }

        this.apiClient = apiClient;
        credentials = new SecretKeyCredentials(configuration);
    }


    @Override
    public CompletableFuture<PaymentReportResponse> paymentsReportAsync(Instant from, Instant to, String reference, Integer limit) {
        String path = "reporting/payments";
        List<String> parameters = new ArrayList<>();
        if(from != null) {
            parameters.add("from=" + from.toString());
        }
        if(to != null){
            parameters.add("to=" + to.toString());
        }
        if(reference != null){
            parameters.add("reference=" + reference);
        }
        if(limit != null){
            parameters.add("limit=" + limit.toString());
        }

        if(!parameters.isEmpty()){
            path += "?" + String.join("&", parameters);
        }

        return apiClient.getAsync(path, credentials, PaymentReportResponse.class);
    }

    @Override
    public CompletableFuture<PaymentReportResponse> singlePaymentReportAsync(String paymentId) {
        return apiClient.getAsync("reporting/payments/" + paymentId, credentials, PaymentReportResponse.class);
    }

    @Override
    public CompletableFuture<StatementReportResponse> statementsReportAsync(Instant from, Instant to) {
        String path = "reporting/statements";
        List<String> parameters = new ArrayList<>();
        if(from != null) {
            parameters.add("from=" + from.toString());
        }
        if(to != null){
            parameters.add("to=" + to.toString());
        }

        if(!parameters.isEmpty()){
            path += "?" + String.join("&", parameters);
        }

        return apiClient.getAsync(path, credentials, StatementReportResponse.class);
    }
}
