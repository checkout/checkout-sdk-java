package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.customer.summary;


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
    private String registrationDate;

    /**
     * The date of the customer's first transaction.
     * [Optional]
     */
    private String firstTransactionDate;

    /**
     * The date of the customer's last payment.
     * [Optional]
     */
    private String lastPaymentDate;

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
     * The customer's lifetime value. This is the total monetary amount that the customer has ordered, in their local
     * currency, excluding the following:
     * canceled orders
     * rejected payments
     * refunded payments
     * Tamara payments
     * The lifetime value is an indicator of how valuable the relationship with the customer is to your company.
     * [Optional]
     */
    private Double lifetimeValue;

}
