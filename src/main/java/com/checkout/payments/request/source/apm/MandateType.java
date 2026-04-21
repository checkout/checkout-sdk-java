package com.checkout.payments.request.source.apm;

import com.google.gson.annotations.SerializedName;

public enum MandateType {

    @SerializedName("Core")
    CORE,

    @SerializedName("B2B")
    B2B,

}
