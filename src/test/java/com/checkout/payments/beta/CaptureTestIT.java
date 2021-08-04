package com.checkout.payments.beta;

import com.checkout.payments.beta.capture.CaptureRequest;
import com.checkout.payments.beta.capture.CaptureResponse;
import com.checkout.payments.beta.response.PaymentResponse;
import com.checkout.payments.beta.response.source.ResponseCardSource;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CaptureTestIT extends AbstractPaymentsTestIT {

    @Test
    public void shouldCaptureCardPayment() {

        final PaymentResponse<ResponseCardSource> paymentResponse = makeCardPayment(false);

        assertTrue(paymentResponse.canCapture());

        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("CaptureTestIT", "shouldCapturePayment");

        final CaptureRequest captureRequest = CaptureRequest.builder()
                .reference("Full Capture")
                .metadata(metadata)
                .build();

        final CaptureResponse captureResponse = blocking(getApiV2().paymentsClient().capturePayment(paymentResponse.getId(), captureRequest));
        assertNotNull(captureResponse);
        assertNotNull(captureResponse.getActionId());
        assertFalse(captureResponse.getActionId().isEmpty());
        assertEquals(captureRequest.getReference(), captureResponse.getReference());

    }

    @Test
    public void shouldCaptureTokenPayment() {

        final PaymentResponse<ResponseCardSource> paymentResponse = makeTokenPayment();

        assertTrue(paymentResponse.canCapture());

        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("CaptureTestIT", "shouldCaptureTokenPayment");

        final CaptureRequest captureRequest = CaptureRequest.builder()
                .reference("Full Capture")
                .metadata(metadata)
                .build();

        final CaptureResponse captureResponse = blocking(getApiV2().paymentsClient().capturePayment(paymentResponse.getId(), captureRequest));
        assertNotNull(captureResponse);
        assertNotNull(captureResponse.getActionId());
        assertFalse(captureResponse.getActionId().isEmpty());
        assertEquals(captureRequest.getReference(), captureResponse.getReference());

    }

    @Test
    public void shouldCapturePaymentPartially() {

        final PaymentResponse<ResponseCardSource> paymentResponse = makeCardPayment(false);

        assertTrue(paymentResponse.canCapture());

        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("CaptureTestIT", "shouldCapturePaymentPartially");

        final CaptureRequest captureRequest = CaptureRequest.builder()
                .amount(5)
                .reference("Partial Capture")
                .metadata(metadata)
                .build();

        final CaptureResponse captureResponse = blocking(getApiV2().paymentsClient().capturePayment(paymentResponse.getId(), captureRequest));
        assertNotNull(captureResponse);
        assertNotNull(captureResponse.getActionId());
        assertFalse(captureResponse.getActionId().isEmpty());
        assertEquals(captureRequest.getReference(), captureResponse.getReference());

    }

}
