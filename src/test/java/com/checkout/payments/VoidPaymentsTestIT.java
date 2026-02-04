package com.checkout.payments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.checkout.payments.response.PaymentResponse;

class VoidPaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldVoidCardPayment() {
        final PaymentResponse paymentResponse = makeCardPayment(false);
        final VoidRequest voidRequest = createVoidRequest();

        final VoidResponse voidResponse = blocking(() -> paymentsClient.voidPayment(paymentResponse.getId(), voidRequest));

        validateVoidResponse(voidResponse);
    }

    @Test
    void shouldVoidIdSourcePayment() {
        final PaymentResponse paymentResponse = makeIdSourcePayment();
        final VoidRequest voidRequest = createVoidRequest();

        final VoidResponse voidResponse = blocking(() -> paymentsClient.voidPayment(paymentResponse.getId(), voidRequest));

        validateVoidResponse(voidResponse);
    }

    @Test
    void shouldVoidTokenSourcePayment() {
        final PaymentResponse paymentResponse = makeTokenPayment();
        final VoidRequest voidRequest = createVoidRequest();

        final VoidResponse voidResponse = blocking(() -> paymentsClient.voidPayment(paymentResponse.getId(), voidRequest));

        validateVoidResponse(voidResponse);
    }

    // Synchronous methods
    @Test
    void shouldVoidCardPaymentSync() {
        final PaymentResponse paymentResponse = makeCardPayment(false);
        final VoidRequest voidRequest = createVoidRequest();

        final VoidResponse voidResponse = paymentsClient.voidPaymentSync(paymentResponse.getId(), voidRequest);

        validateVoidResponse(voidResponse);
    }

    @Test
    void shouldVoidIdSourcePaymentSync() {
        final PaymentResponse paymentResponse = makeIdSourcePayment();
        final VoidRequest voidRequest = createVoidRequest();

        final VoidResponse voidResponse = paymentsClient.voidPaymentSync(paymentResponse.getId(), voidRequest);

        validateVoidResponse(voidResponse);
    }

    @Test
    void shouldVoidTokenSourcePaymentSync() {
        final PaymentResponse paymentResponse = makeTokenPayment();
        final VoidRequest voidRequest = createVoidRequest();

        final VoidResponse voidResponse = paymentsClient.voidPaymentSync(paymentResponse.getId(), voidRequest);

        validateVoidResponse(voidResponse);
    }

    // Common methods
    private VoidRequest createVoidRequest() {
        return VoidRequest.builder()
                .reference(UUID.randomUUID().toString())
                .build();
    }

    private void validateVoidResponse(VoidResponse voidResponse) {
        assertNotNull(voidResponse);
        assertNotNull(voidResponse.getActionId());
        assertNotNull(voidResponse.getReference());
        assertEquals(1, voidResponse.getLinks().size());
    }

}
