package com.checkout.payments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.checkout.PlatformType;
import com.checkout.payments.request.PaymentSearchRequest;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.PaymentSearchResponse;

public class PaymentSearchTestIT extends AbstractPaymentsTestIT {

    public PaymentSearchTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Disabled("Avoid because can create timeout in the pipeline, activate when needed")
    @Test
    void shouldSearchPayments() throws InterruptedException {
        final PaymentResponse payment = makeCardPayment(true);
        final PaymentSearchRequest request = createPaymentSearchRequest(payment.getId());
        
        final PaymentSearchResponse response = retryUntilSearchHasResults(() -> 
            blocking(() -> checkoutApi.paymentsClient().searchPayments(request)));

        validatePaymentSearchResponse(response, payment);
    }

    // Synchronous methods
    @Disabled("Avoid because can create timeout in the pipeline, activate when needed")
    @Test
    void shouldSearchPaymentsSync() throws InterruptedException {
        final PaymentResponse payment = makeCardPayment(true);
        final PaymentSearchRequest request = createPaymentSearchRequest(payment.getId());

        final PaymentSearchResponse response = retryUntilSearchHasResults(() -> 
            checkoutApi.paymentsClient().searchPaymentsSync(request));

        validatePaymentSearchResponse(response, payment);
    }

    // Common methods
    private PaymentSearchRequest createPaymentSearchRequest(String paymentId) {
        final Instant now = Instant.now();
        return PaymentSearchRequest
                .builder()
                .query("id:'" + paymentId + "'")
                .limit(10)
                .from(now.minus(5, ChronoUnit.MINUTES))
                .to(now.plus(5, ChronoUnit.MINUTES))
                .build();
    }

    private PaymentSearchResponse retryUntilSearchHasResults(java.util.function.Supplier<PaymentSearchResponse> searchCall) 
            throws InterruptedException {
        PaymentSearchResponse response = null;
        int attempts = 0;
        final int maxAttempts = 10;
        final long delayMs = 2000;

        while (attempts < maxAttempts) {
            Thread.sleep(delayMs);
            response = searchCall.get();
            if (searchHasResults(response)) {
                return response;
            }
            attempts++;            
        }
        
        return response; // return last response even if no results
    }

    private static boolean searchHasResults(PaymentSearchResponse response) {
        return response != null && response.getData() != null && !response.getData().isEmpty();
    }

    private void validatePaymentSearchResponse(PaymentSearchResponse response, PaymentResponse expectedPayment) {
        assertNotNull(response);
        assertNotNull(response.getData());
        assertTrue(response.getData().size() > 0);
        assertEquals(expectedPayment.getId(), response.getData().get(0).getId());
    }

}