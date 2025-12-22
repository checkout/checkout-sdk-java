package com.checkout.handlepaymentsandpayouts.setups.entities.customer;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Merchant account information
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantAccount {

    /**
     * The unique identifier of the merchant account
     */
    private String id;

    /**
     * The date when the customer's account was first registered with the merchant
     */
    @SerializedName("registration_date")
    private Instant registrationDate;

    /**
     * The date when the customer's account was last modified
     */
    @SerializedName("last_modified")
    private Instant lastModified;

    /**
     * Indicates whether this is a returning customer
     */
    @SerializedName("returning_customer")
    private Boolean returningCustomer;

    /**
     * The date of the customer's first transaction with the merchant
     */
    @SerializedName("first_transaction_date")
    private Instant firstTransactionDate;

    /**
     * The date of the customer's most recent transaction with the merchant
     */
    @SerializedName("last_transaction_date")
    private Instant lastTransactionDate;

    /**
     * The total number of orders the customer has placed with the merchant
     */
    @SerializedName("total_order_count")
    private Integer totalOrderCount;

    /**
     * The amount of the customer's last payment with the merchant
     */
    @SerializedName("last_payment_amount")
    private Long lastPaymentAmount;
}