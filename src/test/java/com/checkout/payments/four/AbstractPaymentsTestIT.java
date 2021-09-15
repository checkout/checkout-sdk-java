package com.checkout.payments.four;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import com.checkout.common.four.Currency;
import com.checkout.payments.four.capture.CaptureRequest;
import com.checkout.payments.four.capture.CaptureResponse;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.request.Payments;
import com.checkout.payments.four.request.source.RequestCardSource;
import com.checkout.payments.four.request.source.RequestIdSource;
import com.checkout.payments.four.request.source.RequestTokenSource;
import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.payments.four.response.source.ResponseCardSource;
import com.checkout.payments.four.response.source.ResponseIdSource;
import com.checkout.payments.four.sender.RequestCorporateSender;
import com.checkout.payments.four.sender.RequestIndividualSender;
import com.checkout.tokens.four.TokensClient;
import com.checkout.tokens.four.request.CardTokenRequest;
import com.checkout.tokens.four.response.CardTokenResponse;

import java.util.UUID;

import static com.checkout.payments.four.CardSourceHelper.getCardSourcePayment;
import static com.checkout.payments.four.CardSourceHelper.getCorporateSender;
import static com.checkout.payments.four.CardSourceHelper.getIndividualSender;
import static com.checkout.payments.four.CardSourceHelper.getRequestCardSource;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AbstractPaymentsTestIT extends SandboxTestFixture {

    protected final PaymentsClient paymentsClient;
    protected final TokensClient tokensClient;

    public AbstractPaymentsTestIT() {
        super(PlatformType.FOUR);
        this.paymentsClient = fourApi.paymentsClient();
        this.tokensClient = fourApi.tokensClient();
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

        final PaymentResponse<ResponseIdSource> response = blocking(fourApi.paymentsClient().requestPayment(idSourceRequest));

        assertNotNull(response);

        return response;

    }

    protected PaymentResponse<ResponseCardSource> makeTokenPayment() {

        final CardTokenResponse cardTokenResponse = requestToken();

        final RequestTokenSource tokenSource = RequestTokenSource.builder()
                .token(cardTokenResponse.getToken())
                .billingAddress(Address.builder()
                        .addressLine1("Address Line 1")
                        .addressLine2("Address Line 2")
                        .city("City")
                        .country(CountryCode.ES)
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

        final PaymentResponse<ResponseCardSource> paymentResponse = blocking(fourApi.paymentsClient().requestPayment(tokenRequest));

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

    protected CardTokenResponse requestToken() {
        final CardTokenRequest request = CardTokenRequest.builder()
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .build();
        return blocking(tokensClient.requestAsync(request));
    }

}
