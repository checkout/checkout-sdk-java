package com.checkout.payments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResponseSourceExtensionsTest {

    @Test
    public void can_convert_to_card_source() {
        final CardSourceResponse source = new CardSourceResponse();
        final PaymentProcessed payment = new PaymentProcessed();
        payment.setSource(source);

        assertTrue(payment.getSource() instanceof CardSourceResponse);
        assertEquals(source, payment.getSource());
    }

    @Test
    public void can_convert_to_alternative_payment_source() {
        final AlternativePaymentSourceResponse source = new AlternativePaymentSourceResponse();
        final PaymentProcessed payment = new PaymentProcessed();
        payment.setSource(source);

        assertTrue(payment.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(source, payment.getSource());
    }
}