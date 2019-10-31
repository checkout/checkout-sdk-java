package com.checkout.payments;

import com.checkout.ApiTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.CheckoutUtils;
import com.checkout.common.Currency;
import org.junit.Assert;
import org.junit.Test;

public class IdSourcePaymentsTests extends ApiTestFixture {

    @Test
    public void can_request_card_payment() throws Exception {
        PaymentRequest<CardSource> firstCardPayment = TestHelper.createCardPaymentRequest();

        PaymentResponse firstCardPaymentResponse = getApi().paymentsClient().requestAsync(firstCardPayment).get();

        IdSource idSource = new IdSource(((CardSourceResponse) firstCardPaymentResponse.getPayment().getSource()).getId());
        PaymentRequest<IdSource> cardIdPaymentRequest = PaymentRequest.fromSource(idSource, Currency.GBP, 100L);
        cardIdPaymentRequest.setCapture(false);
        cardIdPaymentRequest.setCustomer(toRequest(firstCardPaymentResponse.getPayment().getCustomer()));

        PaymentResponse apiResponseForCustomerSourcePayment = getApi().paymentsClient().requestAsync(cardIdPaymentRequest).get();

        Assert.assertNotNull(apiResponseForCustomerSourcePayment.getPayment());
        Assert.assertTrue(apiResponseForCustomerSourcePayment.getPayment().isApproved());
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(apiResponseForCustomerSourcePayment.getPayment().getId()));
        Assert.assertNotEquals(firstCardPaymentResponse.getPayment().getId(), apiResponseForCustomerSourcePayment.getPayment().getId());
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(apiResponseForCustomerSourcePayment.getPayment().getActionId()));
        Assert.assertEquals(cardIdPaymentRequest.getAmount().intValue(), apiResponseForCustomerSourcePayment.getPayment().getAmount());
        Assert.assertEquals(cardIdPaymentRequest.getCurrency(), apiResponseForCustomerSourcePayment.getPayment().getCurrency());
        Assert.assertEquals(cardIdPaymentRequest.getReference(), apiResponseForCustomerSourcePayment.getPayment().getReference());
        Assert.assertNotNull(apiResponseForCustomerSourcePayment.getPayment().getCustomer());
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(apiResponseForCustomerSourcePayment.getPayment().getCustomer().getId()));
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(apiResponseForCustomerSourcePayment.getPayment().getCustomer().getEmail()));
        Assert.assertEquals(firstCardPaymentResponse.getPayment().getCustomer().getId(), apiResponseForCustomerSourcePayment.getPayment().getCustomer().getId());
        Assert.assertEquals(((CardSourceResponse) firstCardPaymentResponse.getPayment().getSource()).getId(), ((CardSourceResponse) apiResponseForCustomerSourcePayment.getPayment().getSource()).getId());
        Assert.assertTrue(apiResponseForCustomerSourcePayment.getPayment().canCapture());
        Assert.assertTrue(apiResponseForCustomerSourcePayment.getPayment().canVoid());
    }

    private CustomerRequest toRequest(CustomerResponse customer) {
        CustomerRequest request = new CustomerRequest();
        request.setId(customer.getId());
        request.setEmail(customer.getEmail());
        request.setName(customer.getName());
        return request;
    }
}