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

    public PaymentSetupsClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<PaymentSetupsResponse> createPaymentSetup(final PaymentSetupsRequest paymentSetupsRequest) {
        validateParams("paymentSetupsRequest", paymentSetupsRequest);
        return apiClient.postAsync(PAYMENT_SETUPS_PATH, sdkAuthorization(), PaymentSetupsResponse.class, paymentSetupsRequest, null);
    }

    @Override
    public CompletableFuture<PaymentSetupsResponse> updatePaymentSetup(final String paymentSetupId, final PaymentSetupsRequest paymentSetupsRequest) {
        validateParams("paymentSetupId", paymentSetupId, "paymentSetupsRequest", paymentSetupsRequest);
        return apiClient.putAsync(buildPath(PAYMENT_SETUPS_PATH, paymentSetupId), sdkAuthorization(), PaymentSetupsResponse.class, paymentSetupsRequest);
    }

    @Override
    public CompletableFuture<PaymentSetupsResponse> getPaymentSetup(final String paymentSetupId) {
        validateParams("paymentSetupId", paymentSetupId);
        return apiClient.getAsync(buildPath(PAYMENT_SETUPS_PATH, paymentSetupId), sdkAuthorization(), PaymentSetupsResponse.class);
    }

    @Override
    public CompletableFuture<PaymentSetupsConfirmResponse> confirmPaymentSetup(final String paymentSetupId, final String paymentMethodOptionId) {
        validateParams("paymentSetupId", paymentSetupId, "paymentMethodOptionId", paymentMethodOptionId);
        return apiClient.postAsync(buildPath("payments", "setups", paymentSetupId, "confirm", paymentMethodOptionId), sdkAuthorization(), PaymentSetupsConfirmResponse.class, null, null);
    }
}