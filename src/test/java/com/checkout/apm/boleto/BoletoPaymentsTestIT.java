package com.checkout.apm.boleto;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.Payer;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.apm.IntegrationType;
import com.checkout.payments.request.source.apm.RequestBoletoSource;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.source.AlternativePaymentSourceResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BoletoPaymentsTestIT extends SandboxTestFixture {

    BoletoPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    @Disabled("not available")
    void shouldSucceedBoletoRedirectPayment() {

        final RequestBoletoSource boletoSource = RequestBoletoSource.builder()
                .country(CountryCode.BR)
                .description("boleto payment")
                .integrationType(IntegrationType.REDIRECT)
                .payer(Payer.builder().email("john@doe-enterprises.com").name("John Doe").document("53033315550").build())
                .build();

        final PaymentRequest request = PaymentRequest.builder()
                .source(boletoSource)
                .currency(Currency.BRL)
                .amount(100L)
                .build();

        final PaymentResponse response = blocking(() -> defaultApi.paymentsClient().requestPayment(request));
        assertNotNull(response);
        assertEquals(PaymentStatus.PENDING, response.getStatus());
        assertNotNull(response.getLink("self"));
        assertNotNull(response.getLink("redirect"));

        // Get payment
        final GetPaymentResponse getPaymentResponse = blocking(() -> defaultApi.paymentsClient().getPayment(response.getId()));

        assertNotNull(response);
        assertEquals(PaymentStatus.PENDING, getPaymentResponse.getStatus());

        assertNotNull(getPaymentResponse.getSource());
        assertTrue(getPaymentResponse.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) getPaymentResponse.getSource();
        assertEquals(PaymentSourceType.BOLETO, source.getType());
        assertEquals("redirect", source.get("integration_type"));
        assertNotNull(source.get("dlocal_order_id"));
        assertNotNull(source.get("dlocal_payment_id"));
        assertEquals("boleto payment", source.get("description"));

    }

    @Test
    @Disabled("not available")
    void shouldMakeBoletoDirectPayment_thirdPartyRejection() {

        final RequestBoletoSource boletoSource = RequestBoletoSource.builder()
                .country(CountryCode.BR)
                .description("boleto payment")
                .integrationType(IntegrationType.DIRECT)
                .payer(Payer.builder().email("john@doe-enterprises.com").name("John Doe").document("53033315550").build())
                .build();

        final PaymentRequest request = PaymentRequest.builder()
                .source(boletoSource)
                .currency(Currency.BRL)
                .amount(100L)
                .build();

        final PaymentResponse response = blocking(() -> defaultApi.paymentsClient().requestPayment(request));
        assertNotNull(response);
        assertEquals(PaymentStatus.DECLINED, response.getStatus());
        assertEquals("Rejected", response.getResponseSummary());
        assertNotNull(response.getLink("self"));
        assertNotNull(response.getLink("actions"));

        // Get payment
        final GetPaymentResponse getPaymentResponse = blocking(() -> defaultApi.paymentsClient().getPayment(response.getId()));

        assertNotNull(response);
        assertEquals(PaymentStatus.DECLINED, getPaymentResponse.getStatus());

        assertNotNull(getPaymentResponse.getSource());
        assertTrue(getPaymentResponse.getSource() instanceof AlternativePaymentSourceResponse);
        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) getPaymentResponse.getSource();
        assertEquals(PaymentSourceType.BOLETO, source.getType());
        assertEquals("direct", source.get("integration_type"));
        assertEquals("third_party_rejected", source.get("failure_code"));
        assertNotNull(source.get("dlocal_order_id"));
        assertEquals("boleto payment", source.get("description"));

    }

}
