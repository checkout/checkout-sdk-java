package com.checkout;

import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.Phone;
import com.checkout.common.Product;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.CardSource;
import com.checkout.payments.DLocalSource;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.Processing;
import com.checkout.payments.RequestSource;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.SenderInformation;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.TokenSource;
import com.checkout.payments.four.request.PaymentRecipient;
import com.checkout.payments.four.request.ProcessingSettings;
import com.checkout.payments.hosted.HostedPaymentRequest;
import com.checkout.payments.links.PaymentLinkRequest;
import com.checkout.tokens.CardTokenRequest;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.UUID;

public final class TestHelper {

    private TestHelper() {
    }

    // Default
    public static final String VALID_DEFAULT_SK = "sk_test_fde517a8-3f01-41ef-b4bd-4282384b0a64";
    public static final String VALID_DEFAULT_PK = "pk_test_fe70ff27-7c32-4ce1-ae90-5691a188ee7b";
    public static final String INVALID_DEFAULT_SK = "sk_test_asdsad3q4dq";
    public static final String INVALID_DEFAULT_PK = "pk_test_q414dasds";

    // FOUR
    public static final String VALID_FOUR_SK = "sk_sbox_m73dzbpy7cf3gfd46xr4yj5xo4e";
    public static final String VALID_FOUR_PK = "pk_sbox_pkhpdtvmkgf7hdnpwnbhw7r2uic";
    public static final String INVALID_FOUR_SK = "sk_sbox_m73dzbpy7c-f3gfd46xr4yj5xo4e";
    public static final String INVALID_FOUR_PK = "pk_sbox_pkh";

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
                        .country(CountryCode.US)
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

        final CustomerRequest customer = new CustomerRequest(null, generateRandomEmail(), null);

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

        final CustomerRequest customer = new CustomerRequest(null, generateRandomEmail(), null);

        final PaymentRequest<DLocalSource> request = PaymentRequest.fromSource(dlocalSource, Currency.GBP, amount);
        request.setCapture(false);
        request.setCustomer(customer);
        request.setReference(UUID.randomUUID().toString());

        return request;
    }

    public static PaymentRequest<RequestSource> createAlternativePaymentMethodRequest(final RequestSource alternativePaymentMethodRequestSource, final Currency currency) {
        return createAlternativePaymentMethodRequest(alternativePaymentMethodRequestSource, currency, 100);
    }

    public static PaymentRequest<RequestSource> createAlternativePaymentMethodRequest(final RequestSource alternativePaymentMethodRequestSource, final Currency currency, final long amount) {

        final CustomerRequest customer = new CustomerRequest(null, generateRandomEmail(), null);

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
        return new CustomerRequest(null, generateRandomEmail(), "Jack Napier");
    }

    public static Phone createPhone() {
        return Phone.builder()
                .countryCode("1")
                .number("4155552671")
                .build();
    }

    public static Address createAddress() {
        return Address.builder()
                .addressLine1("CheckoutSdk.com")
                .addressLine2("90 Tottenham Court Road")
                .city("London")
                .state("London")
                .zip("W1T 4TJ")
                .country(CountryCode.GB)
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

    public static PaymentLinkRequest createPaymentLinksRequest(final String reference) {
        final Instant time = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        return PaymentLinkRequest.builder()
                .amount(200L)
                .currency(Currency.GBP)
                .reference(reference)
                .description("Payment for Gold Necklace")
                .expiresIn(604800)
                .customer(createCustomer())
                .shipping(ShippingDetails.builder()
                        .address(createAddress())
                        .phone(createPhone())
                        .build())
                .billing(BillingInformation.builder()
                        .address(createAddress())
                        .phone(createPhone())
                        .build())
                .recipient(createRecipient())
                .processing(ProcessingSettings.builder()
                        .aft(true)
                        .build())
                .capture(true)
                .captureOn(time)
                .products(Collections.singletonList(createProduct()))
                .threeDS(createThreeDS())
                .risk(new RiskRequest(Boolean.FALSE))
                .returnUrl("https://example.com/success")
                .locale("en-GB")
                .build();
    }

    public static HostedPaymentRequest createHostedPaymentRequest(final String reference) {
        final Instant time = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        return HostedPaymentRequest.builder()
                .amount(1000L)
                .reference(reference)
                .currency(Currency.GBP)
                .description("Payment for Gold Necklace")
                .customer(createCustomer())
                .shippingDetails(ShippingDetails.builder()
                        .address(createAddress())
                        .phone(createPhone())
                        .build())
                .billing(BillingInformation.builder()
                        .address(createAddress())
                        .phone(createPhone())
                        .build())
                .recipient(createRecipient())
                .processing(ProcessingSettings.builder()
                        .aft(true)
                        .build())
                .products(Collections.singletonList(createProduct()))
                .risk(new RiskRequest(Boolean.FALSE))
                .successUrl("https://example.com/payments/success")
                .cancelUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/success")
                .locale("en-GB")
                .threeDS(createThreeDS())
                .capture(true)
                .captureOn(time)
                .build();
    }

    public static PaymentRecipient createRecipient() {
        return PaymentRecipient.builder()
                .accountNumber("1234567")
                .country(CountryCode.ES)
                .dateOfBirth(LocalDate.of(1985, 5, 15))
                .firstName("IT")
                .lastName("TESTING")
                .zip("12345")
                .build();
    }

}