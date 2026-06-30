package com.checkout.payments.contexts;

import com.checkout.CheckoutApiException;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static com.checkout.payments.AbstractPaymentsTestIT.APM_SERVICE_UNAVAILABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.abort;

class PaymentContextsTestIT extends SandboxTestFixture {

    PaymentContextsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldMakeAPaymentContextPayPalRequest() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsPayPalRequest();
        final PaymentContextsRequestResponse response = tolerateApmUnavailable(
                () -> checkoutApi.paymentContextsClient().requestPaymentContexts(request, null));

        validatePayPalResponse(response);
    }

    @Test
    void shouldMakeAPaymentContextKlarnaRequest() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsKlarnaRequest();
        final PaymentContextsRequestResponse response = blocking(() -> checkoutApi.paymentContextsClient().requestPaymentContexts(request, null));
        
        validateKlarnaResponse(response);
    }

    @Disabled("Unavailable")
    @Test
    void shouldMakeAPaymentContextStcpayRequest() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsStcpayRequest();
        final PaymentContextsRequestResponse response = blocking(() -> checkoutApi.paymentContextsClient().requestPaymentContexts(request, null));
        
        validateStcpayResponse(response);
    }

    @Test
    void shouldMakeAPaymentContextTabbyRequest() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsTabbyRequest();
        checkErrorItem(() -> checkoutApi.paymentContextsClient().requestPaymentContexts(request, null), APM_SERVICE_UNAVAILABLE);
    }

    @Test
    void shouldGetAPaymentContext() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsPayPalRequest();
        final PaymentContextsRequestResponse paymentContextsResponse = tolerateApmUnavailable(
                () -> checkoutApi.paymentContextsClient().requestPaymentContexts(request, null));
        final PaymentContextDetailsResponse response = blocking(() -> checkoutApi.paymentContextsClient().getPaymentContextDetails(paymentContextsResponse.getId()));

        validatePaymentContextDetails(response);
    }

    // Synchronous test methods
    @Test
    void shouldMakeAPaymentContextPayPalRequestSync() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsPayPalRequest();
        final PaymentContextsRequestResponse response = tolerateApmUnavailableSync(
                () -> checkoutApi.paymentContextsClient().requestPaymentContextsSync(request, null));

        validatePayPalResponse(response);
    }

    @Test
    void shouldMakeAPaymentContextKlarnaRequestSync() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsKlarnaRequest();
        final PaymentContextsRequestResponse response = checkoutApi.paymentContextsClient().requestPaymentContextsSync(request, null);
        
        validateKlarnaResponse(response);
    }

    @Disabled("Unavailable")
    @Test
    void shouldMakeAPaymentContextStcpayRequestSync() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsStcpayRequest();
        final PaymentContextsRequestResponse response = checkoutApi.paymentContextsClient().requestPaymentContextsSync(request, null);
        
        validateStcpayResponse(response);
    }

    @Test
    void shouldMakeAPaymentContextTabbyRequestSync() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsTabbyRequest();
        checkErrorItemSync(() -> checkoutApi.paymentContextsClient().requestPaymentContextsSync(request, null), APM_SERVICE_UNAVAILABLE);
    }

    @Test
    void shouldGetAPaymentContextSync() {
        final PaymentContextsRequest request = TestHelper.createPaymentContextsPayPalRequest();
        final PaymentContextsRequestResponse paymentContextsResponse = tolerateApmUnavailableSync(
                () -> checkoutApi.paymentContextsClient().requestPaymentContextsSync(request, null));
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

    // PayPal is an APM that can be temporarily unavailable in sandbox, returning
    // a 422 'apm_service_unavailable'. When that happens we skip the test instead of
    // failing, since it reflects an environment outage rather than an SDK defect.
    private <T> T tolerateApmUnavailable(final Supplier<CompletableFuture<T>> supplier) {
        try {
            return supplier.get().get();
        } catch (final InterruptedException | ExecutionException exception) {
            if (isApmServiceUnavailable(exception.getCause())) {
                abort("PayPal APM unavailable in sandbox (apm_service_unavailable) - skipping");
            }
            throw new AssertionFailedError("Unexpected error", exception);
        }
    }

    private <T> T tolerateApmUnavailableSync(final Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch (final CheckoutApiException exception) {
            if (isApmServiceUnavailable(exception)) {
                abort("PayPal APM unavailable in sandbox (apm_service_unavailable) - skipping");
            }
            throw exception;
        }
    }

    @SuppressWarnings("unchecked")
    private static boolean isApmServiceUnavailable(final Throwable throwable) {
        if (!(throwable instanceof CheckoutApiException)) {
            return false;
        }
        final CheckoutApiException apiException = (CheckoutApiException) throwable;
        if (apiException.getErrorDetails() == null) {
            return false;
        }
        final Object errorCodes = apiException.getErrorDetails().get("error_codes");
        return errorCodes instanceof List && ((List<String>) errorCodes).contains(APM_SERVICE_UNAVAILABLE);
    }
}