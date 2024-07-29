package com.checkout.sessions;

import com.google.gson.annotations.SerializedName;

public enum PreOrderPurchaseIndicator {
    
    @SerializedName("future_availability")
    FUTURE_AVAILABILITY,
    @SerializedName("merchandise_available")
    MERCHANDISE_AVAILABLE
    
}
