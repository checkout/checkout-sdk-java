package com.checkout.paymentmethods;

import java.util.concurrent.CompletableFuture;

import com.checkout.paymentmethods.requests.PaymentMethodsQuery;
import com.checkout.paymentmethods.responses.PaymentMethodsResponse;

public interface PaymentMethodsClient {

    /**
     * Get available payment methods 
     * <b>Beta</b>
     * Get a list of all available payment methods for a specific Processing Channel ID.
     *
     * @param paymentMethodsQuery The query parameters including processing_channel_id
     * @return CompletableFuture containing the payment methods response
     */
    CompletableFuture<PaymentMethodsResponse> getPaymentMethods(PaymentMethodsQuery paymentMethodsQuery);

    // Synchronous methods
    PaymentMethodsResponse getPaymentMethodsSync(PaymentMethodsQuery paymentMethodsQuery);

}