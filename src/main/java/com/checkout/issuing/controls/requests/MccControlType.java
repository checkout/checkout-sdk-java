package com.checkout.issuing.controls.requests;

import com.google.gson.annotations.SerializedName;

public enum MccControlType {

    @SerializedName("allow")
    ALLOW,
    @SerializedName("block")
    BLOCK

}
