package com.checkout.customers;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.TestHelper;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.InstrumentType;
import com.checkout.common.Phone;
import com.checkout.instruments.CreateInstrumentRequest;
import com.checkout.instruments.CreateInstrumentResponse;
import com.checkout.instruments.InstrumentCustomerRequest;
import com.checkout.instruments.InstrumentDetails;
import com.checkout.tokens.CardTokenRequest;
import com.checkout.tokens.CardTokenResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomersTestIT extends SandboxTestFixture {

    CustomersTestIT() {
        super(PlatformType.DEFAULT);
    }

    @Test
    void shouldCreateAndGetCustomer() {
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Customer")
                .phone(TestHelper.createPhone())
                .build();
        final String customerId = blocking(() -> defaultApi.customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        final CustomerDetailsResponse customerDetailsResponse = blocking(() -> defaultApi.customersClient().get(customerId));
        assertNotNull(customerDetailsResponse);
        assertEquals(customerRequest.getEmail(), customerDetailsResponse.getEmail());
        assertEquals(customerRequest.getName(), customerDetailsResponse.getName());
        assertEquals(customerRequest.getPhone(), customerDetailsResponse.getPhone());
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
        final String customerId = blocking(() -> defaultApi.customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        //Update Customer
        customerRequest.setEmail(TestHelper.generateRandomEmail());
        customerRequest.setName("Customer Changed");
        blocking(() -> defaultApi.customersClient().update(customerId, customerRequest));
        //Verify changes were applied
        final CustomerDetailsResponse customerDetailsResponse = blocking(() -> defaultApi.customersClient().get(customerId));
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
        final String customerId = blocking(() -> defaultApi.customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        //Delete customer
        blocking(() -> defaultApi.customersClient().delete(customerId));
        //Verify customer does not exist
        assertNotFound(defaultApi.customersClient().get(customerId));
    }

    @Test
    void shouldGetCustomerDetailsWithInstrument() {
        final CardTokenResponse cardToken = blocking(() -> defaultApi.tokensClient().requestCardToken(createValidTokenRequest()));

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

        final CreateInstrumentResponse instrumentResponse = blocking(() -> defaultApi.instrumentsClient().create(request));
        assertNotNull(instrumentResponse);
        assertNotNull(instrumentResponse.getCustomer());
        assertNotNull(instrumentResponse.getCustomer().getId());

        final CustomerDetailsResponse customerDetailsResponse = blocking(() -> defaultApi.customersClient().get(instrumentResponse.getCustomer().getId()));
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
