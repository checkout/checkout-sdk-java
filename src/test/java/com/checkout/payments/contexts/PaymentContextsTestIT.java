package com.checkout.payments.contexts;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.checkout.payments.AbstractPaymentsTestIT.APM_SERVICE_UNAVAILABLE;
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
        
        validatePayPalResponse(response);
    }

    @Test
    void shouldMakeAPaymentContextKlarnaRequest() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsKlarnaRequest();
        final PaymentContextsRequestResponse response = blocking(() -> checkoutApi.paymentContextsClient().requestPaymentContexts(request));
        
        validateKlarnaResponse(response);
    }

    @Disabled("Unavailable")
    @Test
    void shouldMakeAPaymentContextStcpayRequest() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsStcpayRequest();
        final PaymentContextsRequestResponse response = blocking(() -> checkoutApi.paymentContextsClient().requestPaymentContexts(request));
        
        validateStcpayResponse(response);
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
        
        validatePaymentContextDetails(response);
    }

    // Synchronous test methods
    @Test
    void shouldMakeAPaymentContextPayPalRequestSync() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsPayPalRequest();
        final PaymentContextsRequestResponse response = checkoutApi.paymentContextsClient().requestPaymentContextsSync(request);
        
        validatePayPalResponse(response);
    }

    @Test
    void shouldMakeAPaymentContextKlarnaRequestSync() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsKlarnaRequest();
        final PaymentContextsRequestResponse response = checkoutApi.paymentContextsClient().requestPaymentContextsSync(request);
        
        validateKlarnaResponse(response);
    }

    @Disabled("Unavailable")
    @Test
    void shouldMakeAPaymentContextStcpayRequestSync() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsStcpayRequest();
        final PaymentContextsRequestResponse response = checkoutApi.paymentContextsClient().requestPaymentContextsSync(request);
        
        validateStcpayResponse(response);
    }

    @Test
    void shouldMakeAPaymentContextTabbyRequestSync() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsTabbyRequest();
        checkErrorItemSync(() -> checkoutApi.paymentContextsClient().requestPaymentContextsSync(request), APM_SERVICE_UNAVAILABLE);
    }

    @Test
    void shouldGetAPaymentContextSync() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsPayPalRequest();
        final PaymentContextsRequestResponse paymentContextsResponse = checkoutApi.paymentContextsClient().requestPaymentContextsSync(request);
        final PaymentContextDetailsResponse response = checkoutApi.paymentContextsClient().getPaymentContextDetailsSync(paymentContextsResponse.getId());
        
        validatePaymentContextDetails(response);
    }

    // Common validation methods
    private void validatePayPalResponse(PaymentContextsRequestResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getPartnerMetadata().getOrderId());
    }

    private void validateKlarnaResponse(PaymentContextsRequestResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getPartnerMetadata().getSessionId());
        assertNotNull(response.getPartnerMetadata().getClientToken());
    }

    private void validateStcpayResponse(PaymentContextsRequestResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getPartnerMetadata().getSessionId());
        assertNotNull(response.getPartnerMetadata().getClientToken());
    }

    private void validatePaymentContextDetails(PaymentContextDetailsResponse response) {
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