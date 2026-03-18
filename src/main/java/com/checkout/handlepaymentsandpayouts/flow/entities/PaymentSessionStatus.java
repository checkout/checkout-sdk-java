package com.checkout.handlepaymentsandpayouts.flow.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Payment submission response
 */
public enum PaymentSessionStatus {
    @SerializedName("Approved")
    APPROVED,
    @SerializedName("Declined")
    DECLINED,
    @SerializedName("Action Required")
    ACTION_REQUIRED
}