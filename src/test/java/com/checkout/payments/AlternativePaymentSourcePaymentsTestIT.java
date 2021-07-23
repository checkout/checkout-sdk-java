package com.checkout.payments;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.Currency;
import org.junit.Assert;
import org.junit.Test;

public class AlternativePaymentSourcePaymentsTestIT extends SandboxTestFixture {

    public AlternativePaymentSourcePaymentsTestIT() {
        super(PlatformType.CLASSIC);
    }

    @Test
    public void can_request_ideal_payment() throws Exception {
        AlternativePaymentSource alternativePaymentSource = new AlternativePaymentSource("ideal");
        alternativePaymentSource.put("bic", "INGBNL2A");
        alternativePaymentSource.put("description", "ORD 5023 4E89");

        requestAlternativePayment(alternativePaymentSource);
    }

    private PaymentPending requestAlternativePayment(AlternativePaymentSource alternativePaymentSource) throws Exception {
        PaymentRequest<RequestSource> paymentRequest = TestHelper.createAlternativePaymentMethodRequest(alternativePaymentSource, Currency.EUR);

        PaymentResponse apiResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();
        Assert.assertTrue(apiResponse.isPending());
        Assert.assertNotNull(apiResponse.getPending());

        PaymentPending pendingPayment = apiResponse.getPending();
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(pendingPayment.getId()));
        Assert.assertEquals(PaymentStatus.PENDING, pendingPayment.getStatus());
        Assert.assertEquals(paymentRequest.getReference(), pendingPayment.getReference());
        Assert.assertNotNull(pendingPayment.getCustomer());
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(pendingPayment.getCustomer().getId()));
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(pendingPayment.getCustomer().getEmail()));
        Assert.assertTrue(pendingPayment.requiresRedirect());
        Assert.assertNotNull(pendingPayment.getRedirectLink());

        return pendingPayment;
    }
}