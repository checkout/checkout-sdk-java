package com.checkout.payments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResponseSourceExtensionsTest {

    @Test
    void shouldConvertToCardSource() {

        final CardSourceResponse source = new CardSourceResponse();
        final PaymentProcessed payment = new PaymentProcessed();
        payment.setSource(source);

        assertTrue(payment.getSource() instanceof CardSourceResponse);
        assertEquals(source, payment.getSource());

    }

    @Test
    void shouldConvertToAlternativePaymentSource() {

        final AlternativePaymentSourceResponse source = new AlternativePaymentSourceResponse();
        final PaymentProcessed payment = new PaymentProcessed();
        payment.setSource(source);

        assertTrue(payment.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(source, payment.getSource());

    }
}
