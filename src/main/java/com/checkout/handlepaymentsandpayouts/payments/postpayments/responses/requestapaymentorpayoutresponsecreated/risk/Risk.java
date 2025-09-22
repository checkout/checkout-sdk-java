package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.risk;

import lombok.Builder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * risk
 * Returns the payment's risk assessment results
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Risk {

    /**
     * Default: false Whether or not the payment was flagged by a risk check
     * [Optional]
     */
    @Builder.Default
    private Boolean flagged = false;

    /**
     * The risk score calculated by our Fraud Detection engine. Absent if not enough data provided.
     * [Optional]
     * [ 0 .. 100 ]
     */
    private Integer score;

}
