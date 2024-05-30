package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum PanProcessedType {

    @SerializedName("fpan")
    FPAN,

    @SerializedName("dpan")
    DPAN,

}
