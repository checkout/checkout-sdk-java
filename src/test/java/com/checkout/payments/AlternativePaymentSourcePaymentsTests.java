package com.checkout.payments;

import com.checkout.ApiTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.Currency;
import org.junit.Assert;
import org.junit.Test;

public class AlternativePaymentSourcePaymentsTests extends ApiTestFixture {

    @Test
    public void can_request_giropay_payment() throws Exception {
        AlternativePaymentSource alternativePaymentSource = new AlternativePaymentSource("giropay");
        alternativePaymentSource.put("bic", "TESTDETT421");
        alternativePaymentSource.put("purpose", "CKO giropay test");

        requestAlternativePayment(alternativePaymentSource);
    }

    @Test
    public void can_request_ideal_payment() throws Exception {
        AlternativePaymentSource alternativePaymentSource = new AlternativePaymentSource("ideal");
        alternativePaymentSource.put("bic", "INGBNL2A");
        alternativePaymentSource.put("description", "ORD 5023 4E89");

        requestAlternativePayment(alternativePaymentSource);
    }

    @Test
    public void can_get_alternative_payment() throws Exception {
        AlternativePaymentSource alternativePaymentSource = new AlternativePaymentSource("giropay");
        alternativePaymentSource.put("bic", "TESTDETT421");
        alternativePaymentSource.put("purpose", "CKO giropay test");

        PaymentPending payment = requestAlternativePayment(alternativePaymentSource);

        GetPaymentResponse verifiedPayment = getApi().paymentsClient().getAsync(payment.getId()).get();

        Assert.assertNotNull(verifiedPayment);
        Assert.assertEquals(payment.getId(), verifiedPayment.getId());
        Assert.assertNotNull(verifiedPayment);

        AlternativePaymentSourceResponse verifiedSource = (AlternativePaymentSourceResponse) verifiedPayment.getSource();
        verifiedSource.keySet().forEach(key -> Assert.assertEquals(alternativePaymentSource.get(key), verifiedSource.get(key)));
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