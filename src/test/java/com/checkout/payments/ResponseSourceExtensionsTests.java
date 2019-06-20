package com.checkout.payments;

import org.junit.Assert;
import org.junit.Test;

public class ResponseSourceExtensionsTests {
    @Test
    public void can_convert_to_card_source() {
        CardSourceResponse source = new CardSourceResponse();
        PaymentProcessed payment = new PaymentProcessed();
        payment.setSource(source);

        Assert.assertTrue(payment.getSource() instanceof CardSourceResponse);
        Assert.assertEquals(source, payment.getSource());
    }

    @Test
    public void can_convert_to_alternative_payment_source() {
        AlternativePaymentSourceResponse source = new AlternativePaymentSourceResponse();
        PaymentProcessed payment = new PaymentProcessed();
        payment.setSource(source);

        Assert.assertTrue(payment.getSource() instanceof AlternativePaymentSourceResponse);
        Assert.assertEquals(source, payment.getSource());
    }
}