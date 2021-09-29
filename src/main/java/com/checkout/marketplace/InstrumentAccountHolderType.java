package com.checkout.marketplace;

import com.google.gson.annotations.SerializedName;

public enum InstrumentAccountHolderType {

    @SerializedName("individual")
    INDIVIDUAL,
    @SerializedName("corporate")
    CORPORATE,
    @SerializedName("government")
    GOVERNMENT

}
