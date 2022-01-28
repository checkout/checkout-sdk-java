package com.checkout.apm.baloto;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.apm.RequestBalotoSource;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.source.AlternativePaymentSourceResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BalotoPaymentsTestIT extends SandboxTestFixture {

    BalotoPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldSucceedBalotoPayment() {

        final String paymentId = makeBalotoPayment();

        blocking(() -> defaultApi.balotoClient().succeed(paymentId));

        final GetPaymentResponse response = blocking(() -> defaultApi.paymentsClient().getPayment(paymentId), new PaymentIsInStatus(PaymentStatus.CAPTURED));

        assertNotNull(response);
        assertEquals(PaymentStatus.CAPTURED, response.getStatus());

        assertNotNull(response.getSource());
        assertTrue(response.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) response.getSource();
        assertEquals(PaymentSourceType.BALOTO, source.getType());
        assertEquals("redirect", source.get("integration_type"));
        assertNotNull(source.get("dlocal_order_id"));
        assertNotNull(source.get("dlocal_payment_id"));
        assertEquals("simulate Via Baloto Demo Payment", source.get("description"));

    }

    @Test
    void shouldExpireBalotoPayment() {

        final String paymentId = makeBalotoPayment();

        blocking(() -> defaultApi.balotoClient().expire(paymentId));

        final GetPaymentResponse response = blocking(() -> defaultApi.paymentsClient().getPayment(paymentId), new PaymentIsInStatus(PaymentStatus.EXPIRED));

        assertNotNull(response);
        assertEquals(PaymentStatus.EXPIRED, response.getStatus());

        assertNotNull(response.getSource());
        assertTrue(response.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) response.getSource();
        assertEquals(PaymentSourceType.BALOTO, source.getType());
        assertEquals("redirect", source.get("integration_type"));
        assertNotNull(source.get("dlocal_order_id"));
        assertNotNull(source.get("dlocal_payment_id"));
        assertEquals("simulate Via Baloto Demo Payment", source.get("description"));

    }

    private String makeBalotoPayment() {

        final RequestBalotoSource balotoSource = RequestBalotoSource.builder()
                .country(CountryCode.CO)
                .description("simulate Via Baloto Demo Payment")
                .payer(RequestBalotoSource.Payer.builder().email("bruce@wayne-enterprises.com").name("Bruce Wayne").build())
                .build();

        final PaymentRequest request = PaymentRequest.baloto(balotoSource, Currency.COP, 100000L);

        final PaymentResponse response = blocking(() -> defaultApi.paymentsClient().requestPayment(request));
        assertNotNull(response);
        assertEquals(PaymentStatus.PENDING, response.getStatus());
        assertNotNull(response.getLink("self"));
        assertNotNull(response.getLink("redirect"));
        assertNotNull(response.getLink("simulator:payment-succeed"));
        assertNotNull(response.getLink("simulator:payment-expire"));

        return response.getId();

    }

}
