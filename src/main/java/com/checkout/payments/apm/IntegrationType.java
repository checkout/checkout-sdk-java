package com.checkout.payments.apm;

import com.google.gson.annotations.SerializedName;

public enum IntegrationType {

    @SerializedName("direct")
    DIRECT,
    @SerializedName("redirect")
    REDIRECT

}
