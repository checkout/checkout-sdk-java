package com.checkout.payments;

import com.checkout.ApiTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.CheckoutUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CaptureTests extends ApiTestFixture {
    @Test
    public void can_fully_capture_payment() throws Exception {
        PaymentProcessed payment = makePayment();

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("CaptureTests", "can_fully_capture_payment");

        CaptureRequest captureRequest = new CaptureRequest();
        captureRequest.setReference("Full Capture");
        captureRequest.setMetadata(metadata);

        CaptureResponse captureResponse = getApi().paymentsClient().captureAsync(payment.getId(), captureRequest).get();
        Assert.assertNotNull(captureResponse);
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(captureResponse.getActionId()));
        Assert.assertEquals(captureRequest.getReference(), captureResponse.getReference());
    }

    @Test
    public void can_partially_capture_payment() throws Exception {
        PaymentProcessed payment = makePayment();

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("CaptureTests", "can_partially_capture_payment");

        CaptureRequest captureRequest = new CaptureRequest();
        captureRequest.setAmount(500);
        captureRequest.setReference("Partial Capture");
        captureRequest.setMetadata(metadata);

        CaptureResponse captureResponse = getApi().paymentsClient().captureAsync(payment.getId(), captureRequest).get();
        Assert.assertNotNull(captureResponse);
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(captureResponse.getActionId()));
        Assert.assertEquals(captureRequest.getReference(), captureResponse.getReference());
    }

    private PaymentProcessed makePayment() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest(1000);
        paymentRequest.setCapture(false);

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();
        Assert.assertTrue(paymentResponse.getPayment().canCapture());

        return paymentResponse.getPayment();
    }
}