package com.checkout.customers;

import com.checkout.EmptyResponse;
import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

public interface CustomersClient {

    CompletableFuture<CustomerResponse> get(String customerId);

    CompletableFuture<IdResponse> create(CustomerRequest customerRequest);

    CompletableFuture<EmptyResponse> update(String customerId, CustomerRequest customerRequest);

    CompletableFuture<EmptyResponse> delete(String customerId);

    // Synchronous methods
    CustomerResponse getSync(String customerId);

    IdResponse createSync(CustomerRequest customerRequest);

    EmptyResponse updateSync(String customerId, CustomerRequest customerRequest);

    EmptyResponse deleteSync(String customerId);

}
