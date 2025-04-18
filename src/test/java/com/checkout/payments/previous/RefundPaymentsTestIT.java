package com.checkout.payments.previous;

import com.checkout.payments.RefundRequest;
import com.checkout.payments.RefundResponse;
import com.checkout.payments.previous.response.PaymentResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("unavailable")
class RefundPaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    @Disabled("unavailable")
    void shouldRefundPayment() {

        final PaymentResponse paymentResponse = makeCardPayment(true, 10);

        final RefundRequest refundRequest = RefundRequest.builder()
                .reference(UUID.randomUUID().toString())
                .build();

        refundRequest.getMetadata().put("test", "1234");

        final RefundResponse refundResponse = blocking(() -> paymentsClient.refundPayment(paymentResponse.getId(), refundRequest));
        assertNotNull(refundResponse);

        assertNotNull(refundResponse.getActionId());
        assertNotNull(refundResponse.getReference());
        assertEquals(1, refundResponse.getLinks().size());

    }

    @Test
    @Disabled("unavailable")
    void shouldRefundPaymentIdempotently() {

        final PaymentResponse paymentResponse = makeCardPayment(true, 10);

        final RefundRequest refundRequest = RefundRequest.builder()
                .reference(UUID.randomUUID().toString())
                .build();

        refundRequest.getMetadata().put("test", "1234");

        final RefundResponse refundResponse1 = blocking(() -> paymentsClient.refundPayment(paymentResponse.getId(), refundRequest, IDEMPOTENCY_KEY));
        assertNotNull(refundResponse1);

        final RefundResponse refundResponse2 = blocking(() -> paymentsClient.refundPayment(paymentResponse.getId(), refundRequest, IDEMPOTENCY_KEY));
        assertNotNull(refundResponse2);

        assertEquals(refundResponse1.getActionId(), refundResponse2.getActionId());

    }

}
