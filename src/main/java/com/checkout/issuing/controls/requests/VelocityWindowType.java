package com.checkout.issuing.controls.requests;

import com.google.gson.annotations.SerializedName;

public enum VelocityWindowType {

    @SerializedName("daily")
    DAILY,
    @SerializedName("weekly")
    WEEKLY,
    @SerializedName("monthly")
    MONTHLY,
    @SerializedName("all_time")
    ALL_TIME

}
