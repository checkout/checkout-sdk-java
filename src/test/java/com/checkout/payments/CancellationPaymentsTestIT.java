package com.checkout.payments;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.checkout.payments.response.PaymentResponse;

/**
 * Integration tests for POST /payments/{id}/cancellations.
 *
 * The cancellation endpoint cancels an upcoming scheduled payment retry.
 * It requires a payment that has a scheduled retry pending, which is not
 * reproducible on demand in sandbox — tests are @Disabled accordingly.
 * Enable them manually with a valid payment ID that has a pending retry.
 */
@Disabled("Requires a payment with a pending scheduled retry — enable manually with a valid paymentId")
class CancellationPaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldCancelPayment() {
        final PaymentResponse paymentResponse = makeCardPayment(false);
        final CancellationRequest request = createCancellationRequest();

        final CancellationAcceptedResponse response = blocking(() ->
                paymentsClient.cancelPayment(paymentResponse.getId(), request));

        validateCancellationResponse(response);
    }

    @Test
    void shouldCancelPaymentWithoutBody() {
        final PaymentResponse paymentResponse = makeCardPayment(false);

        final CancellationAcceptedResponse response = blocking(() ->
                paymentsClient.cancelPayment(paymentResponse.getId()));

        validateCancellationResponse(response);
    }

    // Sync

    @Test
    void shouldCancelPaymentSync() {
        final PaymentResponse paymentResponse = makeCardPayment(false);
        final CancellationRequest request = createCancellationRequest();

        final CancellationAcceptedResponse response =
                paymentsClient.cancelPaymentSync(paymentResponse.getId(), request);

        validateCancellationResponse(response);
    }

    @Test
    void shouldCancelPaymentWithoutBodySync() {
        final PaymentResponse paymentResponse = makeCardPayment(false);

        final CancellationAcceptedResponse response =
                paymentsClient.cancelPaymentSync(paymentResponse.getId());

        validateCancellationResponse(response);
    }

    // Common methods

    private CancellationRequest createCancellationRequest() {
        return CancellationRequest.builder()
                .reference(UUID.randomUUID().toString())
                .build();
    }

    private void validateCancellationResponse(final CancellationAcceptedResponse response) {
        assertNotNull(response);
        assertNotNull(response.getActionId());
    }
}
