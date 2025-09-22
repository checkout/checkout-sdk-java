package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.customer.summary;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * summary
 * Summary of the customer's transaction history.  Used for risk assessment when source.type is Tamara
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Summary {

    /**
     * The date the customer registered.
     * [Optional]
     */
    @SerializedName("registration_date")
    private String registrationDate;

    /**
     * The date of the customer's first transaction.
     * [Optional]
     */
    @SerializedName("first_transaction_date")
    private String firstTransactionDate;

    /**
     * The date of the customer's last payment.
     * [Optional]
     */
    @SerializedName("last_payment_date")
    private String lastPaymentDate;

    /**
     * The total number of orders made by the customer.
     * [Optional]
     */
    @SerializedName("total_order_count")
    private Integer totalOrderCount;

    /**
     * The amount of the customer's last payment.
     * [Optional]
     */
    @SerializedName("last_payment_amount")
    private Double lastPaymentAmount;

    /**
     * Specifies whether the customer is a premium customer.
     * [Optional]
     */
    @SerializedName("is_premium_customer")
    private Boolean isPremiumCustomer;

    /**
     * Specifies whether the customer is a returning customer.
     * [Optional]
     */
    @SerializedName("is_returning_customer")
    private Boolean isReturningCustomer;

    /**
     * The customer's lifetime value. This is the total monetary amount that the customer has ordered, in their local
     * currency, excluding the following:
     * canceled orders
     * rejected payments
     * refunded payments
     * Tamara payments
     * The lifetime value is an indicator of how valuable the relationship with the customer is to your company.
     * [Optional]
     */
    @SerializedName("lifetime_value")
    private Double lifetimeValue;

}
