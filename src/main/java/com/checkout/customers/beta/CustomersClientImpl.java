package com.checkout.customers.beta;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
import com.checkout.beta.AbstractClient;
import com.checkout.common.beta.IdResponse;

import java.util.concurrent.CompletableFuture;

public class CustomersClientImpl extends AbstractClient implements CustomersClient {

    public static final String CUSTOMERS_PATH = "/customers";

    public CustomersClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, SecretKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<CustomerResponse> get(final String customerId) {
        return apiClient.getAsync(getCustomersUrl(customerId), apiCredentials, CustomerResponse.class);
    }

    @Override
    public CompletableFuture<IdResponse> create(final CustomerRequest customerRequest) {
        return apiClient.postAsync(CUSTOMERS_PATH, apiCredentials, IdResponse.class, customerRequest, null);
    }

    @Override
    public CompletableFuture<Void> update(final String customerId, final CustomerRequest customerRequest) {
        return apiClient.patchAsync(getCustomersUrl(customerId), apiCredentials, Void.class, customerRequest, null);
    }

    @Override
    public CompletableFuture<Void> delete(final String customerId) {
        return apiClient.deleteAsync(getCustomersUrl(customerId), apiCredentials);
    }

    private String getCustomersUrl(final String customerId) {
        return buildPath(CUSTOMERS_PATH, customerId);
    }

}
