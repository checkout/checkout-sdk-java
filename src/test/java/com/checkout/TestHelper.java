package com.checkout;

import com.checkout.common.Address;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.common.Product;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.CardSource;
import com.checkout.payments.CustomerRequest;
import com.checkout.payments.DLocalSource;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.Processing;
import com.checkout.payments.RequestSource;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.SenderInformation;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.TokenSource;
import com.checkout.payments.hosted.HostedPaymentRequest;
import com.checkout.payments.links.PaymentLinkRequest;
import com.checkout.tokens.CardTokenRequest;

import java.util.Collections;
import java.util.UUID;

public class TestHelper {

    public static final String VALID_CLASSIC_SK = "sk_test_fde517a8-3f01-41ef-b4bd-4282384b0a64";
    public static final String VALID_CLASSIC_PK = "pk_test_fe70ff27-7c32-4ce1-ae90-5691a188ee7b";
    public static final String INVALID_CLASSIC_SK = "sk_test_asdsad3q4dq";
    public static final String INVALID_CLASSIC_PK = "pk_test_q414dasds";
    public static final String VALID_FOUR_SK = "sk_sbox_m73dzbpy7cf3gfd46xr4yj5xo4e";
    public static final String VALID_FOUR_PK = "pk_sbox_pkhpdtvmkgf7hdnpwnbhw7r2uic";
    public static final String INVALID_FOUR_SK = "sk_sbox_m73dzbpy7c-f3gfd46xr4yj5xo4e";
    public static final String INVALID_FOUR_PK = "pk_sbox_pkh";

    public static CheckoutConfiguration mockClassicConfiguration() {
        return new CheckoutConfiguration(VALID_CLASSIC_SK, true, VALID_CLASSIC_PK);
    }

    public static CheckoutConfiguration mockFourConfiguration() {
        return new CheckoutConfiguration(VALID_FOUR_PK, VALID_FOUR_SK, Environment.SANDBOX);
    }

    public static PaymentRequest<CardSource> createCardPaymentRequest() {
        return createCardPaymentRequest(100L);
    }

    public static PaymentRequest<DLocalSource> createDLocalPaymentRequest() {
        return createDLocalPaymentRequest(100L);
    }

    public static PaymentRequest<CardSource> createCardPayoutRequest() {
        final CardSource cardSource = new CardSource(TestCardSource.VISA.getNumber(), TestCardSource.VISA.getExpiryMonth(), TestCardSource.VISA.getExpiryYear());
        cardSource.setFirstName("John");
        cardSource.setLastName("Doe");


        final PaymentRequest<CardSource> request = PaymentRequest.fromDestination(cardSource, Currency.GBP, 100L);
        request.setReference(UUID.randomUUID().toString());
        final Processing processing = Processing.builder().senderInformation(
                SenderInformation.builder()
                        .firstName("Jane")
                        .lastName("Doe-Doe")
                        .address("1 Random Ave.")
                        .city("New York")
                        .country("US")
                        .postalCode("12345")
                        .state("New York")
                        .accountNumber("DE1234567890")
                        .reference("U1234567890")
                        .sourceOfFunds("Credit")
                        .build()
        ).build();
        request.setProcessing(processing);

        return request;
    }

    public static PaymentRequest<CardSource> createCardPaymentRequest(final Long amount) {
        final CardSource cardSource = new CardSource(TestCardSource.VISA.getNumber(), TestCardSource.VISA.getExpiryMonth(), TestCardSource.VISA.getExpiryYear());
        cardSource.setCvv(TestCardSource.VISA.getCvv());
        cardSource.setStored(false);

        final CustomerRequest customer = new CustomerRequest();
        customer.setEmail(generateRandomEmail());

        final PaymentRequest<CardSource> request = PaymentRequest.fromSource(cardSource, Currency.GBP, amount);
        request.setCapture(false);
        request.setCustomer(customer);
        request.setReference(UUID.randomUUID().toString());

        return request;
    }

    public static PaymentRequest<DLocalSource> createDLocalPaymentRequest(final Long amount) {
        final DLocalSource dlocalSource = new DLocalSource(TestCardSource.VISA.getNumber(), TestCardSource.VISA.getExpiryMonth(), TestCardSource.VISA.getExpiryYear());
        dlocalSource.setCvv(TestCardSource.VISA.getCvv());
        dlocalSource.setStored(false);

        final CustomerRequest customer = new CustomerRequest();
        customer.setEmail(generateRandomEmail());

        final PaymentRequest<DLocalSource> request = PaymentRequest.fromSource(dlocalSource, Currency.GBP, amount);
        request.setCapture(false);
        request.setCustomer(customer);
        request.setReference(UUID.randomUUID().toString());

        return request;
    }

    public static PaymentRequest<RequestSource> createAlternativePaymentMethodRequest(final RequestSource alternativePaymentMethodRequestSource, final String currency) {
        return createAlternativePaymentMethodRequest(alternativePaymentMethodRequestSource, currency, 100);
    }

    public static PaymentRequest<RequestSource> createAlternativePaymentMethodRequest(final RequestSource alternativePaymentMethodRequestSource, final String currency, final long amount) {
        final CustomerRequest customer = new CustomerRequest();
        customer.setEmail(generateRandomEmail());

        final PaymentRequest<RequestSource> request = PaymentRequest.fromSource(alternativePaymentMethodRequestSource, currency, amount);
        request.setCapture(false);
        request.setReference(UUID.randomUUID().toString());
        request.setCustomer(customer);

        return request;
    }

    public static CardTokenRequest createCardTokenRequest() {
        final CardTokenRequest request = new CardTokenRequest(TestCardSource.VISA.getNumber(), TestCardSource.VISA.getExpiryMonth(), TestCardSource.VISA.getExpiryYear());
        request.setCvv(TestCardSource.VISA.getCvv());
        return request;
    }

    public static String generateRandomEmail() {
        return UUID.randomUUID().toString() + "@checkout-sdk-java.com";
    }

    public static PaymentRequest<TokenSource> createTokenPaymentRequest(final String token) {
        final PaymentRequest<TokenSource> request = PaymentRequest.fromSource(new TokenSource(token), Currency.GBP, 100L);
        request.setCapture(false);
        return request;
    }

    public static CustomerRequest createCustomer() {
        return CustomerRequest.builder()
                .email(generateRandomEmail())
                .name("Jack Napier")
                .build();
    }

    public static Phone createPhone() {
        return Phone.builder()
                .countryCode("1")
                .number("4155552671")
                .build();
    }

    public static Address createAddress() {
        return Address.builder()
                .addressLine1("Checkout.com")
                .addressLine2("90 Tottenham Court Road")
                .city("London")
                .state("London")
                .zip("W1T 4TJ")
                .country("GB")
                .build();
    }

    public static Product createProduct() {
        return Product.builder()
                .name("Gold Necklace")
                .quantity(1L)
                .price(200L)
                .build();
    }

    public static ThreeDSRequest createThreeDS() {
        return ThreeDSRequest.builder()
                .enabled(Boolean.FALSE)
                .attemptN3D(Boolean.FALSE)
                .build();
    }

    public static PaymentLinkRequest createPaymentLinksRequest(final String reference){
        return PaymentLinkRequest.builder()
                .amount(200L)
                .currency(Currency.GBP)
                .reference(reference)
                .description("Payment for Gold Necklace")
                .expiresIn(604800)
                .customer(createCustomer())
                .billing(BillingInformation.builder()
                        .address(createAddress())
                        .phone(createPhone())
                        .build())
                .products(Collections.singletonList(createProduct()))
                .threeDS(createThreeDS())
                .risk(new RiskRequest(Boolean.FALSE))
                .returnUrl("https://example.com/success")
                .locale("en-GB")
                .build();
    }

    public static HostedPaymentRequest createHostedPaymentRequest(final String reference){
        return HostedPaymentRequest.builder()
                .amount(1000L)
                .reference(reference)
                .currency(Currency.GBP)
                .description("Payment for Gold Necklace")
                .customer(createCustomer())
                .billing(BillingInformation.builder()
                        .address(createAddress())
                        .phone(createPhone())
                        .build())
                .products(Collections.singletonList(createProduct()))
                .risk(new RiskRequest(Boolean.FALSE))
                .successUrl("https://example.com/payments/success")
                .cancelUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/success")
                .locale("en-GB")
                .threeDS(createThreeDS())
                .build();
    }

}