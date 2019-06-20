package com.checkout.payments;

import com.checkout.ApiTestFixture;
import com.checkout.TestHelper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class CardVerificationTests extends ApiTestFixture {
    @Test
    public void can_verify_card() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest(0);
        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        Assert.assertEquals(PaymentStatus.CARD_VERIFIED, paymentResponse.getPayment().getStatus());
    }
}