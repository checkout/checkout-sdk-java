package com.checkout.customers;

import com.checkout.CheckoutResourceNotFoundException;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CustomersTestIT extends SandboxTestFixture {

    public CustomersTestIT() {
        super(PlatformType.CLASSIC);
    }

    @Test
    public void shouldCreateAndGetCustomer() {
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Armando Ibarra")
                .phone(TestHelper.createPhone())
                .build();
        final String customerId = blocking(getApi().customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        final CustomerDetailsResponse customerDetailsResponse = blocking(getApi().customersClient().get(customerId));
        assertNotNull(customerDetailsResponse);
        assertEquals(customerRequest.getEmail(), customerDetailsResponse.getEmail());
        assertEquals(customerRequest.getName(), customerDetailsResponse.getName());
        assertEquals(customerRequest.getPhone(), customerDetailsResponse.getPhone());
        assertNull(customerDetailsResponse.getDefaultId());
        assertTrue(customerDetailsResponse.getInstruments().isEmpty());
    }

    @Test
    public void shouldCreateAndUpdateCustomer() {
        //Create Customer
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Armando Ibarra")
                .phone(TestHelper.createPhone())
                .build();
        final String customerId = blocking(getApi().customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        //Update Customer
        customerRequest.setEmail(TestHelper.generateRandomEmail());
        customerRequest.setName("Armando Changed");
        blocking(getApi().customersClient().update(customerId, customerRequest));
        //Verify changes were applied
        final CustomerDetailsResponse customerDetailsResponse = blocking(getApi().customersClient().get(customerId));
        assertNotNull(customerDetailsResponse);
        assertEquals(customerRequest.getName(), customerDetailsResponse.getName());
        assertEquals(customerRequest.getEmail(), customerDetailsResponse.getEmail());
    }

    @Test
    public void shouldCreateAndEditCustomer() {
        //Create Customer
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Armando Ibarra")
                .phone(TestHelper.createPhone())
                .build();
        final String customerId = blocking(getApi().customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        //Delete customer
        blocking(getApi().customersClient().delete(customerId));
        //Verify customer does not exist
        try {
            getApi().customersClient().get(customerId).get();
        } catch (final Exception e) {
            assertTrue(e.getCause() instanceof CheckoutResourceNotFoundException);
        }
    }
}