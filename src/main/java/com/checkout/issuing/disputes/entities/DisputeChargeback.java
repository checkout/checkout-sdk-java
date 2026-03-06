package com.checkout.issuing.disputes.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * Chargeback details in dispute response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisputeChargeback {

    /**
     * The date and time when the chargeback was successfully submitted to the card scheme, in UTC
     */
    private Instant submittedOn;

    /**
     * The four-digit scheme-specific reason code you provide for the chargeback
     */
    private String reason;

    /**
     * The disputed amount, in the minor unit of the transaction currency
     */
    private DisputeAmount amount;

    /**
     * Your evidence for the chargeback
     */
    private List<DisputeFileEvidence> evidence;

    /**
     * Your justification for the chargeback
     */
    private String justification;

}