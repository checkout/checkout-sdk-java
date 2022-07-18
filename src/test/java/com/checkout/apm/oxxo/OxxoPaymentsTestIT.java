package com.checkout.apm.oxxo;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.Payer;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.apm.RequestOxxoSource;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.source.AlternativePaymentSourceResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OxxoPaymentsTestIT extends SandboxTestFixture {

    OxxoPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    @Disabled("not_available")
    void shouldSucceedOxxoPayment() {

        final String paymentId = makeOxxoPayment();

        blocking(() -> defaultApi.oxxoClient().succeed(paymentId));

        final GetPaymentResponse response = blocking(() -> defaultApi.paymentsClient().getPayment(paymentId));

        assertNotNull(response);
        //assertEquals(PaymentStatus.CAPTURED, response.getStatus());

        assertNotNull(response.getSource());
        assertTrue(response.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) response.getSource();
        assertEquals(PaymentSourceType.OXXO, source.getType());
        assertEquals("redirect", source.get("integration_type"));
        assertNotNull(source.get("dlocal_order_id"));
        assertNotNull(source.get("dlocal_payment_id"));
        assertEquals("simulate Via Oxxo Demo Payment", source.get("description"));

    }

    @Test
    @Disabled("not_available")
    void shouldExpireOxxoPayment() {

        final String paymentId = makeOxxoPayment();

        blocking(() -> defaultApi.oxxoClient().expire(paymentId));

        final GetPaymentResponse response = blocking(() -> defaultApi.paymentsClient().getPayment(paymentId));

        assertNotNull(response);
        //assertEquals(PaymentStatus.EXPIRED, response.getStatus());

        assertNotNull(response.getSource());
        assertTrue(response.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) response.getSource();
        assertEquals(PaymentSourceType.OXXO, source.getType());
        assertEquals("redirect", source.get("integration_type"));
        assertNotNull(source.get("dlocal_order_id"));
        assertNotNull(source.get("dlocal_payment_id"));
        assertEquals("simulate Via Oxxo Demo Payment", source.get("description"));

    }

    private String makeOxxoPayment() {

        final RequestOxxoSource oxxoSource = RequestOxxoSource.builder()
                .country(CountryCode.MX)
                .description("simulate Via Oxxo Demo Payment")
                .payer(Payer.builder().email("bruce@wayne-enterprises.com").name("Bruce Wayne").build())
                .build();

        final PaymentRequest request = PaymentRequest.oxxo(oxxoSource, Currency.MXN, 100000L);

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
