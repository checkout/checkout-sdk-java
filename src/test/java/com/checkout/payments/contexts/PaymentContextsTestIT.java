package com.checkout.payments.contexts;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentContextsTestIT extends SandboxTestFixture {

    PaymentContextsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldMakeAPaymentContextRequest() {

        final PaymentContextsRequest request = TestHelper.createPaymentContextsRequest();

        final PaymentContextsRequestResponse response = blocking(() -> checkoutApi.paymentContextsClient().requestPaymentContexts(request));

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getPartnerMetadata().getOrderId());
    }

    @Test
    void shouldGetAPaymentContext() {

        final PaymentContextsRequest request = TestHelper.createPaymentContextsRequest();

        final PaymentContextsRequestResponse paymentContextsResponse = blocking(() -> checkoutApi.paymentContextsClient().requestPaymentContexts(request));

        final PaymentContextDetailsResponse response = blocking(() -> checkoutApi.paymentContextsClient().getPaymentContextDetails(paymentContextsResponse.getId()));

        assertNotNull(response);
        assertNotNull(response.getPaymentRequest());
        assertEquals(2000, response.getPaymentRequest().getAmount());
        assertEquals(Currency.EUR, response.getPaymentRequest().getCurrency());
        assertEquals(PaymentType.REGULAR, response.getPaymentRequest().getPaymentType());
        assertEquals(true, response.getPaymentRequest().isCapture());
        assertEquals("mask", response.getPaymentRequest().getItems().get(0).getName());
        assertEquals(1, response.getPaymentRequest().getItems().get(0).getQuantity());
        assertEquals(2000, response.getPaymentRequest().getItems().get(0).getUnitPrice());
        assertEquals("https://example.com/payments/success", response.getPaymentRequest().getSuccessUrl());
        assertEquals("https://example.com/payments/fail", response.getPaymentRequest().getFailureUrl());
        assertNotNull(response.getPartnerMetadata());
        assertNotNull(response.getPartnerMetadata().getOrderId());
    }
}