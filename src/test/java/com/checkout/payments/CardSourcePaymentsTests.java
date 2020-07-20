package com.checkout.payments;

import com.checkout.ApiTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.CheckoutUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class CardSourcePaymentsTests extends ApiTestFixture {
    @Test
    public void can_request_non_3ds_card_payment() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setThreeDS(ThreeDSRequest.from(false));

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        Assert.assertNotNull(paymentResponse.getPayment());
        Assert.assertTrue(paymentResponse.getPayment().isApproved());
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(paymentResponse.getPayment().getId()));
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(paymentResponse.getPayment().getActionId()));
        Assert.assertEquals(paymentRequest.getAmount().intValue(), paymentResponse.getPayment().getAmount());
        Assert.assertEquals(paymentRequest.getCurrency(), paymentResponse.getPayment().getCurrency());
        Assert.assertEquals(paymentRequest.getReference(), paymentResponse.getPayment().getReference());
        Assert.assertNotNull(paymentResponse.getPayment().getCustomer());
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(paymentResponse.getPayment().getCustomer().getId()));
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(paymentResponse.getPayment().getCustomer().getEmail()));
        Assert.assertTrue(paymentResponse.getPayment().canCapture());
        Assert.assertTrue(paymentResponse.getPayment().canVoid());
        Assert.assertNotNull(paymentResponse.getPayment().getSource());
        Assert.assertFalse(paymentRequest.getSource().isStored());
    }

    @Test
    public void can_request_3ds_card_payment() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setThreeDS(ThreeDSRequest.from(true));

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        Assert.assertTrue(paymentResponse.isPending());
        PaymentPending pending = paymentResponse.getPending();

        Assert.assertNotNull(pending);

        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(pending.getId()));
        Assert.assertEquals(paymentRequest.getReference(), pending.getReference());
        Assert.assertNotNull(pending.getCustomer());
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(pending.getCustomer().getId()));
        Assert.assertEquals(paymentRequest.getCustomer().getEmail(), pending.getCustomer().getEmail());
        Assert.assertNotNull(pending.getThreeDS());
        Assert.assertFalse(pending.getThreeDS().isDowngraded());
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(pending.getThreeDS().getEnrolled()));
        Assert.assertTrue(pending.requiresRedirect());
        Assert.assertNotNull(pending.getRedirectLink());
    }

    @Test
    public void can_void_payment() throws Exception {
        // Auth
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();
        Assert.assertTrue(paymentResponse.getPayment().canVoid());

        VoidRequest voidRequest = new VoidRequest();
        voidRequest.setReference(UUID.randomUUID().toString());

        // Void Auth
        VoidResponse voidResponse = getApi().paymentsClient().voidAsync(paymentResponse.getPayment().getId(), voidRequest).get();

        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(voidResponse.getActionId()));
        Assert.assertEquals(voidRequest.getReference(), voidResponse.getReference());
    }

    @Test
    public void can_refund_payment() throws Exception {
        // Auth
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();
        Assert.assertTrue(paymentResponse.getPayment().canCapture());

        // Capture
        getApi().paymentsClient().captureAsync(paymentResponse.getPayment().getId()).get();

        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setReference(UUID.randomUUID().toString());

        // Refund
        RefundResponse refundResponse = getApi().paymentsClient().refundAsync(paymentResponse.getPayment().getId(), refundRequest).get();

        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(refundResponse.getActionId()));
        Assert.assertEquals(refundRequest.getReference(), refundResponse.getReference());
    }
}
