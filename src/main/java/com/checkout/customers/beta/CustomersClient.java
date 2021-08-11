package com.checkout.customers.beta;

import com.checkout.common.beta.IdResponse;

import java.util.concurrent.CompletableFuture;

public interface CustomersClient {

    CompletableFuture<CustomerResponse> get(String customerId);

    CompletableFuture<IdResponse> create(CustomerRequest customerRequest);

    CompletableFuture<Void> update(String customerId, CustomerRequest customerRequest);

    CompletableFuture<Void> delete(String customerId);

}
