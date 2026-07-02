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
     * The unique identifier of the merchant account.
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
     * Indicates whether this is a returning customer.
     * [Optional]
     */
    private Boolean returningCustomer;

    /**
     * The date of the customer's first transaction with the merchant.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate firstTransactionDate;

    /**
     * The date of the customer's most recent transaction with the merchant.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    private LocalDate lastTransactionDate;

    /**
     * The total number of orders the customer has placed with the merchant.
     * [Optional]
     */
    private Integer totalOrderCount;

    /**
     * The amount of the customer's last payment with the merchant.
     * [Optional]
     */
    private Long lastPaymentAmount;
}