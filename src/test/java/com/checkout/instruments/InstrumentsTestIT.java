package com.checkout.instruments;

import com.checkout.CheckoutResourceNotFoundException;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.common.Address;
import com.checkout.common.Phone;
import com.checkout.tokens.CardTokenRequest;
import com.checkout.tokens.CardTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InstrumentsTestIT extends SandboxTestFixture {

    private AccountHolder accountHolder;

    public InstrumentsTestIT() {
        super(PlatformType.CLASSIC);
    }

    @BeforeEach
    public void setUp() {
        accountHolder = AccountHolder.builder()
                .billingAddress(Address.builder()
                        .addressLine1("123 Street")
                        .addressLine2("Hollywood Avenue")
                        .city("Los Angeles")
                        .state("CA")
                        .zip("91001")
                        .country("US")
                        .build())
                .phone(Phone.builder()
                        .countryCode("1")
                        .number("999555222")
                        .build())
                .build();
    }

    @Test
    public void shouldCreateInstrument() {
        final CardTokenResponse cardToken = blocking(getApi().tokensClient().requestAsync(createValidTokenRequest()));

        final CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type("token")
                .token(cardToken.getToken())
                .accountHolder(accountHolder)
                .build();
        final CreateInstrumentResponse response = blocking(getApi().instrumentsClient().createInstrument(request));

        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getType());
        assertNotNull(response.getBin());
        assertNotNull(response.getCardCategory());
        assertNotNull(response.getCardType());
        assertNotNull(response.getExpiryMonth());
        assertNotNull(response.getExpiryYear());
        assertNotNull(response.getIssuer());
        assertNotNull(response.getIssuerCountry());
        assertNotNull(response.getProductId());
        assertNotNull(response.getProductType());
        assertNotNull(response.getCustomer());
        assertNotNull(response.getCustomer().getId());
        assertNotNull(response.getCustomer().getEmail());
    }

    @Test
    public void shouldGetInstrument() {
        final CardTokenResponse cardToken = blocking(getApi().tokensClient().requestAsync(createValidTokenRequest()));

        final CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type("token")
                .token(cardToken.getToken())
                .accountHolder(accountHolder)
                .build();
        final CreateInstrumentResponse createInstrumentResponse = blocking(getApi().instrumentsClient().createInstrument(request));

        final InstrumentDetailsResponse instrument = blocking(getApi().instrumentsClient().getInstrument(createInstrumentResponse.getId()));
        assertNotNull(instrument);
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
        assertNotNull(instrument.getFingerprint());
        assertNotNull(instrument.getAccountHolder());
        assertNotNull(instrument.getAccountHolder().getBillingAddress());
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
        assertTrue(instrument.getCustomer().isDefault());
    }

    @Test
    public void shouldUpdateInstrument(){
        final CardTokenResponse cardToken = blocking(getApi().tokensClient().requestAsync(createValidTokenRequest()));

        final CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type("token")
                .token(cardToken.getToken())
                .build();
        final CreateInstrumentResponse response = blocking(getApi().instrumentsClient().createInstrument(request));
        final UpdateInstrumentResponse updateResponse = blocking(getApi().instrumentsClient().updateInstrument(response.getId(), UpdateInstrumentRequest.builder()
                .name("Test")
                .build()));

        assertNotNull(updateResponse);
        assertNotNull(updateResponse.getFingerprint());
        assertNotNull(updateResponse.getType());

        final InstrumentDetailsResponse instrument = blocking(getApi().instrumentsClient().getInstrument(response.getId()));
        assertEquals("Test", instrument.getName());
    }

    @Test
    public void shouldDeleteInstrument() {
        final CardTokenResponse cardToken = blocking(getApi().tokensClient().requestAsync(createValidTokenRequest()));

        final CreateInstrumentRequest request = CreateInstrumentRequest.builder()
                .type("token")
                .token(cardToken.getToken())
                .build();
        final CreateInstrumentResponse response = blocking(getApi().instrumentsClient().createInstrument(request));

        try {
            getApi().instrumentsClient().getInstrument(response.getId()).get();
        } catch (final Exception e) {
            assertTrue(e.getCause() instanceof CheckoutResourceNotFoundException);
        }

    }

    private CardTokenRequest createValidTokenRequest() {
        final Address billingAddress = Address.builder()
                .addressLine1("Checkout.com")
                .addressLine2("90 Tottenham Court Road")
                .city("London")
                .state("London")
                .zip("W1T 4TJ")
                .country("GB")
                .build();

        final Phone phone = Phone.builder()
                .countryCode("44")
                .number("020 222333")
                .build();

        final CardTokenRequest request = new CardTokenRequest(TestCardSource.VISA.getNumber(), TestCardSource.VISA.getExpiryMonth(), TestCardSource.VISA.getExpiryYear());
        request.setCvv(TestCardSource.VISA.getCvv());
        request.setBillingAddress(billingAddress);
        request.setPhone(phone);

        return request;
    }
}