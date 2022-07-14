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
    void shouldCreateCustomer() throws ExecutionException, InterruptedException {

        final CustomerRequest request = mock(CustomerRequest.class);
        final IdResponse response = mock(IdResponse.class);

        when(apiClient.postAsync(eq("customers"), eq(authorization), eq(IdResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<IdResponse> future = client.create(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetCustomers() throws ExecutionException, InterruptedException {

        final CustomerResponse response = mock(CustomerResponse.class);
        when(apiClient.getAsync("customers/customer_id", authorization,
                CustomerResponse.class))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CustomerResponse> future = client.get("customer_id");

        assertNotNull(future.get());

    }

    @Test
    void shouldUpdateInstrument() throws ExecutionException, InterruptedException {

        final CustomerRequest request = mock(CustomerRequest.class);
        final EmptyResponse response = mock(EmptyResponse.class);

        when(apiClient.patchAsync(eq("customers/customer_id"), eq(authorization),
                eq(EmptyResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = client.update("customer_id", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldDeleteInstrument() throws ExecutionException, InterruptedException {

        final EmptyResponse response = mock(EmptyResponse.class);

        when(apiClient.deleteAsync("customers/customer_id", authorization))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = client.delete("customer_id");

        assertNotNull(future.get());

    }
}