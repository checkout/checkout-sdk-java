package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum PaymentStatus {

    @SerializedName("Active")
    ACTIVE,
    @SerializedName("Requested")
    REQUESTED,
    @SerializedName("Pending")
    PENDING,
    @SerializedName("Authorized")
    AUTHORIZED,
    @SerializedName("Card Verified")
    CARD_VERIFIED,
    @SerializedName("Canceled")
    CANCELED,
    @SerializedName("Expired")
    EXPIRED,
    @SerializedName("Paid")
    PAID,
    @SerializedName("Declined")
    DECLINED,
    @SerializedName("Voided")
    VOIDED,
    @SerializedName("Partially Captured")
    PARTIALLY_CAPTURED,
    @SerializedName("Captured")
    CAPTURED,
    @SerializedName("Partially Refunded")
    PARTIALLY_REFUNDED,
    @SerializedName("Refunded")
    REFUNDED,

}

