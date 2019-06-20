package com.checkout.payments;

import com.checkout.ApiTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.Currency;
import org.junit.Assert;
import org.junit.Test;

public class CustomerSourcePaymentsTests extends ApiTestFixture {

    @Test
    public void can_request_card_payment() throws Exception {
        PaymentRequest<CardSource> firstCardPayment = TestHelper.createCardPaymentRequest();
        PaymentResponse firstCardPaymentResponse = getApi().paymentsClient().requestAsync(firstCardPayment).get();
        CustomerSource customerSource = new CustomerSource(firstCardPayment.getCustomer().getId(), firstCardPayment.getCustomer().getEmail());
        PaymentRequest<CustomerSource> customerPaymentRequest = new PaymentRequest<>(customerSource, Currency.GBP, 100);
        customerPaymentRequest.setCapture(false);

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(customerPaymentRequest).get();

        Assert.assertNotNull(paymentResponse.getPayment());
        Assert.assertTrue(paymentResponse.getPayment().isApproved());
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(paymentResponse.getPayment().getId()));
        Assert.assertNotEquals(firstCardPaymentResponse.getPayment().getId(), paymentResponse.getPayment().getId());
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(paymentResponse.getPayment().getActionId()));
        Assert.assertEquals(customerPaymentRequest.getAmount().intValue(), paymentResponse.getPayment().getAmount());
        Assert.assertEquals(customerPaymentRequest.getCurrency(), paymentResponse.getPayment().getCurrency());
        Assert.assertEquals(customerPaymentRequest.getReference(), paymentResponse.getPayment().getReference());
        Assert.assertNotNull(paymentResponse.getPayment().getCustomer());
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(paymentResponse.getPayment().getCustomer().getId()));
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(paymentResponse.getPayment().getCustomer().getEmail()));
        Assert.assertEquals(firstCardPaymentResponse.getPayment().getCustomer().getId(), paymentResponse.getPayment().getCustomer().getId());
        Assert.assertNotNull(paymentResponse.getPayment().getSource());
        Assert.assertEquals(((CardSourceResponse) firstCardPaymentResponse.getPayment().getSource()).getId(), ((CardSourceResponse) paymentResponse.getPayment().getSource()).getId());
        Assert.assertTrue(paymentResponse.getPayment().canCapture());
        Assert.assertTrue(paymentResponse.getPayment().canVoid());
    }
}