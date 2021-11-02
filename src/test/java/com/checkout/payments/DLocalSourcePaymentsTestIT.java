package com.checkout.payments;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DLocalSourcePaymentsTestIT extends SandboxTestFixture {

    DLocalSourcePaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldRequestNon3dsCardPayment() throws Exception {

        final PaymentRequest<DLocalSource> paymentRequest = TestHelper.createDLocalPaymentRequest();
        paymentRequest.setThreeDS(new ThreeDSRequest());

        final PaymentResponse paymentResponse = defaultApi.paymentsClient().requestAsync(paymentRequest).get();

        assertNotNull(paymentResponse.getPayment());
        assertTrue(paymentResponse.getPayment().isApproved());
        assertFalse(StringUtils.isEmpty(paymentResponse.getPayment().getId()));
        assertFalse(StringUtils.isEmpty(paymentResponse.getPayment().getActionId()));
        assertEquals(paymentRequest.getAmount().intValue(), paymentResponse.getPayment().getAmount());
        assertEquals(paymentRequest.getCurrency(), paymentResponse.getPayment().getCurrency());
        assertEquals(paymentRequest.getReference(), paymentResponse.getPayment().getReference());
        assertNotNull(paymentResponse.getPayment().getCustomer());
        assertFalse(StringUtils.isEmpty(paymentResponse.getPayment().getCustomer().getId()));
        assertFalse(StringUtils.isEmpty(paymentResponse.getPayment().getCustomer().getEmail()));
        assertTrue(paymentResponse.getPayment().canCapture());
        assertTrue(paymentResponse.getPayment().canVoid());
        assertNotNull(paymentResponse.getPayment().getSource());
        assertTrue(paymentResponse.getPayment().getSource() instanceof CardSourceResponse);
        assertFalse(paymentRequest.getSource().getStored());
    }

}