package com.checkout.apm.sofort;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import com.checkout.payments.AlternativePaymentSourceResponse;
import com.checkout.payments.GetPaymentResponse;
import com.checkout.payments.PaymentPending;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.PaymentResponse;
import com.checkout.payments.apm.SofortSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SofortPaymentsTestIT extends SandboxTestFixture {

    SofortPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldSucceedSofortPayment() {

        final PaymentRequest<SofortSource> request = PaymentRequest.sofort(Currency.EUR, 100L);

        nap();

        final PaymentResponse response = blocking(defaultApi.paymentsClient().requestAsync(request));

        assertNotNull(response);

        final PaymentPending paymentPending = response.getPending();
        assertNotNull(paymentPending);
        assertEquals("Pending", paymentPending.getStatus());

        assertNotNull(paymentPending.getLink(SELF));
        assertNotNull(paymentPending.getLink("redirect"));

        // Get payment

        nap();

        final GetPaymentResponse getPaymentResponse = blocking(defaultApi.paymentsClient().getAsync(paymentPending.getId()));

        assertNotNull(response);
        assertEquals("Pending", getPaymentResponse.getStatus());

        assertNotNull(getPaymentResponse.getSource());
        assertTrue(getPaymentResponse.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) getPaymentResponse.getSource();
        assertEquals("sofort", source.getType());

    }

}
