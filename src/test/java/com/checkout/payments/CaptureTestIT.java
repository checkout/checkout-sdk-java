package com.checkout.payments;

import com.checkout.payments.response.PaymentResponse;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CaptureTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldCapturePayment() {

        final PaymentResponse paymentResponse = makeCardPayment(false, 10L);

        assertNotNull(paymentResponse.getLink("capture"));

        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("CaptureTestIT", "shouldCapturePayment");

        final CaptureRequest captureRequest = CaptureRequest.builder()
                .reference("Full Capture")
                .metadata(metadata)
                .build();

        final CaptureResponse captureResponse = blocking(() -> defaultApi.paymentsClient().capturePayment(paymentResponse.getId(), captureRequest));
        assertNotNull(captureResponse);
        assertNotNull(captureResponse.getActionId());
        assertFalse(captureResponse.getActionId().isEmpty());
        assertEquals(captureRequest.getReference(), captureResponse.getReference());

    }

    @Test
    void shouldCapturePaymentPartially() {

        final PaymentResponse paymentResponse = makeCardPayment(false, 10L);

        assertNotNull(paymentResponse.getLink("capture"));

        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("CaptureTestIT", "shouldCapturePaymentPartially");

        final CaptureRequest captureRequest = CaptureRequest.builder()
                .amount(5L)
                .reference("Partial Capture")
                .metadata(metadata)
                .build();

        final CaptureResponse captureResponse = blocking(() -> defaultApi.paymentsClient().capturePayment(paymentResponse.getId(), captureRequest));
        assertNotNull(captureResponse);
        assertNotNull(captureResponse.getActionId());
        assertFalse(captureResponse.getActionId().isEmpty());
        assertEquals(captureRequest.getReference(), captureResponse.getReference());

    }

    @Test
    void shouldCapturePaymentIdempotently() {

        final PaymentResponse paymentResponse = makeCardPayment(false, 10L);

        assertNotNull(paymentResponse.getLink("capture"));

        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("CaptureTestIT", "shouldCapturePayment");

        final CaptureRequest captureRequest = CaptureRequest.builder()
                .reference("Full Capture")
                .metadata(metadata)
                .build();

        final CaptureResponse captureResponse1 = blocking(() -> defaultApi.paymentsClient().capturePayment(paymentResponse.getId(), captureRequest, IDEMPOTENCY_KEY));
        assertNotNull(captureResponse1);

        final CaptureResponse captureResponse2 = blocking(() -> defaultApi.paymentsClient().capturePayment(paymentResponse.getId(), captureRequest, IDEMPOTENCY_KEY));
        assertNotNull(captureResponse2);

        assertEquals(captureResponse1.getActionId(), captureResponse2.getActionId());

    }

}
