package com.checkout.apm.sofort;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.apm.RequestSofortSource;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.source.AlternativePaymentSourceResponse;
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

        final PaymentRequest request = PaymentRequest.builder()
                .source(new RequestSofortSource())
                .currency(Currency.EUR)
                .amount(100L)
                .build();

        final PaymentResponse response = blocking(() -> defaultApi.paymentsClient().requestPayment(request));

        assertNotNull(response);
        assertEquals(PaymentStatus.PENDING, response.getStatus());
        assertNotNull(response.getLink(SELF));
        assertNotNull(response.getLink("redirect"));

        // Get payment
        final GetPaymentResponse getPaymentResponse = blocking(() -> defaultApi.paymentsClient().getPayment(response.getId()));

        assertNotNull(response);
        assertEquals(PaymentStatus.PENDING, getPaymentResponse.getStatus());

        assertNotNull(getPaymentResponse.getSource());
        assertTrue(getPaymentResponse.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) getPaymentResponse.getSource();
        assertEquals(PaymentSourceType.SOFORT, source.getType());

    }

}
