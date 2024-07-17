package com.checkout.payments;

import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.RequestCardSource;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.sender.PaymentCorporateSender;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.checkout.CardSourceHelper.getCardSourcePayment;
import static com.checkout.CardSourceHelper.getCorporateSender;
import static com.checkout.CardSourceHelper.getRequestCardSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReversePaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldReversePayment() {

        final RequestCardSource source = getRequestCardSource();
        final PaymentCorporateSender sender = getCorporateSender();
        final PaymentRequest request = getCardSourcePayment(source, sender, false);

        // payment
        final PaymentResponse paymentResponse = makeCardPayment(request);
        assertNotNull(paymentResponse.getLink("capture"));

        final String reference = UUID.randomUUID().toString();

        // reverse
        final ReverseRequest reverseRequest = ReverseRequest.builder()
                .reference(reference)
                .build();

        final ReverseResponse reverseResponse = blocking(() -> paymentsClient.reversePayment(paymentResponse.getId(), reverseRequest));

        assertNotNull(reverseResponse);
        assertNotNull(reverseResponse.getReference());
        assertEquals(reference, reverseResponse.getReference());

    }

    @Test
    void shouldReversePayment_idempotencyKey() {

        final RequestCardSource source = getRequestCardSource();
        final PaymentCorporateSender sender = getCorporateSender();
        final PaymentRequest request = getCardSourcePayment(source, sender, false);

        // payment
        final PaymentResponse paymentResponse = makeCardPayment(request);
        assertNotNull(paymentResponse.getLink("capture"));

        final String reference = UUID.randomUUID().toString();
        final String idempotencyKey = UUID.randomUUID().toString();

        // reverse
        final ReverseRequest reverseRequest = ReverseRequest.builder()
                .reference(reference)
                .build();

        final ReverseResponse reverseResponse = blocking(() -> paymentsClient.reversePayment(paymentResponse.getId(), reverseRequest, idempotencyKey));

        assertNotNull(reverseResponse);
        assertNotNull(reverseResponse.getReference());
        assertEquals(reference, reverseResponse.getReference());

        final ReverseResponse reverseResponse_2 = blocking(() -> paymentsClient.reversePayment(paymentResponse.getId(), reverseRequest, idempotencyKey));

        assertNotNull(reverseResponse_2);
        assertNotNull(reverseResponse_2.getReference());
        assertEquals(reference, reverseResponse_2.getReference());

        assertEquals(reverseResponse.getActionId(), reverseResponse_2.getActionId());

    }

}
