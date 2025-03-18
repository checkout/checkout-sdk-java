package com.checkout.payments.previous;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.checkout.payments.PaymentsQueryFilter;
import com.checkout.payments.previous.response.PaymentResponse;
import com.checkout.payments.previous.response.PaymentsQueryResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("unavailable")
public class GetPaymentsListTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldQueryPayments() {
        PaymentResponse payment = makeCardPayment(false, 100L);

        final PaymentsQueryFilter query = PaymentsQueryFilter
                .builder()
                .limit(100)
                .skip(0)
                .reference(payment.getReference())
                .build();

        PaymentsQueryResponse response = blocking(() -> previousApi.paymentsClient().getPaymentsList(query));

        assertNotNull(response);
        assertEquals(query.getLimit(), response.getLimit());
        assertEquals(query.getSkip(), response.getSkip());
        assertNotNull(response.getData());
        assertEquals(1, response.getTotalCount());
        assertEquals(query.getReference(), response.getData().get(0).getReference());
    }
}
