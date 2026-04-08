package com.checkout.handlepaymentsandpayouts.setups.entities.paymentMethods.common;

import com.google.gson.annotations.SerializedName;

public enum PaymentMethodStatus {

    @SerializedName("unavailable")
    UNAVAILABLE,

    @SerializedName("action_required")
    ACTION_REQUIRED,

    @SerializedName("pending")
    PENDING,

    @SerializedName("ready")
    READY,

    @SerializedName("available")
    AVAILABLE

}
