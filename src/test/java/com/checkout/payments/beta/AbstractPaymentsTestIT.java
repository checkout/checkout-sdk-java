package com.checkout.payments.beta;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.beta.Address;
import com.checkout.common.beta.Currency;
import com.checkout.common.beta.Phone;
import com.checkout.payments.beta.capture.CaptureRequest;
import com.checkout.payments.beta.capture.CaptureResponse;
import com.checkout.payments.beta.request.PaymentRequest;
import com.checkout.payments.beta.request.Payments;
import com.checkout.payments.beta.request.source.RequestCardSource;
import com.checkout.payments.beta.request.source.RequestIdSource;
import com.checkout.payments.beta.request.source.RequestTokenSource;
import com.checkout.payments.beta.response.PaymentResponse;
import com.checkout.payments.beta.response.source.ResponseCardSource;
import com.checkout.payments.beta.response.source.ResponseIdSource;
import com.checkout.payments.beta.sender.RequestCorporateSender;
import com.checkout.payments.beta.sender.RequestIndividualSender;
import com.checkout.tokens.beta.CardTokenRequest;
import com.checkout.tokens.beta.CardTokenResponse;
import com.checkout.tokens.beta.TokensClient;

import java.util.UUID;

import static com.checkout.payments.beta.CardSourceHelper.getCardSourcePayment;
import static com.checkout.payments.beta.CardSourceHelper.getCorporateSender;
import static com.checkout.payments.beta.CardSourceHelper.getIndividualSender;
import static com.checkout.payments.beta.CardSourceHelper.getRequestCardSource;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public abstract class AbstractPaymentsTestIT extends SandboxTestFixture {

    protected final PaymentsClient paymentsClient;
    protected final TokensClient tokensClient;

    public AbstractPaymentsTestIT() {
        super(PlatformType.FOUR);
        this.paymentsClient = getApiV2().paymentsClient();
        this.tokensClient = getApiV2().tokensClient();
    }

    protected PaymentResponse<ResponseCardSource> makeCardPayment(final boolean three3ds) {

        final RequestCardSource source = getRequestCardSource();
        final RequestIndividualSender sender = getIndividualSender();

        final PaymentRequest request = getCardSourcePayment(source, sender, three3ds);
        final PaymentResponse<ResponseCardSource> paymentResponse = blocking(paymentsClient.requestPayment(request));

        assertNotNull(paymentResponse);

        return paymentResponse;
    }

    protected PaymentResponse<ResponseIdSource> makeIdSourcePayment() {

        final PaymentResponse<ResponseCardSource> cardPaymentResponse = makeCardPayment(false);

        final RequestIdSource idSource = RequestIdSource.builder()
                .id(cardPaymentResponse.getSource().getId())
                .ccv(CardSourceHelper.Visa.CCV)
                .build();

        final RequestIndividualSender sender = getIndividualSender();

        final PaymentRequest idSourceRequest = Payments.id(idSource).individualSender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(10L)
                .currency(Currency.EUR)
                .build();

        final PaymentResponse<ResponseIdSource> response = blocking(getApiV2().paymentsClient().requestPayment(idSourceRequest));

        assertNotNull(response);

        return response;

    }

    protected PaymentResponse<ResponseCardSource> makeTokenPayment() {

        final CardTokenResponse cardTokenResponse = getToken();

        final RequestTokenSource tokenSource = RequestTokenSource.builder()
                .token(cardTokenResponse.getToken())
                .billingAddress(Address.builder()
                        .addressLine1("Address Line 1")
                        .addressLine2("Address Line 2")
                        .city("City")
                        .country("ES")
                        .build())
                .phone(Phone.builder().number("675676541").countryCode("+34").build())
                .build();

        final RequestCorporateSender sender = getCorporateSender();

        final PaymentRequest tokenRequest = Payments.token(tokenSource).corporateSender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(3456L)
                .currency(Currency.USD)
                .build();

        final PaymentResponse<ResponseCardSource> paymentResponse = blocking(getApiV2().paymentsClient().requestPayment(tokenRequest));

        assertNotNull(paymentResponse);

        return paymentResponse;

    }

    protected PaymentResponse<ResponseCardSource> makeCardPayment(final PaymentRequest payment) {
        final PaymentResponse<ResponseCardSource> paymentResponse = blocking(paymentsClient.requestPayment(payment));
        assertNotNull(paymentResponse);
        return paymentResponse;
    }

    protected void capturePayment(final String paymentId) {
        final CaptureResponse captureResponse = blocking(paymentsClient.capturePayment(paymentId));
        assertNotNull(captureResponse);
    }

    protected CaptureResponse capturePayment(final String paymentId, final CaptureRequest captureRequest) {
        final CaptureResponse captureResponse = blocking(paymentsClient.capturePayment(paymentId, captureRequest));
        assertNotNull(captureResponse);
        return captureResponse;
    }

    protected CardTokenResponse getToken() {
        final CardTokenRequest request = CardTokenRequest.builder()
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .build();
        return blocking(tokensClient.requestAsync(request));
    }

    protected void waitForIt() {
        try {
            Thread.sleep(2000);
        } catch (final InterruptedException ignore) {
            fail();
        }
    }

}
