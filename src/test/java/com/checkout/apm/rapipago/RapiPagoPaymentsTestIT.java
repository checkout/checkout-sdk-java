package com.checkout.apm.rapipago;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.apm.Payer;
import com.checkout.payments.request.source.apm.RequestRapiPagoSource;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.source.AlternativePaymentSourceResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RapiPagoPaymentsTestIT extends SandboxTestFixture {

    RapiPagoPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    @Disabled("not_available")
    void shouldSucceedRapiPagoPayment() {

        final String paymentId = makeRapiPagoPayment();

        blocking(() -> defaultApi.rapiPagoClient().succeed(paymentId));

        final GetPaymentResponse response = blocking(() -> defaultApi.paymentsClient().getPayment(paymentId));

        assertNotNull(response);
        assertEquals(PaymentStatus.CAPTURED, response.getStatus());

        assertNotNull(response.getSource());
        assertTrue(response.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) response.getSource();
        assertEquals(PaymentSourceType.RAPIPAGO, source.getType());
        assertEquals("redirect", source.get("integration_type"));
        assertNotNull(source.get("dlocal_order_id"));
        assertNotNull(source.get("dlocal_payment_id"));
        assertEquals("simulate Via Rapi Pago Demo Payment", source.get("description"));

    }

    @Test
    @Disabled("not_available")
    void shouldExpireRapiPagoPayment() {

        final String paymentId = makeRapiPagoPayment();

        blocking(() -> defaultApi.rapiPagoClient().expire(paymentId));

        final GetPaymentResponse response = blocking(() -> defaultApi.paymentsClient().getPayment(paymentId));

        assertNotNull(response);
        assertEquals(PaymentStatus.EXPIRED, response.getStatus());

        assertNotNull(response.getSource());
        assertTrue(response.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) response.getSource();
        assertEquals(PaymentSourceType.RAPIPAGO, source.getType());
        assertEquals("redirect", source.get("integration_type"));
        assertNotNull(source.get("dlocal_order_id"));
        assertNotNull(source.get("dlocal_payment_id"));
        assertEquals("simulate Via Rapi Pago Demo Payment", source.get("description"));

    }

    private String makeRapiPagoPayment() {

        final RequestRapiPagoSource rapiPagoSource = RequestRapiPagoSource.builder()
                .country(CountryCode.AR)
                .description("simulate Via Rapi Pago Demo Payment")
                .payer(Payer.builder().email("bruce@wayne-enterprises.com").name("Bruce Wayne").build())
                .build();

        final PaymentRequest request = PaymentRequest.builder()
                .source(rapiPagoSource)
                .currency(Currency.ARS)
                .amount(100000L)
                .build();

        final PaymentResponse response = blocking(() -> defaultApi.paymentsClient().requestPayment(request));

        assertNotNull(response);
        assertEquals(PaymentStatus.PENDING, response.getStatus());
        assertTrue(response.getLinks().containsKey("self"));
        assertTrue(response.getLinks().containsKey("redirect"));
        assertTrue(response.getLinks().containsKey("simulator:payment-succeed"));
        assertTrue(response.getLinks().containsKey("simulator:payment-expire"));

        return response.getId();

    }

}
