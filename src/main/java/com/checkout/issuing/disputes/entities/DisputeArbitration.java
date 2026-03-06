package com.checkout.issuing.disputes.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Arbitration details in dispute response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisputeArbitration {

    /**
     * The date and time when the arbitration was successfully escalated to the card scheme, in UTC
     */
    private Instant submittedOn;

    /**
     * The disputed amount at the arbitration stage, in the minor currency unit
     */
    private DisputeAmount amount;

    /**
     * Your justification for escalating the dispute to arbitration
     */
    private String justification;

    /**
     * The date and time when the card scheme decided the arbitration case, in UTC
     */
    private Instant decidedOn;

}