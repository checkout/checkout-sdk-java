package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.balances;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("total_authorized")
    private Integer totalAuthorized;

    /**
     * The total amount that has been voided
     * [Optional]
     */
    @SerializedName("total_voided")
    private Integer totalVoided;

    /**
     * The total amount that is still available for voiding
     * [Optional]
     */
    @SerializedName("available_to_void")
    private Integer availableToVoid;

    /**
     * The total amount that has been captured
     * [Optional]
     */
    @SerializedName("total_captured")
    private Integer totalCaptured;

    /**
     * The total amount that is still available for capture
     * [Optional]
     */
    @SerializedName("available_to_capture")
    private Integer availableToCapture;

    /**
     * The total amount that has been refunded
     * [Optional]
     */
    @SerializedName("total_refunded")
    private Integer totalRefunded;

    /**
     * The total amount that is still available for refund
     * [Optional]
     */
    @SerializedName("available_to_refund")
    private Integer availableToRefund;

}
