package com.checkout.payments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.PaymentsQueryResponse;

public class GetPaymentsListTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldQueryPayments() throws InterruptedException {
        final PaymentResponse payment = makeCardPayment(false);
        final PaymentsQueryFilter query = createPaymentsQuery(payment.getReference());
        
        Thread.sleep(2000); // to ensure the payment is indexed before querying 

        final PaymentsQueryResponse response = blocking(() -> checkoutApi.paymentsClient().getPaymentsList(query));

        validatePaymentsQueryResponse(response, query, payment);
    }

    // Synchronous methods
    @Test
    void shouldQueryPaymentsSync() throws InterruptedException {
        final PaymentResponse payment = makeCardPayment(false);
        final PaymentsQueryFilter query = createPaymentsQuery(payment.getReference());

        Thread.sleep(2000); // to ensure the payment is indexed before querying 
        
        final PaymentsQueryResponse response = checkoutApi.paymentsClient().getPaymentsListSync(query);

        validatePaymentsQueryResponse(response, query, payment);
    }

    // Common methods
    private PaymentsQueryFilter createPaymentsQuery(String reference) {
        return PaymentsQueryFilter
                .builder()
                .limit(100)
                .skip(0)
                .reference(reference)
                .build();
    }

    private void validatePaymentsQueryResponse(PaymentsQueryResponse response, PaymentsQueryFilter query, PaymentResponse expectedPayment) {
        assertNotNull(response);
        assertEquals(query.getLimit(), response.getLimit());
        assertEquals(query.getSkip(), response.getSkip());
        assertNotNull(response.getData());
        assertEquals(1, response.getTotalCount());
        assertEquals(expectedPayment.getReference(), response.getData().get(0).getReference());
    }

}
