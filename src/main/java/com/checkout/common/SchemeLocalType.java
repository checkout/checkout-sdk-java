package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum SchemeLocalType {

    @SerializedName("accel")
    ACCEL,
    @SerializedName("cartes_bancaires")
    CARTES_BANCAIRES,
    @SerializedName("mada")
    MADA,
    @SerializedName("nyce")
    NYCE,
    @SerializedName("omannet")
    OMANNET,
    @SerializedName("pulse")
    PULSE,
    @SerializedName("star")
    STAR,
    @SerializedName("upi")
    UPI,
}
