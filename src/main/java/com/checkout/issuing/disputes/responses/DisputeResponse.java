package com.checkout.issuing.disputes.responses;

import com.checkout.common.Resource;
import com.checkout.issuing.disputes.entities.DisputeArbitration;
import com.checkout.issuing.disputes.entities.DisputeChargeback;
import com.checkout.issuing.disputes.entities.DisputeAmount;
import com.checkout.issuing.disputes.entities.IssuingDisputeStatus;
import com.checkout.issuing.disputes.entities.IssuingDisputeStatusReason;
import com.checkout.issuing.disputes.entities.DisputeMerchant;
import com.checkout.issuing.disputes.entities.DisputePreArbitration;
import com.checkout.issuing.disputes.entities.DisputeRepresentment;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

/**
 * Issuing dispute response
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DisputeResponse extends Resource {

    /**
     * The unique identifier for the Issuing dispute
     */
    private String id;

    /**
     * The four-digit scheme-specific reason code you provide for the chargeback
     */
    private String reason;

    /**
     * The disputed amount, in the minor unit of the transaction currency
     */
    private DisputeAmount disputedAmount;

    /**
     * The dispute status
     */
    private IssuingDisputeStatus status;

    /**
     * The status reason, which provides more information about the dispute status
     */
    private IssuingDisputeStatusReason statusReason;

    /**
     * The unique Checkout.com identifier for the transaction
     */
    private String transactionId;

    /**
     * The unique identifier for the disputed presentment message
     */
    private String presentmentMessageId;

    /**
     * The details of the merchant you raised the dispute with
     */
    private DisputeMerchant merchant;

    /**
     * The date and time when the dispute was created, in UTC
     */
    private Instant createdOn;

    /**
     * The date and time when the dispute was last modified, in UTC
     */
    private Instant modifiedOn;

    /**
     * The dispute details at the chargeback stage
     */
    private DisputeChargeback chargeback;

    /**
     * The information provided by the merchant when they reject the chargeback and send a representment
     */
    private DisputeRepresentment representment;

    /**
     * The dispute details at the pre-arbitration stage
     */
    private DisputePreArbitration preArbitration;

    /**
     * The dispute details during the arbitration stage
     */
    private DisputeArbitration arbitration;

}