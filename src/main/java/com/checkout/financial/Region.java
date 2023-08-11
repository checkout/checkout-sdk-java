package com.checkout.financial;

import com.google.gson.annotations.SerializedName;

public enum Region {

    @SerializedName("Domestic")
    DOMESTIC,

    @SerializedName("Intra")
    INTRA,

    @SerializedName("IntraEEA")
    INTRA_EEA,

    @SerializedName("IntraEuropean_SEPA")
    INTRA_EUROPEAN_SEPA,

    @SerializedName("International")
    INTERNATIONAL,

    @SerializedName("EEA")
    EEA
}
