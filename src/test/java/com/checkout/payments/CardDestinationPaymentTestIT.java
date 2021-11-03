package com.checkout.payments;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CardDestinationPaymentTestIT extends SandboxTestFixture {

    CardDestinationPaymentTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void canPerformCardPayout() {
        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPayoutRequest();
        final PaymentResponse paymentResponse = blocking(defaultApi.paymentsClient().requestAsync(paymentRequest));
        assertNotNull(paymentResponse.getPayment());
        final PaymentProcessed paymentProcessed = paymentResponse.getPayment();
        assertNotNull(paymentProcessed.getId());
        assertEquals("Authorized", paymentProcessed.getStatus());
        assertNotNull(paymentProcessed.getReference());
        assertNotNull(paymentProcessed.getCustomer());
        assertNull(paymentProcessed.getThreeDS());
        assertNotNull(paymentProcessed.getSelfLink());
    }

}
