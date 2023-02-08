package com.checkout.financial;

import com.google.gson.annotations.SerializedName;

public enum Region {

    @SerializedName("Domestic")
    DOMESTIC,
    @SerializedName("EEA")
    EEA,
    @SerializedName("International")
    INTERNATIONAL;
}
