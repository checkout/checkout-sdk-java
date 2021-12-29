package com.checkout.payments.hosted;

import com.google.gson.annotations.SerializedName;

public enum HostedPaymentStatus {

    @SerializedName("Payment Pending")
    PAYMENT_PENDING,

    @SerializedName("Payment Received")
    PAYMENT_RECEIVED,

    @SerializedName("Expired")
    EXPIRED
}
