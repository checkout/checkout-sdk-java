package com.checkout.apm.giropay;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentPending;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.PaymentResponse;
import com.checkout.payments.apm.GiropaySource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GiropayPaymentsTestIT extends SandboxTestFixture {

    GiropayPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldMakeGiropayPayment() {

        final GiropaySource giropaySource = GiropaySource.builder()
                .purpose("CKO Giropay test")
                .build();

        final PaymentRequest<GiropaySource> request = PaymentRequest.giropay(giropaySource, Currency.EUR, 1000L);

        final PaymentResponse response = blocking(defaultApi.paymentsClient().requestAsync(request));

        assertNotNull(response);

        final PaymentPending paymentPending = response.getPending();
        assertNotNull(paymentPending);
        assertEquals("Pending", paymentPending.getStatus());

        assertNotNull(paymentPending.getLink("self"));
        assertNotNull(paymentPending.getLink("redirect"));

    }

}
