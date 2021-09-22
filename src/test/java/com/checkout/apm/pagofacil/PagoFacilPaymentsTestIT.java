package com.checkout.apm.pagofacil;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.payments.AlternativePaymentSourceResponse;
import com.checkout.payments.GetPaymentResponse;
import com.checkout.payments.PaymentPending;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.PaymentResponse;
import com.checkout.payments.apm.PagoFacilSource;
import com.checkout.payments.apm.Payer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PagoFacilPaymentsTestIT extends SandboxTestFixture {

    PagoFacilPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldSucceedPagoFacilPayment() {

        final String paymentId = makePagoFacilPayment();

        blocking(defaultApi.pagoFacilClient().succeed(paymentId));

        final GetPaymentResponse response = blocking(defaultApi.paymentsClient().getAsync(paymentId));

        assertNotNull(response);
        assertEquals("Captured", response.getStatus());

        assertNotNull(response.getSource());
        assertTrue(response.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) response.getSource();
        assertEquals("pagofacil", source.getType());
        assertEquals("redirect", source.get("integration_type"));
        assertNotNull(source.get("dlocal_order_id"));
        assertNotNull(source.get("dlocal_payment_id"));
        assertEquals("simulate Via Pago Facil Demo Payment", source.get("description"));

    }

    @Test
    void shouldExpirePagoFacilPayment() {

        final String paymentId = makePagoFacilPayment();

        blocking(defaultApi.pagoFacilClient().expire(paymentId));

        final GetPaymentResponse response = blocking(defaultApi.paymentsClient().getAsync(paymentId));

        assertNotNull(response);
        assertEquals("Expired", response.getStatus());

        assertNotNull(response.getSource());
        assertTrue(response.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) response.getSource();
        assertEquals("pagofacil", source.getType());
        assertEquals("redirect", source.get("integration_type"));
        assertNotNull(source.get("dlocal_order_id"));
        assertNotNull(source.get("dlocal_payment_id"));
        assertEquals("simulate Via Pago Facil Demo Payment", source.get("description"));

    }

    private String makePagoFacilPayment() {

        final PagoFacilSource pagoFacilSource = PagoFacilSource.builder()
                .country(CountryCode.AR)
                .description("simulate Via Pago Facil Demo Payment")
                .payer(Payer.builder().email("bruce@wayne-enterprises.com").name("Bruce Wayne").build())
                .build();

        final PaymentRequest<PagoFacilSource> request = PaymentRequest.pagoFacil(pagoFacilSource, Currency.ARS, 100000L);

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
