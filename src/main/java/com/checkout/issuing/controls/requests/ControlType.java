package com.checkout.issuing.controls.requests;

import com.google.gson.annotations.SerializedName;

public enum ControlType {

    @SerializedName("velocity_limit")
    VELOCITY_LIMIT,
    @SerializedName("mcc_limit")
    MCC_LIMIT

}
