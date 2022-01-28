package com.checkout.payments.four;

import com.checkout.payments.four.response.PaymentResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentActionsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldGetPaymentActions() {

        final PaymentResponse paymentResponse = makeCardPayment(false);

        final List<PaymentAction> paymentActions = blocking(() -> fourApi.paymentsClient().getPaymentActions(paymentResponse.getId()));

        assertNotNull(paymentActions);
        assertEquals(1, paymentActions.size());

        paymentActions.forEach(paymentAction -> {
            assertEquals(10, paymentAction.getAmount());
            assertTrue(paymentAction.getApproved());
            assertNotNull(paymentAction.getLinks());
            assertNotNull(paymentAction.getProcessedOn());
            assertNotNull(paymentAction.getReference());
            assertNotNull(paymentAction.getResponseCode());
            assertNotNull(paymentAction.getResponseSummary());
            assertNotNull(paymentAction.getType());
        });

    }

}
