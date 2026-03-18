package com.checkout.issuing.disputes.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dispute reason change details
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisputeReasonChange {

    /**
     * The updated four-digit scheme-specific reason code for the chargeback
     */
    private String reason;

    /**
     * Your justification for changing the dispute reason
     */
    private String justification;
}