package com.checkout.instruments.previous;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.InstrumentType;
import com.checkout.common.Phone;
import com.checkout.tokens.CardTokenRequest;
import com.checkout.tokens.CardTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("unavailable")
class InstrumentsTestIT extends SandboxTestFixture {

    private InstrumentAccountHolder accountHolder;

    InstrumentsTestIT() {
        super(PlatformType.PREVIOUS);
    }

    @BeforeEach
    void setUp() {
        accountHolder = InstrumentAccountHolder.builder()
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
                .build();
    }

    @Test
    @Disabled("unavailable")
    void shouldCreateInstrument() {
        final CardTokenResponse cardToken = blocking(() -> previousApi.tokensClient().requestCardToken(createValidTokenRequest()));

        final CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type(InstrumentType.TOKEN)
                .token(cardToken.getToken())
                .customer(InstrumentCustomerRequest.builder()
                        .email("instrumentcustomer@checkout.com")
                        .name("Instrument Customer")
                        .phone(Phone.builder().countryCode("+1").number("4155552671").build())
                        .isDefault(true)
                        .build())
                .build();

        final CreateInstrumentResponse response = blocking(() -> previousApi.instrumentsClient().create(request));

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getType());
        assertNotNull(response.getBin());
        assertNotNull(response.getCardCategory());
        assertNotNull(response.getCardType());
        assertNotNull(response.getExpiryMonth());
        assertNotNull(response.getExpiryYear());
        //assertNotNull(response.getIssuer());
        assertNotNull(response.getIssuerCountry());
        assertNotNull(response.getProductId());
        assertNotNull(response.getProductType());
        assertNotNull(response.getCustomer());
        assertNotNull(response.getCustomer().getId());
        assertNotNull(response.getCustomer().getEmail());
        assertNotNull(response.getCustomer());
        assertNotNull(response.getCustomer().getId());
        assertEquals(request.getCustomer().getName(), response.getCustomer().getName());
        assertEquals(request.getCustomer().getEmail(), response.getCustomer().getEmail());
    }

    @Test
    @Disabled("unavailable")
    void shouldGetInstrument() {

        final CardTokenResponse cardToken = blocking(() -> previousApi.tokensClient().requestCardToken(createValidTokenRequest()));

        final CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type(InstrumentType.TOKEN)
                .token(cardToken.getToken())
                .accountHolder(accountHolder)
                .build();

        final CreateInstrumentResponse createInstrumentResponse = blocking(() -> previousApi.instrumentsClient().create(request));

        final InstrumentDetailsResponse instrument = blocking(() -> previousApi.instrumentsClient().get(createInstrumentResponse.getId()));

        assertNotNull(instrument);
        assertNotNull(instrument.getCustomer());
        assertNotNull(instrument.getCustomer().getId());
        assertNotNull(instrument.getCustomer().getEmail());
        assertTrue(instrument.getCustomer().isDefault());

        assertEquals(createInstrumentResponse.getBin(), instrument.getBin());
        assertEquals(createInstrumentResponse.getProductType(), instrument.getProductType());
        assertEquals(createInstrumentResponse.getProductId(), instrument.getProductId());
        assertEquals(createInstrumentResponse.getId(), instrument.getId());
        assertEquals(createInstrumentResponse.getType(), instrument.getType());
        assertEquals(createInstrumentResponse.getExpiryMonth(), instrument.getExpiryMonth());
        assertEquals(createInstrumentResponse.getExpiryYear(), instrument.getExpiryYear());
        assertEquals(createInstrumentResponse.getScheme(), instrument.getScheme());
        assertEquals(createInstrumentResponse.getLast4(), instrument.getLast4());
        assertEquals(createInstrumentResponse.getBin(), instrument.getBin());
        assertEquals(createInstrumentResponse.getCardType(), instrument.getCardType());
        assertEquals(createInstrumentResponse.getCardCategory(), instrument.getCardCategory());
        assertEquals(createInstrumentResponse.getIssuer(), instrument.getIssuer());
        assertEquals(createInstrumentResponse.getIssuerCountry(), instrument.getIssuerCountry());
        assertNotNull(createInstrumentResponse.getCustomer());
        assertNotNull(createInstrumentResponse.getCustomer().getId());
        assertNotNull(createInstrumentResponse.getCustomer().getEmail());
        assertEquals(accountHolder.getBillingAddress().getAddressLine1(), instrument.getAccountHolder().getBillingAddress().getAddressLine1());
        assertEquals(accountHolder.getBillingAddress().getAddressLine2(), instrument.getAccountHolder().getBillingAddress().getAddressLine2());
        assertEquals(accountHolder.getBillingAddress().getCity(), instrument.getAccountHolder().getBillingAddress().getCity());
        assertEquals(accountHolder.getBillingAddress().getCountry(), instrument.getAccountHolder().getBillingAddress().getCountry());
        assertEquals(accountHolder.getBillingAddress().getState(), instrument.getAccountHolder().getBillingAddress().getState());
        assertEquals(accountHolder.getBillingAddress().getZip(), instrument.getAccountHolder().getBillingAddress().getZip());
        assertNotNull(instrument.getAccountHolder().getPhone());
        assertEquals(accountHolder.getPhone().getCountryCode(), instrument.getAccountHolder().getPhone().getCountryCode());
        assertEquals(accountHolder.getPhone().getNumber(), instrument.getAccountHolder().getPhone().getNumber());
        assertNotNull(instrument.getCustomer());
        assertNotNull(instrument.getCustomer().getId());
        assertNotNull(instrument.getCustomer().getEmail());

    }

    @Test
    @Disabled("unavailable")
    void shouldUpdateInstrument() {
        final CardTokenResponse cardToken = blocking(() -> previousApi.tokensClient().requestCardToken(createValidTokenRequest()));

        final CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type(InstrumentType.TOKEN)
                .token(cardToken.getToken())
                .build();
        final CreateInstrumentResponse response = blocking(() -> previousApi.instrumentsClient().create(request));
        final UpdateInstrumentResponse updateResponse = blocking(() -> previousApi.instrumentsClient().update(response.getId(), UpdateInstrumentRequest.builder()
                .name("Test")
                .build()));

        assertNotNull(updateResponse);
        assertNotNull(updateResponse.getFingerprint());
        assertNotNull(updateResponse.getType());

        final InstrumentDetailsResponse instrument = blocking(() -> previousApi.instrumentsClient().get(response.getId()));
        assertEquals("Test", instrument.getName());
    }

    @Test
    @Disabled("unavailable")
    void shouldDeleteInstrument() {

        final CardTokenResponse cardToken = blocking(() -> previousApi.tokensClient().requestCardToken(createValidTokenRequest()));

        final CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type(InstrumentType.TOKEN)
                .token(cardToken.getToken())
                .build();

        final CreateInstrumentResponse response = blocking(() -> previousApi.instrumentsClient().create(request));

        blocking(() -> previousApi.instrumentsClient().delete(response.getId()));

        assertNotFound(previousApi.instrumentsClient().get(response.getId()));

    }

    private CardTokenRequest createValidTokenRequest() {
        final Address billingAddress = Address.builder()
                .addressLine1("Checkout.com")
                .addressLine2("90 Tottenham Court Road")
                .city("London")
                .state("London")
                .zip("W1T 4TJ")
                .country(CountryCode.GB)
                .build();

        final Phone phone = Phone.builder()
                .countryCode("44")
                .number("020222333")
                .build();

        return CardTokenRequest.builder()
                .number(TestCardSource.VISA.getNumber())
                .expiryMonth(TestCardSource.VISA.getExpiryMonth())
                .expiryYear(TestCardSource.VISA.getExpiryYear())
                .cvv(TestCardSource.VISA.getCvv())
                .name("Checkout Test")
                .billingAddress(billingAddress)
                .phone(phone)
                .build();
    }
}
