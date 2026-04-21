package com.checkout.handlepaymentsandpayouts.flow.entities;

import com.checkout.common.Phone;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Comprehensive customer information for Flow payment sessions
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Customer {

    /**
     * The customer's email address. Required if source.type is tamara.
     * [Optional]
     */
    private String email;

    /**
     * The customer's name. Required if source.type is tamara.
     * [Optional]
     */
    private String name;

    /**
     * The unique identifier for an existing customer.
     * [Optional]
     */
    private String id;

    /**
     * The customer's phone number. Required if source.type is tamara.
     * [Optional]
     */
    private Phone phone;

    /**
     * The customer's value-added tax (VAT) registration number.
     * [Optional]
     */
    private String taxNumber;

    /**
     * Summary of the customer's transaction history. Used for risk assessment when source.type is Tamara.
     * [Optional]
     */
    private CustomerSummary summary;

    /**
     * Customer transaction history summary
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerSummary {

        /**
         * The date the customer registered.
         * [Optional]
         * Format: yyyy-MM-dd
         */
        private LocalDate registrationDate;

        /**
         * The date of the customer's first transaction.
         * [Optional]
         * Format: yyyy-MM-dd
         */
        private LocalDate firstTransactionDate;

        /**
         * The date of the customer's last payment.
         * [Optional]
         * Format: yyyy-MM-dd
         */
        private LocalDate lastPaymentDate;

        /**
         * The total number of orders made by the customer.
         * [Optional]
         */
        private Integer totalOrderCount;

        /**
         * The amount of the customer's last payment.
         * [Optional]
         */
        private Double lastPaymentAmount;

        /**
         * Specifies whether the customer is a premium customer.
         * [Optional]
         */
        private Boolean isPremiumCustomer;

        /**
         * Specifies whether the customer is a returning customer.
         * [Optional]
         */
        private Boolean isReturningCustomer;

        /**
         * The customer's lifetime value. This is the total monetary amount that the customer has ordered,
         * in their local currency, excluding canceled orders, rejected payments, refunded payments, and Tamara payments.
         * The lifetime value is an indicator of how valuable the relationship with the customer is to your company.
         * [Optional]
         */
        private Double lifetimeValue;
    }
}
