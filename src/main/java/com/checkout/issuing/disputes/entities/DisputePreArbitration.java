package com.checkout.issuing.disputes.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

/**
 * Pre-arbitration details in dispute response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class DisputePreArbitration {

    /**
     * The date and time when the pre-arbitration was successfully escalated to the card scheme, in UTC
     */
    private Instant submittedOn;

    /**
     * The evidence relating to the Issuing dispute provided at the pre-arbitration stage
     */
    private List<DisputeFileEvidence> evidence;

    /**
     * The disputed amount at the pre-arbitration stage, in the minor currency unit
     */
    private DisputeAmount amount;

    /**
     * The change to the dispute reason and your justification for changing it
     */
    private DisputeReasonChange reasonChange;

    /**
     * Your justification for escalating the dispute to pre-arbitration
     */
    private String justification;

    /**
     * The date and time when the merchant provided evidence against the pre-arbitration, in UTC
     */
    private Instant merchantRespondedOn;

    /**
     * Evidence provided by the merchant against the pre-arbitration
     */
    private List<DisputeFileEvidence> merchantEvidence;
}