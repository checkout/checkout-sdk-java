package com.checkout.payments;

import com.checkout.CardSourceHelper;
import com.checkout.CheckoutArgumentException;
import com.checkout.common.AccountHolderIdentification;
import com.checkout.common.AccountHolderIdentificationType;
import com.checkout.common.Address;
import com.checkout.common.AmountAllocations;
import com.checkout.common.ChallengeIndicator;
import com.checkout.common.Commission;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
import com.checkout.common.ThreeDSEnrollmentStatus;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.RequestCardSource;
import com.checkout.payments.request.source.RequestCustomerSource;
import com.checkout.payments.request.source.RequestIdSource;
import com.checkout.payments.request.source.RequestTokenSource;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.PaymentResponseBalances;
import com.checkout.payments.response.source.CardResponseSource;
import com.checkout.payments.sender.PaymentCorporateSender;
import com.checkout.payments.sender.PaymentIndividualSender;
import com.checkout.tokens.CardTokenResponse;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static com.checkout.CardSourceHelper.getCorporateSender;
import static com.checkout.CardSourceHelper.getIndividualSender;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestPaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldMakeCardPayment() {

        final RequestCardSource source = RequestCardSource.builder()
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .cvv(CardSourceHelper.Visa.CVV)
                .stored(false)
                .build();

        final PaymentIndividualSender sender = PaymentIndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .address(Address.builder()
                        .addressLine1("Address Line 1")
                        .addressLine2("Address Line 2")
                        .city("City")
                        .country(CountryCode.GB)
                        .build())
                .build();

        final PaymentPlanType recurringPlan = PaymentPlanType.builder()
                .amountVariability(AmountVariability.FIXED)
                .daysBetweenPayments(1)
                .totalNumberOfPayments(1)
                .currentPaymentNumber(1)
                .expiry(Instant.parse("2025-12-31T00:00:00Z"))
                .build();

        final PaymentRequest request = PaymentRequest.builder()
                .source(source)
                .paymentType(PaymentType.RECURRING)
                .paymentPlan(recurringPlan)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(10L)
                .currency(Currency.EUR)
                .amountAllocations(Collections.singletonList(AmountAllocations.builder()
                        .id("ent_sdioy6bajpzxyl3utftdp7legq")
                        .amount(10L)
                        .reference(UUID.randomUUID().toString())
                        .commission(Commission.builder()
                                .amount(1L)
                                .percentage(0.0)
                                .build())
                        .build()))
                .build();

        final PaymentResponse paymentResponse = blocking(() -> checkoutApi.paymentsClient().requestPayment(request));

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getId());
        assertEquals(Long.valueOf(10), paymentResponse.getAmount());
        assertEquals(Currency.EUR, paymentResponse.getCurrency());
        assertTrue(paymentResponse.isApproved());
        assertEquals(PaymentStatus.AUTHORIZED, paymentResponse.getStatus());
        assertEquals("Approved", paymentResponse.getResponseSummary());
        assertNull(paymentResponse.getThreeDSEnrollment());
        assertNull(paymentResponse.getCustomer());
        assertNotNull(paymentResponse.getReference());
        assertNotNull(paymentResponse.getProcessing());
        assertEquals(4, paymentResponse.getLinks().size());
        assertEquals(PaymentResponseBalances.builder()
                .availableToCapture(10L)
                .availableToRefund(0L)
                .availableToVoid(10L)
                .totalAuthorized(10L)
                .totalCaptured(0L)
                .totalRefunded(0L)
                .totalVoided(0L)
                .build(), paymentResponse.getBalances()
        );
        assertNotNull(paymentResponse.getProcessing());
        assertNotNull(paymentResponse.getProcessing().getAcquirerTransactionId());
        assertNotNull(paymentResponse.getProcessing().getRetrievalReferenceNumber());
        final CardResponseSource responseCardSource = (CardResponseSource) paymentResponse.getSource();
        assertNotNull(responseCardSource);
        assertEquals(PaymentSourceType.CARD, responseCardSource.getType());
        assertEquals(CardSourceHelper.Visa.EXPIRY_MONTH, (int) responseCardSource.getExpiryMonth());
        assertEquals(CardSourceHelper.Visa.EXPIRY_YEAR, (int) responseCardSource.getExpiryYear());
        assertEquals("Visa", responseCardSource.getScheme());

    }

    @Test
    void shouldMakeCardPayment_3ds() {

        final RequestCardSource source = RequestCardSource.builder()
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .cvv(CardSourceHelper.Visa.CVV)
                .stored(false)
                .build();

        final PaymentIndividualSender sender = PaymentIndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .address(Address.builder()
                        .addressLine1("Address Line 1")
                        .addressLine2("Address Line 2")
                        .city("City")
                        .country(CountryCode.GB)
                        .build())
                .identification(AccountHolderIdentification.builder()
                        .type(AccountHolderIdentificationType.DRIVING_LICENCE)
                        .number("1234")
                        .issuingCountry(CountryCode.GB)
                        .build())
                .build();

        final ThreeDSRequest threeDSRequest = ThreeDSRequest.builder().enabled(true).challengeIndicator(ChallengeIndicator.NO_CHALLENGE_REQUESTED).build();

        final PaymentRequest request = PaymentRequest.builder()
                .source(source)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(45L)
                .currency(Currency.EUR)
                .threeDS(threeDSRequest)
                .successUrl("https://test.checkout.com/success")
                .failureUrl("https://test.checkout.com/failure")
                .build();

        final PaymentResponse paymentResponse = blocking(() -> checkoutApi.paymentsClient().requestPayment(request));

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getId());
        assertNull(paymentResponse.getAmount());
        assertNull(paymentResponse.getCurrency());
        assertFalse(paymentResponse.isApproved());
        assertEquals(PaymentStatus.PENDING, paymentResponse.getStatus());
        assertNull(paymentResponse.getResponseSummary());
        assertEquals(ThreeDSEnrollment.builder().enrolled(ThreeDSEnrollmentStatus.ISSUER_ENROLLED).downgraded(false).build(), paymentResponse.getThreeDSEnrollment());
        assertNull(paymentResponse.getCustomer());
        assertNotNull(paymentResponse.getReference());
        assertNull(paymentResponse.getProcessing());
        assertEquals(3, paymentResponse.getLinks().size());
        assertNull(paymentResponse.getBalances());
        final CardResponseSource responseCardSource = (CardResponseSource) paymentResponse.getSource();
        assertNull(responseCardSource);

        final GetPaymentResponse paymentDetails = blocking(() -> checkoutApi.paymentsClient().getPayment(paymentResponse.getId()));
        assertNotNull(paymentDetails);
        assertNotNull(paymentDetails.getThreeDSData());
        assertEquals(ThreeDSEnrollmentStatus.ISSUER_ENROLLED, paymentDetails.getThreeDSData().getEnrolled());
        assertFalse(paymentDetails.getThreeDSData().getDowngraded());
        assertNotNull(paymentDetails.getThreeDSData().getVersion());
    }

    @Test
    void shouldMakeCardVerification() {

        final RequestCardSource source = RequestCardSource.builder()
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .cvv(CardSourceHelper.Visa.CVV)
                .stored(false)
                .build();

        final PaymentIndividualSender sender = PaymentIndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .address(Address.builder()
                        .addressLine1("Address Line 1")
                        .addressLine2("Address Line 2")
                        .city("City")
                        .country(CountryCode.GB)
                        .build())
                .identification(AccountHolderIdentification.builder()
                        .type(AccountHolderIdentificationType.DRIVING_LICENCE)
                        .number("1234")
                        .issuingCountry(CountryCode.GB)
                        .build())
                .build();

        final PaymentRequest request = PaymentRequest.builder()
                .source(source)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(0L)
                .currency(Currency.EUR)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> checkoutApi.paymentsClient().requestPayment(request));

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getId());
        assertEquals(Long.valueOf(0), paymentResponse.getAmount());
        assertEquals(Currency.EUR, paymentResponse.getCurrency());
        assertTrue(paymentResponse.isApproved());
        assertEquals(PaymentStatus.CARD_VERIFIED, paymentResponse.getStatus());
        assertEquals("Approved", paymentResponse.getResponseSummary());
        assertNull(paymentResponse.getThreeDSEnrollment());
        assertNull(paymentResponse.getCustomer());
        assertNotNull(paymentResponse.getReference());
        assertNotNull(paymentResponse.getProcessing());
        assertEquals(2, paymentResponse.getLinks().size());
        assertEquals(PaymentResponseBalances.builder()
                .availableToCapture(0L)
                .availableToRefund(0L)
                .availableToVoid(0L)
                .totalAuthorized(0L)
                .totalCaptured(0L)
                .totalRefunded(0L)
                .totalVoided(0L)
                .build(), paymentResponse.getBalances()
        );
        final CardResponseSource responseCardSource = (CardResponseSource) paymentResponse.getSource();
        assertNotNull(responseCardSource);
        assertEquals(PaymentSourceType.CARD, responseCardSource.getType());
        assertEquals(CardSourceHelper.Visa.EXPIRY_MONTH, (int) responseCardSource.getExpiryMonth());
        assertEquals(CardSourceHelper.Visa.EXPIRY_YEAR, (int) responseCardSource.getExpiryYear());
        assertEquals("Visa", responseCardSource.getScheme());

    }

    @Test
    void shouldMakeIdSourcePayment() {

        final PaymentResponse cardPaymentResponse = makeCardPayment(false);

        // id payment
        final RequestIdSource idSource = RequestIdSource.builder()
                .id(((CardResponseSource) cardPaymentResponse.getSource()).getId())
                .cvv(CardSourceHelper.Visa.CVV)
                .build();

        final PaymentIndividualSender sender = getIndividualSender();

        final PaymentRequest idSourceRequest = PaymentRequest.builder()
                .source(idSource)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(16L)
                .currency(Currency.EUR)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> checkoutApi.paymentsClient().requestPayment(idSourceRequest));

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getId());
        assertEquals(Long.valueOf(16), paymentResponse.getAmount());
        assertEquals(Currency.EUR, paymentResponse.getCurrency());
        assertTrue(paymentResponse.isApproved());
        assertEquals(PaymentStatus.AUTHORIZED, paymentResponse.getStatus());
        assertEquals("Approved", paymentResponse.getResponseSummary());
        assertNull(paymentResponse.getThreeDSEnrollment());
        assertNull(paymentResponse.getCustomer());
        assertNotNull(paymentResponse.getReference());
        assertNotNull(paymentResponse.getProcessing());
        assertEquals(4, paymentResponse.getLinks().size());
        assertEquals(PaymentResponseBalances.builder()
                .availableToCapture(16L)
                .availableToRefund(0L)
                .availableToVoid(16L)
                .totalAuthorized(16L)
                .totalCaptured(0L)
                .totalRefunded(0L)
                .totalVoided(0L)
                .build(), paymentResponse.getBalances()
        );
        final CardResponseSource responseCardSource = (CardResponseSource) paymentResponse.getSource();
        assertNotNull(responseCardSource);
        assertEquals(PaymentSourceType.CARD, responseCardSource.getType());
        assertEquals(CardSourceHelper.Visa.EXPIRY_MONTH, (int) responseCardSource.getExpiryMonth());
        assertEquals(CardSourceHelper.Visa.EXPIRY_YEAR, (int) responseCardSource.getExpiryYear());
        assertEquals("Visa", responseCardSource.getScheme());

    }

    @Test
    void shouldMakeTokenPayment() {

        final CardTokenResponse cardTokenResponse = requestToken();

        final RequestTokenSource tokenSource = RequestTokenSource.builder()
                .token(cardTokenResponse.getToken())
                .billingAddress(Address.builder()
                        .addressLine1("Address Line 1")
                        .addressLine2("Address Line 2")
                        .city("City")
                        .country(CountryCode.ES)
                        .build())
                .phone(Phone.builder().number("675676541").countryCode("+34").build())
                .build();

        final PaymentCorporateSender sender = getCorporateSender();

        final PaymentRequest tokenRequest = PaymentRequest.builder()
                .source(tokenSource)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(3456L)
                .currency(Currency.USD)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> checkoutApi.paymentsClient().requestPayment(tokenRequest));

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getId());
        assertEquals(Long.valueOf(3456), paymentResponse.getAmount());
        assertEquals(Currency.USD, paymentResponse.getCurrency());
        assertTrue(paymentResponse.isApproved());
        assertEquals(PaymentStatus.AUTHORIZED, paymentResponse.getStatus());
        assertEquals("Approved", paymentResponse.getResponseSummary());
        assertNull(paymentResponse.getThreeDSEnrollment());
        assertNull(paymentResponse.getCustomer());
        assertNotNull(paymentResponse.getReference());
        assertNotNull(paymentResponse.getProcessing());
        assertEquals(4, paymentResponse.getLinks().size());
        assertEquals(PaymentResponseBalances.builder()
                .availableToCapture(3456L)
                .availableToRefund(0L)
                .availableToVoid(3456L)
                .totalAuthorized(3456L)
                .totalCaptured(0L)
                .totalRefunded(0L)
                .totalVoided(0L)
                .build(), paymentResponse.getBalances()
        );
        final CardResponseSource responseCardSource = (CardResponseSource) paymentResponse.getSource();
        assertNotNull(responseCardSource);
        assertEquals(PaymentSourceType.CARD, responseCardSource.getType());

    }

    @Test
    void shouldMakeTokenPayment_3ds() {

        final CardTokenResponse cardTokenResponse = requestToken();

        final RequestTokenSource tokenSource = RequestTokenSource.builder()
                .token(cardTokenResponse.getToken())
                .billingAddress(Address.builder()
                        .addressLine1("Address Line 1")
                        .addressLine2("Address Line 2")
                        .city("City")
                        .country(CountryCode.ES)
                        .build())
                .phone(Phone.builder().number("675676541").countryCode("+34").build())
                .build();

        final PaymentIndividualSender sender = getIndividualSender();

        final ThreeDSRequest threeDSRequest = ThreeDSRequest.builder().enabled(true).challengeIndicator(ChallengeIndicator.NO_CHALLENGE_REQUESTED).build();

        final PaymentRequest tokenRequest = PaymentRequest.builder()
                .source(tokenSource)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(677777L)
                .currency(Currency.USD)
                .threeDS(threeDSRequest)
                .successUrl("https://test.checkout.com/success")
                .failureUrl("https://test.checkout.com/failure")
                .build();

        final PaymentResponse paymentResponse = blocking(() -> checkoutApi.paymentsClient().requestPayment(tokenRequest));

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getId());
        assertNull(paymentResponse.getAmount());
        assertNull(paymentResponse.getCurrency());
        assertFalse(paymentResponse.isApproved());
        assertEquals(PaymentStatus.PENDING, paymentResponse.getStatus());
        assertNull(paymentResponse.getResponseSummary());
        assertEquals(ThreeDSEnrollment.builder().enrolled(ThreeDSEnrollmentStatus.ISSUER_ENROLLED).downgraded(false).build(), paymentResponse.getThreeDSEnrollment());
        assertNull(paymentResponse.getCustomer());
        assertNotNull(paymentResponse.getReference());
        assertNull(paymentResponse.getProcessing());
        assertEquals(3, paymentResponse.getLinks().size());
        assertNull(paymentResponse.getBalances());
        final CardResponseSource responseCardSource = (CardResponseSource) paymentResponse.getSource();
        assertNull(responseCardSource);

    }

    @Test
    void shouldMakeCustomerPayment() {
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(RequestCustomerSource.builder()
                        .id("cus_udst2tfldj6upmye2reztkmm4i")
                        .build())
                .currency(Currency.GBP)
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), "customer_not_found");
    }

}
