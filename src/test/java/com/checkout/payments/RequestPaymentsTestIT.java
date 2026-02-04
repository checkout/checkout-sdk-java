package com.checkout.payments;

import static com.checkout.CardSourceHelper.getCorporateSender;
import static com.checkout.CardSourceHelper.getIndividualSender;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.checkout.CardSourceHelper;
import com.checkout.CheckoutApiException;
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

class RequestPaymentsTestIT extends AbstractPaymentsTestIT {

    @Test
    void shouldMakeCardPayment() {
        final RequestCardSource source = createCardSource();
        final PaymentIndividualSender sender = createIndividualSender();
        final PaymentPlan recurringPlan = createRecurringPlan();
        final PaymentRequest request = createCardPaymentRequest(source, sender, recurringPlan, 10L, Currency.EUR);

        final PaymentResponse paymentResponse = blocking(() -> checkoutApi.paymentsClient().requestPayment(request));

        validateCardPaymentResponse(paymentResponse, 10L, Currency.EUR, PaymentStatus.AUTHORIZED, 4);
    }

    @Test
    void shouldMakeCardPayment_3ds() {
        final RequestCardSource source = createCardSource();
        final PaymentIndividualSender sender = createIndividualSenderWithIdentification();
        final ThreeDSRequest threeDSRequest = create3DSRequest();
        final PaymentRequest request = create3DSPaymentRequest(source, sender, threeDSRequest, 45L, Currency.EUR);

        final PaymentResponse paymentResponse = blocking(() -> checkoutApi.paymentsClient().requestPayment(request));
        validate3DSPaymentResponse(paymentResponse, 3);
        final GetPaymentResponse paymentDetails = blocking(() -> checkoutApi.paymentsClient().getPayment(paymentResponse.getId()));

        validate3DSPaymentDetails(paymentDetails);
    }

    @Test
    void shouldMakeCardVerification() {
        final RequestCardSource source = createCardSource();
        final PaymentIndividualSender sender = createIndividualSenderWithIdentification();
        final PaymentRequest request = createVerificationPaymentRequest(source, sender);

        final PaymentResponse paymentResponse = blocking(() -> checkoutApi.paymentsClient().requestPayment(request));

        validateCardVerificationResponse(paymentResponse, 2);
    }

    @Test
    void shouldMakeIdSourcePayment() {
        final PaymentResponse cardPaymentResponse = makeCardPayment(false);
        final RequestIdSource idSource = createIdSource(cardPaymentResponse);
        final PaymentIndividualSender sender = getIndividualSender();
        final PaymentRequest idSourceRequest = createIdSourcePaymentRequest(idSource, sender, 16L);

        final PaymentResponse paymentResponse = blocking(() -> checkoutApi.paymentsClient().requestPayment(idSourceRequest));

        validateCardPaymentResponse(paymentResponse, 16L, Currency.EUR, PaymentStatus.AUTHORIZED, 4);
    }

    @Test
    void shouldMakeTokenPayment() {
        final CardTokenResponse cardTokenResponse = requestToken();
        final RequestTokenSource tokenSource = createTokenSource(cardTokenResponse);
        final PaymentCorporateSender sender = getCorporateSender();
        final PaymentRequest tokenRequest = createTokenPaymentRequest(tokenSource, sender, 3456L);

        final PaymentResponse paymentResponse = blocking(() -> checkoutApi.paymentsClient().requestPayment(tokenRequest));

        validateTokenPaymentResponse(paymentResponse, 3456L, Currency.USD, PaymentStatus.AUTHORIZED, 4);
    }

    @Test
    void shouldMakeTokenPayment_3ds() {
        final CardTokenResponse cardTokenResponse = requestToken();
        final RequestTokenSource tokenSource = createTokenSource(cardTokenResponse);
        final PaymentIndividualSender sender = getIndividualSender();
        final ThreeDSRequest threeDSRequest = create3DSRequest();
        final PaymentRequest tokenRequest = createToken3DSPaymentRequest(tokenSource, sender, threeDSRequest, 677777L);

        final PaymentResponse paymentResponse = blocking(() -> checkoutApi.paymentsClient().requestPayment(tokenRequest));

        validate3DSPaymentResponse(paymentResponse, 3);
    }

    @Test
    void shouldMakeCustomerPayment() {
        final PaymentRequest paymentRequest = createCustomerPaymentRequest();

        checkErrorItem(() -> paymentsClient.requestPayment(paymentRequest), "customer_not_found");
    }

    // Synchronous methods
    @Test
    void shouldMakeCardPaymentSync() {
        final RequestCardSource source = createCardSource();
        final PaymentIndividualSender sender = createIndividualSender();
        final PaymentPlan recurringPlan = createRecurringPlan();
        final PaymentRequest request = createCardPaymentRequest(source, sender, recurringPlan, 10L, Currency.EUR);

        final PaymentResponse paymentResponse = checkoutApi.paymentsClient().requestPaymentSync(request);

        validateCardPaymentResponse(paymentResponse, 10L, Currency.EUR, PaymentStatus.AUTHORIZED, 4);
    }

    @Test
    void shouldMakeCardPayment_3dsSync() {
        final RequestCardSource source = createCardSource();
        final PaymentIndividualSender sender = createIndividualSenderWithIdentification();
        final ThreeDSRequest threeDSRequest = create3DSRequest();
        final PaymentRequest request = create3DSPaymentRequest(source, sender, threeDSRequest, 45L, Currency.EUR);

        final PaymentResponse paymentResponse = checkoutApi.paymentsClient().requestPaymentSync(request);
        validate3DSPaymentResponse(paymentResponse, 3);     
        final GetPaymentResponse paymentDetails = checkoutApi.paymentsClient().getPaymentSync(paymentResponse.getId());

        validate3DSPaymentDetails(paymentDetails);
    }

    @Test
    void shouldMakeCardVerificationSync() {
        final RequestCardSource source = createCardSource();
        final PaymentIndividualSender sender = createIndividualSenderWithIdentification();
        final PaymentRequest request = createVerificationPaymentRequest(source, sender);

        final PaymentResponse paymentResponse = checkoutApi.paymentsClient().requestPaymentSync(request);

        validateCardVerificationResponse(paymentResponse, 2);
    }

    @Test
    void shouldMakeIdSourcePaymentSync() {
        final PaymentResponse cardPaymentResponse = makeCardPayment(false);
        final RequestIdSource idSource = createIdSource(cardPaymentResponse);
        final PaymentIndividualSender sender = getIndividualSender();
        final PaymentRequest idSourceRequest = createIdSourcePaymentRequest(idSource, sender, 16L);

        final PaymentResponse paymentResponse = checkoutApi.paymentsClient().requestPaymentSync(idSourceRequest);

        validateCardPaymentResponse(paymentResponse, 16L, Currency.EUR, PaymentStatus.AUTHORIZED, 4);
    }

    @Test
    void shouldMakeTokenPaymentSync() {
        final CardTokenResponse cardTokenResponse = requestToken();
        final RequestTokenSource tokenSource = createTokenSource(cardTokenResponse);
        final PaymentCorporateSender sender = getCorporateSender();
        final PaymentRequest tokenRequest = createTokenPaymentRequest(tokenSource, sender, 3456L);

        final PaymentResponse paymentResponse = checkoutApi.paymentsClient().requestPaymentSync(tokenRequest);

        validateTokenPaymentResponse(paymentResponse, 3456L, Currency.USD, PaymentStatus.AUTHORIZED, 4);
    }

    @Test
    void shouldMakeTokenPayment_3dsSync() {
        final CardTokenResponse cardTokenResponse = requestToken();
        final RequestTokenSource tokenSource = createTokenSource(cardTokenResponse);
        final PaymentIndividualSender sender = getIndividualSender();
        final ThreeDSRequest threeDSRequest = create3DSRequest();
        final PaymentRequest tokenRequest = createToken3DSPaymentRequest(tokenSource, sender, threeDSRequest, 677777L);

        final PaymentResponse paymentResponse = checkoutApi.paymentsClient().requestPaymentSync(tokenRequest);

        validate3DSPaymentResponse(paymentResponse, 3);
    }

    @Test
    void shouldMakeCustomerPaymentSync() {
        final PaymentRequest paymentRequest = createCustomerPaymentRequest();

        try {
            paymentsClient.requestPaymentSync(paymentRequest);
            fail("Expected CheckoutApiException");
        } catch (CheckoutApiException e) {
            assertTrue(e.getErrorDetails().get("error_codes").toString().contains("customer_not_found"));
        }
    }

    // Common methods
    private RequestCardSource createCardSource() {
        return RequestCardSource.builder()
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .cvv(CardSourceHelper.Visa.CVV)
                .stored(false)
                .build();
    }

    private PaymentIndividualSender createIndividualSender() {
        return PaymentIndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .address(createAddress())
                .build();
    }

    private PaymentIndividualSender createIndividualSenderWithIdentification() {
        return PaymentIndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .address(createAddress())
                .identification(createAccountHolderIdentification())
                .build();
    }

    private Address createAddress() {
        return Address.builder()
                .addressLine1("Address Line 1")
                .addressLine2("Address Line 2")
                .city("City")
                .country(CountryCode.GB)
                .build();
    }

    private Address createSpanishAddress() {
        return Address.builder()
                .addressLine1("Address Line 1")
                .addressLine2("Address Line 2")
                .city("City")
                .country(CountryCode.ES)
                .build();
    }

    private AccountHolderIdentification createAccountHolderIdentification() {
        return AccountHolderIdentification.builder()
                .type(AccountHolderIdentificationType.DRIVING_LICENSE)
                .number("1234")
                .issuingCountry(CountryCode.GB)
                .build();
    }

    private PaymentPlan createRecurringPlan() {
        return PaymentPlan.builder()
                .amountVariabilityType(AmountVariabilityType.FIXED)
                .daysBetweenPayments(1)
                .totalNumberOfPayments(1)
                .currentPaymentNumber(1)
                .expiry(Instant.parse("2025-12-31T00:00:00Z"))
                .build();
    }

    private ThreeDSRequest create3DSRequest() {
        return ThreeDSRequest.builder()
                .enabled(true)
                .challengeIndicator(ChallengeIndicator.NO_CHALLENGE_REQUESTED)
                .build();
    }

    private PaymentRequest createCardPaymentRequest(RequestCardSource source, PaymentIndividualSender sender, 
                                                   PaymentPlan paymentPlan, Long amount, Currency currency) {
        return PaymentRequest.builder()
                .source(source)
                .paymentType(PaymentType.RECURRING)
                .paymentPlan(paymentPlan)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(amount)
                .currency(currency)
                .amountAllocations(createAmountAllocations(amount))
                .build();
    }

    private PaymentRequest create3DSPaymentRequest(RequestCardSource source, PaymentIndividualSender sender, 
                                                  ThreeDSRequest threeDSRequest, Long amount, Currency currency) {
        return PaymentRequest.builder()
                .source(source)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(amount)
                .currency(currency)
                .threeDS(threeDSRequest)
                .successUrl("https://test.checkout.com/success")
                .failureUrl("https://test.checkout.com/failure")
                .build();
    }

    private PaymentRequest createVerificationPaymentRequest(RequestCardSource source, PaymentIndividualSender sender) {
        return PaymentRequest.builder()
                .source(source)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(0L)
                .currency(Currency.EUR)
                .build();
    }

    private RequestIdSource createIdSource(PaymentResponse cardPaymentResponse) {
        return RequestIdSource.builder()
                .id(((CardResponseSource) cardPaymentResponse.getSource()).getId())
                .cvv(CardSourceHelper.Visa.CVV)
                .build();
    }

    private PaymentRequest createIdSourcePaymentRequest(RequestIdSource idSource, PaymentIndividualSender sender, Long amount) {
        return PaymentRequest.builder()
                .source(idSource)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(amount)
                .currency(Currency.EUR)
                .build();
    }

    private RequestTokenSource createTokenSource(CardTokenResponse cardTokenResponse) {
        return RequestTokenSource.builder()
                .token(cardTokenResponse.getToken())
                .billingAddress(createSpanishAddress())
                .phone(Phone.builder().number("675676541").countryCode("+34").build())
                .build();
    }

    private PaymentRequest createTokenPaymentRequest(RequestTokenSource tokenSource, PaymentCorporateSender sender, Long amount) {
        return PaymentRequest.builder()
                .source(tokenSource)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(amount)
                .currency(Currency.USD)
                .build();
    }

    private PaymentRequest createToken3DSPaymentRequest(RequestTokenSource tokenSource, PaymentIndividualSender sender, 
                                                       ThreeDSRequest threeDSRequest, Long amount) {
        return PaymentRequest.builder()
                .source(tokenSource)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(amount)
                .currency(Currency.USD)
                .threeDS(threeDSRequest)
                .successUrl("https://test.checkout.com/success")
                .failureUrl("https://test.checkout.com/failure")
                .build();
    }

    private PaymentRequest createCustomerPaymentRequest() {
        return PaymentRequest.builder()
                .source(RequestCustomerSource.builder()
                        .id("cus_udst2tfldj6upmye2reztkmm4i")
                        .build())
                .currency(Currency.GBP)
                .amount(10L)
                .capture(true)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();
    }

    private java.util.List<AmountAllocations> createAmountAllocations(Long amount) {
        return Collections.singletonList(AmountAllocations.builder()
                .id("ent_sdioy6bajpzxyl3utftdp7legq")
                .amount(amount)
                .reference(UUID.randomUUID().toString())
                .commission(Commission.builder()
                        .amount(1L)
                        .percentage(0.0)
                        .build())
                .build());
    }

    private void validateCardPaymentResponse(PaymentResponse paymentResponse, Long expectedAmount, Currency expectedCurrency, 
                                           PaymentStatus expectedStatus, int expectedLinksCount) {
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getId());
        assertEquals(expectedAmount, paymentResponse.getAmount());
        assertEquals(expectedCurrency, paymentResponse.getCurrency());
        assertTrue(paymentResponse.isApproved());
        assertEquals(expectedStatus, paymentResponse.getStatus());
        assertEquals("Approved", paymentResponse.getResponseSummary());
        assertNull(paymentResponse.getThreeDSEnrollment());
        assertNull(paymentResponse.getCustomer());
        assertNotNull(paymentResponse.getReference());
        assertNotNull(paymentResponse.getProcessing());
        assertEquals(expectedLinksCount, paymentResponse.getLinks().size());
        
        validatePaymentBalances(paymentResponse, expectedAmount);
        validateCardSource(paymentResponse);
        
        if (expectedStatus == PaymentStatus.AUTHORIZED) {
            validateProcessingDetails(paymentResponse);
        }
    }

    private void validateCardVerificationResponse(PaymentResponse paymentResponse, int expectedLinksCount) {
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
        assertEquals(expectedLinksCount, paymentResponse.getLinks().size());
        
        validatePaymentBalances(paymentResponse, 0L);
        validateCardSource(paymentResponse);
    }

    private void validate3DSPaymentResponse(PaymentResponse paymentResponse, int expectedLinksCount) {
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getId());
        assertNull(paymentResponse.getAmount());
        assertNull(paymentResponse.getCurrency());
        assertFalse(paymentResponse.isApproved());
        assertEquals(PaymentStatus.PENDING, paymentResponse.getStatus());
        assertNull(paymentResponse.getResponseSummary());
        assertEquals(ThreeDSEnrollment.builder().enrolled(ThreeDSEnrollmentStatus.ISSUER_ENROLLED).downgraded(false).build(), 
                     paymentResponse.getThreeDSEnrollment());
        assertNull(paymentResponse.getCustomer());
        assertNotNull(paymentResponse.getReference());
        assertNull(paymentResponse.getProcessing());
        assertEquals(expectedLinksCount, paymentResponse.getLinks().size());
        assertNull(paymentResponse.getBalances());
        final CardResponseSource responseCardSource = (CardResponseSource) paymentResponse.getSource();
        assertNull(responseCardSource);
    }

    private void validate3DSPaymentDetails(GetPaymentResponse paymentDetails) {
        assertNotNull(paymentDetails);
        assertNotNull(paymentDetails.getThreeDSData());
        assertEquals(ThreeDSEnrollmentStatus.ISSUER_ENROLLED, paymentDetails.getThreeDSData().getEnrolled());
        assertFalse(paymentDetails.getThreeDSData().getDowngraded());
        assertNotNull(paymentDetails.getThreeDSData().getVersion());
    }

    private void validateTokenPaymentResponse(PaymentResponse paymentResponse, Long expectedAmount, Currency expectedCurrency, 
                                            PaymentStatus expectedStatus, int expectedLinksCount) {
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getId());
        assertEquals(expectedAmount, paymentResponse.getAmount());
        assertEquals(expectedCurrency, paymentResponse.getCurrency());
        assertTrue(paymentResponse.isApproved());
        assertEquals(expectedStatus, paymentResponse.getStatus());
        assertEquals("Approved", paymentResponse.getResponseSummary());
        assertNull(paymentResponse.getThreeDSEnrollment());
        assertNull(paymentResponse.getCustomer());
        assertNotNull(paymentResponse.getReference());
        assertNotNull(paymentResponse.getProcessing());
        assertEquals(expectedLinksCount, paymentResponse.getLinks().size());
        
        validatePaymentBalances(paymentResponse, expectedAmount);
        
        final CardResponseSource responseCardSource = (CardResponseSource) paymentResponse.getSource();
        assertNotNull(responseCardSource);
        assertEquals(PaymentSourceType.CARD, responseCardSource.getType());
    }

    private void validatePaymentBalances(PaymentResponse paymentResponse, Long expectedAmount) {
        assertEquals(PaymentResponseBalances.builder()
                .availableToCapture(expectedAmount)
                .availableToRefund(0L)
                .availableToVoid(expectedAmount)
                .totalAuthorized(expectedAmount)
                .totalCaptured(0L)
                .totalRefunded(0L)
                .totalVoided(0L)
                .build(), paymentResponse.getBalances());
    }

    private void validateCardSource(PaymentResponse paymentResponse) {
        final CardResponseSource responseCardSource = (CardResponseSource) paymentResponse.getSource();
        assertNotNull(responseCardSource);
        assertEquals(PaymentSourceType.CARD, responseCardSource.getType());
        assertEquals(CardSourceHelper.Visa.EXPIRY_MONTH, (int) responseCardSource.getExpiryMonth());
        assertEquals(CardSourceHelper.Visa.EXPIRY_YEAR, (int) responseCardSource.getExpiryYear());
        assertEquals("Visa", responseCardSource.getScheme());
    }

    private void validateProcessingDetails(PaymentResponse paymentResponse) {
        assertNotNull(paymentResponse.getProcessing());
        assertNotNull(paymentResponse.getProcessing().getAcquirerTransactionId());
        assertNotNull(paymentResponse.getProcessing().getRetrievalReferenceNumber());
    }

}
