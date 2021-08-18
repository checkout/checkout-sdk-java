package com.checkout.payments;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardVerificationTestIT extends SandboxTestFixture {

    public CardVerificationTestIT() {
        super(PlatformType.CLASSIC);
    }

    @Test
    public void can_verify_card() throws Exception {
        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest(0L);
        final PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        assertEquals(PaymentStatus.CARD_VERIFIED, paymentResponse.getPayment().getStatus());
    }
}