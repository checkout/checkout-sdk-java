package com.checkout.apm.fawry;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Currency;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.apm.RequestFawrySource;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.source.AlternativePaymentSourceResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FawryPaymentsTestIT extends SandboxTestFixture {

    FawryPaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    @Disabled
    void shouldApproveFawryPayment() {

        final String paymentId = makeFawryPayment();

        final String paymentReference = validatePaymentPending(paymentId);

        // Approve payment
        blocking(defaultApi.fawryClient().approve(paymentReference));

        nap();

        final GetPaymentResponse paymentApproved = blocking(defaultApi.paymentsClient().getPayment(paymentId));
        assertNotNull(paymentApproved);
        assertEquals(PaymentStatus.CAPTURED, paymentApproved.getStatus());
        assertNotNull(paymentApproved.getSource());
        assertTrue(paymentApproved.getSource() instanceof AlternativePaymentSourceResponse);

        final AlternativePaymentSourceResponse approvedSource = (AlternativePaymentSourceResponse) paymentApproved.getSource();
        assertEquals(PaymentSourceType.FAWRY, approvedSource.getType());
        assertNotNull(approvedSource.get("reference_number"));
        assertEquals("Fawry Demo Payment", approvedSource.get("description"));

    }

    @Test
    @Disabled
    void shouldCancelFawryPayment() {

        final String paymentId = makeFawryPayment();

        final String paymentReference = validatePaymentPending(paymentId);

        // Cancel payment
        blocking(defaultApi.fawryClient().cancel(paymentReference));

        nap();

        final GetPaymentResponse paymentCanceled = blocking(defaultApi.paymentsClient().getPayment(paymentId));
        assertNotNull(paymentCanceled);
        assertEquals(PaymentStatus.CANCELED, paymentCanceled.getStatus());
        assertNotNull(paymentCanceled.getSource());
        assertTrue(paymentCanceled.getSource() instanceof AlternativePaymentSourceResponse);

        final AlternativePaymentSourceResponse approvedSource = (AlternativePaymentSourceResponse) paymentCanceled.getSource();
        assertEquals(PaymentSourceType.FAWRY, approvedSource.getType());
        assertNotNull(approvedSource.get("reference_number"));
        assertEquals("Fawry Demo Payment", approvedSource.get("description"));

    }

    private String makeFawryPayment() {

        final RequestFawrySource fawrySource = RequestFawrySource.builder()
                .description("Fawry Demo Payment")
                .customerEmail("bruce@wayne-enterprises.com")
                .customerMobile("01058375055")
                .products(Collections.singletonList(
                        RequestFawrySource.Product.builder()
                                .id("0123456789")
                                .description("Fawry Demo Product")
                                .price(1000L)
                                .quantity(1L)
                                .build()
                ))
                .build();

        final PaymentRequest request = PaymentRequest.fawry(fawrySource, Currency.EGP, 1000L);

        final PaymentResponse response = blocking(defaultApi.paymentsClient().requestPayment(request));
        assertNotNull(response);
        assertEquals(PaymentStatus.PENDING, response.getStatus());
        assertNotNull(response.getLink("self"));
        assertNotNull(response.getLink("approve"));
        assertNotNull(response.getLink("cancel"));
        return response.getId();

    }

    private String validatePaymentPending(final String paymentId) {

        final GetPaymentResponse pendingPayment = blocking(defaultApi.paymentsClient().getPayment(paymentId));
        assertNotNull(pendingPayment);
        assertEquals(PaymentStatus.PENDING, pendingPayment.getStatus());
        assertNotNull(pendingPayment.getSource());
        assertTrue(pendingPayment.getSource() instanceof AlternativePaymentSourceResponse);

        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) pendingPayment.getSource();
        assertEquals(PaymentSourceType.FAWRY, source.getType());
        assertNotNull(source.get("reference_number"));
        assertEquals("Fawry Demo Payment", source.get("description"));

        return (String) source.get("reference_number");

    }

}
