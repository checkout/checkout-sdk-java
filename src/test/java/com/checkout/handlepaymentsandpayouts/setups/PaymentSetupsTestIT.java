package com.checkout.handlepaymentsandpayouts.setups;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.setups.requests.PaymentSetupsRequest;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsConfirmResponse;
import com.checkout.handlepaymentsandpayouts.setups.responses.PaymentSetupsResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for Payment Setups module.
 * To run these tests, add the environment variable CHECKOUT_DEFAULT_SECRET_KEY
 */
class PaymentSetupsTestIT extends SandboxTestFixture {

    PaymentSetupsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    @Disabled("Requires valid secret key and proper API setup")
    void shouldCreatePaymentSetup() {
        final PaymentSetupsRequest request = createBasicPaymentSetupsRequest();

        final CompletableFuture<PaymentSetupsResponse> future = 
                checkoutApi.paymentSetupsClient().createPaymentSetup(request);

        final PaymentSetupsResponse response = future.join();

        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    @Disabled("Requires valid secret key and proper API setup")
    void shouldUpdatePaymentSetup() {
        // First create a payment setup
        final PaymentSetupsRequest createRequest = createBasicPaymentSetupsRequest();
        final CompletableFuture<PaymentSetupsResponse> createFuture = 
                checkoutApi.paymentSetupsClient().createPaymentSetup(createRequest);
        final PaymentSetupsResponse createResponse = createFuture.join();

        // Then update it
        final PaymentSetupsRequest updateRequest = createBasicPaymentSetupsRequest();
        updateRequest.setReference("updated_" + Instant.now().getEpochSecond());

        final CompletableFuture<PaymentSetupsResponse> updateFuture = 
                checkoutApi.paymentSetupsClient().updatePaymentSetup(createResponse.getId(), updateRequest);

        final PaymentSetupsResponse updateResponse = updateFuture.join();

        assertNotNull(updateResponse);
        assertNotNull(updateResponse.getId());
    }

    @Test
    @Disabled("Requires valid secret key and proper API setup")
    void shouldGetPaymentSetup() {
        // First create a payment setup
        final PaymentSetupsRequest request = createBasicPaymentSetupsRequest();
        final CompletableFuture<PaymentSetupsResponse> createFuture = 
                checkoutApi.paymentSetupsClient().createPaymentSetup(request);
        final PaymentSetupsResponse createResponse = createFuture.join();

        // Then get it
        final CompletableFuture<PaymentSetupsResponse> getFuture = 
                checkoutApi.paymentSetupsClient().getPaymentSetup(createResponse.getId());

        final PaymentSetupsResponse getResponse = getFuture.join();

        assertNotNull(getResponse);
        assertNotNull(getResponse.getId());
    }

    @Test
    @Disabled("Requires valid secret key and proper API setup")
    void shouldConfirmPaymentSetup() {
        // First create a payment setup
        final PaymentSetupsRequest request = createBasicPaymentSetupsRequest();
        final CompletableFuture<PaymentSetupsResponse> createFuture = 
                checkoutApi.paymentSetupsClient().createPaymentSetup(request);
        final PaymentSetupsResponse createResponse = createFuture.join();

        // Note: This would need a real payment method option ID from the create response
        final String paymentMethodOptionId = "pmo_test_123456789"; // This should come from the create response

        final CompletableFuture<PaymentSetupsConfirmResponse> confirmFuture = 
                checkoutApi.paymentSetupsClient().confirmPaymentSetup(createResponse.getId(), paymentMethodOptionId);

        final PaymentSetupsConfirmResponse confirmResponse = confirmFuture.join();

        assertNotNull(confirmResponse);
    }

    private PaymentSetupsRequest createBasicPaymentSetupsRequest() {
        final PaymentSetupsRequest request = new PaymentSetupsRequest();

        // Set basic required properties
        request.setCurrency(Currency.EUR);
        request.setReference("setup_test_" + Instant.now().getEpochSecond());

        return request;
    }
}