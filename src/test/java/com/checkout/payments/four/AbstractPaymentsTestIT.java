package com.checkout.payments.four;

import com.checkout.CardSourceHelper;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.request.Payments;
import com.checkout.payments.four.request.source.RequestCardSource;
import com.checkout.payments.four.request.source.RequestIdSource;
import com.checkout.payments.four.request.source.RequestTokenSource;
import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.payments.four.response.source.CardResponseSource;
import com.checkout.payments.four.sender.PaymentCorporateSender;
import com.checkout.payments.four.sender.PaymentIndividualSender;
import com.checkout.tokens.CardTokenRequest;
import com.checkout.tokens.CardTokenResponse;
import com.checkout.tokens.TokensClient;

import java.util.UUID;

import static com.checkout.CardSourceHelper.getCardSourcePayment;
import static com.checkout.CardSourceHelper.getCorporateSender;
import static com.checkout.CardSourceHelper.getIndividualSender;
import static com.checkout.CardSourceHelper.getRequestCardSource;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AbstractPaymentsTestIT extends SandboxTestFixture {

    protected final PaymentsClient paymentsClient;
    protected final TokensClient tokensClient;

    public AbstractPaymentsTestIT() {
        super(PlatformType.FOUR);
        this.paymentsClient = fourApi.paymentsClient();
        this.tokensClient = fourApi.tokensClient();
    }

    protected PaymentResponse makeCardPayment(final boolean three3ds) {

        final RequestCardSource source = getRequestCardSource();
        final PaymentIndividualSender sender = getIndividualSender();

        final PaymentRequest request = getCardSourcePayment(source, sender, three3ds);
        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(request));

        assertNotNull(paymentResponse);

        return paymentResponse;
    }

    protected PaymentResponse makeIdSourcePayment() {

        final PaymentResponse cardPaymentResponse = makeCardPayment(false);

        final RequestIdSource idSource = RequestIdSource.builder()
                .id(((CardResponseSource) cardPaymentResponse.getSource()).getId())
                .cvv(CardSourceHelper.Visa.CVV)
                .build();

        final PaymentIndividualSender sender = getIndividualSender();

        final PaymentRequest idSourceRequest = Payments.id(idSource).individualSender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(10L)
                .currency(Currency.EUR)
                .build();

        final PaymentResponse response = blocking(() -> fourApi.paymentsClient().requestPayment(idSourceRequest));

        assertNotNull(response);

        return response;

    }

    protected PaymentResponse makeTokenPayment() {

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

        final PaymentCorporateSender sender = getCorporateSender();

        final PaymentRequest tokenRequest = Payments.token(tokenSource).corporateSender(sender)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .amount(3456L)
                .currency(Currency.USD)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> fourApi.paymentsClient().requestPayment(tokenRequest));

        assertNotNull(paymentResponse);

        return paymentResponse;

    }

    protected PaymentResponse makeCardPayment(final PaymentRequest payment) {
        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(payment));
        assertNotNull(paymentResponse);
        return paymentResponse;
    }

    protected void capturePayment(final String paymentId) {
        final CaptureResponse captureResponse = blocking(() -> paymentsClient.capturePayment(paymentId));
        assertNotNull(captureResponse);
    }

    protected CaptureResponse capturePayment(final String paymentId, final CaptureRequest captureRequest) {
        final CaptureResponse captureResponse = blocking(() -> paymentsClient.capturePayment(paymentId, captureRequest));
        assertNotNull(captureResponse);
        return captureResponse;
    }

    protected CardTokenResponse requestToken() {
        final CardTokenRequest request = CardTokenRequest.builder()
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .build();
        return blocking(() -> tokensClient.request(request));
    }

}
