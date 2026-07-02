package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.balances;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * balances
 * The payment balances
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Balances {

    /**
     * The total amount that has been authorized
     * [Optional]
     */
    private Integer totalAuthorized;

    /**
     * The total amount that has been voided
     * [Optional]
     */
    private Integer totalVoided;

    /**
     * The total amount that is still available for voiding
     * [Optional]
     */
    private Integer availableToVoid;

    /**
     * The total amount that has been captured
     * [Optional]
     */
    private Integer totalCaptured;

    /**
     * The total amount that is still available for capture
     * [Optional]
     */
    private Integer availableToCapture;

    /**
     * The total amount that has been refunded
     * [Optional]
     */
    private Integer totalRefunded;

    /**
     * The total amount that is still available for refund
     * [Optional]
     */
    private Integer availableToRefund;

}
