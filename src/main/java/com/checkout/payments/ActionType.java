package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum ActionType {

    @SerializedName("Authorization")
    AUTHORIZATION,
    @SerializedName("Card Verification")
    CARD_VERIFICATION,
    @SerializedName("Void")
    VOID,
    @SerializedName("Capture")
    CAPTURE,
    @SerializedName("Refund")
    REFUND,
    @SerializedName("Payout")
    PAYOUT,
    @SerializedName("Return")
    RETURN

}
