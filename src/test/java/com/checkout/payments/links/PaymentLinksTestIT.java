package com.checkout.payments.links;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.payments.PaymentStatus;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentLinksTestIT extends SandboxTestFixture {

    public static final String REFERENCE = "ORD-123A";

    public PaymentLinksTestIT() {
        super(PlatformType.CLASSIC);
    }

    @Test
    public void shouldCreatePaymentsLink() throws ExecutionException, InterruptedException {
        final PaymentLinkRequest paymentLinksRequest = TestHelper.createPaymentLinksRequest(REFERENCE);
        final PaymentLinkResponse response = getApi().paymentLinksClient().createAsync(paymentLinksRequest).get();
        assertNotNull(response);
        assertEquals(REFERENCE, response.getReference());
        assertNotNull(response.getExpiresOn());
        assertNotNull(response.getLinks());
        assertTrue(response.getLinks().containsKey("redirect"));
    }

    @Test
    public void shouldRetrievePaymentsLink() throws ExecutionException, InterruptedException {
        final PaymentLinkRequest paymentLinksRequest = TestHelper.createPaymentLinksRequest(REFERENCE);
        final PaymentLinkResponse paymentLinkResponse = getApi().paymentLinksClient().createAsync(paymentLinksRequest).get();
        assertNotNull(paymentLinkResponse);
        final PaymentLinkDetailsResponse detailsResponse = getApi().paymentLinksClient().getAsync(paymentLinkResponse.getId()).get();
        assertNotNull(detailsResponse);
        assertEquals(paymentLinkResponse.getId(), detailsResponse.getId());
        assertThat(detailsResponse.getStatus(), anyOf(equalTo(PaymentStatus.ACTIVE), equalTo(PaymentStatus.PAYMENT_RECEIVED), equalTo(PaymentStatus.EXPIRED)));
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