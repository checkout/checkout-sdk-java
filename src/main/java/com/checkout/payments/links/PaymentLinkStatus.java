package com.checkout.payments.links;

import com.google.gson.annotations.SerializedName;

public enum PaymentLinkStatus {

    @SerializedName("Active")
    ACTIVE,
    @SerializedName("Payment Received")
    PAYMENT_RECEIVED,
    @SerializedName("Expired")
    EXPIRED

}
