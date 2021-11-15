package com.checkout.apm.ideal.four;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.apm.ideal.IdealInfo;
import com.checkout.apm.ideal.IssuerResponse;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.request.Payments;
import com.checkout.payments.four.request.source.apm.RequestIdealSource;
import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.payments.four.sender.PaymentIndividualSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IdealPaymentsTestIT extends SandboxTestFixture {

    IdealPaymentsTestIT() {
        super(PlatformType.FOUR);
    }

    @Test
    void shouldMakeIdealPayment() {

        final PaymentIndividualSender sender = PaymentIndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .address(Address.builder()
                        .addressLine1("Address Line 1")
                        .addressLine2("Address Line 2")
                        .city("City")
                        .country(CountryCode.GB)
                        .build())
                .build();

        final RequestIdealSource idealSource = RequestIdealSource.builder()
                .bic("INGBNL2A")
                .description("ORD50234E89")
                .language("nl")
                .build();

        final PaymentRequest request = Payments.ideal(idealSource, Currency.EUR, 1000L)
                .individualSender(sender)
                .successUrl("https://testing.checkout.com/sucess")
                .failureUrl("https://testing.checkout.com/failure")
                .build();

        final PaymentResponse response = blocking(fourApi.paymentsClient().requestPayment(request));

        assertNotNull(response);

    }

    @Test
    void shouldGetInfo() {

        final IdealInfo idealInfo = blocking(fourApi.idealClient().getInfo());

        assertNotNull(idealInfo);
        assertNotNull(idealInfo.getLinks().getSelf());
        assertNotNull(idealInfo.getLinks().getCuries());
        assertEquals(1, idealInfo.getLinks().getCuries().size());
        assertNotNull(idealInfo.getLinks().getIssuers());

    }

    @Test
    void shouldGetIssuers() {

        final IssuerResponse issuerResponse = blocking(fourApi.idealClient().getIssuers());

        assertNotNull(issuerResponse);
        assertNotNull(issuerResponse.getSelfLink());
        assertNotNull(issuerResponse.getCountries());

    }

}
