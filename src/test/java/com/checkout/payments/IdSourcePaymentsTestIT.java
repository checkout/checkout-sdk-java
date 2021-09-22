package com.checkout.payments;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdSourcePaymentsTestIT extends SandboxTestFixture {

    IdSourcePaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldRequestCardPayment() throws Exception {
        final PaymentRequest<CardSource> firstCardPayment = TestHelper.createCardPaymentRequest();

        final PaymentResponse firstCardPaymentResponse = defaultApi.paymentsClient().requestAsync(firstCardPayment).get();

        final IdSource idSource = new IdSource(((CardSourceResponse) firstCardPaymentResponse.getPayment().getSource()).getId());
        final PaymentRequest<IdSource> cardIdPaymentRequest = PaymentRequest.fromSource(idSource, Currency.GBP, 100L);
        cardIdPaymentRequest.setCapture(false);
        cardIdPaymentRequest.setCustomer(toRequest(firstCardPaymentResponse.getPayment().getCustomer()));

        final PaymentResponse apiResponseForCustomerSourcePayment = defaultApi.paymentsClient().requestAsync(cardIdPaymentRequest).get();

        assertNotNull(apiResponseForCustomerSourcePayment.getPayment());
        assertTrue(apiResponseForCustomerSourcePayment.getPayment().isApproved());
        assertFalse(StringUtils.isEmpty(apiResponseForCustomerSourcePayment.getPayment().getId()));
        assertNotEquals(firstCardPaymentResponse.getPayment().getId(), apiResponseForCustomerSourcePayment.getPayment().getId());
        assertFalse(StringUtils.isEmpty(apiResponseForCustomerSourcePayment.getPayment().getActionId()));
        assertEquals(cardIdPaymentRequest.getAmount().intValue(), apiResponseForCustomerSourcePayment.getPayment().getAmount());
        assertEquals(cardIdPaymentRequest.getCurrency(), apiResponseForCustomerSourcePayment.getPayment().getCurrency());
        assertEquals(cardIdPaymentRequest.getReference(), apiResponseForCustomerSourcePayment.getPayment().getReference());
        assertNotNull(apiResponseForCustomerSourcePayment.getPayment().getCustomer());
        assertFalse(StringUtils.isEmpty(apiResponseForCustomerSourcePayment.getPayment().getCustomer().getId()));
        assertFalse(StringUtils.isEmpty(apiResponseForCustomerSourcePayment.getPayment().getCustomer().getEmail()));
        assertEquals(firstCardPaymentResponse.getPayment().getCustomer().getId(), apiResponseForCustomerSourcePayment.getPayment().getCustomer().getId());
        assertEquals(((CardSourceResponse) firstCardPaymentResponse.getPayment().getSource()).getId(), ((CardSourceResponse) apiResponseForCustomerSourcePayment.getPayment().getSource()).getId());
        assertTrue(apiResponseForCustomerSourcePayment.getPayment().canCapture());
        assertTrue(apiResponseForCustomerSourcePayment.getPayment().canVoid());
    }

    private CustomerRequest toRequest(final CustomerResponse customer) {
        return new CustomerRequest(customer.getId(), customer.getEmail(), customer.getName());
    }

}