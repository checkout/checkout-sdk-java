package com.checkout.apm;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.CountryCode;
import com.checkout.payments.AlternativePaymentSourceResponse;
import com.checkout.payments.GetPaymentResponse;
import com.checkout.payments.PaymentPending;
import com.checkout.payments.PaymentProcessed;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.PaymentResponse;
import com.checkout.payments.apm.BoletoSource;
import com.checkout.payments.apm.IntegrationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoletoPaymentsTestIT extends SandboxTestFixture {

    BoletoPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldSucceedBoletoRedirectPayment() {

        final BoletoSource boletoSource = BoletoSource.builder()
                .country(CountryCode.BR)
                .description("boleto payment")
                .integrationType(IntegrationType.REDIRECT)
                .payer(BoletoSource.Payer.builder().email("john@doe-enterprises.com").name("John Doe").document("53033315550").build())
                .build();

        final PaymentRequest<BoletoSource> request = PaymentRequest.boleto(boletoSource, com.checkout.common.beta.Currency.BRL, 100L);

        nap();

        final PaymentResponse response = blocking(getApi().paymentsClient().requestAsync(request));

        assertNotNull(response);

        final PaymentPending paymentPending = response.getPending();
        assertNotNull(paymentPending);
        assertEquals("Pending", paymentPending.getStatus());

        assertTrue(paymentPending.hasLink("self"));
        assertTrue(paymentPending.hasLink("redirect"));

        // Get payment

        nap();

        final GetPaymentResponse getPaymentResponse = blocking(getApi().paymentsClient().getAsync(paymentPending.getId()));

        assertNotNull(response);
        assertEquals("Pending", getPaymentResponse.getStatus());

        assertNotNull(getPaymentResponse.getSource());
        assertTrue(getPaymentResponse.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) getPaymentResponse.getSource();
        assertEquals("boleto", source.getType());
        assertEquals("redirect", source.get("integration_type"));
        assertNotNull(source.get("dlocal_order_id"));
        assertNotNull(source.get("dlocal_payment_id"));
        assertEquals("boleto payment", source.get("description"));

    }

    @Test
    void shouldMakeBoletoDirectPayment_thirdPartyRejection() {

        final BoletoSource boletoSource = BoletoSource.builder()
                .country(CountryCode.BR)
                .description("boleto payment")
                .integrationType(IntegrationType.DIRECT)
                .payer(BoletoSource.Payer.builder().email("john@doe-enterprises.com").name("John Doe").document("53033315550").build())
                .build();

        final PaymentRequest<BoletoSource> request = PaymentRequest.boleto(boletoSource, com.checkout.common.beta.Currency.BRL, 100L);

        nap();

        final PaymentResponse response = blocking(getApi().paymentsClient().requestAsync(request));

        assertNotNull(response);

        final PaymentProcessed paymentProcessed = response.getPayment();
        assertNotNull(paymentProcessed);
        assertEquals("Declined", paymentProcessed.getStatus());
        assertEquals("Rejected", paymentProcessed.getResponseSummary());
        assertTrue(paymentProcessed.hasLink("self"));
        assertTrue(paymentProcessed.hasLink("actions"));

        // Get payment

        nap();

        final GetPaymentResponse getPaymentResponse = blocking(getApi().paymentsClient().getAsync(paymentProcessed.getId()));

        assertNotNull(response);
        assertEquals("Declined", getPaymentResponse.getStatus());

        assertNotNull(getPaymentResponse.getSource());
        assertTrue(getPaymentResponse.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) getPaymentResponse.getSource();
        assertEquals("boleto", source.getType());
        assertEquals("direct", source.get("integration_type"));
        assertEquals("third_party_rejected", source.get("failure_code"));
        assertNotNull(source.get("dlocal_order_id"));
        assertEquals("boleto payment", source.get("description"));

    }

}
