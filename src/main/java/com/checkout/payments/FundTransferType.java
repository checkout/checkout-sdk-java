package com.checkout.payments;

import com.google.gson.annotations.SerializedName;

public enum FundTransferType {

    @SerializedName("AA")
    AA,

    @SerializedName("PP")
    PP,

    @SerializedName("FT")
    FT,

    @SerializedName("FD")
    FD,

    @SerializedName("PD")
    PD,

    @SerializedName("LO")
    LO,

    @SerializedName("OG")
    OG,

}
