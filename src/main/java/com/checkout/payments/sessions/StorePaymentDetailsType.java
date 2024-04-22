package com.checkout.payments.sessions;

import com.google.gson.annotations.SerializedName;

public enum StorePaymentDetailsType {

    @SerializedName("disabled")
    DISABLED,

    @SerializedName("enabled")
    ENABLED,

}
