package com.checkout.payments.four.response;

import com.google.gson.annotations.SerializedName;

public enum PaymentStatus {

    @SerializedName("Authorized")
    AUTHORIZED,
    @SerializedName("Pending")
    PENDING,
    @SerializedName("Card Verified")
    CARD_VERIFIED,
    @SerializedName("Captured")
    CAPTURED,
    @SerializedName("Declined")
    DECLINED,
    @SerializedName("Paid")
    PAID

}
