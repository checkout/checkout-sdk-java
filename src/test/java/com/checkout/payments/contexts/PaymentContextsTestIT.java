package com.checkout.payments.contexts;

import com.checkout.CheckoutApiException;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.checkout.payments.AbstractPaymentsTestIT.APM_SERVICE_UNAVAILABLE;
import static com.checkout.payments.AbstractPaymentsTestIT.PAYEE_NOT_ONBOARDED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PaymentContextsTestIT extends SandboxTestFixture {

    PaymentContextsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldMakeAPaymentContextPayPalRequest() {

        final PaymentContextsRequest request = TestHelper.createPaymentContextsPayPalRequest();

        final PaymentContextsRequestResponse response = blocking(() -> checkoutApi.paymentContextsClient().requestPaymentContexts(request));

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getPartnerMetadata().getOrderId());
    }

    @Test
    void shouldMakeAPaymentContextKlarnaRequest() {

        final PaymentContextsRequest request = TestHelper.createPaymentContextsKlarnaRequest();

        final PaymentContextsRequestResponse response = blocking(() -> checkoutApi.paymentContextsClient().requestPaymentContexts(request));

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getPartnerMetadata().getSessionId());
        assertNotNull(response.getPartnerMetadata().getClientToken());
    }

    @Disabled("Unavailable")
    @Test
    void shouldMakeAPaymentContextStcpayRequest() {

        final PaymentContextsRequest request = TestHelper.createPaymentContextsStcpayRequest();

        final PaymentContextsRequestResponse response = blocking(() -> checkoutApi.paymentContextsClient().requestPaymentContexts(request));

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getPartnerMetadata().getSessionId());
        assertNotNull(response.getPartnerMetadata().getClientToken());
    }

    @Test
    void shouldMakeAPaymentContextTabbyRequest() {

        final PaymentContextsRequest request = TestHelper.createPaymentContextsTabbyRequest();

        checkErrorItem(() -> checkoutApi.paymentContextsClient().requestPaymentContexts(request), APM_SERVICE_UNAVAILABLE);
    }

    @Test
    void shouldGetAPaymentContext() {

        final PaymentContextsRequest request = TestHelper.createPaymentContextsPayPalRequest();

        final PaymentContextsRequestResponse paymentContextsResponse = blocking(() -> checkoutApi.paymentContextsClient().requestPaymentContexts(request));

        final PaymentContextDetailsResponse response = blocking(() -> checkoutApi.paymentContextsClient().getPaymentContextDetails(paymentContextsResponse.getId()));

        assertNotNull(response);
        assertNotNull(response.getPaymentRequest());
        assertEquals(1000, response.getPaymentRequest().getAmount());
        assertEquals(Currency.EUR, response.getPaymentRequest().getCurrency());
        assertEquals(PaymentType.REGULAR, response.getPaymentRequest().getPaymentType());
        assertEquals("mask", response.getPaymentRequest().getItems().get(0).getName());
        assertEquals(1, response.getPaymentRequest().getItems().get(0).getQuantity());
        assertEquals(1000, response.getPaymentRequest().getItems().get(0).getUnitPrice());
        assertEquals("https://example.com/payments/success", response.getPaymentRequest().getSuccessUrl());
        assertEquals("https://example.com/payments/fail", response.getPaymentRequest().getFailureUrl());
        assertNotNull(response.getPartnerMetadata());
        assertNotNull(response.getPartnerMetadata().getOrderId());
    }
}