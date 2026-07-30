package com.checkout.common;

import com.google.gson.annotations.SerializedName;

public enum CardCategory {

    @SerializedName(value = "Commercial", alternate = {"COMMERCIAL", "commercial"})
    COMMERCIAL,
    @SerializedName(value = "Consumer", alternate = {"CONSUMER", "consumer"})
    CONSUMER,
    @SerializedName(value = "Unknown", alternate = {"UNKNOWN", "unknown"})
    UNKNOWN

}
