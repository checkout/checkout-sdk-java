package com.checkout.customers;

import com.checkout.ApiClient;
import com.checkout.ApiCredentials;
import com.checkout.CheckoutConfiguration;
import com.checkout.CheckoutResourceNotFoundException;
import com.checkout.TestHelper;
import com.checkout.common.IdResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomersClientImplTest {

    private static String CUSTOMER_ID = "cus_123456789abcdefgh";

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private CustomerDetailsResponse customerDetailsResponse;

    @Mock
    private IdResponse idResponse;

    @Mock
    private Void voidResponse;

    @Mock
    private CompletableFuture<CustomerDetailsResponse> customerDetailsAsync;

    @Mock
    private CompletableFuture<IdResponse> idAsync;

    @Mock
    private CompletableFuture<Void> voidAsync;

    private CustomersClient client;

    @Before
    public void setUp() {
        when(idResponse.getId()).thenReturn(CUSTOMER_ID);
        idAsync = CompletableFuture.completedFuture(idResponse);
        customerDetailsAsync = CompletableFuture.completedFuture(customerDetailsResponse);
        voidAsync = CompletableFuture.completedFuture(voidResponse);
        client = new CustomersClientImpl(apiClient, configuration);
    }

    @Test
    public void shouldCreateAndGetCustomer() throws ExecutionException, InterruptedException {
        doReturn(idAsync)
                .when(apiClient).postAsync(eq(CustomersClientImpl.CUSTOMERS), any(ApiCredentials.class),
                eq(IdResponse.class), any(CustomerRequest.class), any());
        doReturn(customerDetailsAsync)
                .when(apiClient)
                .getAsync(eq(CustomersClientImpl.CUSTOMERS + "/" + CUSTOMER_ID), any(ApiCredentials.class),
                        eq(CustomerDetailsResponse.class));
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Armando Ibarra")
                .phone(TestHelper.createPhone())
                .build();
        when(customerDetailsResponse.getEmail()).thenReturn(customerRequest.getEmail());
        when(customerDetailsResponse.getName()).thenReturn(customerRequest.getName());
        when(customerDetailsResponse.getPhone()).thenReturn(customerRequest.getPhone());
        final String customerId = client.create(customerRequest).get().getId();
        assertNotNull(customerId);
        final CustomerDetailsResponse customerDetailsResponse = client.get(customerId).get();
        assertNotNull(customerDetailsResponse);
        assertEquals(customerRequest.getEmail(), customerDetailsResponse.getEmail());
        assertEquals(customerRequest.getName(), customerDetailsResponse.getName());
        assertEquals(customerRequest.getPhone(), customerDetailsResponse.getPhone());
        assertNull(customerDetailsResponse.getDefaultId());
        assertTrue(customerDetailsResponse.getInstruments().isEmpty());
    }

    @Test
    public void shouldCreateAndUpdateCustomer() throws ExecutionException, InterruptedException {
        doReturn(idAsync)
                .when(apiClient).postAsync(eq(CustomersClientImpl.CUSTOMERS), any(ApiCredentials.class),
                eq(IdResponse.class), any(CustomerRequest.class), any());
        doReturn(customerDetailsAsync)
                .when(apiClient)
                .getAsync(eq(CustomersClientImpl.CUSTOMERS + "/" + CUSTOMER_ID), any(ApiCredentials.class),
                        eq(CustomerDetailsResponse.class));
        doReturn(voidAsync)
                .when(apiClient)
                .patchAsync(eq(CustomersClientImpl.CUSTOMERS + "/" + CUSTOMER_ID), any(ApiCredentials.class),
                        eq(Void.class), any(CustomerRequest.class), any());
        //Create Customer
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Armando Ibarra")
                .phone(TestHelper.createPhone())
                .build();
        final String customerId = client.create(customerRequest).get().getId();
        assertNotNull(customerId);
        //Update Customer
        customerRequest.setEmail(TestHelper.generateRandomEmail());
        customerRequest.setName("Armando Changed");
        when(customerDetailsResponse.getEmail()).thenReturn(customerRequest.getEmail());
        when(customerDetailsResponse.getName()).thenReturn(customerRequest.getName());
        client.update(customerId, customerRequest).get();
        //Verify changes were applied
        final CustomerDetailsResponse customerDetailsResponse = client.get(customerId).get();
        assertNotNull(customerDetailsResponse);
        assertEquals(customerRequest.getName(), customerDetailsResponse.getName());
        assertEquals(customerRequest.getEmail(), customerDetailsResponse.getEmail());
    }

    @Test
    public void shouldCreateAndEditCustomer() throws ExecutionException, InterruptedException {
        doReturn(idAsync)
                .when(apiClient).postAsync(eq(CustomersClientImpl.CUSTOMERS), any(ApiCredentials.class),
                eq(IdResponse.class), any(CustomerRequest.class), any());
        doThrow(new CheckoutResourceNotFoundException("Customer not found"))
                .when(apiClient)
                .getAsync(eq(CustomersClientImpl.CUSTOMERS + "/" + CUSTOMER_ID), any(ApiCredentials.class),
                        eq(CustomerDetailsResponse.class));
        doReturn(voidAsync)
                .when(apiClient)
                .deleteAsync(eq(CustomersClientImpl.CUSTOMERS + "/" + CUSTOMER_ID), any(ApiCredentials.class));
        //Create Customer
        final CustomerRequest customerRequest = CustomerRequest.builder()
                .email(TestHelper.generateRandomEmail())
                .name("Armando Ibarra")
                .phone(TestHelper.createPhone())
                .build();
        final String customerId = client.create(customerRequest).get().getId();
        assertNotNull(customerId);
        //Delete customer
        client.delete(customerId).get();
        //Verify customer does not exist
        try {
            client.get(customerId);
            Assert.fail();
        } catch (final CheckoutResourceNotFoundException ex) {
            //do nothing
        }
    }
}