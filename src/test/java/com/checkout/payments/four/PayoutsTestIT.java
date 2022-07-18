package com.checkout.payments.four;


import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.common.four.AccountHolder;
import com.checkout.common.four.AccountHolderType;
import com.checkout.common.four.SenderIdentification;
import com.checkout.payments.four.request.PaymentInstruction;
import com.checkout.payments.four.request.PayoutBillingDescriptor;
import com.checkout.payments.four.request.PayoutRequest;
import com.checkout.payments.four.request.destination.PaymentRequestCardDestination;
import com.checkout.payments.four.request.source.PayoutRequestCurrencyAccountSource;
import com.checkout.payments.four.response.PayoutResponse;
import com.checkout.payments.four.sender.PaymentIndividualSender;
import com.checkout.payments.four.sender.SenderIdentificationType;
import com.checkout.payments.four.sender.SourceOfFunds;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PayoutsTestIT extends SandboxTestFixture {

    public PayoutsTestIT() {
        super(PlatformType.FOUR_OAUTH);
    }

    @Test
    void shouldMakeCardPayoutPayments() {

        final PayoutRequest request = PayoutRequest.builder()
                .source(PayoutRequestCurrencyAccountSource.builder()
                        .id("ca_qcc7x4nxxk6efeogm7yczdnsxu")
                        .build())
                .destination(PaymentRequestCardDestination.builder()
                        .number("5219565036325411")
                        .expiryMonth(12)
                        .expiryYear(2024)
                        .accountHolder(AccountHolder.builder()
                                .type(AccountHolderType.INDIVIDUAL)
                                .firstName("John")
                                .lastName("Smith")
                                .dateOfBirth("1939-05-05")
                                .countryOfBirth(CountryCode.FR)
                                .billingAddress(Address.builder()
                                        .addressLine1("Checkout")
                                        .addressLine2("Shepherdless Walk")
                                        .city("London")
                                        .zip("N17BQ")
                                        .country(CountryCode.GB)
                                        .build())
                                .phone(Phone.builder()
                                        .countryCode("44")
                                        .number("09876512412")
                                        .build())
                                .identification(SenderIdentification.builder()
                                        .type(SenderIdentificationType.PASSPORT)
                                        .number("E2341")
                                        .issuingCountry(CountryCode.FR)
                                        .dateOfExpiry("2024-05-05")
                                        .build())
                                .email("jonh.smith@checkout.com")
                                .build())
                        .build())
                .amount(10L)
                .currency(Currency.EUR)
                .sender(PaymentIndividualSender.builder()
                        .firstName("Hayley")
                        .lastName("Jones")
                        .address(Address.builder()
                                .addressLine1("Checkout")
                                .addressLine2("Shepherdless Walk")
                                .city("London")
                                .zip("N17BQ")
                                .country(CountryCode.GB)
                                .build())
                        .reference("1234567ABCDEFG")
                        .referenceType("other")
                        .dateOfBirth("1939-05-05")
                        .sourceOfFunds(SourceOfFunds.CREDIT)
                        .build())
                .reference("Pay-out to Card - Money Transfer")
                .billingDescriptor(PayoutBillingDescriptor.builder()
                        .reference("Pay-out to Card - Money Transfer")
                        .build())
                .instruction(PaymentInstruction.builder()
                        .purpose("pension")
                        .fundsTransferType("C07")
                        .mvv("0123456789")
                        .build())
                .processingChannelId("pc_q727c4x6vtwujbiys3bb7wjpaa")
                .build();

        final PayoutResponse payoutResponse = blocking(() -> fourApi.paymentsClient().requestPayout(request));

        assertNotNull(payoutResponse);
        assertNotNull(payoutResponse.getId());
        assertNotNull(payoutResponse.getStatus());
        assertNotNull(payoutResponse.getInstruction());
        assertNotNull(payoutResponse.getInstruction().getValueDate());
    }
}