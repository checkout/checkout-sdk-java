package com.checkout.issuing.disputes.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Dispute status reason enumeration
 */
public enum IssuingDisputeStatusReason {
    
    @SerializedName("expired")
    EXPIRED,
    
    @SerializedName("chargeback_pending")
    CHARGEBACK_PENDING,
    
    @SerializedName("chargeback_evidence_invalid_or_insufficient")
    CHARGEBACK_EVIDENCE_INVALID_OR_INSUFFICIENT,
    
    @SerializedName("chargeback_processed")
    CHARGEBACK_PROCESSED,
    
    @SerializedName("chargeback_rejected")
    CHARGEBACK_REJECTED,
    
    @SerializedName("chargeback_reversal_pending")
    CHARGEBACK_REVERSAL_PENDING,
    
    @SerializedName("chargeback_reversed")
    CHARGEBACK_REVERSED,
    
    @SerializedName("chargeback_response_accepted")
    CHARGEBACK_RESPONSE_ACCEPTED,
    
    @SerializedName("prearbitration_pending")
    PREARBITRATION_PENDING,
    
    @SerializedName("prearbitration_evidence_invalid_or_insufficient")
    PREARBITRATION_EVIDENCE_INVALID_OR_INSUFFICIENT,
    
    @SerializedName("prearbitration_processed")
    PREARBITRATION_PROCESSED,
    
    @SerializedName("prearbitration_rejected")
    PREARBITRATION_REJECTED,
    
    @SerializedName("prearbitration_reversal_pending")
    PREARBITRATION_REVERSAL_PENDING,
    
    @SerializedName("prearbitration_reversed")
    PREARBITRATION_REVERSED,
    
    @SerializedName("prearbitration_response_accepted")
    PREARBITRATION_RESPONSE_ACCEPTED,
    
    @SerializedName("arbitration_pending")
    ARBITRATION_PENDING,
    
    @SerializedName("arbitration_processed")
    ARBITRATION_PROCESSED,
    
    @SerializedName("presentment_reversed")
    PRESENTMENT_REVERSED
}