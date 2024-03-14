package com.checkout.payments.sessions;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import org.junit.jupiter.api.Test;

import static com.checkout.TestHelper.createAddress;
import static com.checkout.TestHelper.createCustomer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentSessionsTestIT extends SandboxTestFixture {

    PaymentSessionsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldMakeAPaymentSessionsRequest() {

        final Billing billing = Billing.builder()
                .address(createAddress())
                .build();

        final PaymentSessionsRequest request = PaymentSessionsRequest.builder()
                .amount(1000L)
                .currency(Currency.GBP)
                .reference("ORD-123A")
                .billing(billing)
                .customer(createCustomer())
                .successUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/failure")
                .build();

        final PaymentSessionsResponse response = blocking(() -> checkoutApi.paymentSessionsClient().requestPaymentSessions(request));

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getLinks());

    }

}
