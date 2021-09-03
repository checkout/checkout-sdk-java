package com.checkout.payments;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.Currency;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerSourcePaymentsTestIT extends SandboxTestFixture {

    CustomerSourcePaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldRequestCardPayment() throws Exception {

        final PaymentRequest<CardSource> firstCardPayment = TestHelper.createCardPaymentRequest();
        final PaymentResponse firstCardPaymentResponse = getApi().paymentsClient().requestAsync(firstCardPayment).get();
        final CustomerSource customerSource = new CustomerSource(firstCardPayment.getCustomer().getId(), firstCardPayment.getCustomer().getEmail());
        final PaymentRequest<CustomerSource> customerPaymentRequest = PaymentRequest.fromSource(customerSource, Currency.GBP, 100L);
        customerPaymentRequest.setCapture(false);

        final PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(customerPaymentRequest).get();

        assertNotNull(paymentResponse.getPayment());
        assertTrue(paymentResponse.getPayment().isApproved());
        assertFalse(StringUtils.isEmpty(paymentResponse.getPayment().getId()));
        assertNotEquals(firstCardPaymentResponse.getPayment().getId(), paymentResponse.getPayment().getId());
        assertFalse(StringUtils.isEmpty(paymentResponse.getPayment().getActionId()));
        assertEquals(customerPaymentRequest.getAmount().intValue(), paymentResponse.getPayment().getAmount());
        assertEquals(customerPaymentRequest.getCurrency(), paymentResponse.getPayment().getCurrency());
        assertEquals(customerPaymentRequest.getReference(), paymentResponse.getPayment().getReference());
        assertNotNull(paymentResponse.getPayment().getCustomer());
        assertFalse(StringUtils.isEmpty(paymentResponse.getPayment().getCustomer().getId()));
        assertFalse(StringUtils.isEmpty(paymentResponse.getPayment().getCustomer().getEmail()));
        assertEquals(firstCardPaymentResponse.getPayment().getCustomer().getId(), paymentResponse.getPayment().getCustomer().getId());
        assertNotNull(paymentResponse.getPayment().getSource());
        assertEquals(((CardSourceResponse) firstCardPaymentResponse.getPayment().getSource()).getId(), ((CardSourceResponse) paymentResponse.getPayment().getSource()).getId());
        assertTrue(paymentResponse.getPayment().canCapture());
        assertTrue(paymentResponse.getPayment().canVoid());
    }

}