package com.checkout.payments;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class RequestPayoutsTestIT extends SandboxTestFixture {

    RequestPayoutsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldRequestPayout() throws Exception {

        final PaymentCardDestination paymentCardDestination = PaymentCardDestination.builder()
                .billingAddress(Address.builder()
                        .addressLine1("CheckoutSdk.com")
                        .addressLine2("90 Tottenham Court Road")
                        .city("London")
                        .state("London")
                        .zip("W1T 4TJ")
                        .country(CountryCode.GB)
                        .build())
                .expiryMonth(TestCardSource.VISA.getExpiryMonth())
                .expiryYear(TestCardSource.VISA.getExpiryYear())
                .number(TestCardSource.VISA.getNumber())
                .firstName(TestCardSource.VISA.getName())
                .lastName("Integration")
                .name(TestCardSource.VISA.getName())
                .phone(Phone.builder()
                        .countryCode("44")
                        .number("020 222333")
                        .build())
                .build();

        final PaymentRequest<CardSource> payoutRequest = new PaymentRequest<>();
        payoutRequest.setDestination(paymentCardDestination);
        payoutRequest.setCurrency(Currency.GBP);
        payoutRequest.setAmount(10L);
        payoutRequest.setCapture(false);
        payoutRequest.setReference(UUID.randomUUID().toString());
        payoutRequest.setDestination(paymentCardDestination);
        payoutRequest.setPurpose(Purpose.EDUCATION);

        final PaymentResponse paymentResponse = defaultApi.paymentsClient().requestAsync(payoutRequest).get();

        assertNotNull(paymentResponse.getPending());

    }
}