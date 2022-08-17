package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum AccountHolderType {
    @SerializedName("individual")
    INDIVIDUAL,
    @SerializedName("corporate")
    CORPORATE,
    @SerializedName("government")
    GOVERNMENT
}
