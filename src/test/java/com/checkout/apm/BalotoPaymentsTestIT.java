package com.checkout.apm;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.payments.AlternativePaymentSourceResponse;
import com.checkout.payments.GetPaymentResponse;
import com.checkout.payments.PaymentPending;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.PaymentResponse;
import com.checkout.payments.apm.BalotoSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BalotoPaymentsTestIT extends SandboxTestFixture {

    public BalotoPaymentsTestIT() {
        super(PlatformType.CLASSIC);
    }

    @Test
    public void shouldSucceedBalotoPayment() {

        final String paymentId = makeBaletoPayment();

        blocking(getApi().balotoClient().succeed(paymentId));

        final GetPaymentResponse response = blocking(getApi().paymentsClient().getAsync(paymentId));

        assertNotNull(response);
        assertEquals("Captured", response.getStatus());

        assertNotNull(response.getSource());
        assertTrue(response.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) response.getSource();
        assertEquals("baloto", source.getType());
        assertEquals("redirect", source.get("integration_type"));
        assertNotNull(source.get("dlocal_order_id"));
        assertNotNull(source.get("dlocal_payment_id"));
        assertEquals("simulate Via Baloto Demo Payment", source.get("description"));

    }

    @Test
    public void shouldExpireBalotoPayment() {

        final String paymentId = makeBaletoPayment();

        blocking(getApi().balotoClient().expire(paymentId));

        final GetPaymentResponse response = blocking(getApi().paymentsClient().getAsync(paymentId));

        assertNotNull(response);
        assertEquals("Expired", response.getStatus());

        assertNotNull(response.getSource());
        assertTrue(response.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) response.getSource();
        assertEquals("baloto", source.getType());
        assertEquals("redirect", source.get("integration_type"));
        assertNotNull(source.get("dlocal_order_id"));
        assertNotNull(source.get("dlocal_payment_id"));
        assertEquals("simulate Via Baloto Demo Payment", source.get("description"));

    }

    private String makeBaletoPayment() {

        final BalotoSource balotoSource = BalotoSource.builder()
                .country("CO")
                .description("simulate Via Baloto Demo Payment")
                .payer(BalotoSource.Payer.builder().email("bruce@wayne-enterprises.com").name("Bruce Wayne").build())
                .build();

        final PaymentRequest<BalotoSource> request = PaymentRequest.baloto(balotoSource, com.checkout.common.beta.Currency.COP, 100000L);

        final PaymentResponse response = blocking(getApi().paymentsClient().requestAsync(request));

        assertNotNull(response);

        final PaymentPending paymentPending = response.getPending();
        assertNotNull(paymentPending);
        assertEquals("Pending", paymentPending.getStatus());

        assertTrue(paymentPending.hasLink("self"));
        assertTrue(paymentPending.hasLink("redirect"));
        assertTrue(paymentPending.hasLink("simulator:payment-succeed"));
        assertTrue(paymentPending.hasLink("simulator:payment-expire"));

        return paymentPending.getId();

    }

}
