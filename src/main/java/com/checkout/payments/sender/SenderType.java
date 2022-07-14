package com.checkout.payments.sender;

import com.google.gson.annotations.SerializedName;

public enum SenderType {

    @SerializedName("instrument")
    INSTRUMENT,
    @SerializedName("individual")
    INDIVIDUAL,
    @SerializedName("corporate")
    CORPORATE,
    @SerializedName("government")
    GOVERNMENT

}
