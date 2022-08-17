package com.checkout.payments;

import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.CheckoutUtils;
import org.junit.Assert;
import org.junit.Test;

public class CardDestinationPaymentTests extends SandboxTestFixture {
    @Test
    public void can_perform_card_payout() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPayoutRequest();
        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        if (paymentResponse.getPending() != null) {
            final GetPaymentResponse payment = getApi().paymentsClient().getAsync(paymentResponse.getPending().getId()).get();
            Assert.assertNotNull(payment);
            Assert.assertFalse(CheckoutUtils.isNullOrEmpty(payment.getId()));
            Assert.assertEquals(paymentRequest.getAmount(), payment.getAmount());
            Assert.assertEquals(paymentRequest.getCurrency(), payment.getCurrency());
            Assert.assertEquals(paymentRequest.getReference(), payment.getReference());
        } else {
            Assert.assertNotNull(paymentResponse.getPayment());
            Assert.assertTrue(paymentResponse.getPayment().isApproved());
            Assert.assertFalse(CheckoutUtils.isNullOrEmpty(paymentResponse.getPayment().getId()));
            Assert.assertFalse(CheckoutUtils.isNullOrEmpty(paymentResponse.getPayment().getActionId()));
            Assert.assertEquals(paymentRequest.getAmount().intValue(), paymentResponse.getPayment().getAmount());
            Assert.assertEquals(paymentRequest.getCurrency(), paymentResponse.getPayment().getCurrency());
            Assert.assertEquals(paymentRequest.getReference(), paymentResponse.getPayment().getReference());
            Assert.assertNotNull(paymentResponse.getPayment().getDestination());
            Assert.assertEquals(paymentResponse.getPayment().getDestination().getBin(), paymentRequest.getDestination().getNumber().substring(0, 6));
            Assert.assertEquals(
                    paymentResponse.getPayment().getDestination().getLast4(),
                    paymentRequest.getDestination().getNumber().substring(paymentRequest.getDestination().getNumber().length() - 4));
            Assert.assertEquals(paymentRequest.getDestination().getType(), paymentResponse.getPayment().getDestination().getType());
        }
    }
}
