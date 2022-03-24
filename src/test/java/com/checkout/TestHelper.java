package com.checkout;

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
import com.checkout.payments.PaymentRecipient;
import com.checkout.payments.PaymentType;
import com.checkout.payments.ProcessingSettings;
import com.checkout.payments.RiskRequest;
import com.checkout.payments.ShippingDetails;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.hosted.HostedPaymentRequest;
import com.checkout.payments.links.PaymentLinkRequest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
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

    public static String generateRandomEmail() {
        return UUID.randomUUID().toString() + "@checkout-sdk-java.com";
    }

    public static CustomerRequest createCustomer() {
        return new CustomerRequest(null, generateRandomEmail(), "Jack Napier", null);
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
                .challengeIndicator(ChallengeIndicator.NO_CHALLENGE_REQUESTED)
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
                .risk(new RiskRequest(Boolean.FALSE))
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

}