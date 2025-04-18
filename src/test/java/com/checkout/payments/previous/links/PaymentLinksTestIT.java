package com.checkout.payments.previous.links;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.payments.links.PaymentLinkDetailsResponse;
import com.checkout.payments.links.PaymentLinkRequest;
import com.checkout.payments.links.PaymentLinkResponse;
import com.checkout.payments.links.PaymentLinkStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("unavailable")
class PaymentLinksTestIT extends SandboxTestFixture {

    static final String REFERENCE = "ORD-123A";

    PaymentLinksTestIT() {
        super(PlatformType.PREVIOUS);
    }

    @Test
    void shouldCreatePaymentsLink() {
        final PaymentLinkRequest paymentLinksRequest = TestHelper.createPaymentLinksRequest(REFERENCE);
        final PaymentLinkResponse response = blocking(() -> previousApi.paymentLinksClient().createPaymentLink(paymentLinksRequest));
        assertNotNull(response);
        assertEquals(REFERENCE, response.getReference());
        assertNotNull(response.getExpiresOn());
        assertNotNull(response.getLinks());
        assertTrue(response.getLinks().containsKey("redirect"));
    }

    @Test
    void shouldRetrievePaymentsLink() {
        final PaymentLinkRequest paymentLinksRequest = TestHelper.createPaymentLinksRequest(REFERENCE);
        final PaymentLinkResponse paymentLinkResponse = blocking(() -> previousApi.paymentLinksClient().createPaymentLink(paymentLinksRequest));
        assertNotNull(paymentLinkResponse);
        final PaymentLinkDetailsResponse detailsResponse = blocking(() -> previousApi.paymentLinksClient().getPaymentLink(paymentLinkResponse.getId()));
        assertNotNull(detailsResponse);
        assertEquals(paymentLinkResponse.getId(), detailsResponse.getId());
        assertThat(detailsResponse.getStatus(), anyOf(equalTo(PaymentLinkStatus.ACTIVE), equalTo(PaymentLinkStatus.PAYMENT_RECEIVED), equalTo(PaymentLinkStatus.EXPIRED)));
        assertNotNull(detailsResponse.getExpiresOn());
        assertEquals(paymentLinksRequest.getReturnUrl(), detailsResponse.getReturnUrl());
        assertEquals(paymentLinksRequest.getAmount(), detailsResponse.getAmount());
        assertEquals(paymentLinksRequest.getCurrency(), detailsResponse.getCurrency());
        assertEquals(paymentLinksRequest.getReference(), detailsResponse.getReference());
        assertEquals(paymentLinksRequest.getDescription(), detailsResponse.getDescription());
        assertEquals(paymentLinksRequest.getBilling(), detailsResponse.getBilling());
        assertEquals(paymentLinksRequest.getProducts(), detailsResponse.getProducts());
        assertNotNull(detailsResponse.getCustomer());
    }
}