package com.checkout.customers;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorizationType;
import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class CustomersClientImpl extends AbstractClient implements CustomersClient {

    private static final String CUSTOMERS_PATH = "customers";

    public CustomersClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY_OR_OAUTH);
    }

    @Override
    public CompletableFuture<CustomerResponse> get(final String customerId) {
        validateCustomerId(customerId);
        return apiClient.getAsync(getCustomersIdPath(customerId), sdkAuthorization(), CustomerResponse.class);
    }

    @Override
    public CompletableFuture<IdResponse> create(final CustomerRequest customerRequest) {
        validateCustomerRequest(customerRequest);
        return apiClient.postAsync(CUSTOMERS_PATH, sdkAuthorization(), IdResponse.class, customerRequest, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> update(final String customerId, final CustomerRequest customerRequest) {
        validateCustomerIdAndRequest(customerId, customerRequest);
        return apiClient.patchAsync(getCustomersIdPath(customerId), sdkAuthorization(), EmptyResponse.class, customerRequest, null);
    }

    @Override
    public CompletableFuture<EmptyResponse> delete(final String customerId) {
        validateCustomerId(customerId);
        return apiClient.deleteAsync(getCustomersIdPath(customerId), sdkAuthorization());
    }

    // Synchronous methods
    @Override
    public CustomerResponse getSync(final String customerId) {
        validateCustomerId(customerId);
        return apiClient.get(getCustomersIdPath(customerId), sdkAuthorization(), CustomerResponse.class);
    }

    @Override
    public IdResponse createSync(final CustomerRequest customerRequest) {
        validateCustomerRequest(customerRequest);
        return apiClient.post(CUSTOMERS_PATH, sdkAuthorization(), IdResponse.class, customerRequest, null);
    }

    @Override
    public EmptyResponse updateSync(final String customerId, final CustomerRequest customerRequest) {
        validateCustomerIdAndRequest(customerId, customerRequest);
        return apiClient.patch(getCustomersIdPath(customerId), sdkAuthorization(), EmptyResponse.class, customerRequest, null);
    }

    @Override
    public EmptyResponse deleteSync(final String customerId) {
        validateCustomerId(customerId);
        return apiClient.delete(getCustomersIdPath(customerId), sdkAuthorization());
    }

    // Common methods
    protected void validateCustomerId(final String customerId) {
        validateParams("customerId", customerId);
    }

    protected void validateCustomerRequest(final CustomerRequest customerRequest) {
        validateParams("customerRequest", customerRequest);
    }

    protected void validateCustomerIdAndRequest(final String customerId, final CustomerRequest customerRequest) {
        validateParams("customerId", customerId, "customerRequest", customerRequest);
    }

    private String getCustomersIdPath(final String customerId) {
        return buildPath(CUSTOMERS_PATH, customerId);
    }

}
