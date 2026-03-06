package com.checkout.issuing.disputes.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Dispute status enumeration
 */
public enum IssuingDisputeStatus {
    
    @SerializedName("created")
    CREATED,
    
    @SerializedName("canceled")
    CANCELED,
    
    @SerializedName("processing")
    PROCESSING,
    
    @SerializedName("action_required")
    ACTION_REQUIRED,
    
    @SerializedName("won")
    WON,
    
    @SerializedName("lost")
    LOST
}