package com.checkout.apm;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.apm.giropay.BanksResponse;
import com.checkout.payments.PaymentPending;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.PaymentResponse;
import com.checkout.payments.apm.GiropaySource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GiropayPaymentsTestIT extends SandboxTestFixture {

    public GiropayPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    public void shouldMakeGiropayPayment() {

        final GiropaySource giropaySource = GiropaySource.builder()
                .bic("TESTDETT421")
                .purpose("CKO Giropay test")
                .build();

        final PaymentRequest<GiropaySource> request = PaymentRequest.giropay(giropaySource, com.checkout.common.beta.Currency.EUR, 1000L);

        final PaymentResponse response = blocking(getApi().paymentsClient().requestAsync(request));

        assertNotNull(response);

        final PaymentPending paymentPending = response.getPending();
        assertNotNull(paymentPending);
        assertEquals("Pending", paymentPending.getStatus());

        assertTrue(paymentPending.hasLink("self"));
        assertTrue(paymentPending.hasLink("redirect"));

    }

    @Test
    public void shouldGetBanks() {

        final BanksResponse banksResponse = blocking(getApi().giropayClient().getBanks());

        assertNotNull(banksResponse);
        assertNotNull(banksResponse.getSelfLink());
        assertNotNull(banksResponse.getBanks());
        assertFalse(banksResponse.getBanks().isEmpty());

    }

    @Test
    public void shouldGetEpsBanks() {

        final BanksResponse banksResponse = blocking(getApi().giropayClient().getEpsBanks());

        assertNotNull(banksResponse);
        assertNotNull(banksResponse.getSelfLink());
        assertNotNull(banksResponse.getBanks());
        assertFalse(banksResponse.getBanks().isEmpty());

    }

}
