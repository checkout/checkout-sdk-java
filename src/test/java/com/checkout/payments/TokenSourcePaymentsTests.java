package com.checkout.payments;

import com.checkout.ApiTestFixture;
import com.checkout.TestHelper;
import com.checkout.common.CheckoutUtils;
import com.checkout.tokens.CardTokenRequest;
import com.checkout.tokens.CardTokenResponse;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class TokenSourcePaymentsTests extends ApiTestFixture {

    @Test
    public void can_request_non_3ds_card_payment() throws Exception {
        CardTokenRequest cardTokenRequest = TestHelper.createCardTokenRequest();
        CardTokenResponse cardTokenResponse = getApi().tokensClient().requestAsync(cardTokenRequest).get();
        PaymentRequest<TokenSource> paymentRequest = TestHelper.createTokenPaymentRequest(cardTokenResponse.getToken());
        paymentRequest.setThreeDS(ThreeDSRequest.from(false));

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        Assert.assertNotNull(paymentResponse.getPayment());
        Assert.assertTrue(paymentResponse.getPayment().isApproved());
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(paymentResponse.getPayment().getId()));
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(paymentResponse.getPayment().getActionId()));
        Assert.assertEquals(paymentRequest.getAmount().intValue(), paymentResponse.getPayment().getAmount());
        Assert.assertEquals(paymentRequest.getCurrency(), paymentResponse.getPayment().getCurrency());
        Assert.assertEquals(paymentRequest.getReference(), paymentResponse.getPayment().getReference());
        Assert.assertTrue(paymentResponse.getPayment().canCapture());
        Assert.assertTrue(paymentResponse.getPayment().canVoid());
        Assert.assertNotNull(paymentResponse.getPayment().getSource());
    }

    @Test
    public void can_request_3ds_card_payment() throws Exception {
        CardTokenRequest cardTokenRequest = TestHelper.createCardTokenRequest();
        CardTokenResponse cardTokenResponse = getApi().tokensClient().requestAsync(cardTokenRequest).get();
        PaymentRequest<TokenSource> paymentRequest = TestHelper.createTokenPaymentRequest(cardTokenResponse.getToken());
        paymentRequest.setThreeDS(ThreeDSRequest.from(true));

        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();

        Assert.assertTrue(paymentResponse.isPending());
        PaymentPending pending = paymentResponse.getPending();

        Assert.assertNotNull(pending);

        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(pending.getId()));
        Assert.assertEquals(paymentRequest.getReference(), pending.getReference());
        Assert.assertNotNull(pending.getThreeDS());
        Assert.assertFalse(pending.getThreeDS().isDowngraded());
        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(pending.getThreeDS().getEnrolled()));
        Assert.assertTrue(pending.requiresRedirect());
        Assert.assertNotNull(pending.getRedirectLink());
    }

    @Test
    public void can_capture_payment() throws Exception {
        CardTokenRequest cardTokenRequest = TestHelper.createCardTokenRequest();
        CardTokenResponse cardTokenResponse = getApi().tokensClient().requestAsync(cardTokenRequest).get();
        PaymentRequest<TokenSource> paymentRequest = TestHelper.createTokenPaymentRequest(cardTokenResponse.getToken());
        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();


        Assert.assertTrue(paymentResponse.getPayment().canCapture());

        CaptureRequest captureRequest = new CaptureRequest();
        captureRequest.setReference(UUID.randomUUID().toString());

        CaptureResponse captureResponse = getApi().paymentsClient().captureAsync(paymentResponse.getPayment().getId(), captureRequest).get();

        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(captureResponse.getActionId()));
        Assert.assertEquals(captureRequest.getReference(), captureResponse.getReference());
    }

    @Test
    public void can_void_payment() throws Exception {
        PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();
        Assert.assertTrue(paymentResponse.getPayment().canVoid());

        VoidRequest voidRequest = new VoidRequest();
        voidRequest.setReference(UUID.randomUUID().toString());

        VoidResponse voidResponse = getApi().paymentsClient().voidAsync(paymentResponse.getPayment().getId(), voidRequest).get();

        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(voidResponse.getActionId()));
        Assert.assertEquals(voidRequest.getReference(), voidResponse.getReference());
    }

    @Test
    public void can_refund_payment() throws Exception {
        CardTokenRequest cardTokenRequest = TestHelper.createCardTokenRequest();
        CardTokenResponse cardTokenResponse = getApi().tokensClient().requestAsync(cardTokenRequest).get();
        PaymentRequest<TokenSource> paymentRequest = TestHelper.createTokenPaymentRequest(cardTokenResponse.getToken());
        PaymentResponse paymentResponse = getApi().paymentsClient().requestAsync(paymentRequest).get();
        Assert.assertTrue(paymentResponse.getPayment().canCapture());

        getApi().paymentsClient().captureAsync(paymentResponse.getPayment().getId()).get();

        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setReference(UUID.randomUUID().toString());

        RefundResponse refundResponse = getApi().paymentsClient().refundAsync(paymentResponse.getPayment().getId(), refundRequest).get();

        Assert.assertFalse(CheckoutUtils.isNullOrEmpty(refundResponse.getActionId()));
        Assert.assertEquals(refundRequest.getReference(), refundResponse.getReference());
    }
}