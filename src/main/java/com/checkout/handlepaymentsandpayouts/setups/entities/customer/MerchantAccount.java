package com.checkout.handlepaymentsandpayouts.setups.entities.customer;

import com.google.gson.annotations.SerializedName;
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
    @SerializedName("registration_date")
    private LocalDate registrationDate;

    /**
     * The date the customer's account with the merchant was last modified.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("last_modified")
    private LocalDate lastModified;

    /**
     * Indicates whether this is a returning customer.
     * [Optional]
     */
    @SerializedName("returning_customer")
    private Boolean returningCustomer;

    /**
     * The date of the customer's first transaction with the merchant.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("first_transaction_date")
    private LocalDate firstTransactionDate;

    /**
     * The date of the customer's most recent transaction with the merchant.
     * [Optional]
     * Format: yyyy-MM-dd
     */
    @SerializedName("last_transaction_date")
    private LocalDate lastTransactionDate;

    /**
     * The total number of orders the customer has placed with the merchant.
     * [Optional]
     */
    @SerializedName("total_order_count")
    private Integer totalOrderCount;

    /**
     * The amount of the customer's last payment with the merchant.
     * [Optional]
     */
    @SerializedName("last_payment_amount")
    private Long lastPaymentAmount;
}