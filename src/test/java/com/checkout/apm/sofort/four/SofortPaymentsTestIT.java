package com.checkout.apm.sofort.four;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.request.Payments;
import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.payments.four.sender.PaymentIndividualSender;
import com.checkout.payments.four.sender.SenderIdentification;
import com.checkout.payments.four.sender.SenderIdentificationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class SofortPaymentsTestIT extends SandboxTestFixture {

    SofortPaymentsTestIT() {
        super(PlatformType.FOUR);
    }

    @Test
    void shouldMakeSofortPayment() {

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

        final PaymentRequest request = Payments.sofort(Currency.EUR, 1000L)
                .individualSender(sender)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .sender(sender)
                .build();

        final PaymentResponse response = blocking(() -> fourApi.paymentsClient().requestPayment(request));

        assertNotNull(response);


    }

}
