package com.checkout.payments.four.hosted;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.Currency;
import com.checkout.payments.hosted.HostedPaymentDetailsResponse;
import com.checkout.payments.hosted.HostedPaymentRequest;
import com.checkout.payments.hosted.HostedPaymentResponse;
import com.checkout.payments.hosted.HostedPaymentStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HostedPaymentsTestIT extends SandboxTestFixture {

    static final String REFERENCE = "ORD-123A";

    HostedPaymentsTestIT() {
        super(PlatformType.FOUR);
    }

    @Test
    void shouldCreateAndGetHostedPayments() {
        final HostedPaymentRequest request = TestHelper.createHostedPaymentRequest(REFERENCE);
        final HostedPaymentResponse response = blocking(() -> fourApi.hostedPaymentsClient().createHostedPaymentsPageSession(request));
        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(REFERENCE, response.getReference());
        assertNotNull(response.getLinks());
        assertTrue(response.getLinks().containsKey("redirect"));
        assertNotNull(response.getWarnings());

        final HostedPaymentDetailsResponse detailsResponse = blocking(() -> fourApi.hostedPaymentsClient().getHostedPaymentsPageDetails(response.getId()));

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