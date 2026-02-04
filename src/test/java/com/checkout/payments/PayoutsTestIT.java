package com.checkout.payments;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.AccountHolder;
import com.checkout.common.AccountHolderIdentification;
import com.checkout.common.AccountHolderIdentificationType;
import com.checkout.common.AccountHolderType;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.payments.request.PaymentInstruction;
import com.checkout.payments.request.PayoutBillingDescriptor;
import com.checkout.payments.request.PayoutRequest;
import com.checkout.payments.request.destination.PaymentRequestCardDestination;
import com.checkout.payments.request.source.PayoutRequestCurrencyAccountSource;
import com.checkout.payments.response.PayoutResponse;
import com.checkout.payments.sender.PaymentIndividualSender;
import com.checkout.payments.sender.SourceOfFunds;

class PayoutsTestIT extends SandboxTestFixture {

    public PayoutsTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Test
    void shouldMakeCardPayoutPayments() {
        final PayoutRequest request = createPayoutRequest();
        final PayoutResponse payoutResponse = blocking(() -> checkoutApi.paymentsClient().requestPayout(request));
        validatePayoutResponse(payoutResponse);
    }

    // Synchronous methods
    @Test
    void shouldMakeCardPayoutPaymentsSync() {
        final PayoutRequest request = createPayoutRequest();
        final PayoutResponse payoutResponse = checkoutApi.paymentsClient().requestPayoutSync(request);
        validatePayoutResponse(payoutResponse);
    }

    // Common methods
    private PayoutRequest createPayoutRequest() {
        return PayoutRequest.builder()
                .source(createPayoutSource())
                .destination(createCardDestination())
                .amount(10L)
                .currency(Currency.EUR)
                .sender(createPayoutSender())
                .reference("Pay-out to Card - Money Transfer")
                .billingDescriptor(createPayoutBillingDescriptor())
                .instruction(createPaymentInstruction())
                .processingChannelId("pc_q727c4x6vtwujbiys3bb7wjpaa")
                .build();
    }

    private PayoutRequestCurrencyAccountSource createPayoutSource() {
        return PayoutRequestCurrencyAccountSource.builder()
                .id("ca_qcc7x4nxxk6efeogm7yczdnsxu")
                .build();
    }

    private PaymentRequestCardDestination createCardDestination() {
        return PaymentRequestCardDestination.builder()
                .number("5219565036325411")
                .expiryMonth(12)
                .expiryYear(2030)
                .accountHolder(createAccountHolder())
                .build();
    }

    private AccountHolder createAccountHolder() {
        return AccountHolder.builder()
                .type(AccountHolderType.INDIVIDUAL)
                .firstName("John")
                .lastName("Smith")
                .dateOfBirth("1939-05-05")
                .countryOfBirth(CountryCode.FR)
                .billingAddress(createBillingAddress())
                .phone(createPayoutPhone())
                .identification(createAccountHolderIdentification())
                .email("jonh.smith@checkout.com")
                .build();
    }

    private Address createBillingAddress() {
        return Address.builder()
                .addressLine1("Checkout")
                .addressLine2("Shepherdless Walk")
                .city("London")
                .zip("N17BQ")
                .country(CountryCode.GB)
                .build();
    }

    private Phone createPayoutPhone() {
        return Phone.builder()
                .countryCode("44")
                .number("09876512412")
                .build();
    }

    private AccountHolderIdentification createAccountHolderIdentification() {
        return AccountHolderIdentification.builder()
                .type(AccountHolderIdentificationType.PASSPORT)
                .number("E2341")
                .issuingCountry(CountryCode.FR)
                .dateOfExpiry("2030-05-05")
                .build();
    }

    private PaymentIndividualSender createPayoutSender() {
        return PaymentIndividualSender.builder()
                .firstName("Hayley")
                .lastName("Jones")
                .address(createBillingAddress())
                .reference("1234567ABCDEFG")
                .referenceType("other")
                .dateOfBirth("1939-05-05")
                .sourceOfFunds(SourceOfFunds.CREDIT)
                .build();
    }

    private PayoutBillingDescriptor createPayoutBillingDescriptor() {
        return PayoutBillingDescriptor.builder()
                .reference("Pay-out to Card - Money Transfer")
                .build();
    }

    private PaymentInstruction createPaymentInstruction() {
        return PaymentInstruction.builder()
                .purpose("pension")
                .fundsTransferType("C07")
                .mvv("0123456789")
                .build();
    }

    private void validatePayoutResponse(PayoutResponse payoutResponse) {
        assertNotNull(payoutResponse);
        assertNotNull(payoutResponse.getId());
        assertNotNull(payoutResponse.getStatus());
        assertNotNull(payoutResponse.getInstruction());
        assertNotNull(payoutResponse.getInstruction().getValueDate());
    }

}