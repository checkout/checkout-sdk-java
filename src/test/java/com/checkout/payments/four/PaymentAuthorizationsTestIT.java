package com.checkout.payments.four;

import com.checkout.CardSourceHelper;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.payments.four.request.AuthorizationRequest;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.request.Payments;
import com.checkout.payments.four.request.source.RequestCardSource;
import com.checkout.payments.four.response.AuthorizationResponse;
import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.payments.four.sender.PaymentIndividualSender;
import com.checkout.payments.four.sender.SenderIdentification;
import com.checkout.payments.four.sender.SenderIdentificationType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentAuthorizationsTestIT extends SandboxTestFixture {

    public PaymentAuthorizationsTestIT() {
        super(PlatformType.FOUR);
    }

    @Test
    void shouldIncrementPaymentAuthorization() {

        final PaymentResponse paymentResponse = makeIncrementalAuthorizationPayment();

        final AuthorizationRequest authorizationRequest = AuthorizationRequest.builder()
                .amount(100L)
                .reference(paymentResponse.getReference())
                .build();

        final AuthorizationResponse authorizationResponse = blocking(() -> fourApi.paymentsClient().incrementPaymentAuthorization(paymentResponse.getId(), authorizationRequest));
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

        final AuthorizationResponse authorizationResponse1 = blocking(() -> fourApi.paymentsClient().incrementPaymentAuthorization(paymentResponse.getId(), authorizationRequest, idempotencyKey));
        assertNotNull(authorizationResponse1);

        final AuthorizationResponse authorizationResponse2 = blocking(() -> fourApi.paymentsClient().incrementPaymentAuthorization(paymentResponse.getId(), authorizationRequest, idempotencyKey));
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
                .identification(SenderIdentification.builder()
                        .type(SenderIdentificationType.DRIVING_LICENCE)
                        .number("1234")
                        .issuingCountry(CountryCode.GB)
                        .build())
                .build();

        final PaymentRequest request = Payments.card(source).fromSender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(10L)
                .currency(Currency.EUR)
                .authorizationType(AuthorizationType.ESTIMATED)
                .threeDS(null)
                .successUrl(null)
                .failureUrl(null)
                .build();

        return blocking(() -> fourApi.paymentsClient().requestPayment(request));

    }

}
