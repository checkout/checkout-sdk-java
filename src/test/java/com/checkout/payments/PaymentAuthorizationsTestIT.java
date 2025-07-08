package com.checkout.payments;

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
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentAuthorizationsTestIT extends SandboxTestFixture {

    public PaymentAuthorizationsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldIncrementPaymentAuthorization() {

        final PaymentResponse paymentResponse = makeIncrementalAuthorizationPayment();

        final AuthorizationRequest authorizationRequest = AuthorizationRequest.builder()
                .amount(100L)
                .reference(paymentResponse.getReference())
                .build();

        final AuthorizationResponse authorizationResponse = blocking(() -> checkoutApi.paymentsClient().incrementPaymentAuthorization(paymentResponse.getId(), authorizationRequest));
        assertNotNull(authorizationResponse);
        assertEquals(100, authorizationResponse.getAmount());
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

    @Test
    void shouldIncrementPaymentAuthorization_idempotently() {

        final PaymentResponse paymentResponse = makeIncrementalAuthorizationPayment();

        final AuthorizationRequest authorizationRequest = AuthorizationRequest.builder()
                .amount(6540L)
                .reference(paymentResponse.getReference())
                .build();

        final String idempotencyKey = UUID.randomUUID().toString();

        final AuthorizationResponse authorizationResponse1 = blocking(() -> checkoutApi.paymentsClient().incrementPaymentAuthorization(paymentResponse.getId(), authorizationRequest, idempotencyKey));
        assertNotNull(authorizationResponse1);

        final AuthorizationResponse authorizationResponse2 = blocking(() -> checkoutApi.paymentsClient().incrementPaymentAuthorization(paymentResponse.getId(), authorizationRequest, idempotencyKey));
        assertNotNull(authorizationResponse2);

        assertEquals(authorizationResponse1.getActionId(), authorizationResponse2.getActionId());

    }

    private PaymentResponse makeIncrementalAuthorizationPayment() {

        final RequestCardSource source = RequestCardSource.builder()
                .number("4556447238607884")
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
                        .type(AccountHolderIdentificationType.DRIVING_LICENSE)
                        .number("1234")
                        .issuingCountry(CountryCode.GB)
                        .build())
                .build();

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

        final PaymentRequest request = PaymentRequest.builder()
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

        return blocking(() -> checkoutApi.paymentsClient().requestPayment(request));

    }

}
