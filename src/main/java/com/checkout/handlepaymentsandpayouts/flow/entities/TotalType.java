package com.checkout.handlepaymentsandpayouts.flow.entities;

import com.google.gson.annotations.SerializedName;

/**
 * The type of the Apple Pay payment total line item
 */
public enum TotalType {
    
    @SerializedName("pending")
    PENDING,
    
    @SerializedName("final")
    FINAL
}