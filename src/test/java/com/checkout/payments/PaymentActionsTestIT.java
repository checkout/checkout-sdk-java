package com.checkout.payments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.checkout.ItemsResponse;
import com.checkout.payments.response.PaymentResponse;

class PaymentActionsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldGetPaymentActions() {
        final PaymentResponse paymentResponse = makeCardPayment(false);

        final ItemsResponse<PaymentAction> paymentActions = blocking(() -> checkoutApi.paymentsClient().getPaymentActions(paymentResponse.getId()));

        validatePaymentActionsResponse(paymentActions);
    }

    // Synchronous methods
    @Test
    void shouldGetPaymentActionsSync() {
        final PaymentResponse paymentResponse = makeCardPayment(false);

        final ItemsResponse<PaymentAction> paymentActions = checkoutApi.paymentsClient().getPaymentActionsSync(paymentResponse.getId());

        validatePaymentActionsResponse(paymentActions);
    }

    // Common methods
    private void validatePaymentActionsResponse(ItemsResponse<PaymentAction> paymentActions) {
        assertNotNull(paymentActions);
        assertEquals(1, paymentActions.getItems().size());
        assertNotNull(paymentActions.getResponseHeaders());
        assertNotNull(paymentActions.getBody());
        assertNotNull(paymentActions.getHttpStatusCode());

        paymentActions.getItems().forEach(this::validatePaymentAction);
    }

    private void validatePaymentAction(PaymentAction paymentAction) {
        assertEquals(10, paymentAction.getAmount());
        assertTrue(paymentAction.getApproved());
        assertNotNull(paymentAction.getLinks());
        assertNotNull(paymentAction.getProcessedOn());
        assertNotNull(paymentAction.getReference());
        assertNotNull(paymentAction.getResponseCode());
        assertNotNull(paymentAction.getResponseSummary());
        assertNotNull(paymentAction.getType());
    }

}
