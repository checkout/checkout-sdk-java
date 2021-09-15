package com.checkout.customers;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

public class CustomersClientImpl extends AbstractClient implements CustomersClient {

    private static final String CUSTOMERS = "/customers";

    public CustomersClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<CustomerDetailsResponse> get(final String customerId) {
        return apiClient.getAsync(buildPath(CUSTOMERS, customerId), sdkAuthorization(), CustomerDetailsResponse.class);
    }

    @Override
    public CompletableFuture<IdResponse> create(final CustomerRequest customerRequest) {
        return apiClient.postAsync(CUSTOMERS, sdkAuthorization(), IdResponse.class, customerRequest, null);
    }

    @Override
    public CompletableFuture<Void> update(final String customerId, final CustomerRequest customerRequest) {
        return apiClient.patchAsync(buildPath(CUSTOMERS, customerId), sdkAuthorization(), Void.class, customerRequest, null);
    }

    @Override
    public CompletableFuture<Void> delete(final String customerId) {
        return apiClient.deleteAsync(buildPath(CUSTOMERS, customerId), sdkAuthorization());
    }

}
