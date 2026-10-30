package com.checkout.handlepaymentsandpayouts.setups.entities.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Merchant account information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MerchantAccount {

    /**
     * The merchant's unique identifier for the customer's account.
     * [Optional]
     */
    private String id;

    /**
     * The date the customer registered their account with the merchant.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate registrationDate;

    /**
     * The date the customer's account with the merchant was last modified.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate lastModified;

    /**
     * Specifies if the customer is a returning customer.
     * [Optional]
     */
    private Boolean returningCustomer;

    /**
     * The date of the customer's first transaction.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate firstTransactionDate;

    /**
     * The date of the customer's most recent transaction.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate lastTransactionDate;

    /**
     * The total number of orders made by the customer.
     * [Optional]
     */
    private Integer totalOrderCount;

    /**
     * The payment amount of the customer's most recent transaction.
     * The specification types this as a number (example 55.99); the API currently stores it as an integer.
     * [Optional]
     */
    private Double lastPaymentAmount;
}