package com.checkout;

import com.checkout.common.Address;
import com.checkout.common.ChallengeIndicator;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.four.AccountHolderIdentification;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.request.source.RequestCardSource;
import com.checkout.payments.four.sender.PaymentCorporateSender;
import com.checkout.payments.four.sender.PaymentIndividualSender;
import com.checkout.payments.four.sender.PaymentSender;
import com.checkout.common.four.AccountHolderIdentificationType;

import java.util.UUID;

public class CardSourceHelper {

    private static long amount = 10L;

    public static class Visa {

        public static final String NAME = "Mr. Duke";
        public static final String NUMBER = "4242424242424242";
        public static final int EXPIRY_MONTH = 6;
        public static final int EXPIRY_YEAR = 2025;
        public static final String CVV = "100";

    }

    public static PaymentIndividualSender getIndividualSender() {
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
                        .type(AccountHolderIdentificationType.DRIVING_LICENCE)
                        .number("1234")
                        .issuingCountry(CountryCode.GB)
                        .build())
                .build();
    }

    public static PaymentCorporateSender getCorporateSender() {
        return PaymentCorporateSender.builder()
                .companyName("CheckoutSdk")
                .address(Address.builder()
                        .addressLine1("Address Line 1")
                        .addressLine2("Address Line 2")
                        .city("City")
                        .country(CountryCode.GB)
                        .build())
                .build();
    }

    public static RequestCardSource getRequestCardSource() {
        return RequestCardSource.builder()
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .cvv(Visa.CVV)
                .stored(false)
                .build();
    }

    public static PaymentRequest getCardSourcePayment(final RequestCardSource cardSource, final PaymentSender sender, final boolean three3ds) {
        final ThreeDSRequest threeDSRequest = ThreeDSRequest.builder().enabled(three3ds).challengeIndicator(ChallengeIndicator.NO_CHALLENGE_REQUESTED).build();
        return PaymentRequest.builder()
                .source(cardSource)
                .sender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(amount)
                .currency(Currency.EUR)
                .threeDS(threeDSRequest)
                .successUrl(three3ds ? "https://test.checkout.com/success" : null)
                .failureUrl(three3ds ? "https://test.checkout.com/failure" : null)
                .build();
    }

    public static PaymentRequest getCardSourcePaymentForDispute(final RequestCardSource cardSource, final PaymentSender sender, final boolean three3ds) {
        amount = 1040L;
        return getCardSourcePayment(cardSource, sender, three3ds);
    }

}