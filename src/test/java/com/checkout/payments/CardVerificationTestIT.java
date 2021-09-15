package com.checkout.payments;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardVerificationTestIT extends SandboxTestFixture {

    CardVerificationTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldVerifyCard() throws Exception {
        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest(0L);
        final PaymentResponse paymentResponse = defaultApi.paymentsClient().requestAsync(paymentRequest).get();

        assertEquals(PaymentStatus.CARD_VERIFIED, paymentResponse.getPayment().getStatus());
    }
}