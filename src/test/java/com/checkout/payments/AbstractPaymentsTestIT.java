package com.checkout.payments;

import com.checkout.CardSourceHelper;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.TestHelper;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.Phone;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.RequestCardSource;
import com.checkout.payments.request.source.RequestTokenSource;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.tokens.TokensClient;
import com.checkout.tokens.CardTokenRequest;
import com.checkout.tokens.CardTokenResponse;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class AbstractPaymentsTestIT extends SandboxTestFixture {

    protected static final String IDEMPOTENCY_KEY = "test.java";

    protected final PaymentsClient paymentsClient;
    protected final TokensClient tokensClient;

    public AbstractPaymentsTestIT() {
        super(PlatformType.DEFAULT);
        this.paymentsClient = defaultApi.paymentsClient();
        this.tokensClient = defaultApi.tokensClient();
    }

    protected PaymentResponse makeCardPayment(final boolean shouldCapture, final long amount) {

        final Phone phone = Phone.builder()
                .countryCode("44")
                .number("020 222333")
                .build();

        final Address billingAddress = Address.builder()
                .addressLine1("CheckoutSdk.com")
                .addressLine2("90 Tottenham Court Road")
                .city("London")
                .state("London")
                .zip("W1T 4TJ")
                .country(CountryCode.GB)
                .build();

        final RequestCardSource source = RequestCardSource.builder()
                .name(CardSourceHelper.Visa.NAME)
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .cvv(CardSourceHelper.Visa.CVV)
                .stored(false)
                .billingAddress(billingAddress)
                .phone(phone)
                .build();

        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(source)
                .capture(shouldCapture)
                .reference(UUID.randomUUID().toString())
                .amount(amount)
                .currency(Currency.GBP)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        return paymentResponse;
    }

    protected PaymentResponse makeTokenPayment() {

        final Phone phone = Phone.builder()
                .countryCode("44")
                .number("020 222333")
                .build();

        final Address billingAddress = Address.builder()
                .addressLine1("CheckoutSdk.com")
                .addressLine2("90 Tottenham Court Road")
                .city("London")
                .state("London")
                .zip("W1T 4TJ")
                .country(CountryCode.GB)
                .build();

        final CardTokenRequest cardTokenRequest = CardTokenRequest.builder()
                .name(TestCardSource.VISA.getName())
                .number(TestCardSource.VISA.getNumber())
                .expiryMonth(TestCardSource.VISA.getExpiryMonth())
                .expiryYear(TestCardSource.VISA.getExpiryYear())
                .cvv(TestCardSource.VISA.getCvv())
                .billingAddress(billingAddress)
                .phone(phone)
                .build();

        final CardTokenResponse cardTokenResponse = blocking(() -> defaultApi.tokensClient().request(cardTokenRequest));
        assertNotNull(cardTokenResponse);

        final RequestTokenSource requestTokenSource = RequestTokenSource.builder()
                .token(cardTokenResponse.getToken())
                .build();

        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(requestTokenSource)
                .capture(true)
                .reference(UUID.randomUUID().toString())
                .amount(10L)
                .currency(Currency.GBP)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        return paymentResponse;
    }

    protected PaymentResponse make3dsCardPayment(final boolean attemptN3d) {

        final Phone phone = Phone.builder()
                .countryCode("44")
                .number("020 222333")
                .build();

        final Address billingAddress = Address.builder()
                .addressLine1("CheckoutSdk.com")
                .addressLine2("90 Tottenham Court Road")
                .city("London")
                .state("London")
                .zip("W1T 4TJ")
                .country(CountryCode.GB)
                .build();

        final RequestCardSource source = RequestCardSource.builder()
                .name(CardSourceHelper.Visa.NAME)
                .number(CardSourceHelper.Visa.NUMBER)
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .cvv(CardSourceHelper.Visa.CVV)
                .stored(false)
                .billingAddress(billingAddress)
                .phone(phone)
                .build();

        final ThreeDSRequest threeDSRequest = ThreeDSRequest.builder()
                .attemptN3D(attemptN3d)
                .cryptogram(attemptN3d ? "AgAAAAAAAIR8CQrXcIhbQAAAAAA" : null)
                .eci(attemptN3d ? "05" : null)
                .enabled(true)
                .version("2.0.1")
                .xid(attemptN3d ? "MDAwMDAwMDAwMDAwMDAwMzIyNzY=" : null)
                .build();

        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(source)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .customer(new CustomerRequest(null, TestHelper.generateRandomEmail(), null, null))
                .amount(10L)
                .currency(Currency.USD)
                .threeDS(threeDSRequest)
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(paymentRequest));
        assertNotNull(paymentResponse);

        return paymentResponse;
    }

}
