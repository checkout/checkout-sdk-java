package com.checkout.payments.four;

import com.checkout.payments.four.refund.RefundRequest;
import com.checkout.payments.four.refund.RefundResponse;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.request.source.RequestCardSource;
import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.payments.four.response.source.ResponseCardSource;
import com.checkout.payments.four.sender.RequestCorporateSender;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.checkout.payments.four.CardSourceHelper.getCardSourcePayment;
import static com.checkout.payments.four.CardSourceHelper.getCorporateSender;
import static com.checkout.payments.four.CardSourceHelper.getRequestCardSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RefundPaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldRefundCardPayment() {

        final RequestCardSource source = getRequestCardSource();
        final RequestCorporateSender sender = getCorporateSender();
        final PaymentRequest request = getCardSourcePayment(source, sender, false);

        // payment
        final PaymentResponse<ResponseCardSource> paymentResponse = makeCardPayment(request);
        assertNotNull(paymentResponse.getLink("capture"));

        // capture
        capturePayment(paymentResponse.getId());

        nap();

        // refund
        final RefundRequest refundRequest = RefundRequest.builder()
                .reference(UUID.randomUUID().toString())
                .build();

        final RefundResponse refundResponse = blocking(paymentsClient.refundPayment(paymentResponse.getId(), refundRequest));

        assertNotNull(refundResponse);
        assertNotNull(refundResponse.getActionId());
        assertNotNull(refundResponse.getReference());
        assertEquals(1, refundResponse.getLinks().size());

    }

    @Test
    void shouldRefundTokenPayment() {

        // Make Payment
        final PaymentResponse<ResponseCardSource> paymentResponse = makeTokenPayment();
        assertNotNull(paymentResponse.getLink("capture"));

        // Capture Payment
        capturePayment(paymentResponse.getId());

        nap();

        // Refund
        final RefundRequest refundRequest = RefundRequest.builder()
                .reference(UUID.randomUUID().toString())
                .build();

        final RefundResponse refundResponse = blocking(paymentsClient.refundPayment(paymentResponse.getId(), refundRequest));

        assertNotNull(refundResponse);
        assertNotNull(refundResponse.getActionId());
        assertNotNull(refundResponse.getReference());
        assertEquals(1, refundResponse.getLinks().size());

    }

}
