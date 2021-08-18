package com.checkout.payments;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CaptureTestIT extends SandboxTestFixture {

    public CaptureTestIT() {
        super(PlatformType.CLASSIC);
    }

    @Test
    public void can_fully_capture_payment() throws Exception {
        final PaymentProcessed payment = makePayment();

        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("CaptureTests", "can_fully_capture_payment");

        final CaptureRequest captureRequest = new CaptureRequest();
        captureRequest.setReference("Full Capture");
        captureRequest.setMetadata(metadata);

        final CaptureResponse captureResponse = getApi().paymentsClient().captureAsync(payment.getId(), captureRequest).get();
        assertNotNull(captureResponse);
        assertFalse(StringUtils.isEmpty(captureResponse.getActionId()));
        assertEquals(captureRequest.getReference(), captureResponse.getReference());
    }

    @Test
    public void can_partially_capture_payment() throws Exception {
        final PaymentProcessed payment = makePayment();

        final Map<String, Object> metadata = new HashMap<>();
        metadata.put("CaptureTests", "can_partially_capture_payment");

        final CaptureRequest captureRequest = new CaptureRequest();
        captureRequest.setAmount(500L);
        captureRequest.setReference("Partial Capture");
        captureRequest.setMetadata(metadata);

        final CaptureResponse captureResponse = getApi().paymentsClient().captureAsync(payment.getId(), captureRequest).get();
        assertNotNull(captureResponse);
        assertFalse(StringUtils.isEmpty(captureResponse.getActionId()));
        assertEquals(captureRequest.getReference(), captureResponse.getReference());
    }

    private PaymentProcessed makePayment() throws Exception {
        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest(1000L);
        paymentRequest.setCapture(false);

        final PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();
        assertTrue(paymentResponse.getPayment().canCapture());

        return paymentResponse.getPayment();
    }
}