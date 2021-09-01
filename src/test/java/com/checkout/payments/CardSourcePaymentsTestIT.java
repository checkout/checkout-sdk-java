package com.checkout.payments;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardSourcePaymentsTestIT extends SandboxTestFixture {

    public CardSourcePaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    public void can_request_non_3ds_card_payment() throws Exception {
        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setThreeDS(ThreeDSRequest.from(false));

        final PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        assertNotNull(paymentResponse.getPayment());
        assertTrue(paymentResponse.getPayment().isApproved());
        assertFalse(StringUtils.isEmpty(paymentResponse.getPayment().getId()));
        assertFalse(StringUtils.isEmpty(paymentResponse.getPayment().getActionId()));
        assertEquals(paymentRequest.getAmount().intValue(), paymentResponse.getPayment().getAmount());
        assertEquals(paymentRequest.getCurrency(), paymentResponse.getPayment().getCurrency());
        assertEquals(paymentRequest.getReference(), paymentResponse.getPayment().getReference());
        assertNotNull(paymentResponse.getPayment().getCustomer());
        assertFalse(StringUtils.isEmpty(paymentResponse.getPayment().getCustomer().getId()));
        assertFalse(StringUtils.isEmpty(paymentResponse.getPayment().getCustomer().getEmail()));
        assertTrue(paymentResponse.getPayment().canCapture());
        assertTrue(paymentResponse.getPayment().canVoid());
        assertNotNull(paymentResponse.getPayment().getSource());
        assertTrue(paymentResponse.getPayment().getSource() instanceof CardSourceResponse);
        assertFalse(paymentRequest.getSource().getStored());
    }

    @Test
    public void can_request_3ds_card_payment() throws Exception {
        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        paymentRequest.setThreeDS(ThreeDSRequest.from(true));

        final PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        assertTrue(paymentResponse.isPending());
        final PaymentPending pending = paymentResponse.getPending();

        assertNotNull(pending);

        assertFalse(StringUtils.isEmpty(pending.getId()));
        assertEquals(paymentRequest.getReference(), pending.getReference());
        assertNotNull(pending.getCustomer());
        assertFalse(StringUtils.isEmpty(pending.getCustomer().getId()));
        assertEquals(paymentRequest.getCustomer().getEmail(), pending.getCustomer().getEmail());
        assertNotNull(pending.getThreeDS());
        assertFalse(pending.getThreeDS().isDowngraded());
        assertFalse(StringUtils.isEmpty(pending.getThreeDS().getEnrolled()));
        assertTrue(pending.requiresRedirect());
        assertNotNull(pending.getRedirectLink());
    }

    @Test
    public void can_void_payment() throws Exception {
        // Auth
        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        final PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();
        assertTrue(paymentResponse.getPayment().canVoid());

        final VoidRequest voidRequest = new VoidRequest();
        voidRequest.setReference(UUID.randomUUID().toString());

        // Void Auth
        final VoidResponse voidResponse = getApi().paymentsClient().voidAsync(paymentResponse.getPayment().getId(), voidRequest).get();

        assertFalse(StringUtils.isEmpty(voidResponse.getActionId()));
        assertEquals(voidRequest.getReference(), voidResponse.getReference());
    }

    @Test
    public void can_refund_payment() throws Exception {
        // Auth
        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        final PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();
        assertTrue(paymentResponse.getPayment().canCapture());

        // Capture
        getApi().paymentsClient().captureAsync(paymentResponse.getPayment().getId()).get();

        final RefundRequest refundRequest = new RefundRequest();
        refundRequest.setReference(UUID.randomUUID().toString());

        // Refund
        final RefundResponse refundResponse = getApi().paymentsClient().refundAsync(paymentResponse.getPayment().getId(), refundRequest).get();

        assertFalse(StringUtils.isEmpty(refundResponse.getActionId()));
        assertEquals(refundRequest.getReference(), refundResponse.getReference());
    }
}