package com.checkout.issuing.disputes.requests;

import com.checkout.common.Resource;
import com.checkout.issuing.disputes.entities.DisputeEvidence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Request to create an Issuing dispute
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class CreateDisputeRequest extends Resource {

    /**
     * The transaction's unique identifier
     */
    @NotBlank
    private String transactionId;

    /**
     * The four-digit scheme-specific reason code for the chargeback.
     * Only provide this if Checkout.com is your issuing processor.
     * Checkout.com does not validate this value.
     */
    @NotBlank
    private String reason;

    /**
     * Your evidence for raising the chargeback, in line with the card scheme's requirements
     */
    private List<DisputeEvidence> evidence;

    /**
     * The chargeback amount, in the minor unit of the transaction currency.
     * If not provided, Checkout.com uses the full amount of the presentment.
     */
    private Long amount;

    /**
     * The unique identifier for the disputed presentment message, if the transaction has multiple presentments.
     * If the transaction has only one presentment, Checkout.com uses this automatically.
     */
    private String presentmentMessageId;

    /**
     * Indicates whether to submit the dispute:
     * • Immediately – Set to true.
     * • Later – Set to false.
     * Default: false
     */
    @Builder.Default
    private Boolean isReadyForSubmission = false;

    /**
     * Your justification for the chargeback
     */
    @Size(max = 100)
    private String justification;

}