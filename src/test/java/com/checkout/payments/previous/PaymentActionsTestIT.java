package com.checkout.payments.previous;

import com.checkout.ItemsResponse;
import com.checkout.payments.previous.response.PaymentResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("unavailable")
class PaymentActionsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldGetPaymentActions() {

        final PaymentResponse paymentResponse = makeCardPayment(true, 10L);

        final ItemsResponse<PaymentAction> paymentActions = blocking(() -> previousApi.paymentsClient().getPaymentActions(paymentResponse.getId()), new ListHasSize<ItemsResponse<PaymentAction>, PaymentAction>(2));

        assertNotNull(paymentActions);
        assertEquals(2, paymentActions.getItems().size());
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
