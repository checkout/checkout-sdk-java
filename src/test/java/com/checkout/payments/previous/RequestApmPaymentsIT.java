package com.checkout.payments.previous;

import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.PaymentMethodDetails;
import com.checkout.payments.previous.request.PaymentRequest;
import com.checkout.payments.previous.request.source.apm.IntegrationType;
import com.checkout.payments.previous.request.source.apm.RequestAlipaySource;
import com.checkout.payments.previous.request.source.apm.RequestBancontactSource;
import com.checkout.payments.previous.request.source.apm.RequestBenefitPaySource;
import com.checkout.payments.previous.request.source.apm.RequestBoletoSource;
import com.checkout.payments.previous.request.source.apm.RequestEpsSource;
import com.checkout.payments.previous.request.source.apm.RequestFawrySource;
import com.checkout.payments.previous.request.source.apm.RequestGiropaySource;
import com.checkout.payments.previous.request.source.apm.RequestIdealSource;
import com.checkout.payments.previous.request.source.apm.RequestKnetSource;
import com.checkout.payments.previous.request.source.apm.RequestMultiBancoSource;
import com.checkout.payments.previous.request.source.apm.RequestOxxoSource;
import com.checkout.payments.previous.request.source.apm.RequestP24Source;
import com.checkout.payments.previous.request.source.apm.RequestPagoFacilSource;
import com.checkout.payments.previous.request.source.apm.RequestPayPalSource;
import com.checkout.payments.previous.request.source.apm.RequestPoliSource;
import com.checkout.payments.previous.request.source.apm.RequestQPaySource;
import com.checkout.payments.previous.request.source.apm.RequestRapiPagoSource;
import com.checkout.payments.previous.request.source.apm.RequestSofortSource;
import com.checkout.payments.previous.response.GetPaymentResponse;
import com.checkout.payments.previous.response.PaymentResponse;
import com.checkout.payments.previous.response.source.AlternativePaymentSourceResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.checkout.TestHelper.getPayer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("unavailable")
class RequestApmPaymentsIT extends AbstractPaymentsTestIT {

    @Test
    @Disabled("not available")
    void shouldMakeAliPayPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(new RequestAlipaySource())
                .currency(Currency.USD)
                .amount(100L)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.ALIPAY, paymentDetails.getSource().getType());
    }

    @Test
    @Disabled("not available")
    void shouldMakeBenefitPayPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestBenefitPaySource.builder()
                        .integrationType("mobile")
                        .build())
                .currency(Currency.USD)
                .amount(100L)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.BENEFITPAY, paymentDetails.getSource().getType());
    }

    @Test
    @Disabled("not available")
    void shouldMakeBoletoPaymentRedirect() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestBoletoSource.builder()
                        .country(CountryCode.BR)
                        .description("boleto payment")
                        .payer(getPayer())
                        .integrationType(IntegrationType.REDIRECT)
                        .build())
                .currency(Currency.BRL)
                .amount(100L)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.BOLETO, paymentDetails.getSource().getType());
    }

    @Test
    void shouldMakeEpsPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestEpsSource.builder()
                        .purpose("Mens black t-shirt L")
                        .build())
                .currency(Currency.EUR)
                .amount(1914L)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.EPS, paymentDetails.getSource().getType());
    }

    @Test
    void shouldMakeFawryPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestFawrySource.builder()
                        .description("Fawry Demo Payment")
                        .customerEmail("bruce@wayne-enterprises.com")
                        .customerMobile("01058375055")
                        .products(Collections.singletonList(
                                RequestFawrySource.Product.builder()
                                        .id("0123456789")
                                        .description("Fawry Demo Product")
                                        .price(1000L)
                                        .quantity(1L)
                                        .build()))
                        .build())
                .currency(Currency.EGP)
                .amount(1000L)
                .capture(true)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.FAWRY, paymentDetails.getSource().getType());
    }

    @Test
    @Disabled("Temporarily skipped")
    void shouldMakeGiropayPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestGiropaySource.builder()
                        .purpose("CKO Giropay test")
                        .build())
                .currency(Currency.EUR)
                .amount(1000L)
                .capture(true)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.GIROPAY, paymentDetails.getSource().getType());
    }

    @Test
    void shouldMakeIdealPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestIdealSource.builder()
                        .bic("INGBNL2A")
                        .description("ORD50234E89")
                        .language("nl")
                        .build())
                .currency(Currency.EUR)
                .amount(1000L)
                .capture(true)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.IDEAL, paymentDetails.getSource().getType());
    }

    @Test
    @Disabled("not available")
    void shouldMakeOxxoPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestOxxoSource.builder()
                        .country(CountryCode.MX)
                        .description("ORD50234E89")
                        .payer(getPayer())
                        .build())
                .currency(Currency.MXN)
                .amount(1000L)
                .capture(true)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.OXXO, paymentDetails.getSource().getType());
    }

    @Test
    @Disabled("not available")
    void shouldMakePagoFacilPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestPagoFacilSource.builder()
                        .country(CountryCode.AR)
                        .description("simulate Via Pago Facil Demo Payment")
                        .payer(getPayer())
                        .build())
                .currency(Currency.ARS)
                .amount(1000L)
                .capture(true)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.PAGOFACIL, paymentDetails.getSource().getType());
    }

    @Test
    @Disabled("not available")
    void shouldMakeRapiPagoPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestRapiPagoSource.builder()
                        .country(CountryCode.AR)
                        .description("simulate Via Rapi Pago Demo Payment")
                        .payer(getPayer())
                        .build())
                .currency(Currency.ARS)
                .amount(1000L)
                .capture(true)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.RAPIPAGO, paymentDetails.getSource().getType());
    }

    @Test
    void shouldMakeSofortPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestSofortSource.builder()
                        .build())
                .currency(Currency.EUR)
                .amount(1000L)
                .capture(true)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.SOFORT, paymentDetails.getSource().getType());
    }

    @Test
    void shouldMakeKnetPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestKnetSource.builder()
                        .language("en")
                        .paymentMethodDetails(PaymentMethodDetails.builder()
                                .displayName("name")
                                .type("type")
                                .network("card_network")
                                .build())
                        .build())
                .currency(Currency.KWD)
                .amount(1000L)
                .capture(true)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.KNET, paymentDetails.getSource().getType());
    }

    @Disabled("not available")
    @Test
    void shouldMakePrzelewy24Payment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestP24Source.builder()
                        .paymentCountry(CountryCode.PL)
                        .accountHolderName("Bruce Wayne")
                        .accountHolderEmail("bruce@wayne-enterprises.com")
                        .billingDescriptor("P24 Demo Payment")
                        .build())
                .currency(Currency.PLN)
                .amount(100L)
                .capture(true)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.P24, paymentDetails.getSource().getType());
    }

    @Test
    void shouldMakePayPalPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestPayPalSource.builder()
                        .invoiceNumber("CKO00001")
                        .logoUrl("https://www.example.com/logo.jpg")
                        .build())
                .currency(Currency.EUR)
                .amount(100L)
                .capture(true)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.PAYPAL, paymentDetails.getSource().getType());
    }

    @Test
    void shouldMakePoliPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(new RequestPoliSource())
                .currency(Currency.AUD)
                .amount(111L)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.POLI, paymentDetails.getSource().getType());
    }

    @Disabled("not available")
    @Test
    void shouldMakeBancontactPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestBancontactSource.builder()
                        .paymentCountry(CountryCode.BE)
                        .accountHolderName("Bruce Wayne")
                        .billingDescriptor("CKO Demo - bancontact")
                        .build())
                .currency(Currency.EUR)
                .amount(100L)
                .capture(true)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.BANCONTACT, paymentDetails.getSource().getType());
    }

    @Test
    void shouldMakeQPayPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestQPaySource.builder()
                        .description("QPay Demo Payment")
                        .language("en")
                        .quantity(1)
                        .nationalId("070AYY010BU234M")
                        .build())
                .currency(Currency.QAR)
                .amount(100L)
                .capture(true)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.QPAY, paymentDetails.getSource().getType());
    }

    @Test
    @Disabled("not available")
    void shouldMakeMultiBancoPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestMultiBancoSource.builder()
                        .paymentCountry(CountryCode.PT)
                        .accountHolderName("Bruce Wayne")
                        .billingDescriptor("Multibanco Demo Payment")
                        .build())
                .currency(Currency.QAR)
                .amount(100L)
                .capture(true)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        final GetPaymentResponse paymentDetails = blocking(() -> paymentsClient.getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertTrue(paymentDetails.getSource() instanceof AlternativePaymentSourceResponse);
        assertEquals(PaymentSourceType.MULTIBANCO, paymentDetails.getSource().getType());
    }

}
