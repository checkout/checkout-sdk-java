package com.checkout.payments.four.sender;

import com.google.gson.annotations.SerializedName;

public enum RequestSenderType {

    @SerializedName("instrument")
    INSTRUMENT,
    @SerializedName("individual")
    INDIVIDUAL,
    @SerializedName("corporate")
    CORPORATE

}
