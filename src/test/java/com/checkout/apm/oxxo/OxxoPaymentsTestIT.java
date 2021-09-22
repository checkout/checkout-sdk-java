package com.checkout.apm.oxxo;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.payments.AlternativePaymentSourceResponse;
import com.checkout.payments.GetPaymentResponse;
import com.checkout.payments.PaymentPending;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.PaymentResponse;
import com.checkout.payments.apm.OxxoSource;
import com.checkout.payments.apm.Payer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OxxoPaymentsTestIT extends SandboxTestFixture {

    OxxoPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldSucceedOxxoPayment() {

        final String paymentId = makePagoFacilPayment();

        blocking(defaultApi.oxxoClient().succeed(paymentId));

        final GetPaymentResponse response = blocking(defaultApi.paymentsClient().getAsync(paymentId));

        assertNotNull(response);
        assertEquals("Captured", response.getStatus());

        assertNotNull(response.getSource());
        assertTrue(response.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) response.getSource();
        assertEquals("oxxo", source.getType());
        assertEquals("redirect", source.get("integration_type"));
        assertNotNull(source.get("dlocal_order_id"));
        assertNotNull(source.get("dlocal_payment_id"));
        assertEquals("simulate Via Oxxo Demo Payment", source.get("description"));

    }

    @Test
    void shouldExpireOxxoPayment() {

        final String paymentId = makePagoFacilPayment();

        blocking(defaultApi.oxxoClient().expire(paymentId));

        final GetPaymentResponse response = blocking(defaultApi.paymentsClient().getAsync(paymentId));

        assertNotNull(response);
        assertEquals("Expired", response.getStatus());

        assertNotNull(response.getSource());
        assertTrue(response.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) response.getSource();
        assertEquals("oxxo", source.getType());
        assertEquals("redirect", source.get("integration_type"));
        assertNotNull(source.get("dlocal_order_id"));
        assertNotNull(source.get("dlocal_payment_id"));
        assertEquals("simulate Via Oxxo Demo Payment", source.get("description"));

    }

    private String makePagoFacilPayment() {

        final OxxoSource oxxoSource = OxxoSource.builder()
                .country(CountryCode.MX)
                .description("simulate Via Oxxo Demo Payment")
                .payer(Payer.builder().email("bruce@wayne-enterprises.com").name("Bruce Wayne").build())
                .build();

        final PaymentRequest<OxxoSource> request = PaymentRequest.oxxo(oxxoSource, Currency.MXN, 100000L);

        final PaymentResponse response = blocking(defaultApi.paymentsClient().requestAsync(request));

        assertNotNull(response);

        final PaymentPending paymentPending = response.getPending();
        assertNotNull(paymentPending);
        assertEquals("Pending", paymentPending.getStatus());

        assertTrue(paymentPending.getLinks().containsKey("self"));
        assertTrue(paymentPending.getLinks().containsKey("redirect"));
        assertTrue(paymentPending.getLinks().containsKey("simulator:payment-succeed"));
        assertTrue(paymentPending.getLinks().containsKey("simulator:payment-expire"));

        return paymentPending.getId();

    }

}
