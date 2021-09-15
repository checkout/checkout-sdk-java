package com.checkout.payments.four.action;

import com.google.gson.annotations.SerializedName;

public enum PaymentActionType {

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
