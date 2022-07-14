package com.checkout.customers;

import com.checkout.common.Phone;
import com.checkout.instruments.CardTokenInstrumentsTestIT;
import com.checkout.instruments.get.GetCardInstrumentResponse;
import org.junit.jupiter.api.Test;

import static com.checkout.TestHelper.generateRandomEmail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomersTestIT extends CardTokenInstrumentsTestIT {

    @Test
    void shouldCreateAndGetCustomer() {

        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(generateRandomEmail())
                .name("Testing")
                .phone(Phone.builder()
                        .countryCode("1")
                        .number("4155552671")
                        .build())
                .build();

        final String customerId = blocking(() -> checkoutApi.customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);

        final CustomerResponse customerResponse = blocking(() -> checkoutApi.customersClient().get(customerId));
        assertNotNull(customerResponse);
        assertEquals(customerRequest.getEmail(), customerResponse.getEmail());
        assertEquals(customerRequest.getName(), customerResponse.getName());
        assertEquals(customerRequest.getPhone(), customerResponse.getPhone());
        assertNull(customerResponse.getDefaultId());
        assertTrue(customerResponse.getInstruments().isEmpty());

        super.createTokenInstrument(customerId);

        final CustomerResponse customerWithInstruments = blocking(() -> checkoutApi.customersClient().get(customerId));
        assertEquals(1, customerWithInstruments.getInstruments().size());
        assertTrue(customerWithInstruments.getInstruments().get(0) instanceof GetCardInstrumentResponse);

    }

    @Test
    void shouldCreateAndUpdateCustomer() {
        //Create Customer
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(generateRandomEmail())
                .name("Testing")
                .phone(Phone.builder()
                        .countryCode("1")
                        .number("4155552671")
                        .build())
                .build();
        final String customerId = blocking(() -> checkoutApi.customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        //Update Customer
        customerRequest.setEmail(generateRandomEmail());
        customerRequest.setName("Testing New");
        blocking(() -> checkoutApi.customersClient().update(customerId, customerRequest));
        //Verify changes were applied
        final CustomerResponse customerResponse = blocking(() -> checkoutApi.customersClient().get(customerId));
        assertNotNull(customerResponse);
        assertEquals(customerRequest.getName(), customerResponse.getName());
        assertEquals(customerRequest.getEmail(), customerResponse.getEmail());
    }

    @Test
    void shouldCreateAndEditCustomer() {
        //Create Customer
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(generateRandomEmail())
                .name("Testing")
                .phone(Phone.builder()
                        .countryCode("1")
                        .number("4155552671")
                        .build())
                .build();
        final String customerId = blocking(() -> checkoutApi.customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        //Delete customer
        blocking(() -> checkoutApi.customersClient().delete(customerId));
        //Verify customer does not exist
        assertNotFound(checkoutApi.customersClient().get(customerId));

    }

}
