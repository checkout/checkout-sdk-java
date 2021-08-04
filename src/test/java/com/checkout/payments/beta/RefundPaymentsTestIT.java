package com.checkout.payments.beta;

import com.checkout.payments.beta.refund.RefundRequest;
import com.checkout.payments.beta.refund.RefundResponse;
import com.checkout.payments.beta.request.PaymentRequest;
import com.checkout.payments.beta.request.source.RequestCardSource;
import com.checkout.payments.beta.response.PaymentResponse;
import com.checkout.payments.beta.response.source.ResponseCardSource;
import com.checkout.payments.beta.sender.RequestCorporateSender;
import org.junit.Test;

import java.util.UUID;

import static com.checkout.payments.beta.CardSourceHelper.getCardSourcePayment;
import static com.checkout.payments.beta.CardSourceHelper.getCorporateSender;
import static com.checkout.payments.beta.CardSourceHelper.getRequestCardSource;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class RefundPaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    public void shouldRefundCardPayment() {

        final RequestCardSource source = getRequestCardSource();
        final RequestCorporateSender sender = getCorporateSender();
        final PaymentRequest request = getCardSourcePayment(source, sender, false);

        // payment
        final PaymentResponse<ResponseCardSource> paymentResponse = makeCardPayment(request);
        assertTrue(paymentResponse.canCapture());

        // capture
        capturePayment(paymentResponse.getId());

        waitForIt();

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
    public void shouldRefundTokenPayment() {

        // Make Payment
        final PaymentResponse<ResponseCardSource> paymentResponse = makeTokenPayment();
        assertTrue(paymentResponse.canCapture());

        // Capture Payment
        capturePayment(paymentResponse.getId());

        waitForIt();

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
