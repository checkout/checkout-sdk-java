package com.checkout.customers.beta;

import com.checkout.CheckoutResourceNotFoundException;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.common.beta.Phone;
import org.junit.jupiter.api.Test;

import static com.checkout.beta.TestHelper.getRandomEmail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomersTestIT extends SandboxTestFixture {

    public CustomersTestIT() {
        super(PlatformType.FOUR);
    }

    @Test
    public void shouldCreateAndGetCustomer() {

        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(getRandomEmail())
                .name("Testing")
                .phone(Phone.builder()
                        .countryCode("1")
                        .number("4155552671")
                        .build())
                .build();

        final String customerId = blocking(getApiV2().customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);

        final CustomerResponse customerResponse = blocking(getApiV2().customersClient().get(customerId));
        assertNotNull(customerResponse);
        assertEquals(customerRequest.getEmail(), customerResponse.getEmail());
        assertEquals(customerRequest.getName(), customerResponse.getName());
        assertEquals(customerRequest.getPhone(), customerResponse.getPhone());
        assertNull(customerResponse.getDefaultId());
        // TODO Review customer instruments when related functionality is implemented for CS2
        //assertTrue(customerResponse.getInstruments().isEmpty());

    }

    @Test
    public void shouldCreateAndUpdateCustomer() {
        //Create Customer
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(getRandomEmail())
                .name("Testing")
                .phone(Phone.builder()
                        .countryCode("1")
                        .number("4155552671")
                        .build())
                .build();
        final String customerId = blocking(getApiV2().customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        //Update Customer
        customerRequest.setEmail(getRandomEmail());
        customerRequest.setName("Testing New");
        blocking(getApiV2().customersClient().update(customerId, customerRequest));
        //Verify changes were applied
        final CustomerResponse customerResponse = blocking(getApiV2().customersClient().get(customerId));
        assertNotNull(customerResponse);
        assertEquals(customerRequest.getName(), customerResponse.getName());
        assertEquals(customerRequest.getEmail(), customerResponse.getEmail());
    }

    @Test
    public void shouldCreateAndEditCustomer() {
        //Create Customer
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(getRandomEmail())
                .name("Testing")
                .phone(Phone.builder()
                        .countryCode("1")
                        .number("4155552671")
                        .build())
                .build();
        final String customerId = blocking(getApiV2().customersClient().create(customerRequest)).getId();
        assertNotNull(customerId);
        //Delete customer
        blocking(getApiV2().customersClient().delete(customerId));
        //Verify customer does not exist
        try {
            getApiV2().customersClient().get(customerId).get();
        } catch (final Exception e) {
            assertTrue(e.getCause() instanceof CheckoutResourceNotFoundException);
        }
    }
}