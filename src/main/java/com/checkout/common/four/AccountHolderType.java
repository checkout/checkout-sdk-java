package com.checkout.common.four;

import com.google.gson.annotations.SerializedName;

public enum AccountHolderType {

    @SerializedName("individual")
    INDIVIDUAL,
    @SerializedName("corporate")
    CORPORATE,
    @SerializedName("government")
    GOVERNMENT

}
