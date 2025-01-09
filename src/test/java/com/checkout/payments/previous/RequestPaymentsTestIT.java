package com.checkout.payments.previous;

import com.checkout.CardSourceHelper;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
import com.checkout.common.ThreeDSEnrollmentStatus;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.previous.request.PaymentRequest;
import com.checkout.payments.previous.request.source.RequestCardSource;
import com.checkout.payments.previous.response.PaymentResponse;
import com.checkout.payments.previous.response.source.CardResponseSource;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestPaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldMakeCardPayment() {

        final PaymentResponse paymentResponse = makeCardPayment(false, 10);

        assertNotNull(paymentResponse.getId());
        assertNotNull(paymentResponse.getProcessedOn());
        assertNotNull(paymentResponse.getReference());
        assertNotNull(paymentResponse.getActionId());
        assertNotNull(paymentResponse.getResponseCode());
        assertNotNull(paymentResponse.getSchemeId());
        assertEquals("Approved", paymentResponse.getResponseSummary());
        assertEquals(PaymentStatus.AUTHORIZED, paymentResponse.getStatus());
        assertEquals(10, paymentResponse.getAmount());
        assertTrue(paymentResponse.getApproved());
        assertNotNull(paymentResponse.getAuthCode());
        assertEquals(Currency.GBP, paymentResponse.getCurrency());
        assertNull(paymentResponse.getThreeDSEnrollment());
        // source
        assertTrue(paymentResponse.getSource() instanceof CardResponseSource);
        final CardResponseSource responseCardSource = (CardResponseSource) paymentResponse.getSource();
        assertNotNull(responseCardSource.getBillingAddress());
        assertNotNull(responseCardSource.getPhone());
        assertEquals(PaymentSourceType.CARD, responseCardSource.getType());
        assertNotNull(responseCardSource.getId());
        assertEquals("S", responseCardSource.getAvsCheck());
        assertEquals("Y", responseCardSource.getCvvCheck());
        assertNotNull(responseCardSource.getBin());
        //assertEquals(CardCategory.CONSUMER, responseCardSource.getCardCategory());
        //assertEquals(CardType.CREDIT, responseCardSource.getCardType());
        assertEquals(CardSourceHelper.Visa.EXPIRY_MONTH, responseCardSource.getExpiryMonth());
        assertEquals(CardSourceHelper.Visa.EXPIRY_YEAR, responseCardSource.getExpiryYear());
        assertNotNull(responseCardSource.getLast4());
        //assertNotNull(responseCardSource.getScheme());
        assertNotNull(responseCardSource.getName());
        assertNotNull(responseCardSource.getFastFunds());
        assertNotNull(responseCardSource.getFingerprint());
        //assertNotNull(responseCardSource.getIssuer());
        //assertEquals(CountryCode.US, responseCardSource.getIssuerCountry());
        assertTrue(responseCardSource.getPayouts());
        //assertNotNull(responseCardSource.getProductId());
        //assertNotNull(responseCardSource.getProductType());
        // customer
        assertNotNull(paymentResponse.getCustomer());
        assertNotNull(paymentResponse.getCustomer().getId());
        assertNotNull(paymentResponse.getCustomer().getName());
        // processing
        assertNotNull(paymentResponse.getProcessing());
        assertNotNull(paymentResponse.getProcessing().getAcquirerTransactionId());
        assertNotNull(paymentResponse.getProcessing().getRetrievalReferenceNumber());
        //risk
        assertFalse(paymentResponse.getRisk().getFlagged());
        // links
        assertEquals(4, paymentResponse.getLinks().size());
        assertNotNull(paymentResponse.getLink(SELF));
        assertNotNull(paymentResponse.getLink("actions"));
        assertNotNull(paymentResponse.getLink("capture"));
        assertNotNull(paymentResponse.getLink("void"));
    }

    @Test
    void shouldMakeCardVerification() {

        final Phone phone = Phone.builder()
                .countryCode("44")
                .number("020 222333")
                .build();

        final Address billingAddress = Address.builder()
                .addressLine1("CheckoutSdk.com")
                .addressLine2("90 Tottenham Court Road")
                .city("London")
                .state("London")
                .zip("W1T 4TJ")
                .country(CountryCode.GB)
                .build();

        final RequestCardSource source = RequestCardSource.builder()
                .name(CardSourceHelper.Visa.NAME)
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .cvv(CardSourceHelper.Visa.CVV)
                .stored(false)
                .billingAddress(billingAddress)
                .phone(phone)
                .build();

        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(source)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(0L)
                .currency(Currency.GBP)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        assertNotNull(paymentResponse.getId());
        assertNotNull(paymentResponse.getProcessedOn());
        assertNotNull(paymentResponse.getReference());
        assertNotNull(paymentResponse.getActionId());
        assertNotNull(paymentResponse.getResponseCode());
        assertNotNull(paymentResponse.getSchemeId());
        assertEquals("Approved", paymentResponse.getResponseSummary());
        assertEquals(PaymentStatus.CARD_VERIFIED, paymentResponse.getStatus());
        assertEquals(0, paymentResponse.getAmount());
        assertTrue(paymentResponse.getApproved());
        assertNotNull(paymentResponse.getAuthCode());
        assertEquals(Currency.GBP, paymentResponse.getCurrency());
        assertNull(paymentResponse.getThreeDSEnrollment());
        // source
        assertTrue(paymentResponse.getSource() instanceof CardResponseSource);
        final CardResponseSource responseCardSource = (CardResponseSource) paymentResponse.getSource();
        assertNotNull(responseCardSource.getBillingAddress());
        assertNotNull(responseCardSource.getPhone());
        assertEquals(PaymentSourceType.CARD, responseCardSource.getType());
        assertNotNull(responseCardSource.getId());
        assertEquals("S", responseCardSource.getAvsCheck());
        assertEquals("Y", responseCardSource.getCvvCheck());
        assertNotNull(responseCardSource.getBin());
        //assertEquals(CardCategory.CONSUMER, responseCardSource.getCardCategory());
        //assertEquals(CardType.CREDIT, responseCardSource.getCardType());
        assertEquals(CardSourceHelper.Visa.EXPIRY_MONTH, responseCardSource.getExpiryMonth());
        assertEquals(CardSourceHelper.Visa.EXPIRY_YEAR, responseCardSource.getExpiryYear());
        assertNotNull(responseCardSource.getLast4());
        //assertNotNull(responseCardSource.getScheme());
        assertNotNull(responseCardSource.getName());
        assertNotNull(responseCardSource.getFastFunds());
        assertNotNull(responseCardSource.getFingerprint());
        //assertNotNull(responseCardSource.getIssuer());
        //assertEquals(CountryCode.US, responseCardSource.getIssuerCountry());
        assertTrue(responseCardSource.getPayouts());
        //assertNotNull(responseCardSource.getProductId());
        //assertNotNull(responseCardSource.getProductType());
        // customer
        assertNotNull(paymentResponse.getCustomer());
        assertNotNull(paymentResponse.getCustomer().getId());
        assertNotNull(paymentResponse.getCustomer().getName());
        // processing
        assertNotNull(paymentResponse.getProcessing());
        assertNotNull(paymentResponse.getProcessing().getAcquirerTransactionId());
        assertNotNull(paymentResponse.getProcessing().getRetrievalReferenceNumber());
        //risk
        assertFalse(paymentResponse.getRisk().getFlagged());
        // links
        assertEquals(2, paymentResponse.getLinks().size());
        assertNotNull(paymentResponse.getLink(SELF));
        assertNotNull(paymentResponse.getLink("actions"));

    }

    @Test
    @Disabled("unavailable")
    void shouldMakeCardPayment_3ds() {

        final PaymentResponse paymentResponse = make3dsCardPayment(false);

        assertNotNull(paymentResponse.getId());
        assertNotNull(paymentResponse.getReference());
        assertEquals(PaymentStatus.PENDING, paymentResponse.getStatus());
        //3ds
        assertNotNull(paymentResponse.getThreeDSEnrollment());
        assertFalse(paymentResponse.getThreeDSEnrollment().getDowngraded());
        assertEquals(ThreeDSEnrollmentStatus.ISSUER_ENROLLED, paymentResponse.getThreeDSEnrollment().getEnrolled());
        // customer
        assertNotNull(paymentResponse.getCustomer());
        assertNotNull(paymentResponse.getCustomer().getId());
        assertNotNull(paymentResponse.getCustomer().getName());
        // links
        assertEquals(2, paymentResponse.getLinks().size());
        assertNotNull(paymentResponse.getLink(SELF));
        assertNotNull(paymentResponse.getLink("redirect"));

    }

    @Test
    void shouldMakeCardPayment_n3d() {

        final PaymentResponse paymentResponse = make3dsCardPayment(true);

        assertNotNull(paymentResponse.getId());
        assertNotNull(paymentResponse.getProcessedOn());
        assertNotNull(paymentResponse.getReference());
        assertNotNull(paymentResponse.getActionId());
        assertNotNull(paymentResponse.getResponseCode());
        assertNotNull(paymentResponse.getSchemeId());
        assertEquals("Approved", paymentResponse.getResponseSummary());
        assertEquals(PaymentStatus.AUTHORIZED, paymentResponse.getStatus());
        assertEquals(10, paymentResponse.getAmount());
        assertTrue(paymentResponse.getApproved());
        assertNotNull(paymentResponse.getAuthCode());
        assertEquals(Currency.USD, paymentResponse.getCurrency());
        assertNull(paymentResponse.getThreeDSEnrollment());
        // source
        assertTrue(paymentResponse.getSource() instanceof CardResponseSource);
        final CardResponseSource responseCardSource = (CardResponseSource) paymentResponse.getSource();
        assertNotNull(responseCardSource.getBillingAddress());
        assertNotNull(responseCardSource.getPhone());
        assertEquals(PaymentSourceType.CARD, responseCardSource.getType());
        assertNotNull(responseCardSource.getId());
        assertEquals("S", responseCardSource.getAvsCheck());
        assertEquals("Y", responseCardSource.getCvvCheck());
        assertNotNull(responseCardSource.getBin());
        //assertEquals(CardCategory.CONSUMER, responseCardSource.getCardCategory());
        //assertEquals(CardType.CREDIT, responseCardSource.getCardType());
        assertEquals(CardSourceHelper.Visa.EXPIRY_MONTH, responseCardSource.getExpiryMonth());
        assertEquals(CardSourceHelper.Visa.EXPIRY_YEAR, responseCardSource.getExpiryYear());
        assertNotNull(responseCardSource.getLast4());
        //assertNotNull(responseCardSource.getScheme());
        assertNotNull(responseCardSource.getName());
        assertNotNull(responseCardSource.getFastFunds());
        assertNotNull(responseCardSource.getFingerprint());
        //assertNotNull(responseCardSource.getIssuer());
        //assertEquals(CountryCode.US, responseCardSource.getIssuerCountry());
        assertTrue(responseCardSource.getPayouts());
        //assertNotNull(responseCardSource.getProductId());
        //assertNotNull(responseCardSource.getProductType());
        // customer
        assertNotNull(paymentResponse.getCustomer());
        assertNotNull(paymentResponse.getCustomer().getId());
        assertNotNull(paymentResponse.getCustomer().getName());
        // processing
        assertNotNull(paymentResponse.getProcessing());
        assertNotNull(paymentResponse.getProcessing().getAcquirerTransactionId());
        assertNotNull(paymentResponse.getProcessing().getRetrievalReferenceNumber());
        //risk
        assertFalse(paymentResponse.getRisk().getFlagged());
        // links
        assertEquals(4, paymentResponse.getLinks().size());
        assertNotNull(paymentResponse.getLink(SELF));
        assertNotNull(paymentResponse.getLink("actions"));
        assertNotNull(paymentResponse.getLink("capture"));
        assertNotNull(paymentResponse.getLink("void"));

    }

    @Test
    @Disabled("unavailable")
    void shouldMakeTokenPayment() {

        final PaymentResponse paymentResponse = makeTokenPayment();

        assertNotNull(paymentResponse.getId());
        assertNotNull(paymentResponse.getProcessedOn());
        assertNotNull(paymentResponse.getReference());
        assertNotNull(paymentResponse.getActionId());
        assertNotNull(paymentResponse.getResponseCode());
        assertNotNull(paymentResponse.getSchemeId());
        assertEquals("Approved", paymentResponse.getResponseSummary());
        assertEquals(PaymentStatus.AUTHORIZED, paymentResponse.getStatus());
        assertEquals(10, paymentResponse.getAmount());
        assertTrue(paymentResponse.getApproved());
        assertNotNull(paymentResponse.getAuthCode());
        assertEquals(Currency.GBP, paymentResponse.getCurrency());
        assertNull(paymentResponse.getThreeDSEnrollment());
        // source
        assertTrue(paymentResponse.getSource() instanceof CardResponseSource);
        final CardResponseSource responseCardSource = (CardResponseSource) paymentResponse.getSource();
        assertNotNull(responseCardSource.getBillingAddress());
        assertNotNull(responseCardSource.getPhone());
        assertEquals(PaymentSourceType.CARD, responseCardSource.getType());
        assertNotNull(responseCardSource.getId());
        assertEquals("S", responseCardSource.getAvsCheck());
        assertEquals("Y", responseCardSource.getCvvCheck());
        assertNotNull(responseCardSource.getBin());
        //assertEquals(CardCategory.CONSUMER, responseCardSource.getCardCategory());
        //assertEquals(CardType.CREDIT, responseCardSource.getCardType());
        assertEquals(CardSourceHelper.Visa.EXPIRY_MONTH, responseCardSource.getExpiryMonth());
        assertEquals(CardSourceHelper.Visa.EXPIRY_YEAR, responseCardSource.getExpiryYear());
        assertNotNull(responseCardSource.getLast4());
        //assertNotNull(responseCardSource.getScheme());
        assertNotNull(responseCardSource.getName());
        assertNotNull(responseCardSource.getFastFunds());
        assertNotNull(responseCardSource.getFingerprint());
        //assertNotNull(responseCardSource.getIssuer());
        //assertEquals(CountryCode.US, responseCardSource.getIssuerCountry());
        assertTrue(responseCardSource.getPayouts());
        //assertNotNull(responseCardSource.getProductId());
        //assertNotNull(responseCardSource.getProductType());
        // customer
        assertNotNull(paymentResponse.getCustomer());
        assertNotNull(paymentResponse.getCustomer().getId());
        assertNull(paymentResponse.getCustomer().getName());
        // processing
        assertNotNull(paymentResponse.getProcessing());
        assertNotNull(paymentResponse.getProcessing().getAcquirerTransactionId());
        assertNotNull(paymentResponse.getProcessing().getRetrievalReferenceNumber());
        //risk
        assertFalse(paymentResponse.getRisk().getFlagged());
        // links
        assertEquals(4, paymentResponse.getLinks().size());
        assertNotNull(paymentResponse.getLink(SELF));
        assertNotNull(paymentResponse.getLink("actions"));
        assertNotNull(paymentResponse.getLink("capture"));
        assertNotNull(paymentResponse.getLink("void"));

    }

    @Test
    void shouldMakePaymentsIdempotently() {

        final Phone phone = Phone.builder()
                .countryCode("44")
                .number("020 222333")
                .build();

        final Address billingAddress = Address.builder()
                .addressLine1("CheckoutSdk.com")
                .addressLine2("90 Tottenham Court Road")
                .city("London")
                .state("London")
                .zip("W1T 4TJ")
                .country(CountryCode.GB)
                .build();

        final RequestCardSource source = RequestCardSource.builder()
                .name(CardSourceHelper.Visa.NAME)
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .cvv(CardSourceHelper.Visa.CVV)
                .stored(false)
                .billingAddress(billingAddress)
                .phone(phone)
                .build();

        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(source)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(10L)
                .currency(Currency.GBP)
                .build();

        final PaymentResponse paymentResponse1 = blocking(() -> paymentsClient.requestPayment(paymentRequest, IDEMPOTENCY_KEY));
        assertNotNull(paymentResponse1);

        final PaymentResponse paymentResponse2 = blocking(() -> paymentsClient.requestPayment(paymentRequest, IDEMPOTENCY_KEY));
        assertNotNull(paymentResponse2);

        assertEquals(paymentResponse1.getId(), paymentResponse2.getId());

    }

}
