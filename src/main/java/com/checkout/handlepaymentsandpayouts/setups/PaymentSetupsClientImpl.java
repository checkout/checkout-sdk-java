package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.handlepaymentsandpayouts.setups.requests.PaymentSetupsRequest;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsResponse;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsConfirmResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

/**
 * Implementation of the payment setup client.
 */
public class PaymentSetupsClientImpl extends AbstractClient implements PaymentSetupsClient {

    private static final String PAYMENT_SETUPS_PATH = "payments/setups";
    private static final String CONFIRM_PATH = "confirm";

    public PaymentSetupsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    /**
     * Creates a new payment setup.
     *
     * @param paymentSetupsRequest The payment setup request
     * @return CompletableFuture containing the payment setup response
     */
    @Override
    public CompletableFuture<PaymentSetupsResponse> createPaymentSetup(
            final PaymentSetupsRequest paymentSetupsRequest) {
        validateParams("paymentSetupsRequest", paymentSetupsRequest);
        return apiClient.postAsync(PAYMENT_SETUPS_PATH, sdkAuthorization(), PaymentSetupsResponse.class,
                paymentSetupsRequest, null);
    }

    /**
     * Updates an existing payment setup.
     *
     * @param id                   The payment setup ID
     * @param paymentSetupsRequest The payment setup request
     * @return CompletableFuture containing the payment setup response
     */
    @Override
    public CompletableFuture<PaymentSetupsResponse> updatePaymentSetup(final String id,
            final PaymentSetupsRequest paymentSetupsRequest) {
        validateParams("id", id, "paymentSetupsRequest", paymentSetupsRequest);
        return apiClient.putAsync(buildPath(PAYMENT_SETUPS_PATH, id), sdkAuthorization(), PaymentSetupsResponse.class,
                paymentSetupsRequest);
    }

    /**
     * Retrieves a payment setup by ID.
     *
     * @param id The payment setup ID
     * @return CompletableFuture containing the payment setup response
     */
    @Override
    public CompletableFuture<PaymentSetupsResponse> getPaymentSetup(final String id) {
        validateParams("id", id);
        return apiClient.getAsync(buildPath(PAYMENT_SETUPS_PATH, id), sdkAuthorization(), PaymentSetupsResponse.class);
    }

    /**
     * Confirms a payment setup.
     *
     * @param id                    The payment setup ID
     * @param paymentMethodOptionId The payment method option ID
     * @return CompletableFuture containing the payment setup confirmation response
     */
    @Override
    public CompletableFuture<PaymentSetupsConfirmResponse> confirmPaymentSetup(final String id,
            final String paymentMethodOptionId) {
        validateParams("id", id, "paymentMethodOptionId", paymentMethodOptionId);
        return apiClient.postAsync(buildPath(PAYMENT_SETUPS_PATH, id, CONFIRM_PATH, paymentMethodOptionId),
                sdkAuthorization(), PaymentSetupsConfirmResponse.class, null, null);
    }
}