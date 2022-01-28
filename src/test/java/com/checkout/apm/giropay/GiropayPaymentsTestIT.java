package com.checkout.apm.giropay;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.apm.RequestGiropaySource;
import com.checkout.payments.response.PaymentResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GiropayPaymentsTestIT extends SandboxTestFixture {

    GiropayPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldMakeGiropayPayment() {

        final RequestGiropaySource giropaySource = RequestGiropaySource.builder()
                .purpose("CKO Giropay test")
                .build();

        final PaymentRequest request = PaymentRequest.giropay(giropaySource, Currency.EUR, 1000L);
        final PaymentResponse response = blocking(() -> defaultApi.paymentsClient().requestPayment(request));
        assertNotNull(response);
        assertEquals(PaymentStatus.PENDING, response.getStatus());
        assertNotNull(response.getLink("self"));
        assertNotNull(response.getLink("redirect"));

    }

}
