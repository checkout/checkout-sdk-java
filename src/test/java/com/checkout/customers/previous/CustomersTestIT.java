package com.checkout.customers.previous;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.TestHelper;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.InstrumentType;
import com.checkout.common.Phone;
import com.checkout.instruments.previous.CreateInstrumentRequest;
import com.checkout.instruments.previous.CreateInstrumentResponse;
import com.checkout.instruments.previous.InstrumentCustomerRequest;
import com.checkout.instruments.previous.InstrumentDetails;
import com.checkout.tokens.CardTokenRequest;
import com.checkout.tokens.CardTokenResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomersTestIT extends SandboxTestFixture {

    CustomersTestIT() {
        super(PlatformType.PREVIOUS);
    }

    @Test
    void shouldCreateAndGetCustomer() {
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Customer")
                .phone(TestHelper.createPhone())
                .build();
        final String customerId = blocking(() -> previousApi.customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        final CustomerDetailsResponse customerDetailsResponse = blocking(() -> previousApi.customersClient().get(customerId));
        assertNotNull(customerDetailsResponse);
        assertEquals(customerRequest.getEmail(), customerDetailsResponse.getEmail());
        assertEquals(customerRequest.getName(), customerDetailsResponse.getName());
        assertNull(customerDetailsResponse.getDefaultId());
        assertTrue(customerDetailsResponse.getInstruments().isEmpty());
    }

    @Test
    void shouldCreateAndUpdateCustomer() {
        //Create Customer
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Customer")
                .phone(TestHelper.createPhone())
                .build();
        final String customerId = blocking(() -> previousApi.customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        //Update Customer
        customerRequest.setEmail(TestHelper.generateRandomEmail());
        customerRequest.setName("Customer Changed");
        blocking(() -> previousApi.customersClient().update(customerId, customerRequest));
        //Verify changes were applied
        final CustomerDetailsResponse customerDetailsResponse = blocking(() -> previousApi.customersClient().get(customerId));
        assertNotNull(customerDetailsResponse);
        assertEquals(customerRequest.getName(), customerDetailsResponse.getName());
        assertEquals(customerRequest.getEmail(), customerDetailsResponse.getEmail());
    }

    @Test
    void shouldCreateAndEditCustomer() {
        //Create Customer
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Customer")
                .phone(TestHelper.createPhone())
                .build();
        final String customerId = blocking(() -> previousApi.customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        //Delete customer
        blocking(() -> previousApi.customersClient().delete(customerId));
        //Verify customer does not exist
        assertNotFound(previousApi.customersClient().get(customerId));
    }

    @Test
    @Disabled("unavailable")
    void shouldGetCustomerDetailsWithInstrument() {
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

        final CreateInstrumentResponse instrumentResponse = blocking(() -> previousApi.instrumentsClient().create(request));
        assertNotNull(instrumentResponse);
        assertNotNull(instrumentResponse.getCustomer());
        assertNotNull(instrumentResponse.getCustomer().getId());

        final CustomerDetailsResponse customerDetailsResponse = blocking(() -> previousApi.customersClient().get(instrumentResponse.getCustomer().getId()));
        assertNotNull(customerDetailsResponse);
        assertEquals(1, customerDetailsResponse.getInstruments().size());
        final InstrumentDetails instrumentDetails = customerDetailsResponse.getInstruments().get(0);
        assertEquals(instrumentResponse.getId(), instrumentDetails.getId());
        assertEquals(instrumentResponse.getType(), instrumentDetails.getType());
        assertEquals(instrumentResponse.getFingerprint(), instrumentDetails.getFingerprint());
        assertEquals(instrumentResponse.getExpiryMonth(), instrumentDetails.getExpiryMonth());
        assertEquals(instrumentResponse.getExpiryYear(), instrumentDetails.getExpiryYear());
        //assertEquals(instrumentResponse.getScheme(), instrumentDetails.getScheme());
        assertEquals(instrumentResponse.getLast4(), instrumentDetails.getLast4());
        assertEquals(instrumentResponse.getBin(), instrumentDetails.getBin());
        assertEquals(instrumentResponse.getCardType(), instrumentDetails.getCardType());
        assertEquals(instrumentResponse.getIssuer(), instrumentDetails.getIssuer());
        assertEquals(instrumentResponse.getIssuerCountry(), instrumentDetails.getIssuerCountry());
        assertEquals(instrumentResponse.getProductId(), instrumentDetails.getProductId());
        assertEquals(instrumentResponse.getProductType(), instrumentDetails.getProductType());
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
                .number("020 222333")
                .build();

        return CardTokenRequest.builder()
                .number(TestCardSource.VISA.getNumber())
                .expiryMonth(TestCardSource.VISA.getExpiryMonth())
                .expiryYear(TestCardSource.VISA.getExpiryYear())
                .cvv(TestCardSource.VISA.getCvv())
                .billingAddress(billingAddress)
                .phone(phone)
                .build();
    }

}
