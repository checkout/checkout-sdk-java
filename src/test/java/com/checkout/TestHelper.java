package com.checkout;

import com.checkout.common.AccountHolder;
import com.checkout.common.AccountHolderType;
import com.checkout.common.Address;
import com.checkout.common.AmountAllocations;
import com.checkout.common.ChallengeIndicator;
import com.checkout.common.Commission;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.Exemption;
import com.checkout.common.PaymentSourceType;
import com.checkout.common.Phone;
import com.checkout.common.Product;
import com.checkout.instruments.previous.InstrumentCustomerRequest;
import com.checkout.payments.Applepay;
import com.checkout.payments.BillingDescriptor;
import com.checkout.payments.BillingInformation;
import com.checkout.payments.Card;
import com.checkout.payments.Googlepay;
import com.checkout.payments.LocaleType;
import com.checkout.payments.Payer;
import com.checkout.payments.PaymentInstruction;
import com.checkout.payments.PaymentMethodConfiguration;
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.contexts.PaymentContextsCustomerRequest;
import com.checkout.payments.contexts.PaymentContextsItems;
import com.checkout.payments.contexts.PaymentContextsProcessing;
import com.checkout.payments.contexts.PaymentContextsRequest;
import com.checkout.payments.hosted.HostedPaymentRequest;
import com.checkout.payments.hosted.PaymentPurposeType;
import com.checkout.payments.links.PaymentLinkRequest;
import com.checkout.payments.request.PaymentRetryRequest;
import com.checkout.payments.request.source.contexts.PaymentContextsKlarnaSource;
import com.checkout.payments.request.source.contexts.PaymentContextsPayPalSource;
import com.checkout.payments.request.source.contexts.PaymentContextsStcpaySource;
import com.checkout.payments.request.source.contexts.PaymentContextsTabbySource;
import com.checkout.payments.sender.PaymentInstrumentSender;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public final class TestHelper {

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

    private TestHelper() {
    }

    @SneakyThrows
    public static String getMock(final String mock) {
        return new String(Files.readAllBytes(Paths.get(TestHelper.class.getResource(mock).toURI())));
    }

    public static String generateRandomEmail() {
        return UUID.randomUUID() + "@checkout-sdk-java.com";
    }

    public static CustomerRequest createCustomer() {
        return new CustomerRequest(generateRandomEmail(), "Jack Napier", null);
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
                .billing(BillingInformation.builder()
                        .address(createAddress())
                        .phone(createPhone())
                        .build())
                .paymentType(PaymentType.REGULAR)
                .paymentIp("127.0.0.1")
                .billingDescriptor(BillingDescriptor.builder()
                        .city("London")
                        .name("Awesome name")
                        .build())
                .reference(reference)
                .description("Payment for Gold Necklace")
                .displayName("Gold Necklace")
                .processingChannelId(requireNonNull(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID")))
                .amountAllocations(Collections.singletonList(AmountAllocations.builder()
                        .id("ent_w4jelhppmfiufdnatam37wrfc4")
                        .amount(1000L)
                        .reference("ORD-5023-4E89")
                        .commission(Commission.builder()
                                .amount(1000L)
                                .percentage(1.125)
                                .build())
                        .build()))
                .expiresIn(1)
                .customer(createCustomer())
                .shipping(ShippingDetails.builder()
                        .address(createAddress())
                        .phone(createPhone())
                        .build())
                .recipient(createRecipient())
                .processing(ProcessingSettings.builder()
                        .aft(true)
                        .build())
                .allowPaymentMethods(Arrays.asList(PaymentSourceType.CARD, PaymentSourceType.APPLEPAY, PaymentSourceType.GOOGLEPAY))
                .disabledPaymentMethods(Arrays.asList(PaymentSourceType.EPS, PaymentSourceType.IDEAL, PaymentSourceType.KNET))
                .products(Collections.singletonList(Product.builder()
                        .reference("string")
                        .name("Gold Necklace")
                        .quantity(1L)
                        .price(1000L)
                        .build()))
                .metadata(Collections.singletonMap("key", "value"))
                .threeDS(createThreeDS())
                .risk(RiskRequest.builder().enabled(false).build())
                .customerRetry(PaymentRetryRequest.builder()
                        .maxAttempts(3)
                        .build())
                .sender(PaymentInstrumentSender.builder()
                        .reference("8285282045818")
                        .build())
                .locale(LocaleType.EN_GB)
                .capture(true)
                .captureOn(time)
                .instruction(PaymentInstruction.builder()
                        .purpose(PaymentPurposeType.EDUCATION)
                        .build())
                .paymentMethodConfiguration(PaymentMethodConfiguration.builder()
                        .applepay(Applepay.builder()
                                .accountHolder(AccountHolder.builder()
                                        .firstName("John")
                                        .lastName("Jones")
                                        .type(AccountHolderType.INDIVIDUAL)
                                        .build())
                                .build())
                        .card(Card.builder()
                                .accountHolder(AccountHolder.builder()
                                        .firstName("John")
                                        .lastName("Jones")
                                        .type(AccountHolderType.INDIVIDUAL)
                                        .build())
                                .build())
                        .googlepay(Googlepay.builder()
                                .accountHolder(AccountHolder.builder()
                                        .firstName("John")
                                        .lastName("Jones")
                                        .type(AccountHolderType.INDIVIDUAL)
                                        .build())
                                .build())
                        .build())
                .build();
    }

    public static HostedPaymentRequest createHostedPaymentRequest(final String reference) {
        return HostedPaymentRequest.builder()
                .amount(1000L)
                .currency(Currency.GBP)
                .paymentType(PaymentType.REGULAR)
                .paymentIp("192.168.0.1")
                .billingDescriptor(BillingDescriptor.builder()
                        .name("The Jewelry Shop")
                        .city("London")
                        .reference("ORD-123A")
                        .build())
                .reference(reference)
                .description("Payment for Gold Necklace")
                .displayName("The Jewelry Shop")
                .processingChannelId(requireNonNull(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID")))
                .amountAllocations(Collections.singletonList(AmountAllocations.builder()
                        .id("ent_w4jelhppmfiufdnatam37wrfc4")
                        .amount(1000L)
                        .reference("ORD-5023-4E89")
                        .commission(Commission.builder()
                                .amount(1000L)
                                .percentage(1.125)
                                .build())
                        .build()))
                .customer(InstrumentCustomerRequest.builder()
                        .email("brucewayne@email.com")
                        .name("Bruce Wayne")
                        .build())
                .shipping(ShippingDetails.builder()
                        .address(createAddress())
                        .phone(createPhone())
                        .build())
                .billing(BillingInformation.builder()
                        .address(createAddress())
                        .phone(createPhone())
                        .build())
                .recipient(PaymentRecipient.builder()
                        .dateOfBirth(LocalDate.of(1985, 5, 15).toString())
                        .accountNumber("5555554444")
                        .address(createAddress())
                        .zip("SW1A")
                        .firstName("John")
                        .lastName("Jones")
                        .build())
                .processing(ProcessingSettings.builder()
                        .aft(true)
                        .build())
                .allowPaymentMethods(Arrays.asList(PaymentSourceType.CARD, PaymentSourceType.APPLEPAY, PaymentSourceType.GOOGLEPAY))
                .disabledPaymentMethods(Arrays.asList(PaymentSourceType.EPS, PaymentSourceType.IDEAL, PaymentSourceType.KNET))
                .products(Collections.singletonList(Product.builder()
                        .reference("string")
                        .name("Gold Necklace")
                        .quantity(1L)
                        .price(1000L)
                        .build()))
                .risk(RiskRequest.builder()
                        .enabled(false)
                        .build())
                .customerRetry(PaymentRetryRequest.builder()
                        .maxAttempts(2)
                        .build())
                .sender(PaymentInstrumentSender.builder()
                        .reference("8285282045818")
                        .build())
                .successUrl("https://example.com/payments/success")
                .cancelUrl("https://example.com/payments/cancel")
                .failureUrl("https://example.com/payments/failure")
                .locale(LocaleType.AR)
                .threeDS(ThreeDSRequest.builder()
                        .enabled(false)
                        .attemptN3D(false)
                        .challengeIndicator(ChallengeIndicator.NO_PREFERENCE)
                        .allowUpgrade(true)
                        .exemption(Exemption.LOW_VALUE)
                        .build())
                .capture(true)
                .captureOn(LocalDateTime.now().toInstant(ZoneOffset.UTC))
                .instruction(PaymentInstruction.builder()
                        .purpose(PaymentPurposeType.DONATIONS)
                        .build())
                .paymentMethodConfiguration(PaymentMethodConfiguration.builder()
                        .applepay(Applepay.builder()
                                .accountHolder(AccountHolder.builder()
                                        .firstName("John")
                                        .lastName("Jones")
                                        .type(AccountHolderType.INDIVIDUAL)
                                        .build())
                                .build())
                        .card(Card.builder()
                                .accountHolder(AccountHolder.builder()
                                        .firstName("John")
                                        .lastName("Jones")
                                        .type(AccountHolderType.INDIVIDUAL)
                                        .build())
                                .build())
                        .googlepay(Googlepay.builder()
                                .accountHolder(AccountHolder.builder()
                                        .firstName("John")
                                        .lastName("Jones")
                                        .type(AccountHolderType.INDIVIDUAL)
                                        .build())
                                .build())
                        .build())
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

        final PaymentContextsCustomerRequest customer = PaymentContextsCustomerRequest.builder()
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

    public static PaymentContextsRequest createPaymentContextsStcpayRequest() {

        final PaymentContextsStcpaySource source = PaymentContextsStcpaySource.builder().build();

        final PaymentContextsCustomerRequest customer = PaymentContextsCustomerRequest.builder()
                .phone(createPhone())
                .build();

        return PaymentContextsRequest.builder()
                .source(source)
                .amount(1000L)
                .reference("ORD-1011-87AH")
                .currency(Currency.SAR)
                .customer(customer)
                .processingChannelId(requireNonNull(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID")))
                .build();
    }

    public static PaymentContextsRequest createPaymentContextsTabbyRequest() {

        final PaymentContextsTabbySource source = PaymentContextsTabbySource.builder().build();

        final PaymentContextsCustomerRequest customer = PaymentContextsCustomerRequest.builder()
                .email(generateRandomEmail())
                .name("Bruce Wayne")
                .phone(createPhone())
                .build();

        return PaymentContextsRequest.builder()
                .source(source)
                .amount(1000L)
                .reference("ORD-1011-87AH")
                .currency(Currency.AED)
                .customer(customer)
                .processing(PaymentContextsProcessing.builder()
                        .locale("ar-AE")
                        .build())
                .processingChannelId(requireNonNull(System.getenv("CHECKOUT_PROCESSING_CHANNEL_ID")))
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