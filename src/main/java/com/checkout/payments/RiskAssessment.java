package com.checkout.payments;

import lombok.Data;

/**
 * Returns the payment's risk assessment results.
 */
@Data
public final class RiskAssessment {

    /**
     * Whether or not the payment was flagged by a risk check.
     * [Optional]
     * Default: false
     */
    private Boolean flagged;

    /**
     * The risk score calculated by our Fraud Detection engine. Absent if not enough data provided.
     * [Optional]
     * Decimal number, for example 22.5
     * [ 0 .. 100 ]
     */
    private Double score;
}
