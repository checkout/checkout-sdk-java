package com.checkout.issuing.disputes.requests;

import com.checkout.common.Resource;
import com.checkout.issuing.disputes.entities.DisputeEvidence;
import com.checkout.issuing.disputes.entities.IssuingDisputeFraudDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * Request to amend an Issuing dispute that is currently blocked from proceeding. This endpoint handles
 * both chargeback-stage and escalation-stage amendments using the same flat payload, as the server
 * determines the context from the dispute's current state.
 * If reason specifies a fraud-related dispute, you must provide fraudDetails.
 * If you change the reason at the escalation stage, you must provide reasonChangeJustification.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class AmendDisputeRequest extends Resource {

    /**
     * The updated four-digit scheme-specific reason code.
     * If a value is not provided, the existing reason code is retained.
     */
    private String reason;

    /**
     * The updated disputed amount, in the minor unit of the transaction currency.
     * If not provided, the existing amount is retained.
     */
    private Long amount;

    /**
     * The updated or additional evidence requested by the Dispute Resolution team.
     * Follow the card scheme's requirements.
     */
    private List<DisputeEvidence> evidence;

    /**
     * Provides the fraud category, and additional context if available.
     * This field is required if reason specifies a fraud-related dispute.
     */
    private IssuingDisputeFraudDetails fraudDetails;

    /**
     * Explains the justification for the reason change. This is shared with the Dispute Resolution
     * review team and may be submitted to the card scheme.
     * This field is required if you change the reason at the escalation stage.
     * <p>
     * [Optional]
     * </p>
     * max 13000 characters
     */
    @Size(max = 13000)
    private String reasonChangeJustification;

    /**
     * Free-form text that you can use to explain your choices, provide additional context, or ask
     * questions about the requested changes.
     * <p>
     * [Optional]
     * </p>
     * max 1000 characters
     */
    @Size(max = 1000)
    private String actionResponse;

}
