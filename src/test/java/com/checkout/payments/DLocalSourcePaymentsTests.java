package com.checkout.payments;

import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.CheckoutUtils;
import org.junit.Assert;
import org.junit.Test;

public class DLocalSourcePaymentsTests extends SandboxTestFixture {
    @Test
    public void can_request_non_3ds_card_payment() throws Exception {
        PaymentRequest<DLocalSource> paymentRequest = TestHelper.createDLocalPaymentRequest();
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
        Assert.assertTrue(paymentResponse.getPayment().getSource() instanceof CardSourceResponse);
        Assert.assertFalse(paymentRequest.getSource().getStored());
    }
}