package com.checkout.payments.four;

import com.checkout.ItemsResponse;
import com.checkout.payments.four.response.PaymentResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentActionsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldGetPaymentActions() {

        final PaymentResponse paymentResponse = makeCardPayment(false);

        final ItemsResponse<PaymentAction> paymentActions = blocking(() -> fourApi.paymentsClient().getPaymentActions(paymentResponse.getId()));

        assertNotNull(paymentActions);
        assertEquals(1, paymentActions.getItems().size());
        assertNotNull(paymentActions.getResponseHeaders());
        assertNotNull(paymentActions.getBody());
        assertNotNull(paymentActions.getHttpStatusCode());

        paymentActions.getItems().forEach(paymentAction -> {
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
