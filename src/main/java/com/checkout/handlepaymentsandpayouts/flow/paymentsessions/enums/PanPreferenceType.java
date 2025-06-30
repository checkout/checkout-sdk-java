package com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums;

import com.google.gson.annotations.SerializedName;

/** Specifies the preferred type of Primary Account Number (PAN) for the payment:
 * DPAN: Uses the Checkout.com Network Token.
 * FPAN: Uses the full card number.
 * Note: This only works when source.type is any of: cards instruments tokens
 */
public enum PanPreferenceType {
    @SerializedName("fpan")
    FPAN,
    @SerializedName("dpan")
    DPAN
}
