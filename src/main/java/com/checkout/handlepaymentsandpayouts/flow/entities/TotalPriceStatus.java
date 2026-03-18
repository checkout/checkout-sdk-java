package com.checkout.handlepaymentsandpayouts.flow.entities;

import com.google.gson.annotations.SerializedName;

/**
 * The status of the Google Pay payment total price
 */
public enum TotalPriceStatus {
    
    @SerializedName("estimated")
    ESTIMATED,
    
    @SerializedName("final")
    FINAL
}