package com.checkout;

import com.checkout.common.AccountHolderIdentification;
import com.checkout.common.AccountHolderIdentificationType;
import com.checkout.common.Address;
import com.checkout.common.ChallengeIndicator;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.RequestCardSource;
import com.checkout.payments.sender.PaymentCorporateSender;
import com.checkout.payments.sender.PaymentIndividualSender;
import com.checkout.payments.sender.PaymentSender;

import java.util.UUID;

import static com.checkout.TestHelper.createAddress;
import static com.checkout.TestHelper.createPhone;
import static com.checkout.TestHelper.getAccountHolder;

public class CardSourceHelper {

    private static long amount = 10L;

    public static class Visa {

        public static final String NAME = "Mr. Duke";
        public static final String NUMBER = "4242424242424242";
        public static final int EXPIRY_MONTH = 12;
        public static final int EXPIRY_YEAR = 2030;
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
                        .type(AccountHolderIdentificationType.DRIVING_LICENSE)
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
                .billingAddress(createAddress())
                .phone(createPhone())
                .accountHolder(getAccountHolder())
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
                .processingChannelId(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID"))
                .successUrl(three3ds ? "https://test.checkout.com/success" : null)
                .failureUrl(three3ds ? "https://test.checkout.com/failure" : null)
                .build();
    }

    public static PaymentRequest getCardSourcePaymentForDispute(final RequestCardSource cardSource, final PaymentSender sender, final boolean three3ds) {
        amount = 1040L;
        return getCardSourcePayment(cardSource, sender, three3ds);
    }

}