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

class RefundPaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldRefundCardPayment() {

        final RequestCardSource source = getRequestCardSource();
        final PaymentCorporateSender sender = getCorporateSender();
        final PaymentRequest request = getCardSourcePayment(source, sender, false);

        // payment
        final PaymentResponse paymentResponse = makeCardPayment(request);
        assertNotNull(paymentResponse.getLink("capture"));

        // capture
        capturePayment(paymentResponse.getId());

        // refund
        final RefundRequest refundRequest = RefundRequest.builder()
                .reference(UUID.randomUUID().toString())
                .build();

        final RefundResponse refundResponse = blocking(() -> paymentsClient.refundPayment(paymentResponse.getId(), refundRequest));

        assertNotNull(refundResponse);
        assertNotNull(refundResponse.getActionId());
        assertNotNull(refundResponse.getReference());
        assertEquals(1, refundResponse.getLinks().size());

    }

    @Test
    void shouldRefundTokenPayment() {

        // Make Payment
        final PaymentResponse paymentResponse = makeTokenPayment();
        assertNotNull(paymentResponse.getLink("capture"));

        // Capture Payment
        capturePayment(paymentResponse.getId());

        // Refund
        final RefundRequest refundRequest = RefundRequest.builder()
                .reference(UUID.randomUUID().toString())
                .build();

        final RefundResponse refundResponse = blocking(() -> paymentsClient.refundPayment(paymentResponse.getId(), refundRequest));

        assertNotNull(refundResponse);
        assertNotNull(refundResponse.getActionId());
        assertNotNull(refundResponse.getReference());
        assertEquals(1, refundResponse.getLinks().size());

    }

}
