package com.checkout.apm.ideal;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.apm.RequestIdealSource;
import com.checkout.payments.response.PaymentResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IdealPaymentsTestIT extends SandboxTestFixture {

    IdealPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldMakeIdealPayment() {

        final RequestIdealSource idealSource = RequestIdealSource.builder()
                .bic("INGBNL2A")
                .description("ORD50234E89")
                .language("nl")
                .build();

        final PaymentRequest request = PaymentRequest.builder()
                .source(idealSource)
                .currency(Currency.EUR)
                .amount(1000L)
                .build();

        final PaymentResponse response = blocking(() -> defaultApi.paymentsClient().requestPayment(request));
        assertNotNull(response);
        //assertEquals(PaymentStatus.PENDING, response.getStatus());
        assertNotNull(response.getLink("self"));
        //assertNotNull(response.getLink("redirect"));

    }

    @Test
    @Disabled("unavailable")
    void shouldGetInfo() {

        final IdealInfo idealInfo = blocking(() -> defaultApi.idealClient().getInfo());

        assertNotNull(idealInfo);
        assertNotNull(idealInfo.getLinks().getSelf());
        assertNotNull(idealInfo.getLinks().getCuries());
        assertEquals(1, idealInfo.getLinks().getCuries().size());
        assertNotNull(idealInfo.getLinks().getIssuers());

    }

    @Test
    void shouldGetIssuers() {

        final IssuerResponse issuerResponse = blocking(() -> defaultApi.idealClient().getIssuers());

        assertNotNull(issuerResponse);
        assertNotNull(issuerResponse.getSelfLink());
        assertNotNull(issuerResponse.getCountries());

    }

}
