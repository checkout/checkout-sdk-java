package com.checkout.payments.hosted;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.Currency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HostedPaymentsTestIT extends SandboxTestFixture {

    static final String REFERENCE = "ORD-123A";

    HostedPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldCreateAndGetHostedPayments() {
        final HostedPaymentRequest request = TestHelper.createHostedPaymentRequest(REFERENCE);
        final HostedPaymentResponse response = blocking(() -> defaultApi.hostedPaymentsClient().createHostedPaymentsPageSession(request));
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(REFERENCE, response.getReference());
        assertNotNull(response.getLinks());
        assertTrue(response.getLinks().containsKey("redirect"));
        HostedPaymentRequest.builder().build();

        final HostedPaymentDetailsResponse detailsResponse = blocking(() -> defaultApi.hostedPaymentsClient().getHostedPaymentsPageDetails(response.getId()));

        assertNotNull(detailsResponse);
        assertNotNull(detailsResponse.getId());
        assertNotNull(detailsResponse.getReference());
        assertEquals(HostedPaymentStatus.PAYMENT_PENDING, detailsResponse.getStatus());
        assertNotNull(detailsResponse.getAmount());
        assertNotNull(detailsResponse.getBilling());
        assertEquals(Currency.GBP, detailsResponse.getCurrency());
        assertNotNull(detailsResponse.getCustomer());
        assertNotNull(detailsResponse.getDescription());
        assertNotNull(detailsResponse.getFailureUrl());
        assertNotNull(detailsResponse.getSuccessUrl());
        assertNotNull(detailsResponse.getCancelUrl());
    }
}