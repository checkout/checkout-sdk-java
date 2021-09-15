package com.checkout.payments;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import com.checkout.tokens.CardTokenRequest;
import com.checkout.tokens.CardTokenResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenSourcePaymentsTestIT extends SandboxTestFixture {

    TokenSourcePaymentsTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldRequestNon3dsCardPayment() throws Exception {

        final CardTokenRequest cardTokenRequest = TestHelper.createCardTokenRequest();
        final CardTokenResponse cardTokenResponse = defaultApi.tokensClient().requestAsync(cardTokenRequest).get();
        final PaymentRequest<TokenSource> paymentRequest = TestHelper.createTokenPaymentRequest(cardTokenResponse.getToken());
        paymentRequest.setThreeDS(ThreeDSRequest.from(false));

        final PaymentResponse paymentResponse = defaultApi.paymentsClient().requestAsync(paymentRequest).get();

        assertNotNull(paymentResponse.getPayment());
        assertTrue(paymentResponse.getPayment().isApproved());
        assertFalse(StringUtils.isEmpty(paymentResponse.getPayment().getId()));
        assertFalse(StringUtils.isEmpty(paymentResponse.getPayment().getActionId()));
        assertEquals(paymentRequest.getAmount().intValue(), paymentResponse.getPayment().getAmount());
        assertEquals(paymentRequest.getCurrency(), paymentResponse.getPayment().getCurrency());
        assertEquals(paymentRequest.getReference(), paymentResponse.getPayment().getReference());
        assertTrue(paymentResponse.getPayment().canCapture());
        assertTrue(paymentResponse.getPayment().canVoid());
        assertNotNull(paymentResponse.getPayment().getSource());
    }

    @Test
    void shouldRequest3dsCardPayment() throws Exception {

        final CardTokenRequest cardTokenRequest = TestHelper.createCardTokenRequest();
        final CardTokenResponse cardTokenResponse = defaultApi.tokensClient().requestAsync(cardTokenRequest).get();
        final PaymentRequest<TokenSource> paymentRequest = TestHelper.createTokenPaymentRequest(cardTokenResponse.getToken());
        paymentRequest.setThreeDS(ThreeDSRequest.from(true));

        final PaymentResponse paymentResponse = defaultApi.paymentsClient().requestAsync(paymentRequest).get();

        assertTrue(paymentResponse.isPending());
        final PaymentPending pending = paymentResponse.getPending();

        assertNotNull(pending);

        assertFalse(StringUtils.isEmpty(pending.getId()));
        assertEquals(paymentRequest.getReference(), pending.getReference());
        assertNotNull(pending.getThreeDS());
        assertFalse(pending.getThreeDS().isDowngraded());
        assertFalse(StringUtils.isEmpty(pending.getThreeDS().getEnrolled()));
        assertTrue(pending.requiresRedirect());
        assertNotNull(pending.getRedirectLink());
    }

    @Test
    void shouldCapturePayment() throws Exception {

        final CardTokenRequest cardTokenRequest = TestHelper.createCardTokenRequest();
        final CardTokenResponse cardTokenResponse = defaultApi.tokensClient().requestAsync(cardTokenRequest).get();
        final PaymentRequest<TokenSource> paymentRequest = TestHelper.createTokenPaymentRequest(cardTokenResponse.getToken());
        final PaymentResponse paymentResponse = defaultApi.paymentsClient().requestAsync(paymentRequest).get();

        assertTrue(paymentResponse.getPayment().canCapture());

        final CaptureRequest captureRequest = new CaptureRequest();
        captureRequest.setReference(UUID.randomUUID().toString());

        final CaptureResponse captureResponse = defaultApi.paymentsClient().captureAsync(paymentResponse.getPayment().getId(), captureRequest).get();

        assertFalse(StringUtils.isEmpty(captureResponse.getActionId()));
        assertEquals(captureRequest.getReference(), captureResponse.getReference());
    }

    @Test
    void shouldVoidPayment() throws Exception {

        final PaymentRequest<CardSource> paymentRequest = TestHelper.createCardPaymentRequest();
        final PaymentResponse paymentResponse = defaultApi.paymentsClient().requestAsync(paymentRequest).get();
        assertTrue(paymentResponse.getPayment().canVoid());

        final VoidRequest voidRequest = new VoidRequest();
        voidRequest.setReference(UUID.randomUUID().toString());

        final VoidResponse voidResponse = defaultApi.paymentsClient().voidAsync(paymentResponse.getPayment().getId(), voidRequest).get();

        assertFalse(StringUtils.isEmpty(voidResponse.getActionId()));
        assertEquals(voidRequest.getReference(), voidResponse.getReference());
    }

    @Test
    void shouldRefundPayment() throws Exception {

        final CardTokenRequest cardTokenRequest = TestHelper.createCardTokenRequest();
        final CardTokenResponse cardTokenResponse = defaultApi.tokensClient().requestAsync(cardTokenRequest).get();
        final PaymentRequest<TokenSource> paymentRequest = TestHelper.createTokenPaymentRequest(cardTokenResponse.getToken());
        final PaymentResponse paymentResponse = defaultApi.paymentsClient().requestAsync(paymentRequest).get();
        assertTrue(paymentResponse.getPayment().canCapture());

        defaultApi.paymentsClient().captureAsync(paymentResponse.getPayment().getId()).get();

        final RefundRequest refundRequest = new RefundRequest();
        refundRequest.setReference(UUID.randomUUID().toString());

        final RefundResponse refundResponse = defaultApi.paymentsClient().refundAsync(paymentResponse.getPayment().getId(), refundRequest).get();

        assertFalse(StringUtils.isEmpty(refundResponse.getActionId()));
        assertEquals(refundRequest.getReference(), refundResponse.getReference());
    }

}
