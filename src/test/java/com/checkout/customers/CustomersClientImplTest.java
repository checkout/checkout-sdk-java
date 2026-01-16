package com.checkout.customers;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.IdResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomersClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private CustomersClient client;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new CustomersClientImpl(apiClient, configuration);
    }

    @Test
    void shouldGetCustomer() throws ExecutionException, InterruptedException {
        final CustomerResponse expectedResponse = mock(CustomerResponse.class);

        when(apiClient.getAsync("customers/customer_id", authorization, CustomerResponse.class))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<CustomerResponse> future = client.get("customer_id");
        final CustomerResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCreateCustomer() throws ExecutionException, InterruptedException {
        final CustomerRequest request = createMockCustomerRequest();
        final IdResponse expectedResponse = mock(IdResponse.class);

        when(apiClient.postAsync(eq("customers"), eq(authorization), eq(IdResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<IdResponse> future = client.create(request);
        final IdResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldUpdateCustomer() throws ExecutionException, InterruptedException {
        final CustomerRequest request = createMockCustomerRequest();
        final EmptyResponse expectedResponse = mock(EmptyResponse.class);

        when(apiClient.patchAsync(eq("customers/customer_id"), eq(authorization),
                eq(EmptyResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<EmptyResponse> future = client.update("customer_id", request);
        final EmptyResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldDeleteCustomer() throws ExecutionException, InterruptedException {
        final EmptyResponse expectedResponse = mock(EmptyResponse.class);

        when(apiClient.deleteAsync("customers/customer_id", authorization))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<EmptyResponse> future = client.delete("customer_id");
        final EmptyResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    // Synchronous methods
    @Test
    void shouldGetCustomerSync() {
        final CustomerResponse expectedResponse = mock(CustomerResponse.class);
        
        when(apiClient.get("customers/customer_id", authorization, CustomerResponse.class))
                .thenReturn(expectedResponse);

        final CustomerResponse actualResponse = client.getSync("customer_id");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCreateCustomerSync() {
        final CustomerRequest request = createMockCustomerRequest();
        final IdResponse expectedResponse = mock(IdResponse.class);

        when(apiClient.post(eq("customers"), eq(authorization), eq(IdResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final IdResponse actualResponse = client.createSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldUpdateCustomerSync() {
        final CustomerRequest request = createMockCustomerRequest();
        final EmptyResponse expectedResponse = mock(EmptyResponse.class);

        when(apiClient.patch(eq("customers/customer_id"), eq(authorization), eq(EmptyResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final EmptyResponse actualResponse = client.updateSync("customer_id", request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldDeleteCustomerSync() {
        final EmptyResponse expectedResponse = mock(EmptyResponse.class);

        when(apiClient.delete("customers/customer_id", authorization))
                .thenReturn(expectedResponse);

        final EmptyResponse actualResponse = client.deleteSync("customer_id");

        validateResponse(expectedResponse, actualResponse);
    }

    // Common methods
    private CustomerRequest createMockCustomerRequest() {
        return mock(CustomerRequest.class);
    }

    private <T> void validateResponse(T expectedResponse, T actualResponse) {
        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse);
    }

}