package com.checkout.customers;

import com.checkout.common.AccountHolder;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Phone;
import com.checkout.instruments.CardTokenInstrumentsTestIT;
import com.checkout.instruments.create.CreateInstrumentResponse;
import com.checkout.instruments.create.CreateInstrumentTokenRequest;
import com.checkout.instruments.get.GetCardInstrumentResponse;
import com.checkout.tokens.CardTokenResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.checkout.TestHelper.generateRandomEmail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomersTestIT extends CardTokenInstrumentsTestIT {

    @Test
    void shouldCreateAndGetCustomer() {
        final CustomerRequest customerRequest = createBasicCustomerRequest();

        final String customerId = blocking(() -> checkoutApi.customersClient().create(customerRequest)).getId();
        validateCustomerCreation(customerId);

        final CustomerResponse customerResponse = blocking(() -> checkoutApi.customersClient().get(customerId));
        validateBasicCustomerResponse(customerResponse, customerRequest);

        super.createTokenInstrument(customerId);

        final CustomerResponse customerWithInstruments = blocking(() -> checkoutApi.customersClient().get(customerId));
        validateCustomerWithInstruments(customerWithInstruments);
    }

    @Test
    @Disabled
    void shouldCreateAndUpdateCustomer() {
        //Create Customer
        final CustomerRequest customerRequest = createBasicCustomerRequest();
        final String customerId = blocking(() -> checkoutApi.customersClient().create(customerRequest)).getId();
        validateCustomerCreation(customerId);
        
        //Update Customer
        applyCustomerUpdates(customerRequest);
        blocking(() -> checkoutApi.customersClient().update(customerId, customerRequest));
        
        //Verify changes were applied
        final CustomerResponse customerResponse = blocking(() -> checkoutApi.customersClient().get(customerId));
        validateUpdatedCustomer(customerResponse, customerRequest);
    }

    @Test
    void shouldCreateAndEditCustomer() {
        //Create Customer
        final CustomerRequest customerRequest = createBasicCustomerRequest();
        final String customerId = blocking(() -> checkoutApi.customersClient().create(customerRequest)).getId();
        validateCustomerCreation(customerId);
        
        //Delete customer
        blocking(() -> checkoutApi.customersClient().delete(customerId));
        
        //Verify customer does not exist
        verifyCustomerDeleted(customerId);
    }

    @Test
    void shouldGetCustomerDetailsWithInstrument() {
        final CardTokenResponse cardToken = requestToken();
        final CreateInstrumentResponse tokenInstrument = createTokenInstrument(cardToken);
        final CustomerRequest customerRequest = createCustomerRequestWithDefaultId(tokenInstrument.getId());

        final String customerId = blocking(() -> checkoutApi.customersClient().create(customerRequest)).getId();

        final CustomerResponse customerResponse = blocking(() -> checkoutApi.customersClient().get(customerId));
        validateCustomerWithDefaultInstrument(customerResponse, tokenInstrument.getId());
    }

    // Synchronous method tests
    @Test
    void shouldCreateAndGetCustomerSync() {
        final CustomerRequest customerRequest = createBasicCustomerRequest();

        final String customerId = checkoutApi.customersClient().createSync(customerRequest).getId();
        validateCustomerCreation(customerId);

        final CustomerResponse customerResponse = checkoutApi.customersClient().getSync(customerId);
        validateBasicCustomerResponse(customerResponse, customerRequest);

        super.createTokenInstrument(customerId);

        final CustomerResponse customerWithInstruments = checkoutApi.customersClient().getSync(customerId);
        validateCustomerWithInstruments(customerWithInstruments);
    }

    @Test
    @Disabled
    void shouldCreateAndUpdateCustomerSync() {
        //Create Customer
        final CustomerRequest customerRequest = createBasicCustomerRequest();
        final String customerId = checkoutApi.customersClient().createSync(customerRequest).getId();
        validateCustomerCreation(customerId);
        
        //Update Customer
        applyCustomerUpdates(customerRequest);
        checkoutApi.customersClient().updateSync(customerId, customerRequest);
        
        //Verify changes were applied
        final CustomerResponse customerResponse = checkoutApi.customersClient().getSync(customerId);
        validateUpdatedCustomer(customerResponse, customerRequest);
    }

    @Test
    void shouldCreateAndEditCustomerSync() {
        //Create Customer
        final CustomerRequest customerRequest = createBasicCustomerRequest();
        final String customerId = checkoutApi.customersClient().createSync(customerRequest).getId();
        validateCustomerCreation(customerId);
        
        //Delete customer
        checkoutApi.customersClient().deleteSync(customerId);
        
        //Verify customer does not exist
        verifyCustomerDeleted(customerId);
    }

    @Test
    void shouldGetCustomerDetailsWithInstrumentSync() {
        final CardTokenResponse cardToken = requestToken();
        final CreateInstrumentResponse tokenInstrument = createTokenInstrument(cardToken);
        final CustomerRequest customerRequest = createCustomerRequestWithDefaultId(tokenInstrument.getId());

        final String customerId = checkoutApi.customersClient().createSync(customerRequest).getId();

        final CustomerResponse customerResponse = checkoutApi.customersClient().getSync(customerId);
        validateCustomerWithDefaultInstrument(customerResponse, tokenInstrument.getId());
    }

    // Common  methods
    private CreateInstrumentResponse createTokenInstrument(final CardTokenResponse token) {
        final CreateInstrumentTokenRequest request = CreateInstrumentTokenRequest.builder()
                .token(token.getToken())
                .accountHolder(AccountHolder.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .phone(Phone.builder()
                                .countryCode("+1")
                                .number("415 555 2671")
                                .build())
                        .billingAddress(Address.builder()
                                .addressLine1("CheckoutSdk.com")
                                .addressLine2("90 Tottenham Court Road")
                                .city("London")
                                .state("London")
                                .zip("W1T 4TJ")
                                .country(CountryCode.GB)
                                .build())
                        .build())
                .build();

        return blocking(() -> checkoutApi.instrumentsClient().create(request));
    }

    private CustomerRequest createBasicCustomerRequest() {
        return CustomerRequest.builder()
                .email(generateRandomEmail())
                .name("Testing")
                .phone(Phone.builder()
                        .countryCode("1")
                        .number("4155552671")
                        .build())
                .build();
    }

    private CustomerRequest createCustomerRequestWithDefaultId(String defaultId) {
        return CustomerRequest.builder()
                .email(generateRandomEmail())
                .name("Testing")
                .phone(Phone.builder()
                        .countryCode("1")
                        .number("4155552671")
                        .build())
                .DefaultId(defaultId)
                .build();
    }

    private void applyCustomerUpdates(CustomerRequest customerRequest) {
        customerRequest.setEmail(generateRandomEmail());
        customerRequest.setName("Testing New");
    }

    private void validateCustomerCreation(String customerId) {
        assertNotNull(customerId);
    }

    private void validateBasicCustomerResponse(CustomerResponse response, CustomerRequest request) {
        assertNotNull(response);
        assertEquals(request.getEmail(), response.getEmail());
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getPhone(), response.getPhone());
        assertNull(response.getDefaultId());
        assertTrue(response.getInstruments().isEmpty());
    }

    private void validateUpdatedCustomer(CustomerResponse response, CustomerRequest request) {
        assertNotNull(response);
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getEmail(), response.getEmail());
    }

    private void validateCustomerWithInstruments(CustomerResponse response) {
        assertEquals(1, response.getInstruments().size());
        assertTrue(response.getInstruments().get(0) instanceof GetCardInstrumentResponse);
    }

    private void validateCustomerWithDefaultInstrument(CustomerResponse response, String instrumentId) {
        assertNotNull(response);
        assertNotNull(response.getDefaultId());
        assertNotNull(response.getInstruments());
        assertEquals(response.getInstruments().get(0).getId(), instrumentId);
    }

    private void verifyCustomerDeleted(String customerId) {
        assertNotFound(checkoutApi.customersClient().get(customerId));
    }

}
