package com.checkout.apm;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.payments.AlternativePaymentSourceResponse;
import com.checkout.payments.GetPaymentResponse;
import com.checkout.payments.PaymentPending;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.PaymentResponse;
import com.checkout.payments.apm.FawrySource;
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
    void shouldApproveFawryPayment() {

        final String paymentId = makeFawryPayment();

        final String paymentReference = validatePaymentPending(paymentId);

        // Approve payment
        blocking(getApi().fawryClient().approve(paymentReference));

        nap();

        final GetPaymentResponse paymentApproved = blocking(getApi().paymentsClient().getAsync(paymentId));
        assertNotNull(paymentApproved);
        assertEquals("Captured", paymentApproved.getStatus());
        assertNotNull(paymentApproved.getSource());
        assertTrue(paymentApproved.getSource() instanceof AlternativePaymentSourceResponse);

        final AlternativePaymentSourceResponse approvedSource = (AlternativePaymentSourceResponse) paymentApproved.getSource();
        assertEquals("fawry", approvedSource.getType());
        assertNotNull(approvedSource.get("reference_number"));
        assertEquals("Fawry Demo Payment", approvedSource.get("description"));

    }

    @Test
    void shouldCancelFawryPayment() {

        final String paymentId = makeFawryPayment();

        final String paymentReference = validatePaymentPending(paymentId);

        // Cancel payment
        blocking(getApi().fawryClient().cancel(paymentReference));

        nap();

        final GetPaymentResponse paymentCanceled = blocking(getApi().paymentsClient().getAsync(paymentId));
        assertNotNull(paymentCanceled);
        assertEquals("Canceled", paymentCanceled.getStatus());
        assertNotNull(paymentCanceled.getSource());
        assertTrue(paymentCanceled.getSource() instanceof AlternativePaymentSourceResponse);

        final AlternativePaymentSourceResponse approvedSource = (AlternativePaymentSourceResponse) paymentCanceled.getSource();
        assertEquals("fawry", approvedSource.getType());
        assertNotNull(approvedSource.get("reference_number"));
        assertEquals("Fawry Demo Payment", approvedSource.get("description"));

    }

    private String makeFawryPayment() {

        final FawrySource fawrySource = FawrySource.builder()
                .description("Fawry Demo Payment")
                .customerEmail("bruce@wayne-enterprises.com")
                .customerMobile("01058375055")
                .products(Collections.singletonList(
                        FawrySource.Product.builder()
                                .id("0123456789")
                                .description("Fawry Demo Product")
                                .price(1000L)
                                .quantity(1L)
                                .build()
                ))
                .build();

        final PaymentRequest<FawrySource> request = PaymentRequest.fawry(fawrySource, com.checkout.common.beta.Currency.EGP, 1000L);

        final PaymentResponse response = blocking(getApi().paymentsClient().requestAsync(request));

        assertNotNull(response);

        final PaymentPending paymentPending = response.getPending();
        assertNotNull(paymentPending);
        assertEquals("Pending", paymentPending.getStatus());

        assertTrue(paymentPending.hasLink("self"));
        assertTrue(paymentPending.hasLink("approve"));
        assertTrue(paymentPending.hasLink("cancel"));

        return paymentPending.getId();

    }

    private String validatePaymentPending(final String paymentId) {

        final GetPaymentResponse pendingPayment = blocking(getApi().paymentsClient().getAsync(paymentId));
        assertNotNull(pendingPayment);
        assertEquals("Pending", pendingPayment.getStatus());
        assertNotNull(pendingPayment.getSource());
        assertTrue(pendingPayment.getSource() instanceof AlternativePaymentSourceResponse);

        final AlternativePaymentSourceResponse source = (AlternativePaymentSourceResponse) pendingPayment.getSource();
        assertEquals("fawry", source.getType());
        assertNotNull(source.get("reference_number"));
        assertEquals("Fawry Demo Payment", source.get("description"));

        return (String) source.get("reference_number");

    }

}
