package com.checkout.risk;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.TestHelper;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.CustomerRequest;
import com.checkout.common.Phone;
import com.checkout.instruments.AccountHolder;
import com.checkout.instruments.CreateInstrumentRequest;
import com.checkout.instruments.CreateInstrumentResponse;
import com.checkout.CardSourceHelper;
import com.checkout.risk.preauthentication.PreAuthenticationAssessmentRequest;
import com.checkout.risk.preauthentication.PreAuthenticationAssessmentResponse;
import com.checkout.risk.precapture.AuthenticationResult;
import com.checkout.risk.precapture.AuthorizationResult;
import com.checkout.risk.precapture.PreCaptureAssessmentRequest;
import com.checkout.risk.precapture.PreCaptureAssessmentResponse;
import com.checkout.risk.source.CardSourcePrism;
import com.checkout.risk.source.CustomerSourcePrism;
import com.checkout.risk.source.IdSourcePrism;
import com.checkout.risk.source.RiskPaymentRequestSource;
import com.checkout.tokens.CardTokenRequest;
import com.checkout.tokens.CardTokenResponse;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PreAuthenticationCaptureTestIT extends SandboxTestFixture {

    public PreAuthenticationCaptureTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldPreAuthenticate_card() {

        final CardSourcePrism cardSourcePrism = CardSourcePrism.builder()
                .billingAddress(Address.builder()
                        .addressLine1("123 Street")
                        .addressLine2("Hollywood Avenue")
                        .city("Los Angeles")
                        .state("CA")
                        .zip("91001")
                        .country(CountryCode.US)
                        .build())
                .expiryMonth(CardSourceHelper.Visa.EXPIRY_MONTH)
                .expiryYear(CardSourceHelper.Visa.EXPIRY_YEAR)
                .number(CardSourceHelper.Visa.NUMBER)
                .phone(TestHelper.createPhone())
                .build();

        final PreAuthenticationAssessmentResponse authenticationAssessmentResponse = testAuthenticationAssessmentRequest(cardSourcePrism);

        testPreCaptureAssessmentRequest(cardSourcePrism, authenticationAssessmentResponse.getAssessmentId());

    }

    @Test
    void shouldPreAuthenticate_customer() {

        final com.checkout.customers.CustomerRequest customerRequest = com.checkout.customers.CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("User")
                .phone(TestHelper.createPhone())
                .build();

        final String customerId = blocking(defaultApi.customersClient().create(customerRequest)).getId();

        final CustomerSourcePrism customerSourcePrism = CustomerSourcePrism.builder()
                .id(customerId)
                .build();

        final PreAuthenticationAssessmentResponse authenticationAssessmentResponse = testAuthenticationAssessmentRequest(customerSourcePrism);

        testPreCaptureAssessmentRequest(customerSourcePrism, authenticationAssessmentResponse.getAssessmentId());

    }

    @Test
    void shouldPreAuthenticate_id() {

        final Address billingAddress = Address.builder()
                .addressLine1("CheckoutSdk.com")
                .addressLine2("90 Tottenham Court Road")
                .city("London")
                .state("London")
                .zip("W1T 4TJ")
                .country(CountryCode.GB)
                .build();

        final Phone phone = Phone.builder()
                .countryCode("44")
                .number("020 222333")
                .build();

        final CardTokenRequest cardTokenRequest = new CardTokenRequest(
                TestCardSource.VISA.getNumber(),
                TestCardSource.VISA.getExpiryMonth(),
                TestCardSource.VISA.getExpiryYear());
        cardTokenRequest.setCvv(TestCardSource.VISA.getCvv());
        cardTokenRequest.setBillingAddress(billingAddress);
        cardTokenRequest.setPhone(phone);

        final CardTokenResponse cardToken = blocking(defaultApi.tokensClient().requestAsync(cardTokenRequest));

        final CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type("token")
                .token(cardToken.getToken())
                .accountHolder(AccountHolder.builder()
                        .billingAddress(Address.builder()
                                .addressLine1("123 Street")
                                .addressLine2("Hollywood Avenue")
                                .city("Los Angeles")
                                .state("CA")
                                .zip("91001")
                                .country(CountryCode.US)
                                .build())
                        .phone(Phone.builder()
                                .countryCode("1")
                                .number("999555222")
                                .build())
                        .build())
                .build();

        final CreateInstrumentResponse response = blocking(defaultApi.instrumentsClient().createInstrument(request));

        final IdSourcePrism idSourcePrism = IdSourcePrism.builder()
                .id(response.getId()).cvv(TestCardSource.VISA.getCvv())
                .build();

        final PreAuthenticationAssessmentResponse authenticationAssessmentResponse = testAuthenticationAssessmentRequest(idSourcePrism);

        testPreCaptureAssessmentRequest(idSourcePrism, authenticationAssessmentResponse.getAssessmentId());

    }

    private PreAuthenticationAssessmentResponse testAuthenticationAssessmentRequest(final RiskPaymentRequestSource requestSource) {

        final PreAuthenticationAssessmentRequest request = PreAuthenticationAssessmentRequest.builder()
                .date(Instant.now())
                .source(requestSource)
                .customer(new CustomerRequest("id", TestHelper.generateRandomEmail(), "name"))
                .payment(RiskPayment.builder().psp("CheckoutSdk.com").id("78453878").build())
                .shipping(RiskShippingDetails.builder().address(
                        Address.builder()
                                .addressLine1("CheckoutSdk.com")
                                .addressLine2("90 Tottenham Court Road")
                                .city("London")
                                .state("London")
                                .zip("W1T 4TJ")
                                .country(CountryCode.GB)
                                .build()
                ).build())
                .reference("ORD-1011-87AH")
                .description("Set of 3 masks")
                .amount(6540L)
                .currency(Currency.GBP)
                .device(Device.builder()
                        .ip("90.197.169.245")
                        .location(Location.builder().longitude("0.1313").latitude("51.5107").build())
                        .type("Phone")
                        .os("ISO")
                        .model("iPhone X")
                        .date(Instant.now())
                        .userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
                        .fingerprint("34304a9e3fg09302")
                        .build())
                .metadata(Stream.of(
                        new AbstractMap.SimpleImmutableEntry<>("VoucherCode", "loyalty_10"),
                        new AbstractMap.SimpleImmutableEntry<>("discountApplied", "10"),
                        new AbstractMap.SimpleImmutableEntry<>("customer_id", "2190EF321"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .build();

        final PreAuthenticationAssessmentResponse response = blocking(defaultApi.riskClient().requestPreAuthenticationRiskScan(request));
        assertNotNull(response);

        assertNotNull(response.getAssessmentId());
        assertNotNull(response.getResult());
        assertNotNull(response.getResult().getDecision());
        assertNotNull(response.getLink("pre_capture"));
        assertNotNull(response.getLink("self"));
        assertNotNull(response.getLink("pre_capture").getLink());
        assertNotNull(response.getLink("self").getLink());

        return response;
    }

    private void testPreCaptureAssessmentRequest(final RiskPaymentRequestSource requestSource, final String assessmentId) {

        final PreCaptureAssessmentRequest request = PreCaptureAssessmentRequest.builder()
                .date(Instant.now())
                .source(requestSource)
                .customer(new CustomerRequest("id", TestHelper.generateRandomEmail(), "name"))
                .payment(RiskPayment.builder().psp("CheckoutSdk.com").id("78453878").build())
                .shipping(RiskShippingDetails.builder().address(
                        Address.builder()
                                .addressLine1("CheckoutSdk.com")
                                .addressLine2("90 Tottenham Court Road")
                                .city("London")
                                .state("London")
                                .zip("W1T 4TJ")
                                .country(CountryCode.GB)
                                .build()
                ).build())
                .amount(6540L)
                .currency(Currency.GBP)
                .device(Device.builder()
                        .ip("90.197.169.245")
                        .location(Location.builder().longitude("0.1313").latitude("51.5107").build())
                        .type("Phone")
                        .os("ISO")
                        .model("iPhone X")
                        .date(Instant.now())
                        .userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1")
                        .fingerprint("34304a9e3fg09302")
                        .build())
                .metadata(Stream.of(
                        new AbstractMap.SimpleImmutableEntry<>("VoucherCode", "loyalty_10"),
                        new AbstractMap.SimpleImmutableEntry<>("discountApplied", "10"),
                        new AbstractMap.SimpleImmutableEntry<>("customer_id", "2190EF321"))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                .authenticationResult(AuthenticationResult.builder()
                        .attempted(true)
                        .challenged(true)
                        .liabilityShifted(true)
                        .method("3ds")
                        .succeeded(true)
                        .version("2.0")
                        .build())
                .authorizationResult(AuthorizationResult.builder()
                        .avsCode("V")
                        .cvvResult("N")
                        .build())
                .build();

        final PreCaptureAssessmentResponse response = blocking(defaultApi.riskClient().requestPreCaptureRiskScan(request));

        assertNotNull(response);

        assertNotNull(response.getAssessmentId());
        assertNotNull(response.getLink("pre_capture"));
        assertNotNull(response.getLink("self"));
        assertNotNull(response.getLink("pre_capture").getLink());
        assertNotNull(response.getLink("self").getLink());
    }

}