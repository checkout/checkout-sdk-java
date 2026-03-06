package com.checkout.issuing.disputes.requests;

import com.checkout.common.Resource;
import com.checkout.issuing.disputes.entities.DisputeEvidence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request to submit an Issuing dispute to the card scheme for processing
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SubmitDisputeRequest extends Resource {

    /**
     * The updated four-digit scheme-specific reason code.
     * If not provided, Checkout.com uses the existing reason code.
     */
    private String reason;

    /**
     * Your evidence for the chargeback, if updated since you created the dispute
     */
    private List<DisputeEvidence> evidence;

    /**
     * The updated disputed amount, in the minor unit of the transaction or representment currency.
     * If not provided, Checkout.com uses the existing disputed amount.
     */
    private Long amount;

}