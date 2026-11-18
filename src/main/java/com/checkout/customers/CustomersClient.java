package com.checkout.customers;

import com.checkout.EmptyResponse;
import com.checkout.common.IdResponse;

import java.util.concurrent.CompletableFuture;

/**
 * Client for the customers API: create, retrieve, update and delete customers.
 */
public interface CustomersClient {

    /**
     * Gets the details of a customer and their payment instruments ({@code GET /customers/{identifier}}).
     *
     * @param customerId the customer's ID, or their email. An identifier is sent as exactly one path
     *                   segment, so an email containing "/", {@code \} or an ASCII control character is
     *                   refused with a {@link com.checkout.CheckoutArgumentException}: use the customer's
     *                   ID instead
     * @return the customer details
     */
    CompletableFuture<CustomerResponse> get(String customerId);

    /**
     * Creates a customer ({@code POST /customers}).
     *
     * @param customerRequest the customer details
     * @return the ID of the new customer
     */
    CompletableFuture<IdResponse> create(CustomerRequest customerRequest);

    /**
     * Updates the details of a customer ({@code PATCH /customers/{identifier}}).
     *
     * @param customerId      the customer's ID
     * @param customerRequest the details to update
     * @return an empty response
     */
    CompletableFuture<EmptyResponse> update(String customerId, CustomerRequest customerRequest);

    /**
     * Deletes a customer and their linked payment instruments ({@code DELETE /customers/{identifier}}).
     *
     * @param customerId the customer's ID
     * @return an empty response
     */
    CompletableFuture<EmptyResponse> delete(String customerId);

    // Synchronous methods

    /**
     * Gets the details of a customer and their payment instruments ({@code GET /customers/{identifier}}).
     *
     * @param customerId the customer's ID, or their email. An identifier is sent as exactly one path
     *                   segment, so an email containing "/", {@code \} or an ASCII control character is
     *                   refused with a {@link com.checkout.CheckoutArgumentException}: use the customer's
     *                   ID instead
     * @return the customer details
     */
    CustomerResponse getSync(String customerId);

    /**
     * Creates a customer ({@code POST /customers}).
     *
     * @param customerRequest the customer details
     * @return the ID of the new customer
     */
    IdResponse createSync(CustomerRequest customerRequest);

    /**
     * Updates the details of a customer ({@code PATCH /customers/{identifier}}).
     *
     * @param customerId      the customer's ID
     * @param customerRequest the details to update
     * @return an empty response
     */
    EmptyResponse updateSync(String customerId, CustomerRequest customerRequest);

    /**
     * Deletes a customer and their linked payment instruments ({@code DELETE /customers/{identifier}}).
     *
     * @param customerId the customer's ID
     * @return an empty response
     */
    EmptyResponse deleteSync(String customerId);

}
