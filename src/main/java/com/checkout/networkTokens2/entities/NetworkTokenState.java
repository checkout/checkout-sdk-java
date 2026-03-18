package com.checkout.networktokens.entities;

import com.google.gson.annotations.SerializedName;

public enum NetworkTokenState {
    @SerializedName("active")
    ACTIVE,
    
    @SerializedName("suspended")
    SUSPENDED,
    
    @SerializedName("inactive")
    INACTIVE,

    @SerializedName("declined")
    DECLINED,

    @SerializedName("requested")
    REQUESTED
}