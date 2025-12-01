package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.handlepaymentsandpayouts.setups.requests.PaymentSetupsRequest;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsResponse;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsConfirmResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Client interface for payment setup operations.
 */
public interface IPaymentSetupsClient {

    /**
     * Creates a new payment setup.
     *
     * @param paymentSetupsRequest The payment setup request
     * @return CompletableFuture containing the payment setup response
     */
    CompletableFuture<PaymentSetupsResponse> createPaymentSetup(PaymentSetupsRequest paymentSetupsRequest);

    /**
     * Updates an existing payment setup.
     *
     * @param paymentSetupId The payment setup ID
     * @param paymentSetupsRequest The payment setup request
     * @return CompletableFuture containing the payment setup response
     */
    CompletableFuture<PaymentSetupsResponse> updatePaymentSetup(String paymentSetupId, PaymentSetupsRequest paymentSetupsRequest);

    /**
     * Retrieves a payment setup by ID.
     *
     * @param paymentSetupId The payment setup ID
     * @return CompletableFuture containing the payment setup response
     */
    CompletableFuture<PaymentSetupsResponse> getPaymentSetup(String paymentSetupId);

    /**
     * Confirms a payment setup.
     *
     * @param paymentSetupId The payment setup ID
     * @param paymentMethodOptionId The payment method option ID
     * @return CompletableFuture containing the payment setup confirmation response
     */
    CompletableFuture<PaymentSetupsConfirmResponse> confirmPaymentSetup(String paymentSetupId, String paymentMethodOptionId);
}