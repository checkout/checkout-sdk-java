package com.checkout.payments.links;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaymentLinksTestIT extends SandboxTestFixture {

    static final String REFERENCE = "ORD-123A";

    PaymentLinksTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldCreatePaymentsLink() throws ExecutionException, InterruptedException {
        final PaymentLinkRequest paymentLinksRequest = TestHelper.createPaymentLinksRequest(REFERENCE);
        final PaymentLinkResponse response = defaultApi.paymentLinksClient().createAsync(paymentLinksRequest).get();
        assertNotNull(response);
        assertEquals(REFERENCE, response.getReference());
        assertNotNull(response.getExpiresOn());
        assertNotNull(response.getLinks());
        assertTrue(response.getLinks().containsKey("redirect"));
    }

    @Test
    void shouldRetrievePaymentsLink() throws ExecutionException, InterruptedException {
        final PaymentLinkRequest paymentLinksRequest = TestHelper.createPaymentLinksRequest(REFERENCE);
        final PaymentLinkResponse paymentLinkResponse = defaultApi.paymentLinksClient().createAsync(paymentLinksRequest).get();
        assertNotNull(paymentLinkResponse);
        final PaymentLinkDetailsResponse detailsResponse = defaultApi.paymentLinksClient().getAsync(paymentLinkResponse.getId()).get();
        assertNotNull(detailsResponse);
        assertEquals(paymentLinkResponse.getId(), detailsResponse.getId());
        assertThat(detailsResponse.getStatus(), anyOf(equalTo(PaymentLinkStatus.ACTIVE), equalTo(PaymentLinkStatus.PAYMENT_RECEIVED), equalTo(PaymentLinkStatus.EXPIRED)));
        assertNotNull(detailsResponse.getExpiresOn());
        assertEquals(paymentLinksRequest.getReturnUrl(), detailsResponse.getReturnUrl());
        assertEquals(paymentLinksRequest.getAmount(), detailsResponse.getAmount());
        assertEquals(paymentLinksRequest.getCurrency(), detailsResponse.getCurrency());
        assertEquals(paymentLinksRequest.getReference(), detailsResponse.getReference());
        assertEquals(paymentLinksRequest.getDescription(), detailsResponse.getDescription());
        assertEquals(paymentLinksRequest.getCustomer(), detailsResponse.getCustomer());
        assertEquals(paymentLinksRequest.getBilling(), detailsResponse.getBilling());
        assertEquals(paymentLinksRequest.getProducts(), detailsResponse.getProducts());
    }
}