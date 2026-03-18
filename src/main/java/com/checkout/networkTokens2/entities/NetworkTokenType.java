package com.checkout.networktokens.entities;

import com.google.gson.annotations.SerializedName;

public enum NetworkTokenType {
    @SerializedName("vts")
    VTS,
    
    @SerializedName("mdes")
    MDES
}