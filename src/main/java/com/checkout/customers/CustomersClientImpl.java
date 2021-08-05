package com.checkout.customers;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
import com.checkout.AbstractClient;
import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

public class CustomersClientImpl extends AbstractClient implements CustomersClient {

    public static final String CUSTOMERS = "/customers";

    public CustomersClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, new SecretKeyCredentials(configuration));
    }

    @Override
    public CompletableFuture<CustomerDetailsResponse> get(final String customerId) {
        return apiClient.getAsync(CUSTOMERS + "/" + customerId, apiCredentials, CustomerDetailsResponse.class);
    }

    @Override
    public CompletableFuture<IdResponse> create(final CustomerRequest customerRequest) {
        return apiClient.postAsync(CUSTOMERS, apiCredentials, IdResponse.class, customerRequest, null);
    }

    @Override
    public CompletableFuture<Void> update(final String customerId, final CustomerRequest customerRequest) {
        return apiClient.patchAsync(CUSTOMERS + "/" + customerId, apiCredentials, Void.class, customerRequest, null);
    }

    @Override
    public CompletableFuture<Void> delete(final String customerId) {
        return apiClient.deleteAsync(CUSTOMERS + "/" + customerId, apiCredentials);
    }

}
