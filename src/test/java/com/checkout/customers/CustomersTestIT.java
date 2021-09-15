package com.checkout.customers;

import com.checkout.CheckoutResourceNotFoundException;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
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
        final String customerId = blocking(defaultApi.customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        final CustomerDetailsResponse customerDetailsResponse = blocking(defaultApi.customersClient().get(customerId));
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
        final String customerId = blocking(defaultApi.customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        //Update Customer
        customerRequest.setEmail(TestHelper.generateRandomEmail());
        customerRequest.setName("Customer Changed");
        blocking(defaultApi.customersClient().update(customerId, customerRequest));
        //Verify changes were applied
        final CustomerDetailsResponse customerDetailsResponse = blocking(defaultApi.customersClient().get(customerId));
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
        final String customerId = blocking(defaultApi.customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        //Delete customer
        blocking(defaultApi.customersClient().delete(customerId));
        //Verify customer does not exist
        try {
            defaultApi.customersClient().get(customerId).get();
        } catch (final Exception e) {
            assertTrue(e.getCause() instanceof CheckoutResourceNotFoundException);
        }
    }
}