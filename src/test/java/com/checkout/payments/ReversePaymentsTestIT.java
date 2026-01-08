package com.checkout.payments;

import static com.checkout.CardSourceHelper.getCardSourcePayment;
import static com.checkout.CardSourceHelper.getCorporateSender;
import static com.checkout.CardSourceHelper.getRequestCardSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.RequestCardSource;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.sender.PaymentCorporateSender;

class ReversePaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldReversePayment() {
        final PaymentResponse paymentResponse = makeCardPaymentForReverse();
        final String reference = UUID.randomUUID().toString();
        final ReverseRequest reverseRequest = createReverseRequest(reference);

        final ReverseResponse reverseResponse = blocking(() -> paymentsClient.reversePayment(paymentResponse.getId(), reverseRequest));

        validateReverseResponse(reverseResponse, reference);
    }

    @Test
    void shouldReversePayment_idempotencyKey() {
        final PaymentResponse paymentResponse = makeCardPaymentForReverse();
        final String reference = UUID.randomUUID().toString();
        final String idempotencyKey = UUID.randomUUID().toString();
        final ReverseRequest reverseRequest = createReverseRequest(reference);

        final ReverseResponse reverseResponse = blocking(() -> paymentsClient.reversePayment(paymentResponse.getId(), reverseRequest, idempotencyKey));
        validateReverseResponse(reverseResponse, reference);
        final ReverseResponse reverseResponse_2 = blocking(() -> paymentsClient.reversePayment(paymentResponse.getId(), reverseRequest, idempotencyKey));
        validateReverseResponse(reverseResponse_2, reference);

        assertEquals(reverseResponse.getActionId(), reverseResponse_2.getActionId());
    }

    // Synchronous methods
    @Test
    void shouldReversePaymentSync() {
        final PaymentResponse paymentResponse = makeCardPaymentForReverse();
        final String reference = UUID.randomUUID().toString();
        final ReverseRequest reverseRequest = createReverseRequest(reference);

        final ReverseResponse reverseResponse = paymentsClient.reversePaymentSync(paymentResponse.getId(), reverseRequest);

        validateReverseResponse(reverseResponse, reference);
    }

    @Test
    void shouldReversePaymentSync_idempotencyKey() {
        final PaymentResponse paymentResponse = makeCardPaymentForReverse();
        final String reference = UUID.randomUUID().toString();
        final String idempotencyKey = UUID.randomUUID().toString();
        final ReverseRequest reverseRequest = createReverseRequest(reference);

        final ReverseResponse reverseResponse = paymentsClient.reversePaymentSync(paymentResponse.getId(), reverseRequest, idempotencyKey);
        validateReverseResponse(reverseResponse, reference);
        final ReverseResponse reverseResponse_2 = paymentsClient.reversePaymentSync(paymentResponse.getId(), reverseRequest, idempotencyKey);
        validateReverseResponse(reverseResponse_2, reference);

        assertEquals(reverseResponse.getActionId(), reverseResponse_2.getActionId());
    }

    // Common methods
    private ReverseRequest createReverseRequest(String reference) {
        return ReverseRequest.builder()
                .reference(reference)
                .build();
    }

    private PaymentResponse makeCardPaymentForReverse() {
        final RequestCardSource source = getRequestCardSource();
        final PaymentCorporateSender sender = getCorporateSender();
        final PaymentRequest request = getCardSourcePayment(source, sender, false);

        final PaymentResponse paymentResponse = makeCardPayment(request);
        assertNotNull(paymentResponse.getLink("capture"));
        return paymentResponse;
    }

    private void validateReverseResponse(ReverseResponse reverseResponse, String expectedReference) {
        assertNotNull(reverseResponse);
        assertNotNull(reverseResponse.getReference());
        assertEquals(expectedReference, reverseResponse.getReference());
    }

}
