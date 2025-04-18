package com.checkout.payments.previous;

import com.checkout.payments.VoidRequest;
import com.checkout.payments.VoidResponse;
import com.checkout.payments.previous.response.PaymentResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("unavailable")
class VoidPaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldVoidPayment() {

        final PaymentResponse paymentResponse = makeCardPayment(false, 10L);

        final VoidRequest voidRequest = VoidRequest.builder()
                .reference(UUID.randomUUID().toString())
                .build();

        final VoidResponse voidResponse = blocking(() -> paymentsClient.voidPayment(paymentResponse.getId(), voidRequest));

        assertNotNull(voidResponse);
        assertNotNull(voidResponse.getActionId());
        assertNotNull(voidResponse.getReference());
        assertEquals(1, voidResponse.getLinks().size());

    }

    @Test
    void shouldVoidPaymentIdempotently() {

        final PaymentResponse paymentResponse = makeCardPayment(false, 10L);

        final VoidRequest voidRequest = VoidRequest.builder()
                .reference(UUID.randomUUID().toString())
                .build();

        final VoidResponse voidResponse1 = blocking(() -> paymentsClient.voidPayment(paymentResponse.getId(), voidRequest, IDEMPOTENCY_KEY));
        assertNotNull(voidResponse1);

        final VoidResponse voidResponse2 = blocking(() -> paymentsClient.voidPayment(paymentResponse.getId(), voidRequest, IDEMPOTENCY_KEY));
        assertNotNull(voidResponse2);

        assertEquals(voidResponse1.getActionId(), voidResponse2.getActionId());

    }

}

