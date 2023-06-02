package com.checkout.issuing.testing.responses;

import com.google.gson.annotations.SerializedName;

public enum ReversalStatus {

    @SerializedName("Reversed")
    REVERSED,

    @SerializedName("Declined")
    DECLINED
}
