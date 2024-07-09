package com.checkout;

import com.checkout.common.AccountHolder;
import com.checkout.common.Address;
import com.checkout.common.ChallengeIndicator;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
import com.checkout.common.Product;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.Payer;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.contexts.PaymentContextsItems;
import com.checkout.payments.contexts.PaymentContextsProcessing;
import com.checkout.payments.contexts.PaymentContextsRequest;
import com.checkout.payments.hosted.HostedPaymentRequest;
import com.checkout.payments.links.PaymentLinkRequest;
import com.checkout.payments.request.PaymentCustomerRequest;
import com.checkout.payments.request.source.contexts.PaymentContextsKlarnaSource;
import com.checkout.payments.request.source.contexts.PaymentContextsPayPalSource;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class TestHelper {

    private TestHelper() {
    }

    // Previous
    public static final String VALID_PREVIOUS_SK = "sk_test_fde517a8-3f01-41ef-b4bd-4282384b0a64";
    public static final String VALID_PREVIOUS_PK = "pk_test_fe70ff27-7c32-4ce1-ae90-5691a188ee7b";
    public static final String INVALID_PREVIOUS_SK = "sk_test_asdsad3q4dq";
    public static final String INVALID_PREVIOUS_PK = "pk_test_q414dasds";

    // Default
    public static final String VALID_DEFAULT_SK = "sk_sbox_m73dzbpy7cf3gfd46xr4yj5xo4e";
    public static final String VALID_DEFAULT_PK = "pk_sbox_pkhpdtvmkgf7hdnpwnbhw7r2uic";
    public static final String INVALID_DEFAULT_SK = "sk_sbox_m73dzbpy7c-f3gfd46xr4yj5xo4e";
    public static final String INVALID_DEFAULT_PK = "pk_sbox_pkh";

    @SneakyThrows
    public static String getMock(final String mock) {
        return new String(Files.readAllBytes(Paths.get(TestHelper.class.getResource(mock).toURI())));
    }

    public static String generateRandomEmail() {
        return UUID.randomUUID().toString() + "@checkout-sdk-java.com";
    }

    public static CustomerRequest createCustomer() {
        return new CustomerRequest(null, generateRandomEmail(), "Jack Napier", null);
    }

    public static Phone createPhone() {
        return Phone.builder()
                .countryCode("+1")
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
                .price(1000L)
                .build();
    }

    public static ThreeDSRequest createThreeDS() {
        return ThreeDSRequest.builder()
                .enabled(Boolean.FALSE)
                .attemptN3D(Boolean.FALSE)
                .challengeIndicator(ChallengeIndicator.NO_CHALLENGE_REQUESTED)
                .build();
    }

    public static PaymentLinkRequest createPaymentLinksRequest(final String reference) {
        final Instant time = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        return PaymentLinkRequest.builder()
                .amount(1000L)
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
                .risk(RiskRequest.builder().enabled(false).build())
                .returnUrl("https://example.com/success")
                .locale("en-GB")
                .paymentType(PaymentType.REGULAR)
                .billingDescriptor(BillingDescriptor.builder()
                        .city("London")
                        .name("Awesome name")
                        .build())
                .allowPaymentMethods(Arrays.asList(PaymentSourceType.CARD, PaymentSourceType.IDEAL))
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
                .products(Collections.singletonList(createProduct()))
                .risk(RiskRequest.builder().enabled(false).build())
                .successUrl("https://example.com/payments/success")
                .cancelUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/success")
                .locale("en-GB")
                .threeDS(createThreeDS())
                .capture(true)
                .captureOn(time)
                .paymentType(PaymentType.REGULAR)
                .billingDescriptor(BillingDescriptor.builder()
                        .city("London")
                        .name("Awesome name")
                        .build())
                .allowPaymentMethods(Arrays.asList(PaymentSourceType.CARD, PaymentSourceType.IDEAL))
                .build();
    }

    public static PaymentContextsRequest createPaymentContextsPayPalRequest() {

        final PaymentContextsPayPalSource source = PaymentContextsPayPalSource.builder().build();

        final PaymentContextsItems item = PaymentContextsItems.builder()
                .name("mask")
                .quantity(1)
                .unitPrice(1000)
                .totalAmount(1000)
                .build();

        final List<PaymentContextsItems> items = Arrays.asList(
                item
        );

        final PaymentCustomerRequest customer = PaymentCustomerRequest.builder()
                .phone(createPhone())
                .build();

        return PaymentContextsRequest.builder()
                .source(source)
                .amount(1000L)
                .currency(Currency.EUR)
                .paymentType(PaymentType.REGULAR)
                .capture(true)
                .customer(customer)
                .processingChannelId(requireNonNull(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID")))
                .successUrl("https://example.com/payments/success")
                .failureUrl("https://example.com/payments/fail")
                .items(items)
                .build();
    }

    public static PaymentContextsRequest createPaymentContextsKlarnaRequest() {

        final PaymentContextsKlarnaSource source = PaymentContextsKlarnaSource.builder()
                .accountHolder(AccountHolder.builder()
                        .billingAddress(Address.builder()
                                .country(CountryCode.DE)
                                .build())
                        .build())
                .build();

        final PaymentContextsItems item = PaymentContextsItems.builder()
                .name("mask")
                .quantity(1)
                .unitPrice(1000)
                .totalAmount(1000)
                .build();

        final List<PaymentContextsItems> items = Arrays.asList(
                item
        );

        final PaymentContextsProcessing processing = PaymentContextsProcessing.builder()
                .locale("en-GB")
                .build();

        return PaymentContextsRequest.builder()
                .source(source)
                .amount(1000L)
                .currency(Currency.EUR)
                .paymentType(PaymentType.REGULAR)
                .processingChannelId(requireNonNull(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID")))
                .items(items)
                .processing(processing)
                .build();
    }

    public static PaymentRecipient createRecipient() {
        return PaymentRecipient.builder()
                .accountNumber("1234567")
                .country(CountryCode.ES)
                .dateOfBirth("1985-05-15")
                .firstName("IT")
                .lastName("TESTING")
                .zip("12345")
                .build();
    }

    public static Payer getPayer() {
        return Payer.builder()
                .name("Bruce Wayne")
                .email("bruce@wayne-enterprises.com")
                .document("53033315550")
                .build();
    }

    public static AccountHolder getAccountHolder() {
        return AccountHolder.builder()
                .firstName("John")
                .lastName("Doe")
                .email(generateRandomEmail())
                .phone(createPhone())
                .billingAddress(createAddress())
                .build();
    }

}