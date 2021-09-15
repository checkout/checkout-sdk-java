package com.checkout.customers.four;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.four.IdResponse;

import java.util.concurrent.CompletableFuture;

public class CustomersClientImpl extends AbstractClient implements CustomersClient {

    public static final String CUSTOMERS_PATH = "/customers";

    public CustomersClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<CustomerResponse> get(final String customerId) {
        return apiClient.getAsync(getCustomersUrl(customerId), sdkAuthorization(), CustomerResponse.class);
    }

    @Override
    public CompletableFuture<IdResponse> create(final CustomerRequest customerRequest) {
        return apiClient.postAsync(CUSTOMERS_PATH, sdkAuthorization(), IdResponse.class, customerRequest, null);
    }

    @Override
    public CompletableFuture<Void> update(final String customerId, final CustomerRequest customerRequest) {
        return apiClient.patchAsync(getCustomersUrl(customerId), sdkAuthorization(), Void.class, customerRequest, null);
    }

    @Override
    public CompletableFuture<Void> delete(final String customerId) {
        return apiClient.deleteAsync(getCustomersUrl(customerId), sdkAuthorization());
    }

    private String getCustomersUrl(final String customerId) {
        return buildPath(CUSTOMERS_PATH, customerId);
    }

}
