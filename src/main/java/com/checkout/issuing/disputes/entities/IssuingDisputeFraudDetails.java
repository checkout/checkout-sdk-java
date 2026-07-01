package com.checkout.issuing.disputes.entities;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Fraud-related information required when the dispute reason code is fraud-related.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class IssuingDisputeFraudDetails {

    /**
     * The type of fraud the cardholder is asserting.
     * <p>
     * [Required]
     * </p>
     */
    @SerializedName("fraud_type")
    private IssuingDisputeFraudType fraudType;

    /**
     * A description of the fraud circumstances, for internal reference.
     * This is not forwarded to the scheme.
     * <p>
     * [Optional]
     * </p>
     */
    private String description;

}
