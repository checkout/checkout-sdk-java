package com.checkout.payments;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.Currency;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlternativePaymentSourcePaymentsTestIT extends SandboxTestFixture {

    public AlternativePaymentSourcePaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    public void can_request_ideal_payment() throws Exception {
        final AlternativePaymentSource alternativePaymentSource = new AlternativePaymentSource("ideal");
        alternativePaymentSource.put("bic", "INGBNL2A");
        alternativePaymentSource.put("description", "ORD 5023 4E89");

        requestAlternativePayment(alternativePaymentSource);
    }

    private void requestAlternativePayment(final AlternativePaymentSource alternativePaymentSource) throws Exception {
        final PaymentRequest<RequestSource> paymentRequest = TestHelper.createAlternativePaymentMethodRequest(alternativePaymentSource, Currency.EUR);

        final PaymentResponse apiResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();
        assertTrue(apiResponse.isPending());
        assertNotNull(apiResponse.getPending());

        final PaymentPending pendingPayment = apiResponse.getPending();
        assertFalse(StringUtils.isEmpty(pendingPayment.getId()));
        assertEquals(PaymentStatus.PENDING, pendingPayment.getStatus());
        assertEquals(paymentRequest.getReference(), pendingPayment.getReference());
        assertNotNull(pendingPayment.getCustomer());
        assertFalse(StringUtils.isBlank(pendingPayment.getCustomer().getId()));
        assertFalse(StringUtils.isEmpty(pendingPayment.getCustomer().getEmail()));
        assertTrue(pendingPayment.requiresRedirect());
        assertNotNull(pendingPayment.getRedirectLink());

    }
}
