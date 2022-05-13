package com.checkout.customers;

import com.checkout.EmptyResponse;
import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

public interface CustomersClient {

    CompletableFuture<CustomerDetailsResponse> get(String customerId);

    CompletableFuture<IdResponse> create(CustomerRequest customerRequest);

    CompletableFuture<EmptyResponse> update(String customerId, CustomerRequest customerRequest);

    CompletableFuture<EmptyResponse> delete(String customerId);
}
