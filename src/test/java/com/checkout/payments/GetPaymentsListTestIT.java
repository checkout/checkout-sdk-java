package com.checkout.payments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.PaymentsQueryResponse;
import org.junit.jupiter.api.Test;

public class GetPaymentsListTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldQueryPayments() {
        PaymentResponse payment = makeCardPayment(false);

        final PaymentsQueryFilter query = PaymentsQueryFilter
                .builder()
                .limit(100)
                .skip(0)
                .reference(payment.getReference())
                .build();

        PaymentsQueryResponse response = blocking(() -> checkoutApi.paymentsClient().getPaymentsList(query));

        assertNotNull(response);
        assertEquals(query.getLimit(), response.getLimit());
        assertEquals(query.getSkip(), response.getSkip());
        assertNotNull(response.getData());
        assertEquals(1, response.getTotalCount());
        assertEquals(query.getReference(), response.getData().get(0).getReference());
    }
}
