package com.checkout.apm.ideal;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentPending;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.PaymentResponse;
import com.checkout.payments.apm.IdealSource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class IdealPaymentsTestIT extends SandboxTestFixture {

    IdealPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldMakeIdealPayment() {

        final IdealSource idealSource = IdealSource.builder()
                .bic("INGBNL2A")
                .description("ORD50234E89")
                .language("nl")
                .build();

        final PaymentRequest<IdealSource> request = PaymentRequest.ideal(idealSource, Currency.EUR, 1000L);

        final PaymentResponse response = blocking(defaultApi.paymentsClient().requestAsync(request));

        assertNotNull(response);

        final PaymentPending paymentPending = response.getPending();
        assertNotNull(paymentPending);
        assertEquals("Pending", paymentPending.getStatus());

        assertNotNull(paymentPending.getLink("self"));
        assertNotNull(paymentPending.getLink("redirect"));

    }

    @Test
    void shouldGetInfo() {

        final IdealInfo idealInfo = blocking(defaultApi.idealClient().getInfo());

        assertNotNull(idealInfo);
        assertNotNull(idealInfo.getLinks().getSelf());
        assertNotNull(idealInfo.getLinks().getCuries());
        assertEquals(1, idealInfo.getLinks().getCuries().size());
        assertNotNull(idealInfo.getLinks().getIssuers());

    }

    @Test
    void shouldGetIssuers() {

        final IssuerResponse issuerResponse = blocking(defaultApi.idealClient().getIssuers());

        assertNotNull(issuerResponse);
        assertNotNull(issuerResponse.getSelfLink());
        assertNotNull(issuerResponse.getCountries());

    }

}
