package com.checkout.payments.links;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.AmountAllocations;
import com.checkout.common.Commission;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

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
    void shouldCreateAndGetPaymentsLink() {
        final PaymentLinkRequest paymentLinksRequest = createPaymentLinksRequest();

        final PaymentLinkResponse paymentLinkResponse =
                blocking(() -> checkoutApi.paymentLinksClient().createPaymentLink(paymentLinksRequest));

        validateCreatePaymentLinkResponse(paymentLinkResponse);

        final PaymentLinkDetailsResponse detailsResponse =
                blocking(() -> checkoutApi.paymentLinksClient().getPaymentLink(paymentLinkResponse.getId()));

        validatePaymentLinkDetailsResponse(detailsResponse, paymentLinkResponse, paymentLinksRequest);
    }

    // Synchronous test methods
    @Test
    void shouldCreateAndGetPaymentsLinkSync() {
        final PaymentLinkRequest paymentLinksRequest = createPaymentLinksRequest();

        final PaymentLinkResponse paymentLinkResponse =
                checkoutApi.paymentLinksClient().createPaymentLinkSync(paymentLinksRequest);

        validateCreatePaymentLinkResponse(paymentLinkResponse);

        final PaymentLinkDetailsResponse detailsResponse =
                checkoutApi.paymentLinksClient().getPaymentLinkSync(paymentLinkResponse.getId());

        validatePaymentLinkDetailsResponse(detailsResponse, paymentLinkResponse, paymentLinksRequest);
    }

    // Common methods
    private PaymentLinkRequest createPaymentLinksRequest() {
        final PaymentLinkRequest paymentLinksRequest = TestHelper.createPaymentLinksRequest(REFERENCE);
        paymentLinksRequest.setAmountAllocations(Collections.singletonList(createAmountAllocation()));
        return paymentLinksRequest;
    }

    private AmountAllocations createAmountAllocation() {
        return AmountAllocations.builder()
                .id("ent_sdioy6bajpzxyl3utftdp7legq")
                .amount(100L)
                .reference(UUID.randomUUID().toString())
                .commission(Commission.builder()
                        .amount(1L)
                        .percentage(0.1)
                        .build())
                .build();
    }

    private void validateCreatePaymentLinkResponse(final PaymentLinkResponse paymentLinkResponse) {
        assertNotNull(paymentLinkResponse);
        assertEquals(REFERENCE, paymentLinkResponse.getReference());
        assertNotNull(paymentLinkResponse.getExpiresOn());
        assertNotNull(paymentLinkResponse.getLinks());
        assertTrue(paymentLinkResponse.getLinks().containsKey("redirect"));
        assertEquals(201, paymentLinkResponse.getHttpStatusCode());
    }

    private void validatePaymentLinkDetailsResponse(final PaymentLinkDetailsResponse detailsResponse,
                                                    final PaymentLinkResponse paymentLinkResponse,
                                                    final PaymentLinkRequest paymentLinksRequest) {
        assertNotNull(detailsResponse);
        assertEquals(paymentLinkResponse.getId(), detailsResponse.getId());
        assertThat(detailsResponse.getStatus(), anyOf(equalTo(PaymentLinkStatus.ACTIVE),
                equalTo(PaymentLinkStatus.PAYMENT_RECEIVED), equalTo(PaymentLinkStatus.EXPIRED)));
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