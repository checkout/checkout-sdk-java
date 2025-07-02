package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum StorePaymentDetailsType {

    @SerializedName("disabled")
    DISABLED,

    @SerializedName("enabled")
    ENABLED,

}
