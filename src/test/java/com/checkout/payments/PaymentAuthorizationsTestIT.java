package com.checkout.payments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.checkout.CardSourceHelper;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.AccountHolderIdentification;
import com.checkout.common.AccountHolderIdentificationType;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.payments.request.Authentication;
import com.checkout.payments.request.AuthorizationRequest;
import com.checkout.payments.request.PartialAuthorization;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.RequestCardSource;
import com.checkout.payments.response.AuthorizationResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.sender.PaymentIndividualSender;

class PaymentAuthorizationsTestIT extends SandboxTestFixture {

    public PaymentAuthorizationsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldIncrementPaymentAuthorization() {
        final PaymentResponse paymentResponse = makeIncrementalAuthorizationPayment();
        final AuthorizationRequest authorizationRequest = createAuthorizationRequest(100L, paymentResponse.getReference());

        final AuthorizationResponse authorizationResponse = blocking(() -> checkoutApi.paymentsClient().incrementPaymentAuthorization(paymentResponse.getId(), authorizationRequest));
        
        validateAuthorizationResponse(authorizationResponse, 100);
    }

    @Test
    void shouldIncrementPaymentAuthorization_idempotently() {
        final PaymentResponse paymentResponse = makeIncrementalAuthorizationPayment();
        final AuthorizationRequest authorizationRequest = createAuthorizationRequest(6540L, paymentResponse.getReference());
        final String idempotencyKey = UUID.randomUUID().toString();

        final AuthorizationResponse authorizationResponse1 = blocking(() -> checkoutApi.paymentsClient().incrementPaymentAuthorization(paymentResponse.getId(), authorizationRequest, idempotencyKey));
        final AuthorizationResponse authorizationResponse2 = blocking(() -> checkoutApi.paymentsClient().incrementPaymentAuthorization(paymentResponse.getId(), authorizationRequest, idempotencyKey));

        validateIdempotencyResponse(authorizationResponse1, authorizationResponse2);
    }

    // Synchronous methods
    @Test
    void shouldIncrementPaymentAuthorizationSync() {
        final PaymentResponse paymentResponse = makeIncrementalAuthorizationPayment();
        final AuthorizationRequest authorizationRequest = createAuthorizationRequest(100L, paymentResponse.getReference());

        final AuthorizationResponse authorizationResponse = checkoutApi.paymentsClient().incrementPaymentAuthorizationSync(paymentResponse.getId(), authorizationRequest);
        
        validateAuthorizationResponse(authorizationResponse, 100);
    }

    @Test
    void shouldIncrementPaymentAuthorizationSync_idempotently() {
        final PaymentResponse paymentResponse = makeIncrementalAuthorizationPayment();
        final AuthorizationRequest authorizationRequest = createAuthorizationRequest(6540L, paymentResponse.getReference());
        final String idempotencyKey = UUID.randomUUID().toString();

        final AuthorizationResponse authorizationResponse1 = checkoutApi.paymentsClient().incrementPaymentAuthorizationSync(paymentResponse.getId(), authorizationRequest, idempotencyKey);
        final AuthorizationResponse authorizationResponse2 = checkoutApi.paymentsClient().incrementPaymentAuthorizationSync(paymentResponse.getId(), authorizationRequest, idempotencyKey);

        validateIdempotencyResponse(authorizationResponse1, authorizationResponse2);
    }

    // Common methods
    private AuthorizationRequest createAuthorizationRequest(Long amount, String reference) {
        return AuthorizationRequest.builder()
                .amount(amount)
                .reference(reference)
                .build();
    }

    private RequestCardSource createIncrementalCardSource() {
        return RequestCardSource.builder()
                .number("4556447238607884")
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .cvv(CardSourceHelper.Visa.CVV)
                .stored(false)
                .build();
    }

    private PaymentIndividualSender createIncrementalSender() {
        return PaymentIndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .address(Address.builder()
                        .addressLine1("Address Line 1")
                        .addressLine2("Address Line 2")
                        .city("City")
                        .country(CountryCode.GB)
                        .build())
                .identification(AccountHolderIdentification.builder()
                        .type(AccountHolderIdentificationType.DRIVING_LICENSE)
                        .number("1234")
                        .issuingCountry(CountryCode.GB)
                        .build())
                .build();
    }

    private PaymentRequest createIncrementalPaymentRequest(RequestCardSource source, PaymentIndividualSender sender) {
        final PartialAuthorization partialAuthorization = PartialAuthorization.builder()
                .enabled(true)
                .build();

        final List<PreferredExperiences> experiences = Arrays.asList(
                PreferredExperiences.GOOGLE_SPA,
                PreferredExperiences.THREE_DS
        );

        final Authentication authentication = Authentication.builder()
                .preferredExperiences(experiences)
                .build();

        return PaymentRequest.builder()
                .source(source)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(10L)
                .currency(Currency.EUR)
                .authorizationType(AuthorizationType.ESTIMATED)
                .threeDS(null)
                .successUrl(null)
                .failureUrl(null)
                .partialAuthorization(partialAuthorization)
                .authentication(authentication)
                .build();
    }

    private PaymentResponse makeIncrementalAuthorizationPayment() {
        final RequestCardSource source = createIncrementalCardSource();
        final PaymentIndividualSender sender = createIncrementalSender();
        final PaymentRequest request = createIncrementalPaymentRequest(source, sender);

        return blocking(() -> checkoutApi.paymentsClient().requestPayment(request));
    }

    private void validateAuthorizationResponse(AuthorizationResponse authorizationResponse, int expectedAmount) {
        assertNotNull(authorizationResponse);
        assertEquals(expectedAmount, authorizationResponse.getAmount());
        assertNotNull(authorizationResponse.getActionId());
        assertNotNull(authorizationResponse.getCurrency());
        assertFalse(authorizationResponse.getApproved());
        assertNotNull(authorizationResponse.getResponseCode());
        assertNotNull(authorizationResponse.getResponseSummary());
        assertNotNull(authorizationResponse.getExpiresOn());
        assertNotNull(authorizationResponse.getProcessedOn());
        assertNotNull(authorizationResponse.getBalances());
        assertNotNull(authorizationResponse.getLinks());
        assertNotNull(authorizationResponse.getRisk());
    }

    private void validateIdempotencyResponse(AuthorizationResponse response1, AuthorizationResponse response2) {
        assertNotNull(response1);
        assertNotNull(response2);
        assertEquals(response1.getActionId(), response2.getActionId());
    }

}
