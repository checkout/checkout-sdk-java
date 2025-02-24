package com.checkout.payments.sessions;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import com.checkout.payments.BillingInformation;
import org.junit.jupiter.api.Test;

import static com.checkout.TestHelper.createAddress;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentSessionsTestIT extends SandboxTestFixture {

    PaymentSessionsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldMakeAPaymentSessionsRequest() {

        final BillingInformation billing = BillingInformation.builder()
                .address(createAddress())
                .build();

        final PaymentSessionsRequest request = PaymentSessionsRequest.builder()
                .amount(1000L)
                .currency(Currency.GBP)
                .reference("ORD-123A")
                .billing(billing)
                .successUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/failure")
                .build();

        final PaymentSessionsResponse response = blocking(() -> checkoutApi.paymentSessionsClient().requestPaymentSessions(request));

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getPaymentSessionToken());
        assertNotNull(response.getLinks());

    }

}
