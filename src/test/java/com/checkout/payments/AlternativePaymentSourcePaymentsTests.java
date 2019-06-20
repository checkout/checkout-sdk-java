package com.checkout.payments;

import com.checkout.ApiTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.Currency;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

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

        AlternativePaymentSourceResponse verifiedSource = (AlternativePaymentSourceResponse) verifiedPayment.getSource();
        verifiedSource.keySet().forEach(key -> Assert.assertEquals(alternativePaymentSource.get(key), verifiedSource.get(key)));
    }

    @Ignore // Missing dependant Klarna workflow
    @Test
    public void test_klarna_payment() throws Exception {
        Map<String, Object> billingAddress = new HashMap<>();
        billingAddress.put("given_name", "John");
        billingAddress.put("family_name", "Doe");
        billingAddress.put("email", "johndoe@email.com");
        billingAddress.put("title", "Mr");
        billingAddress.put("street_address", "13 New Burlington St");
        billingAddress.put("street_address2", "Apt 214");
        billingAddress.put("postal_code", "W13 3BG");
        billingAddress.put("city", "London");

        Map<String, Object> product = new HashMap<>();
        product.put("name", "Battery Power Pack");
        product.put("quantity", 1);
        product.put("unit_price", 1000);
        product.put("tax_rate", 0);
        product.put("total_amount", 1000);
        product.put("total_tax_amount", 0);

        List<Map<String, Object>> products = new ArrayList<>();
        products.add(product);

        AlternativePaymentSource alternativePaymentSource = new AlternativePaymentSource("klarna");
        alternativePaymentSource.put("authorization_token", "b4bd3423-24e3");
        alternativePaymentSource.put("locale", "en-GB");
        alternativePaymentSource.put("purchase_country", "GB");
        alternativePaymentSource.put("tax_amount", 0);
        alternativePaymentSource.put("billing_address", billingAddress);
        alternativePaymentSource.put("products", products);

        PaymentRequest<AlternativePaymentSource> paymentRequest = new PaymentRequest<>(alternativePaymentSource, Currency.GBP, 1000);
        paymentRequest.setCapture(false);
        paymentRequest.setThreeDS(ThreeDSRequest.from(false));
        paymentRequest.setReference(UUID.randomUUID().toString());

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();
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