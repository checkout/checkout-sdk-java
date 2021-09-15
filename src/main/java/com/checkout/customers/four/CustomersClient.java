package com.checkout.customers.four;

import com.checkout.common.four.IdResponse;

import java.util.concurrent.CompletableFuture;

public interface CustomersClient {

    CompletableFuture<CustomerResponse> get(String customerId);

    CompletableFuture<IdResponse> create(CustomerRequest customerRequest);

    CompletableFuture<Void> update(String customerId, CustomerRequest customerRequest);

    CompletableFuture<Void> delete(String customerId);

}
