package com.checkout.issuing.disputes.requests;

import com.checkout.common.Resource;
import com.checkout.issuing.disputes.entities.DisputeEvidence;
import com.checkout.issuing.disputes.entities.DisputeReasonChange;
import com.checkout.issuing.disputes.entities.IssuingDisputeFraudDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Request to escalate an Issuing dispute to pre-arbitration or arbitration
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class EscalateDisputeRequest extends Resource {

    /**
     * Justification for escalating the dispute
     */
    @NotBlank
    @Size(max = 13000)
    private String justification;

    /**
     * Your evidence for escalating the dispute, in line with the card scheme's requirements.
     * If the request goes to arbitration, the card scheme ignores any evidence you provide at this stage using this request.
     */
    private List<DisputeEvidence> additionalEvidence;

    /**
     * The updated disputed amount, in the minor unit of the representment currency
     */
    private Long amount;

    /**
     * The change to the dispute reason and your justification for changing it
     */
    private DisputeReasonChange reasonChange;

    /**
     * Provides fraud-related details.
     * This field is required if the dispute has a fraud-related reason code at the escalation stage,
     * or after a requested reason code change to a fraud code.
     * <p>
     * [Optional]
     * </p>
     */
    private IssuingDisputeFraudDetails fraudDetails;
}